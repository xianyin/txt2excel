package net.xianyin.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import net.xianyin.excelcontrol.Text2Excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * 把左侧数据含有的右侧数据剔除
 * @author xianyin
 *
 */
public class RemoveDuplicateDataFrame {
	private JFrame frame;
	private JTextField textField;
	private JFileChooser fileChooser;
	private File file;
	private JTabbedPane tabbedPane;
	private List<String> waitFileList = new ArrayList<String>();//待删文件
	private List<String> duplicateFileList = new ArrayList<String>();//需要去除的列表
	private static int compareColumnNum;// 要比对的列
	final static JTextField splitSymbol = new JTextField(4); //用户输入的分隔符

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveDuplicateDataFrame window = new RemoveDuplicateDataFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RemoveDuplicateDataFrame() {
		initialize();
	}

	@SuppressWarnings("rawtypes")
	private void initialize() {
		this.frame = new JFrame();
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}

				String extension = RemoveDuplicateDataFrame.MyUtils.getExtension(f);
				if (extension != null) {
					if (extension.equals(MyUtils.xls)) {
						return true;
					} else if(extension.equals(MyUtils.xlsx)){
						return true;
					} else if(extension.equals("txt")) {
						return true;
					} else {
						return false;
					}
				}

				return false;
			}

			public String getDescription() {
				return "txt";
			}
		});
		this.frame.setBounds(100, 100, 643, 496);
		this.frame.setDefaultCloseOperation(3);
		this.frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		JPanel panelNorth = new JPanel();
		JPanel panelSouth = new JPanel();
		this.frame.getContentPane().add(panel, "South");
		panel.setLayout(new BorderLayout(0, 0));

		this.textField = new JTextField();
		this.textField.setEditable(false);
		panel.add(this.textField);
		this.textField.setColumns(10);
		
		String[] selectColumnNum = {"0","1","2"};
		@SuppressWarnings("unchecked")
		JComboBox combo = new JComboBox(selectColumnNum);
		combo.setBorder(BorderFactory.createTitledBorder("输入待比较的列号"));
		compareColumnNum = Integer.parseInt((String)combo.getSelectedItem());
		
		splitSymbol.setToolTipText("请输入文档内分隔符，如果为空则不需要分割");
		panelNorth.add(splitSymbol , "EAST");
//		combo.
		// JButton btnCenter = new JButton("执行");
		JButton btnWait = new JButton("TXT待去重文件");
		JButton btnDuplicate = new JButton("TXT重复的文件");
		JButton btnResult = new JButton("生成结果");
		combo.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				//一共发生了2个事件，旧的选项DESELECTED，以及新的选项SELECTED
			    if(event.getStateChange() == ItemEvent.SELECTED) {
				    compareColumnNum = Integer.parseInt((String)event.getItem());
//				    new JOptionPane().showMessageDialog(null, userinput.getText());
			    }
				
			}
		});
		/*
		 * 待查重文件，在东方
		 */
		btnWait.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				RemoveDuplicateDataFrame.this.fileChooser.showOpenDialog(RemoveDuplicateDataFrame.this.frame);
				if (RemoveDuplicateDataFrame.this.fileChooser.getSelectedFile() == null) {
					return;
				}
				RemoveDuplicateDataFrame.this.textField.setText(RemoveDuplicateDataFrame.this.fileChooser
						.getSelectedFile().getAbsolutePath());
				RemoveDuplicateDataFrame.this.file = RemoveDuplicateDataFrame.this.fileChooser
						.getSelectedFile();
				// RemoveDuplicateDataFrame.this.tabbedPane.removeAll();
				String extension = MyUtils.getExtension(file);
				if(extension.equals(MyUtils.xls) || extension.equals(MyUtils.xlsx)) 
				{
					RemoveDuplicateDataFrame.this.waitFileList = RemoveDuplicateDataFrame.MyUtils.excel2List(file);
					new JOptionPane().showMessageDialog(null,
							"文件已正常加载！");
				} else {
					try {
						BufferedReader br = new BufferedReader(new FileReader(RemoveDuplicateDataFrame.this.file));
						RemoveDuplicateDataFrame.this.waitFileList = RemoveDuplicateDataFrame.MyUtils.text2List(br);
						new JOptionPane().showMessageDialog(null,"文件已正常生成！");
						RemoveDuplicateDataFrame.this.tabbedPane.updateUI();
					} catch (IOException e1) {
						new JOptionPane().showMessageDialog(null, "程序意外终止");
						System.exit(0);
						e1.printStackTrace();
					}
				}
			}
		});
		/*
		 * 待去重数据
		 */
		btnDuplicate.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				
				RemoveDuplicateDataFrame.this.fileChooser.showOpenDialog(RemoveDuplicateDataFrame.this.frame);
				if (RemoveDuplicateDataFrame.this.fileChooser.getSelectedFile() == null) {
					return;
				}
				RemoveDuplicateDataFrame.this.textField.setText(RemoveDuplicateDataFrame.this.fileChooser
						.getSelectedFile().getAbsolutePath());
				RemoveDuplicateDataFrame.this.file = RemoveDuplicateDataFrame.this.fileChooser
						.getSelectedFile();
				// RemoveDuplicateDataFrame.this.tabbedPane.removeAll();
				String extension = MyUtils.getExtension(file);
				if(extension.equals(MyUtils.xls) || extension.equals(MyUtils.xlsx)) 
				{
					RemoveDuplicateDataFrame.this.duplicateFileList = RemoveDuplicateDataFrame.MyUtils.excel2List(file);
					new JOptionPane().showMessageDialog(null,
							"文件已正常加载！");
				} 
				else 
				{
					try {
						BufferedReader br = new BufferedReader(new FileReader(RemoveDuplicateDataFrame.this.file));
						RemoveDuplicateDataFrame.this.duplicateFileList = RemoveDuplicateDataFrame.MyUtils.text2List(br);
	
						new JOptionPane().showMessageDialog(null,
								"文件已正常加载！");
						RemoveDuplicateDataFrame.this.tabbedPane.updateUI();
					} catch (IOException e1) {
						new JOptionPane().showMessageDialog(null, "程序意外终止");
						System.exit(0);
						e1.printStackTrace();
					}
				}
			}
		});
		/*
		 * 生成结果
		 */
		btnResult.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				
				// RemoveDuplicateDataFrame.this.tabbedPane.removeAll();
				try {
					Date startTime = new Date();
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File("Result.txt")));
					if(duplicateFileList.size() > 0 && waitFileList.size() > 0) {
						List<String> textList = new ArrayList<String>();
						// 重复字符串，已经被分割版本。避免每次都进行分割操作
						List<String> duplicateHasSplitList = new ArrayList<String>(); 
						for(int i = 0 ; i < duplicateFileList.size() ; i++) {
							// 不把null放到 重复字符串列表里，是为了防止待查重文件没索引的行被删掉
							if(MyUtils.getStringByIndex(duplicateFileList.get(i)) != null) 
								duplicateHasSplitList.add(MyUtils.getStringByIndex(duplicateFileList.get(i)));			
						}
						for(int i = 0 ; i < waitFileList.size(); i++) {
							if(duplicateHasSplitList.contains(MyUtils.getStringByIndex(waitFileList.get(i))))
								continue;
							bw.write(waitFileList.get(i));
							bw.newLine();
							textList.add(waitFileList.get(i));
						}
						String extension = MyUtils.getExtension(file);
						if(extension.equals(MyUtils.xls) || extension.equals(MyUtils.xlsx)) {
							Text2Excel.createExcel("Result.txt", "xlsx", textList, "\t");
						}
						
						
						bw.flush();
						bw.close();
						Date endTime = new Date();
						new JOptionPane().showMessageDialog(null,
								"文件已正常生成！耗时：" + (endTime.getTime() - startTime.getTime()) / 1000 + " s");
					} else {
						new JOptionPane().showMessageDialog(null, "请确保输入了两个非空文件");
					}
						
					RemoveDuplicateDataFrame.this.tabbedPane.updateUI();
				} catch (IOException e1) {
					new JOptionPane().showMessageDialog(null, "程序意外终止");
					System.exit(0);
					e1.printStackTrace();
				}
			}
		});
		panelSouth.add(btnDuplicate, "West");
		panelSouth.add(btnWait, "East");
		panelSouth.add(btnResult , BorderLayout.CENTER);
		panelNorth.add(combo ,  "West");
		panel.add(panelSouth , BorderLayout.SOUTH);
		panel.add(panelNorth , BorderLayout.NORTH);

		this.tabbedPane = new JTabbedPane(1);
		this.frame.getContentPane().add(this.tabbedPane, "Center");
	}

	public static class MyUtils {
		public static final String xls = "xls";
		public static final String xlsx = "xlsx";

		public static String getExtension(File f) {
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if ((i > 0) && (i < s.length() - 1)) {
				ext = s.substring(i + 1).toLowerCase();
			}
			return ext;
		}
		/**
		 * text转成List结构，注意空行会被过滤
		 */
		public static List<String> text2List(BufferedReader br) {
			List<String> list = new ArrayList<String>();
			try {
				String line = null;
				while((line = br.readLine()) != null) {
					//注意，空行会被过滤掉
					if(line.trim().equals(""))
						continue;
					list.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		/**
		 * 解析excel并返回字符串数组，注意空行会被过滤
		 * @param f 文件
		 * @return
		 */
		public static List<String> excel2List(File f) {
			List<String> list = new ArrayList<String>();
			try 
			{
				InputStream inp = new FileInputStream(f);
				Workbook wb = WorkbookFactory.create(inp);
				int sheetNum = wb.getNumberOfSheets();
				for(int i = 0 ; i < sheetNum; i ++) {
					Sheet sheet = wb.getSheetAt(i);
					for(Row row : sheet) {
						String handleStr = "";
						for(Cell cell : row) {
							if(cell != null) {
								handleStr += cell.toString();
								handleStr += "\t";
							}
						}
						list.add(handleStr);
					}
				}
			 
			} catch(IOException e) {
				e.printStackTrace();
			} catch(InvalidFormatException e1) {
				e1.printStackTrace();
			}
			return list;
		}
		/**
		 * 获取待比较字符串。把一个字符串按照分隔符切割，提取第N个元素。
		 * 注意1：如果没有该索引，则返回空
		 * 注意2：如果没有设定分隔符，默认分隔符为\t
		 * @param longstring
		 * @return
		 */
		public static String getStringByIndex(String longstring) {
			String symbol = "\t"; // 默认分隔符
			if(splitSymbol.getText() != null && !splitSymbol.getText().equals(""))
				symbol = splitSymbol.getText();
			String[] strs = longstring.split(symbol);
			if(strs.length < compareColumnNum + 1) 
				return null;
			return strs[compareColumnNum];
		}

	}
}
