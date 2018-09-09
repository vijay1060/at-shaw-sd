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

public class Port_PhoneNumber_DistinctiveRing extends SeleniumTestUp
{


	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
}