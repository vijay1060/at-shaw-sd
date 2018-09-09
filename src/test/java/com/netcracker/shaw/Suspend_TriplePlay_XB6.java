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

public class Suspend_TriplePlay_XB6 extends SeleniumTestUp {

	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	@DataProvider
	public Object[][] getData1(){
		return Utility.getData(xls,"TC1");
	}

	@Test (dataProvider="getData1",description="Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Suspend_TriplePlay(Hashtable<String,String> data) throws Exception{
		try
		{
			log.debug("Entering Suspend_TriplePlay");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String tvHardwareSerialNo=data.get("TV_SD_Serial_No");
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
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

			ExtentTest childTest3=extent.startTest("Add Phone, Internet and TV");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServicePhone(phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			order.addServiceTV(tvProduct);

			ExtentTest childTest4=extent.startTest("Add TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addTVHardware(tvHardwareSerialNo,"SD");

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
			order.openCOMOrderPage("false",order.getAccountNumber());

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

			ExtentTest childTest12=extent.startTest("SUSPEND TV");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.suspendOrder();
			Assert.assertEquals(com.suspendStatus(),"Active","Suspend Status is not Correct");

			ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			som=createChildTest(childTest13,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14=extent.startTest("Validate SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest14);
			som=createChildTest(childTest14,extent,som);
			report=createChildTest(childTest14,extent,report);
			som.navigateToSOMOrder();
			som.navigateToSuspendTVProvisioningReport1();
			//Assert.assertTrue(som.validateSuspendTriplePlayAllParams(accntNum),"Attributes are not validated successfully");
			Assert.assertTrue(report.getActualSuspend(accntNum),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			log.debug("Leaving Suspend_TriplePlay");

		}catch(Exception e)
		{
			log.error("Error in Suspend_TriplePlay:" + e.getMessage());
			ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed");
		}
	}
}








