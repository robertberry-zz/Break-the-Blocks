package com.sunderance.break_the_blocks.states;

import java.util.Set;
import java.util.TreeSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.Lists;
import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.EnterTextRenderComponent;
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
	
	private static final String HIGH_SCORE_TEXT = "High score!";

	private static final int MAX_NAME_SIZE = 11;
	
	private GameState gameState;
	private int finalScore;
	private boolean isHighScore;
	
	private String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + 
			"abcdefghijklmnopqrstuvwxyz0123456789 ";

	private EnterTextRenderComponent renderEnterScore;

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
		gameOverText.setPosition(gcRect.getCentre().addY(
				-gameOverText.getHeight() * 1.5f ));
		addEntity(gameOverText);
		
		StringRenderComponent highScoreRender = new StringRenderComponent(
				resources.getFont("press_key"), HIGH_SCORE_TEXT);
		ComponentBasedEntity highScoreText =
				new ComponentBasedEntity(highScoreRender);
		highScoreText.setPosition(gcRect.getCentre());
		addEntity(highScoreText);
		
		Set<Character> allowedChars = 
				new TreeSet<Character>(Lists.charactersOf(ALLOWED_CHARS));
		renderEnterScore = new EnterTextRenderComponent(MAX_NAME_SIZE, "Anon", 
				resources.getFont("press_key"), allowedChars);
		ComponentBasedEntity enterScore = new ComponentBasedEntity(
				renderEnterScore);
		enterScore.setPosition(highScoreText.getPosition()
				.addY(highScoreText.getHeight()));
		addEntity(enterScore);
	}
	
	public void enter(GameContainer gc, StateBasedGame game) {
		BreakTheBlocksResources resources = 
				BreakTheBlocksResources.getInstance();		
		
		finalScore = ((InGame) gameState).getScore();
		isHighScore = resources.isHighScore(finalScore);
		
		// otherwise if you press enter on a previous screen that does not
		// capture the event, immediately skips to menu
		Input input = gc.getInput();
		input.clearKeyPressedRecord();
		
		input.addKeyListener(renderEnterScore);
	}
	
	public void exit(GameContainer gc, StateBasedGame game) {
		gc.getInput().removeKeyListener(renderEnterScore);
		renderEnterScore.reset();
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
			if (isHighScore) {
				int score = ((InGame) game.getState(
						BreakTheBlocks.State.IN_GAME.ordinal())).getScore();
				String name = renderEnterScore.getText();
				
				BreakTheBlocksResources resources = 
						BreakTheBlocksResources.getInstance();
				resources.addHighScore(name, score);
			}
			
			((InGame) gameState).reset();
			
			if (isHighScore) {
				game.enterState(BreakTheBlocks.State.HIGH_SCORES.ordinal());
			} else {
				game.enterState(BreakTheBlocks.State.MENU.ordinal());
			}
		}
	}
}
