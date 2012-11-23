/**
 * 
 */
package com.sunderance.slick_utils;

import com.sunderance.weeaboo.entities.Entity;

/**
 * Utilities class for mechanics-related operations
 * 
 * @author Robert Berry
 */
public class MechanicsUtilities {
	private static MechanicsUtilities instance;
	
	/**
	 * Singleton - cannot be constructed
	 */
	private MechanicsUtilities() {
	}
	
	/**
	 * Given an entity, returns what would be its next position after the
	 * given time delta assuming it is unimpeded in its progress
	 * 
	 * @param entity The entity
	 * @param delta How much time has passed in milliseconds
	 * @return The new position
	 */
	public Vector2f getNextPosition(Entity entity, int delta) {
		return entity.getPosition()
				.add(entity.getVelocity().scale(delta))
				.add(entity.getAcceleration().scale(delta * delta * 0.5f));
	}
	
	/**
	 * Given an entity, returns what would be its next velocity after the
	 * given time delta given its current acceleration
	 * 
	 * @param entity The entity
	 * @param delta How much time has passed in milliseconds
	 * @return The new velocity
	 */
	public Vector2f getNextVelocity(Entity entity, int delta) {
		Vector2f velocity = entity.getVelocity();
		
		return velocity
				.add(entity.getAcceleration()
						.scale(delta)
						.scale(velocity.sgn()));
	}
	
	/**
	 * Instance of the singleton
	 * 
	 * @return The instance
	 */
	public MechanicsUtilities getInstance() {
		if (instance == null) {
			instance = new MechanicsUtilities();
		}
		return instance;
	}
}
