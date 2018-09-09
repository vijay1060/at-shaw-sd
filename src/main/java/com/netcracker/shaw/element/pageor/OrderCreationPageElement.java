package com.netcracker.shaw.element.pageor;
import static com.netcracker.shaw.factory.Locator.ID;
import static com.netcracker.shaw.factory.Locator.LINK_TEXT;
import static com.netcracker.shaw.factory.Locator.XPATH;

import org.openqa.selenium.By;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.factory.ByType;
import com.netcracker.shaw.factory.Locator;



public enum OrderCreationPageElement implements PageElement {
	
	CUSTOMERID_TAB(LINK_TEXT,getPropValue("customerIdTab")),
	FIRSTNAME(ID,getPropValue("firstName")),
	LASTNAME(ID,getPropValue("lastName")),
	CUSTOMERDECLINED_CHECKBOX(XPATH,getPropValue("customerCheckbox")),
	CUST_PHONENUMBER1(XPATH,getPropValue("custPhoneNbr1")),
	CUST_PHONENUMBER(ID,getPropValue("custPhoneNbr")),
	AUTHENCTICATIONTYPE(ID,getPropValue("authenticationType")),
	PIN(XPATH,getPropValue("pin")),
	PIN1(ID,getPropValue("pin1")),
	BILLINGPREFERENCE_TAB(LINK_TEXT,getPropValue("billingPreferenceTab")),
	BILLINGTYPE(XPATH,getPropValue("billingType")),
	ADDSERVICESANDFEATURES_TAB(LINK_TEXT,getPropValue("addServices")),
	ADDPRODUCT(XPATH,getPropValue("addProduct")),
	PHONEPRODUCT_LIST(XPATH,getPropValue("phoneProductList")),
	SELECTNUMBER(XPATH,getPropValue("selectNbr")),
	CHOOSENUMBER(XPATH,getPropValue("chooseNbr")),
	PHONENUMBERFROM_LIST(XPATH,getPropValue("phoneNbrFromList")),
	ASSIGN_BUTTON(XPATH,getPropValue("assignButton")),
	LISTINGTYPE_LIST(XPATH,getPropValue("listingTypeList")),
	LISTINGTYPE(XPATH,getPropValue("listingType")),
	OK_BUTTON(XPATH,getPropValue("okButton")),
	VOICEMAIL_BUTTON(XPATH,getPropValue("voiceMailButton")),
	PRODUCT_LIST(XPATH,getPropValue("productList")),
	HARDWARE(XPATH,getPropValue("hardware")),
	PHONEHARDWARE(XPATH,getPropValue("phoneHardware")),
	PH_SERIALNUMBERFIELD(XPATH,getPropValue("phSerialNbrField")),
	HARDWARE_OK_BUTTON(XPATH,getPropValue("hardwareOkButton")),
	VALIDATE_BUTTON(XPATH,getPropValue("validateButton")),
	SELECT_PHONESERVICE(XPATH,getPropValue("selectPhoneSvc")),
	SELECT_INTERNETSERVICE(XPATH,getPropValue("selectInternetSvc")),
	INTERNET150_UNLIMITED(XPATH,getPropValue("internet150Unlimited")),
	INTERNET_HARDWARE(XPATH,getPropValue("internetHardware")),
	INTERNET150_RENT(XPATH,getPropValue("internet150Rent")),
	INTERNET150_HITRONRENT(XPATH,getPropValue("internet150HitronRent")),
	INTERNET150_SERIAL_NBR_FIELD(XPATH,getPropValue("internet150SerialNbrField")),
	INTERNET150_VALIDATE(XPATH,getPropValue("internet150Validate")),
	
	DELETE_CONVERGED_HARDWARE(XPATH,getPropValue("deleteConvergedHardware")),
    REMOVE_CONFIRM_YES(XPATH,getPropValue("removeConfirmYes")),
    
    DIGITAL_CHANNEL_OK(XPATH,getPropValue("DigitalChannelOk")),
    INTERNET_300(XPATH,getPropValue("internet300")),
    SMALL_TV_PICK5(XPATH,getPropValue("smallTvPick5")),
    SELECT_SHAW_DIRECT(XPATH,getPropValue("selectShawDirect")),
    DCX3510_RENT_LEGECY(XPATH,getPropValue("Dcx3510RentLegecy")),
	
	DCX_3200M_RENT(XPATH,getPropValue("Dcx3200mrent")),
	DCX_RENT_SLNO(XPATH,getPropValue("DcxRentSlNo")),
	DCX3510_RENT_BTN(XPATH,getPropValue("Dcx3510RentBtn")),
	TV_DCX_WRENCH_BTN(XPATH,getPropValue("TVDCXWrenchbtn")),
	VALIDATE_DCX3510(XPATH,getPropValue("ValidateDCX3510")),
	SMALLTV_PICK5_HARDWARE_WRENCH(XPATH,getPropValue("SmallTvPick5HardwareWrench")),
	
	//for priority scenarios - ramesh
	LARGE_TV_PICK12(XPATH,getPropValue("LargeTVPick12")),
	LARGE_TV_HARDWARE(XPATH,getPropValue("LargeTVHardwareWrench")),
	TV_NOT_SELECTED(XPATH,getPropValue("TVNotSelected")),
	
	SWITCH_TO_COMPETITOR(XPATH,getPropValue("Switchtocompetitor")),
    MOVING(XPATH,getPropValue("DisconnectMoving")),
    TEMPORARY_DISCONNECT(XPATH,getPropValue("TemporaryMoving")),
    BUDGET_OR_PRISE_RELATED(XPATH,getPropValue("BudjetorPriseRelated")),
    TECHNICIAL_CUSTOMER_CARE(XPATH,getPropValue("TechnicialCustomercare")),
    OTHER(XPATH,getPropValue("DisconnectOther")),
    CUSTOMER_DECLINED_TO_SAY(XPATH,getPropValue("CustomerDeclinedToSay")),
    SWITCH_TO_COMPETITOR_DROPDOWN(XPATH,getPropValue("SwitchtocompetitorDropdown")),
    
    DIGITAL_CHANNEL_SECTION(XPATH,getPropValue("DigitalChannelSelection")),
    PICK10_PACK1(XPATH,getPropValue("Pick10Pack1")),
    PICK10_PACK1_WRENCH(XPATH,getPropValue("Pick10Pack1Wrench")),
    ASIDE(XPATH,getPropValue("ASide")),
    ESPN_CLASSIC(XPATH,getPropValue("ESPNClassic")),
    FYI(XPATH,getPropValue("FYI")),
    FIGHT_NETWORK(XPATH,getPropValue("FightNetwork")),
    MAKEFUL(XPATH,getPropValue("Makeful")),
    SILVER_SCREEN_CLASSICS(XPATH,getPropValue("SilverScreenClassics")),
    LOVE_NATURE(XPATH,getPropValue("LoveNature")),
    DEJA_VIEW(XPATH,getPropValue("Dejaview")),
    BCC_CANADA(XPATH,getPropValue("BBCCanada")),
    GSN_GAMESHOW_NETWORK(XPATH,getPropValue("GSNGameShowNetwork")),
    DIGITAL_CHANNEL_THEME(XPATH,getPropValue("DigitalChannelTheme")),
    ENT3MEDIUM_POTPOURRI2_MUSICPOP_CULTURE(XPATH,getPropValue("Ent3MediumPotpourri2MusicPopCulture")),
    
    //kirti - 72
    PORT_NUMBER_RADIO_BUTTON(XPATH,getPropValue("PortRadioButton")),
    PORTED_NUMBER(XPATH,getPropValue("PortedNumber")),
    PORT_NOW(XPATH,getPropValue("PortNow")),
    LANDLINE_RADIO_BUTTON(XPATH,getPropValue("LandlineRadioButton")),   
    DROP_DOWN_CLASS(XPATH,getPropValue("DropDownClass")),
    SERVICE_PROVIDER_DROP_DOWN(XPATH,getPropValue("ServiceProviderDropDown")),
    SERVICE_PROVIDER_VALUE(XPATH,getPropValue("ServiceProviderValue")), 
    RESELLER_DROP_DOWN(XPATH,getPropValue("ResellerDropDown")),
    RESELLER_VALUE(XPATH,getPropValue("ResellerValue")),
    CUSTOMER_LIFELINE_YES(XPATH,getPropValue("CustomerLifeLineYes")),
    SHAW_SERVICE_REQUESTED_NO(XPATH,getPropValue("ShawServiceRequestedNo")),
    PREVIOUS_PROVIDER_ACCOUNT_NUMBER(XPATH,getPropValue("PreviousProviderAccount")),
    LISTINGTYPE411ONlY(XPATH,getPropValue("listingType411Only")),
	
	//CONVERGED_HARDWARE_PROD(CSS_SELECTOR,"#ConvergedHardwareCategory_addOfferButton > span.UIShawButton > span.UIShawButton-inner"),
	CONVERGED_HARDWARE_PROD(XPATH,getPropValue("convergedHardwareProd")),
	CONVERGED_HARDWARE(XPATH,getPropValue("convergedHardware")),
	CONVERGED_HARWARE_RENT(XPATH,getPropValue("convergedHardwareRent")),
	CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD(XPATH,getPropValue("convergedHardwareSerialNbr")),
	LIMITED_TV(XPATH,getPropValue("limitedTv")),
	LIMITED_TV_HARDWARE(XPATH,getPropValue("limitedTvHardware")),
	SD_RENT(XPATH,getPropValue("sdRent")),
	TV_SERIAL_NO_FIELD(XPATH,getPropValue("tvSerialNoField")),
	SD_VALIDATE(XPATH,getPropValue("sdValidate")),
	TODO_LIST(XPATH,getPropValue("todoList")),
	INSTALLATIONFEE(XPATH,getPropValue("installationFee")),
	APPOINTMENT(LINK_TEXT,getPropValue("appointment")),
	ISTECHREQUIRED_NO(XPATH,getPropValue("isTechRequiredNo")),
	ISTECHREQUIRED_YES(XPATH,getPropValue("isTechRequiredYes")),
	TECH_APPOINTMENT_DATE(ID,getPropValue( "techAppointmentDate" )),
	DIRECT_FULFILLMENT_BUTTON_NO(ID,getPropValue("directFulfillmentButtonNo")),
	RETAIL_PICKUP_BUTTON_NO(XPATH,getPropValue("retailPickupButtonNo")),
	
	//added as part of Blue sky
	RENT_SHAW_BLUE_SKY_526(XPATH,getPropValue("RentShawBlueSkyTVbox526")),
	RENT_SHAW_BLUE_SKY_TV_BOX526_SLNO_INPUT(XPATH,getPropValue("RentShawBlueSkyTVbox526SlNoInput")),
	RENT_SHAW_BLUE_SKY_TV_BOX526_SLNO_INPUT_VALIDATE(XPATH,getPropValue("RentShawBlueSkyTVbox526SlNoInputValidate")),
	RENT_SHAW_BLUE_SKY_TV_PORTAL_416(XPATH,getPropValue("RentShawBlueSkyTVportal416")),
	RENT_SHAW_BLUE_SKY_TV_BOX416_SLNO_INPUT(XPATH,getPropValue("RentShawBlueSkyTVbox416SlNoInput")),
	RENT_SHAW_BLUE_SKY_TV_BOX416_SLNO_INPUT_VALIDATE(XPATH,getPropValue("RentShawBlueSkyTVbox416SlNoInputValidate")),
	
	//added as part of triple play TV hardwareswap
	SHAW_BLUE_SKY_TV_PORTAL_BOX526_SWAP(XPATH,getPropValue("ShawBlueSkyTvPortalBox526Swap")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX526_TECHNICIAN_RADIO_BUTTON(XPATH,getPropValue("ShawBlueSkyTvPortalBox526TechnicianRadioButton")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX526_TECHNICIAN_ID(XPATH,getPropValue("ShawBlueSkyTvPortalBox526TechnicianId")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX526_VALIDATE_TECH_ID(XPATH,getPropValue("ShawBlueSkyTvPortalBox526ValidateTechId")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX526_HARDWARE_NEW_SERIAL_NO(XPATH,getPropValue("ShawBlueSkyTvPortalBox526HardwareNewSerialNo")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX526_VALIDATE_NEW_SERIAL_NO(XPATH,getPropValue("ShawBlueSkyTvPortalBox526ValidateNewSerialNo")),
   
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_SWAP(XPATH,getPropValue("ShawBlueSkyTvPortalBox416Swap")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_TECHNICIAN_RADIO_BUTTON(XPATH,getPropValue("ShawBlueSkyTvPortalBox416TechnicianRadioButton")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_TECHNICIAN_ID(XPATH,getPropValue("ShawBlueSkyTvPortalBox416TechnicianId")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_VALIDATE_TECH_ID(XPATH,getPropValue("ShawBlueSkyTvPortalBox416ValidateTechId")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_HARDWARE_NEW_SERIAL_NO(XPATH,getPropValue("ShawBlueSkyTvPortalBox416HardwareNewSerialNo")),
    SHAW_BLUE_SKY_TV_PORTAL_BOX416_VALIDATE_NEW_SERIAL_NO(XPATH,getPropValue("ShawBlueSkyTvPortalBox416ValidateNewSerialNo")),
    
  //added as part of triple play converged hardwareswap
    CONVERGED_HARDWARE_SWAP(XPATH,getPropValue("ConvergedHardwareSwap")),
    TECHNICIAN_RADIO_BUTTON(XPATH,getPropValue("TechnicianRadioButton")),
    CONVERGED_HARDWARE_TECH_ID(XPATH,getPropValue("ConvergedHardwareTechId")),
    CONVERGED_HARDWARE_VALIDATE_TECH_ID(XPATH,getPropValue("ConvergedHardwareValidateTechId")),
    CONVERGED_HARDWARE_NEW_SERIAL_NO(XPATH,getPropValue("ConvergedHardwareNewSerialNo")),
    CONVERGED_HARDWARE_NEW_SERIAL_NO_VALIDATE(XPATH,getPropValue("ConvergedHardwareNewSerialNoValidate")),
    CONVERGED_HARDWARE_NEW_SERIAL_NO_OK_BUTTON(XPATH,getPropValue("ConvergedHardwareNewSerialNoOkButton")),
    
    DISTINCTIVE_RING_INCREMENT(XPATH,getPropValue("distinctiveRingIncrement")),
    DR_SELECT_NUMBER(XPATH,getPropValue("drSelectNumber")),
    FEATURES_TAB(XPATH,getPropValue("featuresTab")),
    RING_PATTERN_DROP_DOWN(XPATH,getPropValue("ringPatternList")),
    TYPE_3_RING_SHORT(XPATH,getPropValue("3ShortRingType")),
    
	CHANGEAUTHORIZEDBY(ID,getPropValue("changeAuthorizedBy")),
	REVIEW(XPATH,getPropValue("review")),
	FINISH(XPATH,getPropValue("finish")),
	ACCOUNTNUMBER(XPATH,getPropValue("accountNbr")),
	ORDERNUMBER(XPATH,getPropValue("orderNbr")),
	PHONENUMBER(XPATH,getPropValue("phoneNbr")),
	CUSTINFOSTAT(XPATH,getPropValue("custInfoStat")),
	BILLINGACCOUNTSTAT(XPATH,getPropValue("billingAcctStat")),
	ACCOUNT_LINK(XPATH,getPropValue("acctLink")),
	ACCOUNT_LINK_FOR_ORDERS(XPATH,getPropValue("acctLinkForOrders")),
	ORDER_MGMT_STAT(XPATH,getPropValue("ordermgmtstat")),
	
	SWAP_HARDWARE(XPATH,getPropValue("SwapHardware")),
    TECH_RADIO_BTN(XPATH,getPropValue("TechRadioBtn")),
    TECHNICIAN_ID(XPATH,getPropValue("TechnicianId")),
    VALIDATE_TECH_ID(XPATH,getPropValue("ValidateTechId")),
    SWAP_SL_NO(XPATH,getPropValue("SwapSlno")),
    VALIDATE_SWAP_SLNO(XPATH,getPropValue("ValidateSwapSlno")),
	
	//added as part of Premise Move
	SELECT_PREMISE_MOVE_DATE(XPATH,getPropValue("selectPremiseMoveDate")),
	PREMISE_MOVE_SERVICE_NUMBER(ID,getPropValue("premiseMoveServiceNumber")),
	PREMISE_MOVE_DISCONNECTION_DATE(XPATH,getPropValue("premiseMoveDisconnectionDate")),
	SELECT_MANUAL_APPOINTMENT(XPATH,getPropValue("selectManualAppointment")),
	CLICK_CALENDAR(XPATH,getPropValue("clickCalendar")),
	
	REMOVE_PERSONAL_PHONE_HARDWARE(XPATH,getPropValue("removePersonalPhoneHardware")),
	CONFIRM_PRODUCT_REMOVE(XPATH,getPropValue("confirmProductRemove")),
	INTERNET_75(XPATH,getPropValue("internet75")),
    
    HPSA(XPATH,getPropValue("hpsa")),
    HPSA_PHONE_LINE(XPATH,getPropValue("hpsaPhoneLine")),
    HPSA_WIFI(XPATH,getPropValue("hpsaWifi")),
    HPSA_CABLE_TV(XPATH,getPropValue("hpsaCableTv")),
    HPSA_INTERNET(XPATH,getPropValue("hpsaInternet")),
    
    CANCEL_ORDER(XPATH,getPropValue("cancelOrder")),
    CANCEL_YES(XPATH,getPropValue("cancelYes")),
    CANCEL_ORDER_OK(XPATH,getPropValue("cancelOrderOk"));
	
	private Locator locator;
	private String expression;
	
	public static String getPropValue(String value)
    {
        String path = Utility.getValueFromPropertyFile("orderCreationPropPath");
        value = Utility.getValueFromPropertyFile( value, path );
        return value;
    }
	
	OrderCreationPageElement(Locator locator, String expression){
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
