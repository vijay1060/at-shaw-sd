package com.netcracker.shaw.element.pageor;

import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;
import static com.netcracker.shaw.factory.Locator.*;
public enum COMOrdersPageElement implements PageElement {
	
	COM_ORDER_TAB(LINK_TEXT,getPropValue("comOrder")),
	CLEC_REQUEST_TAB(LINK_TEXT,getPropValue("clecRequests")),
	SEARCHREGULTSACCOUNTNUMBER(XPATH,getPropValue("searchRegultAcctNbr")),
	SUSPENSION_HISTORY(LINK_TEXT,getPropValue("suspensionHistory")),
	HARDWARE_RESOURCES(LINK_TEXT,getPropValue("hardwareResources")),
	LOCK_STATUS(LINK_TEXT,getPropValue("lockStatus")),
	CUSTOMER_PORTAL(LINK_TEXT,getPropValue("customerPortal")),
	INSTANCE_INFORMATION(LINK_TEXT,getPropValue("instanceInfo")),
	CUSTOMER_LOCATIONS(LINK_TEXT,getPropValue("custLocation")),
	SCHEMA(LINK_TEXT,getPropValue("schema")),
	INTEGRATION_INFORMATION(LINK_TEXT,getPropValue("integrationInfo")),
	PORTING_INFORMATION(LINK_TEXT,getPropValue("portingInfo")),
	ACCOUNT_INFORMATION(LINK_TEXT,getPropValue("acctInfo")),
	COM_INSTANCES(LINK_TEXT,getPropValue("comInstances")),
	SOM_INSTANCES(LINK_TEXT,getPropValue("somInstances")),
	
	ROWS_IN_COM_ORDER(XPATH,getPropValue("rowsInComOrder")),
	ROWS_IN_CLEC_ORDER(XPATH,getPropValue("rowsInClecOrder")),
	NEW_CUSTOMER_ORDER(XPATH,getPropValue("newCustOrder")),
	NEW_BILLING_SERVICE_ORDER(XPATH,getPropValue("newBillingSvcOrder")),
	NEW_CDI_CFS_ORDER(XPATH,getPropValue("newCdiCfsOrder")),
	NEW_CONVERGED_HARDWARE_CFS_ORDER(XPATH,getPropValue("newConvergedHardwareCfsOrder")),
	NEW_INTERNET_CFS_ORDER(XPATH,getPropValue("newInternetCfsOrder")),
	NEW_INTERNET_HARDWARE_CFS_ORDER3(XPATH,getPropValue("newInternetHardwareCfsOrder3")),
	NEW_INTERNET_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newInternetHardwareCfsOrder2")),
	
	NEWINTERNETPRODUCTORDER(XPATH,getPropValue("newInternetProdOrder")),
	NEW_PHONELINE_CFS_ORDER(XPATH,getPropValue("newPhoneLineCfsOrder")),
	NEW_PHONE_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newPhoneHardwareCFSOrder2")),
	NEW_PHONELINE_PRODUCT_ORDER(XPATH,getPropValue("newPhoneLineProdOrder")),
	NEW_TV_CFS_ORDER(XPATH,getPropValue("newTvCfsOrder")),
	NEW_TV_HARDWARE_CFS_ORDER(XPATH,getPropValue("newTvHardwareCfsOrder")),
	NEW_TV_PRODUCT_ORDER(XPATH,getPropValue("newTvProdOrder")),
	NEW_VODP_PARAMS_CFSO_ORDER(XPATH,getPropValue("newVodParamsCfsOrder")),
	NEW_WIFI_CFS_ORDER(XPATH,getPropValue("newWifiCfsOrder")),
	NEW_VOICEMAIL_CFS_ORDER(XPATH,getPropValue("newVoiceMailCfsOrder")),
	
	NEW_BILLING_SERVICE_ORDER_STATUS(XPATH,getPropValue("newBillingSvcOrderStatus")),
	NEW_CDI_CFS_ORDER_STATUS(XPATH,getPropValue("newCdiCfsOrderStatus")),
	NEW_CONVERGED_HARDWARE_CFS_ORDER_STATUS(XPATH,getPropValue("newConvergedHardwareCfsOrderStatus")),
	NEW_CUSTOMER_ORDER_STATUS(XPATH,getPropValue("newCustOrderStatus")),
	NEW_INTERNET_CFS_ORDER_STATUS(XPATH,getPropValue("newInternetCfsOrderStatus")),
	NEW_INTERNET_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newInternetProdOrderStatus")),
	NEW_PHONELINE_CFS_ORDER_STATUS(XPATH,getPropValue("newPhoneLineCfsOrderStatus")),
	NEW_PHONELINE_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newPhoneLineProdOrderStatus")),
	NEW_TV_CFS_ORDER_STATUS(XPATH,getPropValue("newTvCfsOrderStatus")),
	NEW_TV_HARDWARE_CFS_ORDER_STATUS(XPATH,getPropValue("newTvHardwareCfsOrderStatus")),
	NEW_TV_PRODUCT_ORDER_STATUS(XPATH,getPropValue("newTvProdOrderStatus")),
	NEW_VOD_PARAMS_CFS_ORDER_STATUS(XPATH,getPropValue("newVodParamsCfsOrderStatus")),
	NEW_VOICEMAIL_CFS_ORDER_STATUS(XPATH,getPropValue("newVoiceMailCfsOrderStatus")),
	NEW_WIFI_CFS_ORDER_STATUS(XPATH,getPropValue("newWifiCfsOrderStatus")),
	
	
    BLIF_REQUEST(LINK_TEXT,getPropValue("blifRequest")),
    E911ADD(LINK_TEXT,getPropValue("e911Add")),
    BLIF_REQUEST_STATUS(XPATH,getPropValue("bliffRequestStatus")),
    E911ADD_STATUS(XPATH,getPropValue("e911AddStatus")),
	
    SUSPEND_BUTTON(LINK_TEXT,getPropValue("suspendButton")),
    SUSPEND_INTERNET(XPATH,getPropValue("suspendInternet")),
    SUSPEND_TV(XPATH,getPropValue("suspendTv")),
    SUSPEND_PHONE(XPATH,getPropValue("suspendPhone")),
    
    SUSPENSION_NOTES(XPATH,getPropValue("suspensionNotes")),
    AUTHORIZED_BY(XPATH,getPropValue("authorizedBy")),
    SUBMIT_BUTTON(XPATH,getPropValue("submitButton")),
    RETURN_TO_SUSPENSION_HISTORY(LINK_TEXT,getPropValue("returnToSuspensionHist")),
    SUSPENSION_STATUS(XPATH,getPropValue("suspensionStatus")),
    SUSPENSION_HISTORY_RECORD(LINK_TEXT,getPropValue("suspensionHistoryRecord")),
    RESUME_BUTTON(LINK_TEXT,getPropValue("resumeButton")),
    RETURN_TO_SUSPENSION_HISTORY_RECORD(LINK_TEXT,getPropValue("returnToSuspensionHistRecord")),
    RESUME_NOTE(XPATH,getPropValue("resumeNote")),
    RESUME_AUTHORIZED(XPATH,getPropValue("resumeAuthorized")),
    LINK_TO_COM_PAGE(XPATH,getPropValue("linkToComPage")),
	E911_RESPONSE(XPATH,getPropValue("e911Response")),
	
	RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER(XPATH,getPropValue("resumeTvHardwareCfsImmdOrder")),
	RESUME_TV_PRODUCT_IMMEDIATE_ORDER(XPATH,getPropValue("resumeTvProdImmdOrder")),
	ROOT_RESUME_IMMEDIATE_ORDER(XPATH,getPropValue("rootResumeImmdOrder")),
	ROOT_SUSPEND_IMMEDIATE_ORDER(XPATH,getPropValue("rootSuspendImmdOrder")),
	SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER(XPATH,getPropValue("suspendTvHardwareCfsImmdOrder")),
	SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER(XPATH,getPropValue("suspendTvProdImmdOrder")),
	
	RESUME_TV_HARWARE_CFS_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("resumeTvHardwareCfsImmdOrderStatus")),
	RESUME_TV_PRODUCT_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("resumeTvProdImmdOrderStatus")),
	ROOT_RESUME_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("rootResumeImmdOrderStatus")),
	ROOT_SUSPEND_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("rootSuspendImmdOrderStatus")),
	SUSPEND_TV_HARDWARE_CFS_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("suspendTvHardwareCfsImmdOrderStatus")),
	SUSPEND_TV_PRODUCT_IMMEDIATE_ORDER_STATUS(XPATH,getPropValue("suspendTvProdImmdOrderStatus")),
	
	SVG_DIAGRAM(LINK_TEXT,getPropValue("svgDiagram")),
	TASK_TAB(LINK_TEXT,getPropValue("taskTab")),
	TASK_FILTER_STATUS(XPATH,getPropValue("taskFilterStatus")),
	TASK_WAITING_CHECKBOX(LINK_TEXT,getPropValue("taskWaitingCheckBox")),
	TASK_SELECT_ALL(XPATH,getPropValue("taskSelectAll")),
	TASK_MARK_FINISHED(XPATH,getPropValue("taskMarkFinished")),
	
	TASK_SEND_SERIAL_NBR(LINK_TEXT,getPropValue("taskSendSerialNbr")),
	TASK_TECHNICIAN_ID(ID,getPropValue("taskTechnicianId")),
	TASK_SERIAL_NBR(ID,getPropValue("taskSerialNbr")),
	TASK_SEND_BUTTON(ID,getPropValue("taskSendButton")),
	TASK_OK_BUTTON(ID,getPropValue("taskOkButton")),
	ORDER_PARAMETERS(XPATH,getPropValue("orderParameters")),
	
	OLD_SERIAL_NBR(XPATH,getPropValue("oldSerialNbr")),
	SERVICE_INFORMATION(LINK_TEXT,getPropValue("serviceInfo")),
	COM_ACCT_NBR(XPATH,getPropValue("comAcctNbr")),
	
	//Added as part of premise move
	MODIFY_CUSTOMER_ORDER(XPATH,getPropValue("modifyCustOrder")),
	MODIFY_CUSTOMER_ORDER2(XPATH,getPropValue("modifyCustOrder2")),
	MODIFY_BILLING_SERVICE_ORDER(XPATH,getPropValue("modifyBillingServiceOrder")),
	MODIFY_BILLING_SERVICE_ORDER2(XPATH,getPropValue("modifyBillingServiceOrder2")),
	MODIFY_CONVERGRED_HARDWARE_CFS_ORDER_LINK(XPATH,getPropValue("modifyConvergedHardwareCfsOrder")),
	MODIFY_TV_HARDWARE_CFS_ORDER_1(XPATH,getPropValue("modifyTvHardwareCfsOrder1")),
    MODIFY_TV_HARDWARE_CFS_ORDER_2(XPATH,getPropValue("modifyTvHardwareCfsOrder2")),
    NEW_TV_HARDWARE_CFS_ORDER2(XPATH,getPropValue("newTvHardwareCfsOrder2")),
    
    //Added as part of Port phone number
    
    SERVICE_INFORMATION_TAB(XPATH,getPropValue("serviceInforTab")),
    PORT_OPTION(XPATH,getPropValue("portOption")),
    NEW_DISTINCTIVE_RING_CFS_ORDER(XPATH,getPropValue("newDistinctiveRingCfsOrder")),    
    E911_REQUEST_STATUS(XPATH,getPropValue("E911RequestStatus")),
    BLIF_REQUEST_STATUS1(XPATH,getPropValue("bliffRequestStatus1")),
    BLIF_REQUEST_STATUS2(XPATH,getPropValue("bliffRequestStatus2")),    
    LSR_AllSTRREAM_P_STATUS1(XPATH,getPropValue("LsrAllstreamPStatus1")),
    LSR_AllSTRREAM_P_STATUS2(XPATH,getPropValue("LsrAllstreamPStatus2")),
    NPAC_REQUEST_STATUS1(XPATH,getPropValue("NPACRequestStatus1")),
    NPAC_REQUEST_STATUS2(XPATH,getPropValue("NPACRequestStatus2")),    
    BLIF_REQUEST_LINK1(XPATH,getPropValue("bliffRequestLink1")),
    BLIF_REQUEST_LINK2(XPATH,getPropValue("bliffRequestLink2")),
    LSR_ALLSTREAM_P1_LINK1(XPATH,getPropValue("lsrAllstreamPLink1")),
    LSR_ALLSTREAM_P1_LINK2(XPATH,getPropValue("lsrAllstreamPLink2")),
    NPAC_REQUEST_LINK1(XPATH,getPropValue("npscRequestLink1")),
    NPAC_REQUEST_LINK2(XPATH,getPropValue("npscRequestLink2")),  
    E911_REQUEST_LINK(XPATH,getPropValue("e911RequestLink")),
    LSC_RESPONSE_JOB(XPATH,getPropValue("lscResponseJob")),
    BLIF_RESPONSE_JOB(XPATH,getPropValue("blifResponseJob")),
     
	RUN_JOB_BUTTON(LINK_TEXT,"Run Job");
	private Locator locator;
	private String expression;
	
	public static String getPropValue(String value)
    {
        String path = Utility.getValueFromPropertyFile("comOrderPropPath");
        value = Utility.getValueFromPropertyFile( value, path );
        return value;
    }
	
	COMOrdersPageElement(Locator locator, String expression){
		this.locator = locator;
		this.expression = expression;
	}
	
	public Locator getLocator() {
		return locator;
	}

	public String getValue() {
		return expression;
	}

	public By getBy(String...placeholder) {
		return ByType.getLocator(locator, expression, placeholder);
	}

}
