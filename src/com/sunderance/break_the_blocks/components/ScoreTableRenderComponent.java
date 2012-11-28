package com.sunderance.break_the_blocks.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.StateBasedGame;

import com.google.common.collect.Iterables;
import com.sunderance.slick_utils.ScoreTable;
import com.sunderance.slick_utils.Vector2f;

public class ScoreTableRenderComponent extends RenderComponent {
	private static final String format = "%-20s %6d";
	
	private int width;
	private int height;
	private int margin;
	private UnicodeFont font;
	private ScoreTable table;
	
	public ScoreTableRenderComponent(UnicodeFont font, ScoreTable table,
			int margin) {
		super();
		this.font = font;
		this.table = table;
		this.margin = margin;
		
		String first = getEntryText(Iterables.getFirst(table, null));
		width = font.getWidth(first);
		height = font.getHeight(first) * table.size() + 
				margin * (table.size() - 1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, 
			Graphics graphics) {
		Vector2f position = getOwner().getRect().getTopLeft();
		float x = position.getX(),
				y = position.getY();
		
		for (ScoreTable.Entry entry : table) {
			String entryString = getEntryText(entry);
			font.drawString(x, y, entryString);
			y += font.getHeight(entryString);
			y += margin;
		}
	}
	
	private String getEntryText(ScoreTable.Entry entry) {
		return String.format(format, entry.getName(),
					entry.getScore());
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
		// do nothing
	}
}
