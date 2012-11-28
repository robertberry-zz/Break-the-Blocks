/**
 * 
 */
package com.sunderance.break_the_blocks.entities;

import org.newdawn.slick.Image;

import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.components.ImageRenderComponent;
import com.sunderance.break_the_blocks.components.PaddleMovementComponent;

/**
 * Factory for constructing entities in the game
 * 
 * @author Robert Berry
 */
public class EntityFactory {

	private static EntityFactory instance;
	
	private BreakTheBlocksResources resources;
	
	/**
	 * This class is a singleton and cannot be constructed by an end-user
	 */
	private EntityFactory() {
		resources = BreakTheBlocksResources.getInstance();
	}
	
	/**
	 * Constructs the paddle
	 * 
	 * @return The paddle
	 */
	public ComponentBasedEntity createPaddle() {
		Image paddleImage = resources.getImage("paddle");
		ImageRenderComponent renderPaddle = 
				new ImageRenderComponent(paddleImage);
		
		ComponentBasedEntity paddle = new ComponentBasedEntity(renderPaddle);
		paddle.addUpdateComponent(new PaddleMovementComponent(0.5f));
		
		return paddle;
	}
	
	/**
	 * Constructs the ball
	 * 
	 * @return The ball
	 */
	public ComponentBasedEntity createBall() {
		Image ballImage = resources.getImage("ball");
		ImageRenderComponent renderBall = new ImageRenderComponent(ballImage);
		
		ComponentBasedEntity ball = new ComponentBasedEntity(renderBall);
		
		return ball;
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
