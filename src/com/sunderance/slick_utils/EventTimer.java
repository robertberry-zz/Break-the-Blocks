/**
 * 
 */
package com.sunderance.slick_utils;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Timer for triggering events after a given delay
 * 
 * @author Robert Berry
 */
public class EventTimer {
	PriorityQueue<Event> events;
	
	int time = 0;
	
	private class Event {
		private TimedEvent event;
		private int time;
		
		public Event(int time, TimedEvent event) {
			this.time = time;
			this.event = event;
		}

		public TimedEvent getEvent() {
			return event;
		}

		public int getTime() {
			return time;
		}
	}
	
	private class EventComparator implements Comparator<Event> {
		@Override
		public int compare(Event event1, Event event2) {
			return event1.getTime() - event2.getTime();
		}
	}
	
	public EventTimer() {
		events = new PriorityQueue<Event>(11, new EventComparator());
	}
	
	/**
	 * Adds the event to trigger after the given delay
	 * 
	 * @param delay The delay in milliseconds
	 * @param event The event
	 */
	public void addEvent(int delay, TimedEvent event) {
		events.add(new Event(time + delay, event));
	}
	
	/**
	 * Updates the timer - should be run when the state's update runs
	 * 
	 * @param delta How many milliseconds have passed
	 */
	public void update(int delta) {
		time += delta;
		
		while (!events.isEmpty() && events.peek().getTime() <= time) {
			Event event = events.remove();
			event.getEvent().trigger();
		}
	}
}
