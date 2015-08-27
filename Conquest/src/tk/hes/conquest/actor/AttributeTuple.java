package tk.hes.conquest.actor;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class AttributeTuple implements Cloneable {

	public boolean leaveCorpse;
	public boolean hasShadow;
	public int shadowType;

	public int health;
	public int healthMax;
	public int mana;
	public int manaMax;
	public int exp;
	public int expMax;
	public int heroExpReward;
	public int goldReward;
	public int chargeReward;
	public int level;

	public int attackPhysical;
	public int attackRandomPhysical; //max .random value
	public int attackMagic;
	public int attackRandomMagical;
	public int defense;
	public int magicDefense;
	public int range;
	public int blindRange;

	public int knockback;
	public int knockbackResistance;
	public float speed;
	public int evasion;
	public int critChance; //critical strike chance

	public String name;
	public String lore;
	public int purchaseCost;
	public int deployDelay;

	@Nullable
	public static final AttributeTuple getCopyOf(@NotNull AttributeTuple tuple) {
		try {
			return (AttributeTuple) tuple.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
