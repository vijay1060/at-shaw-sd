package com.netcracker.shaw;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	@DataProvider
	public Object[][] getData21(){
		return Utility.getData(xls,"TC21");
	}

	@Test (dataProvider="getData21",description="Priority-1",priority=20)
	@SuppressWarnings(value = { "rawtypes" })
	public void TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get( "Converged_hardware" );
			String convergedHardWareSerialNbr=data.get("Converged_Serial_No");
			String tvBoxSerialNbr=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNbr=data.get("TV_Bluesky_Portal_Serial_No");
			String disconnectReason=data.get("Disconnect_Reasons");
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

			ExtentTest childTest3=extent.startTest("Add Phone, Voice mail");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServicePhone(phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Internet");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add TV");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6=extent.startTest("Add TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTVWithBlueSky("Limited TV", tvBoxSerialNbr, tvPortalSerialNbr);

			ExtentTest childTest7=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest8=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest9=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("false",order.getAccountNumber());

			ExtentTest childTest11=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.runJob();

			ExtentTest childTest13=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14=extent.startTest("Navigate to Customer Order Page");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToCustOrderPage("false",accntNum);

			ExtentTest childTest15=extent.startTest("TV Product Not selected Disconnect");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.disconnectTVProduct();

			ExtentTest childTest16 = extent.startTest( "Installation Fee" );
			ExtentTestManager.getTest().appendChild(childTest16);
			order = createChildTest(childTest16,extent,order);
			order.enterInstallationFee();

			ExtentTest childTest17 = extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest17);
			order = createChildTest(childTest17, extent,order);
			order.techAppointment(techAppointment);

			ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			ExtentTestManager.getTest().appendChild(childTest18);
			order = createChildTest(childTest18, extent,order);
			order.disconnectReasons(disconnectReason);

			ExtentTest childTest19=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.openCOMOrderPage("false",order.getAccountNumber());

			ExtentTest childTest21 = extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest21);
			com = createChildTest(childTest21,extent,com);
			som = createChildTest(childTest21,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest22);
			som=createChildTest(childTest22,extent,som);
			report=createChildTest(childTest22,extent,report);
			som.navigateToDisconnectTVRFSProvisioningReport1();
			Assert.assertTrue(report.getActualDisconnectTVNcToHpsa_11(accntNum,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectTVRFSProvisioningReport2();
			Assert.assertTrue(report.getActualDisconnectTVNcToHpsa_11(accntNum,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			report=createChildTest(childTest23,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport();
			Assert.assertTrue( report.getActualCustOrderNcJMS_11( accntNum, tvBoxSerialNbr, tvPortalSerialNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com. navigateToModifyBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_11(accntNum,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB");
		}catch(Exception e)
		{
			log.error("Error in TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}
}

