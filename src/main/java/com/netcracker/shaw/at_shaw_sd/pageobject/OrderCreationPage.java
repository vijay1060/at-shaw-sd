package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.COMOrdersPageElement.CLEC_REQUEST_TAB;
import static com.netcracker.shaw.element.pageor.COMOrdersPageElement.COM_ORDER_TAB;
import static com.netcracker.shaw.element.pageor.LandingPageElement.LOGIN;
import static com.netcracker.shaw.element.pageor.LandingPageElement.PASSWORD;
import static com.netcracker.shaw.element.pageor.LandingPageElement.USER_NAME;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.*;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.SOM_ORDER_TAB;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class OrderCreationPage<OrderCreationPageElement> extends BasePage {
	public boolean isOrderCreated = false;
	public String phoneNbr=null;
	public String portPhoneNbr=null;
	public String distinctivePhoneNbr=null;
	
	Logger log = Logger.getLogger(OrderCreationPage.class);
   
	public OrderCreationPage(ExtentTest test) {
		super(test);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void customerInformation() 
	{
		try
		{
			log.debug("Entering customerInformation");	
			click(CUSTOMERID_TAB);
			log.debug("Navigating to Customer Information Tab");
			wait(2);
			inputText(FIRSTNAME, "Test");
			log.debug("Entering First Name");
			wait(2);
			inputText(LASTNAME, "Test");
			log.debug("Entering Last Name");
			wait(3);
			click(CUSTOMERDECLINED_CHECKBOX);
			log.debug("Clicking on decline emmail checkbox");
			wait(2);
			inputText(CUST_PHONENUMBER, "3345567789");
			log.debug("Entering Phone Number");
			wait(2);
			selectFromList(AUTHENCTICATIONTYPE, "PIN");
			log.debug("Selecting Authentication type");
			wait(3);
			// inputPin( PIN,PIN1,"500081" );
			inputText(PIN1, "500081");
			log.debug("Entering PIN Number");
			takeScreenShot();
			test.log(LogStatus.PASS, "Fill Cust Info", "Filled Customer Info Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving customerInformation");	
		}catch(Exception e){
			log.error("Error in customerInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void selectBillType() 
	{
		try
		{
			log.debug("Entering selectBillType");	
			click(BILLINGPREFERENCE_TAB);
			log.debug("Navigating to Billing Type Tab");
			wait(2);
			click(BILLINGTYPE);
			log.debug("Selecting Billing Type");
			wait(2);
			takeScreenShot();
			test.log(LogStatus.PASS, "Select Bill Type", "Selected Bill Type Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectBillType");
		}catch(Exception e){
			log.error("Error in selectBillType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void selectProduct() 
	{
		try
		{
			log.debug("Entering selectProduct");
			wait(2);
			click(ADDSERVICESANDFEATURES_TAB);
			wait(3);
			takeScreenShot();
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving selectProduct");
		}catch(Exception e){
			log.error("Error in selectProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public String addServicePhone(String prod)
	{
		try{
			log.debug("Entering addServicePhone");
			wait(2);
			click( ADDPRODUCT );
			wait(3);
			selectFromList( PHONEPRODUCT_LIST,prod );
			wait(3);
			click( SELECTNUMBER );
			wait(3);
			switchWindow();
			click( CHOOSENUMBER );
			wait(3);
			click( PHONENUMBERFROM_LIST );
			phoneNbr=convertPhoneToString(getText( PHONENUMBERFROM_LIST ));
			wait(3);
			click( ASSIGN_BUTTON );
			wait(3);
			click( LISTINGTYPE_LIST );
			wait(2);
			click( LISTINGTYPE );
			wait(3);
			click( OK_BUTTON );
			wait(2);
			takeScreenShot();
			test.log(LogStatus.PASS, "Add Phone Service", "Added Phone Service Successfully");
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving addServicePhone");
		}catch(Exception e)
		{
			log.error("Error in addServicePhone:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return phoneNbr;

	}

	public String convertPhoneToString(String phoneNbr)
	{
		phoneNbr=phoneNbr.replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
		return phoneNbr;
	}

	public void selectVoiceMail() 
	{
		try
		{
			log.debug("Entering selectVoiceMail");
			click(VOICEMAIL_BUTTON);
			wait(2);
			takeScreenShot();
			test.log(LogStatus.PASS, "Select Voice Mail", "Selected Voice Mail Successfully");
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving selectVoiceMail");
		}catch(Exception e)
		{
			log.error("Error in selectVoiceMail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void addPhoneHardware(String phoneSerialNo, String phoneHardware)
	{
		try
		{
			log.debug("Entering addPhoneHardware");
			wait(2);
			click( ADDPRODUCT );
			wait(2);
			selectFromList( HARDWARE,phoneHardware);
			wait(2);
			click( PHONEHARDWARE );
			wait(3);
			switchWindow();
			wait(1);
			inputText( PH_SERIALNUMBERFIELD,phoneSerialNo );
			wait(1);
			click( VALIDATE_BUTTON );
			wait(2);
			test.log(LogStatus.PASS, "Add Phone Hardware", "Added Phone Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( HARDWARE_OK_BUTTON );
			wait(2);
			takeScreenShot();
			log.debug("Leaving addPhoneHardware");
		}catch(Exception e)
		{
			log.error("Error in addPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void removePersonalPhoneHardware()
	{
		try
		{
			log.debug("Entering removePersonalPhoneHardware");			
			wait(1);
			click(REMOVE_PERSONAL_PHONE_HARDWARE);
			wait(2);
			switchWindow();
			wait(1);
			click(CONFIRM_PRODUCT_REMOVE);
			wait(2);
			test.log(LogStatus.PASS, "Remove Phone Hardware", "Removed Phone Hardware Successfully");
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			log.debug("Leaving removePersonalPhoneHardware");
		}catch(Exception e)
		{
			log.error("Error in removePersonalPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void addServiceInternet(String prod) 
	{
		try
		{
			log.debug("Entering addServiceInternet");
			if (prod.equals("Internet 150 Unlimited")) {
				click(INTERNET150_UNLIMITED);
			}
			if(prod.equals("Internet 75"))
			{
				click(INTERNET_75);
			}
			if(prod.equals("Internet 300"))
			{
				click(INTERNET_300);
			}
			wait(2);
			test.log(LogStatus.PASS, "Add Phone and Internet Product", "Added Phone and Internet Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceInternet");
		}catch(Exception e)
		{
			log.error("Error in addServiceInternet:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void addServiceTV(String prod)
	{
		try
		{
			log.debug("Entering addServiceTV");
			if (prod.equals("Limited TV")) {
				click(LIMITED_TV);
			}
			if (prod.equals("Large TV Pick 12")) {
				click(LARGE_TV_PICK12);
			}
			if (prod.equals("Small TV Pick 5")) {
	            click(SMALL_TV_PICK5);
	        }
			wait(2);

			test.log(LogStatus.PASS, "Add TV Product", "Added TV Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceTV");
		}catch(Exception e)
		{
			log.error("Error in addServiceTV:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void addTVHardwareDCX3510(String tvHardwareSerialNbr,String tvHardwareDCX3510SlNbr)
	{
		try
		{
			log.debug("Entering addTVHardwareDCX3510");			
			click(SMALLTV_PICK5_HARDWARE_WRENCH);
			wait(2);
			switchWindow();
			click(SD_RENT);
			wait(2);
			inputText(TV_SERIAL_NO_FIELD,tvHardwareSerialNbr);
			wait(2);
			click(VALIDATE_BUTTON);
			wait(2);  
			click(DCX3510_RENT_BTN);
			wait(2);
			inputText(TV_SERIAL_NO_FIELD,tvHardwareDCX3510SlNbr);
			wait(2);
			click(VALIDATE_DCX3510);
			wait(2);  
			test.log(LogStatus.PASS, "Add TV DCX Hardware", "Added TV DCX Hardware Successfully");
			test.log( LogStatus.INFO,"Snapshot Below: "+ test.addScreenCapture(addScreenshot() ) );
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving addTVHardwareDCX3510");
		}catch(Exception e)
		{
			log.error("Error in addTVHardwareDCX3510:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void addTVHardware(String tvHardwareSerialNo,String tvRentSelection) 
	{
		try
		{
			log.debug("Entering addTVHardware");
			click( LIMITED_TV_HARDWARE );
			wait(2);
			switchWindow();
			if(tvRentSelection.equals("SD"))
				click( SD_RENT );
			if(tvRentSelection.equals("DCX"))
				click(DCX3510_RENT_LEGECY);
			wait(2);
			inputText( TV_SERIAL_NO_FIELD,tvHardwareSerialNo);
			wait(3);
			click( SD_VALIDATE );
			wait(3);
			test.log(LogStatus.PASS, "Added TV Hardware", "Added TV Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( HARDWARE_OK_BUTTON );
			wait(2);
			log.debug("Leaving addTVHardware");

		}catch(Exception e)
		{
			log.error("Error in addTVHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}

	}
	
	public void addInternetHardware(String internetHardwareSerialNo, String internetRentSelection)
	{
		try
		{
			log.debug("Entering addInternetHardware");
			click( INTERNET_HARDWARE );
			wait(2);
			if(internetRentSelection.equalsIgnoreCase( "Cisco" ))
				click( INTERNET150_RENT );
			if(internetRentSelection.equalsIgnoreCase( "Hitron" ))
				click( INTERNET150_HITRONRENT );
			wait(2);
			inputText( INTERNET150_SERIAL_NBR_FIELD,internetHardwareSerialNo);
			wait(3);
			click( INTERNET150_VALIDATE );
			wait(2);
			test.log(LogStatus.PASS, "Added Internet Harware", "Added Internet Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( HARDWARE_OK_BUTTON );
			wait(2);
			log.debug("Leaving addInternetHardware");
		}catch(Exception e)
		{
			log.error("Error in addInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void addConvergedHardware(String serialNo) 
	{
		try
		{
			log.debug("Entering addConvergedHardware");
			click(CONVERGED_HARDWARE_PROD);
			wait(3);
			click(CONVERGED_HARDWARE);
			wait(3);
			click(CONVERGED_HARWARE_RENT);
			takeScreenShot();
			switchWindow();
			inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD,serialNo);
			wait(2);
			click(VALIDATE_BUTTON);
			wait(3);
			click(HARDWARE_OK_BUTTON);
			wait(3);
			takeScreenShot();
			test.log(LogStatus.INFO, "Added Converged Harware", "Added Converged Harware Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addConvergedHardware");
		}catch(Exception e){
			log.error("Error in addConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}

	}
	
	public void selectConvergedHardware(String convergedHardware)
	{
		try
		{
			log.debug("Entering selectConvergedHardware");
			if(convergedHardware.equalsIgnoreCase( "Phone" ))
			{
				click( SELECT_PHONESERVICE );
				wait(2);
			}
			if(convergedHardware.equalsIgnoreCase( "Internet" ))
			{
				click( SELECT_INTERNETSERVICE );
				wait(2);
			}
			if(convergedHardware.equalsIgnoreCase( "Both" ))
			{
				click( SELECT_PHONESERVICE );
				wait(2);
				click( SELECT_INTERNETSERVICE );
				wait(1);
			}
			test.log(LogStatus.PASS, "Select Phone or Internet Service", "Selected Phone or Internet Service Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			takeScreenShot();
			log.debug("Leaving selectConvergedHardware");
		}catch(Exception e)
		{
			log.error("Error in selectConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void deleteConvergedHardware()
	{
		try
		{
			log.debug("Entering deleteConvergedHardware");
			wait(2);
			click(DELETE_CONVERGED_HARDWARE);
			wait(2);
			click(REMOVE_CONFIRM_YES);
			wait(2);
			log.debug("Leaving deleteConvergedHardware");
		}catch(Exception e)
		{
			log.error("Error in deleteConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void selectPhoneConvergedHardware() 
	{
		try
		{
			log.debug("Entering selectPhoneConvergedHardware");
			click(SELECT_PHONESERVICE);
			wait(3);
			takeScreenShot();
			test.log(LogStatus.PASS, "Added Phone Converged Harware", "Added Phone Converged Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectPhoneConvergedHardware");
		}catch(Exception e)
		{
			log.error("Error in selectPhoneConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void selectInternetConvergedHardware() 
	{
		try
		{
			log.debug("Entering selectInternetConvergedHardware");
			click(SELECT_INTERNETSERVICE);
			wait(3);
			takeScreenShot();
			test.log(LogStatus.PASS, "Add Internet Converged Hardware","Added Internet Converged Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectInternetConvergedHardware");
		}catch(Exception e)
		{
			log.error("Error in selectInternetConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void addDigitalChannelSectionTheme()
	{
		try
		{
			log.debug("Entering addDigitalChannelSectionTheme");
			click(DIGITAL_CHANNEL_SECTION);  
			wait(2);
			removeTab();
			click(PICK10_PACK1);
			wait(2);
			click(PICK10_PACK1_WRENCH);
			removeTab();
			wait(2);
			click(ASIDE);
			wait(3);
			click(BCC_CANADA);
			wait(3);
			click(DEJA_VIEW);
			wait(3);
			click(ESPN_CLASSIC);
			wait(3);
			click(FYI);
			wait(3);
			click(FIGHT_NETWORK);
			wait(3);
			click(GSN_GAMESHOW_NETWORK);
			wait(3);
			click(LOVE_NATURE);
			wait(3);
			click(MAKEFUL);
			wait(3);
			click(SILVER_SCREEN_CLASSICS);
			wait(2);
			click(DIGITAL_CHANNEL_OK);
			wait(2);
			click(DIGITAL_CHANNEL_THEME);
			wait(2);
			click(ENT3MEDIUM_POTPOURRI2_MUSICPOP_CULTURE);
			wait(2);
			test.log(LogStatus.PASS, "Add Digital Theme","Added Digital Theme Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CANCEL_ORDER_OK);
			wait(2);
			log.debug("Leaving addDigitalChannelSectionTheme");
		}catch(Exception e)
		{
			log.error("Error in addDigitalChannelSectionTheme:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	// added by kirti
	public void addServiceTVWithBlueSky(String prod,String serialNoBox,String serialNoPortal) 
	{
		try
		{
			log.debug("Entering addServiceTVWithBlueSky");
			if (prod.equals("Large TV Pick 12")) 
			{
				click(LARGE_TV_HARDWARE);
			}
			if (prod.equals("Limited TV")) 
			{
				click(LIMITED_TV_HARDWARE);
			}
			wait(2);
			switchWindow();
			click(RENT_SHAW_BLUE_SKY_526);
			wait(2);
			inputText(RENT_SHAW_BLUE_SKY_TV_BOX526_SLNO_INPUT,serialNoBox);
			wait(3);
			click(RENT_SHAW_BLUE_SKY_TV_BOX526_SLNO_INPUT_VALIDATE);
			wait(3);
			click(RENT_SHAW_BLUE_SKY_TV_PORTAL_416);
			wait(2);
			inputText(RENT_SHAW_BLUE_SKY_TV_BOX416_SLNO_INPUT,serialNoPortal);
			wait(2);
			click(RENT_SHAW_BLUE_SKY_TV_BOX416_SLNO_INPUT_VALIDATE);
			wait(2);
			test.log(LogStatus.PASS, "Add TV BlueSky Product", "Added TV Product WithBlueSky Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			takeScreenShot();
			log.debug("Leaving addServiceTVWithBlueSky");
		}catch(Exception e)
		{
			log.error("Error in addServiceTVWithBlueSky:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void addTVHardwareDCX3200M(String tvHardwareSerialNo,String tvHardwareDCX3200MSlNo)
	{
		try
		{
			log.debug("Entering addTVHardwareDCX3200M");
			click(LIMITED_TV_HARDWARE);
			wait(2);
			switchWindow();
			click(SD_RENT);
			wait(2);
			inputText(TV_SERIAL_NO_FIELD,tvHardwareSerialNo);
			wait(2);
			click(VALIDATE_BUTTON);
			wait(3);  
			click(DCX_3200M_RENT);
			wait(2);
			inputText(DCX_RENT_SLNO,tvHardwareDCX3200MSlNo);
			wait(2);
			click(VALIDATE_BUTTON);
			wait(2);  
			test.log(LogStatus.PASS, "Add TV DCX 3200 Hardware", "Added TV DCX 3200 Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving addTVHardwareDCX3200M");
		}catch(Exception e)
		{
			log.error("Error in addTVHardwareDCX3200M:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void swapConvergedHardware(String serialNum) 
	{
		try
		{
			log.debug("Entering swapConvergedHardware");
			click(CONVERGED_HARDWARE);
			wait(4);
			removeTab();
			wait(2);
			click(CONVERGED_HARDWARE_SWAP);
			wait(2);
			click(TECHNICIAN_RADIO_BUTTON);
			wait(2);
			inputText(CONVERGED_HARDWARE_TECH_ID, Utility.getValueFromPropertyFile("Technician_Id"));
			wait(3);
			click(CONVERGED_HARDWARE_VALIDATE_TECH_ID);
			wait(2);
			inputText(CONVERGED_HARDWARE_NEW_SERIAL_NO, serialNum);
			wait(2);
			click(CONVERGED_HARDWARE_NEW_SERIAL_NO_VALIDATE);
			wait(2);
			test.log(LogStatus.PASS, "Swap Converged Hardware", "Swapped Converged Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CONVERGED_HARDWARE_NEW_SERIAL_NO_OK_BUTTON);
			wait(2);
			log.debug("Leaving swapConvergedHardware");
		}catch(Exception e)
		{
			log.error("Error in swapConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void swapServiceTVBlueSky(String boxserialNum, String portalSerialNum) 
	{
		try
		{
			log.debug("Entering swapServiceTVBlueSky");
			click(LIMITED_TV_HARDWARE);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX526_SWAP);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX526_TECHNICIAN_RADIO_BUTTON);
			wait(3);
			inputText(SHAW_BLUE_SKY_TV_PORTAL_BOX526_TECHNICIAN_ID, Utility.getValueFromPropertyFile("Technician_Id"));
			wait(3);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX526_VALIDATE_TECH_ID);
			wait(2);
			inputText(SHAW_BLUE_SKY_TV_PORTAL_BOX526_HARDWARE_NEW_SERIAL_NO,boxserialNum);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX526_VALIDATE_NEW_SERIAL_NO);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX416_SWAP);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX416_TECHNICIAN_RADIO_BUTTON);
			wait(2);
			inputText(SHAW_BLUE_SKY_TV_PORTAL_BOX416_TECHNICIAN_ID, Utility.getValueFromPropertyFile("Technician_Id"));
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX416_VALIDATE_TECH_ID);
			wait(2);
			inputText(SHAW_BLUE_SKY_TV_PORTAL_BOX416_HARDWARE_NEW_SERIAL_NO,portalSerialNum);
			wait(2);
			click(SHAW_BLUE_SKY_TV_PORTAL_BOX416_VALIDATE_NEW_SERIAL_NO);
			wait(2);
			takeScreenShot();
			test.log(LogStatus.PASS, "Swap TV Service Bluesky", "Swapped TV Service Bluesky Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			log.debug("Leaving swapServiceTVBlueSky");
		}catch(Exception e)
		{
			log.error("Error in swapServiceTVBlueSky:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void addDistinctiveRing()
	{
		try
		{
			log.debug("Entering addDistinctiveRing");
			click(DISTINCTIVE_RING_INCREMENT);
			wait(3);
			click(DR_SELECT_NUMBER);
			wait(3);
			switchWindow();
			click( CHOOSENUMBER );
			wait(3);
			click( PHONENUMBERFROM_LIST );
			wait(3);
			click( ASSIGN_BUTTON );
			wait(3);
			click( LISTINGTYPE_LIST );
			wait(2);
			click( LISTINGTYPE );
			wait(3);
			click(FEATURES_TAB);
			wait(3);
			click(RING_PATTERN_DROP_DOWN);
			wait(3);
			click(TYPE_3_RING_SHORT);
			takeScreenShot();
			test.log(LogStatus.PASS, "Add Distinctive Ring", "Added Distinctive Ring Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			click( OK_BUTTON );
			log.debug("Leaving addDistinctiveRing");
		}catch(Exception e)
		{
			log.error("Error in addDistinctiveRing:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}

	}

	public void techAppointment(String prod)
	{
		try
		{
			log.debug("Entering techAppointment");
			if(prod.equalsIgnoreCase( "No" ))
			{
				wait(2);
				click( APPOINTMENT );
				wait(3);
				click( ISTECHREQUIRED_NO );
				wait(3);
				click( DIRECT_FULFILLMENT_BUTTON_NO );
				wait(2);
				click( RETAIL_PICKUP_BUTTON_NO );
				wait(3);
			}else if(prod.equalsIgnoreCase( "Yes" ))
			{
				wait(2);
				click( APPOINTMENT );
				wait(3);
				click( ISTECHREQUIRED_YES );
				wait(3);
				click( TECH_APPOINTMENT_DATE );
				wait(2);
			}

			inputText( CHANGEAUTHORIZEDBY,"admin" );
			test.log(LogStatus.PASS, "Add Tech Appointment", "Added Tech Appointment Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			takeScreenShot();
			log.debug("Leaving techAppointment");
		}catch(Exception e)
		{
			log.error("Error in techAppointment:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void techAppointmentCurrentDate()
	{
		try
		{
			log.debug("Entering techAppointmentCurrentDate");
			wait(2);
			click( APPOINTMENT );
			wait(3);
			click( ISTECHREQUIRED_YES );
			wait(3);
			click( SELECT_MANUAL_APPOINTMENT );
			wait(2);
			click(CLICK_CALENDAR);
			wait(2);
			getWebElement(CLICK_CALENDAR).sendKeys( Keys.ENTER );
			wait(2);
			inputText( CHANGEAUTHORIZEDBY,"admin" );
			test.log(LogStatus.PASS, "Add Tech Appointment With CurrentDate", "Added Tech Appointment With CurrentDate Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			takeScreenShot();
			log.debug("Leaving techAppointmentCurrentDate");
		}catch(Exception e)
		{
			log.error("Error in techAppointmentCurrentDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void techAppointmentWithGAP()
	{
		try
		{
			log.debug("Entering techAppointmentWithGAP");
			click( PREMISE_MOVE_SERVICE_NUMBER );
			wait(3);
			click( PREMISE_MOVE_DISCONNECTION_DATE );
			wait(3);
			getWebElement(PREMISE_MOVE_DISCONNECTION_DATE).findElement( By.xpath("//*[text()='"+datePicker()+"']")).click();
			wait(3);
			takeScreenShot();
			test.log(LogStatus.PASS, "Add Tech Appointment WithGap", "Added Tech Appointment WithGap Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving techAppointmentWithGAP");
		}catch(Exception e)
		{
			log.error("Error in techAppointmentWithGAP:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public String datePicker()
	{
		String newDate="";  
		SimpleDateFormat sdf = new SimpleDateFormat("dd");  
		Calendar cal = Calendar.getInstance();     
		cal.add(Calendar.DATE, 1);
		newDate = sdf.format(cal.getTime());
		System.out.println("currrent date + 1 " + newDate) ;
		return newDate;
}

	public void reviewAndFinishOrder() 
	{
		try
		{
			log.debug("Entering reviewAndFinishOrder");
			click(REVIEW);
			wait(2);
			takeScreenShot("REVIEW PAGE");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(FINISH);
			do {
				wait(2);
			} while (!isDisplayed(ORDER_MGMT_STAT));
			Assert.assertTrue(isOrderCreated(), "Order Was Not Created Successfully");
			takeScreenShot("ORDER CREATION");
			test.log(LogStatus.PASS, "Order creation", "Order Created Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving reviewAndFinishOrder");
		}catch(Exception e)
		{
			log.error("Error in reviewAndFinishOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public String getAccountNumber() {
		return getText(ACCOUNTNUMBER);
	}

	public void enterInstallationFee() 
	{
		try
		{
			log.debug("Entering enterInstallationFee");
			wait(2);
			JavascriptExecutor javascript = ((JavascriptExecutor) driver);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			click(TODO_LIST);
			wait(2);
			getWebElement(INSTALLATIONFEE).sendKeys("1.00");
			test.log(LogStatus.PASS, "Add Installation Fee", "Added Installation Fee Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving enterInstallationFee");
		}catch(Exception e)
		{
			log.error("Error in enterInstallationFee:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void selectTodo()
	{
		try
		{
			log.debug("Entering selectTodo");
			click( TODO_LIST );
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectTodo");
		}catch(Exception e)
		{
			log.error("Error in selectTodo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public boolean isOrderCreated() 
	{
		try
		{
			log.debug("Entering isOrderCreated");
			if (isDisplayed(ORDER_MGMT_STAT))
				isOrderCreated = true;
			log.debug("Leaving isOrderCreated");
		}catch(Exception e)
		{
			log.error("Error in isOrderCreated:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return isOrderCreated;
	}

	public void navigateToCustOrderPage(String newTabExists,String acctNbr)
	{
		try
		{
			log.debug("Entering navigateToCustOrderPage");
			String custOrderPageUrl = Utility.getValueFromPropertyFile( "basepage_url" )
					+ "/oe.newCustomerDesktop.nc?accountId=1" + acctNbr + "&locationId=221";
			System.out.println( "custOrderPageUrl:" + custOrderPageUrl );
			if(newTabExists.equalsIgnoreCase( "true" ))
				switchSecondNewTab();
			navigate( custOrderPageUrl );
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving navigateToCustOrderPage");
		}catch(Exception e)
		{
			log.error("Error in navigateToCustOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void cancelOrder() 
	{
		try
		{
			log.debug("Entering cancelOrder");
			click(REVIEW);
			wait(2);
			click(CANCEL_ORDER);
			wait(2);
			removeTab();
			click(CANCEL_YES);
			wait(5);
			click(CANCEL_ORDER_OK);
			wait(3);
			test.log(LogStatus.PASS, "Cancel Order", "Cancelled Order Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			takeScreenShot("CANCEL ORDER");
			log.debug("Leaving cancelOrder");
		}catch(Exception e)
		{
			log.error("Error in cancelOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void swapPhoneHardware(String swapPhonehardwareSerialNbr)
	{
		try
		{
			log.debug("Entering swapPhoneHardware");
			wait(2);
			click(PHONEHARDWARE);
			removeTab();
			wait(2);
			click (SWAP_HARDWARE);
			wait(2);
			click (TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID,Utility.getValueFromPropertyFile("Technician_Id"));
			wait(2);
			click (VALIDATE_TECH_ID);
			wait(2);
			inputText( SWAP_SL_NO,swapPhonehardwareSerialNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(2);
			test.log(LogStatus.PASS, "Swap Phone Hardware", "Swapped Phone Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving swapPhoneHardware");
		}catch(Exception e)
		{
			log.error("Error in swapPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void swapHitronInternetHardware(String swapInternetHardwareSrlNbr )
	{
		try
		{
			log.debug("Entering swapHitronInternetHardware");
			wait(3);
			click(INTERNET_HARDWARE);
			removeTab();
			wait(2);
			click ( SWAP_HARDWARE );
			wait(2);
			click ( TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID,Utility.getValueFromPropertyFile("Technician_Id"));
			wait(2);
			click (VALIDATE_TECH_ID);
			wait(2);
			inputText( SWAP_SL_NO, swapInternetHardwareSrlNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			test.log(LogStatus.PASS, "Swap Hitron Internet Hardware", "Swapped Hitron Internet Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving swapHitronInternetHardware");
		}catch(Exception e)
		{
			log.error("Error in swapHitronInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void swapTVDCXHardware(String swapTVDCXHardwareSrlNbr)
	{
		try
		{
			log.debug("Entering swapTVDCXHardware");
			wait(2);
			click( TV_DCX_WRENCH_BTN );
			wait(2);
			removeTab();
			click (SWAP_HARDWARE);
			wait(2);
			click (TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID,Utility.getValueFromPropertyFile("Technician_Id"));
			wait(2);
			click (VALIDATE_TECH_ID);
			wait(2);
			inputText( SWAP_SL_NO,swapTVDCXHardwareSrlNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			test.log(LogStatus.PASS, "Swap TV DCX Hardware", "Swapped TV DCX Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving swapTVDCXHardware");
		}catch(Exception e)
		{
			log.error("Error in swapTVDCXHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToOeStubServerToFail(String newTabExists)
	{
		try
		{
			log.debug("Entering navigateToOeStubServerToFail");
			if(newTabExists=="true")
				switchSecondNewTab();
			navigate( Utility.getValueFromPropertyFile( "oestub_url" ) );
			wait(4);
			click( HPSA );
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToOeStubServerToFail");
		}catch(Exception e)
		{
			log.error("Error in navigateToOeStubServerToFail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void navigateToHpsaFail(String value)
	{
		try
		{
			log.debug("Entering navigateToHpsaFail");
			if(value.equalsIgnoreCase( "Phone" ))
			{
				click( HPSA_PHONE_LINE );
				wait(1);
				click( HPSA_PHONE_LINE );
				wait(1);
			}
			if(value.equalsIgnoreCase( "Internet" ))
			{
				click( HPSA_INTERNET );
				wait(1);
				click( HPSA_INTERNET );
				wait(1);
			}
			if(value.equalsIgnoreCase( "CableTV" ))
			{
				click( HPSA_CABLE_TV );
				wait(1);
				click( HPSA_CABLE_TV );
				wait(1);
			}
			if(value.equalsIgnoreCase("Wifi"))
			{
				click(HPSA_WIFI);
				wait(2);
				click(HPSA_WIFI);
				wait(3);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToHpsaFail");
		}catch(Exception e)
		{
			log.error("Error in navigateToHpsaFail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToOeStubServerToPass(String newTabExists,String value)
	{
		try
		{
			log.debug("Entering navigateToOeStubServerToPass");
			if(newTabExists=="true")
				switchNextTab();
			wait(1);
			if(value.equalsIgnoreCase( "Phone" ))
			{
				click( HPSA_PHONE_LINE );
			}
			if(value.equalsIgnoreCase( "Internet" ))
			{
				click( HPSA_INTERNET ); 
			}
			if(value.equalsIgnoreCase( "CableTV" ))
			{
				wait(1);
				click( HPSA_CABLE_TV );
			}
			if(value.equalsIgnoreCase( "Wifi" ))
			{
				click( HPSA_WIFI );
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToOeStubServerToPass");
		}catch(Exception e)
		{
			log.error("Error in navigateToOeStubServerToPass:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void navigateToExistingAccountForPremise(String accountNum) 
	{
		try
		{
			log.debug("Entering navigateToExistingAccountForPremise");
			String existingAccountUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + accountNum + "&locationId=221&newLocationId=155";

			System.out.println(existingAccountUrl);
			navigate(existingAccountUrl);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			log.debug("Leaving navigateToExistingAccountForPremise");
		}catch(Exception e)
		{
			log.error("Error in navigateToExistingAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	public void clickAccountLinkForOrders() 
	{
		try
		{
			log.debug("Entering clickAccountLinkForOrders");
			click(ACCOUNT_LINK_FOR_ORDERS);
			wait(2);
			log.debug("Leaving clickAccountLinkForOrders");
		}catch(Exception e)
		{
			log.error("Error in clickAccountLinkForOrders:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	@SuppressWarnings({})
	public COMOrdersPage openCOMOrderPage(String newTabExists, String acctNbr) 
	{
		try
		{
			log.debug("Entering openCOMOrderPage");
			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=1000&_vname=1" + acctNbr
					+ "&_rname=in&search_mode=search&do_search=yes&fast_search=yes";
			System.out.println(accountPageurl);
			wait(1);
			if (newTabExists.equals("true"))
				switchFirstNewTab();
			navigate(accountPageurl);
			wait(3);
			click(ACCOUNT_LINK);
			wait(6);
			takeScreenShot("COM ORDER");
			click(SOM_ORDER_TAB);
			wait(2);
			click(CLEC_REQUEST_TAB);
			wait(2);
			click(COM_ORDER_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openCOMOrderPage");
		}catch(Exception e)
		{
			log.error("Error in openCOMOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return new COMOrdersPage(test);
	}
	
	@SuppressWarnings({})
	public COMOrdersPage openCOMOrderPageLogin(String newTabExists, String acctNbr) 
	{
		try
		{
			log.debug("Entering openCOMOrderPage");
			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=1000&_vname=1" + acctNbr
					+ "&_rname=in&search_mode=search&do_search=yes&fast_search=yes";
			System.out.println(accountPageurl);
			wait(1);
			if (newTabExists.equals("true"))
				switchFirstNewTab();
			navigate(accountPageurl);
			wait(3);
			inputText(USER_NAME,Utility.getValueFromPropertyFile("user"));
			log.debug("Entering User Name");
			wait(1);
			inputText(PASSWORD,Utility.getValueFromPropertyFile("password"));
			log.debug("Entering Password");
			wait(1);
			driver.findElement(By.xpath("//div[@class='buttonInner']//a[@href='#'][contains(text(),'Login')]")).click();
			log.debug("Clicking Login Button");
			wait(2);
			click(ACCOUNT_LINK);
			wait(6);
			takeScreenShot("COM ORDER");
			click(SOM_ORDER_TAB);
			wait(2);
			click(CLEC_REQUEST_TAB);
			wait(2);
			click(COM_ORDER_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openCOMOrderPage");
		}catch(Exception e)
		{
			log.error("Error in openCOMOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return new COMOrdersPage(test);
	}
	
	public void navigateToVancoverLocationId(String accountNum)
	{  
		try
		{
			log.debug("Entering navigateToVancoverLocationId");
			String existingAccountUrl = Utility.getValueFromPropertyFile("basepage_url")+"/oe.newCustomerDesktop.nc?accountId=1"+accountNum+"&locationId=221&newLocationId=2777";   

			System.out.println(existingAccountUrl);
			navigate(existingAccountUrl);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving navigateToVancoverLocationId");
		}catch(Exception e)
		{
			log.error("Error in navigateToVancoverLocationId:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}

	//Ramesh
	
	public void disconnectTVProduct()
	{
		try
		{
			log.debug("Entering disconnectTVProduct");
			wait(2);
			click(TV_NOT_SELECTED);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving disconnectTVProduct");
		}catch(Exception e)
		{
			log.error("Error in disconnectTVProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	public void disconnectReasons(String prod)
	{
		try
		{
			log.debug("Entering disconnectReasons");
			if (prod.equals("Switch to competitor:"))
			{
				click(SWITCH_TO_COMPETITOR);
				wait(2);
				click(SWITCH_TO_COMPETITOR_DROPDOWN);
				wait(2);
				click(SELECT_SHAW_DIRECT);
			}
			if (prod.equals("Moving:")) 
			{
				click(MOVING);
			}
			if (prod.equals("Temporary disconnect:")) 
			{
				click(TEMPORARY_DISCONNECT);
			}
			if (prod.equals("Budget or price-related:")) 
			{
				click(BUDGET_OR_PRISE_RELATED);
			}
			if (prod.equals("Technical/Customer care:")) 
			{
				click(TECHNICIAL_CUSTOMER_CARE);
			}
			if (prod.equals("Other:")) 
			{
				click(OTHER);
			}
			if (prod.equals("Customer declined to say:")) 
			{
				click(CUSTOMER_DECLINED_TO_SAY);
			}
			wait(2);
			test.log(LogStatus.PASS, "Add Disconnect Reason", "Add Disconnect Reason Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving disconnectReasons");
		}catch(Exception e)
		{
			log.error("Error in disconnectReasons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
	}
	
	//kirti
	public String portPhoneNumber(String prod)
	{
		try
		{
			log.debug("Entering portPhoneNumber");
			wait(3);
			click( ADDPRODUCT );
			wait(3);
			selectFromList( PHONEPRODUCT_LIST,prod );
			wait(3);
			click( SELECTNUMBER );
			wait(3);
			switchWindow();
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(3);
			inputText( PORTED_NUMBER,
					Utility.getValueFromPropertyFile( "9" ) + Utility.getRandomPhoneNumber() );
			portPhoneNbr=getText(PORTED_NUMBER);
			wait(3);
			click(PORT_NOW);
			wait(3);
			click(LANDLINE_RADIO_BUTTON);
			wait(3);        
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER,"9999999999");        
			wait(3);              
			click(RESELLER_DROP_DOWN);
			wait(3); 
			click(RESELLER_DROP_DOWN);
			click(RESELLER_VALUE);
			wait(3);
			click(CUSTOMER_LIFELINE_YES);
			wait(3);
			click(SHAW_SERVICE_REQUESTED_NO);
			wait(3);
			click( OK_BUTTON );
			wait(3);
			click( LISTINGTYPE_LIST );
			wait(2);
			click( LISTINGTYPE411ONlY );
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( OK_BUTTON );
			log.debug("Leaving portPhoneNumber");
		}catch(Exception e)
		{
			log.error("Error in portPhoneNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return portPhoneNbr;
	}

	public String portDistinctiveNumber()
	{
		try
		{
			log.debug("Entering portDistinctiveNumber");
			wait(3);
			click(DISTINCTIVE_RING_INCREMENT);
			wait(3);
			click(DR_SELECT_NUMBER);       
			wait(3);
			switchWindow();
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(3);
			inputText( PORTED_NUMBER,
					Utility.getValueFromPropertyFile( "6" ) + Utility.getRandomPhoneNumber() );
			distinctivePhoneNbr=getText(PORTED_NUMBER);
			wait(3);
			click(PORT_NOW);
			wait(3);
			click(LANDLINE_RADIO_BUTTON);
			wait(3);
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER,"6666666666");  
			wait(3);              
			click(RESELLER_DROP_DOWN);
			wait(3); 
			click(RESELLER_DROP_DOWN);
			click(RESELLER_VALUE);
			wait(3); 
			click(CUSTOMER_LIFELINE_YES);
			wait(3);
			click(SHAW_SERVICE_REQUESTED_NO);
			wait(3);
			click( OK_BUTTON );
			wait(3);
			click( LISTINGTYPE_LIST );
			wait(2);
			click( LISTINGTYPE411ONlY );
			wait(2);
			click(FEATURES_TAB);
			wait(3);
			click(RING_PATTERN_DROP_DOWN);
			wait(3);
			click(TYPE_3_RING_SHORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click( OK_BUTTON );      
			wait(3);
			log.debug("Leaving portDistinctiveNumber");
		}catch(Exception e)
		{
			log.error("Error in portDistinctiveNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return distinctivePhoneNbr;
	}

}
