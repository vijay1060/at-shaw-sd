package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.*;

import org.apache.log4j.Logger;
import org.testng.Assert;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class SOMOrderPage<SOMOrdersPageElement> extends BasePage {
	public SOMOrderPage(ExtentTest test) {
		super(test);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}
	
	Logger log = Logger.getLogger(SOMOrderPage.class);

	public void verifyRecordsCountInSOMOrder( int expectedRows )
	{
		try
		{
			log.debug("Entering verifyRecordsCountInSOMOrder");
			click( SOM_ORDER_TAB );
			wait(3);
			int record = countRowsinTable( ROWS_IN_SOM_ORDER );
			while ( !(record == expectedRows) )
			{
				refreshPage();
				record = countRowsinTable( ROWS_IN_SOM_ORDER );
			}
			if ( Utility.compareIntegerVals( record,
					expectedRows ) )
			{
				test.log( LogStatus.PASS,"Record count match","Total Number of records in SOM page is: " + record );
				test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			}
			wait(3);
			log.debug("Leaving verifyRecordsCountInSOMOrder");
		}catch(Exception e)
		{
			log.error("Error in verifyRecordsCountInSOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log( LogStatus.INFO,"Record count match","Expected Records not displaying in SOM order" );
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}


	public SOMOrderPage navigateToSOMOrder() 
	{
		try
		{
			log.debug("Entering navigateToSOMOrder");
			click(SOM_ORDER_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			refreshPage();
			takeScreenShot("SOM ORDER");
			log.debug("Leaving navigateToSOMOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToSOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return new SOMOrderPage(test);
	}

	public void navigateToResumeTVProvisioningReport1()
	{
		try
		{
			log.debug("Entering navigateToResumeTVProvisioningReport1");
			click( RESUME_TV_PROVISIONING_RFS_ORDER1 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToResumeTVProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToResumeTVProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToResumeTVProvisioningReport2()
	{
		try
		{
			log.debug("Entering navigateToResumeTVProvisioningReport2");
			click( RESUME_TV_PROVISIONING_RFS_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToResumeTVProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToResumeTVProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToResumeInternetProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToResumeInternetProvisioningReport");
			click( RESUME_INTERNET_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToResumeInternetProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToResumeInternetProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewCustTVProvisioningReport1() 
	{
		try
		{
			log.debug("Entering navigateToNewCustTVProvisioningReport1");
			click(NEW_TV_PROVISIONING_RFS_ORDER_1);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewCustTVProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustTVProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewCustTVProvisioningReport2() 
	{
		try
		{
			log.debug("Entering navigateToNewCustTVProvisioningReport2");
			click(NEW_TV_PROVISIONING_RFS_ORDER_2);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewCustTVProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustTVProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewCustTVProvisioningReport3() 
	{
		try
		{
			log.debug("Entering navigateToNewCustTVProvisioningReport3");
			click(NEW_TV_PROVISIONING_RFS_ORDER_3);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewCustTVProvisioningReport3");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustTVProvisioningReport3:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewCustTVProvisioningReport6() 
	{
		try
		{
			log.debug("Entering navigateToNewCustTVProvisioningReport6");
			click(NEW_TV_PROVISIONING_RFS_ORDER_6);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewCustTVProvisioningReport6");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustTVProvisioningReport6:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToSuspendPhoneProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToSuspendPhoneProvisioningReport");
			click( SUSPEND_PHONE_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToSuspendPhoneProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToSuspendPhoneProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToSuspendInternetProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToSuspendInternetProvisioningReport");
			click( SUSPEND_INTERNET_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToSuspendInternetProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToSuspendInternetProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToSuspendTVProvisioningReport1()
	{
		try
		{
			log.debug("Entering navigateToSuspendTVProvisioningReport1");
			click( SUSPEND_TV_PROVISIONING_RFS_ORDER1 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToSuspendTVProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToSuspendTVProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToSuspendTVProvisioningReport2()
	{
		try
		{
			log.debug("Entering navigateToSuspendTVProvisioningReport2");
			click( SUSPEND_TV_PROVISIONING_RFS_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToSuspendTVProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToSuspendTVProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToDisconnectPhoneRFSProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToDisconnectPhoneRFSProvisioningReport");
			click( DISCONNECT_PHONE_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToDisconnectPhoneRFSProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToDisconnectPhoneRFSProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToDisconnectInternetRFSProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToDisconnectInternetRFSProvisioningReport");
			click( DISCONNECT_INTERNET_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToDisconnectInternetRFSProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToDisconnectInternetRFSProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToDisconnectTVRFSProvisioningReport1()
	{
		try
		{
			log.debug("Entering navigateToDisconnectTVRFSProvisioningReport1");
			click( DISCONNECT_TV_PROVISIONING_RFS_ORDER1 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToDisconnectTVRFSProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToDisconnectTVRFSProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToDisconnectTVRFSProvisioningReport2()
	{
		try
		{
			log.debug("Entering navigateToDisconnectTVRFSProvisioningReport2");
			click( DISCONNECT_TV_PROVISIONING_RFS_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToDisconnectTVRFSProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToDisconnectTVRFSProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewInternetRFSProvisioningReport1()
	{
		try
		{
			log.debug("Entering navigateToNewInternetRFSProvisioningReport1");
			click( NEW_INTERNET_PROVISIONING_RFS_ORDER1 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewInternetRFSProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewInternetRFSProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewInternetRFSProvisioningReport2()
	{
		try
		{
			log.debug("Entering navigateToNewInternetRFSProvisioningReport2");
			click( NEW_INTERNET_PROVISIONING_RFS_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewInternetRFSProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewInternetRFSProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewInternetRFSProvisioningReport4()
	{
		try
		{
			log.debug("Entering navigateToNewInternetRFSProvisioningReport4");
			click( NEW_INTERNET_PROVISIONING_RFS_ORDER4 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewInternetRFSProvisioningReport4");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewInternetRFSProvisioningReport4:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToCancelWifiRFSProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToCancelWifiRFSProvisioningReport");
			click( CANCEL_WIFI_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCancelWifiRFSProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToCancelWifiRFSProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyInternetRFSProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToModifyInternetRFSProvisioningReport");
			click( MODIFY_INTERNET_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyInternetRFSProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyInternetRFSProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyPhoneRFSOrderReport()
	{
		try
		{
			log.debug("Entering navigateToModifyPhoneRFSOrderReport");
			click( MODIFY_PHONE_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyPhoneRFSOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyPhoneRFSOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewPhoneRFSProvisioningReport1()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneRFSProvisioningReport1");
			click( NEW_PHONE_PROVISIONING_RFS_ORDER1 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewPhoneRFSProvisioningReport1");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneRFSProvisioningReport1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewPhoneRFSProvisioningReport2()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneRFSProvisioningReport2");
			click( NEW_PHONE_PROVISIONING_RFS_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewPhoneRFSProvisioningReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneRFSProvisioningReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewPhoneRFSProvisioningReport4()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneRFSProvisioningReport4");
			click( NEW_PHONE_PROVISIONING_RFS_ORDER4 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewPhoneRFSProvisioningReport4");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneRFSProvisioningReport4:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewWifiRFSProvisioningReport()
	{
		try
		{
			log.debug("Entering navigateToNewWifiRFSProvisioningReport");
			click( NEW_WIFI_PROVISIONING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving navigateToNewWifiRFSProvisioningReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewWifiRFSProvisioningReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	//kirti - 72
	public void navigateToNewDistinctiveRingRfsOrderReport()
	{
		try
		{
			log.debug("Entering navigateToNewDistinctiveRingRfsOrderReport");
			click( NEW_DISTINCTIVE_RING_RFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving navigateToNewDistinctiveRingRfsOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewDistinctiveRingRfsOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewSomPhoneLineCFSOrderReport()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneLineCFSOrderReport");
			click( NEW_SOM_PHONE_LINE_CFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving navigateToNewPhoneLineCFSOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneLineCFSOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public boolean validateSuspendTriplePlayAllParams(String acctNbr) 
	{
		boolean validate = false;
		log.debug("Entering validateSuspendTriplePlayAllParams");
		IntegrationReports ir = new IntegrationReports(test);
		if ((ir.getExpectedSuspendXMLToString(acctNbr).equals(ir.getActualSuspendXmlToString()))) {
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateSuspendTriplePlayAllParams");
		return validate;
	}
	
	public boolean validateDisconnectRfsOrder_44( String acctNbr,String phoneSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateDisconnectRfsOrder_44");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualDisconnectPhoneNcToHpsa1_44( acctNbr,phoneSerialNbr )
				&& ir.getActualDisconnectPhoneNcToHpsa2_44( acctNbr,phoneSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateDisconnectRfsOrder_44");
		return validate;
	}
    
	public boolean validateDisconnectInternetRfsOrder( String acctNbr,String convergedSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateDisconnectInternetRfsOrder");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualDisconnectInternetNcToHpsa1( acctNbr,convergedSerialNbr )
				|| ir.getActualDisconnectInternetNcToHpsa2( acctNbr,convergedSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateDisconnectInternetRfsOrder");
		return validate;
	}
    

	public boolean validateDisconnectPhoneRfsOrder( String acctNbr, String phoneNbr,String convergedSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateDisconnectPhoneRfsOrder");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualDisconnectPhoneNcToHpsa1( acctNbr, phoneNbr,convergedSerialNbr )
				|| ir.getActualDisconnectPhoneNcToHpsa2( acctNbr,convergedSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateDisconnectPhoneRfsOrder");
		return validate;
	}
    
	public boolean validateDisconnectInternetRfsOrder_23( String acctNbr,String internetSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateDisconnectInternetRfsOrder_23");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualDisconnectInternetNcToHpsa1_23( acctNbr,internetSerialNbr )
				|| ir.getActualDisconnectInternetNcToHpsa2_23( acctNbr,internetSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateDisconnectInternetRfsOrder_23");
		return validate;
	}
    
	public boolean validateDisconnectPhoneRfsOrder_23( String acctNbr, String phoneNbr,String phoneSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateDisconnectPhoneRfsOrder_23");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualDisconnectPhoneNcToHpsa1_23( acctNbr, phoneNbr,phoneSerialNbr )
				|| ir.getActualDisconnectPhoneNcToHpsa2_23( acctNbr,phoneSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
		log.debug("Leaving validateDisconnectPhoneRfsOrder_23");
		return validate;
	}

}
