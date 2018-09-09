package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.LandingPageElement.*;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.testng.Assert;

//import com.aventstack.extentreports.Status;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;


@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class LandingPage<LandingPageElement> extends BasePage {
    public ExtentTest test;
    Logger log = Logger.getLogger(LandingPage.class);
	public LandingPage(ExtentTest test1){
		super(test1);
	}
	
	public void setTest(ExtentTest test1){
	test=test1;
	}
	public void Login(){
		String ExpectedTitle="Inventory - NetCracker";
		try{
			log.debug("Entering Login");
			navigate(Utility.getValueFromPropertyFile("basepage_url"));
			test.log(LogStatus.INFO, "Open URL ","OPen  "+Utility.getValueFromPropertyFile("basepage_url"));
			log.debug("Opening the URL");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			if(isDisplayed(LOGIN))
				test.log(LogStatus.PASS,"Browser opened Successfully","LOGIN button is visisble");

			else
				test.log(LogStatus.FAIL, "Browser didn't open");

			inputText(USER_NAME,Utility.getValueFromPropertyFile("user"));
			log.debug("Entering User Name");
			wait(1);
			inputText(PASSWORD,Utility.getValueFromPropertyFile("password"));
			log.debug("Entering Password");
			wait(1);
			click(LOGIN);
			log.debug("Clicking Login Button");
			wait(2);
			takeScreenShot();

			log.debug("Title::"+driver.getTitle());
			test.log(LogStatus.PASS, "Login","Logged IN Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: "+ test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");	
		}catch(Exception e){
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());

		}

	}

	//public OrderCreationPage openUrl(){
	public void openUrl()
	{
		try{
			log.debug("Entering openUrl");	
			navigate(Utility.getValueFromPropertyFile("basepage_url1"));
			log.debug("Opening OE Page");
			wait(2);
			test.log(LogStatus.PASS, "Open Browser","Order Creation Page opened");
			test.log(LogStatus.INFO, "Snapshot Below: "+ test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openUrl");	
		}catch(Exception e)
		{
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
}
