/**
 * 
 */
package com.sunderance.break_the_blocks.components;

import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;

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
