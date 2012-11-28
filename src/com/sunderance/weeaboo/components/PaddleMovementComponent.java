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
	private float speed;

	/**
	 * Creates a paddle movement component that moves the paddle at the given
	 * speed, which is how many pixels the paddle should move per ms
	 * 
	 * @param speed How many pixels per ms the paddle moves
	 */
	public PaddleMovementComponent(float speed) {
		super();
		this.speed = speed;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		
		ComponentBasedEntity owner = getOwner();
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			owner.setVelocity(new Vector2f(-speed, 0));
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			owner.setVelocity(new Vector2f(speed, 0));
		} else {
			owner.setVelocity(Vector2f.zero());
		}
	}
}
