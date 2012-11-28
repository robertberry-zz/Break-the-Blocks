package com.sunderance.break_the_blocks.timed_events;

public interface MessageListener<T> {
	public void tell(T message);
}
