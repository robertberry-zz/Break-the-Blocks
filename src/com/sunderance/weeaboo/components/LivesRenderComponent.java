package com.sunderance.weeaboo.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.slick_utils.Vector2f;

public class LivesRenderComponent extends RenderComponent {
	HasLives model;
	
	Image life;
	Image spentLife;
	
	int imageWidth;
	int margin;
	
	int maxLives;
	
	public LivesRenderComponent(HasLives model, Image life, Image spentLife,
			int maxLives, int margin) {
		super();
		this.model = model;
		this.life = life;
		this.spentLife = spentLife;
		this.maxLives = maxLives;
		this.margin = margin;
		
		imageWidth = life.getWidth();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		int lives = model.getLives();
		
		Vector2f position = getOwner().getRect().getTopLeft();
		
		float x = position.getX(),
				y = position.getY();
		int i = 0;
		
		while (i < maxLives) {
			if (i < lives) {
				life.draw(x, y);
			} else {
				spentLife.draw(x, y);
			}
			x += imageWidth + margin;
			++i;
		}
	}

	@Override
	public int getWidth() {
		return imageWidth * maxLives + margin * (maxLives - 1);
	}

	@Override
	public int getHeight() {
		return life.getHeight();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		// do nothing
	}
}
