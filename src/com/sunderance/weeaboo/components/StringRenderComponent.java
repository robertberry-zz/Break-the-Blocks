/**
 * 
 */
package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Render component for rendering a static string in a given font
 * 
 * @author Robert Berry
 */
public class StringRenderComponent extends RenderComponent {

	UnicodeFont font;
	String text;
	
	public StringRenderComponent(UnicodeFont font, String text) {
		super();
		this.font = font;
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see com.sunderance.weeaboo.components.RenderComponent#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		Vector2f position = getOwner().getPosition();
		
		font.drawString(position.getX(), position.getY(), text);
	}

	/* (non-Javadoc)
	 * @see com.sunderance.weeaboo.components.RenderComponent#getWidth()
	 */
	@Override
	public int getWidth() {
		return font.getWidth(text);
	}

	/* (non-Javadoc)
	 * @see com.sunderance.weeaboo.components.RenderComponent#getHeight()
	 */
	@Override
	public int getHeight() {
		return font.getHeight(text);
	}

	/* (non-Javadoc)
	 * @see com.sunderance.weeaboo.components.Component#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// Do nothing
	}
}
