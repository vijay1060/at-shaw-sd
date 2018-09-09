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

public class Activate_TV_With_DCT_700_HD_Guide extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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

}

