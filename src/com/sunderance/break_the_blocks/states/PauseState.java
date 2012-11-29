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

public class PauseState extends EntityBasedState {
	private static final String PAUSE_TEXT = "Paused";
	private GameState gameState;
	
	public PauseState(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		gameState = game.getState(BreakTheBlocks.State.IN_GAME.ordinal());
		
		BreakTheBlocksResources resources = 
				BreakTheBlocksResources.getInstance();
		Rect gcRect = Rect.fromGameContainer(gc);
		
		StringRenderComponent renderPaused = new StringRenderComponent(
				resources.getFont("header"), PAUSE_TEXT);
		ComponentBasedEntity pausedText = 
				new ComponentBasedEntity(renderPaused);
		pausedText.setPosition(gcRect.getCentre());
		addEntity(pausedText);
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
		
		if (input.isKeyPressed(Input.KEY_P)) {
			game.enterState(BreakTheBlocks.State.IN_GAME.ordinal());
		}
	}
}
