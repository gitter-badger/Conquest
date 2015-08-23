package tk.hes.conquest.actor;

import me.deathjockey.tinypixel.Time;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;

import java.util.ArrayList;

public abstract class Actor implements ActionKeyFrameListener {

    public static final int SPRITE_SCALE = 2;

    protected Vector2f position;
    protected BB bb;
    protected AttributeTuple attributes;
    protected ActionSet actionSet;
    protected ActionType currentAction = ActionType.MOVE;
    protected boolean remove = false;
    protected Player owner;
    protected GameBoard board;
    protected int currentLane;
    protected boolean hurt = false;
    protected long hurtTime;
	protected float hurtAlpha = 1.0f;
    protected static final int hurtTintDuration = 1000;
    protected boolean dead = false;
    protected long deadTime;
    protected static final int corpseDecayTime = 10000;

    //This constructor is invoked automatically via reflection in ActorFactory to create new actors
    public Actor(Player owner) {
        this.owner = owner;
        initialize();
    }

    protected abstract void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions);

    //Decide which animation to use, what attack to go for...
    public abstract void preAttack();

    //The actual 'hitbox calculation & apply damage logic'
    public abstract void onAttack();

    public abstract void onDeath();

    private void initialize() {
        attributes = new AttributeTuple();
        bb = new BB();
        actionSet = new ActionSet(this);
        position = new Vector2f(0, 0);
        initializeAttributes(attributes, bb, actionSet);
    }

    public void render(RenderContext c) {
        Action action = actionSet.get(currentAction);

        if (action != null) {
            Action.Frame frame = action.getCurrentFrame();
            boolean flipped = owner.getOrigin().equals(Origin.EAST);
            Bitmap sprite = (flipped) ? frame.getBitmap().getFlipped(false, true) : frame.getBitmap();
			int drawX = (int) position.getX() + ((flipped) ? frame.getxOffset() : 0);
			int drawY = (int) position.getY() + ((flipped) ? frame.getyOffset() : 0);

            if (!hurt) {
				if(!dead) {
					c.render(sprite, drawX, drawY);
				} else {
					c.render(sprite, drawX, drawY,
							1f - ((float) System.currentTimeMillis() - (float) deadTime) * (float)corpseDecayTime);
				}
            } else {
                int tint = 128 - (int) ((float) (System.currentTimeMillis() - hurtTime) / (float) hurtTintDuration * 128);
				if(tint < 0) tint = 0;

                //System.out.println(tint);
                c.render(sprite, (int) position.getX() + ((flipped) ? frame.getxOffset() : 0),
                        (int) position.getY() + ((flipped) ? frame.getyOffset() : 0), 1.0f,
                        Colors.toInt(255, 0, 0, (int) hurtAlpha * 255));
            }
        }
    }

    public void update() {
        boolean canMove = true; //todo more move logic

        if (dead) {
            currentAction = ActionType.DEATH;
			hurt = true;
			hurtAlpha = 0.7f;

            if (System.currentTimeMillis() - deadTime > corpseDecayTime)
                remove();
        } else {
			if(hurt) {
				if (System.currentTimeMillis() - hurtTime > hurtTintDuration) {
					hurt = false;
				}
				hurtAlpha = ((float) System.currentTimeMillis() - (float) hurtTime) / (float) hurtTintDuration;
			}

            //check enemies
            ArrayList<Actor> actors = board.getActorsInLane(currentLane);
            for (int i = 0; i < actors.size(); i++) {
                Actor actor = actors.get(i);
                if (this.owner.equals(actor.getOwner())) continue;
                if (actor.equals(this)) continue;
                if (actor.isDead()) continue;

                int xDiff = 0;
                switch (this.owner.getOrigin()) {
                    case WEST:
                        xDiff = (int) Math.abs(this.position.getX() + bb.getRx() + bb.getWidth() - actor.position.getX() - actor.getBB().getRx());
                        break;
                    case EAST:
                        xDiff = (int) Math.abs(this.position.getX() + bb.getRx() - (actor.position.getX() + actor.bb.getWidth() + actor.bb.getRx()));
                        break;
                }
                //xDiff < range - blindRage = can fire
				//TODO range check bugged (not hitting issue)
                boolean canAttack = (xDiff > attributes.blindRange && xDiff <= attributes.range - 4);
                if (canAttack) {
                    preAttack();
                    canMove = false;
                }
            }

            if (canMove) {
                currentAction = ActionType.MOVE;
                switch (owner.getOrigin()) {
                    case WEST:
                        position.setX(position.getX() + attributes.speed * (float) Time.delta);
                        break;
                    case EAST:
                        position.setX(position.getX() - attributes.speed * (float) Time.delta);
                        break;
                }
            }
        }

        actionSet.update();
    }

    public void hurt(Actor provoker) {
        AttributeTuple ptuple = provoker.attributes;
        int physicalDamage = ptuple.attackPhysical + (int) (Math.random() * ptuple.attackRandomPhysical)
                - this.attributes.defense;
        int magicalDamage = ptuple.attackMagic + (int) (Math.random() * ptuple.attackRandomMagical)
                - this.attributes.magicDefense;
        int totalDamage = physicalDamage + magicalDamage;
        boolean evaded = (int) (Math.random() * 100) < attributes.evasion;
        if (evaded) {
            //TODO evasion skillset / animation / etc.
            return;
        }
        attributes.health -= totalDamage;
        hurt = true;
        hurtTime = System.currentTimeMillis();

        if (attributes.health < 0) {
            if (attributes.leaveCorpse) {
                dead = true;
                deadTime = System.currentTimeMillis();
                onDeath();
            } else remove();
        }
    }

    public void assignBoard(GameBoard board, int lane) {
        this.board = board;
        this.currentLane = lane;
    }

    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    public void remove() {
        remove = true;
    }

    public boolean canRemove() {
        return remove;
    }

    public boolean isDead() {
        return dead;
    }

    public ActionType getCurrentAction() {
        return currentAction;
    }

    public AttributeTuple getAttributes() {
        return attributes;
    }

    public BB getBB() {
        return bb;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }


}
