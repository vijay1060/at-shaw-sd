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

public class PremiseMove_TriplePlay_XB6_BlueSky extends SeleniumTestUp
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
}
