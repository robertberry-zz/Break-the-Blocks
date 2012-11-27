package com.sunderance.slick_utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RectTest {
	private Rect rect;

	@Before
	public void setUp() throws Exception {
		rect = new Rect(new Vector2f(5, 5), 5f, 10f);
	}
	
	@Test
	public void testGetLeft() {
		assertEquals(rect.getLeft(), 2.5f, 0f);
	}
	
	@Test
	public void testGetRight() {
		assertEquals(rect.getRight(), 7.5f, 0f);
	}

	@Test
	public void testGetTop() {
		assertEquals(rect.getTop(), 0f, 0f);
	}
	
	@Test
	public void testGetBottom() {
		assertEquals(rect.getBottom(), 10f, 0f);
	}

	@Test
	public void testGetTopRight() {
		Vector2f topRight = rect.getTopRight();
		
		assertEquals("X co-ordinate is wrong", topRight.getX(), 7.5f, 0f);
		assertEquals("Y co-ordinate is wrong", topRight.getY(), 0f, 0f);
	}
	
	@Test
	public void testGetTopLeft() {
		Vector2f topLeft = rect.getTopLeft();
		
		assertEquals("X co-ordinate is wrong", topLeft.getX(), 2.5f, 0f);
		assertEquals("Y co-ordinate is wrong", topLeft.getY(), 0f, 0f);
	}
	
	@Test
	public void testGetBottomRight() {
		Vector2f bottomRight = rect.getBottomRight();
		
		assertEquals("X co-ordinate is wrong", bottomRight.getX(), 7.5f, 0f);
		assertEquals("Y co-ordinate is wrong", bottomRight.getY(), 10f, 0f);
	}
	
	@Test
	public void testGetBottomLeft() {
		Vector2f bottomLeft = rect.getBottomLeft();
		
		assertEquals("X co-ordinate is wrong", bottomLeft.getX(), 2.5f, 0f);
		assertEquals("Y co-ordinate is wrong", bottomLeft.getY(), 10f, 0f);
	}
}
