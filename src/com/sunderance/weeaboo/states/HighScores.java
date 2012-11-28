package com.sunderance.weeaboo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Rect;
import com.sunderance.weeaboo.Weeaboo;
import com.sunderance.weeaboo.WeeabooResources;
import com.sunderance.weeaboo.Weeaboo.State;
import com.sunderance.weeaboo.components.StringRenderComponent;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

public class HighScores extends EntityBasedState {
	private static final int HEADER_TOP_MARGIN = 50;
	private static final String TITLE = "High Scores";
	private static final String PRESS_KEY_TEXT = 
			"Press enter to return to menu";
	
	public HighScores(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		WeeabooResources resources = WeeabooResources.getInstance();
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
	public void update(GameContainer gc, StateBasedGame game, int delta) 
			throws SlickException {
		super.update(gc, game, delta);
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_RETURN)) {
			game.enterState(Weeaboo.State.MENU.ordinal());
		}
	}
}
