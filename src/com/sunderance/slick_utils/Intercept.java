package com.sunderance.slick_utils;

public class Intercept {

	private Side side;
	
	private Vector2f position;

	public Intercept(Side side, Vector2f position) {
		super();
		this.side = side;
		this.position = position;
	}

	public Side getSide() {
		return side;
	}

	public Vector2f getPosition() {
		return position;
	}
	
}
