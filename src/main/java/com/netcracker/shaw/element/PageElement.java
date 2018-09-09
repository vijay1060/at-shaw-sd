package com.netcracker.shaw.element;

import org.openqa.selenium.By;

import com.netcracker.shaw.factory.Locator;


public interface PageElement {
	public Locator getLocator();
	public String getValue();
	public By getBy(String...placeholder);


}
