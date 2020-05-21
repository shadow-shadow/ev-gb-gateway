package com.dyy.tsp.evgb.gateway.exe.gui;

import com.dyy.tsp.evgb.gateway.exe.handler.BusinessHandler;
import javax.swing.*;
import java.awt.*;

/**
 * 主面板
 */
public class MainPanel extends JFrame {

    private BusinessHandler parseHandler;

    /**
     * 初始化
     */
    public MainPanel(BusinessHandler parseHandler){
        this.parseHandler = parseHandler;
        //初始化主面板
        this.initPanel();
        //设置标题
        this.setTitle("GB32960");
        //设置窗体大小
        this.setSize(Config.mainPanelWidth,Config.mainPanelHeight);
        //设置窗体大小最小值
        this.setMinimumSize(new Dimension(Config.mainPanelWidth,Config.mainPanelHeight));
        //设置根据桌面绝对定位
//        this.setLocation(Config.mainLocationWidth,Config.mainLocationHeight);
        //设置关闭窗口时，保证JVM也退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //禁止用户改变窗体大小
        this.setResizable(false);
        //居中，在初始化之后添加
        this.setLocationRelativeTo(null);
        //显示(默认不显示)
        this.setVisible(true);
    }

    /**
     * 初始化主面板
     */
    private void initPanel() {
        Container container = this.getContentPane();
        //设置图标
//        ImageIcon imageIcon = new ImageIcon("");
//        this.setIconImage(imageIcon.getImage());
        //创建选项卡
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setFont(FontConfig.font16);
        //创建滚动面板
        final JScrollPane scrollPane1 = new JScrollPane();
        jTabbedPane.addTab("HEX报文可视化",null,scrollPane1,"HEX报文可视化");
        //添加面板
        scrollPane1.setViewportView(new HexVisualizationPanel(parseHandler));
        final JScrollPane scrollPane2 = new JScrollPane();
        jTabbedPane.addTab("JSON报文编码HEX",null,scrollPane2,"JSON报文编码HEX");
        scrollPane2.setViewportView(new SimulationHexPanel(parseHandler));
        //面板添加到主窗体中,居中方式(默认)
        container.add(jTabbedPane,BorderLayout.CENTER);
    }

}

