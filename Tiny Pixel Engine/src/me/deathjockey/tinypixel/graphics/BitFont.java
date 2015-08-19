package me.deathjockey.tinypixel.graphics;

import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class projects a spritesheet of glyphs onto the screen in a meaningful manner that
 * displays a string of information to the player. If the bitmap font is not monospaced,
 * you can apply specific spacing rules to tweak the aesthetics of the font on screen.
 *
 * This is <strong>NOT</strong> a class to load .ttf or other forms of font files!
 *
 * @author Kevin Yang
 */
public class BitFont {

	private String charMap;
	private int rows, columns;
	private Dimension defaultGlyphSize;
	private Spritesheet glyphBitmap;
	private RenderContext context;
	private ArrayList<BitFontSpacingRule> spacingRules = new ArrayList<>();

	/**
	 * Default constructor for each BitFont.
	 *
	 * @param glyphBitmap The bitmap spritesheet that contains the graphics of all glyphs
	 * @param charMap A pre-defined string equivalent representation of the glyphs on the spritesheet in exact order
	 * @param rows Number of rows of glyphs on the spritesheet
	 * @param columns Number of columns on the spritesheet
	 * @param defaultGlyphSize Default width and height for a single glyph (assuming monospaced for the time being)
	 */
	public BitFont(Spritesheet glyphBitmap, String charMap, int rows, int columns, Dimension defaultGlyphSize) {
		this.charMap = charMap;
		this.rows = rows;
		this.columns = columns;
		this.glyphBitmap = glyphBitmap;
		this.defaultGlyphSize = defaultGlyphSize;
	}

	/**
	 * Assigns context for the font. Once assigned, render methods can be
	 * invoked without the need to pass the context parameter.
	 *
	 * This is done automatically upon installing the font.
	 *
	 * @param context Render context to be assigned
	 */
	protected void setContext(RenderContext context) {
		this.context = context;
	}

	/**
	 * Draws a string of text using the bitmap font at a specified location.
	 *
	 * @param text The string of message
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 */
	public void render(String text, int x, int y) {
		render(text, x, y, 0x00000000);
	}

	/**
	 * Draws a string of text using the bitmap font at a specified location
	 * with a color tint.
	 *
	 * @param text The string of message
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param color Color tint to be applied to each glyph
	 */
	public void render(String text, int x, int y, int color) {
		render(text, x, y, color, 1.0f);
	}

	/**
	 * Draws a string of text using the bitmap font at a specified location
	 * with a color tint and custom glyph scaling.
	 *
	 * @param text The string of message
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param color Color tint to be applied to each glyph
	 * @param scale Custom sizing for each glyph
	 */
	public void render(String text, int x, int y, int color, float scale) {
		render(text, x, y, color, scale, 1.0f);
	}

	/**
	 * Draws a string of text using the bitmap font at a specified location
	 * with a color tint, custom glyph scaling and transparency.
	 *
	 * @param text The string of message
	 * @param x x co-ordinate on screen
	 * @param y y co-ordinate on screen
	 * @param color Color tint to be applied to each glyph
	 * @param scale Custom sizing for each glyph
	 * @param alpha Custom alpha (transparency) to be applied to each glyph
	 */
	public void render(String text, int x, int y, int color, float scale, float alpha) {
		if(context == null)
			throw new RuntimeException("Install font using RenderContext before use!");

		int drawX = x, drawY = y;

		for(int i = 0; i < text.length(); i++) {
			int index = charMap.indexOf(text.charAt(i));
			if(index < 0) continue;

			char ch = text.charAt(i);
			BitFontSpacingRule rule = getRuleFor(ch);
			int fontWidth = rule == null ? defaultGlyphSize.width : rule.glyphWidth;
			int fontSink = rule == null ? 0 : rule.glyphSink;

			//space
			if(ch == ' ') {
				drawX += (int) ((float) fontWidth * scale);
				continue;
			}

			//next line
			if(ch == '\\') {
				drawY += (int) ((float) defaultGlyphSize.height * scale) +
							(int) ((float) defaultGlyphSize.height * scale) / 6;
				drawX = x;
				continue;
			}

			Bitmap glyph = glyphBitmap.getSprite(index % columns, index / columns);
			if(scale != 1.0f) {
				glyph = glyph.getScaled(scale);
				fontSink =  (int) (fontSink * scale);
			}
			context.render(glyph, drawX, drawY + fontSink, alpha, color);
			drawX += fontWidth;
		}
	}

	/**
	 * Assigns a spacing rule for a set of glyphs.
	 *
	 * @param glyphs The string of individual affected glyphs
	 * @param width Width of affected glyphs
	 * @param sink Sinking of affected glyphs, for letters such as j, q, p
	 */
	public void addSpacingRule(String glyphs, int width, int sink) {
		BitFontSpacingRule rule = new BitFontSpacingRule(glyphs, width, sink);
		spacingRules.add(0, rule);
	}

	/**
	 * Supplies the first encountered rule for a given character glyph.
	 *
	 * @param ch The investigated character.
	 * @return First encountered rule for the provided character glyph
	 */
	@Nullable
	public BitFontSpacingRule getRuleFor(char ch) {
		for(BitFontSpacingRule rule : spacingRules) {
			if(rule.affectedGlyphs.indexOf(ch) >= 0) {
				return rule;
			}
		}
		return null;
	}

	/**
	 * Supplies the character mapping of the bitmap font image.
	 *
	 * @return Bitmap font character map
	 */
	public String getCharMap() {
		return charMap;
	}

	/**
	 * Supplies the number of rows in the bitmap font.
	 *
	 * @return Row count in the bitmap font image
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Supplies the number of columns in the bitmap font.
	 *
	 * @return Column count in the bitmap font image
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Defines a custom set spacing preferences for a list of given
	 * chars (stored as Strings). Letters such as i, j, l are much
	 * thinner than m, n, etc.
	 *
	 * As such, unless the bitmap font is monospaced, each glyph should
	 * be modified to their most aesthetically pleasing spacing options.
	 */
	private class BitFontSpacingRule {

		/** The list of glyphs to modify */
		private String affectedGlyphs;

		/** New width of the aforementioned glyphs */
		private int glyphWidth; //for thin letters i,I etc.

		/** New 'sink' of the aforementioned glyphs, for letters such as q, p, j */
		private int glyphSink;  //for j, p, q etc.

		/**
		 * Defines a set of spacing rule(s) for one or more glyphs.
		 *
		 * @param affectedGlyphs List of glyphs to modify
		 * @param width Width of the affected glyphs
		 * @param sink Sink of the affected glyphs, for letters such as p, q, j
		 */
		private BitFontSpacingRule(String affectedGlyphs, int width, int sink) {
			this.affectedGlyphs = affectedGlyphs;
			this.glyphWidth = width;
			this.glyphSink = sink;
		}
	}
}
