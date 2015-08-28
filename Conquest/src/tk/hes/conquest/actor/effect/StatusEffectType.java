package tk.hes.conquest.actor.effect;

import me.deathjockey.tinypixel.graphics.Colors;

public enum StatusEffectType {

	HEALING("Healing", "Regenerates HP over time"),

	;

	private String name;
	private String description;
	private int nameColor = Colors.PURE_WHITE;

	StatusEffectType(String name, String description) {
		this(name, description, Colors.PURE_WHITE);
	}

	StatusEffectType(String name, String description, int color) {
		this.name = name;
		this.description = description;
		this.nameColor = color;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getNameColor() {
		return nameColor;
	}
}
