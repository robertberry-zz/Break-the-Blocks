package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

public class BallMovementComponent extends UpdateComponent {
	private Vector2f velocity;

	public BallMovementComponent(float speed) {
		super();
		
		velocity = new Vector2f(0, speed);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		ComponentBasedEntity owner = getOwner();
		
		Vector2f newPosition = owner.getPosition().add(velocity.scale(delta));
		
		owner.setPosition(newPosition);
	}
}
