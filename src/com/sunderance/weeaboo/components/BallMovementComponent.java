package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.entities.ComponentBasedEntity;

public class BallMovementComponent extends Component {
	private Vector2f velocity;

	public BallMovementComponent(float speed) {
		super();
		
		velocity = new Vector2f(0, speed);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		ComponentBasedEntity owner = getOwner();
		
		owner.setPosition(owner.getPosition().add(
				new Vector2f(velocity).scale(delta)));
	}
}
