package com.sunderance.weeaboo.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.ImmutableList;
import com.sunderance.slick_utils.Rect;
import com.sunderance.weeaboo.Weeaboo;
import com.sunderance.weeaboo.WeeabooResources;
import com.sunderance.weeaboo.Weeaboo.State;
import com.sunderance.weeaboo.components.EnterStateMenuItem;
import com.sunderance.weeaboo.components.MenuItem;
import com.sunderance.weeaboo.components.MenuRenderComponent;
import com.sunderance.weeaboo.components.QuitMenuItem;
import com.sunderance.weeaboo.components.StringRenderComponent;
import com.sunderance.weeaboo.entities.ComponentBasedEntity;

public class Menu extends EntityBasedState {

	public Menu(State stateID) {
		super(stateID);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		WeeabooResources resources = WeeabooResources.getInstance();
		
		Rect gcRect = Rect.fromGameContainer(gc);
		
		ComponentBasedEntity title = new ComponentBasedEntity(
				new StringRenderComponent(resources.getFont("header"),
				game.getTitle()));
		title.setPosition(gcRect.getCentre().withY(50));
		
		addEntity(title);
		
		MenuItem startGame = new EnterStateMenuItem("New game", game, 
				Weeaboo.State.IN_GAME);
		MenuItem highScores = new EnterStateMenuItem("High scores", game,
				Weeaboo.State.HIGH_SCORES);
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
