package net.xianyin.complex.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
;

public class MainPanel extends JPanel {
	//宽高
	int width,height;
	ToolsFrame frame;
	ConfigGet con = new ConfigGet();
	JButton btn_min,btn_max,btn_close;//要用到的按钮
//	MiddlePanel m_panel;
	MainPanel(ToolsFrame frame , int w , int h) {
		this.width = w;
		this.height = h;
		this.frame = frame;
	}
}
