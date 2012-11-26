package com.sunderance.slick_utils;

/**
 * Drop in replacement for Slick's own Vector2f class. This is an immutable
 * version, so calling 'scale', 'add', etc. will not affect the instance
 * itself but return the result of the operation.
 * 
 * TODO Add all missing methods
 * 
 * @author Robert Berry
 */
public class Vector2f {
	private float x;
	private float y;
	
	/**
	 * Constructs vector with given x and y components
	 * 
	 * @param x X component
	 * @param y Y component
	 */
	public Vector2f(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * The vector that is this vector added to other vector
	 * 
	 * @param other The other vector
	 * @return The sum vector
	 */
	public Vector2f add(Vector2f other) {
		return new Vector2f(x + other.getX(), y + other.getY());
	}
	
	/**
	 * The vector minus the other
	 * 
	 * @param other The other vector
	 * @return The difference vector
	 */
	public Vector2f subtract(Vector2f other) {
		return new Vector2f(x - other.getX(), y - other.getY());
	}
	
	/**
	 * X component of vector
	 * 
	 * @return X component
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Y component of vector
	 * 
	 * @return Y component
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Vector scaled by n
	 * 
	 * @param d Number by which to scale vector
	 * @return Scaled vector
	 */
	public Vector2f scale(float d) {
		return new Vector2f(x * d, y * d);
	}

	/**
	 * Vector representing the signs of the x and y components
	 * 
	 * @return The signum vector
	 */
	public Vector2f sgn() {
		return new Vector2f(x > 0 ? 1 : -1, y > 0 ? 1 : -1);
	}
	
	/**
	 * Absolute-value form of the vector
	 * 
	 * @return The absolute value form
	 */
	public Vector2f abs() {
		return new Vector2f(x < 0 ? -x : x, y < 0 ? -y : y);
	}

	/**
	 * Result of multiplying the x and y co-ordinates of this and the other
	 * vector
	 * 
	 * @param other The other vector
	 * @return The resultant vector
	 */
	public Vector2f scale(Vector2f other) {
		return new Vector2f(x * other.getX(), y * other.getY());
	}
	
	/**
	 * Zero vector
	 * 
	 * @return The vector
	 */
	public static Vector2f zero() {
		return new Vector2f(0, 0);
	}
	
	/**
	 * Vector as string
	 * 
	 * @return The string
	 */
	public String toString() {
		return String.format("Vector2f(%f, %f)", x, y);
	}
	
	/**
	 * This vector with the given x component
	 * 
	 * @param x The x component
	 * @return The vector
	 */
	public Vector2f withX(float x) {
		return new Vector2f(x, y);
	}
	
	/**
	 * This vector with the given y component
	 * 
	 * @param y The y component
	 * @return The vector
	 */
	public Vector2f withY(float y) {
		return new Vector2f(x, y);
	}
}
