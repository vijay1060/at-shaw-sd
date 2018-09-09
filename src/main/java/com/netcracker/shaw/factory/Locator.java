package com.netcracker.shaw.factory;

public enum Locator {
	
	 XPATH("xpath"), 
	 ID("id"), 
	 LINK_TEXT("linkText"),
	 CSS_SELECTOR("cssSelector"),
	 PARTIAL_LINK_TEXT("partialLinktText"),
	 CLASS_NAME("className"),
	 TAG_NAME("tagname"),
	 NAME("name");

	 private String locator;

	 Locator(String locator) {
	  this.locator = locator;
	 }

	 public String getType() {
	  return locator;
	 }

	}

