/**
 * 
 */
package com.sunderance.break_the_blocks.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;

/**
 * An entity is a visible object in the game
 * 
 * @author Robert Berry
 */
public interface Entity {
	/**
	 * Update method
	 * 
	 * @param gc
	 * @param game
	 * @param delta
	 */
	public void update(GameContainer gc, StateBasedGame game, int delta);
	
	/**
	 * Render method
	 * 
	 * @param gc
	 * @param game
	 * @param graphics
	 */
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics);
	
	public Vector2f getPosition();
	
	public Vector2f getVelocity();
	
	public Vector2f getAcceleration();
	
	public void setPosition(Vector2f position);
	
	public void setVelocity(Vector2f velocity);
	
	public void setAcceleration(Vector2f acceleration);
}
