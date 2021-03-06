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

public class Activate_SinglePlay_Internet extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
			order.addInternetHardware( internetHardwareSerialNbr,"Hitron" );
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
}

