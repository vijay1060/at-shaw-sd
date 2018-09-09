package com.netcracker.shaw.setup;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.netcracker.shaw.at_shaw_sd.pageobject.COMOrdersPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.IntegrationReports;
import com.netcracker.shaw.at_shaw_sd.pageobject.LandingPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderCreationPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.SOMOrderPage;
import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.report.ExtentReportManager;
import com.netcracker.shaw.report.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class SeleniumTestUp extends TestListenerAdapter{
		
	public static WebDriver DRIVER;
	public ExcelOperation xls =new ExcelOperation(Constants.GoldenSuite_XLS);
	public static ExtentReports extent = ExtentReportManager.getInstance();
	//public static ExtentReports rep = ExtentReporManager.getInstance();
	public static ExtentTest test;
	public static String testName;
	public String tcid;
	public static String sheetName;
	protected Logger log = Logger.getLogger(SeleniumTestUp.class); 
	
	
	public String getTcid() {
		return tcid;
	}

	public void setTcid(String tcid) {
		this.tcid = tcid;
	}
	
	@BeforeMethod
	public void beforeMethod(Method caller) throws MalformedURLException{
		if(Utility.getValueFromPropertyFile("grid").equalsIgnoreCase("true"))
			DRIVER = Utility.getRemoteBrowser();
		else
			DRIVER = Utility.getBrowser();
	}
			
	@AfterMethod
	public void afterMethod(ITestResult result){
		DRIVER.quit();
		/*if (result.getStatus() == ITestResult.SKIP)
	    {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Error found. Retrying the test...");
	    }
	    else if (result.getStatus() == ITestResult.FAILURE)
	    {
	    	ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable());
	    }
	    else if (result.getStatus() == ITestResult.SUCCESS)
	    {
	    	ExtentTestManager.getTest().log(LogStatus.PASS, "Test completed successfully");
	    }*/

	    //test.appendChild(ExtentTestManager.getTest()); // don't end child, but append
	    extent.flush();
			 
	}
	
	@AfterTest
	public static void endParentTest() {
	    extent.endTest(test);
	    extent.flush();
	}
	
	public OrderCreationPage createChildTest(ExtentTest test,ExtentReports rep,OrderCreationPage o){
		 //test=rep.startTest(TestName);
		 o=new OrderCreationPage(test);
		 o.setTest(test);
		 return o;
		}
	
	public LandingPage createChildTest(ExtentTest test,ExtentReports rep,LandingPage l){
		 //test=rep.startTest(TestName);
		 l=new LandingPage(test);
		 l.setTest(test);
		 return l;
		}
	
	public COMOrdersPage createChildTest(ExtentTest test,ExtentReports rep,COMOrdersPage com){
		 //test=rep.startTest(TestName);
		 com=new COMOrdersPage(test);
		 com.setTest(test);
		 return com;
		}
	public SOMOrderPage createChildTest(ExtentTest test,ExtentReports rep,SOMOrderPage som){
		 //test=rep.startTest(TestName);
		 som=new SOMOrderPage(test);
		 som.setTest(test);
		 return som;
	}
	
	public IntegrationReports createChildTest(ExtentTest test,ExtentReports rep,IntegrationReports report){
		 //test=rep.startTest(TestName);
		 report=new IntegrationReports(test);
		 report.setTest(test);
		 return report;
		}
	
	
	@DataProvider
	public Object[][] getData(){
		return Utility.getData(xls, tcid);
	}
	
	}

	

	

	
	


