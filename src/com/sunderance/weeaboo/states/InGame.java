/**
 * 
 */
package com.sunderance.weeaboo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.GeometryUtilities;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;
import com.sunderance.weeaboo.entities.EntityFactory;
import com.sunderance.weeaboo.Weeaboo.State;

/**
 * In game state.
 * 
 * @author Robert Berry
 */
public class InGame extends EntityBasedState {
	
	public InGame(State stateID) {
		super(stateID);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {

		GeometryUtilities geoUtils = GeometryUtilities.getInstance();
		EntityFactory entityFactory = EntityFactory.getInstance();
		
		ComponentBasedEntity paddle = entityFactory.createPaddle();
		paddle.setPosition(geoUtils.getBottomCentre(gc, paddle));
		addEntity(paddle);
		
		ComponentBasedEntity ball = entityFactory.createBall();
		ball.setPosition(geoUtils.getMiddleCentre(gc, ball));
		addEntity(ball);
	}

}
