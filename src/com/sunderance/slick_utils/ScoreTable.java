package com.sunderance.slick_utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;

/**
 * Table of scores
 * 
 * @author Robert Berry
 */
public class ScoreTable implements Iterable<ScoreTable.Entry> {
	/**
	 * Entry in table
	 * 
	 * @author Robert Berry
	 */
	public static class Entry {
		private String name;
		private int score;
		
		/**
		 * Creates an entry for the person with the given name and score
		 * 
		 * @param name The scorer's name
		 * @param score Their score
		 */
		public Entry(String name, int score) {
			super();
			this.name = name;
			this.score = score;
		}

		/**
		 * Name of person to whom score belongs
		 * 
		 * @return Scorer's name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Value of the score
		 * 
		 * @return The score
		 */
		public int getScore() {
			return score;
		}
	}
	
	private static class EntryComparator implements
		Comparator<Entry>  {
	
		public int compare(Entry score1, Entry score2) {
			return score1.getScore() - score2.getScore();
		}
	}

	private ImmutableSortedSet<Entry> entries;

	/**
	 * Given a list of entries, constructs a score table
	 * 
	 * @param entries The entries
	 */
	public ScoreTable(Iterable<Entry> entries) {
		super();
		this.entries = new ImmutableSortedSet.Builder<Entry>(new 
				ScoreTable.EntryComparator())
				.addAll(entries)
				.build();
	}
	
	/**
	 * Score table with additional entry
	 * 
	 * @param entry The new entry
	 * @return The table
	 */
	public ScoreTable with(Entry entry) {
		return new ScoreTable(new ImmutableList.Builder<Entry>()
				.addAll(entries)
				.add(entry)
				.build());
	}
	
	/**
	 * Score table with at most n entries
	 * 
	 * @param n Number of entries to retain
	 * @return The table
	 */
	public ScoreTable take(int n) {
		return new ScoreTable(Iterables.limit(entries, n));
	}

	@Override
	public Iterator<Entry> iterator() {
		return entries.iterator();
	}
	
	/**
	 * Constructs a score table from an XML document
	 * 
	 * @param doc The document
	 * @return The table
	 */
	public static ScoreTable fromDocument(Document doc) {
		Element root = doc.getRootElement();
		Elements scores = root.getChildElements("score");
		
		ArrayList<Entry> entries = new ArrayList<Entry>();
		
		for (int i = 0, len = scores.size(); i < len; i++) {
			Element score = scores.get(i);
			
			entries.add(new Entry(score.getFirstChildElement("name").getValue(),
					Integer.parseInt(score.getFirstChildElement("value")
							.getValue())));
		}
		
		return new ScoreTable(entries);
	}
	
	/**
	 * Constructs a score table from an XML file
	 * 
	 * @param file The file
	 * @return The table
	 * @throws ValidityException If unable to validate XML
	 * @throws ParsingException If unable to parse XML
	 * @throws IOException If unable to open file
	 */
	public static ScoreTable fromFile(File file) throws ValidityException,
		ParsingException, IOException {
		Builder builder = new Builder();
		Document document = builder.build(file);
		return fromDocument(document);
	}

	/**
	 * Number of entries in table
	 * 
	 * @return The number of entries
	 */
	public int size() {
		return entries.size();
	}
	
	/**
	 * Iterator for the scores in the table
	 * 
	 * @return The iterator
	 */
	public Iterable<Integer> scores() {
		return Iterables.transform(entries, new Function<Entry, Integer>() {
			@Override
			public Integer apply(Entry entry) {
				return entry.getScore();
			}
		});
	}
}
