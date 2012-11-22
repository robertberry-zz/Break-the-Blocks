/**
 * 
 */
package com.sunderance.weeaboo.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.components.Component;
import com.sunderance.weeaboo.components.RenderComponent;

/**
 * Entity constructed with components
 * 
 * @author Robert Berry
 */
public class ComponentBasedEntity implements Entity {

	private Vector2f position;
	
	private RenderComponent renderComponent;
	
	private List<Component> components = new ArrayList<Component>();
	
	/**
	 * Create a component based entity at the given position and with the given
	 * render component
	 * 
	 * @param position The position
	 * @param renderComponent The component used to render the entity
	 */
	public ComponentBasedEntity(Vector2f position, 
			RenderComponent renderComponent) {
		super();
		this.position = position;
		this.setRenderComponent(renderComponent);
	}
	
	/**
	 * Sets the render component of the entity
	 * 
	 * @param renderComponent The render component
	 */
	public void setRenderComponent(RenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		this.addComponent(renderComponent);
	}

	/**
	 * Add a component to the Entity
	 * 
	 * @param component The component
	 */
	public void addComponent(Component component) {
		component.setOwner(this);
		components.add(component);
	}
	
	/**
	 * Current position of the entity
	 * 
	 * @return The position
	 */
	public Vector2f getPosition() {
		return position;
	}

	/**
	 * Sets the current position of the entity
	 * 
	 * @param position The new position
	 */
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		for (Component component : components) {
			component.update(gc, game, delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		renderComponent.render(gc, game, graphics);
	}

}
