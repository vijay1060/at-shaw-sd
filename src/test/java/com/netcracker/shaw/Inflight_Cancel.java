package com.netcracker.shaw;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.netcracker.shaw.at_shaw_sd.pageobject.COMOrdersPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.IntegrationReports;
import com.netcracker.shaw.at_shaw_sd.pageobject.LandingPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderCreationPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.SOMOrderPage;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.report.ExtentReportManager;
import com.netcracker.shaw.report.ExtentTestManager;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Inflight_Cancel extends SeleniumTestUp {

	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	@DataProvider
	public Object[][] getData6(){
		return Utility.getData(xls,"TC6");
	}

	@Test (dataProvider="getData6",description="Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Inflight_Cancel");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String internetProduct=data.get("Internet_Product");
			String internetHardwareSerialNo=data.get( "Internet_Hardware_Serial_No" );
			String techAppointment=data.get( "Tech_Appointment" );
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get( "SOM_Order_Count" )));


			ExtentTest childTest=extent.startTest("LOGIN and Open OE page");
			ExtentTestManager.getTest().appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			landing.Login();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			ExtentTestManager.getTest().appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation();

			ExtentTest childTest2=extent.startTest("Select Bill Type");
			ExtentTestManager.getTest().appendChild(childTest2);
			order = createChildTest(childTest2,extent,order);
			order.selectBillType();

			ExtentTest childTest3=extent.startTest("Add Internet");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addInternetHardware(internetHardwareSerialNo,"Cisco");
			order.selectConvergedHardware("Internet");
			order.deleteConvergedHardware();

			ExtentTest childTest5=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest6=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			String accountNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest8=extent.startTest("Navigate To Customer Order Page");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToCustOrderPage("true",accountNum);
			order.cancelOrder();

			ExtentTest childTest9=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.switchPreviousTab();
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			log.debug("Leaving Inflight_Cancel");
		}catch(Exception e)
		{
			log.error("Error in Inflight_Cancel:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}
}








