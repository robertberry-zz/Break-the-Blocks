/**
 * 
 */
package com.sunderance.slick_utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;

import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * Singleton supplying functions for working with vectors, components, and
 * games
 * 
 * @author Robert Berry
 */
public class GeometryUtilities {
	private static GeometryUtilities instance;
	
	public enum Bound {
		TOP, LEFT, RIGHT, BOTTOM
	}
	
	/**
	 * Singleton - cannot be instantiated by end-user.
	 */
	private GeometryUtilities() {
	}
	
	/**
	 * Given a position for an entity within the game screen, returns that
	 * position but bounded within the game screen play area, so that the
	 * entity is unable to move outside of it.
	 * 
	 * TODO write a better comment
	 * 
	 * @param gc
	 * @param entity
	 * @param newPosition
	 * @return
	 */
	public Vector2f getBoundedPosition(GameContainer gc, 
			ComponentBasedEntity entity, Vector2f newPosition) {
		Set<Bound> bounds = new HashSet<Bound>(Arrays.asList(Bound.values()));
		return getBoundedPosition(gc, entity, newPosition, bounds);
	}
	
	public Vector2f getBoundedPosition(GameContainer gc, 
			ComponentBasedEntity entity, Vector2f newPosition,
			Set<Bound> bounds) {
		float x = newPosition.getX();
		float y = newPosition.getY();
		
		if (bounds.contains(Bound.LEFT) && x < 0) {
			x = 0;
		} else if (bounds.contains(Bound.RIGHT) &&
				x + entity.getWidth() > gc.getWidth()) {
			x = gc.getWidth() - entity.getWidth();
		}
		
		if (bounds.contains(Bound.TOP) && y < 0) {
			y = 0;
		} else if (bounds.contains(Bound.BOTTOM) &&
				x + entity.getHeight() > gc.getHeight()) {
			y = gc.getHeight() - entity.getHeight();
		}
		
		return new Vector2f(x, y);
	}
	
	/**
	 * Vector to place entity at the bottom centre of the screen
	 * 
	 * @param gc The game container
	 * @param entity The entity
	 * @return The position
	 */
	public Vector2f getBottomCentre(GameContainer gc, 
			ComponentBasedEntity entity) {
		return new Vector2f(getCentreX(gc, entity), getBottomY(gc, entity));
	}
	
	/**
	 * Vector to place entity at the middle centre of the screen
	 * 
	 * @param gc The game container
	 * @param entity The entity
	 * @return The position
	 */
	public Vector2f getMiddleCentre(GameContainer gc,
			ComponentBasedEntity entity) {
		return new Vector2f(getCentreX(gc, entity), getMiddleY(gc, entity));
	}
	
	/**
	 * The y co-ordinate to place entity at the bottom of the screen
	 * 
	 * @param gc The game container
	 * @param entity The entity
	 * @return The y co-ordinate
	 */
	public float getBottomY(GameContainer gc, ComponentBasedEntity entity) {
		return gc.getHeight() - entity.getHeight();
	}
	
	/**
	 * Central x co-ordinate for the game
	 * 
	 * @param gc The game container
	 * @return The x co-ordinate
	 */
	public float getCentreX(GameContainer gc) {
		return gc.getWidth() / 2;
	}
	
	/**
	 * Middle y co-ordinate for the game
	 * 
	 * @param gc The game container
	 * @return The y co-ordinate
	 */
	public float getMiddleY(GameContainer gc) {
		return gc.getHeight() / 2;
	}
	
	/**
	 * Y co-ordinate to place entity in the middle of the game play area
	 * 
	 * @param gc The game container
	 * @param entity The entity
	 * @return The y co-ordinate
	 */
	public float getMiddleY(GameContainer gc, ComponentBasedEntity entity) {
		return getMiddleY(gc) - entity.getHeight() / 2;
	}
	
	/**
	 * X co-ordinate for the top-left of the entity to place it centrally
	 * in the game play area
	 * 
	 * @param gc The game container
	 * @param entity The entity
	 * @return The x co-ordinate
	 */
	public float getCentreX(GameContainer gc, ComponentBasedEntity entity) {
		return getCentreX(gc) - entity.getWidth() / 2;
	}
	
	/**
	 * Instance of the singleton
	 * 
	 * @return The instance
	 */
	public static GeometryUtilities getInstance() {
		if (instance == null) {
			instance = new GeometryUtilities();
		}
		return instance;
	}
}
