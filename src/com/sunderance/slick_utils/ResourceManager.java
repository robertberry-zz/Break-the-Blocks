/**
 * Resource manager
 * 
 * todo: add sound management
 */
package com.sunderance.slick_utils;

import java.io.File;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.Image;

/**
 * Slick resource manager - loads and caches images and sounds based on an
 * XML definitions file.
 * 
 * @author Robert Berry
 */
public class ResourceManager {
	private static final String IMAGE_FOLDER_PATH = "res/images/";
	
	private ResourceCache<Image> imageCache;

	/**
	 * Constructs a resource manager with the given image cache
	 * 
	 * @param imageCache The image cache
	 */
	private ResourceManager(ResourceCache<Image> imageCache) {
		this.imageCache = imageCache;
	}
	
	/**
	 * Given an image name returns (and caches) the image
	 * 
	 * @param name The image name
	 * @return The image
	 */
	public Image getImage(String name) {
		return imageCache.getResource(name);
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
		Elements images = root.getFirstChildElement("images")
				.getChildElements("image");
		
		ImageResourceBuilder imageBuilder = 
				new ImageResourceBuilder(IMAGE_FOLDER_PATH);
		ResourceCache<Image> imageCache = 
				new ResourceCache<Image>(imageBuilder);
		
		for (int i = 0, len = images.size(); i < len; i++) {
			imageCache.addResourceDefinition(images.get(i));
		}
		
		Elements fonts = root.getFirstChildElement("fonts")
				.getChildElements("font");
		
		for (int i = 0, len = fonts.size(); i < len; i++) {
			// pass
		}
		
		return new ResourceManager(imageCache);
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
