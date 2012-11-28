package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;

public class EnterStateMenuItem implements MenuItem {
	String text;
	StateBasedGame game;
	BreakTheBlocks.State state;
	
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
