package com.sunderance.break_the_blocks.timed_events;

import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.slick_utils.TimedEvent;
import com.sunderance.slick_utils.Vector2f;

public class SetVelocity implements TimedEvent {
	ComponentBasedEntity entity;
	Vector2f velocity;

	public SetVelocity(ComponentBasedEntity entity, Vector2f velocity) {
		super();
		this.entity = entity;
		this.velocity = velocity;
	}

	@Override
	public void trigger() {
		entity.setVelocity(velocity);
	}

}
