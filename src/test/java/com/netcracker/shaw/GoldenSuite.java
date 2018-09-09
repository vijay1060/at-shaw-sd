package com.netcracker.shaw;

import java.io.InputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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

public class GoldenSuite extends SeleniumTestUp {

	public ExtentReports extent = ExtentReportManager.getInstance();

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

	Logger log = Logger.getLogger(GoldenSuite.class);
	
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
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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



	@DataProvider
	public Object[][] getData2(){
		return Utility.getData(xls,"TC2");
	}

	@Test (dataProvider="getData2",description="Golden Suite",priority=2)
	@SuppressWarnings(value = { "rawtypes" })
	public void Resume_TriplePlay(Hashtable<String,String> data) throws Exception{
		try
		{
			log.debug("Entering Resume_TriplePlay");
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
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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

			ExtentTest childTest12=extent.startTest("SUSPEND TV");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.suspendOrder();
			Assert.assertEquals(com.suspendStatus(),"Active","Suspend Status is not Correct");

			ExtentTest childTest13=extent.startTest("RESUME TV");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.resumeOrder();
			Assert.assertEquals(com.suspendStatus(),"Inactive","Suspend Status is not Correct");
			com.navigateToCOMOrder();
			Assert.assertTrue(com.isPresentSuspendResumeOrderinCOM(),"All Suspend Resume Records not found");

			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest15=extent.startTest("Validate SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest15);
			som=createChildTest(childTest15,extent,som);
			report=createChildTest(childTest15,extent,report);
			som.navigateToSOMOrder();
			som.navigateToResumeTVProvisioningReport1();
			Assert.assertTrue(report.getActualResumeTVRFS(accntNum),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			log.debug("Leaving Resume_TriplePlay");
		}catch(Exception e)
		{
			log.error("Error in Resume_TriplePlay:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}


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
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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

	@DataProvider
	public Object[][] getData5(){
		return Utility.getData(xls,"TC5");
	}

	@Test (dataProvider="getData5",description="Golden Suite",priority=4)
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			String phoneHardwareSerialNbr=data.get("Phone_Hardware_Serial_No");
			String internetHardwareSerialNbr=data.get("Internet_Hardware_Serial_No");
			String tvHardwareDCXSerialNbr=data.get("TV_DCX3510_HDGuide_Sl_No");
			String techAppointment=data.get("Tech_Appointment");
			String swapPhoneHardwareSerialNbr=data.get("Swap_Phone_Hardware_Serial_No");
			String swapInternetHardwareSerialNbr=data.get("Swap_Internet_Hardware_Serial_No");
			String swapTVDCXHardwareSerialNbr=data.get("Swap_TV_DCX3510_HDGuide_Sl_No");
			String diffPhHardwareSrlNbr=data.get("Diff_Phone_Hardware_Serial_No");
			String diffIntHardwareSrlNbr=data.get("Diff_Internet_Hardware_Serial_No");
			String diffTvHardwareSrlNbr=data.get("Diff_TV_DCX3510_HDGuide_Sl_No");

			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

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

			ExtentTest childTest3=extent.startTest("Add Phone");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			String phoneNbr=order.addServicePhone(phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addPhoneHardware(phoneHardwareSerialNbr,phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet hardware");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addInternetHardware(internetHardwareSerialNbr,"Hitron");

			ExtentTest childTest7=extent.startTest("Add TV");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8=extent.startTest("Add TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.addTVHardware(tvHardwareDCXSerialNbr,"DCX");

			ExtentTest childTest9=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest10=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest12=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest13=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.runJob();

			ExtentTest childTest14=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest15=extent.startTest("Navigate to Stub URL Fail");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOeStubServerToFail( "true" );
			order.navigateToHpsaFail("Phone");
			order.navigateToHpsaFail("Internet");
			order.navigateToHpsaFail("CableTV");
			order.switchPreviousTab();

			ExtentTest childTest16=extent.startTest("Navigate to Customer Order Page");
			ExtentTestManager.getTest().appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.navigateToCustOrderPage("false",accntNum);

			ExtentTest childTest17=extent.startTest("Swap Phone Hardware");
			ExtentTestManager.getTest().appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.swapPhoneHardware(swapPhoneHardwareSerialNbr);

			ExtentTest childTest18=extent.startTest("Swap Internet Hardware");
			ExtentTestManager.getTest().appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.swapHitronInternetHardware(swapInternetHardwareSerialNbr);

			ExtentTest childTest19=extent.startTest("Swap TV DCX3510 Hardware");
			ExtentTestManager.getTest().appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.swapTVDCXHardware(swapTVDCXHardwareSerialNbr);

			ExtentTest childTest20=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.enterInstallationFee();
			order.techAppointmentCurrentDate();

			ExtentTest childTest21=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest22=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			order.openCOMOrderPage("false",order.getAccountNumber());

			ExtentTest childTest23=extent.startTest("Wait for provising Mark Finish Task");
			ExtentTestManager.getTest().appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			order=createChildTest(childTest23,extent,order);
			com.navigateToModifyCustOrder();
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest24=extent.startTest("Navigate To New ph hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest24);
			com=createChildTest(childTest24,extent,com);
			order=createChildTest(childTest24,extent,order);
			com.navigateToNewPhoneHardwareCFSOrder2();
			com.sendSerialNbrTask(swapPhoneHardwareSerialNbr);
			order.clickAccountLinkForOrders();

			ExtentTest childTest25=extent.startTest("Navigate To New Int hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			order=createChildTest(childTest25,extent,order);
			com.navigateToNewInternetHardwareCfsOrder2();
			com.sendSerialNbrTask(swapInternetHardwareSerialNbr);
			order.clickAccountLinkForOrders();

			ExtentTest childTest26=extent.startTest("Navigate To New TV hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest26);
			com=createChildTest(childTest26,extent,com);
			order=createChildTest(childTest26,extent,order);
			som=createChildTest(childTest26,extent,som);
			com.navigateToNewTVHardwareCfsOrder2();
			com.sendSerialNbrTask(swapTVDCXHardwareSerialNbr);
			order.clickAccountLinkForOrders();
			som.navigateToSOMOrder();

			ExtentTest childTest27=extent.startTest("Navigate to Stub URL Success");
			ExtentTestManager.getTest().appendChild(childTest27);
			order=createChildTest(childTest27,extent,order);
			order.navigateToOeStubServerToPass( "true", "Phone" );
			order.navigateToOeStubServerToPass( "false", "Internet" );
			order.navigateToOeStubServerToPass( "false", "CableTV" );
			order.switchPreviousTab();

			ExtentTest childTest28=extent.startTest("Navigate To New ph hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest28);
			com=createChildTest(childTest28,extent,com);
			order=createChildTest(childTest28,extent,order);
			com.navigateToNewPhoneHardwareCFSOrder2();
			com.sendSerialNbrTask(diffPhHardwareSrlNbr);
			order.clickAccountLinkForOrders();

			ExtentTest childTest29=extent.startTest("Navigate To New Int hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest29);
			com=createChildTest(childTest29,extent,com);
			order=createChildTest(childTest29,extent,order);
			com.navigateToNewInternetHardwareCfsOrder2();
			com.sendSerialNbrTask(diffIntHardwareSrlNbr);
			order.clickAccountLinkForOrders();

			ExtentTest childTest30=extent.startTest("Navigate To New TV hw CFS Order");
			ExtentTestManager.getTest().appendChild(childTest30);
			com=createChildTest(childTest30,extent,com);
			order=createChildTest(childTest30,extent,order);
			com.navigateToNewTVHardwareCfsOrder2();
			com.sendSerialNbrTask(diffTvHardwareSrlNbr);
			order.clickAccountLinkForOrders();

			ExtentTest childTest31=extent.startTest("Tech cnfm Mark Finish");
			ExtentTestManager.getTest().appendChild(childTest31);
			com=createChildTest(childTest31,extent,com);
			order=createChildTest(childTest31,extent,order);
			com.navigateToModifyCustOrder();
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest32=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest32);
			com=createChildTest(childTest32,extent,com);
			som=createChildTest(childTest32,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest33=extent.startTest("Validate SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest33);
			som=createChildTest(childTest33,extent,som);
			report=createChildTest(childTest33,extent,report);
			som.navigateToDisconnectInternetRFSProvisioningReport();
			Assert.assertTrue(som.validateDisconnectInternetRfsOrder_23(accntNum,internetHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectPhoneRFSProvisioningReport();
			Assert.assertTrue(som.validateDisconnectPhoneRfsOrder_23(accntNum, phoneNbr, phoneHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectTVRFSProvisioningReport1();
			Assert.assertTrue(report.getActualDisconnectTVNcToHpsa_23(accntNum, tvHardwareDCXSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewInternetRFSProvisioningReport2();
			Assert.assertTrue(report.getActualNewInternetNcToHpsa_23(accntNum, internetHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewInternetRFSProvisioningReport4();
			Assert.assertTrue(report.getActualNewInternetNcToHpsa_23(accntNum, diffIntHardwareSrlNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewPhoneRFSProvisioningReport2();
			Assert.assertTrue(report.getActualNewPhoneLineRFS_23(accntNum, phoneNbr, phoneHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewPhoneRFSProvisioningReport4();
			Assert.assertTrue(report.getActualNewPhoneLineRFS_23(accntNum, phoneNbr, diffPhHardwareSrlNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport3();
			Assert.assertTrue(report.getActualNewTVProvisioningRFSNcToHpsa_23(accntNum, tvHardwareDCXSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport6();
			Assert.assertTrue(report.getActualNewTVProvisioningRFSNcToHpsa_23(accntNum, diffTvHardwareSrlNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest34=extent.startTest("Validate COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest34);
			com=createChildTest(childTest34,extent,com);
			report=createChildTest(childTest34,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport();
			Assert.assertTrue(com.validateModifyCustomerOrder_23(accntNum, phoneNbr, phoneHardwareSerialNbr,diffPhHardwareSrlNbr,internetHardwareSerialNbr,diffIntHardwareSrlNbr,tvHardwareDCXSerialNbr,diffTvHardwareSrlNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue(report.getActualNcToBrm_23(accntNum, phoneNbr, phoneHardwareSerialNbr,diffPhHardwareSrlNbr,internetHardwareSerialNbr,diffIntHardwareSrlNbr,tvHardwareDCXSerialNbr,diffTvHardwareSrlNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV");
		}catch(Exception e)
		{
			log.error("Error in TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}

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
			//order.deleteConvergedHardware();

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

	@DataProvider
	public Object[][] getData8(){
		return Utility.getData(xls,"TC8");
	}

	@Test (dataProvider="getData8",description="Golden Suite",priority=7)
	@SuppressWarnings(value = { "rawtypes" })
	public void Modify_Internet75_to_150_XB6(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Modify_Internet75_to_150_XB6");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String internetProduct=data.get("Internet_Product");
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

			ExtentTest childTest3=extent.startTest("Add Internet");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

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
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest8=extent.startTest("Navigate to Customer Order Page");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToCustOrderPage("true",accntNum);

			ExtentTest childTest9=extent.startTest("Add Internet Hardware");
			ExtentTestManager.getTest().appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.addServiceInternet("Internet 150 Unlimited");

			ExtentTest childTest10=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest11=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.switchPreviousTab();
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest13);
			som=createChildTest(childTest13,extent,som);
			report=createChildTest(childTest13,extent,report);
			som.navigateToModifyInternetRFSProvisioningReport();
			Assert.assertTrue( report.getActualModifyInternetConvrgdNcToHpsa( accntNum,convergedHardWareSerialNo),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			report=createChildTest(childTest14,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustOrderReport();
			Assert.assertTrue( report.getActualModifyNcToJms_32(accntNum,convergedHardWareSerialNo),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( report.getActualNewNcToJms_32(accntNum,convergedHardWareSerialNo),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_32(accntNum,convergedHardWareSerialNo),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Modify_Internet75_to_150_XB6");
		}catch(Exception e)
		{
			log.error("Error in Modify_Internet75_to_150_XB6:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}

	@DataProvider
	public Object[][] getData9(){
		return Utility.getData(xls,"TC9");
	}

	@Test (dataProvider="getData9",description="Golden Suite",priority=8)
	@SuppressWarnings(value = { "rawtypes" })
	public void VoiceMail_DistinctiveRing_Converged(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering VoiceMail_DistinctiveRing_Converged");			
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			String phoneNbr=order.addServicePhone(phoneProduct);
			//order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("false",order.getAccountNumber());

			ExtentTest childTest8=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest8);
			com=createChildTest(childTest8,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest9=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			com.runJob();

			ExtentTest childTest10=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest11=extent.startTest("Add Distinctive Ring");
			ExtentTestManager.getTest().appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			order.selectVoiceMail();
			order.addDistinctiveRing();

			ExtentTest childTest12=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest13=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.reviewAndFinishOrder();      

			ExtentTest childTest14=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.openCOMOrderPage("false",accntNum);

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest16);
			som=createChildTest(childTest16,extent,som);
			report=createChildTest(childTest16,extent,report);
			som.navigateToModifyPhoneRFSOrderReport();
			Assert.assertTrue( report.getActualSOMModifyPhoneRFSOrderReport( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest17=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			report=createChildTest(childTest17,extent,report);
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport();
			Assert.assertTrue(report.getActualDistinctiveRingModifyCustOrderReport(),"Attributes are not validated successfully");       
			com.returnToPreviousPage();  
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue(report.getActualDoubleNcToBrm_37(accntNum,phoneNbr,convergedHardWareSerialNo),"Attributes are not validated successfully");  
			com.returnToPreviousPage();   
			com.navigateToNewCustOrderReport();
			Assert.assertTrue(report.getActualDistinctiveRingNewCustOrderReport(phoneNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving VoiceMail_DistinctiveRing_Converged");
		}catch(Exception e)
		{
			log.error("Error in VoiceMail_DistinctiveRing_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData10(){
		return Utility.getData(xls,"TC10");
	}

	@Test (dataProvider="getData10",description="Golden Suite",priority=9)
	@SuppressWarnings(value = { "rawtypes" })
	public void DPT_to_XB6_Converged(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering DPT_to_XB6_Converged");		
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String phoneSerialNo=data.get( "Phone_Hardware_Serial_No" );
			String phoneHardware=data.get( "Phone_hardware" );
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

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addPhoneHardware(phoneSerialNo,phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

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

			ExtentTest childTest13=extent.startTest("Remove Phone hardware");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.removePersonalPhoneHardware();

			ExtentTest childTest14=extent.startTest("Add Phone Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.selectConvergedHardware("Phone");

			ExtentTest childTest15=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.enterInstallationFee();
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
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest18);
			som=createChildTest(childTest18,extent,som);
			som.navigateToDisconnectPhoneRFSProvisioningReport();
			Assert.assertTrue( som.validateDisconnectRfsOrder_44(accntNum,phoneSerialNo),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest19=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			report=createChildTest(childTest19,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( report.getActualNcToInvTelephone_44( phoneNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyCustOrderReport();
			Assert.assertTrue( report.getActualNcToJms_44( accntNum,phoneNbr,phoneSerialNo,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_44( accntNum,phoneNbr,convergedHardWareSerialNo,phoneSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving DPT_to_XB6_Converged");
		}catch(Exception e)
		{
			log.error("Error in DPT_to_XB6_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}

	@DataProvider
	public Object[][] getData11(){
		return Utility.getData(xls,"TC11");
	}

	@Test (dataProvider="getData11",description="Golden Suite",priority=10)
	@SuppressWarnings(value = { "rawtypes" })
	public void PremiseMove_TriplePlay_XB6_BlueSky(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering PremiseMove_TriplePlay_XB6_BlueSky");		
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String tvBoxSerialNo=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNo=data.get("TV_Bluesky_Portal_Serial_No");
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
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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
			com.switchPreviousTab();

			ExtentTest childTest12=extent.startTest("Navigate to Vancover Location");
			ExtentTestManager.getTest().appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest13=extent.startTest("Add Phone and Internet");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.selectProduct();
			String phoneNbr2=order.addServicePhone(phoneProduct);

			ExtentTest childTest14=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.enterInstallationFee();
			order.techAppointment("Yes");
			order.techAppointmentWithGAP();

			ExtentTest childTest15=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.switchNextTab();
			order.openCOMOrderPage("false",accntNum);

			ExtentTest childTest17=extent.startTest("Mark Task Finished");
			ExtentTestManager.getTest().appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			com.navigateToModifyCustOrder();   
			com.setMarkFinishedTask();
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest18=extent.startTest("Send serial number");
			ExtentTestManager.getTest().appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToModifyConvergedCfsOrder();
			com.sendSerialNbrTask( convergedHardWareSerialNo );
			order.clickAccountLinkForOrders();

			ExtentTest childTest19=extent.startTest("Send TV serial number");
			ExtentTestManager.getTest().appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19,extent,order);
			com.navigateToModifyConvergedTvCfsOrder1();
			String tvSerialNbr1=com.getOldSerialNbr();
			com.sendSerialNbrTask(tvSerialNbr1);
			order.clickAccountLinkForOrders();
			com.navigateToModifyConvergedTvCfsOrder2();
			String tvSerialNbr2=com.getOldSerialNbr();
			com.sendSerialNbrTask( tvSerialNbr2 );
			order.clickAccountLinkForOrders();

			ExtentTest childTest20=extent.startTest("Mark Task Finished");
			ExtentTestManager.getTest().appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			com.navigateToModifyCustOrder();   
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest21=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			com.runJob();

			ExtentTest childTest22=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			com.verifyCLECRequestAfterJobRun();

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
			som.navigateToSuspendPhoneProvisioningReport();
			Assert.assertTrue(report.getActualPremiseMoveSuspendPhoneRFS_53( accntNum, phoneNbr ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToSuspendInternetProvisioningReport();
			Assert.assertTrue(report.getActualPremiseMoveSuspendInternetRFS_53( accntNum ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToSuspendTVProvisioningReport2();
			if(!report.getActualPremiseMoveSuspendTVRFS_53( accntNum ))
			{
				som.returnToPreviousPage();
				som.navigateToSuspendTVProvisioningReport1();
			}    
			Assert.assertTrue(report.getActualPremiseMoveSuspendTVRFS_53( accntNum ),"Attributes are not validated successfully");    
			som.returnToPreviousPage();    
			som.navigateToResumeInternetProvisioningReport();  
			Assert.assertTrue(report.getActualPremiseMoveResumeInternetRFS_53( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");           
			som.returnToPreviousPage();    
			som.navigateToResumeTVProvisioningReport1();
			Assert.assertTrue(report.getActualPremiseMoveResumeTVRFS_53( accntNum,tvBoxSerialNo,tvPortalSerialNo ),"Attributes are not validated successfully");        
			som.returnToPreviousPage();     
			som.navigateToResumeTVProvisioningReport2();
			Assert.assertTrue(report.getActualPremiseMoveResumeTVRFS_53( accntNum,tvBoxSerialNo,tvPortalSerialNo ),"Attributes are not validated successfully");        
			som.returnToPreviousPage(); 
			som.navigateToDisconnectPhoneRFSProvisioningReport();
			Assert.assertTrue(som.validateDisconnectPhoneRfsOrder( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();   
			som.navigateToNewPhoneRFSProvisioningReport1();
			Assert.assertTrue(report.getActualNewPhoneCnvrgdLineRFS( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();        
			som.navigateToNewPhoneRFSProvisioningReport2();
			Assert.assertTrue(report.getActualNewPhoneCnvrgdLineRFS_PremiseMove( accntNum,phoneNbr2,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();   

			ExtentTest childTest25=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest25);
			som=createChildTest(childTest25,extent,som);
			report=createChildTest(childTest25,extent,report); 
			com.navigateToCOMOrder();
			com.navigateToModifyCustomerOrderReport();
			Assert.assertTrue(report.getActualNcToJms_53( accntNum,convergedHardWareSerialNo,phoneNbr,phoneNbr2 ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToModifyBillingOrderReport();
			Assert.assertTrue(report.getActualNcToBrm_53( accntNum,phoneNbr2,convergedHardWareSerialNo,tvBoxSerialNo,tvPortalSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving PremiseMove_TriplePlay_XB6_BlueSky");
		}catch(Exception e)
		{
			log.error("Error in PremiseMove_TriplePlay_XB6_BlueSky:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData12(){
		return Utility.getData(xls,"TC12");
	}

	@Test (dataProvider="getData12",description="Golden Suite",priority=11)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_DoublePlay_Converged_PhInt(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_DoublePlay_Converged_PhInt");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			String phoneNbr=order.addServicePhone(phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest8=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest8);
			com=createChildTest(childTest8,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest9=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			com.runJob();

			ExtentTest childTest10=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest11=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			som=createChildTest(childTest11,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest12);
			som=createChildTest(childTest12,extent,som);
			report=createChildTest(childTest12,extent,report);
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewInternetCnvrgdNcToHpsa( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewPhoneRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewPhoneCnvrgdLineRFS( accntNum,phoneNbr,convergedHardWareSerialNo),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest13=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			report=createChildTest(childTest13,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateNewCustomerOrder_65( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_65( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_DoublePlay_Converged_PhInt");
		}catch(Exception e)
		{
			log.error("Error in Activate_DoublePlay_Converged_PhInt:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData13(){
		return Utility.getData(xls,"TC13");
	}

	@Test (dataProvider="getData13",description="Golden Suite",priority=12)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_Triple_Play_With_BlueSkyBox_Portal(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_Triple_Play_With_BlueSkyBox_Portal");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String tvBoxSerialNo=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNo=data.get("TV_Bluesky_Portal_Serial_No");
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
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest13);
			som=createChildTest(childTest13,extent,som);
			report=createChildTest(childTest13,extent,report);
			som.navigateToNewCustTVProvisioningReport1();
			Assert.assertTrue( report.getActualNewNcToHpsa1_67( accntNum, tvBoxSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport2();
			Assert.assertTrue( report.getActualNewNcToHpsa2_67(tvPortalSerialNo),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			report=createChildTest(childTest14,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateCustReport(accntNum,convergedHardWareSerialNo,tvBoxSerialNo,tvPortalSerialNo),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm( accntNum,phoneNbr,convergedHardWareSerialNo,tvBoxSerialNo,tvPortalSerialNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_Triple_Play_With_BlueSkyBox_Portal");
		}catch(Exception e)
		{
			log.error("Error in Activate_Triple_Play_With_BlueSkyBox_Portal:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData14(){
		return Utility.getData(xls,"TC14");
	}

	@Test (dataProvider="getData14",description="Golden Suite",priority=13)
	@SuppressWarnings(value = { "rawtypes" })
	public void Port_PhoneNumber_DistinctiveRing(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Port_PhoneNumber_DistinctiveRing");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNbr=data.get("Converged_Serial_No");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String convergedHardware=data.get( "Converged_hardware" );
			String techAppointment=data.get( "Tech_Appointment" );
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get( "SOM_Order_Count" )));
			int clecOrderCount=Math.round(Float.valueOf(data.get( "CLEC_Order_Count" ))); 

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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();       
			String portPhoneNbr=order.portPhoneNumber(phoneProduct);

			ExtentTest childTest4=extent.startTest("Port Distinctive Ring Phone Number");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			String distinctivePhoneNbr=order.portDistinctiveNumber();

			ExtentTest childTest5=extent.startTest("Add Port Phone Number");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest4,extent,order); 
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("false",order.getAccountNumber());

			ExtentTest childTest10=extent.startTest("LSC JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.runJobLSCResponse();   
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest11=extent.startTest("BLIF JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.runJobBLIFResponse();   

			ExtentTest childTest12=extent.startTest("E911 job run");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.runJob();
			com.navigateToCLECOrder();

			ExtentTest childTest13=extent.startTest("BLIF JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.runJobBLIFResponse();   

			ExtentTest childTest14=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.verifyCLECRequestAfterPortedNumber();            

			ExtentTest childTest15 = extent.startTest( "Navigate to New Phone product CFS order");
			ExtentTestManager.getTest().appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			com.navigateToCOMOrder();                 
			com.navigateToNewPhoneCfsOrder();
			com.navigateToServiceInformationTab();  
			Assert.assertTrue(com.portOptionDisplayed(),"Port Option is displaying for New Phone CFS order");
			order.clickAccountLinkForOrders();

			ExtentTest childTest16 = extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest16);
			com = createChildTest(childTest16,extent,com);
			som = createChildTest(childTest16,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest17=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest17);
			som=createChildTest(childTest17,extent,som);
			report=createChildTest(childTest17,extent,report);
			som.navigateToNewDistinctiveRingRfsOrderReport();   
			Assert.assertTrue(report.getActualNewDistinctiveRFSOrderReport_72(accntNum,portPhoneNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();  
			som.navigateToNewSomPhoneLineCFSOrderReport();
			Assert.assertTrue(report.getActualSOMPhoneCFSOrderReport_72(),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest18=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			report=createChildTest(childTest18,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue(com.validateNewCustomerOrder_72(accntNum, convergedHardWareSerialNbr, distinctivePhoneNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();                
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue(report.getActualNewCustNcToBrm72( accntNum,convergedHardWareSerialNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Port_PhoneNumber_DistinctiveRing");
		}catch(Exception e)
		{
			log.error("Error in Port_PhoneNumber_DistinctiveRing:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}
	}

	@DataProvider
	public Object[][] getData15(){
		return Utility.getData(xls,"TC15");
	}

	@Test (dataProvider="getData15",description="Golden Suite",priority=14)
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_PhoneFail(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering TechRetry_PhoneFail");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String convergedHardWareSerialNo=data.get("Converged_Serial_No");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String swapConvergedHarwareNo=data.get("Swap_Converged_Serial_No");
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

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");

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
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest8=extent.startTest("Navigate To Stub to fail phone");
			ExtentTestManager.getTest().appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToOeStubServerToFail("true");
			order.navigateToHpsaFail("Phone");

			ExtentTest childTest9=extent.startTest("Mark Finished Wait for Effective date task");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			order=createChildTest(childTest9,extent,order);
			com.switchPreviousTab();
			com.navigateToNewCustOrder();
			com.setMarkFinishedTask();
			com.verifySVGDiagram( "wait for provisioning" );
			order.clickAccountLinkForOrders();

			ExtentTest childTest10=extent.startTest("Send same serial number");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.navigateToNewConvergedCfsOrder();
			com.sendSerialNbrTask( convergedHardWareSerialNo );

			ExtentTest childTest11=extent.startTest("Navigate to Stub to up phone");
			ExtentTestManager.getTest().appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOeStubServerToPass("true","Phone");

			ExtentTest childTest12=extent.startTest("Send same serial number");
			ExtentTestManager.getTest().appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			com=createChildTest(childTest12,extent,com);
			com.switchPreviousTab();
			order.clickAccountLinkForOrders();
			com.navigateToNewConvergedCfsOrder();
			com.sendSerialNbrTask( convergedHardWareSerialNo );

			ExtentTest childTest13=extent.startTest("Send Different serial number");
			ExtentTestManager.getTest().appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			com=createChildTest(childTest13,extent,com);
			order.clickAccountLinkForOrders();
			com.navigateToNewConvergedCfsOrder();
			com.sendSerialNbrTask( swapConvergedHarwareNo );
			order.clickAccountLinkForOrders();

			ExtentTest childTest14=extent.startTest("Mark Finished Wait For Tech Confirm");
			ExtentTestManager.getTest().appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			com.navigateToNewCustOrder();
			com.setMarkFinishedTask();
			order.clickAccountLinkForOrders();

			ExtentTest childTest15=extent.startTest("CLEC REQ BEFORE JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest16=extent.startTest("JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			com.runJob();

			ExtentTest childTest17=extent.startTest("CLEC REQ AFTER JOB RUN");
			ExtentTestManager.getTest().appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest18=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			som=createChildTest(childTest18,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest19);
			som=createChildTest(childTest19,extent,som);
			report=createChildTest(childTest19,extent,report);
			som.navigateToDisconnectInternetRFSProvisioningReport();
			Assert.assertTrue( report.getActualDisconnectInternetNcToHpsa_79( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectPhoneRFSProvisioningReport();
			Assert.assertTrue( som.validateDisconnectPhoneRfsOrder(accntNum,phoneNbr,convergedHardWareSerialNo),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToModifyInternetRFSProvisioningReport();
			Assert.assertTrue( report.getActualModifyInternetConvrgdNcToHpsa( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewInternetCnvrgdNcToHpsa( accntNum,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewInternetRFSProvisioningReport2();  
			Assert.assertTrue( report.getActualNewInternetCnvrgdNcToHpsa( accntNum,swapConvergedHarwareNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewPhoneRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewPhoneCnvrgdLineRFS( accntNum,phoneNbr,convergedHardWareSerialNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewPhoneRFSProvisioningReport2();
			Assert.assertTrue( report.getActualNewPhoneCnvrgdLineRFS( accntNum,phoneNbr,swapConvergedHarwareNo ),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			report=createChildTest(childTest20,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateCustomerReport_79( accntNum,phoneNbr,swapConvergedHarwareNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNewNcToBrm_79( accntNum,phoneNbr, swapConvergedHarwareNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving TechRetry_PhoneFail");
		}catch(Exception e)
		{
			log.error("Error in TechRetry_PhoneFail:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData16(){
		return Utility.getData(xls,"TC16");
	}

	@Test (dataProvider="getData16",description="Priority-1",priority=15)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_SinglePlay_Internet(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_SinglePlay_Internet");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String internetProduct=data.get("Internet_Product");
			String internetHardwareSerialNbr=data.get( "Internet_Hardware_Serial_No" );
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
			//order.deleteConvergedHardware();
			order.addInternetHardware( internetHardwareSerialNbr,"Hitron" );

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

			ExtentTest childTest8=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest8);
			com=createChildTest(childTest8,extent,com);
			som=createChildTest(childTest8,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest9=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest9);
			som=createChildTest(childTest9,extent,som);
			report=createChildTest(childTest9,extent,report);
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue(report.getActualNewInternetCnvrgdNcToHpsa_60( accountNum,internetHardwareSerialNbr ),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewWifiRFSProvisioningReport();
			Assert.assertTrue(report.getActualNewWifiRfsNcToHpsa_60( accountNum),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest10=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			report=createChildTest(childTest10,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue(report.getActualNewCustNcToJms_60( accountNum,internetHardwareSerialNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue(report.getActualNcToBrm_SinglePlay60( accountNum),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_SinglePlay_Internet");
		}catch(Exception e)
		{
			log.error("Error in Activate_SinglePlay_Internet:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData17(){
		return Utility.getData(xls,"TC17");
	}

	@Test (dataProvider="getData17",description="Priority-1",priority=16)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_Internet_with_XB6_converged(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_Internet_with_XB6_converged");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get( "Converged_hardware" );
			String convergedHardWareSerialNbr=data.get("Converged_Serial_No");
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

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

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

			ExtentTest childTest8=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest8);
			com=createChildTest(childTest8,extent,com);
			som=createChildTest(childTest8,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest9=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest9);
			som=createChildTest(childTest9,extent,som);
			report=createChildTest(childTest9,extent,report);
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewInternetProvisioningRFSNcToHpsa_61( accountNum, convergedHardWareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest10=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			report=createChildTest(childTest10,extent,report);
			com.navigateToCOMOrder();
			com. navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateNewCustomerOrder_61(accountNum,convergedHardWareSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com. navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_61( accountNum, convergedHardWareSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_Internet_with_XB6_converged");
		}catch(Exception e)
		{
			log.error("Error in Activate_Internet_with_XB6_converged:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData18(){
		return Utility.getData(xls,"TC18");
	}

	@Test (dataProvider="getData18",description="Priority-1",priority=17)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_TV_With_DCT_700_HD_Guide(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_TV_With_DCT_700_HD_Guide");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String tvProduct=data.get("TV_Product");
			String tvHardwareSerialNbr=data.get("TV_SD_Serial_No");
			String tvHardwareDCX3510SlNbr=data.get("TV_DCX3510_HDGuide_Sl_No");
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

			ExtentTest childTest3=extent.startTest("Add DCT and DCX 3510M TV Hardware");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServiceTV( tvProduct );
			order.addTVHardwareDCX3510(tvHardwareSerialNbr,tvHardwareDCX3510SlNbr);

			ExtentTest childTest4=extent.startTest("Tech Appointment");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.enterInstallationFee();
			order.techAppointment(techAppointment);

			ExtentTest childTest5=extent.startTest("Review and Finish");
			ExtentTestManager.getTest().appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6=extent.startTest("Navigate To COM Order");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			String accountNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest7=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest7);
			com=createChildTest(childTest7,extent,com);
			som=createChildTest(childTest7,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest8=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest8);
			som=createChildTest(childTest8,extent,som);
			report=createChildTest(childTest8,extent,report);
			som.navigateToNewCustTVProvisioningReport1();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_62( accountNum, tvHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport2();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_62( accountNum, tvHardwareDCX3510SlNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest9=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			report=createChildTest(childTest9,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateNewCustomerOrder_62( accountNum,tvHardwareSerialNbr,tvHardwareDCX3510SlNbr ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com. navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_62(accountNum, tvHardwareSerialNbr, tvHardwareDCX3510SlNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_TV_With_DCT_700_HD_Guide");
		}catch(Exception e)
		{
			log.error("Error in Activate_TV_With_DCT_700_HD_Guide:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData19(){
		return Utility.getData(xls,"TC19");
	}

	@Test (dataProvider="getData19",description="Priority-1",priority=18)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_TV_With_BlueSky_Xid_portals(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_TV_With_BlueSky_Xid_portals");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String tvProduct=data.get("TV_Product");
			String tvBoxSerialNbr=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNbr=data.get("TV_Bluesky_Portal_Serial_No");
			String internetProduct=data.get("Internet_Product");
			String convergedHardwareSerialNbr=data.get( "Converged_Serial_No");
			String convergedHardware=data.get( "Converged_hardware");
			String techAppointment = data.get( "Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

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

			ExtentTest childTest3=extent.startTest("Add TV");
			ExtentTestManager.getTest().appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectProduct();
			order.addServiceTV(tvProduct);

			ExtentTest childTest4 = extent.startTest("Add Digital Channel Section & Theme");
			ExtentTestManager.getTest().appendChild(childTest4);
			order = createChildTest(childTest4,extent,order);
			order.addDigitalChannelSectionTheme();

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			ExtentTestManager.getTest().appendChild(childTest5);
			order = createChildTest(childTest5,extent,order);
			order.addServiceTVWithBlueSky(tvProduct, tvBoxSerialNbr,tvPortalSerialNbr);

			ExtentTest childTest6=extent.startTest("Add Internet");
			ExtentTestManager.getTest().appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest7=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.addConvergedHardware(convergedHardwareSerialNbr);
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
			String accountNum=order.getAccountNumber();
			order.openCOMOrderPage("true",order.getAccountNumber());

			ExtentTest childTest11=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			som=createChildTest(childTest11,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest12);
			som=createChildTest(childTest12,extent,som);
			report=createChildTest(childTest12,extent,report);
			som.navigateToNewCustTVProvisioningReport1();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_63( accountNum, tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport2();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_63( accountNum, tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest13=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			report=createChildTest(childTest13,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateNewCustomerOrder_63(accountNum, convergedHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com. navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_63(accountNum, tvBoxSerialNbr, convergedHardwareSerialNbr, tvPortalSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_TV_With_BlueSky_Xid_portals");
		}catch(Exception e)
		{
			log.error("Error in Activate_TV_With_BlueSky_Xid_portals:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

	@DataProvider
	public Object[][] getData20(){
		return Utility.getData(xls,"TC20");
	}

	@Test (dataProvider="getData20",description="Priority-1",priority=19)
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_InternetConverged_TV_Bluesky(Hashtable<String,String> data) throws Exception
	{
		try
		{
			log.debug("Entering Activate_InternetConverged_TV_Bluesky");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}

			String internetProduct = data.get("Internet_Product");
			String convergedHardwareSerialNbr=data.get( "Converged_Serial_No");
			String convergedHardware=data.get( "Converged_hardware");
			String tvProduct=data.get("TV_Product");
			String tvBoxSerialNbr=data.get("TV_Bluesky_Box_Serial_No");
			String tvPortalSerialNbr=data.get("TV_Bluesky_Portal_Serial_No");
			String techAppointment = data.get( "Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

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
			order.addServiceInternet( internetProduct );

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			ExtentTestManager.getTest().appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add Service TV");
			ExtentTestManager.getTest().appendChild(childTest5);
			order = createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV With BlueSky");
			ExtentTestManager.getTest().appendChild(childTest6);
			order = createChildTest(childTest6,extent,order);
			order.addServiceTVWithBlueSky(tvProduct, tvBoxSerialNbr,tvPortalSerialNbr);

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

			ExtentTest childTest10=extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest11=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest11);
			som=createChildTest(childTest11,extent,som);
			report=createChildTest(childTest11,extent,report);
			som.navigateToNewInternetRFSProvisioningReport1();
			Assert.assertTrue( report.getActualNewInternetProvisioningRFSNcToHpsa_66( accountNum, convergedHardwareSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport1();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_66( accountNum, tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToNewCustTVProvisioningReport2();
			Assert.assertTrue( report.getActualNewTVProvisioningRFSNcToHpsa_66( accountNum, tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest12=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			report=createChildTest(childTest12,extent,report);
			com.navigateToCOMOrder();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateNewCustomerOrder_66(accountNum, tvBoxSerialNbr, tvPortalSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com. navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNcToBrm_66(accountNum, convergedHardwareSerialNbr,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving Activate_InternetConverged_TV_Bluesky");
		}catch(Exception e)
		{
			log.error("Error in Activate_InternetConverged_TV_Bluesky:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}

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
			//order.selectConvergedHardware("Phone");

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
			order.openCOMOrderPage("true",order.getAccountNumber());

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
			order.navigateToCustOrderPage("true",accntNum);

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
			order.switchPreviousTab();

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			ExtentTestManager.getTest().appendChild(childTest20);
			com = createChildTest(childTest20,extent,com);
			som = createChildTest(childTest20,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.navigateToSOMOrder();
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21=extent.startTest("Verify SOM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest21);
			som=createChildTest(childTest21,extent,som);
			report=createChildTest(childTest21,extent,report);
			som.navigateToDisconnectTVRFSProvisioningReport1();
			Assert.assertTrue(report.getActualDisconnectTVNcToHpsa_11(accntNum,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();
			som.navigateToDisconnectTVRFSProvisioningReport2();
			Assert.assertTrue(report.getActualDisconnectTVNcToHpsa_11(accntNum,tvBoxSerialNbr,tvPortalSerialNbr),"Attributes are not validated successfully");
			som.returnToPreviousPage();

			ExtentTest childTest22=extent.startTest("Verify COM Integration Report");
			ExtentTestManager.getTest().appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			report=createChildTest(childTest22,extent,report);
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
	
	public static void main (String args[])
	{
		InputStream log4j = GoldenSuite.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}


}	





