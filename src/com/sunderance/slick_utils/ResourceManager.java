/**
 * Resource manager
 * 
 * todo: add sound management
 */
package com.sunderance.slick_utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Slick resource manager - loads and caches images and sounds based on an
 * XML definitions file.
 * 
 * @author Robert Berry
 */
public class ResourceManager {
	private final static String RESOURCE_ROOT = "res/";
	private final static String IMAGES_FOLDER = "images/";
	
	Map<String, String> imagePaths;
	Map<String, Image> cachedImages = new HashMap<String, Image>();

	/**
	 * Constructs a resource manager with the given name/path mappings
	 * 
	 * @param imagePaths The image name/path map
	 */
	public ResourceManager(Map<String, String> imagePaths) {
		this.imagePaths = imagePaths;
	}
	
	/**
	 * Given an image name returns (and caches) the image
	 * 
	 * @param name The image name
	 * @return The image
	 */
	public Image getImage(String name) {
		if (cachedImages.containsKey(name)) {
			return cachedImages.get(name);
		} else {
			String path = getImagePath(name);
			try {
				Image image = new Image(path);
				cachedImages.put(name, image);
				return image;
			} catch (SlickException e) {
				throw new RuntimeException(String.format("Resource " +
						"manager unable to load image %s at %s", name,
						path));
			}
		}
	}
	
	/**
	 * Given an image name, returns its path relative to the /res/ folder
	 * 
	 * @param name The name of the image
	 * @return The path
	 */
	public String getImagePath(String name) {
		if (imagePaths.containsKey(name)) {
			return RESOURCE_ROOT + IMAGES_FOLDER + imagePaths.get(name);
		} else {
			throw new RuntimeException(String.format("Attempt to access an " +
					"image (%s) not managed by the resource manager.", name));
		}
	}
	
	/**
	 * Creates a ResourceManager from an XML document
	 * 
	 * @param doc The document
	 * @return The resource manager
	 * @throws XPathExpressionException
	 */
	public static ResourceManager fromDocument(Document doc) {
		Element root = doc.getRootElement();
		
		HashMap<String, String> imagePaths = new HashMap<String, String>();
		
		Elements images = root.getFirstChildElement("images")
				.getChildElements("image");
		
		for (int i = 0, len = images.size(); i < len; i++) {
			Element image = images.get(i);
			
			Element name = image.getFirstChildElement("name");
			Element path = image.getFirstChildElement("path");
			
			imagePaths.put(name.getValue(), path.getValue());
		}
		
		return new ResourceManager(imagePaths);
	}
	
	/**
	 * Creates a ResourceManager from an XML file
	 * 
	 * @param file The file
	 * @return The manager
	 * @throws ParsingException 
	 * @throws ValidityException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws XPathExpressionException 
	 */
	public static ResourceManager fromFile(File file) throws ValidityException,
		ParsingException, IOException {
		Builder builder = new Builder();
		Document document = builder.build(file);
		return fromDocument(document);
	}
}
