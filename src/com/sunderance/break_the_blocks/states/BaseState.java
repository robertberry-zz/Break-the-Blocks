/**
 * BaseState class.
 */
package com.sunderance.break_the_blocks.states;

import org.newdawn.slick.state.BasicGameState;

import com.sunderance.break_the_blocks.BreakTheBlocks.State;

/**
 * Abstract base for Weeaboo game states.
 * 
 * @author Robert Berry
 */
abstract public class BaseState extends BasicGameState {
	private int stateID;

	public BaseState(State stateID) {
		this.stateID = stateID.ordinal();
	}
	
	/**
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return stateID;
	}
}
