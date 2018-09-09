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

public class Activate_TV_With_BlueSky_Xid_portals extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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

}

