/**
 * 
 */
package com.sunderance.weeaboo.components;

import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * Basic component.
 * 
 * @author Robert Berry
 */
public class Component {
	private ComponentBasedEntity owner;
	
	public ComponentBasedEntity getOwner() {
		return owner;
	}

	public void setOwner(ComponentBasedEntity owner) {
		this.owner = owner;
	}
}
