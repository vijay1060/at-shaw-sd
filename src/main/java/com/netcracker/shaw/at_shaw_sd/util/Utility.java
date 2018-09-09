package com.netcracker.shaw.at_shaw_sd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

//import junit.framework.Assert;

public class Utility {
	
	public static WebDriver getRemoteBrowser() throws MalformedURLException
	{
		String Node="http://10.235.10.33:4444/wd/hub";
		WebDriver driver=null;
		if (getValueFromPropertyFile("browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",getValueFromPropertyFile("chromedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("requireWindowFocus", true);
			driver =  new RemoteWebDriver(new URL(Node),capabilities);
		} else if (getValueFromPropertyFile("browser").equalsIgnoreCase("ie")) {
			
			System.setProperty("webdriver.ie.driver",getValueFromPropertyFile("iedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver =  new InternetExplorerDriver();;
		}
		//System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		return driver;
	
	}
	
	public static WebDriver getBrowser(){
		WebDriver driver=null;
		if (getValueFromPropertyFile("browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",getValueFromPropertyFile("chromedriver_path"));
			//System.getProperty("user.dir");
			driver =  new ChromeDriver();
		} else if (getValueFromPropertyFile("browser").equalsIgnoreCase("ie")) {
			
			System.setProperty("webdriver.ie.driver",getValueFromPropertyFile("iedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver =  new InternetExplorerDriver();;
		}
		//System.setProperty("webdriver.chrome.driver", "C:\\Library\\chromedriver.exe");
		return driver;
	}
	
	// Getting property based on key
	public static String getValueFromPropertyFile(String key) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("config/config.properties") );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}
	
	// Getting property based on key and directory
	public static String getValueFromPropertyFile(String key, String propDir) {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(propDir));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            prop.load(input);
        } catch (IOException e) {
            System.out.println("Not able to load");
        }
        return prop.getProperty(key);
        // return null;
    }
	
	//Getting random value
	public static int getRandomValue()
	{
	    Random random = new Random();
        int randomNumber = random.nextInt(499) + 1;
        
        return randomNumber;
	}
	
	 public static int getRandomPhoneNumber()
     {
         Random random = new Random();
         int randomNumber = random.nextInt(1000000000) + 1000000000;        
         return randomNumber;
     }
	 
	//Comparing integer values
	public static boolean compareIntegerVals(int actualIntegerVal, int expectedIntegerVal){
		SoftAssert Soft_Assert = new SoftAssert();
		try{
			   //If this assertion will fail, It will throw exception and catch block will be executed.
			   Assert.assertEquals(actualIntegerVal, expectedIntegerVal);
			   }catch(Throwable t){
			     Soft_Assert.fail("Actual Value '"+actualIntegerVal+"' And Expected Value '"+expectedIntegerVal+"' Do Not Match.");
			    //If Integer values will not match, return false.
			    return false;
			   }
			  //If  Integer values match, return true.
			  return true;
			 }
	
	// Getting XML String from data table
	public static String getXMLString(WebDriver driver, 
	                                String webTableID,
	                                String fromColumnValue,
	                                String toColumnValue,
	                                String orderNameValue,
	                                String xmlStringContain )
	      {
	          String requiredXMLString="";
	       // variables for from column, to column, ordername column and xmlstring
	          List<WebElement> fromColumnValues = driver.findElements( By.xpath( "//table[@id='"
	                                                                             + webTableID
	                                                                             + "\']/tbody/tr/td[2]" ) );
	          List<WebElement> toColumnValues = driver.findElements( By.xpath( "//table[@id='"
	                                                                           + webTableID
	                                                                           + "\']/tbody/tr/td[3]" ) );
	          List<WebElement> orderNameValues = driver.findElements( By.xpath( "//table[@id='"
	                                                                            + webTableID
	                                                                            + "\']/tbody/tr/td[4]" ) );
	          List<WebElement> messageValues = driver.findElements( By.xpath( "//table[@id='"
	                                                                          + webTableID
	                                                                          + "\']/tbody/tr/td[6]" ) );
	          
	          // contain check in the message
	          for ( int m = 0; m < fromColumnValues.size(); m++ )
	          {
	              if ( fromColumnValues.get( m ).getText().contains( fromColumnValue ) )
	              {
	                  if ( toColumnValues.get( m ).getText().contains( toColumnValue ) )
	                  {
	                      if ( orderNameValues.get( m ).getText().contains( orderNameValue ) )
	                      {
	                          if ( messageValues.get( m ).getText().contains( xmlStringContain ) )
	                          {
	                              requiredXMLString = messageValues.get( m ).getText();
	                          }
	                      }
	                  }
	              }

	          }

	          return requiredXMLString;

	      }
	
	// Getting table row count from data table
    public static int getWebTableRowCount(WebDriver driver, 
                                    String webTableID)
          {
              int rowCount=0;
           // getting row values
              String webTableRowXpath = webTableID + "/tbody/tr";
              List<WebElement> rows = driver.findElements(By.xpath(webTableRowXpath));
              rowCount=rows.size();
              System.out.println("\n Total rows in table is " + rowCount);
              return rowCount;
          }
    public static boolean isSkip(ExcelOperation xls, String testName){
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testName)){
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if(runmode.equals("Y"))
					return false;
				else
					return true;
			}
		}
		
		return true;//default
		
	}
	public static String getDataForTest(ExcelOperation xls,String rowName,String colName){
		int rows = xls.getRowCount(Constants.TEST_SHEET);
		String colVal=null;
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData(Constants.TEST_SHEET, "TCID", rNum);
            if(tcid.equals(rowName)){
            		
					colVal=(xls.getCellData("TestData", colName, rNum));
					
					
            }
					
					
				}
		return colVal;
	}
			
    //save xml string
    //C:\selenium\workspace\at-shaw-sd\src\test
    
    //public static String 
    
	public static Object[][] getData(ExcelOperation xls,String tcid){
		String sheetName="TestData";
		// reads data for only testCaseName
		  
		int testStartRowNum=1;
		
		//getDataForTest(xls,TCID,"Run")
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(tcid)){
			testStartRowNum++;
		}
				
		int cols=0;
		while(!xls.getCellData(sheetName, cols, 1).equals("")){
			cols++;
		}
		
		Object[][] data = new Object[1][1];
		int dataRow=0;
		Hashtable<String,String> table=null;
		for(int rNum=testStartRowNum;rNum<=testStartRowNum;rNum++){
			table = new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++){
				String key=xls.getCellData(sheetName,cNum,1);
				String value= xls.getCellData(sheetName, cNum, testStartRowNum);
				table.put(key, value);
				
			}
			data[dataRow][0] =table;
			dataRow++;
		}
		return data;
	}
	
	public static boolean validateTestRun(Hashtable<String,String> data)
	{
		boolean result=true;
        if(data.get("Run").equals("No")){
            result=false;
        }
        return result;
	}
	
	
}
	

