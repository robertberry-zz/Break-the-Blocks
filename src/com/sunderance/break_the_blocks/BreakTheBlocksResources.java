package com.sunderance.break_the_blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

import com.google.common.collect.Iterables;
import com.sunderance.slick_utils.ResourceManager;
import com.sunderance.slick_utils.ScoreTable;

/**
 * Singleton for managing game resources
 * 
 * @author Robert Berry
 */
public class BreakTheBlocksResources {
	private static final String DEFINITIONS_PATH = "res/xml/resources.xml";
	private static final String SCORES_PATH = "res/xml/high_scores.xml";
	private static final int NUMBER_HIGH_SCORES = 10;
	
	private static BreakTheBlocksResources instance;
	
	private ScoreTable highScores;
	
	private ResourceManager manager;
	
	private BreakTheBlocksResources() {
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
	 * Saves the current high scores.
	 */
	public void saveHighScores() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(SCORES_PATH);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			out.write(highScores.toDocument().toXML().toString());
			out.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Whether the given score would appear in the high scores table
	 * 
	 * @param score The score
	 * @return Whether high score
	 */
	public boolean isHighScore(int score) {
		for (int s : highScores.scores()) {
			if (score > s) return true;
		}
		return false;
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
		
		saveHighScores();
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
	public static BreakTheBlocksResources getInstance() {
		if (instance == null) {
			instance = new BreakTheBlocksResources();
		}
		
		return instance;
	}
}
