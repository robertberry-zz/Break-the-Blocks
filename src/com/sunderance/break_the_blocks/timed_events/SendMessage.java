package com.sunderance.break_the_blocks.timed_events;

import com.sunderance.slick_utils.TimedEvent;

public class SendMessage<T> implements TimedEvent {
	MessageListener<T> listener;
	T message;
	
	public SendMessage(MessageListener<T> listener, T message) {
		super();
		this.listener = listener;
		this.message = message;
	}

	@Override
	public void trigger() {
		listener.tell(message);
	}

}
