package tk.hes.conquest.actor;

import me.deathjockey.tinypixel.Time;
import me.deathjockey.tinypixel.graphics.Bitmap;
import me.deathjockey.tinypixel.graphics.Colors;
import me.deathjockey.tinypixel.graphics.RenderContext;
import me.deathjockey.tinypixel.util.Vector2f;
import tk.hes.conquest.ConquestGameDesktopLauncher;
import tk.hes.conquest.actor.effect.StatusEffect;
import tk.hes.conquest.game.GameBoard;
import tk.hes.conquest.game.Origin;
import tk.hes.conquest.game.Player;
import tk.hes.conquest.graphics.Art;

import java.util.ArrayList;

public abstract class Actor implements ActionKeyFrameListener {

    public static final int SPRITE_SCALE = 2;
    protected static final int hurtTintDuration = 1000;
    protected static final int corpseDecayTime = 10000;
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
    protected boolean dead = false;
    protected long deadTime;
	protected boolean shouldAttack = true;

	protected ArrayList<StatusEffect> statusEffectList = new ArrayList<>();

    //This constructor is invoked automatically via reflection in ActorFactory to create new actors
    public Actor(Player owner) {
        this.owner = owner;
        initialize();
    }

    protected abstract void initializeAttributes(AttributeTuple tuple, BB bb, ActionSet actions);
	protected abstract void randomizeAttributes(AttributeTuple tuple);

    //Decide which animation to use, what attack to go for...
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

			if(!dead && attributes.hasShadow) {
				Bitmap shadow = Art.UNIT_SHADOW[attributes.shadowType];
				c.render(shadow, (int) (getPosition().getX() + getBB().getRx() + getBB().getWidth() / 2 - shadow.getWidth() / 2),
						(int) (getPosition().getY() + getBB().getRy() + getBB().getHeight() - getBB().getHeight() / 4 * 3), 0.7f);
			}

            if (!hurt) {
				if(!dead) {
					c.render(sprite, drawX, drawY);
				} else {
					float decayAlpha = 1f - ((float) (System.currentTimeMillis() - deadTime) / (float) corpseDecayTime);
					if(decayAlpha < 0) decayAlpha = 0;
					c.render(sprite, drawX, drawY, decayAlpha);
				}
            } else {
				float tintAlpha = (float) (System.currentTimeMillis() - hurtTime) / (float) hurtTintDuration;
                int tint = 128 - (int) (tintAlpha * 128);
				if(tint < 0) tint = 0;

                c.render(sprite, drawX, drawY, 1.0f, Colors.toInt(128, 0, 0, tint));
            }

			for(StatusEffect effect : statusEffectList) {
				effect.render(c);
			}
        }
    }

    public void update() {
		boolean canMove = true;

		if (dead) {
            currentAction = ActionType.DEATH;
			hurt = false;
			hurtAlpha = 0.7f;

            if (System.currentTimeMillis() - deadTime > corpseDecayTime)
                remove();
        } else {
			if (hurt) {
				if (System.currentTimeMillis() - hurtTime > hurtTintDuration) {
					hurt = false;
				}
				hurtAlpha = ((float) System.currentTimeMillis() - (float) hurtTime) / (float) hurtTintDuration;
			}

			if(shouldAttack) {
				//check enemies
				ArrayList<Actor> actors = board.getOpponentActorsInLane(owner, currentLane);
				for (int i = 0; i < actors.size(); i++) {
					Actor actor = actors.get(i);
					if (actor.isDead()) continue;

					int xDiff = 0;
					switch (this.owner.getOrigin()) {
						case WEST:
							xDiff = (int) (this.position.getX() + bb.getRx() + bb.getWidth()
									- actor.position.getX() - actor.getBB().getRx());
							break;
						case EAST:
							xDiff = (int) (this.position.getX() + bb.getRx()
									- (actor.position.getX() + actor.bb.getWidth() + actor.bb.getRx()));
							break;
					}

					boolean canAttack = (xDiff > 0 && owner.getOrigin().equals(Origin.EAST) ||
								xDiff < 0 && owner.getOrigin().equals(Origin.WEST))
								&& Math.abs(xDiff) > attributes.blindRange
								&& Math.abs(xDiff) <= attributes.range - Actor.SPRITE_SCALE;
					if (canAttack) {
						onAttack();
						canMove = false;
						break;
					}
				}
			}

			if (canMove) {
				move();
			}
		}

		for(int i = 0; i < statusEffectList.size(); i++) {
			StatusEffect effect = statusEffectList.get(i);
			effect.update();
			if(effect.hasExpired())
				removeStatusEffect(effect);
		}

        actionSet.update();
    }

	public void move() {
		currentAction = ActionType.MOVE;
		switch (owner.getOrigin()) {
			case WEST:
				position.setX(position.getX() + attributes.speed * (float) Time.delta);
				if(position.getX() > ConquestGameDesktopLauncher.INIT_WIDTH / ConquestGameDesktopLauncher.SCALE) {
					board.actorReachEdge(this);
				}
				break;
			case EAST:
				position.setX(position.getX() - attributes.speed * (float) Time.delta);
				if(position.getX() + bb.getWidth() + 2 < 0) {
					board.actorReachEdge(this);
				}
				break;
		}
	}

	public void addStatusEffect(StatusEffect effect) {
		this.statusEffectList.add(effect);
	}

	public void removeStatusEffect(StatusEffect effect) {
		statusEffectList.remove(effect);
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
		int knockback = provoker.attributes.knockback - attributes.knockbackResistance;
		if(knockback > 0)
			position.setX(position.getX() + (owner.getOrigin().equals(Origin.WEST) ? -knockback : knockback));

        attributes.health -= totalDamage;
        hurt = true;
        hurtTime = System.currentTimeMillis();

        if (attributes.health <= 0) {
            if (attributes.leaveCorpse) {
                dead = true;
                deadTime = System.currentTimeMillis();
                onDeath();
				board.actorDeath(this);
            } else remove();
        }
    }

    public void assignBoard(GameBoard board, int lane) {
        this.board = board;
        this.currentLane = lane;

		randomizeAttributes(attributes);
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

	public GameBoard getGameBoard() {
		return board;
	}

	public int getCurrentLane() {
		return currentLane;
	}

	public SampleActor getSampleActor() {
		return getSampleActor(new Vector2f(0, 0));
	}

	public SampleActor getSampleActor(Vector2f position) {
		return getSampleActor(position, ActionType.STATIC);
	}

	public SampleActor getSampleActor(Vector2f position, ActionType action) {
		return getSampleActor(position, action, 1.0f);
	}

	public SampleActor getSampleActor(Vector2f position, ActionType action, float scale) {
		return new SampleActor(this, position, action, scale);
	}
}
