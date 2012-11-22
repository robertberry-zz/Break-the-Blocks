/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.GeometryUtilities;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * @author Robert Berry
 */
public class PaddleMovementComponent extends Component {
	private float speed;
	
	private GeometryUtilities geoUtils;

	/**
	 * Creates a paddle movement component that moves the paddle at the given
	 * speed, which is how many pixels the paddle should move per ms
	 * 
	 * @param speed How many pixels per ms the paddle moves
	 */
	public PaddleMovementComponent(float speed) {
		super();
		this.speed = speed;
		geoUtils = GeometryUtilities.getInstance();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		
		float x_d = 0;
		
		if (input.isKeyDown(Input.KEY_LEFT)) {
			x_d -= speed * delta;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			x_d += speed * delta;
		}
		
		ComponentBasedEntity owner = getOwner();
		Vector2f newPosition = owner.getPosition().add(new Vector2f(x_d, 0));
		owner.setPosition(geoUtils.getBoundedPosition(gc, owner, newPosition));
	}
}
