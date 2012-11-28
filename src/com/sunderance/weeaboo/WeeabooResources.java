package com.sunderance.weeaboo;

import java.io.File;
import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

import com.sunderance.slick_utils.ResourceManager;
import com.sunderance.slick_utils.ScoreTable;

/**
 * Singleton for managing game resources
 * 
 * @author Robert Berry
 */
public class WeeabooResources {
	private static final String DEFINITIONS_PATH = "res/xml/resources.xml";
	private static final String SCORES_PATH = "res/xml/high_scores.xml";
	private static final int NUMBER_HIGH_SCORES = 10;
	
	private static WeeabooResources instance;
	
	private ScoreTable highScores;
	
	private ResourceManager manager;
	
	private WeeabooResources() {
		File definitions = new File(DEFINITIONS_PATH);
		File scores = new File(SCORES_PATH);
		
		// TODO clean this up
		try {
			manager = ResourceManager.fromFile(definitions);
			highScores = ScoreTable.fromFile(scores);
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The high scores
	 * 
	 * @return The scores
	 */
	public ScoreTable getHighScores() {
		return highScores;
	}
	
	/**
	 * Adds a high score with the given name and score
	 * 
	 * @param name Name of the scorer
	 * @param score Their score
	 */
	public void addHighScore(String name, int score) {
		ScoreTable.Entry entry = new ScoreTable.Entry(name, score);
		
		highScores = highScores.with(entry).take(NUMBER_HIGH_SCORES);
	}
	
	/**
	 * Returns the image with the given name
	 * 
	 * @param name The name
	 * @return The image
	 */
	public Image getImage(String name) {
		return manager.getImage(name);
	}
	
	/**
	 * Returns the font with the given name
	 * 
	 * @param name The name
	 * @return The font
	 */
	public UnicodeFont getFont(String name) {
		return manager.getFont(name);
	}
	
	/**
	 * An instance of the resource manager
	 * 
	 * @return The instance
	 */
	public static WeeabooResources getInstance() {
		if (instance == null) {
			instance = new WeeabooResources();
		}
		
		return instance;
	}
}
