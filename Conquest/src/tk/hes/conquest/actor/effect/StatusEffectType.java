package tk.hes.conquest.actor.effect;

import me.nibby.pix.PixColor;

public enum StatusEffectType {

	HEALING("Healing", "Regenerates HP over time"),

	;

	private String name;
	private String description;
	private int nameColor = PixColor.WHITE;

	StatusEffectType(String name, String description) {
		this(name, description, PixColor.WHITE);
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
