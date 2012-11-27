package com.sunderance.weeaboo.states;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.EventTimer;
import com.sunderance.slick_utils.TimedEvent;
import com.sunderance.weeaboo.Weeaboo.State;
import com.sunderance.weeaboo.entities.Entity;

abstract public class EntityBasedState extends BaseState {

	List<Entity> entities = new LinkedList<Entity>();
	
	EventTimer timer = new EventTimer();
	
	public EntityBasedState(State stateID) {
		super(stateID);
	}

	/**
	 * Adds an entity into play
	 * 
	 * @param entity The entity
	 */
	protected void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	/**
	 * Removes an entity from play
	 * 
	 * @param entity
	 */
	protected void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	/**
	 * Adds a timed event to trigger after the given delay
	 * 
	 * @param delay The delay in milliseconds
	 * @param event The event
	 */
	protected void addTimedEvent(int delay, TimedEvent event) {
		timer.addEvent(delay, event);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics graphics)
			throws SlickException {
		for (Entity entity : entities) {
			entity.render(gc, game, graphics);
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		timer.update(delta);
		for (Entity entity : entities) {
			entity.update(gc, game, delta);
		}
	}
}
