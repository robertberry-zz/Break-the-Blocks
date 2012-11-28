package com.sunderance.weeaboo.timed_events;

import com.sunderance.slick_utils.TimedEvent;
import com.sunderance.slick_utils.Vector2f;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

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
