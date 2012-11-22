package com.sunderance.slick_utils;

import nu.xom.Element;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageResourceBuilder extends ResourceBuilder<Image> {
	private String imageFolderPath;

	public ImageResourceBuilder(String imageFolderPath) {
		super();
		this.imageFolderPath = imageFolderPath;
	}

	@Override
	public Image build(Element info) {
		Element path = info.getFirstChildElement("path");
		
		try {
			return new Image(imageFolderPath + path.getValue());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
