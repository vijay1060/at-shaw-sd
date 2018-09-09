package com.netcracker.shaw.at_shaw_sd.pageobject;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.at_shaw_sd.util.UtilityXML;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class IntegrationReports extends BasePage
{
	Logger log = Logger.getLogger(IntegrationReports.class);
	String actualStringXmlVal = "";
	String expectedStringXmlVal = "";

	public IntegrationReports(ExtentTest test) {
		super(test);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public String getExpectedSuspendXMLToString(String acctNbr)
	{
		String actualPath = Utility.getValueFromPropertyFile( "expected_xml_path" );
		try {
			log.debug("Entering getExpectedSuspendXMLToString");
			File xmlFile = new File(actualPath);
			for (File fileEntry : xmlFile.listFiles()) 
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(true);
				DocumentBuilder dBuilder;

				if(fileEntry.getName().equals( "SuspendTVProvisioningRFSOrder_NC_HPSA.xml" ))
				{
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fileEntry);
					doc.getDocumentElement().normalize();

					UtilityXML.removeAll( doc,"TechnicalContext");
					UtilityXML.updateElementValue( doc, "CustomerAccountRef", "AccountId", acctNbr );
					UtilityXML.updateElementValue( doc, "ServiceOrderItemProperties", "Action", "SUSPEND" );
					UtilityXML.updateElementValue( doc, "Characteristic", "CharacteristicName", "null" );
					UtilityXML.updateElementValue( doc, "Characteristic", "CategoryName", "null" );
					UtilityXML.updateElementValue( doc, "Characteristic", "Value", "null" );
					UtilityXML.updateElementValue( doc, "ResourceFacingServiceProperties", "ServiceId", "null" );
					UtilityXML.updateElementValue( doc, "ServiceSpecClassification", "ServiceSpecClassificationName", "LinearTV" );
					UtilityXML.updateElementValue( doc, "ResourceFacingServiceSpecProperties", "ServiceSpecId", "100" );
					UtilityXML.updateElementValue( doc, "ResourceRef", "ObjectId", "null" );

					expectedStringXmlVal=UtilityXML.convertDocumentToString(doc);

					String tempPath = Utility.getValueFromPropertyFile( "temp_xml_path" );
					tempPath = tempPath.replace( "\\", "//" );
					String newStringName=StringUtils.substringBefore( fileEntry.getName(), "." );
					newStringName=newStringName+"_Expected.xml";
					UtilityXML.storeXmlFileForExpectedFile(doc,fileEntry.getName(),tempPath+"//"+newStringName);
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getExpectedSuspendXMLToString");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			log.error("Error in getExpectedSuspendXMLToString:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}

		return expectedStringXmlVal;
	}

	public String getActualSuspendXmlToString(){
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Suspend [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		try {
			log.debug("Entering getActualSuspendXmlToString:" + actualNcToHpsaString);
			Document actualDoc=UtilityXML.convertStringToDocument(actualNcToHpsaString);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			actualDoc.getDocumentElement().normalize();
			UtilityXML.removeAll( actualDoc,"TechnicalContext");
			UtilityXML.updateElementValue( actualDoc, "Characteristic", "CharacteristicName", "null" );
			UtilityXML.updateElementValue( actualDoc, "Characteristic", "CategoryName", "null" );
			UtilityXML.updateElementValue( actualDoc, "Characteristic", "Value", "null" );
			UtilityXML.updateElementValue( actualDoc, "ResourceFacingServiceProperties", "ServiceId", "null" );
			UtilityXML.updateElementValue( actualDoc, "ServiceSpecClassification", "ServiceSpecClassificationName", "LinearTV" );
			UtilityXML.updateElementValue( actualDoc, "ResourceFacingServiceSpecProperties", "ServiceSpecId", "100" );
			UtilityXML.updateElementValue( actualDoc, "ResourceRef", "ObjectId", "null" );

			actualStringXmlVal=UtilityXML.convertDocumentToString(actualDoc);

			UtilityXML.storeXmlFileForActual(actualDoc,"SuspendTVProvisioningRFSOrder_NC_HPSA_Actual.xml");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getActualSuspendXmlToString");
		} catch (Exception e) {
			log.error("Error in getActualSuspendXmlToString:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return actualStringXmlVal;
	}

	public boolean getActualSuspend(String acctNbr) 
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Suspend [SOM] TV Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualSuspend:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SUSPEND");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");
		if (checAcctNbr&&checkAddrId&&checkAction&&checkSvcSpecId1&&checkSvcSpecId2&&
				checkSvcSpecId3&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualSuspend");
		return validate;
	}

	public boolean getActualResumeTVRFS(String acctNbr) 
	{
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Resume [SOM] TV Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		boolean validate = false;
		log.debug("Entering getActualResumeTVRFS:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"RESUME");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");
		if (checAcctNbr&&checkAddrId&&checkAction&&checkSvcSpecId1&&checkSvcSpecId2&&
				checkSvcSpecId3&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualResumeTVRFS");
		return validate;
	}

	public String getExpectedModifyCustXMLToString(String acctNbr,String phoneNbr, String convergedSerialNbr, 
			String swapConvergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr, 
			String swapTvBoxSerialNbr, String swapTvPortalSerialNbr)
	{
		String actualPath = Utility.getValueFromPropertyFile( "expected_xml_path" );
		try {
			log.debug("Entering getExpectedModifyCustXMLToString");
			File xmlFile = new File(actualPath);
			for (File fileEntry : xmlFile.listFiles()) 
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(true);
				DocumentBuilder dBuilder;

				if(fileEntry.getName().equals( "HardwareSwapModifyCustomerOrder_NC_JMS.xml" ))
				{
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fileEntry);
					doc.getDocumentElement().normalize();

					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "accountNumber", acctNbr );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "accountId", "1"+acctNbr );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "instanceId", "null" );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "locationTimeZone", "null" );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "orderId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "telephoneNumber", phoneNbr );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "phoneNumberType", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneHardwareOrder", "serialNumber", swapConvergedSerialNbr );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalPhoneHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalPhoneHardwareOrder", "serialNumber", convergedSerialNbr );

					UtilityXML.updateElementValue( doc, "InternetOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectInternetHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectInternetHardwareOrder", "serialNumber", convergedSerialNbr );
					UtilityXML.updateElementValue( doc, "InternetHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "InternetHardwareOrder", "serialNumber", swapConvergedSerialNbr );

					UtilityXML.updateElementValue( doc, "DigitalTVOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", swapTvBoxSerialNbr,"4xgateway" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", swapTvPortalSerialNbr,"5xportal" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalTVHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalTVHardwareOrder","serialNumber", tvBoxSerialNbr,"4xgateway" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalTVHardwareOrder","serialNumber", tvPortalSerialNbr,"5xportal" );

					expectedStringXmlVal=UtilityXML.convertDocumentToString(doc);

					String tempPath = Utility.getValueFromPropertyFile( "temp_xml_path" );
					tempPath = tempPath.replace( "\\", "//" );
					String newStringName=StringUtils.substringBefore( fileEntry.getName(), "." );
					newStringName=newStringName+"_Expected.xml";
					UtilityXML.storeXmlFileForExpectedFile(doc,fileEntry.getName(),tempPath+"//"+newStringName);
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getExpectedModifyCustXMLToString");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			log.error("Error in getExpectedModifyCustXMLToString:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return expectedStringXmlVal;
	}

	public String getActualModifyCustXmlToString()
	{
		String actualNcToHpsaString = "";
		try {

			String disconnectNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Disconnect Converged Hardware CFS Order","<ns2:CustomerOrder" );
			String modifyNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
			String disStatus = UtilityXML.getAttributeValue( UtilityXML.convertStringToDocument( disconnectNcToJMSString ), "CustomerOrder", "status" );
			String modStatus = UtilityXML.getAttributeValue( UtilityXML.convertStringToDocument( modifyNcToJMSString ), "CustomerOrder", "status" );

			if(disStatus!=null && disStatus.equalsIgnoreCase( "Completed" ) )
			{
				actualNcToHpsaString = disconnectNcToJMSString;
			}
			if(modStatus!=null && modStatus.equalsIgnoreCase( "Completed" ))
			{
				actualNcToHpsaString = modifyNcToJMSString;
			}
			log.debug("Entering getActualModifyCustXmlToString:" + actualNcToHpsaString);

			Document actualDoc=UtilityXML.convertStringToDocument(actualNcToHpsaString);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			actualDoc.getDocumentElement().normalize();
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "instanceId", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "locationTimeZone", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "orderId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalPhoneOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "PhoneLineOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "PhoneLineOrder", "phoneNumberType", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalPhoneHardwareOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectDigitalPhoneHardwareOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "InternetOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectInternetHardwareOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "InternetHardwareOrder", "offeringId", "null" );

			UtilityXML.updateElementValue( actualDoc, "DigitalTVOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalTVHardwareOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectDigitalTVHardwareOrder", "offeringId", "null" );

			actualStringXmlVal=UtilityXML.convertDocumentToString(actualDoc);

			UtilityXML.storeXmlFileForActual(actualDoc,"HardwareSwapModifyCustomerOrder_NC_JMS_Actual.xml");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getActualModifyCustXmlToString");
		} catch (Exception e) {
			log.error("Error in getActualModifyCustXmlToString:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}

		return actualStringXmlVal;
	}

	public String getExpectedInvToNcXMLToString_67(String acctNbr, String convergedSerialNbr)
	{
		String actualPath = Utility.getValueFromPropertyFile( "expected_xml_path" );
		try {
			log.debug("Entering getExpectedInvToNcXMLToString_67");
			File xmlFile = new File(actualPath);
			for (File fileEntry : xmlFile.listFiles()) 
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(true);
				DocumentBuilder dBuilder;

				if(fileEntry.getName().equals( "NewCustomerOrder_Inventory_NC.xml" ))
				{
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fileEntry);
					doc.getDocumentElement().normalize();

					UtilityXML.updateElementValue( doc, "equipment", "id", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "serialNumber", convergedSerialNbr );
					UtilityXML.updateElementValue( doc, "equipment", "provisioningRegistrationKey", convergedSerialNbr );
					UtilityXML.updateElementValue( doc, "equipment", "manufacturer", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "modelType", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "isProvisionable", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "description", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "memo", "null" );

					UtilityXML.updateElementValue( doc, "proprietor", "type", "null" );
					UtilityXML.updateElementValue( doc, "proprietor", "identifier", "null" );
					UtilityXML.updateElementValue( doc, "proprietor", "accountNumber", acctNbr );
					UtilityXML.updateElementValue( doc, "proprietor", "description", "null" );

					UtilityXML.updateElementValue( doc, "equipment", "state", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "isActive", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "isCustomerAllowed", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "unitAddress", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "docsisMacAddress", "null" );

					UtilityXML.updateElementValue( doc, "specifications", "specification", "null" );
					UtilityXML.updateElementValue( doc, "components", "component", "null" );

					UtilityXML.updateElementValue( doc, "equipment", "lastModifiedDate", "null" );
					UtilityXML.updateElementValue( doc, "equipment", "isRental", "null" );

					UtilityXML.updateAttributeValue( doc, "link", "href", "null" );
					UtilityXML.updateAttributeValue( doc, "link", "ref", "null" );
					UtilityXML.updateAttributeValue( doc, "link", "rel", "null" );

					expectedStringXmlVal=UtilityXML.convertDocumentToString(doc);

					String tempPath = Utility.getValueFromPropertyFile( "temp_xml_path" );
					tempPath = tempPath.replace( "\\", "//" );
					String newStringName=StringUtils.substringBefore( fileEntry.getName(), "." );
					newStringName=newStringName+"_Expected.xml";
					UtilityXML.storeXmlFileForExpectedFile(doc,fileEntry.getName(),tempPath+"//"+newStringName);
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Entering getExpectedInvToNcXMLToString_67");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			log.error("Error in getExpectedInvToNcXMLToString_67:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return expectedStringXmlVal;
	}

	public String getActualInvToNcXmlToString_67()
	{
		try{

			String actualInvToNcString=Utility.getXMLString( driver, "t","INVENTORY","NETCRACKER","New Converged Hardware CFS Order","<inventory" );
			log.debug("Entering getActualInvToNcXmlToString_67:" + actualInvToNcString);
			Document actualDoc=UtilityXML.convertStringToDocument(actualInvToNcString);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			actualDoc.getDocumentElement().normalize();
			UtilityXML.updateElementValue( actualDoc, "equipment", "id", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "manufacturer", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "modelType", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "isProvisionable", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "description", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "memo", "null" );

			UtilityXML.updateElementValue( actualDoc, "proprietor", "type", "null" );
			UtilityXML.updateElementValue( actualDoc, "proprietor", "identifier", "null" );
			UtilityXML.updateElementValue( actualDoc, "proprietor", "description", "null" );

			UtilityXML.updateElementValue( actualDoc, "equipment", "state", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "isActive", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "isCustomerAllowed", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "unitAddress", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "docsisMacAddress", "null" );

			UtilityXML.updateElementValue( actualDoc, "specifications", "specification", "null" );
			UtilityXML.updateElementValue( actualDoc, "components", "component", "null" );

			UtilityXML.updateElementValue( actualDoc, "equipment", "lastModifiedDate", "null" );
			UtilityXML.updateElementValue( actualDoc, "equipment", "isRental", "null" );

			UtilityXML.updateAttributeValue( actualDoc, "link", "href", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "link", "ref", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "link", "rel", "null" );


			actualStringXmlVal=UtilityXML.convertDocumentToString(actualDoc);

			UtilityXML.storeXmlFileForActual(actualDoc,"NewCustomerOrder_Inventory_NC_Actual.xml");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Entering getActualInvToNcXmlToString_67");
		} catch (Exception e) {
			log.error("Error in getActualInvToNcXmlToString_67:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return actualStringXmlVal;
	}

	public String getExpectedNcToJmsXMLToString_67(String acctNbr, String convergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		String actualPath = Utility.getValueFromPropertyFile("expected_xml_path");
		try{
			log.debug("Entering getExpectedNcToJmsXMLToString_67");
			File xmlFile = new File(actualPath);
			for (File fileEntry : xmlFile.listFiles()) 
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(true);
				DocumentBuilder dBuilder;

				if(fileEntry.getName().equals("NewCustomerOrder_NC_JMS.xml"))
				{
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fileEntry);
					doc.getDocumentElement().normalize();

					UtilityXML.updateAttributeValue(doc, "CustomerOrder", "accountNumber", acctNbr);
					UtilityXML.updateAttributeValue(doc, "CustomerOrder", "accountId", "1"+acctNbr);
					UtilityXML.updateAttributeValue(doc, "CustomerOrder", "instanceId", "null");
					UtilityXML.updateAttributeValue(doc, "CustomerOrder", "locationTimeZone", "null");
					UtilityXML.updateAttributeValue(doc, "CustomerOrder", "orderId", "null");
					UtilityXML.updateElementValue(doc, "DigitalPhoneOrder", "offeringId", "null");
					UtilityXML.updateElementValue(doc, "PhoneLineOrder", "offeringId", "null");
					UtilityXML.updateElementValue(doc, "PhoneLineOrder", "telephoneNumber", "null");
					UtilityXML.updateElementValue(doc, "PhoneLineOrder", "phoneNumberType", "null");
					UtilityXML.updateElementValue(doc, "DigitalPhoneHardwareOrder", "offeringId", "null");
					UtilityXML.updateElementValue(doc, "DigitalPhoneHardwareOrder","technicianId","null");
					UtilityXML.updateElementValue(doc, "DigitalPhoneHardwareOrder", "serialNumber", convergedSerialNbr);
					UtilityXML.updateElementValue(doc, "InternetOrder", "offeringId", "null" );
					UtilityXML.updateElementValue(doc, "InternetHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue(doc, "InternetHardwareOrder","technicianId","null");
					UtilityXML.updateElementValue(doc, "InternetHardwareOrder", "serialNumber", convergedSerialNbr );
					UtilityXML.updateElementValue( doc, "DigitalTVOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder", "technicianId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", tvBoxSerialNbr,"4xgateway" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", tvPortalSerialNbr,"5xportal" );
					UtilityXML.updateElementValue( doc, "VoicemailOrder", "offeringId", "null" );

					expectedStringXmlVal=UtilityXML.convertDocumentToString(doc);

					String tempPath = Utility.getValueFromPropertyFile( "temp_xml_path" );
					tempPath = tempPath.replace( "\\", "//" );
					String newStringName=StringUtils.substringBefore( fileEntry.getName(), "." );
					newStringName=newStringName+"_Expected.xml";
					UtilityXML.storeXmlFileForExpectedFile(doc,fileEntry.getName(),tempPath+"//"+newStringName);
				}
			}
			log.debug("Leaving getExpectedNcToJmsXMLToString_67");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			log.error("Error in getExpectedNcToJmsXMLToString_67:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return expectedStringXmlVal;
	}

	public String getActualNcToJmsXmlToString_67()
	{
		try
		{
			String actualNcToJmsString=Utility.getXMLString(driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder");
			log.debug("Entering getActualNcToJmsXmlToString_67:" + actualNcToJmsString);
			Document actualDoc=UtilityXML.convertStringToDocument(actualNcToJmsString);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			actualDoc.getDocumentElement().normalize();
			UtilityXML.updateAttributeValue(actualDoc, "CustomerOrder", "instanceId", "null");
			UtilityXML.updateAttributeValue(actualDoc, "CustomerOrder", "locationTimeZone", "null");
			UtilityXML.updateAttributeValue(actualDoc, "CustomerOrder", "orderId", "null");
			UtilityXML.updateElementValue(actualDoc, "DigitalPhoneOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "PhoneLineOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "PhoneLineOrder", "telephoneNumber", "null");
			UtilityXML.updateElementValue(actualDoc, "PhoneLineOrder", "phoneNumberType", "null");
			UtilityXML.updateElementValue(actualDoc, "DigitalPhoneHardwareOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "InternetOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "InternetHardwareOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "DigitalTVOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "DigitalTVHardwareOrder", "offeringId", "null");
			UtilityXML.updateElementValue(actualDoc, "VoicemailOrder", "offeringId", "null" );
			UtilityXML.updateElementValue(actualDoc, "DigitalTVHardwareOrder", "technicianId", "null" );
			UtilityXML.updateElementValue(actualDoc, "InternetHardwareOrder","technicianId","null");
			UtilityXML.updateElementValue(actualDoc, "DigitalPhoneHardwareOrder","technicianId","null");

			actualStringXmlVal=UtilityXML.convertDocumentToString(actualDoc);
			UtilityXML.storeXmlFileForActual(actualDoc,"NewCustomerOrder_NC_JMS.xml");
			log.debug("Leaving getActualNcToJmsXmlToString_67");
		} catch (Exception e) {
			log.error("Error in getActualNcToJmsXmlToString_67:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return actualStringXmlVal;
	}

	public boolean getActualNewCustNcToJms_60(String acctNbr,String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewCustNcToJms_60:" + actualNcToJmsString);

		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);    
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"NEW");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,internetHardwareSerialNbr);
		boolean checkReason1=validateAttributeValue(actualNcToJmsString,"Add");   

		if (checkAcctNbr&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&
				checkBoxSerialNbr&&checkReason1)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewCustNcToJms_60");
		return validate;
	}

	public boolean getActualNcToInv_67(String convergedSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New Converged Hardware CFS Order","Request URI" );
		log.debug("Entering getActualNcToInv_67:" + actualNcToInvString);
		if(actualNcToInvString.contains( "Request URI" ))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("xb6converged"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, convergedSerialNbr );
				}
			}
		}
		
		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+convergedSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}
		log.debug("Leaving getActualNcToInv_67");
		return ncToInvUrl;
	}

	public boolean getActualInvToNc_67()
	{
		boolean invToNc=false;
		String actualInvToNcString=Utility.getXMLString( driver, "t","INVENTORY","NETCRACKER","New Converged Hardware CFS Order","200" );
		log.debug("Entering getActualInvToNc_67:" + actualInvToNcString);    	
		if(actualInvToNcString.equalsIgnoreCase( "200" ))
		{
			invToNc=true;
		}
		log.debug("Leaving getActualInvToNc_67");
		return invToNc;
	}

	public boolean getActualNewNcToHpsa1_67(String acctNbr, String tvBoxSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewNcToHpsa1_67:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId4=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkSvcSpecId5=validateAttributeValue(actualNcToHpsaString,"105");
		boolean checkSvcSpecId6=validateAttributeValue(actualNcToHpsaString,"106");
		boolean checkSvcSpecId7=validateAttributeValue(actualNcToHpsaString,"107");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");
		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcSpecId3
				&&checkSvcSpecId4&&checkSvcSpecId5&&checkSvcSpecId6&&checkSvcSpecId7&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewNcToHpsa1_67");

		return validate;
	}

	public boolean getActualNewNcToHpsa2_67(String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewNcToHpsa2_67:" + actualNcToHpsaString);
		boolean checkCharVal=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);
		if (checkCharVal)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewNcToHpsa2_67");

		return validate;
	}

	public boolean getActualNcToBrm_67(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		try
		{
			String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
			log.debug("Entering getActualNcToBrm_67");
			boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
			boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
			boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
			boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
			boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
			boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
			boolean checkTvOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
			boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
			boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
			boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
			boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
			boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
			boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");
			boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental");
			boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental");
			boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);

			if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkInternetOfferingName&&
					checkTvOfferingName&&checkDealName_1&&checkDealName_2&&checkDealName_3&&checkDealName_4&&checkDealName_5&&
					checkDealName_6&&checkDealName_7&&checkDealName_8&&checkCnvgSrlNbr)
				validate=true;
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getActualNcToBrm_67");
		} catch (Exception e) {
			log.error("Error in getActualNcToBrm_67:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return validate;
	}

	public boolean getActualDisconnectPhoneNcToHpsa1(String acctNbr,String phoneNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa1:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa1");
		return validate;
	}

	public boolean getActualDisconnectPhoneNcToHpsa2(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","REMOVE" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa2:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1004");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa2");
		return validate;
	}
	
	public boolean getActualDisconnectInternetNcToHpsa_79(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Internet Provisioning RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectInternetNcToHpsa_79:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;
		
		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDisconnectInternetNcToHpsa_79");
		return validate;
	}

	public boolean getActualDisconnectInternetNcToHpsa1(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Internet Provisioning RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectInternetNcToHpsa1:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		log.debug("Leaving getActualDisconnectInternetNcToHpsa1");
		return validate;
	}

	public boolean getActualDisconnectInternetNcToHpsa2(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Internet Provisioning RFS Order","REMOVE" );
		log.debug("Entering getActualDisconnectInternetNcToHpsa2:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1004");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		log.debug("Leaving getActualDisconnectInternetNcToHpsa2");
		return validate;
	}

	public boolean getActualDisconnectTVNcToHpsa(String acctNbr, String tvPortalSerialNbr, String tvBoxSerialNbr)
	{
		boolean validate =false;
		String boxOrPortalString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualDisconnectTVNcToHpsa:" + boxOrPortalString);
		boolean checkBoxSerialNbr=false;
		if(boxOrPortalString.contains("4xgateway"))
		{
			checkBoxSerialNbr=validateAttributeValue(boxOrPortalString,tvBoxSerialNbr);
		}else if(boxOrPortalString.contains("5xportal"))
		{
			checkBoxSerialNbr=validateAttributeValue(boxOrPortalString,tvPortalSerialNbr);
		}    
		boolean checkAcctNbr=validateAttributeValue(boxOrPortalString,acctNbr);
		boolean checkAddrId=validateAttributeValue(boxOrPortalString,"221");
		boolean checkAction=validateAttributeValue(boxOrPortalString,"REMOVE");
		boolean checkSvcSpecId=validateAttributeValue(boxOrPortalString,"1000");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDisconnectTVNcToHpsa");
		return validate;
	}

	//66th case: 

	public boolean getActualNewInternetProvisioningRFSNcToHpsa_66(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetProvisioningRFSNcToHpsa_66:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");
		boolean checkIPAdrsName=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkServiceId=validateAttributeValue(actualNcToHpsaString,"ServiceId");
		boolean checkESCatalogueServiceId=validateAttributeValue(actualNcToHpsaString,"990");
		boolean checkESCatalogueServiceId1=validateAttributeValue(actualNcToHpsaString,"55");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkIPAdrsName&&checkServiceId&&checkESCatalogueServiceId&&checkESCatalogueServiceId1)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetProvisioningRFSNcToHpsa_66");
		return validate;
	}

	public boolean getActualNewTVProvisioningRFSNcToHpsa_66(String acctNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewTVProvisioningRFSNcToHpsa_66:" + actualNcToHpsaString);
		boolean checkTvSerialNbr=false;

		if(actualNcToHpsaString.contains("4xgateway"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		else if(actualNcToHpsaString.contains("5xportal"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");

		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkCharacteristicValue1=validateAttributeValue(actualNcToHpsaString,"103");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkCharacteristicValue2=validateAttributeValue(actualNcToHpsaString,"560");
		boolean checkCharacteristicValue3=validateAttributeValue(actualNcToHpsaString,"677");
		boolean checkCharacteristicValue4=validateAttributeValue(actualNcToHpsaString,"961");
		boolean checkESCatalogueServiceId=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkIPAdrsName=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkCharacteristicname=validateAttributeValue(actualNcToHpsaString,"ESCatalogueServiceId");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkTvSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkCharacteristicValue1&&checkIPAdrsName&&checkESCatalogueServiceId
				&&checkCharacteristicValue2&&checkCharacteristicValue3&&checkCharacteristicValue4&&checkSvcSpecId3&&checkCharacteristicname&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewTVProvisioningRFSNcToHpsa_66");
		return validate;
	}

	public boolean getActualNewCustOrderReport_66(String convergedHardware, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","JMS", "New Customer Order", "<ns2:CustomerOrder xmlns:ns2" );
		log.debug("Entering getActualNewCustOrderReport_66:" + actualNcToHpsaString);
		boolean checStatus=validateAttributeValue(actualNcToHpsaString,"Completed");    
		boolean checkconvergedslno=validateAttributeValue(actualNcToHpsaString, convergedHardware);
		boolean checkBoxslnbr=validateAttributeValue(actualNcToHpsaString, tvBoxSerialNbr);
		boolean checkPortalslnbr=validateAttributeValue(actualNcToHpsaString, tvPortalSerialNbr);
		boolean chekOrderstatus = validateAttributeValue(actualNcToHpsaString,"provisioned");
		boolean checkreason=validateAttributeValue(actualNcToHpsaString,"Add");
		boolean checkDevicetype=validateAttributeValue(actualNcToHpsaString,"Converged");
		boolean checkTechId=validateAttributeValue(actualNcToHpsaString,"technicianId");
		boolean checkownership=validateAttributeValue(actualNcToHpsaString,"rented");
		boolean checkofferingId=validateAttributeValue(actualNcToHpsaString,"offeringId");

		if (checStatus&&checkconvergedslno&&checkBoxslnbr&&checkPortalslnbr&&chekOrderstatus&&checkreason&&checkDevicetype&&checkTechId&&checkownership&&checkofferingId)
			validate=true;

		log.debug("Leaving getActualNewCustOrderReport_66");
		return validate;    
	}

	public boolean getActualNcToBrm_66(String acctNbr, String convergedHardware,String tvBoxSerialNbr,String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_66");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checktvSrlNbr1=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checktvSrlNbr2=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);
		boolean checktvSrlNbr3=validateAttributeValue(actualNcToBrmString,convergedHardware);
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"TVE");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"MDVR");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkDealName_9=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental [SERIAL NUMBER]");
		boolean checkDealName_10=validateAttributeValue(actualNcToBrmString,"TVOD");
		boolean checkDealName_11=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkDealName_12=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_13=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_14=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental [SERIAL NUMBER]");
		boolean checkDealName_15=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");

		if (checkAccountId&&checkAccountNbr&&checkStatus&&checktvSrlNbr3&&checkChargeType&&checkDealName_1&&checkDealName_2&&
				checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&checkDealName_7&&checkDealName_8&&
				checkDealName_9&&checkDealName_10&&checkDealName_11&&checkDealName_12&&checkDealName_13&&checkDealName_14&&
				checkDealName_15&&checktvSrlNbr1&&checktvSrlNbr2&&checkOfferingName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_66");
		return validate;
	}

	public boolean getActualNcToInv1_66(String tvBoxSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","4xgateway" );
		log.debug("Entering getActualNcToInv1_66:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("4xgateway"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvBoxSerialNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+tvBoxSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv1_66");
		return ncToInvUrl;
	}

	public boolean getActualNcToInv2_66(String tvPortalSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","5xportal" );
		log.debug("Entering getActualNcToInv2_66:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("5xportal"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvPortalSerialNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+tvPortalSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv2_66");
		return ncToInvUrl;
	}


	public boolean getActualNewCustNcToJms_09(String acctNbr,String phoneNbr, String convergedSerialNbr,String swapConvergedSerialNbr, 
			String tvBoxSerialNbr, String tvPortalSerialNbr, String swapTvBoxSerialNbr, String swapTvPortalSerialNbr )
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewCustNcToJms_09:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToJmsString,phoneNbr);
		boolean checkConvergedSlNbr=validateAttributeValue(actualNcToJmsString,convergedSerialNbr);
		boolean checkSwapConvergedSlNbr=validateAttributeValue(actualNcToJmsString,swapConvergedSerialNbr);
		boolean checkTvBoxSlNbr=validateAttributeValue(actualNcToJmsString,tvBoxSerialNbr);
		boolean checkTvPortalSlNbr=validateAttributeValue(actualNcToJmsString,tvPortalSerialNbr);
		boolean checkSwapTvBoxSlNbr=validateAttributeValue(actualNcToJmsString,swapTvBoxSerialNbr);
		boolean checkSwapTvPortalSlNbr=validateAttributeValue(actualNcToJmsString,swapTvPortalSerialNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOrderStatus3=validateAttributeValue(actualNcToJmsString,"deprovisioned");
		boolean checkReason=validateAttributeValue(actualNcToJmsString,"Order Swap");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkReason1=validateAttributeValue(actualNcToJmsString,"Add");   

		if (checkAcctNbr&&checkPhoneNbr&&checkConvergedSlNbr&&checkSwapConvergedSlNbr&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&
				checkTvBoxSlNbr&&checkTvPortalSlNbr&&checkSwapTvBoxSlNbr&&checkSwapTvPortalSlNbr&&checkReason1&&checkOrderStatus3&&checkReason)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewCustNcToJms_09");
		return validate;
	}


	//63th case:
	public boolean getActualNewTVProvisioningRFSNcToHpsa_63(String acctNbr, String tvBoxSerialNbr,String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewTVProvisioningRFSNcToHpsa_63:" + actualNcToHpsaString);
		boolean checkTvSerialNbr=false;
		if(actualNcToHpsaString.contains("4xgateway"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		else if(actualNcToHpsaString.contains("5xportal"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkCharacteristicname=validateAttributeValue(actualNcToHpsaString,"ESCatalogueServiceId");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkIPAdrsName=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkVOD=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkPPV=validateAttributeValue(actualNcToHpsaString,"102");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkSvcName1&&checkVOD&&checkPPV&&checkTvSerialNbr&&checkSvcSpecId1&&checkIPAdrsName
				&&checkSvcSpecId3&&checkCharacteristicname)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewTVProvisioningRFSNcToHpsa_63");
		return validate;
	}

	public boolean getActualNewCustOrderReport_63(String acctNbr, String convergedHardware, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","JMS", "New Customer Order", "<ns2:CustomerOrder xmlns:ns2" );
		log.debug("Entering getActualNewCustOrderReport_63:" + actualNcToHpsaString);
		boolean checStatus=validateAttributeValue(actualNcToHpsaString,"Completed"); 
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString, acctNbr);
		boolean checkconvergedslno=validateAttributeValue(actualNcToHpsaString, convergedHardware);
		boolean checkBoxslnbr=validateAttributeValue(actualNcToHpsaString, tvBoxSerialNbr);
		boolean checkPortalslnbr=validateAttributeValue(actualNcToHpsaString, tvPortalSerialNbr);
		boolean chekOrderstatus = validateAttributeValue(actualNcToHpsaString,"provisioned");
		boolean checkreason=validateAttributeValue(actualNcToHpsaString,"Add");
		boolean checkDevicetype=validateAttributeValue(actualNcToHpsaString,"Converged");
		boolean checkownership=validateAttributeValue(actualNcToHpsaString,"rented");
		boolean checkofferingId=validateAttributeValue(actualNcToHpsaString,"offeringId");

		if (checStatus&&checkAcctNbr&&checkconvergedslno&&checkBoxslnbr&&checkPortalslnbr&&chekOrderstatus&&checkreason&&checkDevicetype&&checkownership&&checkofferingId)
			validate=true;

		log.debug("Leaving getActualNewCustOrderReport_63");
		return validate;    
	}

	public boolean getActualNcToBrm_63(String acctNbr, String tvBoxSerialNbr, String convergedHardware, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_63");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkOfferingName1=validateAttributeValue(actualNcToBrmString,"Large TV Pick 12");
		boolean checkOfferingName2=validateAttributeValue(actualNcToBrmString,"Pick 10 Pack 1");
		boolean checkOfferingName3=validateAttributeValue(actualNcToBrmString,"A.Side");
		boolean checkOfferingName4=validateAttributeValue(actualNcToBrmString,"BBC Canada");
		boolean checkOfferingName5=validateAttributeValue(actualNcToBrmString,"Dejaview");
		boolean checkOfferingName6=validateAttributeValue(actualNcToBrmString,"ESPN Classic");
		boolean checkOfferingName7=validateAttributeValue(actualNcToBrmString,"FYI");
		boolean checkOfferingName8=validateAttributeValue(actualNcToBrmString,"Fight Network");
		boolean checkOfferingName9=validateAttributeValue(actualNcToBrmString,"Makeful");
		boolean checkOfferingName10=validateAttributeValue(actualNcToBrmString,"Love Nature");
		boolean checkOfferingName11=validateAttributeValue(actualNcToBrmString,"Silver Screen Classics");
		boolean checkOfferingName12=validateAttributeValue(actualNcToBrmString,"GSN Game Show Network");
		boolean checktvSrlNbr1=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checktvSrlNbr2=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);
		boolean checktvSrlNbr3=validateAttributeValue(actualNcToBrmString,convergedHardware);
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"TVE");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"MDVR");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Internet 300");
		boolean checkDealName_9=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental [SERIAL NUMBER]");
		boolean checkDealName_10=validateAttributeValue(actualNcToBrmString,"TVOD");
		boolean checkDealName_11=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkDealName_12=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_13=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_14=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental [SERIAL NUMBER]");

		if (checkAccountId&&checkAccountNbr&&checkStatus&&checktvSrlNbr3&&checkChargeType&&checkDealName_2&&checkDealName_1&&
				checktvSrlNbr1&&checktvSrlNbr2&&checkOfferingName1&&checkOfferingName2&&checkOfferingName3&&checkOfferingName4&&
				checkOfferingName5&&checkOfferingName6&&checkOfferingName7&&checkOfferingName8&&checkOfferingName9&&
				checkOfferingName10&&checkOfferingName11&&checkOfferingName12&&checkDealName_3&&checkDealName_4&&checkDealName_5
				&&checkDealName_6&&checkDealName_7&&checkDealName_8&&checkDealName_9&&checkDealName_10&&checkDealName_11&&
				checkDealName_12&&checkDealName_13&&checkDealName_14)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_63");
		return validate;
	}

	public boolean getActualNcToInv1_63(String tvBoxSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","4xgateway" );
		log.debug("Entering getActualNcToInv1_63:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("4xgateway"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvBoxSerialNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+tvBoxSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv1_63");
		return ncToInvUrl;
	}

	public boolean getActualNcToInv2_63(String tvPortalSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","5xportal" );
		log.debug("Entering getActualNcToInv2_63:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("5xportal"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvPortalSerialNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+tvPortalSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv2_63");
		return ncToInvUrl;
	}

	public boolean getActualNewInternetCnvrgdNcToHpsa(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetCnvrgdNcToHpsa:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetCnvrgdNcToHpsa");
		return validate;
	}

	public boolean getActualNewNcToBrm_79(String acctNbr,String phoneNbr, String swapConvergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNewNcToBrm_79");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkCallWaitOfferingName=validateAttributeValue(actualNcToBrmString,"Call Waiting");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Do Not Use Phone Service (Trial Only)");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,swapConvergedSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkVoiceMailOfferingName&&checkCallWaitOfferingName&&checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkDealName_1&&
				checkDealName_2&&checkDealName_3&&checkCnvgSrlNbr&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewNcToBrm_79");
		return validate;
	}

	public boolean getActualNewCustNcToJms_79(String acctNbr,String phoneNbr, String swapConvergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewCustNcToJms_79:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"NEW");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,swapConvergedSerialNbr);
		boolean checkReason1=validateAttributeValue(actualNcToJmsString,"Tech Swap");
		boolean checkReason2=validateAttributeValue(actualNcToJmsString,"Re-assign");
		boolean checkDeviceType=validateAttributeValue(actualNcToJmsString,"Converged");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToJmsString,phoneNbr);

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&
				checkBoxSerialNbr&&checkReason1&&checkReason2&&checkDeviceType&&checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualNewCustNcToJms_79");
		return validate;
	}

	public boolean getActualNewCustInvToNc_79(String swapConvergedSerialNbr)
	{
		boolean validate =false;
		String actualInvToNcString=Utility.getXMLString( driver, "t","INVENTORY","NETCRACKER","New Converged Hardware CFS Order","<type>Customer</type>" );
		log.debug("Entering getActualNewCustInvToNc_79:" + actualInvToNcString);
		boolean checkEquipType=validateAttributeValue(actualInvToNcString,"Xb6Converged");
		boolean checkPrvsnKey=validateAttributeValue(actualInvToNcString,swapConvergedSerialNbr);
		boolean checkIsRental=validateAttributeValue(actualInvToNcString,"true");

		if (checkEquipType&&checkPrvsnKey&&checkIsRental)
			validate=true;

		log.debug("Leaving getActualNewCustInvToNc_79");
		return validate;
	}

	public boolean getActualNcToBrmTriplePlay(String acctNbr,String phoneNbr, String convergedSerialNbr, String swapConvergedSerialNbr, 
			String tvBoxSerialNbr, String tvPortalSerialNbr, String swapTvBoxSerialNbr, 
			String swapTvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrmTriplePlay");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Do Not Use Phone Service (Trial Only)");
		boolean checkTvOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental");
		boolean checkCnvgSrlNbr1=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkCnvgSrlNbr2=validateAttributeValue(actualNcToBrmString,swapConvergedSerialNbr);
		boolean checkTVSrlNbr1=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checkTVSrlNbr2=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);
		boolean checkTVSrlNbr3=validateAttributeValue(actualNcToBrmString,swapTvBoxSerialNbr);
		boolean checkTVSrlNbr4=validateAttributeValue(actualNcToBrmString,swapTvPortalSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkVoiceMailOfferingName&&checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkTvOfferingName&&checkDealName_1&&
				checkDealName_2&&checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&checkDealName_7&&checkDealName_8&&
				checkCnvgSrlNbr1&&checkCnvgSrlNbr2&&checkPhoneNbr&&checkTVSrlNbr1&&checkTVSrlNbr2&&checkTVSrlNbr3&&checkTVSrlNbr4)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrmTriplePlay");
		return validate;
	}


	public boolean getActualDisconnectPhoneNcToHpsa1_44(String acctNbr,String phoneSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa1_44:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,phoneSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1003");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa1_44");
		return validate;
	}

	public boolean getActualDisconnectPhoneNcToHpsa2_44(String acctNbr, String phoneSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","REMOVE" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa2_44:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,phoneSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1003");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa2_44");
		return validate;
	}

	public boolean getActualNcToBrm(String acctNbr,String phoneNbr, String convergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Do Not Use Phone Service (Trial Only)");
		boolean checkTvOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkTVSrlNbr1=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checkTVSrlNbr2=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkVoiceMailOfferingName&&checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkTvOfferingName&&checkDealName_1&&
				checkDealName_2&&checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&checkDealName_7&&checkDealName_8&&
				checkCnvgSrlNbr&&checkPhoneNbr&&checkTVSrlNbr1&&checkTVSrlNbr2)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm");
		return validate;
	}

	public boolean getActualNcToBrm_53(String acctNbr,String phoneNbr, String convergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_53");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Do Not Use Phone Service (Trial Only)");
		boolean checkTvOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkTVSrlNbr1=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checkTVSrlNbr2=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkVoiceMailOfferingName&&checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkTvOfferingName&&checkDealName_1&&
				checkDealName_2&&checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&checkDealName_7&&checkDealName_8&&
				checkCnvgSrlNbr&&checkPhoneNbr&&checkTVSrlNbr1&&checkTVSrlNbr2)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_53");
		return validate;
	}
	
	public boolean getActualNewCustNcToJms_65(String acctNbr,String phoneNbr, String convergedHardWareSerialNo)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewCustNcToJms_65:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"NEW");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkPhnoType=validateAttributeValue(actualNcToJmsString,"Native");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,convergedHardWareSerialNo);
		boolean checkDeviceType=validateAttributeValue(actualNcToJmsString,"Converged");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToJmsString,phoneNbr);

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&checkPhnoType&&
				checkBoxSerialNbr&&checkDeviceType&&checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualNewCustNcToJms_65");
		return validate;
	}
	
	public boolean getActualNcToInv_65(String convergedHardWareSerialNo)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New Converged Hardware CFS Order","xb6converged" );
		log.debug("Entering getActualNcToInv1_65:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("xb6converged"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace(s1, convergedHardWareSerialNo);
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+ convergedHardWareSerialNo;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv1_65");
		return ncToInvUrl;
	}

	public boolean getActualNcToBrm_65(String acctNbr,String phoneNbr,String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_65");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Do Not Use Phone Service (Trial Only)");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkVoiceMailOfferingName&&checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkDealName_1&&
				checkDealName_2&&checkDealName_3&&checkCnvgSrlNbr&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_65");
		return validate;
	}

	public boolean getActualNewCustOrderReport_72(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","JMS", "New Customer Order", "<ns2:CustomerOrder xmlns:ns2" );
		log.debug("Entering getActualNewCustOrderReport_72:" + actualNcToHpsaString);
		boolean checkAccountNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checStatus=validateAttributeValue(actualNcToHpsaString,"Completed");    
		boolean checkCongdSrlNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);         
		boolean chekOrderstatus = validateAttributeValue(actualNcToHpsaString,"provisioned");
		boolean checkreason=validateAttributeValue(actualNcToHpsaString,"Add");
		boolean checkDevicetype=validateAttributeValue(actualNcToHpsaString,"Converged");
		boolean checkTechId=validateAttributeValue(actualNcToHpsaString,"technicianId");
		boolean checkownership=validateAttributeValue(actualNcToHpsaString,"rented");
		boolean checkPorted=validateAttributeValue(actualNcToHpsaString,"Ported");

		if (checkAccountNbr&&checStatus&&checkCongdSrlNbr&&checkPorted&&chekOrderstatus&&checkreason&&checkDevicetype&&checkTechId&&checkownership)
			validate=true;

		log.debug("Leaving getActualNewCustOrderReport_72");
		return validate;    
	}

	public boolean getActualNcToInvTelephone_72(String phoneNbr)
	{
		boolean validate =false;
		String actualNcToInvTeleString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY_TELEPHONE","Modify Customer Order","<reservation" );
		log.debug("Entering getActualNcToInvTelephone_72:" + actualNcToInvTeleString);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToInvTeleString,phoneNbr);

		if (checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualNcToInvTelephone_72");
		return validate;
	}

	public boolean getActualNcToInvTelephone_44(String phoneNbr)
	{
		boolean validate =false;
		String actualNcToInvTeleString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY_TELEPHONE","New Customer Order","<reservation" );
		log.debug("Entering getActualNcToInvTelephone_44:" + actualNcToInvTeleString);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToInvTeleString,phoneNbr);

		if (checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToInvTelephone_44");
		return validate;
	}

	public boolean getActualNcToJms_44(String acctNbr,String phoneNbr,String phoneSerialNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNcToJms_44:" + actualNcToJmsString);
		boolean checkOrderStatus1=false;
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		if(actualNcToJmsString.contains("deprovisioned"))
			checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"deprovisioned");
		else if(actualNcToJmsString.contains("deprovisioningpending"))
			checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"deprovisioningpending");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkPhoneSerialNbr=validateAttributeValue(actualNcToJmsString,phoneSerialNbr);
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,convergedSerialNbr);
		boolean checkReason1=validateAttributeValue(actualNcToJmsString,"Remove");
		boolean checkReason2=validateAttributeValue(actualNcToJmsString,"Add");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToJmsString,phoneNbr);

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkPhoneSerialNbr&&
				checkBoxSerialNbr&&checkReason1&&checkReason2&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToJms_44");
		return validate;
	}

	public boolean getActualNcToBrm_44(String acctNbr,String phoneNbr, String convergedSerialNbr, String phoneSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_44");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName1=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkPhoneOfferingName2=validateAttributeValue(actualNcToBrmString,"Rental Phone Terminal");
		boolean checkPhoneOfferingName3=validateAttributeValue(actualNcToBrmString,"Home Phone Terminal");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkPhoneSrlNbr=validateAttributeValue(actualNcToBrmString,phoneSerialNbr);
		boolean checkVoiceMailOfferingName=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Home Phone Voicemail and Call Waiting");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName1&&checkPhoneOfferingName2&&checkPhoneOfferingName3&&checkStatus&&
				checkChargeType&&checkPhoneSrlNbr&&checkVoiceCallWaitOffName&&checkVoiceMailOfferingName&&
				checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkDealName_1&&checkDealName_2&&checkDealName_3&&
				checkCnvgSrlNbr&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_44");
		return validate;
	}

	public boolean getActualModifyInternetConvrgdNcToHpsa(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Modify [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualModifyInternetConvrgdNcToHpsa:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualModifyInternetConvrgdNcToHpsa");
		return validate;
	}

	public boolean getActualModifyNcToJms_32(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualModifyNcToJms_32:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,convergedSerialNbr);
		boolean checkReason=validateAttributeValue(actualNcToJmsString,"Add");
		boolean checkDeviceType=validateAttributeValue(actualNcToJmsString,"Converged");
		boolean checkOfferingId=validateAttributeValue(actualNcToJmsString,"9145139488513389925");

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&
				checkBoxSerialNbr&&checkReason&&checkDeviceType&&checkOfferingId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualModifyNcToJms_32");
		return validate;
	}

	public boolean getActualNewNcToJms_32(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewNcToJms_32:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"NEW");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJmsString,convergedSerialNbr);
		boolean checkReason=validateAttributeValue(actualNcToJmsString,"Add");
		boolean checkDeviceType=validateAttributeValue(actualNcToJmsString,"Converged");
		boolean checkOfferingId=validateAttributeValue(actualNcToJmsString,"9149616590013404295");

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkOwnership&&
				checkBoxSerialNbr&&checkReason&&checkDeviceType&&checkOfferingId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewNcToJms_32");
		return validate;
	}

	public boolean getActualNcToBrm_32(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_32");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkInternetLOB=validateAttributeValue(actualNcToBrmString,"Internet");
		boolean checkInternetOfferingName1=validateAttributeValue(actualNcToBrmString,"Internet 75");
		boolean checkStatus1=validateAttributeValue(actualNcToBrmString,"Canceled");
		boolean checkInternetOfferingName2=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkStatus2=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Internet Installation Fee");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);

		if (checkAccountId&&checkAccountNbr&&checkInternetLOB&&checkStatus1&&checkInternetOfferingName1&&checkStatus1&&
				checkInternetOfferingName2&&checkStatus2&&checkChargeType&&checkDealName_1&&checkDealName_2&&checkCnvgSrlNbr&&
				checkDealName_3&&checkDealName_4&&checkDealName_5)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_32");
		return validate;
	}

	//kirti

	//suspend TV
	public boolean getActualPremiseMoveSuspendTVRFS_53(String acctNbr) 
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Suspend [SOM] TV Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualPremiseMoveSuspendTVRFS_53:" + actualNcToHpsaString);
		if(actualNcToHpsaString!=null)
		{
			boolean checkSpecID = validateAttributeValue(actualNcToHpsaString,acctNbr);
			boolean checkSuspendStatus=validateAttributeValue(actualNcToHpsaString,"SUSPEND");

			if (checkSpecID&&checkSuspendStatus)
				validate=true;
		}

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualPremiseMoveSuspendTVRFS_53");
		return validate;
	}

	//suspend Phone
	public boolean getActualPremiseMoveSuspendPhoneRFS_53(String acctNbr,String phoneNbr) 
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Suspend [SOM] Phone Line RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualPremiseMoveSuspendPhoneRFS_53:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkSuspendStatus=validateAttributeValue(actualNcToHpsaString,"SUSPEND"); 
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"115");
		if (checAcctNbr&&checkAddrId&&checkSuspendStatus&&checkPhoneNbr&&checkSvcName&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualPremiseMoveSuspendPhoneRFS_53");
		return validate;
	}

	//suspend Internet
	public boolean getActualPremiseMoveSuspendInternetRFS_53(String acctNbr) 
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Suspend [SOM] Internet Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualPremiseMoveSuspendInternetRFS_53:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkSuspendStatus=validateAttributeValue(actualNcToHpsaString,"SUSPEND"); 
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");
		if (checAcctNbr&&checkAddrId&&checkSuspendStatus&&checkSvcName&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualPremiseMoveSuspendInternetRFS_53");
		return validate;
	}

	public boolean getActualPremiseMoveResumeTVRFS_53(String acctNbr,String tvBoxSerialNbr,String tvPortalSerialNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Resume [SOM] TV Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualPremiseMoveResumeTVRFS_53:" + actualNcToHpsaString);
		boolean checkBoxSerialNbr=false;
		if(actualNcToHpsaString.contains("4xgateway"))
		{
			checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		}else if(actualNcToHpsaString.contains("5xportal"))
		{
			checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);
		}
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"2777");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"RESUME");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId4=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkSvcSpecId5=validateAttributeValue(actualNcToHpsaString,"105");
		boolean checkSvcSpecId6=validateAttributeValue(actualNcToHpsaString,"106");
		boolean checkSvcSpecId7=validateAttributeValue(actualNcToHpsaString,"107");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");
		if (checAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcSpecId3&&
				checkSvcSpecId4&&checkSvcSpecId5&&checkSvcSpecId6&&checkSvcSpecId7&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualPremiseMoveResumeTVRFS_53");
		return validate;
	}

	public boolean getActualSOMPhoneCFSOrderReport_72()
	{
		boolean validate = false;
		String actualNcToRequestFlowString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "New [SOM] Phone Line CFS Order", "<E911_RESPONSE xmlns" );
		log.debug("Entering getActualSOMPhoneCFSOrderReport_72:" + actualNcToRequestFlowString);
		boolean checPortOption = validateAttributeValue(actualNcToRequestFlowString,"PortOption");    
		if (checPortOption)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualSOMPhoneCFSOrderReport_72");
		return validate;    
	}

	public boolean getActualNewDistinctiveRFSOrderReport_72(String acctNbr,String portPhoneNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "New [SOM] Distinctive Ring RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewDistinctiveRFSOrderReport_72:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkRingTone=validateAttributeValue(actualNcToHpsaString,"RingTone2");
		boolean checkSecDistinctive=validateAttributeValue(actualNcToHpsaString,"SECONDARY_DN");
		boolean checkPortPhoneNbr=validateAttributeValue(actualNcToHpsaString,portPhoneNbr);
		boolean checkSpecId=validateAttributeValue(actualNcToHpsaString,"116");

		if (checAcctNbr&&checkAddrId&&checkRingTone&&checkSpecId&&checkSecDistinctive&&checkPortPhoneNbr)
			validate=true;  

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewDistinctiveRFSOrderReport_72");
		return validate;   
	}

	public boolean getActualNewCustNcToBrm72(String acctNbr,String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNewCustNcToBrm72");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkDistinctiveRing=validateAttributeValue(actualNcToBrmString,"Distinctive Ring");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDoblePay=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkInternetOfferingName&&
				checkDealName_1&&checkDealName_2&&checkDistinctiveRing&&checkDoblePay&&
				checkCnvgSrlNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewCustNcToBrm72");
		return validate;
	}

	public boolean getActualPremiseMoveResumeInternetRFS_53(String acctNbr,String convergedSerialNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Resume [SOM] Internet Provisioning RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualPremiseMoveResumeInternetRFS_53:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"2777");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"RESUME");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData"); 
		if (checAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualPremiseMoveResumeInternetRFS_53");
		return validate;
	}


	public boolean getActualNewPhoneCnvrgdLineRFS(String acctNbr,String phoneNbr,String convergedSerialNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "New [SOM] Phone Line RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewPhoneCnvrgdLineRFS:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewPhoneCnvrgdLineRFS");
		return validate;
	}

	public boolean getActualNewPhoneCnvrgdLineRFS_PremiseMove(String acctNbr,String phoneNbr,String convergedSerialNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "New [SOM] Phone Line RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewPhoneCnvrgdLineRFS_PremiseMove:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"2777");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewPhoneCnvrgdLineRFS_PremiseMove");
		return validate;
	}

	public String getExpectedPremiseMove_ModifyCustXml(String acctNbr,String phoneNbr1,String phoneNbr2,
			String convergedSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		String actualPath = Utility.getValueFromPropertyFile( "expected_xml_path" );
		try
		{
			log.debug("Entering getExpectedPremiseMove_ModifyCustXml");
			File xmlFile = new File(actualPath);
			for (File fileEntry : xmlFile.listFiles()) 
			{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				dbFactory.setNamespaceAware(true);
				DocumentBuilder dBuilder;
				if(fileEntry.getName().equals( "PremiseMoveModCustOrder_NC_JMS.xml" ))
				{
					dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fileEntry);
					doc.getDocumentElement().normalize();

					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "accountNumber", acctNbr );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "accountId", "1"+acctNbr );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "instanceId", "null" );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "locationTimeZone", "null" );
					UtilityXML.updateAttributeValue( doc, "CustomerOrder", "orderId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "telephoneNumber", phoneNbr2 );
					UtilityXML.updateElementValue( doc, "PhoneLineOrder", "phoneNumberType", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalPhoneHardwareOrder", "serialNumber", convergedSerialNbr);
					UtilityXML.updateElementValue( doc, "DigitalPhoneHardwareOrder", "technicianId", "null" );
					UtilityXML.updateElementValue( doc, "VoicemailOrder", "offeringId", "null" );

					UtilityXML.updateElementValue( doc, "DisconnectPhoneLineOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectPhoneLineOrder", "telephoneNumber", phoneNbr1 );
					UtilityXML.updateElementValue( doc, "DisconnectPhoneLineOrder", "phoneNumberType", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalPhoneHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DisconnectDigitalPhoneHardwareOrder", "serialNumber", convergedSerialNbr);

					UtilityXML.updateElementValue( doc, "DisconnectVoicemailOrder", "offeringId", "null" );

					UtilityXML.updateElementValue( doc, "InternetOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "InternetHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "InternetHardwareOrder", "serialNumber", convergedSerialNbr);

					UtilityXML.updateElementValue( doc, "DigitalTVOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder", "offeringId", "null" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", tvBoxSerialNbr,"4xgateway" );
					UtilityXML.updateElementValue( doc, "DigitalTVHardwareOrder","serialNumber", tvPortalSerialNbr,"5xportal" );

					expectedStringXmlVal=UtilityXML.convertDocumentToString(doc);

					String tempPath = Utility.getValueFromPropertyFile( "temp_xml_path" );
					tempPath = tempPath.replace( "\\", "//" );
					String newStringName=StringUtils.substringBefore( fileEntry.getName(), "." );
					newStringName=newStringName+"_Expected.xml";
					UtilityXML.storeXmlFileForExpectedFile(doc,fileEntry.getName(),tempPath+"//"+newStringName);
				}
			}
			log.debug("Leaving getExpectedPremiseMove_ModifyCustXml");
		} catch (SAXException | ParserConfigurationException | IOException e) {
			log.error("Error in getExpectedPremiseMove_ModifyCustXml:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return expectedStringXmlVal;
	}

	public String getActualPremiseMove_ModifyCustXml()
	{
		String actualNcToHpsaString = "";
		try
		{
			String modifyNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
			String modStatus = UtilityXML.getAttributeValue( UtilityXML.convertStringToDocument( modifyNcToJMSString ), "CustomerOrder", "status" );

			if(modStatus!=null && modStatus.equalsIgnoreCase( "Completed" ))
			{
				actualNcToHpsaString = modifyNcToJMSString;
			}
			log.debug("Entering getActualPremiseMove_ModifyCustXml:" + actualNcToHpsaString);

			Document actualDoc=UtilityXML.convertStringToDocument(actualNcToHpsaString);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			actualDoc.getDocumentElement().normalize();
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "instanceId", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "locationTimeZone", "null" );
			UtilityXML.updateAttributeValue( actualDoc, "CustomerOrder", "orderId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalPhoneOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "PhoneLineOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "PhoneLineOrder", "phoneNumberType", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalPhoneHardwareOrder", "offeringId", "null" );

			UtilityXML.updateElementValue( actualDoc, "DigitalPhoneHardwareOrder", "technicianId", "null" );
			UtilityXML.updateElementValue( actualDoc, "VoicemailOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectPhoneLineOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectPhoneLineOrder", "phoneNumberType", "null" );

			UtilityXML.updateElementValue( actualDoc, "DisconnectDigitalPhoneHardwareOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DisconnectVoicemailOrder", "offeringId", "null" );

			UtilityXML.updateElementValue( actualDoc, "InternetOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "InternetHardwareOrder", "offeringId", "null" );

			UtilityXML.updateElementValue( actualDoc, "DigitalTVOrder", "offeringId", "null" );
			UtilityXML.updateElementValue( actualDoc, "DigitalTVHardwareOrder", "offeringId", "null" );


			actualStringXmlVal=UtilityXML.convertDocumentToString(actualDoc);

			UtilityXML.storeXmlFileForActual(actualDoc,"PremiseMoveModCustOrder_NC_JMS_Actual.xml");

			log.debug("Leaving getActualPremiseMove_ModifyCustXml");
		} catch (Exception e) {
			log.error("Error in getActualPremiseMove_ModifyCustXml:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return actualStringXmlVal;
	}

	public boolean getActualDistinctiveRingModifyCustOrderReport()
	{
		boolean validate = false; 
		String actualNcToInventoryString = Utility.getXMLString( driver, "t", "NETCRACKER","INVENTORY_TELEPHONE", "Modify Customer Order", "<reservation xmlns" );
		log.debug("Entering getActualDistinctiveRingModifyCustOrderReport:" + actualNcToInventoryString);
		boolean checStatus = validateAttributeValue(actualNcToInventoryString,"Ordered");   
		if (checStatus)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDistinctiveRingModifyCustOrderReport");
		return validate;    
	}

	public boolean getActualDistinctiveRingNewCustOrderReport(String phoneNbr)
	{
		boolean validate = false;
		String actualNcToRequestFlowString = Utility.getXMLString( driver, "t", "REQUEST_FLOW","NETCRACKER", "New Phone Line CFS Order", "<E911_RESPONSE xmlns" );
		log.debug("Entering getActualDistinctiveRingNewCustOrderReport:" + actualNcToRequestFlowString);
		boolean checStatus = validateAttributeValue(actualNcToRequestFlowString,"SUCCESS");    
		boolean checPhoneNbr = validateAttributeValue(actualNcToRequestFlowString,phoneNbr);
		boolean checE911 = validateAttributeValue(actualNcToRequestFlowString,"E911_RESPONSE");    
		if (checStatus&&checPhoneNbr&&checE911)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDistinctiveRingNewCustOrderReport");
		return validate;    
	}

	public boolean getActualDoubleNcToBrm_37(String acctNbr,String phoneNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualDoubleNcToBrm_37");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName1=validateAttributeValue(actualNcToBrmString,"Personal Phone");  
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkVoiceCallWaitOffName=validateAttributeValue(actualNcToBrmString,"Call Waiting");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkPhoneSvcOfferingName=validateAttributeValue(actualNcToBrmString,"Home Phone Voicemail and Call Waiting");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName1&&checkStatus&&checkChargeType&&checkVoiceCallWaitOffName&&
				checkPhoneSvcOfferingName&&checkInternetOfferingName&&checkDealName_1&&checkDealName_2&&checkDealName_3&&
				checkCnvgSrlNbr&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDoubleNcToBrm_37");
		return validate;
	}

	public boolean getActualNcToJms_53(String acctNbr,String convergedSerialNbr, String phoneNbr1, String phoneNbr2)
	{
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNcToJms_53:" + actualNcToJmsString);
		boolean validate =false;
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checklocId=validateAttributeValue(actualNcToJmsString,"2777");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkPhnNbr1=validateAttributeValue(actualNcToJmsString,phoneNbr1);
		boolean checkPhnNbr2=validateAttributeValue(actualNcToJmsString,phoneNbr2);
		boolean checkConverged=validateAttributeValue(actualNcToJmsString,"Converged");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkDigitialPhoneOrder=validateAttributeValue(actualNcToJmsString,"DigitalPhoneOrder");
		boolean checkTransfer=validateAttributeValue(actualNcToJmsString,"Transfer");    	
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");    	
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");    	
		boolean checkInternetSerialNbr=validateAttributeValue(actualNcToJmsString,convergedSerialNbr);
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkReason=validateAttributeValue(actualNcToJmsString,"Add");

		if (checkAcctNbr&&checkAcctId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkInternetSerialNbr&&
				checkOwnership&&checkReason&&checklocId&&checkPhnNbr1&&checkPhnNbr2&&checkConverged&&checkDigitialPhoneOrder&&checkTransfer)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToJms_53");
		return validate;
	}

	public boolean getActualNewWifiRfsNcToHpsa_60(String acctNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Wifi Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewWifiRfsNcToHpsa_60:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkSvcSpecName=validateAttributeValue(actualNcToHpsaString,"Wifi");
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"110");
		boolean checkEscSvcId=validateAttributeValue(actualNcToHpsaString,"16176");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkSvcSpecName&&checkSvcSpecId&&checkEscSvcId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewWifiRfsNcToHpsa_60");
		return validate;
	}

	public boolean getActualNcToBrm_SinglePlay60(String acctNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_SinglePlay60");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);

		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkAdvanceWiFiModem=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkAdvanceWiFiModemRental=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Internet Installation Fee");

		if (checkAccountId&&checkAccountNbr&&checkInternetOfferingName&&checkStatus&&checkChargeType&&checkAdvanceWiFiModemRental&&
				checkInternetOfferingName&&checkDealName_3&&checkAdvanceWiFiModem)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_SinglePlay60");
		return validate;
	}

	public boolean getActualSOMModifyPhoneRFSOrderReport(String acctNbr,String phoneNbr, String convergedSerialNbr)
	{
		boolean validate = false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "Modify [SOM] Phone Line RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualSOMModifyPhoneRFSOrderReport:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcName&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualSOMModifyPhoneRFSOrderReport");
		return validate;
	}

	public boolean getActualNewInternetCnvrgdNcToHpsa_28(String acctNbr, String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetCnvrgdNcToHpsa_28:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,internetHardwareSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1002");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");
		boolean checkCharName=validateAttributeValue(actualNcToHpsaString,"NumberOfIPAddresses");
		boolean checkNoOfIpAddress=validateAttributeValue(actualNcToHpsaString,"1");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkCharName&&checkNoOfIpAddress)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetCnvrgdNcToHpsa_28");
		return validate;
	}

	public boolean getActualCancelWifiRfsNcToHpsa_28(String acctNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Cancel New [SOM] Wifi Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualCancelWifiRfsNcToHpsa_28:"+actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkSvcSpecName=validateAttributeValue(actualNcToHpsaString,"Wifi");
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"110");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkSvcSpecName&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualCancelWifiRfsNcToHpsa_28");
		return validate;
	}

	public boolean getActualNcToJms_28(String acctNbr,String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualNcToJmsString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNcToJms_28:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkInternetSerialNbr=validateAttributeValue(actualNcToJmsString,internetHardwareSerialNbr);
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkReason=validateAttributeValue(actualNcToJmsString,"Add");

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&checkInternetSerialNbr&&
				checkOwnership&&checkReason)
			validate=true;

		log.debug("Leaving getActualNcToJms_28");
		return validate;
	}

	public boolean getActualNewCustInvToNc_28(String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualInvToNcString=Utility.getXMLString( driver, "t","INVENTORY","NETCRACKER","New Internet Hardware CFS Order","<type>Customer</type>" );
		log.debug("Entering getActualNewCustInvToNc_28:" + actualInvToNcString);
		boolean checkEquipType=validateAttributeValue(actualInvToNcString,"Modem");
		boolean checkPrvsnKey=validateAttributeValue(actualInvToNcString,internetHardwareSerialNbr);
		boolean checkIsRental=validateAttributeValue(actualInvToNcString,"false");

		if (checkEquipType&&checkPrvsnKey&&checkIsRental)
			validate=true;

		log.debug("Leaving getActualNewCustInvToNc_28");
		return validate;
	}

	public boolean getActualNcToBrm_28(String acctNbr,String phoneNbr,String phoneHardwareSerialNbr,String tvHardwareSerialNbr,
			String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_28");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkPhoneOfferingName=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkInternetOfferingName=validateAttributeValue(actualNcToBrmString,"Internet 75");
		boolean checkTvOfferingName=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental - Hitron");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Double Play Installation Fee");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"DCT 700 (599/594)");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Home Phone Terminal");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Cable Equipment Rental - Motorola");
		boolean checkDealName_9=validateAttributeValue(actualNcToBrmString,"SD");
		boolean checkPhoneSrlNbr=validateAttributeValue(actualNcToBrmString,phoneHardwareSerialNbr);
		boolean checkTVSrlNbr=validateAttributeValue(actualNcToBrmString,tvHardwareSerialNbr);
		boolean checkInternetSrlNbr=validateAttributeValue(actualNcToBrmString,internetHardwareSerialNbr);
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkPhoneOfferingName&&checkStatus&&checkChargeType&&checkInternetOfferingName&&
				checkTvOfferingName&&checkDealName_1&&checkDealName_2&&checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&
				checkDealName_7&&checkDealName_8&&checkDealName_9&&checkPhoneSrlNbr&&checkTVSrlNbr&&checkInternetSrlNbr&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_28");
		return validate;
	}

	//ramesh
	public boolean getActualNewInternetProvisioningRFSNcToHpsa_61(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetProvisioningRFSNcToHpsa_61:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1004");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");
		boolean checkIPAdrsName=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkServiceId=validateAttributeValue(actualNcToHpsaString,"ServiceId");
		boolean checkESCatalogueServiceId=validateAttributeValue(actualNcToHpsaString,"990");
		boolean checkESCatalogueServiceId1=validateAttributeValue(actualNcToHpsaString,"55");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkIPAdrsName&&checkServiceId&&checkESCatalogueServiceId&&checkESCatalogueServiceId1)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetProvisioningRFSNcToHpsa_61");
		return validate;
	}

	public boolean getActualNewCustOrderNcToJMS_61(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","New Customer Order","<ns2:CustomerOrder" );
		log.debug("Entering getActualNewInternetProvisioningRFSNcToJMS_61:" + actualNcToJMSString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJMSString,acctNbr);
		boolean checkorderStatus=validateAttributeValue(actualNcToJMSString,"provisioned");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToJMSString,convergedSerialNbr);
		boolean checkownership=validateAttributeValue(actualNcToJMSString,"rented");
		boolean checkreason=validateAttributeValue(actualNcToJMSString,"Add");
		boolean checkdeviceType=validateAttributeValue(actualNcToJMSString,"Converged");
		boolean checkOrderStatus=validateAttributeValue(actualNcToJMSString,"pending");

		if (checkAcctNbr&&checkorderStatus&&checkBoxSerialNbr&&checkownership&&checkreason&&checkdeviceType&&checkOrderStatus)
			validate=true;

		log.debug("Leaving getActualNewInternetProvisioningRFSNcToJMS_61");
		return validate;
	}

	public boolean getActualNcToBrm_61(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order ","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_61");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkCnvgSrlNbr=validateAttributeValue(actualNcToBrmString,convergedSerialNbr);
		boolean checkStatus2=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkofferingName=validateAttributeValue(actualNcToBrmString,"HHS Entitlement");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Internet Installation Fee");
		boolean checkInternetOfferingName2=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");

		if (checkAccountId&&checkAccountNbr&&checkofferingName&&checkInternetOfferingName2&&checkStatus2&&checkChargeType&&checkDealName_1&&checkDealName_2&&checkCnvgSrlNbr&&checkDealName_3&&checkDealName_4)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_61");
		return validate;
	}

	//TC-21:

	public boolean getActualDisconnectTVNcToHpsa_11(String acctNbr, String tvBoxSerialNbr,String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString="";
		String tvBoxString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] TV Provisioning RFS Order","4xgateway" );
		String tvPortalString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] TV Provisioning RFS Order","5xportal" );

		if(!tvBoxString.equals("")&&tvPortalString.equals(""))
			actualNcToHpsaString=tvBoxString;
		else
			actualNcToHpsaString=tvPortalString;
		log.debug("Entering getActualDisconnectTVNcToHpsa_11:" + actualNcToHpsaString);
		boolean checkTvSerialNbr=false;

		if(actualNcToHpsaString.contains("4xgateway"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		else if(actualNcToHpsaString.contains("5xportal"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);

		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1000");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkTvSerialNbr&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDisconnectTVNcToHpsa_11");
		return validate;
	}

	public boolean getActualCustOrderNcJMS_11(String acctNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder");
		log.debug("Entering getActualCustOrderNcJMS_11:" + actualNcToHpsaString);
		boolean checkTvSerialNbr=false;

		if(actualNcToHpsaString.contains("4xgateway"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvBoxSerialNbr);
		else if(actualNcToHpsaString.contains("5xportal"))
			checkTvSerialNbr=validateAttributeValue(actualNcToHpsaString,tvPortalSerialNbr);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToHpsaString,1+acctNbr);
		boolean checkOrderAim=validateAttributeValue(actualNcToHpsaString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToHpsaString,"Completed");
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"Remove");
		boolean checkwonership=validateAttributeValue(actualNcToHpsaString,"rented");
		boolean checkOrderStatus=validateAttributeValue(actualNcToHpsaString,"deprovisioned");

		if (checkAcctNbr&&checkAcctId&&checkOrderAim&&checkStatus&&checkAddrId&&checkAction&&checkTvSerialNbr
				&&checkwonership&&checkOrderStatus)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualCustOrderNcJMS_11");
		return validate;
	}

	public boolean getActualNcToBrm_11(String acctNbr, String tvBoxSerialNbr, String tvPortalSerialNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_11");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkOfferingName1=validateAttributeValue(actualNcToBrmString,"Call Waiting");
		boolean checkOfferingName2=validateAttributeValue(actualNcToBrmString,"Voicemail & Call Waiting");
		boolean checkOfferingName3=validateAttributeValue(actualNcToBrmString,"Personal Phone");
		boolean checkOfferingName4=validateAttributeValue(actualNcToBrmString,"HHS Entitlement");
		boolean checkOfferingName5=validateAttributeValue(actualNcToBrmString,"Rental XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkOfferingName6=validateAttributeValue(actualNcToBrmString,"XB6 DOCSIS 3.1 WiFi Modem");
		boolean checkDealName_1=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV Devices");
		boolean checkDealName_2=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkDealName_3=validateAttributeValue(actualNcToBrmString,"Home Phone Voicemail and Call Waiting");
		boolean checkDealName_4=validateAttributeValue(actualNcToBrmString,"Hardware");
		boolean checkDealName_5=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental [SERIAL NUMBER]");
		boolean checkDealName_6=validateAttributeValue(actualNcToBrmString,"Voicemail"); 
		boolean checkDealName_7=validateAttributeValue(actualNcToBrmString,"Personal Phone [DIGITAL PHONE]");
		boolean checkDealName_8=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal Rental [SERIAL NUMBER]");
		boolean checkDealName_9=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV portal (416)");
		boolean checkDealName_10=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box (526)");
		boolean checkDealName_11=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
		boolean checkDealName_12=validateAttributeValue(actualNcToBrmString,"Shaw BlueSky TV box Rental");
		boolean checkDealName_13=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkDealName_14=validateAttributeValue(actualNcToBrmString,"TVE");
		boolean checkDealName_15=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkDealName_16=validateAttributeValue(actualNcToBrmString,"MDVR");
		boolean checkDealName_17=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkDealName_18=validateAttributeValue(actualNcToBrmString,"TVOD");
		boolean checkDealName_19=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental");
		boolean checkBoxSrlNbr=validateAttributeValue(actualNcToBrmString,tvBoxSerialNbr);
		boolean checkPortalSrlNbr=validateAttributeValue(actualNcToBrmString,tvPortalSerialNbr);


		if (checkAccountId&&checkAccountNbr&&checkOfferingName1&&checkOfferingName2&&checkOfferingName3&&checkOfferingName4&&
				checkOfferingName5&&checkDealName_1&&checkDealName_1&&checkBoxSrlNbr&&checkPortalSrlNbr&&checkChargeType&&checkDealName_2&&checkOfferingName6&&
				checkDealName_3&&checkDealName_4&&checkDealName_5&&checkDealName_6&&checkDealName_7&&checkDealName_8&&
				checkDealName_9&&checkDealName_10&&checkDealName_11&&checkDealName_12&&checkDealName_13&&checkDealName_14&&checkDealName_15&&
				checkDealName_16&&checkDealName_17&&checkDealName_18&&checkDealName_19)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_11");
		return validate;
	}

	//23rd case
	public boolean getActualDisconnectInternetNcToHpsa1_23(String acctNbr, String internetSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Internet Provisioning RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectInternetNcToHpsa1_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,internetSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1002");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		log.debug("Leaving getActualDisconnectInternetNcToHpsa1_23");
		return validate;
	}

	public boolean getActualDisconnectInternetNcToHpsa2_23(String acctNbr, String internetSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Internet Provisioning RFS Order","REMOVE" );
		log.debug("Entering getActualDisconnectInternetNcToHpsa2_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,internetSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1002");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		log.debug("Leaving getActualDisconnectInternetNcToHpsa2_23");
		return validate;
	}

	public boolean getActualDisconnectPhoneNcToHpsa1_23(String acctNbr,String phoneNbr, String phoneSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","DETACH" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa1_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction1=validateAttributeValue(actualNcToHpsaString,"DETACH");
		boolean checkAction2=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,phoneSerialNbr);
		boolean checkEscSvcId1=validateAttributeValue(actualNcToHpsaString,"3");
		boolean checkEscSvcId2=validateAttributeValue(actualNcToHpsaString,"14");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1003");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checkAcctNbr&&checkAddrId&&checkAction1&&checkAction2&&checkBoxSerialNbr&&checkEscSvcId1&&checkEscSvcId2&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa1_23");
		return validate;
	}

	public boolean getActualDisconnectPhoneNcToHpsa2_23(String acctNbr, String phoneSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] Phone Line RFS Order","REMOVE" );
		log.debug("Entering getActualDisconnectPhoneNcToHpsa2_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,phoneSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(actualNcToHpsaString,"1003");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		log.debug("Leaving getActualDisconnectPhoneNcToHpsa2_23");
		return validate;
	}

	public boolean getActualDisconnectTVNcToHpsa_23(String acctNbr, String tvDctSerialNbr)
	{
		boolean validate =false;
		String boxOrPortalString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","Disconnect [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualDisconnectTVNcToHpsa_23:" + boxOrPortalString);
		boolean checkAcctNbr=validateAttributeValue(boxOrPortalString,acctNbr);
		boolean checkAddrId=validateAttributeValue(boxOrPortalString,"221");
		boolean checkAction=validateAttributeValue(boxOrPortalString,"REMOVE");
		boolean checkBoxSerialNbr=validateAttributeValue(boxOrPortalString,tvDctSerialNbr);
		boolean checkSvcSpecId=validateAttributeValue(boxOrPortalString,"1000");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualDisconnectTVNcToHpsa_23");
		return validate;
	}

	public boolean getActualNewInternetNcToHpsa_23(String acctNbr, String internetHardwareSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetNcToHpsa_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,internetHardwareSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1002");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");
		boolean checkCharName=validateAttributeValue(actualNcToHpsaString,"NumberOfIPAddresses");
		boolean checkNoOfIpAddress=validateAttributeValue(actualNcToHpsaString,"1");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkCharName&&checkNoOfIpAddress)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetNcToHpsa_23");
		return validate;
	}

	public boolean getActualNewPhoneLineRFS_23(String acctNbr,String phoneNbr,String phoneSerialNbr)
	{
		boolean validate = false; 
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","HPSA_JMS", "New [SOM] Phone Line RFS Order", "<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewPhoneLineRFS_23:" + actualNcToHpsaString);
		boolean checAcctNbr = validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,phoneSerialNbr);
		boolean checkEscSvcId1=validateAttributeValue(actualNcToHpsaString,"3");
		boolean checkEscSvcId2=validateAttributeValue(actualNcToHpsaString,"14");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1003");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"115");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"FIXED_LINE");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToHpsaString,phoneNbr);

		if (checAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkEscSvcId1&&checkEscSvcId2&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewPhoneLineRFS_23");
		return validate;
	}

	public boolean getActualNewTVProvisioningRFSNcToHpsa_23(String acctNbr, String tvSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewTVProvisioningRFSNcToHpsa_23:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,tvSerialNbr);
		boolean checkESCatalogueServiceId1=validateAttributeValue(actualNcToHpsaString,"560");
		boolean checkESCatalogueServiceId2=validateAttributeValue(actualNcToHpsaString,"677");
		boolean checkESCatalogueServiceId3=validateAttributeValue(actualNcToHpsaString,"961");
		boolean checkCharacteristicValue1=validateAttributeValue(actualNcToHpsaString,"103");
		boolean checkCharacteristicValue2=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"102");
		boolean checkSvcSpecId4=validateAttributeValue(actualNcToHpsaString,"119");
		boolean checkIPAdrsName=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkSvcName2=validateAttributeValue(actualNcToHpsaString,"VOD");
		boolean checkSvcName3=validateAttributeValue(actualNcToHpsaString,"PPV");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcSpecId3&&
				checkSvcSpecId4&&checkCharacteristicValue1&&checkCharacteristicValue2&&checkIPAdrsName&&checkESCatalogueServiceId1&&
				checkESCatalogueServiceId2&&checkESCatalogueServiceId3&&checkSvcName1&&checkSvcName2&&checkSvcName3)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewTVProvisioningRFSNcToHpsa_23");
		return validate;
	}

	public boolean getActualNcToJms_23(String acctNbr,String phoneNbr,String phoneSerialNbr, String diffPhoneSrlNbr, 
			String internetSerialNbr, String diffInternetSrlNbr, String tvSerialNbr, String diffTvSrlNbr)
	{
		boolean validate =false;
		String actualNcToJmsString="";
		String disconnectNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Disconnect Phone Hardware CFS Order","<ns2:CustomerOrder" );
		String modifyNcToJMSString=Utility.getXMLString( driver, "t","NETCRACKER","JMS","Modify Customer Order","<ns2:CustomerOrder" );
		String disStatus = UtilityXML.getAttributeValue( UtilityXML.convertStringToDocument( disconnectNcToJMSString ), "CustomerOrder", "status" );
		String modStatus = UtilityXML.getAttributeValue( UtilityXML.convertStringToDocument( modifyNcToJMSString ), "CustomerOrder", "status" );

		if(disStatus!=null && disStatus.equalsIgnoreCase( "Completed" ) )
		{
			actualNcToJmsString = disconnectNcToJMSString;
		}
		if(modStatus!=null && modStatus.equalsIgnoreCase( "Completed" ))
		{
			actualNcToJmsString = modifyNcToJMSString;
		}
		log.debug("Entering getActualNcToJms_23:" + actualNcToJmsString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToJmsString,acctNbr);
		boolean checkAcctId=validateAttributeValue(actualNcToJmsString,"1"+acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToJmsString,"221");
		boolean checkOrderAim=validateAttributeValue(actualNcToJmsString,"MODIFY");
		boolean checkStatus=validateAttributeValue(actualNcToJmsString,"Completed");
		boolean checkOrderStatus1=validateAttributeValue(actualNcToJmsString,"deprovisioned");
		boolean checkOrderStatus2=validateAttributeValue(actualNcToJmsString,"provisioned");
		boolean checkOrderStatus3=validateAttributeValue(actualNcToJmsString,"deprovisioningpending");
		boolean checkOrderStatus4=validateAttributeValue(actualNcToJmsString,"pending");
		boolean checkPhoneSerialNbr1=validateAttributeValue(actualNcToJmsString,diffPhoneSrlNbr);
		boolean checkPhoneSerialNbr2=validateAttributeValue(actualNcToJmsString,phoneSerialNbr);
		boolean checkInternetSerialNbr1=validateAttributeValue(actualNcToJmsString,internetSerialNbr);
		boolean checkInternetSerialNbr2=validateAttributeValue(actualNcToJmsString,diffInternetSrlNbr);
		boolean checkTvSerialNbr1=validateAttributeValue(actualNcToJmsString,tvSerialNbr);
		boolean checkTvSerialNbr2=validateAttributeValue(actualNcToJmsString,diffTvSrlNbr);
		boolean checkOwnership=validateAttributeValue(actualNcToJmsString,"rented");
		boolean checkReason1=validateAttributeValue(actualNcToJmsString,"Order Swap");
		boolean checkReason2=validateAttributeValue(actualNcToJmsString,"Re-assign");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToJmsString,phoneNbr);

		if (checkAcctNbr&&checkAcctId&&checkAddrId&&checkOrderAim&&checkStatus&&checkOrderStatus1&&checkOrderStatus2&&
				checkOrderStatus3&&checkOrderStatus4&&checkPhoneSerialNbr1&&checkPhoneSerialNbr2&&checkInternetSerialNbr1&&
				checkInternetSerialNbr2&&checkTvSerialNbr1&&checkTvSerialNbr2&&checkOwnership&&checkReason1&&checkReason2&&checkPhoneNbr)
			validate=true;

		log.debug("Leaving getActualNcToJms_23");
		return validate;
	}

	public boolean getActualNcToInv1_23(String phoneSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New Phone Hardware CFS Order","Request URI" );
		log.debug("Entering getActualNcToInv1_23:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("DPT"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, phoneSerialNbr );
				}
			}
		}
		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+ phoneSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv1_23");
		return ncToInvUrl;
	}

	public boolean getActualNcToInv2_23(String internetSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New Internet Hardware CFS Order","Request URI" );
		log.debug("Entering getActualNcToInv2_23:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("3nacmodem"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, internetSerialNbr );
				}
			}
		}
		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+ internetSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv2_23");
		return ncToInvUrl;
	}

	public boolean getActualNcToInv3_23(String tvSerialNbr)
	{
		boolean ncToInvUrl =false;
		try
		{
			String actualNcToInvString=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","Request URI" );
			log.debug("Entering getActualNcToInv3_23:" + actualNcToInvString);
			if(actualNcToInvString.contains("Request URI"))
			{
				actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
				actualNcToInvString=actualNcToInvString.trim();
				if(actualNcToInvString.contains("cpe"))
				{
					if(actualNcToInvString.contains("3510dct"))
					{
						String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

						actualNcToInvString = actualNcToInvString.replace( s1, tvSerialNbr );
					}
				}
			}
			String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+ tvSerialNbr ;
			if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
			{
				ncToInvUrl=true;
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving getActualNcToInv3_23");
		} catch (Exception e) {
			log.error("Error in getActualNcToInv3_23:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,e.getMessage());
		}
		return ncToInvUrl;
	}

	public boolean getActualNcToBrm_23(String acctNbr, String phoneNbr,String phoneSerialNbr, String diffPhSrlNbr, 
			String internetSerialNbr, String diffIntSrlNbr, String tvSerialNbr, String diffTvSrlNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","Modify Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_23");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkDealName1=validateAttributeValue(actualNcToBrmString,"Hitron AC DOCSIS 3N Advanced WiFi Modem (327/326)");
		boolean checkDealName2=validateAttributeValue(actualNcToBrmString,"Advanced WiFi Modem Rental - Hitron");
		boolean checkDealName3=validateAttributeValue(actualNcToBrmString,"Internet 150 Unlimited");
		boolean checkDealName4=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkDealName5=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkDealName6=validateAttributeValue(actualNcToBrmString,"Cable Equipment Rental - Motorola");
		boolean checkDealName7=validateAttributeValue(actualNcToBrmString,"Tru2way Guide Entitlements");
		boolean checkDealName8=validateAttributeValue(actualNcToBrmString,"Triple Play Installation Fee");
		boolean checkDealName9=validateAttributeValue(actualNcToBrmString,"HD PVR MPEG4");
		boolean checkDealName10=validateAttributeValue(actualNcToBrmString,"DCX 3510 HD Guide (522/521)");
		boolean checkDealName11=validateAttributeValue(actualNcToBrmString,"Call Waiting");
		boolean checkDealName12=validateAttributeValue(actualNcToBrmString,"Phone Equipment Rental - Phone Terminal");
		boolean checkDealName13=validateAttributeValue(actualNcToBrmString,"Voicemail");
		boolean checkDealName14=validateAttributeValue(actualNcToBrmString,"Home Phone Terminal");
		boolean checkDealName15=validateAttributeValue(actualNcToBrmString,"Home Phone Voicemail and Call Waiting");
		boolean checkDealName16=validateAttributeValue(actualNcToBrmString,"Personal Phone [DIGITAL PHONE]");
		boolean checkDealName17=validateAttributeValue(actualNcToBrmString,"Limited TV");
		boolean checkPhoneSrlNbr1=validateAttributeValue(actualNcToBrmString,phoneSerialNbr);
		boolean checkPhoneSrlNbr2=validateAttributeValue(actualNcToBrmString,diffPhSrlNbr);
		boolean checkInternetSrlNbr1=validateAttributeValue(actualNcToBrmString,internetSerialNbr);
		boolean checkInternetSrlNbr2=validateAttributeValue(actualNcToBrmString,diffIntSrlNbr);
		boolean checkTvSrlNbr1=validateAttributeValue(actualNcToBrmString,tvSerialNbr);
		boolean checkTvSrlNbr2=validateAttributeValue(actualNcToBrmString,diffTvSrlNbr);
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");
		boolean checkPhoneNbr=validateAttributeValue(actualNcToBrmString,phoneNbr);

		if (checkAccountId&&checkAccountNbr&&checkDealName1&&checkDealName2&&checkDealName3&&checkDealName4&&checkDealName5&&
				checkDealName6&&checkDealName7&&checkDealName8&&checkDealName9&&checkDealName10&&checkDealName11&&checkDealName12&&
				checkDealName13&&checkDealName14&&checkDealName15&&checkDealName16&&checkDealName17&&checkPhoneSrlNbr1&&
				checkPhoneSrlNbr2&&checkInternetSrlNbr1&&checkInternetSrlNbr2&&checkTvSrlNbr1&&checkTvSrlNbr2&&checkStatus&&
				checkChargeType&&checkPhoneNbr)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_23");
		return validate;
	}

	public boolean getActualNewCustOrderReport_62(String acctNbr, String tvHardwareSerialNbr, String tvHardwareDCX3510SlNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString = Utility.getXMLString( driver, "t", "NETCRACKER","JMS", "New Customer Order", "<ns2:CustomerOrder xmlns:ns2" );
		log.debug("Entering getActualNewCustOrderReport_62:" + actualNcToHpsaString);
		boolean checkAccountNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checStatus=validateAttributeValue(actualNcToHpsaString,"Completed");    
		boolean checkBoxslnbr=validateAttributeValue(actualNcToHpsaString, tvHardwareSerialNbr);
		boolean checkPortalslnbr=validateAttributeValue(actualNcToHpsaString, tvHardwareDCX3510SlNbr);
		boolean chekOrderstatus = validateAttributeValue(actualNcToHpsaString,"provisioned");
		boolean checkreason=validateAttributeValue(actualNcToHpsaString,"Add");
		boolean checkTechId=validateAttributeValue(actualNcToHpsaString,"technicianId");
		boolean checkownership=validateAttributeValue(actualNcToHpsaString,"rented");


		if (checkAccountNbr&&checStatus&&checkBoxslnbr&&checkPortalslnbr&&chekOrderstatus&&checkreason&&checkTechId&&checkownership)
			validate=true;

		log.debug("Leaving getActualNewCustOrderReport_62");
		return validate;    
	}

	public boolean getActualNcToInv1_62(String tvHardwareSerialNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString="";
		String ncToInv700dct=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","700DCT" );
		if(ncToInv700dct.contains("700DCT"))
			actualNcToInvString=ncToInv700dct;
		log.debug("Entering getActualNcToInv1_62:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("700dct"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvHardwareSerialNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+tvHardwareSerialNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv1_62");
		return ncToInvUrl;
	}

	public boolean getActualNcToInv2_62(String tvHardwareDCX3510SlNbr)
	{
		boolean ncToInvUrl =false;
		String actualNcToInvString="";
		String ncToInv3510dct=Utility.getXMLString( driver, "t","NETCRACKER","INVENTORY","New TV Hardware CFS Order","3510dct" );
		if(ncToInv3510dct.contains("3510dct"))
			actualNcToInvString=ncToInv3510dct;

		log.debug("Entering getActualNcToInv2_62:" + actualNcToInvString);
		if(actualNcToInvString.contains("Request URI"))
		{
			actualNcToInvString = actualNcToInvString.substring( actualNcToInvString.indexOf(":")+1,actualNcToInvString.indexOf("\n") );
			actualNcToInvString=actualNcToInvString.trim();
			if(actualNcToInvString.contains("cpe"))
			{
				if(actualNcToInvString.contains("3510dct"))
				{
					String s1=actualNcToInvString.substring( actualNcToInvString.lastIndexOf( "/" )+1,actualNcToInvString.length());

					actualNcToInvString = actualNcToInvString.replace( s1, tvHardwareDCX3510SlNbr );
				}
			}
		}

		String expectedNcToInvString=Utility.getValueFromPropertyFile( "newCustNcToJmsUrl" )+ tvHardwareDCX3510SlNbr ;
		if(actualNcToInvString.trim().equalsIgnoreCase(expectedNcToInvString ))
		{
			ncToInvUrl=true;
		}

		log.debug("Leaving getActualNcToInv2_62");
		return ncToInvUrl;
	}

	public boolean getActualNcToBrm_62(String acctNbr, String tvHardwareSerialNbr, String tvHardwareDCX3510SlNbr)
	{
		boolean validate =false;
		String actualNcToBrmString=Utility.getXMLString( driver, "t","NETCRACKER","BILLING","New Billing Service Order","<brm:CustomerOrder" );
		log.debug("Entering getActualNcToBrm_62");
		boolean checkAccountId=validateAttributeValue(actualNcToBrmString,"1"+acctNbr);
		boolean checkAccountNbr=validateAttributeValue(actualNcToBrmString,acctNbr);
		boolean checkOfferingName=validateAttributeValue(actualNcToBrmString,"Small TV Pick 5");
		boolean checktvSrlNbr1=validateAttributeValue(actualNcToBrmString,tvHardwareSerialNbr);
		boolean checktvSrlNbr2=validateAttributeValue(actualNcToBrmString,tvHardwareDCX3510SlNbr);
		boolean checkStatus=validateAttributeValue(actualNcToBrmString,"Added");
		boolean checkinvoiceDescription1=validateAttributeValue(actualNcToBrmString,"Small TV Pick 5");
		boolean checkinvoiceDescription2=validateAttributeValue(actualNcToBrmString,"DCT 700 (599/594)");
		boolean checkinvoiceDescription3=validateAttributeValue(actualNcToBrmString,"DCX 3510 HD Guide (522/521)");
		boolean checkinvoiceDescription4=validateAttributeValue(actualNcToBrmString,"TV Installation Fee");
		boolean checkinvoiceDescription5=validateAttributeValue(actualNcToBrmString,"HD PVR MPEG4");
		boolean checkinvoiceDescription6=validateAttributeValue(actualNcToBrmString,"Tru2way Guide Entitlements");
		boolean checkinvoiceDescription7=validateAttributeValue(actualNcToBrmString,"Russia Today");
		boolean checkinvoiceDescription8=validateAttributeValue(actualNcToBrmString,"Cable Equipment Rental - Motorola");
		boolean checkinvoiceDescription9=validateAttributeValue(actualNcToBrmString,"VOD/APP");
		boolean checkinvoiceDescription10=validateAttributeValue(actualNcToBrmString,"SD");
		boolean checkChargeType=validateAttributeValue(actualNcToBrmString,"Recurring");

		if (checkAccountId&&checkAccountNbr&&checkStatus&&checkChargeType&&checkinvoiceDescription1&&
				checkinvoiceDescription2&&checkinvoiceDescription3&&checkinvoiceDescription4&&checkinvoiceDescription5&&
				checkinvoiceDescription6&&checkinvoiceDescription7&&checkinvoiceDescription8&&checkinvoiceDescription9&&
				checkinvoiceDescription10&&checktvSrlNbr1&&checktvSrlNbr2&&checkOfferingName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNcToBrm_62");
		return validate;
	}

	public boolean getActualNewTVProvisioningRFSNcToHpsa_62(String acctNbr, String tvSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] TV Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewTVProvisioningRFSNcToHpsa_62:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,tvSerialNbr);
		boolean checkSvcSpecId3=validateAttributeValue(actualNcToHpsaString,"1000");
		boolean checkCharacteristicname=validateAttributeValue(actualNcToHpsaString,"ESCatalogueServiceId");
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"100");
		boolean checkSvcId=validateAttributeValue(actualNcToHpsaString,"1");
		boolean checkSvcName1=validateAttributeValue(actualNcToHpsaString,"LinearTV");
		boolean checkVOD=validateAttributeValue(actualNcToHpsaString,"101");
		boolean checkPPV=validateAttributeValue(actualNcToHpsaString,"102");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkSvcName1&&checkVOD&&checkPPV&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcId
				&&checkSvcSpecId3&&checkCharacteristicname)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewTVProvisioningRFSNcToHpsa_62");
		return validate;
	}

	public boolean getActualNewInternetCnvrgdNcToHpsa_60(String acctNbr, String convergedSerialNbr)
	{
		boolean validate =false;
		String actualNcToHpsaString=Utility.getXMLString( driver, "t","NETCRACKER","HPSA_JMS","New [SOM] Internet Provisioning RFS Order","<ns2:ServiceActivationRequest" );
		log.debug("Entering getActualNewInternetCnvrgdNcToHpsa_60:" + actualNcToHpsaString);
		boolean checkAcctNbr=validateAttributeValue(actualNcToHpsaString,acctNbr);
		boolean checkAddrId=validateAttributeValue(actualNcToHpsaString,"221");
		boolean checkAction=validateAttributeValue(actualNcToHpsaString,"SET");
		boolean checkBoxSerialNbr=validateAttributeValue(actualNcToHpsaString,convergedSerialNbr);
		boolean checkSvcSpecId1=validateAttributeValue(actualNcToHpsaString,"1002");
		boolean checkSvcSpecId2=validateAttributeValue(actualNcToHpsaString,"108");
		boolean checkSvcName=validateAttributeValue(actualNcToHpsaString,"HighSpeedData");

		if (checkAcctNbr&&checkAddrId&&checkAction&&checkBoxSerialNbr&&checkSvcSpecId1&&checkSvcSpecId2&&checkSvcName)
			validate=true;

		if(validate==false)
		{
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false,"Attributes are not validated successfully");
		}

		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving getActualNewInternetCnvrgdNcToHpsa_60");
		return validate;
	}

}
