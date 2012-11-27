/**
 * 
 */
package com.sunderance.slick_utils;

import com.google.common.base.Optional;

/**
 * Singleton supplying functions for working with vectors, components, and
 * games
 * 
 * @author Robert Berry
 */
public class GeometryUtilities {
	private static GeometryUtilities instance;
	
	/**
	 * Singleton - cannot be instantiated by end-user.
	 */
	private GeometryUtilities() {
	}
	
	/**
	 * Magnitude of the distance between p1 and p2
	 * 
	 * @param p1 The first point
	 * @param p2 The second point
	 * @return The magnitude
	 */
	public float magnitude(Vector2f p1, Vector2f p2) {
		return (float) Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) +
				Math.pow(p2.getY() - p1.getY(), 2));
	}
	
	/**
	 * Whether the line segments defined as p1 -> p2 and p3 -> p4 intersect
	 * 
	 * @param p1 Start of line 1
	 * @param p2 End of line 1
	 * @param p3 Start of line 2
	 * @param p4 End of line 2
	 * @return Whether an intersection occurs
	 */
	public Optional<Vector2f> intercepts(Vector2f p1, Vector2f p2, Vector2f p3, 
			Vector2f p4) {
		float x1 = p1.getX(),
				y1 = p1.getY(),
				x2 = p2.getX(),
				y2 = p2.getY(),
				x3 = p3.getX(),
				y3 = p3.getY(),
				x4 = p4.getX(),
				y4 = p4.getY();
		
		float denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		
		if (denom == 0) {
			// lines are parallel
			return Optional.absent();
		} else {
			float ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) /
					denom;
			float ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) /
					denom;
			if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
				return Optional.of(new Vector2f(x1 + ua * (x2 - x1),
						y1 + ua * (y2 - y1)));
			} else {
				return Optional.absent();
			}
		}
	}
	
	/**
	 * Instance of the singleton
	 * 
	 * @return The instance
	 */
	public static GeometryUtilities getInstance() {
		if (instance == null) {
			instance = new GeometryUtilities();
		}
		return instance;
	}
}
