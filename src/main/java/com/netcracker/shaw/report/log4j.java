package com.netcracker.shaw.report;

import org.apache.log4j.Logger;

import com.netcracker.shaw.setup.SeleniumTestUp;

public class log4j extends SeleniumTestUp {
	// Create reference variable “log” referencing getLogger method of Logger
	// class, it is used to store logs in selenium.txt
	Logger log = Logger.getLogger(log4j.class);
	
	// Call debug method with the help of referencing variable log and log the
	// information in test.logs file

}
