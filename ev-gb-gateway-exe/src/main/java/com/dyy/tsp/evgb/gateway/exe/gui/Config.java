package com.dyy.tsp.evgb.gateway.exe.gui;

import lombok.extern.slf4j.Slf4j;
import java.awt.*;

@Slf4j
public class Config {

    /**
     * 得到屏幕的尺寸
     */
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * 屏幕宽度
     */
    public static int windowsWidth = screenSize.width;
    /**
     * 屏幕高度
     */
    public static int windowsHeight = screenSize.height;
    /**
     * 主窗体宽度
     */
    public static int mainPanelWidth;
    /**
     * 主窗体高度
     */
    public static int mainPanelHeight;

    /**
     * 项目初始化窗体宽度和高度
     */
    static {
        //主窗体宽度
        int width = (int)(windowsWidth * 0.7);
        if(width < 700){
            mainPanelWidth = windowsWidth;
        }else{
            mainPanelWidth = width;
        }
        //主窗体高度
        int height = (int)(windowsHeight * 0.8);
        if(height < 600){
            mainPanelHeight = windowsHeight;
        }else{
            mainPanelHeight = height;
        }
    }

}
