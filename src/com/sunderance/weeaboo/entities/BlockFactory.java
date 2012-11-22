package com.sunderance.weeaboo.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import com.sunderance.weeaboo.WeeabooResources;
import com.sunderance.weeaboo.components.ImageRenderComponent;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class BlockFactory {
	private static BlockFactory instance;
	
	private WeeabooResources resources;
	
	private BlockFactory() {
		resources = WeeabooResources.getInstance();
	}
	
	enum BlockType {
		BASIC
	}
	
	private ComponentBasedEntity createBlock(float x, float y, BlockType type,
			String imageName) {
		ComponentBasedEntity entity = new ComponentBasedEntity(
				new ImageRenderComponent(resources.getImage(imageName)));
		
		entity.setPosition(new Vector2f(x, y));
		return entity;
	}
	
	/**
	 * Creates a list of blocks from a given XML document definition
	 * 
	 * @param doc The XML document
	 * @return The blocks
	 */
	public List<ComponentBasedEntity> createBlocks(Document doc) {
		List<ComponentBasedEntity> blocks = 
				new ArrayList<ComponentBasedEntity>();
		Element root = doc.getRootElement();
		
		Elements blockDefinitions = root.getChildElements("block");
		
		for (int i = 0, len = blockDefinitions.size(); i < len; i++) {
			Element blockDefinition = blockDefinitions.get(i);
			
			String typeName = blockDefinition.getFirstChildElement("type")
					.getValue();
			
			String imageName = blockDefinition
					.getFirstChildElement("image_name").getValue();
			
			BlockType type = BlockType.BASIC;
			
			Integer x = Integer.parseInt(blockDefinition
					.getFirstChildElement("x").getValue());
			Integer y = Integer.parseInt(blockDefinition
					.getFirstChildElement("y").getValue());

			blocks.add(createBlock(x, y, type, imageName));
		}
		
		return blocks;
	}
	
	public static BlockFactory getInstance() {
		if (instance == null) {
			instance = new BlockFactory();
		}
		return instance;
	}
}
