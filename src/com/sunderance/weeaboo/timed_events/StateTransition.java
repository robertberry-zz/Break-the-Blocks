package com.sunderance.weeaboo.timed_events;

import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.TimedEvent;
import com.sunderance.weeaboo.Weeaboo;

/**
 * Timed event to move the game into a different state
 * 
 * @author Robert Berry
 */
public class StateTransition implements TimedEvent {
	Weeaboo.State stateID;
	StateBasedGame game;

	public StateTransition(StateBasedGame game, Weeaboo.State stateID) {
		this.game = game;
		this.stateID = stateID;
	}
	
	@Override
	public void trigger() {
		game.enterState(stateID.ordinal());
	}
}
