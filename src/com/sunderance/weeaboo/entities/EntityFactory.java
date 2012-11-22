/**
 * 
 */
package com.sunderance.weeaboo.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.sunderance.weeaboo.WeeabooResources;
import com.sunderance.weeaboo.components.ImageRenderComponent;

/**
 * Factory for constructing entities in the game
 * 
 * @author Robert Berry
 */
public class EntityFactory {

	private static EntityFactory instance;
	
	private WeeabooResources resources;
	
	/**
	 * This class is a singleton and cannot be constructed by an end-user
	 */
	private EntityFactory() {
		resources = WeeabooResources.getInstance();
	}
	
	/**
	 * Constructs the paddle
	 * 
	 * @return The paddle
	 */
	public ComponentBasedEntity createPaddle() {
		Image paddleImage = resources.getImage("paddle");
		Vector2f position = new Vector2f(0, 0);
		ImageRenderComponent renderPaddle = 
				new ImageRenderComponent(paddleImage);
		
		ComponentBasedEntity paddle = new ComponentBasedEntity(position,
				renderPaddle);
		
		return paddle;
	}
	
	/**
	 * Returns the singleton
	 * 
	 * @return The entity factory
	 */
	public static EntityFactory getInstance() {
		if (instance == null) {
			instance = new EntityFactory();
		}
		
		return instance;
	}
}
