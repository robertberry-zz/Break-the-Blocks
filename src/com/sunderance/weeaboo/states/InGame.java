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
import com.sunderance.slick_utils.MechanicsUtilities;
import com.sunderance.slick_utils.Vector2f;
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
	
	ComponentBasedEntity paddle;
	private ComponentBasedEntity ball;
	private List<ComponentBasedEntity> blocks;
	
	public InGame(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {

		GeometryUtilities geoUtils = GeometryUtilities.getInstance();
		EntityFactory entityFactory = EntityFactory.getInstance();
		
		paddle = entityFactory.createPaddle();
		paddle.setPosition(geoUtils.getBottomCentre(gc, paddle));
		addEntity(paddle);
		
		ball = entityFactory.createBall();
		ball.setPosition(geoUtils.getMiddleCentre(gc, ball));
		addEntity(ball);
		
		BlockFactory blockFactory = BlockFactory.getInstance();
		
		Builder builder = new Builder();
		try {
			Document document = builder.build("res/xml/level1.xml");
			blocks = blockFactory.createBlocks(document);
			for (ComponentBasedEntity block : blocks) {
				addEntity(block);
			}
		} catch (ValidityException e) {
			// TODO Clean up this mess
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	public void update(GameContainer gc, StateBasedGame game,
			int delta) throws SlickException {
		super.update(gc, game, delta);
		
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f newPaddlePosition = mechanicsUtils.getNextPosition(paddle, 
				delta);
		
		if (newPaddlePosition.getX() < 0) {
			paddle.setPosition(newPaddlePosition.withX(0));
			paddle.setVelocity(Vector2f.zero());
			paddle.setAcceleration(Vector2f.zero());
		} else if (newPaddlePosition.getX() + 
				paddle.getWidth() > gc.getWidth()) {
			paddle.setAcceleration(newPaddlePosition.withX(gc.getWidth() 
					- paddle.getWidth()));
			paddle.setVelocity(Vector2f.zero());
			paddle.setAcceleration(Vector2f.zero());
		} else {
			paddle.setPosition(newPaddlePosition);
		}
	}
}
