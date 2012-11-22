/**
 * 
 */
package com.sunderance.weeaboo.states;

import java.util.List;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.GeometryUtilities;
import com.sunderance.weeaboo.entities.BlockFactory;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;
import com.sunderance.weeaboo.entities.EntityFactory;
import com.sunderance.weeaboo.Weeaboo.State;

/**
 * In-game state.
 * 
 * @author Robert Berry
 */
public class InGame extends EntityBasedState {
	public InGame(State stateID) {
		super(stateID);
	}

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
		
		BlockFactory blockFactory = BlockFactory.getInstance();
		
		Builder builder = new Builder();
		try {
			Document document = builder.build("res/xml/level1.xml");
			List<ComponentBasedEntity> blocks = 
					blockFactory.createBlocks(document);
			for (ComponentBasedEntity block : blocks) {
				addEntity(block);
			}
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
