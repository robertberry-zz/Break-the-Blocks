package com.sunderance.slick_utils;

import java.util.Map;
import java.util.HashMap;

import nu.xom.Element;

/**
 * A cache for a given type of resource
 * 
 * @author Robert Berry
 *
 * @param <T> The resource being cached
 */
public class ResourceCache<T> {

	private ResourceBuilder<T> resourceBuilder;
	
	private Map<String, Resource<T>> cache = new HashMap<String, Resource<T>>();
	
	/**
	 * Constructs the cache with the given resource builder
	 * 
	 * @param resourceBuilder Builds resources from an XML definition if not
	 *   already in cache
	 */
	public ResourceCache(ResourceBuilder<T> resourceBuilder) {
		super();
		this.resourceBuilder = resourceBuilder;
	}

	/**
	 * Adds an XML node resource definition - this is used by the resource
	 * builder to build a given resource if it is not in the cache
	 * 
	 * @param info The XML node
	 */
	public void addResourceDefinition(Element info) {
		String name = info.getFirstChildElement("name").getValue();
		cache.put(name, new Resource<T>(resourceBuilder, info));
	}
	
	/**
	 * Returns the resource with the given name
	 * 
	 * @param name The name of the resource
	 * @return The resource
	 */
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
