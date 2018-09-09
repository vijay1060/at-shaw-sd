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

public class TechRetry_PhoneFail extends SeleniumTestUp {

	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
			com.navigateToNewCustBillingOrderReport();
			Assert.assertTrue( report.getActualNewNcToBrm_79( accntNum,phoneNbr, swapConvergedHarwareNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();
			com.navigateToNewCustOrderReport();
			Assert.assertTrue( com.validateCustomerReport_79( accntNum,phoneNbr,swapConvergedHarwareNo ),"Attributes are not validated successfully");
			com.returnToPreviousPage();

			log.debug("Leaving TechRetry_PhoneFail");
		}catch(Exception e)
		{
			log.error("Error in TechRetry_PhoneFail:" + e.getMessage());
			test.log(LogStatus.FAIL,"Test Failed",e.getMessage());
		}

	}
}








