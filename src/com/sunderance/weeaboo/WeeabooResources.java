package com.sunderance.weeaboo;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.newdawn.slick.Image;
import org.xml.sax.SAXException;

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
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
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
