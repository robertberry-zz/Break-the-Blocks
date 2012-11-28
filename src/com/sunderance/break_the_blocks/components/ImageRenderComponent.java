/**
 * 
 */
package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.slick_utils.Rect;
import com.sunderance.slick_utils.Vector2f;

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
		Rect rect = owner.getRect();
		Vector2f position = rect.getTopLeft();
		this.image.draw(position.getX(), position.getY());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// do nothing
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}
}
