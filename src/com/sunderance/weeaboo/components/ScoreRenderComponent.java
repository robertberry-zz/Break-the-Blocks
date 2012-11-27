package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;

public class ScoreRenderComponent extends RenderComponent {
	UnicodeFont font;
	Scoring scoring;
	int length;
	String format;
	
	public ScoreRenderComponent(UnicodeFont font, Scoring scoring, int length) {
		super();
		this.font = font;
		this.scoring = scoring;
		this.length = length;
		this.format = "%0" + Integer.toString(length) + "d";
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		Vector2f position = getOwner().getRect().getTopLeft();
		
		font.drawString(position.getX(), position.getY(), getScoreString());
	}

	private String getScoreString() {
		return String.format(format, scoring.getScore());
	}
	
	@Override
	public int getWidth() {
		return font.getWidth(getScoreString());
	}

	@Override
	public int getHeight() {
		return font.getHeight(getScoreString());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// do nothing
	}
}
