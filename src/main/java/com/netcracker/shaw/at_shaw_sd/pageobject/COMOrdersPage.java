package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.COMOrdersPageElement.*;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.INTEGRATION_REPORT;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.ORDER_PARAMETER_TAB;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.SOM_ORDER_TAB;

import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

//import com.aventstack.extentreports.Status;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class COMOrdersPage<COMOrdersPageElement> extends BasePage {
	public COMOrdersPage(ExtentTest test) {
		super(test);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	JavascriptExecutor javascript = ((JavascriptExecutor) driver);
	boolean isAllOrdersPresent = false;
	boolean clecKOrderStatus = false;
	boolean COMOrderStatus = false;
	
	Logger log = Logger.getLogger(COMOrdersPage.class);

	public COMOrdersPage navigateToCOMOrder() 
	{
		try
		{
			log.debug("Entering navigateToCOMOrder");
			click(COM_ORDER_TAB);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			takeScreenShot("COM ORDER");
			log.debug("Leaving navigateToCOMOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToCOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return new COMOrdersPage(test);
	}
	
	public COMOrdersPage navigateToCLECOrder() 
	{
		try
		{
			log.debug("Entering navigateToCLECOrder");
			click(CLEC_REQUEST_TAB);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			takeScreenShot("CLEC ORDER");
			log.debug("Leaving navigateToCLECOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToCLECOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public void verifyCLECRequestBeforeJobRun() 
	{
		try
		{
			log.debug("Entering verifyCLECRequestBeforeJobRun");
			click(CLEC_REQUEST_TAB);
			wait(2);
			String blif_Req_Status = getText(BLIF_REQUEST_STATUS);
			String E911ADD_Status = getText(E911ADD_STATUS);
			if (isDisplayed(BLIF_REQUEST) && isDisplayed(E911ADD) && blif_Req_Status.equalsIgnoreCase("confirmed")
					&& E911ADD_Status.equalsIgnoreCase("In Process")) {
				test.log(LogStatus.PASS, "CLEC order Status", "CLEC order status matches");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} 
			takeScreenShot();
			log.debug("Leaving verifyCLECRequestBeforeJobRun");
		}catch(Exception e)
		{
			log.error("Error in verifyCLECRequestBeforeJobRun:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "CLEC order Status", "CLEC order status does not match");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void verifyCLECRequestAfterJobRun() 
	{
		try
		{
			log.debug("Entering verifyCLECRequestAfterJobRun");
			click(CLEC_REQUEST_TAB);
			wait(2);
			String blif_Req_Status = getText(BLIF_REQUEST_STATUS);
			String E911ADD_Status = getText(E911ADD_STATUS);
			if (isDisplayed(BLIF_REQUEST) && isDisplayed(E911ADD) && blif_Req_Status.equalsIgnoreCase("confirmed")
					&& E911ADD_Status.equalsIgnoreCase("Confirmed")) {
				test.log(LogStatus.PASS, "CLEC order Status", "CLEC order status matches");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			} 
			takeScreenShot();
			log.debug("Leaving verifyCLECRequestAfterJobRun");
		}catch(Exception e)
		{
			log.error("Error in verifyCLECRequestAfterJobRun:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "CLEC order Status", "CLEC order status does not match");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void runJob() 
	{
		try
		{
			log.debug("Entering runJob");
			navigate(Utility.getValueFromPropertyFile("job_monitor_url"));
			wait(3);
			click(E911_RESPONSE);
			wait(2);
			javascript.executeScript("window.scrollBy(0,-3000)", "");
			click(RUN_JOB_BUTTON);
			wait(2);
			refreshPage();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			returnToPreviousPage();
			log.debug("Leaving runJob");
		}catch(Exception e)
		{
			log.error("Error in runJob:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void suspendOrder() 
	{
		try
		{
			log.debug("Entering suspendOrder");
			click(SUSPENSION_HISTORY);
			wait(2);
			click(SUSPEND_BUTTON);
			wait(3);
			click(SUSPEND_TV);
			wait(2);
			inputText(SUSPENSION_NOTES, "Suspend");
			wait(1);
			inputText(AUTHORIZED_BY, "ATTest");
			wait(1);
			click(SUBMIT_BUTTON);
			wait(3);
			click(RETURN_TO_SUSPENSION_HISTORY);
			wait(3);
			takeScreenShot();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving suspendOrder");
		}catch(Exception e)
		{
			log.error("Error in suspendOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void resumeOrder() 
	{
		try
		{
			log.debug("Entering resumeOrder");
			click(SUSPENSION_HISTORY_RECORD);
			wait(2);
			click(RESUME_BUTTON);
			wait(2);
			inputText(RESUME_NOTE, "Resume");
			wait(1);
			inputText(RESUME_AUTHORIZED, "ATTest");
			wait(1);
			click(SUBMIT_BUTTON);
			wait(2);
			click(RETURN_TO_SUSPENSION_HISTORY_RECORD);
			wait(2);
			click(LINK_TO_COM_PAGE);
			wait(3);
			click(SUSPENSION_HISTORY);
			wait(2);
			getText(SUSPENSION_STATUS);
			wait(1);
			takeScreenShot();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving resumeOrder");
		}catch(Exception e)
		{
			log.error("Error in resumeOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public String suspendStatus() 
	{
		log.debug("Entering suspendStatus");
		String suspensionStatus="";
		try
		{
			suspensionStatus=getText(SUSPENSION_STATUS);
			if(suspensionStatus.equalsIgnoreCase("Active"))
			{
				test.log(LogStatus.PASS, "Status", "Status is : " + suspensionStatus);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving suspendStatus");
		}catch(Exception e)
		{
			log.error("Error in suspendStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return suspensionStatus;

	}

	public void verifyRecordsCountInCOMOrder(int expectedRows) 
	{
		log.debug("Entering verifyRecordsCountInCOMOrder");
		try
		{
			click(COM_ORDER_TAB);
			wait(3);
			refreshPage();
			takeScreenShot();
			int record = countRowsinTable(ROWS_IN_COM_ORDER);

			if (Utility.compareIntegerVals(record, expectedRows)) {
				test.log(LogStatus.PASS, "Record count match", "Total Number of records in COM page is: " + record);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving verifyRecordsCountInCOMOrder");
		}catch(Exception e)
		{
			log.error("Error in verifyRecordsCountInCOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "Record count match", "Expected Records not displaying in COM order");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		log.debug("Leaving verifyRecordsCountInCOMOrder");
	}

	public boolean isPresentSuspendResumeOrderinCOM() 
	{
		try
		{
			log.debug("Entering isPresentSuspendResumeOrderinCOM");
			boolean orderLinkPresent = isDisplayed(RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER)
					&& isDisplayed(RESUME_TV_PRODUCT_IMMEDIATE_ORDER) && isDisplayed(ROOT_RESUME_IMMEDIATE_ORDER)
					&& isDisplayed(ROOT_SUSPEND_IMMEDIATE_ORDER) && isDisplayed(SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER)
					&& isDisplayed(SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER);
			if (orderLinkPresent && checkStatusComplete(RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER_STATUS)
					&& checkStatusComplete(RESUME_TV_PRODUCT_IMMEDIATE_ORDER_STATUS)
					&& checkStatusComplete(ROOT_RESUME_IMMEDIATE_ORDER_STATUS)
					&& checkStatusComplete(ROOT_SUSPEND_IMMEDIATE_ORDER_STATUS)
					&& checkStatusComplete(SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER_STATUS)
					&& checkStatusComplete(SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER_STATUS)) {
				COMOrderStatus = true;
			}
			takeScreenShot("COM Order Suspend Status");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving isPresentSuspendResumeOrderinCOM");
		}catch(Exception e)
		{
			log.error("Error in isPresentSuspendResumeOrderinCOM:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return COMOrderStatus;

	}

	public void navigateToNewPhoneCfsOrder()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneCfsOrder");
			click( NEW_PHONELINE_CFS_ORDER );
			wait(3);
			log.debug("Leaving navigateToNewPhoneCfsOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneCfsOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToServiceInformationTab()
	{
		try
		{
			log.debug("Entering navigateToServiceInformationTab");
			wait(3);
			click(SERVICE_INFORMATION_TAB);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToServiceInformationTab");
		}catch(Exception e)
		{
			log.error("Error in navigateToServiceInformationTab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public boolean portOptionDisplayed()
	{
		boolean portOption=false;
		try
		{
			log.debug("Entering portOptionDisplayed");
			if(isDisplayed(PORT_OPTION))
			{
				portOption=true;
			}

			log.debug("Port Option is displayed:" + portOption );
			log.debug("Leaving portOptionDisplayed");
		}catch(Exception e)
		{
			portOption=true;
			log.error("Error in portOptionDisplayed:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return portOption;
	} 

	public void navigateToNewCustOrder()
	{
		try
		{
			log.debug("Entering navigateToNewCustOrder");
			click( NEW_CUSTOMER_ORDER );
			wait(2);
			log.debug("Leaving navigateToNewCustOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewInternetHardwareCfsOrder3()
	{
		try
		{
			log.debug("Entering navigateToNewInternetHardwareCfsOrder3");
			click( NEW_INTERNET_HARDWARE_CFS_ORDER3 );
			wait(2);
			log.debug("Leaving navigateToNewInternetHardwareCfsOrder3");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewInternetHardwareCfsOrder3:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewInternetHardwareCfsOrder2()
	{
		try
		{
			log.debug("Entering navigateToNewInternetHardwareCfsOrder2");
			click( NEW_INTERNET_HARDWARE_CFS_ORDER2 );
			wait(2);
			log.debug("Leaving navigateToNewInternetHardwareCfsOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewInternetHardwareCfsOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewTVHardwareCfsOrder2()
	{
		try
		{
			log.debug("Entering navigateToNewTVHardwareCfsOrder2");
			click( NEW_TV_HARDWARE_CFS_ORDER2 );
			wait(2);
			log.debug("Leaving navigateToNewTVHardwareCfsOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewTVHardwareCfsOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyCustOrder()
	{
		try
		{
			log.debug("Entering navigateToModifyCustOrder");
			click( MODIFY_CUSTOMER_ORDER );
			wait(2);
			log.debug("Leaving navigateToModifyCustOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyCustOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
    
	public void navigateToModifyCustOrder2()
	{
		try
		{
			log.debug("Entering navigateToModifyCustOrder2");
			click( MODIFY_CUSTOMER_ORDER2 );
			wait(2);
			log.debug("Leaving navigateToModifyCustOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyCustOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyConvergedCfsOrder() 
	{
		try
		{
			log.debug("Entering navigateToModifyConvergedCfsOrder");
			click(MODIFY_CONVERGRED_HARDWARE_CFS_ORDER_LINK);
			wait(3);
			log.debug("Leaving navigateToModifyConvergedCfsOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyConvergedCfsOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyConvergedCfsOrder1() 
	{
		try
		{
			log.debug("Entering navigateToModifyConvergedCfsOrder1");
			click(MODIFY_TV_HARDWARE_CFS_ORDER_1);
			wait(3);
			log.debug("Leaving navigateToModifyConvergedCfsOrder1");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyConvergedCfsOrder1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyConvergedCfsOrder2() 
	{
		try
		{
			log.debug("Entering navigateToModifyConvergedCfsOrder2");
			click(MODIFY_TV_HARDWARE_CFS_ORDER_2);
			wait(3);
			log.debug("Leaving navigateToModifyConvergedCfsOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyConvergedCfsOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewPhoneHardwareCFSOrder2()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneHardwareCFSOrder2");
			click( NEW_PHONE_HARDWARE_CFS_ORDER2 );
			wait(3);
			log.debug("Leaving navigateToNewPhoneHardwareCFSOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneHardwareCFSOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	// Check Clec SOM and COM orders tab
	public void checkSomComOrder() 
	{
		try
		{
			log.debug("Entering checkSomComOrder");
			switchPreviousTab();
			click(LINK_TO_COM_PAGE);
			wait(2);
			click(SOM_ORDER_TAB);
			wait(2);
			click(CLEC_REQUEST_TAB);
			wait(2);
			click(COM_ORDER_TAB);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			refreshPage();
			log.debug("Leaving checkSomComOrder");
		}catch(Exception e)
		{
			log.error("Error in checkSomComOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewConvergedCfsOrder() 
	{
		try
		{
			log.debug("Entering navigateToNewConvergedCfsOrder");
			click(NEW_CONVERGED_HARDWARE_CFS_ORDER);
			wait(3);
			log.debug("Leaving navigateToNewConvergedCfsOrder");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewConvergedCfsOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void verifySVGDiagram(String screenShotVal) 
	{
		try
		{
			log.debug("Entering verifySVGDiagram");
			click(ORDER_PARAMETERS);
			wait(2);
			click(SVG_DIAGRAM);
			wait(3);
			takeScreenShot(screenShotVal);
			driver.navigate().back();
			log.debug("Leaving verifySVGDiagram");
		}catch(Exception e)
		{
			log.error("Error in verifySVGDiagram:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public String getOldSerialNbr()
	{
		String oldSerialNbr = "";
		try
		{
			log.debug("Entering getOldSerialNbr");
			click( SERVICE_INFORMATION );
			wait(2);
			JavascriptExecutor javascript = ((JavascriptExecutor) driver);
			javascript.executeScript( "window.scrollBy(0,3000)","" );
			wait(4);
			oldSerialNbr = getText( OLD_SERIAL_NBR );
			javascript.executeScript( "window.scrollBy(0,-3000)","" );
			log.debug("Leaving getOldSerialNbr");
		}catch(Exception e)
		{
			log.error("Error in getOldSerialNbr:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return oldSerialNbr;
	}

	// Tasks method
	public void setMarkFinishedTask() 
	{
		try
		{
			log.debug("Entering setMarkFinishedTask");
			click(TASK_TAB);
			wait(2);
			click(TASK_FILTER_STATUS);
			wait(3);
			click(TASK_WAITING_CHECKBOX);
			wait(3);
			javascript.executeScript("document.getElementsByClassName('selectAll')[0].click()");
			wait(3);
			click(TASK_MARK_FINISHED);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving setMarkFinishedTask");
		}catch(Exception e)
		{
			log.error("Error in setMarkFinishedTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void sendSerialNbrTask( String serialNbr )
	{
		try
		{
			log.debug("Entering sendSerialNbrTask");
			click( TASK_TAB );
			wait(2);
			click( TASK_SEND_SERIAL_NBR );
			wait(3);
			removeTab();
			inputText( TASK_TECHNICIAN_ID,Utility.getValueFromPropertyFile( "Technician_Id" ) );
			wait(3);
			inputText( TASK_SERIAL_NBR,serialNbr );
			wait(3);
			takeScreenShot();
			click( TASK_SEND_BUTTON );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( TASK_OK_BUTTON );
			wait(2);
			log.debug("Leaving sendSerialNbrTask");
		}catch(Exception e)
		{
			log.error("Error in sendSerialNbrTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToModifyConvergedTvCfsOrder1()
	{
		try
		{
			log.debug("Entering navigateToModifyConvergedTvCfsOrder1");
			click( MODIFY_TV_HARDWARE_CFS_ORDER_1 );
			wait(3);
			log.debug("Leaving navigateToModifyConvergedTvCfsOrder1");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyConvergedTvCfsOrder1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyConvergedTvCfsOrder2()
	{
		try
		{
			log.debug("Entering navigateToModifyConvergedTvCfsOrder2");
			click( MODIFY_TV_HARDWARE_CFS_ORDER_2 );
			wait(3);
			log.debug("Leaving navigateToModifyConvergedTvCfsOrder2");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyConvergedTvCfsOrder2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewCustOrderReport() 
	{
		try
		{
			log.debug("Entering navigateToNewCustOrderReport");
			click(NEW_CUSTOMER_ORDER);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewCustOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToNewPhoneLineCFSOrderReport()
	{
		try
		{
			log.debug("Entering navigateToNewPhoneLineCFSOrderReport");
			click( NEW_PHONELINE_CFS_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToNewPhoneLineCFSOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewPhoneLineCFSOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToNewCustBillingOrderReport() 
	{
		try
		{
			log.debug("Entering navigateToNewCustBillingOrderReport");
			click(NEW_BILLING_SERVICE_ORDER);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving navigateToNewCustBillingOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToNewCustBillingOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToModifyCustomerOrderReport()
	{
		try
		{
			log.debug("Entering navigateToModifyCustomerOrderReport");
			click( MODIFY_CUSTOMER_ORDER );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyCustomerOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyCustomerOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToModifyCustomerOrderReport2()
	{
		try
		{
			log.debug("Entering navigateToModifyCustomerOrderReport2");
			click( MODIFY_CUSTOMER_ORDER2 );
			wait(2);
			click( ORDER_PARAMETER_TAB );
			wait(3);
			click( INTEGRATION_REPORT );
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyCustomerOrderReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyCustomerOrderReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToModifyCustOrderReport() 
	{
		try
		{
			log.debug("Entering navigateToModifyCustOrderReport");
			click(MODIFY_CUSTOMER_ORDER);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyCustOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyCustOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToModifyBillingOrderReport() 
	{
		try
		{
			log.debug("Entering navigateToModifyBillingOrderReport");
			click(MODIFY_BILLING_SERVICE_ORDER);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyBillingOrderReport");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyBillingOrderReport:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
    
	public void navigateToModifyBillingOrderReport2() 
	{
		try
		{
			log.debug("Entering navigateToModifyBillingOrderReport2");
			click(MODIFY_BILLING_SERVICE_ORDER2);
			wait(2);
			click(ORDER_PARAMETER_TAB);
			wait(3);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToModifyBillingOrderReport2");
		}catch(Exception e)
		{
			log.error("Error in navigateToModifyBillingOrderReport2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
    
    //kirti - 72
	public void runJobLSCResponse()
	{
		try
		{
			log.debug("Entering runJobLSCResponse");
			navigate( Utility.getValueFromPropertyFile( "job_monitor_url" ) );
			wait(3);
			click( LSC_RESPONSE_JOB );
			wait(2);
			javascript.executeScript( "window.scrollBy(0,-3000)", "" );
			click( RUN_JOB_BUTTON );
			wait(2);
			refreshPage();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			returnToPreviousPage();
			log.debug("Leaving runJobLSCResponse");
		}catch(Exception e)
		{
			log.error("Error in runJobLSCResponse:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
    
	public void runJobBLIFResponse()
	{
		try
		{
			log.debug("Entering runJobBLIFResponse");
			navigate( Utility.getValueFromPropertyFile( "job_monitor_url" ) );
			wait(3);
			click( BLIF_RESPONSE_JOB );
			wait(2);
			javascript.executeScript( "window.scrollBy(0,-3000)", "" );
			click( RUN_JOB_BUTTON );
			wait(2);
			refreshPage();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			returnToPreviousPage();
			wait(3);
			log.debug("Leaving runJobBLIFResponse");
		}catch(Exception e)
		{
			log.error("Error in runJobBLIFResponse:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
    
	public boolean verifyCLECRequestAfterPortedNumber()
	{
		try
		{
			log.debug("Entering verifyCLECRequestAfterPortedNumber");
			click( CLEC_REQUEST_TAB );
			wait(2);
			String blif_Req_Status1 = getText( BLIF_REQUEST_STATUS1 );    
			String blif_Req_Status2 = getText( BLIF_REQUEST_STATUS2 );
			String LSR_Allstream_P1 = getText( LSR_AllSTRREAM_P_STATUS1 );
			String LSR_Allstream_P2 = getText( LSR_AllSTRREAM_P_STATUS2 );   
			String NPAC_Request_Status1 = getText( NPAC_REQUEST_STATUS1 );  
			String NPAC_Request_Status2 = getText( NPAC_REQUEST_STATUS2 );  
			String E911_Request_Status = getText( E911_REQUEST_STATUS );  


			if ( isDisplayed( BLIF_REQUEST_LINK1 ) && isDisplayed( BLIF_REQUEST_LINK2 ) && isDisplayed( LSR_ALLSTREAM_P1_LINK1 ) && isDisplayed( LSR_ALLSTREAM_P1_LINK2 )&& isDisplayed( NPAC_REQUEST_LINK1 )&& isDisplayed( NPAC_REQUEST_LINK2 )&& isDisplayed( E911_REQUEST_LINK )
					&& blif_Req_Status1.equalsIgnoreCase( "Confirmed" )
					&& blif_Req_Status2.equalsIgnoreCase( "Confirmed" )
					&& LSR_Allstream_P1.equalsIgnoreCase( "Confirmed" )
					&& LSR_Allstream_P2.equalsIgnoreCase( "Confirmed" )
					&& NPAC_Request_Status1.equalsIgnoreCase( "Confirmed" )
					&& NPAC_Request_Status2.equalsIgnoreCase( "Confirmed" )
					&& E911_Request_Status.equalsIgnoreCase( "Confirmed" ))
			{
				clecKOrderStatus = true;
			}
			takeScreenShot();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyCLECRequestAfterPortedNumber");
		}catch(Exception e)
		{
			log.error("Error in verifyCLECRequestAfterPortedNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return clecKOrderStatus;
	}
    
	public void verifyRecordsCountInCLECOrder(int expectedRows)
	{
		try
		{
			log.debug("Entering verifyRecordsCountInCLECOrder");
			click( CLEC_REQUEST_TAB );
			wait(3);
			int record = countRowsinTable( ROWS_IN_CLEC_ORDER );
			while ( !(record == expectedRows) )
			{
				wait(2);
				refreshPage();
				record = countRowsinTable( ROWS_IN_CLEC_ORDER );
			}
			takeScreenShot();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyRecordsCountInCLECOrder");
		}catch(Exception e)
		{
			log.error("Error in verifyRecordsCountInCLECOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public boolean validateHardwareSwapTriplePlayAllParams( String acctNbr, String phoneNbr,String convergedSerialNbr, 
			String swapConvergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr, 
			String swapTvBoxSerialNbr, String swapTvPortalSerialNbr ) throws Exception
	{
		boolean validate = false;
		log.debug("Entering validateHardwareSwapTriplePlayAllParams");
		IntegrationReports ir = new IntegrationReports(test);
		Diff d = new Diff( ir.getExpectedModifyCustXMLToString( acctNbr,phoneNbr,convergedSerialNbr,swapConvergedSerialNbr,tvBoxSerialNbr,
				tvPortalSerialNbr,swapTvBoxSerialNbr,swapTvPortalSerialNbr ),ir.getActualModifyCustXmlToString() );
		d.overrideElementQualifier( new RecursiveElementNameAndTextQualifier() );
		if ( d.similar() )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateHardwareSwapTriplePlayAllParams");
		return validate;
	}

	public boolean validateCustReport(String acctNbr, String convergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
			throws Exception
	{
		boolean validate =false;
		log.debug("Entering validateCustReport");
		IntegrationReports ir = new IntegrationReports(test);
		Diff d = new Diff(ir.getExpectedNcToJmsXMLToString_67(acctNbr,convergedSerialNbr,tvBoxSerialNbr,tvPortalSerialNbr),ir.getActualNcToJmsXmlToString_67());
		d.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
		if(d.similar() && ir.getActualNcToInv_67(convergedSerialNbr)&&ir.getActualInvToNc_67())
		{
			validate=true;   
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateCustReport");

		return validate;
	}

	public boolean validateCustomerReport_53(String acctNbr,String phoneNbr1,String phoneNbr2,String convergedSerialNbr, 
			String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		try
		{
			log.debug("Entering validateCustomerReport_53");
			IntegrationReports ir = new IntegrationReports(test);
			Diff d = new Diff( ir.getExpectedPremiseMove_ModifyCustXml( acctNbr,phoneNbr1,phoneNbr2,convergedSerialNbr,tvBoxSerialNbr,tvPortalSerialNbr ),
					ir.getActualPremiseMove_ModifyCustXml() );
			d.overrideElementQualifier( new RecursiveElementNameAndTextQualifier() );
			if ( d.similar() && ir.getActualNcToInvTelephone_72(phoneNbr2))
			{
				validate = true;
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving validateCustomerReport_53");
		}catch(Exception e)
		{
			log.error("Error in validateCustomerReport_53:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return validate;
	}

	public boolean validateCustomerReport_79(String acctNbr,String phoneNbr,String swapConvergedSerialNbr)
	{
		boolean validate =false;
		log.debug("Entering validateCustomerReport_79");
		IntegrationReports ir = new IntegrationReports(test);
		if ( ir.getActualNewCustNcToJms_79( acctNbr, phoneNbr,swapConvergedSerialNbr )&& 
				ir.getActualNewCustInvToNc_79(swapConvergedSerialNbr))
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateCustomerReport_79");
		return validate;
	}

	public boolean validateNewCustomerOrder_28( String acctNbr, String internetHardwareSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateNewCustomerOrder_28");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNcToJms_28( acctNbr,internetHardwareSerialNbr ) && 
				ir.getActualNewCustInvToNc_28( internetHardwareSerialNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_28");
		return validate;
	}
	
	public boolean validateModifyCustomerOrder_23( String acctNbr,String phoneNbr,String phoneSerialNbr, String diffPhoneSrlNbr, 
			String internetSerialNbr, String diffInternetSrlNbr, String tvSerialNbr, String diffTvSrlNbr )
	{
		boolean validate = false;
		log.debug("Entering validateModifyCustomerOrder_23");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNcToJms_23( acctNbr,phoneNbr, phoneSerialNbr,diffPhoneSrlNbr,internetSerialNbr,diffInternetSrlNbr,tvSerialNbr,
				diffTvSrlNbr) && ir.getActualNcToInv1_23( diffPhoneSrlNbr ) && ir.getActualNcToInv2_23( diffInternetSrlNbr ) && 
				ir.getActualNcToInv3_23( diffTvSrlNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateModifyCustomerOrder_23");
		return validate;
	}
	
	public boolean validateNewCustomerOrder_62( String acctNbr, String tvHardwareSerialNbr, String tvHardwareDCX3510SlNbr )
	{
		boolean validate = false;
		log.debug("Entering validateNewCustomerOrder_62");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustOrderReport_62( acctNbr,tvHardwareSerialNbr, tvHardwareDCX3510SlNbr) && 
				ir.getActualNcToInv1_62( tvHardwareSerialNbr ) && ir.getActualNcToInv2_62( tvHardwareDCX3510SlNbr ) )
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_62");
		return validate;
	}
	
	public boolean validateNewCustomerOrder_63( String acctNbr, String tvBoxSerialNbr, String tvPortalSerialNbr, String convergedHardwareSerialNbr )
	{
		boolean validate = false;
		log.debug("Entering validateNewCustomerOrder_63");
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustOrderReport_63( acctNbr, tvBoxSerialNbr, tvPortalSerialNbr, convergedHardwareSerialNbr) && 
				ir.getActualNcToInv1_63(tvBoxSerialNbr)&& ir.getActualNcToInv2_63(tvPortalSerialNbr))
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_63");
		return validate;
	}
	
	public boolean validateNewCustomerOrder_61( String acctNbr, String convergedSerialNbr )
	{
		log.debug("Entering validateNewCustomerOrder_61");
		boolean validate = false;
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustOrderNcToJMS_61( acctNbr,convergedSerialNbr) && ir.getActualNcToInv_67( convergedSerialNbr ))
		{
			validate = true;
		}
		
		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_61");

		return validate;
	}
	
	public boolean validateNewCustomerOrder_66( String acctNbr, String tvBoxSerialNbr, String tvPortalSerialNbr )
	{
		log.debug("Entering validateNewCustomerOrder_66");
		boolean validate = false;
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustOrderReport_66( acctNbr, tvBoxSerialNbr, tvPortalSerialNbr) && 
				ir.getActualNcToInv1_66(tvBoxSerialNbr)&& ir.getActualNcToInv2_66(tvPortalSerialNbr))
		{
			validate = true;
		}
		
		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_66");
		
		return validate;
	}
	
	public boolean validateNewCustomerOrder_72( String acctNbr, String convergedHardwareSrlNbr, String distinctivePhoneNbr )
	{
		log.debug("Entering validateNewCustomerOrder_72");
		boolean validate = false;
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustOrderReport_72( acctNbr, convergedHardwareSrlNbr) && 
				ir.getActualNcToInvTelephone_72(distinctivePhoneNbr))
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_72");
		
		return validate;
	}
	
	public boolean validateNewCustomerOrder_65( String acctNbr, String phoneNbr, String convergedHardwareSrlNbr)
	{
		log.debug("Entering validateNewCustomerOrder_65");
		boolean validate = false;
		IntegrationReports ir = new IntegrationReports( test );
		if ( ir.getActualNewCustNcToJms_65(acctNbr, phoneNbr, convergedHardwareSrlNbr) && ir.getActualNcToInv_65(convergedHardwareSrlNbr))
		{
			validate = true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateNewCustomerOrder_65");

		return validate;
	}
	
}
