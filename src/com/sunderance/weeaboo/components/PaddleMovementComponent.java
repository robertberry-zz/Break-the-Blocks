/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * @author Robert Berry
 */
public class PaddleMovementComponent extends UpdateComponent {
	private float maxSpeed;
	private float acceleration;

	/**
	 * Creates a paddle movement component that moves the paddle at the given
	 * speed, which is how many pixels the paddle should move per ms
	 * 
	 * @param speed How many pixels per ms the paddle moves
	 */
	public PaddleMovementComponent(float maxSpeed, 
			float acceleration) {
		super();
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		
		ComponentBasedEntity owner = getOwner();
		
		float xSpeed = Math.abs(owner.getVelocity().getX());
		boolean aboveMax = xSpeed >= maxSpeed;
		int movement = 0;
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			movement = -1;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			movement = 1;
		}
		
		if (movement == 0) {
			if (owner.getVelocity().getX() > 0) {
				owner.setAcceleration(new Vector2f(-acceleration, 0));
			} else if (owner.getVelocity().getX() < 0) {
				owner.setAcceleration(new Vector2f(acceleration, 0));
			} else {
				owner.setAcceleration(Vector2f.zero());
			}
		} else {
			if (aboveMax) {
				owner.setAcceleration(Vector2f.zero());
				owner.setVelocity(new Vector2f(maxSpeed * movement, 0));
			} else {
				owner.setAcceleration(new Vector2f(acceleration * movement, 0));
			}
		}
	}
}
