package net.xianyin.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.xianyin.excelcontrol.Text2Excel;

public class Txt2ExcelMain {
	public static void main(String[] args) {
		executeMethod();
	}

	private static void executeMethod() {
		String filePath = "Result_all.txt";
		String fileType = "xlsx";
		List<String> textList = new ArrayList<String>();
		String splitSymbol = "\t";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			String str;
			while((str = br.readLine()) != null) {
				textList.add(str);
			}
			Text2Excel.createExcel(filePath, fileType, textList, splitSymbol);
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
