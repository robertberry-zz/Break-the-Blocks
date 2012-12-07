package com.sunderance.break_the_blocks.components;

import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.base.Strings;
import com.sunderance.slick_utils.Vector2f;

public class EnterTextRenderComponent extends RenderComponent 
	implements KeyListener {
	private int maxChars;
	private String initialText;
	private String text;
	private UnicodeFont font;
	private boolean isBlinking = false;
	private static final int BLINKS_FOR_MS = 300;
	private int timeElapsed = 0;
	private Set<Character> allowedChars;
	
	public EnterTextRenderComponent(int maxChars, String initialText, 
			UnicodeFont font, Set<Character> allowedChars) {
		super();
		this.maxChars = maxChars;
		this.initialText = initialText;
		this.text = initialText;
		this.font = font;
		this.allowedChars = allowedChars;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		String text = this.text;
		
		if (isBlinking && text.length() < maxChars) {
			text += "_";
		}
		
		Vector2f position = getOwner().getRect().getTopLeft();
		
		font.drawString(position.getX(), position.getY(), text);
	}

	@Override
	public int getWidth() {
		return font.getWidth(Strings.repeat("*", maxChars));
	}

	@Override
	public int getHeight() {
		return font.getHeight("*");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		timeElapsed += delta;
		
		isBlinking = ((timeElapsed / BLINKS_FOR_MS) % 2 == 0);
	}

	@Override
	public void inputEnded() {
		// do nothing
	}

	@Override
	public void inputStarted() {
		// do nothing
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {
		// do nothing
	}

	@Override
	public void keyPressed(int key, char ch) {
		if (key == Input.KEY_BACK) {
			if (text.length() > 0) {
				text = text.substring(0, text.length() - 1);
			}
		} else if (allowedChars.contains(ch) && text.length() < maxChars) {
			text += ch;
		}
	}

	@Override
	public void keyReleased(int key, char ch) {
		// do nothing
	}

	public String getText() {
		return text;
	}
	
	public void reset() {
		this.text = this.initialText;
	}
}
