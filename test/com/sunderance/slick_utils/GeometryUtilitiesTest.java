/**
 * 
 */
package com.sunderance.slick_utils;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Geometry Utilities
 * 
 * @author Robert Berry
 */
public class GeometryUtilitiesTest {
	GeometryUtilities geoUtils;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		geoUtils = GeometryUtilities.getInstance();
	}

	@Test
	public void testIntercepts() {
		Vector2f[][] interceptors = {
			{new Vector2f(0, 0), new Vector2f(1, 1), 
			 new Vector2f(0, 1), new Vector2f(1, 0)},
			{new Vector2f(0, 0), new Vector2f(-1, -1),
			 new Vector2f(-1, 0), new Vector2f(0, -1)}
		};
		
		Vector2f[][] nonInterceptors = {
			{new Vector2f(0, 0), new Vector2f(1, 1), 
			 new Vector2f(2, 2), new Vector2f(3, 4)},
			{new Vector2f(0, 0), new Vector2f(-1, -1),
			 new Vector2f(0.5f, 0), new Vector2f(1, 0)}
		};
		
		for (Vector2f[] points : Arrays.asList(interceptors)) {
			Vector2f p1 = points[0],
					p2 = points[1],
					p3 = points[2],
					p4 = points[3];
			
			assertTrue(geoUtils.intercepts(p1, p2, p3, p4));
			// should also work for lines defined in opposite directions
			assertTrue(geoUtils.intercepts(p2, p1, p4, p3));
		}
		
		for (Vector2f[] points : Arrays.asList(nonInterceptors)) {
			Vector2f p1 = points[0],
					p2 = points[1],
					p3 = points[2],
					p4 = points[3];
			
			assertFalse(geoUtils.intercepts(p1, p2, p3, p4));
			assertFalse(geoUtils.intercepts(p2, p1, p4, p3));
		}
	}

}
