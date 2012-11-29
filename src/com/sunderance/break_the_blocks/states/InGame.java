/**
 * 
 */
package com.sunderance.break_the_blocks.states;

import java.util.List;
import java.io.IOException;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.base.Optional;
import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.HasLives;
import com.sunderance.break_the_blocks.components.LivesRenderComponent;
import com.sunderance.break_the_blocks.components.ScoreRenderComponent;
import com.sunderance.break_the_blocks.components.Scoring;
import com.sunderance.break_the_blocks.entities.BlockFactory;
import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.break_the_blocks.entities.EntityFactory;
import com.sunderance.break_the_blocks.timed_events.MessageListener;
import com.sunderance.break_the_blocks.timed_events.SendMessage;
import com.sunderance.slick_utils.GeometryUtilities;
import com.sunderance.slick_utils.Intercept;
import com.sunderance.slick_utils.MechanicsUtilities;
import com.sunderance.slick_utils.Rect;
import com.sunderance.slick_utils.Side;
import com.sunderance.slick_utils.TimedEvent;
import com.sunderance.slick_utils.Vector2f;

/**
 * In-game state.
 * 
 * @author Robert Berry
 */
public class InGame extends EntityBasedState implements Scoring, HasLives,
		MessageListener<InGame.Messages> {
	private ComponentBasedEntity paddle;
	private ComponentBasedEntity ball;
	private List<ComponentBasedEntity> blocks;
	
	private static final float BALL_INITIAL_SPEED = 0.4f;
	private static final int SCORE_LENGTH = 8;
	private static final int INITIAL_LIVES = 3;
	private static final int MAX_LIVES = 3;
	private static final int BALL_RELEASE_DELAY = 2000;
	private static final int NEW_BALL_DELAY = 200;
	
	private boolean showCollisionMarker = false;
	
	private GeometryUtilities geoUtils;
	
	private Vector2f collisionPoint;
	
	private int score = 0;
	private int lives = INITIAL_LIVES;
	private boolean ballReleased = false;
	
	private GameContainer gc;
	private StateBasedGame game;
	
	public enum Messages {
		NEW_BALL,
		RELEASE_BALL;
		
		public TimedEvent toEvent(InGame state) {
			return new SendMessage<InGame.Messages>(state, this);
		}
	}
	
	public InGame(State stateID) {
		super(stateID);
		geoUtils = GeometryUtilities.getInstance();
	}

	/**
	 * Places a new paddle into play
	 * 
	 * @param gc The game container
	 */
	private void newPaddle() {
		EntityFactory entityFactory = EntityFactory.getInstance();
		Rect gcRect = Rect.fromGameContainer(gc);
		
		if (paddle != null)
			removeEntity(paddle);
		
		paddle = entityFactory.createPaddle();
		paddle.setPosition(gcRect.getBottomCentre().subtract(new Vector2f(
				0, paddle.getHeight() * 1.5f)));
		addEntity(paddle);		
	}
	
	/**
	 * Places a new ball into play
	 * 
	 * @param gc The game container
	 */
	private void newBall() {
		EntityFactory entityFactory = EntityFactory.getInstance();
		
		if (ball != null)
			removeEntity(ball);
		
		ballReleased = false;
		
		ball = entityFactory.createBall();
		ball.setPosition(Rect.fromGameContainer(gc).getCentre());
		
		addTimedEvent(BALL_RELEASE_DELAY, Messages.RELEASE_BALL.toEvent(this));
		
		addEntity(ball);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		this.gc = gc;
		this.game = game;
		newBall();
		newPaddle();
		
		BreakTheBlocksResources resources = BreakTheBlocksResources.getInstance();
		Rect gcRect = Rect.fromGameContainer(gc);
		
		ScoreRenderComponent scoreRender = new ScoreRenderComponent(
				resources.getFont("score"), this, SCORE_LENGTH);
		ComponentBasedEntity scoreView = new ComponentBasedEntity(scoreRender);
		scoreView.setPosition(gcRect.getBottomRight()
				.subtract(scoreView.getDimensions().scale(0.5f))
				.subtract(new Vector2f(5, 5)));
		addEntity(scoreView);
		
		LivesRenderComponent livesRender = new LivesRenderComponent(this,
			resources.getImage("life"), resources.getImage("spent_life"),
			MAX_LIVES, 5);
		ComponentBasedEntity livesView = new ComponentBasedEntity(livesRender);
		livesView.setPosition(gcRect.getBottomLeft()
				.addX(livesView.getWidth() / 2)
				.addY(-livesView.getHeight() / 2)
				.add(new Vector2f(5, -5)));
		addEntity(livesView);
		
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
	
	/**
	 * Updates the position of the paddle
	 * 
	 * @param gc The game container
	 * @param game The game
	 * @param delta The amount of time passed in ms
	 */
	public void updatePaddle(GameContainer gc, 
			StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f newPaddlePosition = mechanicsUtils.getNextPosition(paddle, 
				delta);
		Vector2f newPaddleVelocity = mechanicsUtils.getNextVelocity(paddle, 
				delta);
		Rect newRect = paddle.getRect().withCentre(newPaddlePosition);
		
		if (newRect.getLeft() < 0) {
			newRect = newRect.withLeft(0);
			paddle.stop();
		} else if (newRect.getRight() > gc.getWidth()) {
			newRect = newRect.withRight(gc.getWidth());
			paddle.stop();
		} else {
			paddle.setVelocity(newPaddleVelocity);
		}
		paddle.setPosition(newRect.getCentre());
	}
	
	/**
	 * Bounces the ball from the given intercept. (i.e. places the ball at the
	 * point of intercept and adjusts the velocity appropriately.)
	 * 
	 * @param i The intercept
	 */
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
	
	/**
	 * Calculates at what point within the delta the intercept occurs (i.e.
	 * exactly when the ball collides with the object).
	 * 
	 * @param initial The initial position
	 * @param end The position that the ball would have moved to in the time
	 *   delta had it not collided with something
	 * @param intercept The point at which the ball collided with something
	 * @param delta The amount of time passed in ms
	 * @return The amount of time passed in ms when the ball collided
	 */
	int calculateInterceptDelta(Vector2f initial, Vector2f end, 
			Vector2f intercept, int delta) {
		float magIntercept = geoUtils.magnitude(initial, intercept);
		float magFull = geoUtils.magnitude(initial, end);
		
		return Math.round(delta * magIntercept / magFull);
	}
	
	/**
	 * Detects for collisions between the ball and the paddle
	 * 
	 * @param gc The game container
	 * @param game The game
	 * @param delta Amount of time passed in ms
	 */
	public void updateBall2(GameContainer gc, StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);
		Vector2f velocity = ball.getVelocity();

		Optional<Intercept> intercept = 
				ballIntercepts(pos1, pos2, paddle.getRect());
		
		if (intercept.isPresent()) {
			Intercept i = intercept.get();
			bounceBall(i);
			
			Vector2f paddleVelocity = paddle.getVelocity();
			velocity = ball.getVelocity();
			
			// add spin based on paddle velocity
			if (paddleVelocity.isRightward()) {
				ball.setVelocity(velocity.scaleX(velocity.isRightward() ? 0.5f
						: 1.5f));
			} else if (paddleVelocity.isLeftward()) {
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
	
	/**
	 * Detects for collisions between the ball and the blocks
	 * 
	 * @param gc The game container
	 * @param game The game
	 * @param delta The amount of time passed in ms
	 */
	public void updateBall3(GameContainer gc, StateBasedGame game, int delta) {
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);

		Optional<Intercept> closestIntercept = Optional.absent();
		Optional<ComponentBasedEntity> blockToKill = Optional.absent();
		
		for (ComponentBasedEntity block : blocks) {
			Optional<Intercept> intercept = 
					ballIntercepts(pos1, pos2, block.getRect());
			
			if (intercept.isPresent()) {
				Intercept i = intercept.get();
				
				if (!closestIntercept.isPresent() ||
						geoUtils.magnitude(pos1, i.getPosition()) < 
						geoUtils.magnitude(pos1, 
								closestIntercept.get().getPosition())) {
					closestIntercept = Optional.of(i);
					blockToKill = Optional.of(block);
				}
			}
		}
		
		if (closestIntercept.isPresent()) {
			bounceBall(closestIntercept.get());
			removeBlock(blockToKill.get());
			
			updateBall(gc, game, delta - calculateInterceptDelta(pos1, pos2, 
					closestIntercept.get().getPosition(), delta));
		} else {
			ball.setPosition(pos2);
		}
	}
	
	/**
	 * Removes block from play
	 * 
	 * @param block The block
	 */
	private void removeBlock(ComponentBasedEntity block) {
		removeEntity(block);
		blocks.remove(block);
		score += 100;
	}
	
	/**
	 * Detects for collisions between the ball and the side of the play area
	 * 
	 * @param gc The game container
	 * @param game The game
	 * @param delta The amount of time passed in ms
	 */
	public void updateBall(GameContainer gc, StateBasedGame game, int delta) {
		if (delta <= 0) return;
		
		MechanicsUtilities mechanicsUtils = MechanicsUtilities.getInstance();
		
		Vector2f pos1 = ball.getPosition();
		Vector2f pos2 = mechanicsUtils.getNextPosition(ball, delta);

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
				if (intercept.isPresent()) {
					ball.setVelocity(velocity.withY(-velocity.getY()));
				}
			} else if (velocity.isDownward()) {
				intercept = geoUtils.intercepts(pos1, pos2, 
						area.getBottomLeft(), area.getBottomRight());
				
				if (intercept.isPresent()) {
					ball.setPosition(pos2);
					addTimedEvent(NEW_BALL_DELAY, new SendMessage<Messages>(
							this, Messages.NEW_BALL));
					return;
				}
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
	
	/**
	 * Subtracts a life
	 */
	private void loseLife() {
		lives -= 1;
		
		if (lives == 0) {
			gameOver();
		}
	}
	
	/**
	 * Triggers game over
	 */
	private void gameOver() {
		game.enterState(BreakTheBlocks.State.GAME_OVER.ordinal());
	}
	
	/**
	 * Whether the path of the ball from pos1 to pos2 intercepts the given
	 * rectangle
	 * 
	 * @param pos1 Initial position
	 * @param pos2 End position
	 * @param rect The rectangle
	 * @return An optional intercept
	 */
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
		
		if (ballReleased) {
			updateBall(gc, game, delta);
		} else {
			ball.setPosition(paddle.getPosition().addY(-(ball.getHeight()
					+ paddle.getHeight()) / 2));
		}
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			releaseBall();
		} else if (input.isKeyPressed(Input.KEY_P)) {
			game.enterState(BreakTheBlocks.State.PAUSED.ordinal());
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) throws SlickException {
		super.render(gc, game, graphics);
		
		if (showCollisionMarker && collisionPoint != null)
			graphics.fillOval(collisionPoint.getX(), 
					collisionPoint.getY(), 5, 5);
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public int getLives() {
		return lives;
	}
	
	public void releaseBall() {
		if (!ballReleased) {
			ballReleased = true;
			ball.setVelocity(new Vector2f(0.2f, -BALL_INITIAL_SPEED));
		}		
	}

	@Override
	public void tell(Messages message) {
		switch (message) {
		case RELEASE_BALL:
			releaseBall();
			break;
		case NEW_BALL:
			newBall();
			newPaddle();
			loseLife();
			break;
		default:
			break;
		}
	}

	public void reset() {
		score = 0;
		lives = INITIAL_LIVES;
	}
}
