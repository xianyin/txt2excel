package net.xianyin.complex.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ToolsUI {
	
	private JFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ToolsUI window = new ToolsUI();
				window.frame.setVisible(true);
			}
		});
	}
	
	public ToolsUI() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	}
}
