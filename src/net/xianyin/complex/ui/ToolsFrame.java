package net.xianyin.complex.ui;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * 主窗体
 * @author xianyin
 *
 */
public class ToolsFrame extends JFrame {
	MainPanel panel;
	ConfigGet con;//配置类
	boolean isFullScreen = true; // 是否全屏
	Dimension fdm,sdm;//窗体大小:窗体|分辨率
	ToolsFrame(String title) {
		super(title);
		//初始化窗体
		setWindow(this);
	}

	//===============================
	//更新窗口
	//===============================
	private void updateWindow(JFrame f)
	{
		f.setUndecorated(true);//设置没有边框
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//添加系统退出事件
		f.validate();//有效
		f.setVisible(true);//可见
	}
	/*
	 * 初始化窗体
	 */
	private void setWindow(ToolsFrame toolsFrame) {
		con = new ConfigGet();
		con.setFrameUI();
//		action = new RepaintActionDo(f);
		//启动时是否全屏
		isFullScreen = con.openfull;
		fdm = con.getFrameSmallSize(isFullScreen);
		sdm = con.getScreenSize();
		toolsFrame.setSize(fdm); //设置窗体大小
		
	}
	
	/*
	 * 添加主面板
	 */
	private void addMainPanel(final ToolsFrame frame ,int fwidth,int fheight,boolean iffull) {
		panel = new MainPanel(frame,fwidth,fheight);
		panel.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		this.getContentPane().add(panel);
	}
	//===============================
	//创建菜单函数,
	//===============================
	public JButton getMenuButton(String buttonName , String tiptext)
	{
	
		JButton btn = new JButton(buttonName);
		btn.setPreferredSize(new Dimension(100,200));
		btn.setBorder(null);
		btn.setFocusCycleRoot(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setToolTipText(tiptext);
		return btn;//返回按钮
	}
}
