package com.netcracker.shaw.report;

import java.io.File;
import java.util.Date;

import javax.swing.JOptionPane;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;



public class ExtentReportManager {
	private static ExtentReports extent;
	public static ExtentReports getInstance() {
		File theDir = new File(String.valueOf(Constants.REPORT_PATH));  // Defining Directory/Folder Name  
		try{   
		    if (!theDir.exists()){  // Checks that Directory/Folder Doesn't Exists!  
		    	System.out.println("Directory " +Constants.REPORT_PATH+" does not exist");
		     boolean result = theDir.mkdir();    
		     if(result){  
		     JOptionPane.showMessageDialog(null, "New Folder created!");}  
		    }  
		}catch(Exception e){  
		    JOptionPane.showMessageDialog(null, e);  
		}  
		if (extent == null) {
			Date d=new Date();
			String fileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
			extent = new ExtentReports(theDir+"/"+fileName, true, DisplayOrder.OLDEST_FIRST);

			
			extent.loadConfig(new File("C:\\selenium\\Shaw_BSS_OE2\\src\\integration\\java\\ca\\shaw\\qa\\test\\at-shaw-sd\\reportxml\\extentconfig.xml"));
			// optional
			extent.addSystemInfo("Selenium Version", "3.13.0").addSystemInfo(
					"Environment", "QA");
		}
		return extent;
	}

	 
 }


