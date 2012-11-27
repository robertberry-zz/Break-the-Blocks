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
import org.newdawn.slick.Graphics;
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
	
	private static float BALL_INITIAL_SPEED = 0.4f;
	private GeometryUtilities geoUtils;
	
	private Vector2f collisionPoint;
	
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
		ball.setVelocity(new Vector2f(0.1f, BALL_INITIAL_SPEED));
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
		Rect newRect = paddle.getRect().withCentre(newPaddlePosition);
		
		if (newRect.getLeft() < 0) {
			newRect = newRect.withLeft(0);
			paddle.stop();
		} else if (newRect.getRight() > gc.getWidth()) {
			newRect = newRect.withRight(gc.getWidth());
			paddle.stop();
		}
		paddle.setPosition(newRect.getCentre());
	}
	
	public void bounceBall(Intercept i) {
		Vector2f posIntercept = i.getPosition();
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
		
		collisionPoint = posIntercept;
		ball.setPosition(posIntercept);		
	}
	
	int calculateInterceptDelta(Vector2f initial, Vector2f end, 
			Vector2f intercept, int delta) {
		float magIntercept = geoUtils.magnitude(initial, intercept);
		float magFull = geoUtils.magnitude(initial, end);
		
		return Math.round(delta * magIntercept / magFull);
	}
	
	public void updateBall2(GameContainer gc, StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);
		Vector2f velocity = ball.getVelocity();
		
		// paddle
		
		Optional<Intercept> intercept = 
				ballIntercepts(pos1, pos2, paddle.getRect());
		
		if (intercept.isPresent()) {
			Intercept i = intercept.get();
			bounceBall(i);
			
			// add spin based on paddle velocity
			if (paddle.getVelocity().isRightward()) {
				ball.setVelocity(velocity.scaleX(velocity.isRightward() ? 0.5f
						: 1.5f));
			} else if (paddle.getVelocity().isLeftward()) {
				ball.setVelocity(velocity.scaleX(velocity.isLeftward() ? 0.5f : 
					1.5f));
			}
			
			updateBall(gc, game, delta - 
					calculateInterceptDelta(pos1, pos2, i.getPosition(), 
							delta));
		} else {
			updateBall3(gc, game, delta);
		}
	}
	
	public void updateBall3(GameContainer gc, StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);
		
		// blocks
		Optional<Intercept> intercept = Optional.absent();
		
		for (ComponentBasedEntity block : blocks) {
			intercept = ballIntercepts(pos1, pos2, block.getRect());
			
			if (intercept.isPresent()) {
				Intercept i = intercept.get();
				bounceBall(i);
				break;
			}
		}
		
		if (intercept.isPresent()) {
			updateBall(gc, game, delta - calculateInterceptDelta(pos1, pos2, 
					intercept.get().getPosition(), delta));
		} else {
			ball.setPosition(pos2);
		}
	}
	
	public void updateBall(GameContainer gc, StateBasedGame game, int delta) {
		if (delta <= 0) return;
		
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);
		
		// game area sides
		
		Rect area = Rect.fromGameContainer(gc).withoutMargin(ball.getHeight()
				/ 2);
		
		Vector2f velocity = ball.getVelocity();
		Optional<Vector2f> intercept = Optional.absent();
		
		if (velocity.isLeftward()) {
			intercept = geoUtils.intercepts(pos1, pos2, area.getTopLeft(), 
					area.getBottomLeft());
		} else if (velocity.isRightward()) {
			intercept = geoUtils.intercepts(pos1, pos2, area.getTopRight(),
					area.getBottomRight());
		}
		
		if (intercept.isPresent()) {
			ball.setVelocity(velocity.withX(-velocity.getX()));
		} else {
			if (velocity.isUpward()) {
				intercept = geoUtils.intercepts(pos1, pos2, area.getTopLeft(),
						area.getTopRight());
			} else if (velocity.isDownward()) {
				intercept = geoUtils.intercepts(pos1, pos2, 
						area.getBottomLeft(), area.getBottomRight());
			}
			
			if (intercept.isPresent()) {
				ball.setVelocity(velocity.withY(-velocity.getY()));
			}
		}
		
		if (intercept.isPresent()) {		
			Vector2f posIntercept = intercept.get();
			collisionPoint = posIntercept;
			ball.setPosition(posIntercept);
			
			updateBall(gc, game, delta - calculateInterceptDelta(pos1, pos2, 
					posIntercept, delta));
		} else {
			updateBall2(gc, game, delta);
		}
	}
	
	public Optional<Intercept> ballIntercepts(Vector2f pos1, Vector2f pos2, 
			Rect rect) {	
		Vector2f path = pos2.subtract(pos1);
		
		Optional<Vector2f> interceptsAt = Optional.absent();
		Side side = null;
		
		rect = rect.withMargin(ball.getHeight() / 2);
		
		if (path.isLeftward()) {
			interceptsAt = geoUtils.intercepts(pos1, pos2, 
					rect.getTopRight(), rect.getBottomRight());
			side = Side.RIGHT;
		} else if (path.isRightward()) {
			interceptsAt = geoUtils.intercepts(pos1, pos2, 
					rect.getTopLeft(), rect.getBottomLeft());
			side = Side.LEFT;
		}
		
		if (!interceptsAt.isPresent()) {
			if (path.isUpward()) {
				interceptsAt = geoUtils.intercepts(pos1, pos2, 
						rect.getBottomLeft(), rect.getBottomRight());
				side = Side.BOTTOM;
			} else if (path.isDownward()) {
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
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) throws SlickException {
		super.render(gc, game, graphics);
		
		if (collisionPoint != null)
			graphics.fillOval(collisionPoint.getX(), 
					collisionPoint.getY(), 5, 5);
	}
}
