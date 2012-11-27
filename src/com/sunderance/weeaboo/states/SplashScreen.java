/**
 * 
 */
package com.sunderance.weeaboo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Rect;
import com.sunderance.weeaboo.Weeaboo;
import com.sunderance.weeaboo.WeeabooResources;
import com.sunderance.weeaboo.Weeaboo.State;
import com.sunderance.weeaboo.components.StringRenderComponent;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;
import com.sunderance.weeaboo.timed_events.StateTransition;

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
		WeeabooResources resources = WeeabooResources.getInstance();
		
		StringRenderComponent renderTitle = new StringRenderComponent(
				resources.getFont("splash"), TEXT);
		
		ComponentBasedEntity title = new ComponentBasedEntity(renderTitle);
		title.setPosition(Rect.fromGameContainer(gc).getCentre());
		
		addEntity(title);
		
		addTimedEvent(DELAY, new StateTransition(game, Weeaboo.State.MENU));
	}
}
