package com.sunderance.break_the_blocks.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.ImmutableList;
import com.sunderance.break_the_blocks.BreakTheBlocks;
import com.sunderance.break_the_blocks.BreakTheBlocksResources;
import com.sunderance.break_the_blocks.BreakTheBlocks.State;
import com.sunderance.break_the_blocks.components.EnterStateMenuItem;
import com.sunderance.break_the_blocks.components.MenuItem;
import com.sunderance.break_the_blocks.components.MenuRenderComponent;
import com.sunderance.break_the_blocks.components.QuitMenuItem;
import com.sunderance.break_the_blocks.components.StringRenderComponent;
import com.sunderance.break_the_blocks.entities.ComponentBasedEntity;
import com.sunderance.slick_utils.Rect;

public class Menu extends EntityBasedState {

	public Menu(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		BreakTheBlocksResources resources = BreakTheBlocksResources.getInstance();
		
		Rect gcRect = Rect.fromGameContainer(gc);
		
		ComponentBasedEntity title = new ComponentBasedEntity(
				new StringRenderComponent(resources.getFont("header"),
				game.getTitle()));
		title.setPosition(gcRect.getCentre().withY(50));
		
		addEntity(title);
		
		MenuItem startGame = new EnterStateMenuItem("New game", game, 
				BreakTheBlocks.State.IN_GAME);
		MenuItem highScores = new EnterStateMenuItem("High scores", game,
				BreakTheBlocks.State.HIGH_SCORES);
		MenuItem quit = new QuitMenuItem("Quit", gc);
		
		MenuRenderComponent menuRender = new MenuRenderComponent(
				resources.getFont("menu_item"), 
				resources.getFont("menu_item_selected"),
				ImmutableList.of(startGame, highScores, quit),
				10);
		
		ComponentBasedEntity menu = new ComponentBasedEntity(menuRender);
		
		menu.setPosition(gcRect.getCentre());
		
		addEntity(menu);
	}

}
