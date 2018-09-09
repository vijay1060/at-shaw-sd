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

public class Triple_Play_Hardware_Swap extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	@DataProvider
	public Object[][] getData3(){
		return Utility.getData(xls,"TC3");
	}

	@Test (dataProvider="getData3",description="Golden Suite",priority=3)
	@SuppressWarnings(value = { "rawtypes" })
	public void Triple_Play_Hardware_Swap(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Triple_Play_Hardware_Swap");
			if(data.get("Run").equals("No")){
				//test.log(LogStatus.INFO, "Run mode is " +data.get("Run"));
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String tvBoxSerialNo=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNo=data.get("TV_Bluesky_Portal_Serial_No");
			String swapConvergedHarwareNo=data.get("Swap_Converged_Serial_No");
			String tvBoxSwapSerialNo=data.get("TV_Bluesky_Swap_Box_Serial_No");
			String tvPortalSwapSerialNo=data.get("TV_Bluesky_Swap_Portal_Serial_No");
			String convergedHardware=data.get( "Converged_hardware" );
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			String phoneNbr=order.addServicePhone(phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addServiceTVWithBlueSky("Limited TV",tvBoxSerialNo,tvPortalSerialNo);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware("Phone");

			ExtentTest childTest6=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest7=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest9=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest10=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.runJob();

			ExtentTest childTest11=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest12=extent.startTest("Navigate to Customer Order Page");
			ExtentTestManager.getTest().appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.navigateToCustOrderPage("true",accntNum);

			ExtentTest childTest13=extent.startTest("Swap Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.swapConvergedHardware(swapConvergedHarwareNo);

			ExtentTest childTest14=extent.startTest("Swap Bluesky TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.swapServiceTVBlueSky(tvBoxSwapSerialNo,tvPortalSwapSerialNo);

			ExtentTest childTest15=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.enterInstallationFee();
			order.selectProduct();
			order.techAppointment(techAppointment);

			ExtentTest childTest16=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			som=createChildTest(childTest17,extent,som);
			com.switchPreviousTab();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest18);
			som=createChildTest(childTest18,extent,som);
			report=createChildTest(childTest18,extent,report);
			som.navigateToDisconnectPhoneRFSProvisioningReport();
			Assert.assertTrue( som.validateDisconnectPhoneRfsOrder( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectInternetRFSProvisioningReport();
			Assert.assertTrue( som.validateDisconnectInternetRfsOrder( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectTVRFSProvisioningReport1();
			Assert.assertTrue( report.getActualDisconnectTVNcToHpsa( accntNum,tvPortalSerialNo,tvBoxSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectTVRFSProvisioningReport2();
			Assert.assertTrue( report.getActualDisconnectTVNcToHpsa( accntNum,tvPortalSerialNo,tvBoxSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest19=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			report=createChildTest(childTest19,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport();
			Assert.assertTrue(report.getActualNewCustNcToJms_09(accntNum,phoneNbr,convergedHardWareSerialNo,swapConvergedHarwareNo,tvBoxSerialNo,tvPortalSerialNo,tvBoxSwapSerialNo,tvPortalSwapSerialNo),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue(report.getActualNcToBrmTriplePlay( accntNum,phoneNbr,convergedHardWareSerialNo,swapConvergedHarwareNo,tvBoxSerialNo,tvPortalSerialNo,tvBoxSwapSerialNo,tvPortalSwapSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Triple_Play_Hardware_Swap");
		}catch(Exception e)
		{
			log.error("Error in Triple_Play_Hardware_Swap:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}
}








