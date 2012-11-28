package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;

public class QuitMenuItem implements MenuItem {
	String text;
	GameContainer gc;
	
	public QuitMenuItem(String text, GameContainer gc) {
		super();
		this.text = text;
		this.gc = gc;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void trigger() {
		gc.exit();
	}

}
