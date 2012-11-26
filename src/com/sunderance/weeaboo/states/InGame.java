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

import com.google.common.base.Optional;
import com.sunderance.slick_utils.GeometryUtilities;
import com.sunderance.slick_utils.Intercept;
import com.sunderance.slick_utils.MechanicsUtilities;
import com.sunderance.slick_utils.Rect;
import com.sunderance.slick_utils.Side;
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
	
	private static float BALL_INITIAL_SPEED = 0.3f;
	private GeometryUtilities geoUtils;
	
	public InGame(State stateID) {
		super(stateID);
		geoUtils = GeometryUtilities.getInstance();
	}

	private void newPaddle(GameContainer gc) {
		EntityFactory entityFactory = EntityFactory.getInstance();		
		paddle = entityFactory.createPaddle();
		paddle.setPosition(geoUtils.getBottomCentre(gc, paddle));
		addEntity(paddle);		
	}
	
	private void newBall(GameContainer gc) {
		EntityFactory entityFactory = EntityFactory.getInstance();
		ball = entityFactory.createBall();
		ball.setPosition(geoUtils.getMiddleCentre(gc, ball));
		ball.setVelocity(new Vector2f(0, BALL_INITIAL_SPEED));
		addEntity(ball);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		newBall(gc);
		newPaddle(gc);
		
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
	
	public void updatePaddle(GameContainer gc, 
			StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f newPaddlePosition = mechanicsUtils.getNextPosition(paddle, 
				delta);
		
		if (newPaddlePosition.getX() < 0) {
			newPaddlePosition = newPaddlePosition.withX(0);
			paddle.stop();
		} else if (newPaddlePosition.getX() + 
				paddle.getWidth() > gc.getWidth()) {
			newPaddlePosition = newPaddlePosition.withX(gc.getWidth() 
					- paddle.getWidth());
			paddle.stop();
		}
		paddle.setPosition(newPaddlePosition);
	}
	
	public void updateBall(GameContainer gc, StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);
		
		// paddle
		
		Optional<Intercept> intercept = 
				ballIntercepts(pos1, pos2, paddle.getRect());
		
		if (intercept.isPresent()) {
			System.out.println(paddle.getRect());
			Intercept i = intercept.get();
			Vector2f posIntercept = i.getPosition();
			
			// time at which intercept occurs
			float ud = delta * geoUtils.magnitude(pos1, posIntercept)
					/ geoUtils.magnitude(pos1, pos2);
			
			Vector2f velocity = ball.getVelocity();
			
			switch (i.getSide()) {
				case LEFT:
				case RIGHT:
					ball.setVelocity(velocity.withX(-velocity.getX()));
					break;
				case TOP:
				case BOTTOM:
					ball.setVelocity(velocity.withY(-velocity.getY()));
					break;
			}
			
			updateBall(gc, game, delta - Math.round(ud));
		} else {
			ball.setPosition(pos2);
		}
	}
	
	public Optional<Intercept> ballIntercepts(Vector2f pos1, Vector2f pos2, 
			Rect rect) {	
		float nx = pos2.getX() - pos1.getX(),
				ny = pos2.getY() - pos1.getY();
		
		Optional<Vector2f> interceptsAt = Optional.absent();
		Side side = null;
		
		rect = rect.withMargin(ball.getHeight() / 2);
		
		if (nx < 0) {
			interceptsAt = geoUtils.intercepts(pos1, pos2, 
					rect.getTopRight(), rect.getBottomRight());
			side = Side.RIGHT;
		} else if (nx > 0) {
			interceptsAt = geoUtils.intercepts(pos1, pos2, 
					rect.getTopLeft(), rect.getBottomLeft());
			side = Side.LEFT;
		}
		
		if (!interceptsAt.isPresent()) {
			if (ny < 0) {
				interceptsAt = geoUtils.intercepts(pos1, pos2, 
						rect.getBottomLeft(), rect.getBottomRight());
				side = Side.BOTTOM;
			} else if (ny > 0) {
				interceptsAt = geoUtils.intercepts(pos1, pos2, 
						rect.getTopLeft(), rect.getTopRight());
				side = Side.TOP;
			}
		}
		
		if (interceptsAt.isPresent()) {
			return Optional.of(new Intercept(side, interceptsAt.get()));
		} else {
			return Optional.absent();
		}
	}
	
	@Override 
	public void update(GameContainer gc, StateBasedGame game,
			int delta) throws SlickException {
		super.update(gc, game, delta);

		updatePaddle(gc, game, delta);
		updateBall(gc, game, delta);
	}
}
