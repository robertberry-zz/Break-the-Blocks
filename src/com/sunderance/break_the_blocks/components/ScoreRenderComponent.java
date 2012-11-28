package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;

public class ScoreRenderComponent extends RenderComponent {
	private UnicodeFont font;
	private Scoring scoring;
	private String format;
	private int score;
	private float incrementPerMs = 0.5f;
	
	public ScoreRenderComponent(UnicodeFont font, Scoring scoring, int length) {
		super();
		this.font = font;
		this.scoring = scoring;
		this.format = "%0" + Integer.toString(length) + "d";
		score = scoring.getScore();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		Vector2f position = getOwner().getRect().getTopLeft();
		
		font.drawString(position.getX(), position.getY(), getScoreString());
	}

	private String getScoreString() {
		return String.format(format, score);
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
		int properScore = scoring.getScore();
		
		if (score < properScore) {
			score = (int) Math.min(properScore, 
					score + delta * incrementPerMs);
		}
	}
}
