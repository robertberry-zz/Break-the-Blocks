/**
 * 
 */
package com.sunderance.slick_utils;

import nu.xom.Element;

/**
 * 
 * 
 * @author Robert Berry
 */
public class Resource<T> {
	private T data;
	
	private ResourceBuilder<T> builder;
	
	private Element info;
	
	public Resource(ResourceBuilder<T> builder, Element info) {
		super();
		this.builder = builder;
		this.info = info;
	}

	public T getResource() {
		if (data == null) {
			data = builder.build(info);
		}
		
		return data;
	}
}
