/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * 
 * @author Robert Berry
 */
abstract public class Component {
	private ComponentBasedEntity owner;
	
	public ComponentBasedEntity getOwner() {
		return owner;
	}

	public void setOwner(ComponentBasedEntity owner) {
		this.owner = owner;
	}

	abstract public void update(GameContainer gc, StateBasedGame game, 
			int delta);
}
