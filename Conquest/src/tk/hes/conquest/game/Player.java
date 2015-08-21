package tk.hes.conquest.game;

import me.deathjockey.tinypixel.Input;
import me.deathjockey.tinypixel.graphics.RenderContext;

public class Player {

	private String name;
	private Race race;
	private Origin origin;

	public Player(String name, Race race, Origin origin) {
		this.name = name;
		this.race = race;
		this.origin = origin;
	}

	public void render(RenderContext c) {

	}

	public void update(Input input) {

	}

	public String getName() {
		return name;
	}

	public Race getRace() {
		return race;
	}

	public Origin getOrigin() {
		return origin;
	}
}
