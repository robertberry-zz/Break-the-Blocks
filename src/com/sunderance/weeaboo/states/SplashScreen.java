/**
 * 
 */
package com.sunderance.weeaboo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.GeometryUtilities;
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

	public SplashScreen(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		GeometryUtilities geoUtils = GeometryUtilities.getInstance();
		WeeabooResources resources = WeeabooResources.getInstance();
		
		StringRenderComponent renderTitle = new StringRenderComponent(
				resources.getFont("header"), game.getTitle());
		
		ComponentBasedEntity title = new ComponentBasedEntity(renderTitle);
		title.setPosition(geoUtils.getMiddleCentre(gc, title));
		
		addEntity(title);
		
		addTimedEvent(1000, new StateTransition(game, Weeaboo.State.IN_GAME));
	}
}