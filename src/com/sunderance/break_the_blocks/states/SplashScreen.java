/**
 * 
 */
package com.sunderance.break_the_blocks.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.StringRenderComponent;
import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.break_the_blocks.timed_events.StateTransition;
import com.sunderance.slick_utils.Rect;

/**
 * Initial splash screen
 * 
 * @author Robert Berry
 */
public class SplashScreen extends EntityBasedState {
	private static final int DELAY = 1000;

	private static final String TEXT = "Robert Berry (c)";
	
	public SplashScreen(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		BreakTheBlocksResources resources = BreakTheBlocksResources.getInstance();
		
		StringRenderComponent renderTitle = new StringRenderComponent(
				resources.getFont("splash"), TEXT);
		
		ComponentBasedEntity title = new ComponentBasedEntity(renderTitle);
		title.setPosition(Rect.fromGameContainer(gc).getCentre());
		
		addEntity(title);
		
		addTimedEvent(DELAY, new StateTransition(game, BreakTheBlocks.State.MENU));
	}
}
