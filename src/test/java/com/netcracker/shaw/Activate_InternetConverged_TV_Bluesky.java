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

public class Activate_InternetConverged_TV_Bluesky extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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

}

