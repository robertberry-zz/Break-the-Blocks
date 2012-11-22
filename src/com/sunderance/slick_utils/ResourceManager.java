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
import org.newdawn.slick.UnicodeFont;

/**
 * Slick resource manager - loads and caches images and sounds based on an
 * XML definitions file.
 * 
 * @author Robert Berry
 */
public class ResourceManager {
	private static final String IMAGE_FOLDER_PATH = "res/images/";
	
	private ResourceCache<Image> imageCache;
	private ResourceCache<UnicodeFont> fontCache;

	/**
	 * Constructs a resource manager with the given image cache
	 * 
	 * @param imageCache The image cache
	 */
	private ResourceManager(ResourceCache<Image> imageCache,
			ResourceCache<UnicodeFont> fontCache) {
		this.imageCache = imageCache;
		this.fontCache = fontCache;
	}
	
	/**
	 * Given an image name returns the image
	 * 
	 * @param name The image name
	 * @return The image
	 */
	public Image getImage(String name) {
		return imageCache.getResource(name);
	}
	
	/**
	 * Given a font name returns the font
	 * 
	 * @param name The font name
	 * @return The font
	 */
	public UnicodeFont getFont(String name) {
		return fontCache.getResource(name);
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
		
		ResourceCache<UnicodeFont> fontCache =
				new ResourceCache<UnicodeFont>(new 
						UnicodeFontResourceBuilder());
		
		for (int i = 0, len = fonts.size(); i < len; i++) {
			fontCache.addResourceDefinition(fonts.get(i));
		}
		
		return new ResourceManager(imageCache, fontCache);
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
