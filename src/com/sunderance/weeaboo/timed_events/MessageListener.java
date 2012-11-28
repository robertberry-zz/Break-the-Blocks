package com.sunderance.weeaboo.timed_events;

public interface MessageListener<T> {
	public void tell(T message);
}
