/**
 * 
 */
package com.sunderance.slick_utils;

import nu.xom.Element;

/**
 * Memoizing wrapper for a resource.
 * 
 * @author Robert Berry
 */
public class Resource<T> {
	private T data;
	
	private ResourceBuilder<T> builder;
	
	private Element info;
	
	/**
	 * Builds the resource wrapper with the given resource builder and XML node
	 * definition
	 * 
	 * @param builder Knows how to construct the resource from the XML node
	 * @param info The XML node
	 */
	public Resource(ResourceBuilder<T> builder, Element info) {
		super();
		this.builder = builder;
		this.info = info;
	}

	/**
	 * The resource - builds it if it's not been previously memoized.
	 * 
	 * @return The reosource
	 */
	public T getResource() {
		if (data == null) {
			data = builder.build(info);
		}
		
		return data;
	}
}
