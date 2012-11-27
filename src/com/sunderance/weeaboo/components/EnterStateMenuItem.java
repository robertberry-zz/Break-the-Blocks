package com.sunderance.weeaboo.components;

import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.weeaboo.Weeaboo;
import com.sunderance.weeaboo.Weeaboo.State;

public class EnterStateMenuItem implements MenuItem {
	String text;
	StateBasedGame game;
	Weeaboo.State state;
	
	public EnterStateMenuItem(String text, StateBasedGame game, State state) {
		super();
		this.text = text;
		this.game = game;
		this.state = state;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void trigger() {
		game.enterState(state.ordinal());
	}
}
