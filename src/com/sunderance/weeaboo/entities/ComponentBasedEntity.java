/**
 * 
 */
package com.sunderance.weeaboo.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;
import com.sunderance.weeaboo.components.UpdateComponent;
import com.sunderance.weeaboo.components.RenderComponent;

/**
 * Entity constructed with components
 * 
 * @author Robert Berry
 */
public class ComponentBasedEntity implements Entity {
	private Vector2f position;
	private Vector2f velocity;
	private Vector2f acceleration;
	
	private RenderComponent renderComponent;
	private List<UpdateComponent> components = new ArrayList<UpdateComponent>();
	
	/**
	 * Create a component based entity at the given position and with the given
	 * render component
	 * 
	 * @param position The position
	 * @param renderComponent The component used to render the entity
	 */
	public ComponentBasedEntity(RenderComponent renderComponent) {
		super();
		this.position = Vector2f.zero();
		this.velocity = Vector2f.zero();
		this.acceleration = Vector2f.zero();
		this.setRenderComponent(renderComponent);
	}
	
	/**
	 * Sets the render component of the entity
	 * 
	 * @param renderComponent The render component
	 */
	public void setRenderComponent(RenderComponent renderComponent) {
		this.renderComponent = renderComponent;
		this.addUpdateComponent(renderComponent);
	}

	/**
	 * Add a component to the Entity
	 * 
	 * @param component The component
	 */
	public void addUpdateComponent(UpdateComponent component) {
		component.setOwner(this);
		components.add(component);
	}
	
	/**
	 * Width of the entity in pixels
	 * 
	 * @return The width
	 */
	public int getWidth() {
		return renderComponent.getWidth();
	}
	
	/**
	 * Height of the entity in pixels
	 * 
	 * @return The height
	 */
	public int getHeight() {
		return renderComponent.getHeight();
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
		for (UpdateComponent component : components) {
			component.update(gc, game, delta);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		renderComponent.render(gc, game, graphics);
	}

	@Override
	public Vector2f getVelocity() {
		return velocity;
	}

	@Override
	public Vector2f getAcceleration() {
		return acceleration;
	}

	@Override
	public void setVelocity(Vector2f velocity) {
		this.velocity = velocity;
	}

	@Override
	public void setAcceleration(Vector2f acceleration) {
		this.acceleration = acceleration;
	}

}
