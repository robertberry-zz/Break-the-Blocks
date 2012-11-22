package com.sunderance.slick_utils;

import nu.xom.Element;

abstract public class ResourceBuilder<T> {

	abstract public T build(Element resourceInfo);
}
