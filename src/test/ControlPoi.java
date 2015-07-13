package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 测试代码
 * @author xianyin
 *
 */
public class ControlPoi {
	public static void main(String[] args) {
		createExcel();
	}
	
	public static void createExcel(){
		HSSFWorkbook wb = new HSSFWorkbook();
	    HSSFSheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    HSSFRow row = sheet.createRow((short) 1);

	    // Aqua background
	    CellStyle style = wb.createCellStyle();
	    HSSFCell cell = row.createCell(1);
	    cell.setCellValue("X");
	    cell.setCellStyle(style);

	    // Orange "foreground", foreground being the fill foreground not the font color.

//	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cell = row.createCell((short) 2);
	    cell.setCellValue("X");


	    // Write the output to a file
	    FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("workbook3.xls");
			 wb.write(fileOut);
			 fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   
	}
	public static void xls2txt(){
		try {
			 InputStream inp = new FileInputStream("test.xls");
		    HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		    ExcelExtractor extractor = new ExcelExtractor(wb);

		    extractor.setFormulasNotResults(true);
		    extractor.setIncludeSheetNames(false);
		    String text = extractor.getText();
		    System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}

}
