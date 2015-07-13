package net.xianyin.complex.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.UIManager;

/**
 * 配置类
 * @author xianyin
 *
 */
public class ConfigGet {
	String softtitle;	//上面的标题文字
	int width,height;//初始窗体宽高
	int leftmenuwidth , splitbarwidth;//主要区域,左二级菜单宽度|左右分格面板的宽度
	boolean iffulltop , ifnormaltop , openfull , lmenucanclose; //窗体最大时总前端显示|正常模式总前端显示|启动时左边菜单是否显示|左边是否可以关闭
	//init
	ConfigGet()
	{
		width = 800;
		height = 600;
		leftmenuwidth = 150 ; //左边二级菜单宽度
		splitbarwidth = 6 ; //分格面板宽度
		softtitle = "弦音的小小工具箱";
		lmenucanclose = true; //是否可以关闭左边菜单栏
		openfull = false ; //软件启动时就全屏
		iffulltop = true ; //全屏幕是否在最前端显示
		ifnormaltop = false ; //非全屏是否在最前端显示
	}
	
	public Dimension getFrameSmallSize(boolean full) {
		if(!full){ //不是最大化初始
			return new Dimension(width,height);
		} 
		else {
			Toolkit kit = Toolkit.getDefaultToolkit();
			return new Dimension(kit.getScreenSize());
		}	
	}
	
	//初始化UI
	public void setFrameUI() {
		Font font = new Font("宋体", Font.PLAIN, 12);
		UIManager.put("Label.font", font);
		UIManager.put("Button.font", font);
		
		Color gray = new Color(240,240,240);
		Color black = new Color(0,0,0);
		
        UIManager.put("ScrollBar.track", black);
        UIManager.put("ScrollBar.trackHighlight",black);
		
        UIManager.put("ScrollBar.background", gray);
        UIManager.put("ScrollBar.shadow", gray);
        UIManager.put("ScrollBar.darkShadow", gray);
        
        UIManager.put("ScrollBar.thumb", gray);
        UIManager.put("ScrollBar.thumbShadow",gray);
        UIManager.put("ScrollBar.thumbDarkShadow",gray);
        UIManager.put("ScrollBar.thumbHighlight", new Color(250,250,250));
        //UIManager.put("ScrollBar.thumbLightShadow", new Color(250,60,250));   
        UIManager.put("ScrollBar.width", 17);
	}

	/*
	 * 返回分辨率
	 */
	public Dimension getScreenSize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		return new Dimension(kit.getScreenSize());
	}

}
