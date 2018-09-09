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

public class Modify_Internet75_to_150_XB6 extends SeleniumTestUp {

	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
}

