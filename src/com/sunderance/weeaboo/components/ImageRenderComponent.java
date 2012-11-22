/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.entities.ComponentBasedEntity;

/**
 * Render component that just draws a static image at the X, Y co-ordinates of
 * the entity
 * 
 * @author Robert Berry
 */
public class ImageRenderComponent extends RenderComponent {
	private Image image;
	
	/**
	 * Constructs an ImageRenderComponent of the given image
	 * 
	 * @param image
	 */
	public ImageRenderComponent(Image image) {
		this.image = image;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		ComponentBasedEntity owner = getOwner();
		Vector2f position = owner.getPosition();
		this.image.draw(position.getX(), position.getY());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// do nothing
	}
}
