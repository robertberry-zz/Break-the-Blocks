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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Slick resource manager - loads and caches images and sounds based on an
 * XML definitions file.
 * 
 * @author Robert Berry
 */
public class ResourceManager {
	private final String RESOURCE_ROOT = "res/";
	
	private final String IMAGES_FOLDER = "images/";
	
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
	public static ResourceManager fromDocument(Document doc) 
			throws XPathExpressionException {
		Map<String, String> imagePaths = new HashMap<String, String>();
		
		NodeList images = doc.getDocumentElement()
				.getElementsByTagName("image");
		
	
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression selectTitle = xpath.compile("name");
		XPathExpression selectPath = xpath.compile("path");
		
		for (int i = 0, len = images.getLength(); i < len; i++) {
			Node node = images.item(i);

			Element image = (Element) node;
			
			String title = ((NodeList) selectTitle.evaluate(image, 
					XPathConstants.NODESET)).item(0).getTextContent();
			String path = ((NodeList) selectPath.evaluate(image, 
					XPathConstants.NODESET)).item(0).getTextContent();
			
			imagePaths.put(title, path);
		}
		
		return new ResourceManager(imagePaths);
	}
	
	/**
	 * Creates a ResourceManager from an XML file
	 * 
	 * @param file The file
	 * @return The manager
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws XPathExpressionException 
	 */
	public static ResourceManager fromFile(File file) throws 
		ParserConfigurationException, SAXException, IOException, 
		XPathExpressionException {
		DocumentBuilderFactory docBuilderFactory = 
				DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);
		
		return fromDocument(doc);
	}
}
