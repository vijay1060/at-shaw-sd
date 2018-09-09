package com.netcracker.shaw.at_shaw_sd.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.NumberToTextConverter;

public class ExcelOperation {
	
	public static String filename = System.getProperty("user.dir")+"\\src\\testdata\\GoldenSuite.xls";
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	private FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
	private DataFormatter objDefaultFormat = new DataFormatter();
	double value = 0.0;
	java.text.DecimalFormat formatter = null;
	java.text.FieldPosition fPosition = null;
	String formattingString = null;
	String resultString = null;
	StringBuffer buffer = null;
	public ExcelOperation(String path) {
		
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
		
	}
	
	// returns the data from a cell
	public String getCellData(String sheetName,String colName,int rowNum){
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);
		int col_Num=-1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
				col_Num=i;
		}
		if(col_Num==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(col_Num);
		
		if(cell==null)
			return "";
		//System.out.println(cell.getCellType());
		if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			  return cell.getStringCellValue();
		else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
			  
			  String cellText  = new BigDecimal(cell.getNumericCellValue()).toPlainString();
		
			 /* if (HSSFDateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cal.get(Calendar.MONTH)+1 + "/" + 
		                      cellText;
		           
		           //System.out.println(cellText);

		         }

			  */
			  
			  return cellText;
		  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
		      return ""; 
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
		
		}
	
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
		}
	
	}
	
	public String getCellData(String sheetName,int colNum,int rowNum){
		
		try{
			if(rowNum <=0)
				return "";
		
		int index = workbook.getSheetIndex(sheetName);

		if(index==-1)
			return "";
		
	
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(colNum);
		//objFormulaEvaluator.evaluate(cell);
		//String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
		//SET CELL AS STRING TYPE
		//cell.setCellType(Cell.CELL_TYPE_STRING);
		if(cell==null)
			return "";
		
	  if(cell.getCellType()==Cell.CELL_TYPE_STRING)
		  return cell.getStringCellValue();
	  else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
		  
		  String cellText  = String.valueOf(cell.getNumericCellValue());
			  return cellText ;
			  }
		  
		  
	  else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
	      return "";
	  else 
		  return String.valueOf(cell.getBooleanCellValue());
		
		
		}
		catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
		
	}
	
	public String formatData(Cell cell){
		
        // Recover the numeric value from the cell
        value  = cell.getNumericCellValue();
            
        // Format that number for display
        formatter.format(value, buffer, fPosition);
            
        // Not strictly necessary but I copy the result from the
        // StringBuffer into a String - leave this out for performance
        // reasons in production code
        resultString = buffer.toString();
        return resultString;
	}
	
	public static String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCellValue = cell.toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
            	
            	strCellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCellValue = new String(new Boolean(
                        cell.getBooleanCellValue()).toString());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCellValue = "";
                break;
            }
        }
        return strCellValue;
    }
	
	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName,String colName,int rowNum, String data){
		try{
		fis = new FileInputStream(path); 
		workbook = new HSSFWorkbook(fis);

		if(rowNum<=0)
			return false;
		
		int index = workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1)
			return false;
		
		
		sheet = workbook.getSheetAt(index);
		

		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++){
			//System.out.println(row.getCell(i).getStringCellValue().trim());
			if(row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum=i;
		}
		if(colNum==-1)
			return false;

		sheet.autoSizeColumn(colNum); 
		row = sheet.getRow(rowNum-1);
		if (row == null)
			row = sheet.createRow(rowNum-1);
		
		cell = row.getCell(colNum);	
		if (cell == null)
	        cell = row.createCell(colNum);

	    // cell style
	    CellStyle cs = workbook.createCellStyle();
	    cs.setWrapText(true);
	    cell.setCellStyle(cs);
	    cell.setCellValue(data);

	    fileOut = new FileOutputStream(path);

		workbook.write(fileOut);

	    fileOut.close();	

		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// returns true if data is set successfully else false
		
	// returns true if sheet is created successfully else false
	public boolean addSheet(String  sheetname){		
		
		FileOutputStream fileOut;
		try {
			 workbook.createSheet(sheetname);	
			 fileOut = new FileOutputStream(path);
			 workbook.write(fileOut);
		     fileOut.close();		    
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
		// returns true if column is created successfully
	public boolean addColumn(String sheetName,String colName){
		//System.out.println("**************addColumn*********************");
		
		try{				
			fis = new FileInputStream(path); 
			workbook = new HSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		sheet=workbook.getSheetAt(index);
		
		row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		
		//cell = row.getCell();	
		//if (cell == null)
		//System.out.println(row.getLastCellNum());
		if(row.getLastCellNum() == -1)
			cell = row.createCell(0);
		else
			cell = row.createCell(row.getLastCellNum());
	        
	        cell.setCellValue(colName);
	        cell.setCellStyle(style);
	        
	        fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		    fileOut.close();		    

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
		
		
	}
	
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
				if(index==-1)
					return false;
				else
					return true;
		}
		else
			return true;
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
		 return -1;
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row==null)
			return -1;
		
		return row.getLastCellNum();
		
		
		
	}
	
	public int getCellRowNum(String sheetName,String colName,String cellValue){
		
		for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    		return i;
	    	}
	    }
		return -1;
		
	}
	
	public String getCellDataFor(ExcelOperation xls,String rowName,String colName){
		String colValue=null;
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, "TCID", rNum);
			 if(tcid.equals(rowName)){
				 
				   colValue=xls.getCellData(Constants.TESTCASES_SHEET, colName, rNum);
         	 }	
		}	
		return colValue;
	}
	
	public String getDataForTest(ExcelOperation xls,String rowName,String colName){
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
	
	public String getDataForTestName(ExcelOperation xls,String rowName,String colName){
		int rows = xls.getRowCount(Constants.TEST_SHEET);
		String colVal=null;
		for(int rNum=2;rNum<=rows;rNum++){
			String tcName = xls.getCellData(Constants.TEST_SHEET, "TC_Name", rNum);

			if(tcName.equals(rowName)){
				colVal=(xls.getCellData("TestData", colName, rNum));
				break;
			}


		}

		return colVal;
	}
}	
	
	
	// to run this on stand alone
	/*public static void main(String arg[]) throws IOException{
		Xls_Operation xls=null;
		//System.out.println(filename);
	xls = new Xls_Operation("C:\\Project\\SHAW\\at-shaw-sd\\src\\testdata\\GoldenSuite.xls");
		int rows = xls.getRowCount("TestData");
		
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData("TestData", "TCID", rNum);
            if(tcid.equals("Suspend_TripplePlay")){
            		
					System.out.println(xls.getCellData("TestData", "Sub_Category", rNum));
					
					System.out.println(xls.getCellData("TestData", "Run", rNum));
					
					System.out.println(xls.getCellData("TestData", "Phone_Product", rNum));
					
					
				}
		}
	
	
	}*/


