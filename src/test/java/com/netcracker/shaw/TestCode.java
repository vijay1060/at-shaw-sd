package com.netcracker.shaw;

import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;

public class TestCode {
	//static WebDriver driver=null;
	 
	 public static Object[][] getData(ExcelOperation xls, String sheetName,String tcid){
		
			//String sheetName="TestData";
		System.out.println("TCID is- "+ tcid);
		  //String[][] tabArray = null;   
			int testStartRowNum=1;
			String val1=xls.getDataForTest(xls,"TC1","Run");
			System.out.println("val is- "+ val1);
		    String val=xls.getCellData(sheetName, 0, testStartRowNum);
		    System.out.println("val is- "+ val);
		    
			while(!(xls.getCellData(sheetName, 0, testStartRowNum)).equals(tcid)){
				
				testStartRowNum++;
			}
			System.out.println("Test starts from row - "+ testStartRowNum);
			System.out.println("value in row col is :"+xls.getCellData(sheetName, 0, testStartRowNum));
			//int rows=0;
			
			//calculate total cols
			int cols=0;
			while(!xls.getCellData(sheetName, cols, 1).equals("")){
				cols++;
			}
			 Object[][] data = new Object[1][1];
			
			 
			System.out.println("Total cols are  - "+cols );
			
			int dataRow=0;
			Hashtable<String,String> table=null;
			for(int rNum=testStartRowNum;rNum<=testStartRowNum;rNum++){
				table = new Hashtable<String,String>();
				for(int cNum=0;cNum<cols;cNum++){
					String key=xls.getCellData(sheetName,cNum,1);
					System.out.println("Key in cell is " +key);
					String value= (xls.getCellData(sheetName, cNum, testStartRowNum));
					System.out.println("value in cell is " +value);
					table.put(key, value);
					// 0,0 0,1 0,2
					//1,0 1,1
				}
				data[dataRow][0] =table;
				//dataRow++;
			}
			
			return (data);
		 
		 /*String[][] tabArray = null;
		 
		  

			 
			   int startCol = 1;

			   int ci=0,cj=0;

			   int totalRows = 1;

			   int totalCols = 2;
               
			   int cols=0;
				while(!xls.getCellData(sheetName, cols, 2).equals("")){
					cols++;
				}
			   
			   tabArray=new String[totalRows][totalCols];

				   for (int j=startCol;j<=cols;j++)

				   {

					   tabArray[0][cj]=xls.getCellData(sheetName, j, 2);

					   System.out.println(tabArray[ci][cj]);

				   }
				   return(tabArray);
*/
			}
	 
		

	



	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Test starts from row - ");
		ExcelOperation xls = new ExcelOperation(Constants.GoldenSuite_XLS);
		System.out.println("file name is - "+ Constants.GoldenSuite_XLS);
		Hashtable<String,String> table=null;
		table=new Hashtable<String,String>();
		
		Object[][] data=getData(xls,"TestData","TC1"); 
		
		}
		//Hashtable<String,String> table=new Hashtable<String,String> getData(xls,"TestData","TC1");
		/*WebDriver driver=null;
		System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
		driver =  new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver,50);
		// TODO Auto-generated method stub
        driver.get("http://qaapp030cn.netcracker.com:6860");
        System.out.println("Title::"+driver.getTitle());
        driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.id("user")).sendKeys("Administrator");
		driver.findElement(By.id("pass")).sendKeys("netcracker");
		driver.findElement(By.linkText("Login")).click();
		
		driver.get("http://qaapp030cn.netcracker.com:6860/oe.newCustomerDesktop.nc?accountId=&locationId=221");
		driver.findElement(By.linkText("Customer & ID")).click();
		//Thread.sleep(2000);
		WebElement firstName=wait.until(ExpectedConditions.presenceOfElementLocated((By.id("customer-first-name_main"))));
		firstName.sendKeys("Test");
		
		WebElement lastName=wait.until(ExpectedConditions.presenceOfElementLocated((By.id("customer-last-name_main"))));
		lastName.sendKeys("Test");
		
		WebElement declineEmail=wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@id='customer-declined-email_main']"))));
		declineEmail.click();
		
		WebElement phoneNum=wait.until(ExpectedConditions.presenceOfElementLocated((By.id("customer-primary-phone-value"))));
		phoneNum.clear();
		phoneNum.sendKeys("5565565566");
		
		WebElement authType=wait.until(ExpectedConditions.presenceOfElementLocated((By.id("customer-primary-phone-value"))));
		new Select(authType).selectByVisibleText("PIN");
		
		WebElement authVal=wait.until(ExpectedConditions.presenceOfElementLocated((By.id("customer-pin_main"))));
		authVal.clear();
		authVal.sendKeys("500049");*/
	}
		
