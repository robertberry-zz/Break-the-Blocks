package com.sunderance.weeaboo.states;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.Weeaboo.State;
import com.sunderance.weeaboo.entities.Entity;

abstract public class EntityBasedState extends BaseState {

	List<Entity> entities = new ArrayList<Entity>();
	
	public EntityBasedState(State stateID) {
		super(stateID);
	}

	protected void addEntity(Entity entity) {
		entities.add(entity);
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
		for (Entity entity : entities) {
			entity.update(gc, game, delta);
		}
	}

}
