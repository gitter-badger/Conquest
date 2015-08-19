package me.deathjockey.tinypixel;

public enum ResizePolicy {
	//Only increasing render area, no special methods are applied
	EXTEND_CANVAS_ONLY,

	//Screen graphics will adapt to the new size, but keeping its original 'scale'
	RESIZE_SCREEN_CONTENT
}
