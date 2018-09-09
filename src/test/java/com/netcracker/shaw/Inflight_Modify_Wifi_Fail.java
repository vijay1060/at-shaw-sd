package com.netcracker.shaw;

import java.util.Hashtable;

import org.testng.Assert;
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

public class Inflight_Modify_Wifi_Fail extends SeleniumTestUp {

	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	@DataProvider
	public Object[][] getData7(){
		return Utility.getData(xls,"TC7");
	}

	@Test (dataProvider="getData7",description="Golden Suite",priority=6)
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Wifi_Fail(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Inflight_Modify_Wifi_Fail");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String phoneProduct=data.get("Phone_Product");
			String phoneHardwareSerialNbr=data.get( "Phone_Hardware_Serial_No" );
			String internetHardwareSerialNbr=data.get( "Internet_Hardware_Serial_No" );
			String phoneHardware=data.get( "Phone_hardware" );
			String tvProduct=data.get("TV_Product");
			String tvHardwareSerialNbr=data.get("TV_SD_Serial_No");
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			String phoneNbr=order.addServicePhone(phoneProduct);

			ExtentTest childTest4=extent.startTest("Add Phone Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addPhoneHardware(phoneHardwareSerialNbr,phoneHardware);

			ExtentTest childTest5=extent.startTest("Add TV Product");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV( tvProduct );

			ExtentTest childTest6=extent.startTest("Add TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addTVHardware( tvHardwareSerialNbr,"SD" );

			ExtentTest childTest7=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest8=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accountNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest10=extent.startTest("Navigate To Stub to fail wifi");
			ExtentTestManager.getTest().appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOeStubServerToFail("true");
			order.navigateToHpsaFail("Wifi");

			ExtentTest childTest11=extent.startTest("Add Internet 150");
			ExtentTestManager.getTest().appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.switchPreviousTab();
			order.navigateToCustOrderPage( "false", accountNum );
			order.addServiceInternet("Internet 150 Unlimited");

			ExtentTest childTest12=extent.startTest("Add Internet Hardware");
			ExtentTestManager.getTest().appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.addInternetHardware( internetHardwareSerialNbr,"Hitron" );
			order.selectConvergedHardware("Internet");
			//order.deleteConvergedHardware();

			ExtentTest childTest13=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.enterInstallationFee();
			order.techAppointment("Yes");

			ExtentTest childTest14=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.openCOMOrderPage("false",accountNum);

			ExtentTest childTest16=extent.startTest("Navigate to Stub to up wifi");
			ExtentTestManager.getTest().appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOeStubServerToPass("true","Wifi");

			ExtentTest childTest17=extent.startTest("Downgrade Internet 150 to Internet 75");
			ExtentTestManager.getTest().appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.switchPreviousTab();
			order.navigateToCustOrderPage( "false", accountNum );
			order.addServiceInternet("Internet 75");

			ExtentTest childTest18=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.selectTodo();
			order.techAppointmentCurrentDate();

			ExtentTest childTest19=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.openCOMOrderPage("false",accountNum);

			ExtentTest childTest21=extent.startTest("Send same serial number");
			ExtentTestManager.getTest().appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			com=createChildTest(childTest21,extent,com);
			com.navigateToNewInternetHardwareCfsOrder3();
			com.sendSerialNbrTask( internetHardwareSerialNbr );
			order.clickAccountLinkForOrders();

			ExtentTest childTest22=extent.startTest("Mark task wait for tech confirm finished");
			ExtentTestManager.getTest().appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			com=createChildTest(childTest22,extent,com);
			com.navigateToModifyCustOrder2();
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest23=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			som=createChildTest(childTest23,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest24=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest24);
			som=createChildTest(childTest24,extent,som);
			report=createChildTest(childTest24,extent,report);
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue(report.getActualNewInternetCnvrgdNcToHpsa_28( accountNum,internetHardwareSerialNbr ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToCancelWifiRFSProvisioningReport();
			Assert.assertTrue(report.getActualCancelWifiRfsNcToHpsa_28( accountNum),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest25=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			report=createChildTest(childTest25,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport2();
			Assert.assertTrue(com.validateNewCustomerOrder_28( accountNum,internetHardwareSerialNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport2();
			Assert.assertTrue(report.getActualNcToBrm_28( accountNum,phoneNbr,phoneHardwareSerialNbr,tvHardwareSerialNbr,internetHardwareSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Inflight_Modify_Wifi_Fail");
		}catch(Exception e)
		{
			log.error("Error in Inflight_Modify_Wifi_Fail:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}
}








