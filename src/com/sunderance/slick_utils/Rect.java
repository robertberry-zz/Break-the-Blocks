/**
 * 
 */
package com.sunderance.slick_utils;

import org.newdawn.slick.GameContainer;

/**
 * Rectangle class - for managing collisions and positioning
 * 
 * @author Robert Berry
 */
public class Rect {
	Vector2f centre;
	
	float height;
	float width;
	
	public Rect(Vector2f centre, float width, float height) {
		super();
		this.centre = centre;
		this.height = height;
		this.width = width;
	}
	
	/**
	 * The rectangle's height
	 * 
	 * @return The height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * The rectangle's width
	 * 
	 * @return The width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * The rectangle with the new centre point
	 * 
	 * @param centre The centre point
	 * @return The resultant rectangle
	 */
	public Rect withCentre(Vector2f centre) {
		return new Rect(centre, width, height);
	}
	
	public Rect withMargin(float margin) {
		return new Rect(centre, width + margin * 2, height + margin * 2);
	}
	
	/**
	 * The rectangle with the new height
	 * 
	 * @param height The new height
	 * @return The resultant rectangle
	 */
	public Rect withHeight(float height) {
		return new Rect(centre, width, height);
	}
	
	/**
	 * The rectangle with the new width
	 * 
	 * @param width The new width
	 * @return The resultant rectangle
	 */
	public Rect withWidth(float width) {
		return new Rect(centre, width, height);
	}
	
	/**
	 * Centre point of the rectangle
	 * 
	 * @return The centre point
	 */
	public Vector2f getCentre() {
		return centre;
	}
	
	/**
	 * Top left point of the rectangle
	 * 
	 * @return The top left point
	 */
	public Vector2f getTopLeft() {
		return centre.add(new Vector2f(-width / 2, -height / 2));
	}
	
	public float getLeft() {
		return centre.getX() - width / 2;
	}
	
	public float getRight() {
		return centre.getX() + width / 2;
	}
	
	public float getTop() {
		return centre.getY() - height / 2;
	}
	
	public float getBottom() {
		return centre.getY() + height / 2;
	}
	
	/**
	 * Top right point of the rectangle
	 * 
	 * @return The top right point
	 */
	public Vector2f getTopRight() {
		return centre.add(new Vector2f(width / 2, -height / 2));
	}
	
	/**
	 * Bottom left point of the rectangle
	 * 
	 * @return The bottom left point
	 */
	public Vector2f getBottomLeft() {
		return centre.add(new Vector2f(-width / 2, height / 2));
	}
	
	/**
	 * Bottom right point of the rectangle
	 * 
	 * @return The bottom right point
	 */
	public Vector2f getBottomRight() {
		return centre.add(new Vector2f(width / 2, height / 2));
	}
	
	public static Rect fromGameContainer(GameContainer gc) {
		return new Rect(new Vector2f(gc.getWidth() / 2, gc.getHeight() / 2),
				gc.getWidth(), gc.getHeight());
	}
	
	public String toString() {
		return String.format("Rect(centre=%s, width=%f, height=%f)",
				centre.toString(), width, height);
	}

	public Rect withoutMargin(float margin) {
		return new Rect(centre, width - margin * 2, height - margin * 2);
	}

	public Rect withLeft(float i) {
		return new Rect(centre.withX(i + width / 2), width, height);
	}
	
	public Rect withRight(float i) {
		return new Rect(centre.withX(i - width / 2), width, height);
	}

	public Vector2f getBottomCentre() {
		return new Vector2f(centre.getX(), getBottom());
	}
}
