package com.sunderance.break_the_blocks.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.ScoreTableRenderComponent;
import com.sunderance.break_the_blocks.components.StringRenderComponent;
import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.slick_utils.Rect;
import com.sunderance.slick_utils.ScoreTable;

public class HighScores extends EntityBasedState {
	private static final int HEADER_TOP_MARGIN = 50;
	private static final String TITLE = "High Scores";
	private static final String PRESS_KEY_TEXT = 
			"Press enter to return to menu";
	
	private ComponentBasedEntity scoreTable;
	
	public HighScores(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		BreakTheBlocksResources resources = BreakTheBlocksResources.getInstance();
		Rect gcRect = Rect.fromGameContainer(gc);
		
		StringRenderComponent renderHeader = new StringRenderComponent(
				resources.getFont("header"), TITLE);
		ComponentBasedEntity header = new ComponentBasedEntity(renderHeader);
		header.setPosition(gcRect.getCentre().withY(HEADER_TOP_MARGIN));
		addEntity(header);
		
		StringRenderComponent renderPressKey = new StringRenderComponent(
				resources.getFont("press_key"), PRESS_KEY_TEXT);
		ComponentBasedEntity pressKey = 
				new ComponentBasedEntity(renderPressKey);
		pressKey.setPosition(gcRect.getBottomCentre()
				.addY(-pressKey.getHeight() * 2));
		addEntity(pressKey);
		
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) {
		if (scoreTable != null)
			removeEntity(scoreTable);
		
		BreakTheBlocksResources resources = BreakTheBlocksResources.getInstance();
		ScoreTable scores = resources.getHighScores();
		
		ScoreTableRenderComponent renderScores = new ScoreTableRenderComponent(
				resources.getFont("high_scores"), scores, 10);
		scoreTable = new ComponentBasedEntity(
				renderScores);
		scoreTable.setPosition(Rect.fromGameContainer(gc).getCentre());
		addEntity(scoreTable);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) 
			throws SlickException {
		super.update(gc, game, delta);
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RETURN)) {
			game.enterState(BreakTheBlocks.State.MENU.ordinal());
		}
	}
}
