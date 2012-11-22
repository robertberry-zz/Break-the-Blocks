/**
 * 
 */
package com.sunderance.slick_utils;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import org.newdawn.slick.SlickException;
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
		Integer size = Integer.parseInt(fontInfo.getFirstChildElement("size")
				.getValue());
		
		int style = Font.PLAIN;
		
		String styleName = fontInfo.getFirstChildElement("style").getValue();
		
		if (styleName == "bold") {
			style = Font.BOLD;
		} else if (styleName == "italic") {
			style = Font.ITALIC;
		}
		
		Element colourInfo = fontInfo.getFirstChildElement("colour");
		
		Integer red = Integer.parseInt(colourInfo
				.getFirstChildElement("red").getValue());
		Integer green = Integer.parseInt(colourInfo
				.getFirstChildElement("green").getValue());
		Integer blue = Integer.parseInt(colourInfo
				.getFirstChildElement("blue").getValue());
		
		UnicodeFont font = new UnicodeFont(new Font(family, style, size));
		font.addAsciiGlyphs();
		
		Color colour = new Color(red, green, blue);
		font.getEffects().add(new ColorEffect(colour));
				
		try {
			font.loadGlyphs();
		} catch (SlickException e) {
			// TODO throw an unchecked exception
		}
		
		return font;
	}
}
