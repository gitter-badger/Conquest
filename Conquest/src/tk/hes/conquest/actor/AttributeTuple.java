package tk.hes.conquest.actor;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class AttributeTuple implements Cloneable {

	public boolean leaveCorpse = false;
	public boolean hasShadow = false;
	public int shadowType = 0;

	public int health = 1;
	public int healthMax = 1;
	public int mana = 0;
	public int manaMax = 0;
	public int exp = 0;
	public int expMax = 0;
	public int heroExpReward = 0;
	public int goldReward = 0;
	public int chargeReward = 0;
	public int dominanceReward = 0;
	public int level = 0;

	public int attackPhysical = 0;
	public int attackRandomPhysical = 0; //max .random value
	public int attackMagic = 0;
	public int attackRandomMagical = 0;
	public int defense = 0;
	public int magicDefense = 0;
	public int range = 0;
	public int blindRange = 0;

	public int knockback = 0;
	public int knockbackResistance = 0;
	public float speed = 0f;
	public int evasion = 0;
	public int parry = 0;
	public int critChance = 0; //critical strike chance

	public String name = "null";
	public String lore = "null";
	public int purchaseCost = 0;
	public int deployDelay = 0;

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
