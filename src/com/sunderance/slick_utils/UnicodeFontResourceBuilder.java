/**
 * 
 */
package com.sunderance.slick_utils;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import nu.xom.Element;

/**
 * Resource builder for Unicode fonts
 * 
 * @author Robert Berry
 */
public class UnicodeFontResourceBuilder extends ResourceBuilder<UnicodeFont> {
	@Override
	public UnicodeFont build(Element fontInfo) {
		String family = fontInfo.getFirstChildElement("family").getValue();
		Integer size = Integer.getInteger(fontInfo.getFirstChildElement("size")
				.getValue());
		
		int style = Font.PLAIN;
		
		String styleName = fontInfo.getFirstChildElement("style").getValue();
		
		if (styleName == "bold") {
			style = Font.BOLD;
		} else if (styleName == "italic") {
			style = Font.ITALIC;
		}
		
		Element colourInfo = fontInfo.getFirstChildElement("colour");
		
		Integer red = Integer.getInteger(colourInfo
				.getFirstChildElement("red").getValue());
		Integer green = Integer.getInteger(colourInfo
				.getFirstChildElement("green").getValue());
		Integer blue = Integer.getInteger(colourInfo
				.getFirstChildElement("blue").getValue());
		
		UnicodeFont font = new UnicodeFont(new Font(family, style, size));
		font.addAsciiGlyphs();
		
		List<ColorEffect> effects = (List<ColorEffect>) font.getEffects();
		Color colour = new Color(red, green, blue);
		effects.add(new ColorEffect(colour));
		
		return font;
	}
}
