package net.xianyin.excelcontrol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Excel2Text {
	/**
	 * 将excel转为txt，工作簿名称将作为最后一列
	 * @param inp 文件流
	 * @param fileName 文件名|xxx.xls or xxx.xlsx
	 * @param splitSymbol 单元格和单元格之间的分隔符，如\t
	 * @param replaceSheetName 待替换的sheet表名
	 */
	public static void excel2txt(InputStream inp , String fileName , String splitSymbol , HashMap<String , String> replaceSheetName) {
//		InputStream inp = new FileInputStream("workbook.xls");
		try {
			Workbook wb = WorkbookFactory.create(inp);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(MyUtils.getFileName(fileName))));
			int sheetNum = wb.getNumberOfSheets();
			for(int i = 0 ; i < sheetNum; i ++) {
				Sheet sheet = wb.getSheetAt(i);
				String sheetname = wb.getSheetName(i);
				if(replaceSheetName.get(sheetname.trim()) != null) {
					sheetname = replaceSheetName.get(sheetname);
				}
				for(Row row : sheet) {
					for(Cell cell : row) {
						if(cell != null) {
							bw.write(cell.toString());
							bw.write(splitSymbol);
						}
					}
					bw.write(sheetname);
					bw.newLine();
				}
				bw.flush();
			}
			bw.close();
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	public static class MyUtils {
		public static String getFileName(String filePath) {
			String name = "default.txt";
			int i = filePath.lastIndexOf('.');

			if (i > 0 && i < filePath.length() - 1) {
				name = filePath.substring(0,i).toLowerCase();
				name += ".txt";
			}
			return name;
		}
//		public static boolean isNotEmpty(Object obj) {
//			
//		}
	}

}
