/**
 * Main game class
 */
package com.sunderance.break_the_blocks;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.sunderance.break_the_blocks.states.*;

/**
 * Weeaboo game
 * 
 * @author Robert Berry
 */
public class BreakTheBlocks extends StateBasedGame {
	private static final boolean FULL_SCREEN = false;
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 640;
	private static final int TARGET_FRAMES_PER_SECOND = 60;
	private static final boolean SHOW_FRAMES_PER_SECOND = true;
	private static final String TITLE = "Break the Blocks!";
	
	/**
	 * State IDs
	 */
	public enum State {
		SPLASH_SCREEN,
		MENU,
		IN_GAME,
		PAUSED,
		HIGH_SCORES
	}
	
	public BreakTheBlocks() {
		super(TITLE);
	}

	/**
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new SplashScreen(State.SPLASH_SCREEN));
		addState(new Menu(State.MENU));
		addState(new HighScores(State.HIGH_SCORES));
		addState(new PauseState(State.PAUSED));
		addState(new InGame(State.IN_GAME));
	}

	/**
	 * Run the game
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new BreakTheBlocks());
			
			app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, FULL_SCREEN);
			app.setTargetFrameRate(TARGET_FRAMES_PER_SECOND);
			app.setShowFPS(SHOW_FRAMES_PER_SECOND);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
