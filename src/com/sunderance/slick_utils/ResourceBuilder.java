package com.sunderance.slick_utils;

import nu.xom.Element;

/**
 * Base class for a class that knows how to build a given type of resource
 * 
 * @author Robert Berry
 *
 * @param <T> The type of the resource
 */
abstract public class ResourceBuilder<T> {
	/**
	 * Builds the resource
	 * 
	 * @param resourceInfo The XML node definition of the resource
	 * @return The resource
	 */
	abstract public T build(Element resourceInfo);
}
