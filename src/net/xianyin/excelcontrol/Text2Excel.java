package net.xianyin.excelcontrol;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * text convert into .xls or .xlsx
 * @author xianyin
 *
 */
public class Text2Excel {
	final static int XLSMAXROW = 65535;	// The max row number of xls file 
	/**
	 * from text String to create excel file
	 * @param filePath
	 * @param textList List<String>
	 * @param splitSymbol
	 */
	public static void createExcel(String filePath , String fileType , List<String> textList , String splitSymbol) {

		Workbook wb = null;
		if(fileType.equals(InnerUtils.xls) && textList.size() <= XLSMAXROW) {
			filePath += ".xls";
			wb = new HSSFWorkbook();
		} else {
			filePath += ".xlsx";
			wb = new XSSFWorkbook();
		}
		Sheet sheet = wb.createSheet();
		// declare a row object reference
		Row row = null;
		String[] rowStrings = null;
		// Define a few rows
		for(int rownum = 0 ; rownum < textList.size() ; rownum++) {
			rowStrings = textList.get(rownum).split(splitSymbol);
			row = sheet.createRow(rownum);
			for(int cellnum = 0; cellnum < rowStrings.length; cellnum++) {
				row.createCell(cellnum).setCellValue(rowStrings[cellnum]);
			}
		}
		
		// Save
//	    String filename = "workbook.xls";
//	    if(wb instanceof XSSFWorkbook) {
//	    	filename = filename + "x";
//	    }
	 
	    FileOutputStream out;
		try {
			out = new FileOutputStream(filePath);
		    wb.write(out);
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		   
	}
	
	public static class InnerUtils {

		/*
		 * file extension
		 */
		public final static String xls = "xls";
		public final static String xlsx = "xlsx";
		

		/*
		 * Get the extension of a filePath.
		 */
		public static String getExtension(String filePath) {
			String ext = null;
			int i = filePath.lastIndexOf('.');

			if (i > 0 && i < filePath.length() - 1) {
				ext = filePath.substring(i + 1).toLowerCase();
			}
			return ext;
		}
	}

}
