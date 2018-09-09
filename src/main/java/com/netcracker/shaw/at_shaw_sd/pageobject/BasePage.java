package com.netcracker.shaw.at_shaw_sd.pageobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
//import com.netcracker.shaw.report.TestListener;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.ExtentReports;
*/
public class BasePage<T extends PageElement> {

	protected WebDriver driver = SeleniumTestUp.DRIVER;
	// public WebDriverWait wait = new WebDriverWait(driver,50);
	// protected ExtentReports rep = TestListener.reports;
	// protected ExtentTest test = TestListener.test;
	public ExtentTest test;
	public long DEFAULT_TIME_OUT = Long.parseLong(Utility.getValueFromPropertyFile("timeout"));

	public BasePage(ExtentTest test) {
		this.test = test;
		
	}

	JavascriptExecutor javascript = ((JavascriptExecutor) driver);

	public WebElement getWebElement(T element, String... placeholder) {
		WebElement e1 = driver.findElement(element.getBy(placeholder));

		// return new WebDriverWait(driver,
		// 30).until(ExpectedConditions.elementToBeClickable(element.getBy(placeholder)));
		return e1;
	}

	/*
	 * public WebElement findWebElement(T element, String type1,String...
	 * placeholder) { WebDriverWait wait = new WebDriverWait(driver,100);
	 * switch(type){ case "click": return
	 * wait.until(ExpectedConditions.elementToBeClickable(element.getBy(
	 * placeholder)));
	 * 
	 * case "edit" : return
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy(
	 * placeholder)));
	 * 
	 * case "select": return
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy(
	 * placeholder)));
	 * 
	 * default: return
	 * wait.until(ExpectedConditions.presenceOfElementLocated(element.getBy(
	 * placeholder)));
	 * 
	 * }
	 * 
	 * 
	 * }
	 */
	// returns list of webelements
	public List<WebElement> getListElement(T element, String... placeholder) {
		List<WebElement> list = driver.findElements(element.getBy(placeholder));
		return list;
	}

	// clicking on the webelement
	public void click(T element) {
		getWebElement(element).click();
		test.log(LogStatus.PASS, "Click element", "Clicked  " + element);
		//log.debug("Clicking the element : "+ element);
		// test.log(Status.PASS,"Clicked "+element);
	}

	// clearing the webelement
	public void clear(T element) {
		getWebElement(element).clear();

	}

	/*
	 * public void inputPin(T element1, T element2,String value,String...
	 * placeholder) throws IOException{
	 * 
	 * WebElement
	 * e=wait.until(ExpectedConditions.visibilityOfElementLocated(element1.getBy
	 * (placeholder))); if(e.isDisplayed()){ getWebElement(element2).clear();
	 * 
	 * getWebElement(element2).sendKeys(value); }
	 * 
	 * }
	 */

	// sending value to the input text
	public void inputText(T element, String value) {
		clear(element);
		wait("1");
		getWebElement(element).click();
		getWebElement(element).sendKeys(value);
		test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + "in " + element);
		//log.debug("Entering text "+value +" in field " +element);
	}

	// navigating to url
	public void navigate(String url) {
		driver.get(url);
	}

	public void selectFromList(T element, String value) {

		new Select(getWebElement(element)).selectByVisibleText(value);
		// new
		// Select(findWebElement(element,"select")).selectByVisibleText(value);
		// test.log(Status.PASS,"Selected value "+value+ "from: "+element);
	}

	public boolean isElementPresent(T element) {
		List<WebElement> e = null;
		e = getListElement(element);

		if (e.size() == 0)
			return false;
		else
			return true;
	}

	public int countRowsinTable(T element) {
		return getListElement(element).size();
	}

	public boolean verifyElementPresent(T element) {
		boolean result = isElementPresent(element);
		return result;
	}

	public void wait(String timeout) {

		try {
			Thread.sleep(Long.parseLong(timeout));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getText(T element) {
		return getWebElement(element).getText();
	}

	public boolean isDisplayed(T element) {
		return getWebElement(element).isDisplayed();
	}

	public String getAttribue(T element, String value) {
		return getWebElement(element).getAttribute(value);
	}

	public String getCssValue(T element, String value) {
		return getWebElement(element).getCssValue(value);
	}

	public void switchWindow() {
		String mainWindowHnd = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		for (String handle : set) {
			if (!(handle == mainWindowHnd)) {
				driver.switchTo().window(handle);
			}
		}
	}

	public void switchFirstNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));
	}

	public void switchSecondNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(2));
	}

	public void switchNextTab() {
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(2));
	}

	public void switchPreviousTab() {
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));
	}

	public void removeTab() {
		Set<String> windowHandle = driver.getWindowHandles();
		windowHandle.remove(windowHandle.iterator().next());
	}
	/*
	 * public void wait(long seconds) throws Exception { Thread.sleep(seconds);
	 * }
	 */
	
	public void wait( int timeout ) throws Exception
	 {
		 long milliSeconds = timeout*1000;
		 Thread.sleep(milliSeconds);
	 }

	public void takeScreenShot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_") + ".png";
		String path = Utility.getValueFromPropertyFile("screenshot_path");
				
		File directory = new File(String.valueOf(Constants.SCREENSHOT_PATH));

		 if(!directory.exists()){

		             directory.mkdir();
		 }
		
		// take screenshot
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(directory +"/"+ screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchThirdNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(3));
	}

	public void takeScreenShot(String str) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy_MMM_ hh_mm_ss a");
		Date d = new Date();
		String time = df.format(d);
		System.out.println(time);
		File directory = new File(String.valueOf(Constants.SCREENSHOT_PATH));

		 if(!directory.exists()){

		             directory.mkdir();
		 }
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(directory +"/"+ str + time + ".png"));

	}

	public String addScreenShot(String str) throws Exception {
		File directory = new File(String.valueOf(Constants.SCREENSHOT_PATH));

		 if(!directory.exists()){

		             directory.mkdir();
		 }
		 System.out.println("Dir name is "+directory);
		DateFormat df = new SimpleDateFormat("yyyy_MMM_ hh_mm_ss a");
		Date d = new Date();
		String time = df.format(d);
		System.out.println(time);
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(directory + "/"+str + time + ".png"));
		//return "C:\\Project\\SHAW\\Screenshots" + str + time + ".png";
		return directory + "/"+str + time + ".png";
	}
	
	public String addScreenshot() {
	     File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	     //Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
	     String encodedBase64 = null;
	     FileInputStream fileInputStreamReader = null;
	     try {
	         fileInputStreamReader = new FileInputStream(scrFile);
	         byte[] bytes = new byte[(int)scrFile.length()];
	         fileInputStreamReader.read(bytes);
	         encodedBase64 = new String(Base64.getEncoder().encodeToString(bytes));
	         //encodedBase64 =new String(Base64.encodeBase64(bytes), "UTF-8");
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     return "data:image/png;base64,"+encodedBase64;
	 }

	public boolean checkStatusComplete(T elementStatus) {
		return getText(elementStatus).equalsIgnoreCase("completed");
	}

	public boolean validateAttributeValue(T element, String attrName, String attrVal) {

		String responseXML = getText(element).trim().replaceAll("(\\s|\n)", "");
		String attr1 = attrName + ">" + attrVal;
		String attr2 = attrName + "=\"" + attrVal + "\"";
		String attr3 = attrName
				+ "</ns3:CharacteristicName><ns3:CharacteristicCategory><ns3:CategoryName>DeviceInfo</ns3:CategoryName></ns3:CharacteristicCategory><ns3:CharacteristicValue><ns3:Value>"
				+ attrVal;
		return (responseXML.contains(attr1) || responseXML.contains(attr2) || responseXML.contains(attr3));

	}

	public void returnToPreviousPage() throws Exception 
	{
		//log.debug("Entering returnToPreviousPage");
		// driver.navigate().back();
		javascript.executeScript("javascript: setTimeout(\"history.go(-3)\", 2000)");
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		wait(2);
		scrollUp();
		//log.debug("Leaving returnToPreviousPage");
	}

	public void scrollUp() {
		JavascriptExecutor javascript = ((JavascriptExecutor) driver);
		javascript.executeScript("window.scrollBy(0,-3000)", "");
	}

	public void scrollDown() {
		JavascriptExecutor javascript = ((JavascriptExecutor) driver);
		javascript.executeScript("window.scrollBy(0,3000)", "");
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public boolean validateAttributeValue(String responseXML, String attributeVal) {
		return responseXML.contains(attributeVal);
	}

}
