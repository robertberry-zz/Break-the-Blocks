package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.sunderance.slick_utils.Vector2f;

public class MenuRenderComponent extends RenderComponent {
	UnicodeFont font;
	UnicodeFont selectedFont;
	ImmutableList<MenuItem> menuItems;
	
	int size;
	
	int width;
	int height;
	int margin;
	
	int selectedIndex = 0;

	public MenuRenderComponent(UnicodeFont font, UnicodeFont selectedFont,
			ImmutableList<MenuItem> menuItems, int margin) {
		super();
		this.menuItems = menuItems;
		this.font = font;
		this.selectedFont = selectedFont;
		this.margin = margin;
		
		size = menuItems.size();
		
		if (size == 0) {
			throw new RuntimeException("Cannot construct a menu of zero " +
					"items.");
		}
		
		String first = Iterables.getFirst(menuItems, null).getText();
		
		width = font.getWidth(first);
		height = font.getHeight(first);
		
		for (MenuItem item : menuItems) {
			width = Math.max(font.getWidth(item.getText()), width);
			height += margin + font.getHeight(item.getText());
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		Vector2f position = getOwner().getRect().getTopLeft();
		
		float x = position.getX(),
				y = position.getY();
		
		for (int i = 0; i < size; i++) {
			UnicodeFont f = (i == selectedIndex) ? selectedFont : font;
			
			String text = menuItems.get(i).getText();
			f.drawString(x, y, text);
			y += f.getHeight(text) + margin;
		}
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			if (selectedIndex != 0) {
				selectedIndex--;
			}
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			if (selectedIndex < size - 1) {
				selectedIndex++;
			}
		} else if (input.isKeyPressed(Input.KEY_RETURN)) {
			this.menuItems.get(selectedIndex).trigger();
		}
	}

}
