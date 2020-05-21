package com.dyy.tsp.evgb.gateway.exe.gui;

import com.dyy.tsp.evgb.gateway.exe.event.OnAcctionEvent;
import com.dyy.tsp.evgb.gateway.exe.handler.BusinessHandler;
import com.dyy.tsp.evgb.gateway.exe.util.ComponentUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.awt.*;

@Slf4j
@Data
@SuppressWarnings("all")
public class HexVisualizationPanel extends JPanel{

    /**
     * 按钮事件
     */
    private OnAcctionEvent acctionEvent;

    /**
     * 输入输出文本域
     */
    private JTextArea inputArea,outArea;

    /**
     * 按钮
     */
    private JButton decodeBtn,clearBtn;

    private BusinessHandler businessHandler;

    public HexVisualizationPanel(BusinessHandler businessHandler) {
        super();
        businessHandler.setHexVisualizationPanel(this);
        this.businessHandler = businessHandler;
        //空布局(自己定位布局)
        this.setLayout(null);
        //白色背景
        this.setBackground(Color.white);
        int height = 230;
        inputArea = ComponentUtil.createJTextArea("请输入HEX报文...",FontConfig.font16,Boolean.TRUE);
        JScrollPane inputJsp = new JScrollPane();
        inputJsp.setBounds(5,5,Config.mainPanelWidth-120,130);
        inputJsp.setViewportView(inputArea);
        this.add(inputJsp);
        /*******************解析和清空按钮************************/
        decodeBtn = ComponentUtil.createJButton("解析",Config.mainPanelWidth-110,10,80,35,FontConfig.font16);
        this.add(decodeBtn);
        clearBtn = ComponentUtil.createJButton("清空",Config.mainPanelWidth-110,100,80,35,FontConfig.font16);
        this.add(clearBtn);

        outArea = ComponentUtil.createJTextArea("",FontConfig.font16,Boolean.TRUE);
        //禁止输入
        outArea.setEditable(false);
        //滚动面板
        JScrollPane jsp2 = new JScrollPane();
        jsp2.setBounds(5,220,Config.mainPanelWidth-20,Config.mainPanelHeight-330);
        jsp2.setViewportView(outArea);
        //增加行号
//        jsp2.setRowHeaderView(new LineNumberHeaderView(FontConfig.font16));
        this.add(jsp2);

        //添加按钮事件
        acctionEvent = new OnAcctionEvent(businessHandler);
        decodeBtn.addActionListener(acctionEvent);
        decodeBtn.setActionCommand("HEX-PARSE");
        clearBtn.addActionListener(acctionEvent);
        clearBtn.setActionCommand("HEX-CLEAR");
    }


    /**
     * 清除方法
     */
    public void clear() {
        inputArea.setText("请输入HEX报文...");
        outArea.setForeground(null);
        outArea.setText(null);
    }

    /**
     * 解析展示JSON报文
     */
    public void showMessage(String message,Boolean errorFlag) {
        if(errorFlag){
            outArea.setForeground(Color.RED);
        }else{
            outArea.setForeground(null);
        }
        outArea.setText(message);
    }

}
