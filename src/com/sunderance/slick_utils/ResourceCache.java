package com.sunderance.slick_utils;

import java.util.Map;
import java.util.HashMap;

import nu.xom.Element;

public class ResourceCache<T> {

	private ResourceBuilder<T> resourceBuilder;
	
	private Map<String, Resource<T>> cache = new HashMap<String, Resource<T>>();
	
	public ResourceCache(ResourceBuilder<T> resourceBuilder) {
		super();
		this.resourceBuilder = resourceBuilder;
	}

	public void addResourceDefinition(Element info) {
		String name = info.getFirstChildElement("name").getValue();
		cache.put(name, new Resource<T>(resourceBuilder, info));
	}
	
	public T getResource(String name) {
		if (cache.containsKey(name)) {
			Resource<T> resource = cache.get(name);
			return resource.getResource();
		} else {
			throw new RuntimeException(String.format("Resource with name %s " +
					"not in definitions file", name));
		}
	}
}
