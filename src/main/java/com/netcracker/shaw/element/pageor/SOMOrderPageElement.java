package com.netcracker.shaw.element.pageor;
import static com.netcracker.shaw.factory.Locator.LINK_TEXT;
import static com.netcracker.shaw.factory.Locator.XPATH;

import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;


public enum SOMOrderPageElement implements PageElement {
	SUSPEND_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("suspendTvProvisionRfsOrder1")),
    SUSPEND_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("suspendTvProvisionRfsOrder2")),
    SUSPEND_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("suspendPhoneProvisionRfsOrder")),
    SUSPEND_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("suspendInternetProvisionRfsOrder")),
    
    
    RESUME_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("resumeTvProvisionRfsOrder1")),
    RESUME_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("resumeTvProvisionRfsOrder2")),
    RESUME_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("resumeInternetProvisionRfsOrder")),
    
	ORDER_PARAMETER_TAB(XPATH,getPropValue("orderParameterTab")),
	INTEGRATION_REPORT(LINK_TEXT,getPropValue("integrationReport")),
	ROWS_IN_SOM_ORDER(XPATH,getPropValue("rowsInSomOrder")),
	SOM_ORDER_TAB(LINK_TEXT,getPropValue("somOrder")),
	DISCONNECT_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("disconnectPhoneProvisionRfsOrder")),
	DISCONNECT_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("disconnectInternetProvisionRfsOrder")),
	DISCONNECT_TV_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("disconnectTVProvisionRfsOrder1")),
	DISCONNECT_TV_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("disconnectTVProvisionRfsOrder2")),
	NEW_INTERNET_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("newInternetProvisionRfsOrder1")),
	NEW_INTERNET_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("newInternetProvisionRfsOrder2")),
	NEW_INTERNET_PROVISIONING_RFS_ORDER4(XPATH,getPropValue("newInternetProvisionRfsOrder4")),
	MODIFY_INTERNET_PROVISIONING_RFS_ORDER(XPATH,getPropValue("modifyInternetProvisionRfsOrder")),
	MODIFY_PHONE_PROVISIONING_RFS_ORDER(XPATH,getPropValue("modifyPhoneProvisionRfsOrder")),
	NEW_PHONE_PROVISIONING_RFS_ORDER1(XPATH,getPropValue("newPhoneProvisionRfsOrder1")),
	NEW_PHONE_PROVISIONING_RFS_ORDER2(XPATH,getPropValue("newPhoneProvisionRfsOrder2")),
	NEW_PHONE_PROVISIONING_RFS_ORDER4(XPATH,getPropValue("newPhoneProvisionRfsOrder4")),
	NEW_TV_PROVISIONING_RFS_ORDER_1(XPATH,getPropValue("newTvProvisionRfsOrder1")),
	NEW_TV_PROVISIONING_RFS_ORDER_2(XPATH,getPropValue("newTvProvisionRfsOrder2")),
	NEW_TV_PROVISIONING_RFS_ORDER_3(XPATH,getPropValue("newTvProvisionRfsOrder3")),
	NEW_TV_PROVISIONING_RFS_ORDER_6(XPATH,getPropValue("newTvProvisionRfsOrder6")),
	
	NEW_DISTINCTIVE_RING_RFS_ORDER(XPATH,getPropValue("newDistinctiveRingRFSOrder")),
	NEW_SOM_PHONE_LINE_CFS_ORDER(XPATH,getPropValue("newSomPhoneCfsOrder")),
	
	CANCEL_WIFI_PROVISIONING_RFS_ORDER(XPATH,getPropValue("cancelWifiProvisionRfsOrder")),
	NEW_WIFI_PROVISIONING_RFS_ORDER(XPATH,getPropValue("newWifiProvisionRfsOrder")),
	NETCRACKER_TO_HPSA_JMS(XPATH,getPropValue("ncToHpsaJms"));

	private Locator locator;
	private String expression;

	public static String getPropValue(String value)
	{
		String path = Utility.getValueFromPropertyFile("somOrderPropPath");
		value = Utility.getValueFromPropertyFile( value, path );
		return value;
	}

	SOMOrderPageElement(Locator locator, String expression){
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
