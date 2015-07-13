package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.xianyin.excelcontrol.Text2Excel;

import org.junit.Test;

public class TestText2Excel {
	@Test
	public void createExcelTest() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("test.txt")));
			List<String> textList = new ArrayList<String>();
			String str = null;
			while((str = br.readLine()) != null) {
				textList.add(str);
			}

			Text2Excel.createExcel("test.txt" , "xls" , textList , ",");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
