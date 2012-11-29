package com.sunderance.break_the_blocks.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.StringRenderComponent;
import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.slick_utils.Rect;

/**
 * Game over state
 * 
 * @author Robert Berry
 */
public class GameOverState extends EntityBasedState {
	private static final String GAME_OVER_TEXT = "Game over!";
	
	private GameState gameState;
	int finalScore;
	boolean isHighScore;

	public GameOverState(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		BreakTheBlocksResources resources = 
				BreakTheBlocksResources.getInstance();		
		
		Rect gcRect = Rect.fromGameContainer(gc);
		
		gameState = game.getState(BreakTheBlocks.State.IN_GAME.ordinal());
		
		StringRenderComponent renderGameOver = new StringRenderComponent(
				resources.getFont("header"), GAME_OVER_TEXT);
		ComponentBasedEntity gameOverText = 
				new ComponentBasedEntity(renderGameOver);
		gameOverText.setPosition(gcRect.getCentre());
		
		addEntity(gameOverText);
	}
	
	public void enter(GameContainer gc, StateBasedGame game) {
		BreakTheBlocksResources resources = 
				BreakTheBlocksResources.getInstance();		
		
		finalScore = ((InGame) gameState).getScore();
		isHighScore = resources.isHighScore(finalScore);		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) throws SlickException {
		gameState.render(gc, game, graphics);
		super.render(gc, game, graphics);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		super.update(gc, game, delta);
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			((InGame) gameState).reset();
			game.enterState(BreakTheBlocks.State.MENU.ordinal());
		}
	}
}
