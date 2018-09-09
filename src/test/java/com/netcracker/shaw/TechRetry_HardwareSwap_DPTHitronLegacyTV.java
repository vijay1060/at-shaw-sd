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

public class TechRetry_HardwareSwap_DPTHitronLegacyTV extends SeleniumTestUp 
{
	public static ExtentReports extent = ExtentReportManager.getInstance();
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	IntegrationReports report=null;

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
}

