package com.sunderance.break_the_blocks.timed_events;

import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.slick_utils.TimedEvent;

/**
 * Timed event to move the game into a different state
 * 
 * @author Robert Berry
 */
public class StateTransition implements TimedEvent {
	BreakTheBlocks.State stateID;
	StateBasedGame game;

	public StateTransition(StateBasedGame game, BreakTheBlocks.State stateID) {
		this.game = game;
		this.stateID = stateID;
	}
	
	@Override
	public void trigger() {
		game.enterState(stateID.ordinal());
	}
}
