package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

abstract public class RenderComponent extends UpdateComponent {
	abstract public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics);
	
	abstract public int getWidth();
	
	abstract public int getHeight();
}
