package com.sunderance.weeaboo;

import java.io.File;
import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

import com.sunderance.slick_utils.ResourceManager;

/**
 * Singleton for managing game resources
 * 
 * @author Robert Berry
 */
public class WeeabooResources {
	private static final String DEFINITIONS_PATH = "res/xml/resources.xml";
	
	private static WeeabooResources instance;
	
	ResourceManager manager;
	
	private WeeabooResources() {
		File definitions = new File(DEFINITIONS_PATH);
		
		// TODO clean this up
		try {
			manager = ResourceManager.fromFile(definitions);
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the image with the given name
	 * 
	 * @param name The name
	 * @return The image
	 */
	public Image getImage(String name) {
		return manager.getImage(name);
	}
	
	/**
	 * Returns the font with the given name
	 * 
	 * @param name The name
	 * @return The font
	 */
	public UnicodeFont getFont(String name) {
		return manager.getFont(name);
	}
	
	/**
	 * An instance of the resource manager
	 * 
	 * @return The instance
	 */
	public static WeeabooResources getInstance() {
		if (instance == null) {
			instance = new WeeabooResources();
		}
		
		return instance;
	}
}
