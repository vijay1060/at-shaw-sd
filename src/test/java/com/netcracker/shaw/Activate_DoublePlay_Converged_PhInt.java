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

public class Activate_DoublePlay_Converged_PhInt extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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

}

