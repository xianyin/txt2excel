package net.xianyin.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import net.xianyin.excelcontrol.Excel2Text;

/**
 * 临时用一用。把excel生成txt
 * @author xianyin
 *
 */
public class Excel2TxtMain {
	public static void main(String[] args) {
		executeMethod();
	}
	public static void executeMethod() {
		String filename = "7.2其他关键词all.xlsx";
		String splitSymbol = " ";
		InputStream inp;
		HashMap<String , String> replaceSheetName = new HashMap<String, String>();
		replaceSheetName.put("板块名与词", "pkey");
		replaceSheetName.put("板块名非词", "nbkey");
		replaceSheetName.put("标题和正文非词", "nkey");
		try {
			inp = new FileInputStream(filename);
			
			Excel2Text.excel2txt(inp, filename, splitSymbol , replaceSheetName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
