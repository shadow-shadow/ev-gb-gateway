package com.dyy.tsp.evgb.gateway.exe.util;

import com.dyy.tsp.evgb.gateway.exe.gui.FontConfig;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("all")
public class ComponentUtil {

    /**
     * 创建标签
     */
    public static JLabel createJLabel(String name, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(name);
        label.setFont(FontConfig.fontBold);
        label.setBounds(x,y,width,height);
        if(font!=null){
            label.setFont(font);
        }
        return label;
    }

    /**
     * 创建文本框
     */
    public static JTextField createJTextField(String name, int x, int y, int width, int height, Font font) {
        JTextField jTextField = new JTextField(name);
        jTextField.setBounds(x,y,width,height);
        jTextField.setBackground(Color.white);
        jTextField.setEditable(false);
        jTextField.setBorder(null);
        if(font != null){
            jTextField.setFont(font);
        }
        return jTextField;
    }

    /**
     * 创建文本框
     * 有提示语的
     */
    public static JTextField createJTextField(String name, int x, int y, int width, int height, Font font, String tip) {
        JTextField jTextField = new JTextField(name);
        jTextField.setBounds(x,y,width,height);
        jTextField.setBackground(Color.white);
        jTextField.setEditable(false);
        jTextField.setBorder(null);
        if(font != null){
            jTextField.setFont(font);
        }
        if(StringUtils.isNotBlank(tip)){
            jTextField.setToolTipText(tip);
        }
        return jTextField;
    }

    /**
     * 创建按钮
     */
    public static JButton createJButton(String name, int x, int y, int width, int height, Font font) {
        JButton button = new JButton(name);
        button.setFont(font);
        button.setBounds(x,y,width,height);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * 初始化文本域
     */
    public static JTextArea createJTextArea(String name, int x, int y, int width, int height, Font font,boolean isLineWrap) {
        JTextArea jTextArea = new JTextArea(name);
        jTextArea.setFont(font);
        jTextArea.setBounds(x,y,width,height);
        //自动换行
        jTextArea.setLineWrap(isLineWrap);
        jTextArea.setWrapStyleWord(isLineWrap);
        return jTextArea;
    }

    /**
     * 初始化文本域
     */
    public static JTextArea createJTextArea(String name, Font font,boolean isLineWrap) {
        JTextArea jTextArea = new JTextArea(name);
        jTextArea.setFont(font);
        //自动换行
        jTextArea.setLineWrap(isLineWrap);
        //设置在单词过长的时候是否要把长单词移到下一行
        jTextArea.setWrapStyleWord(isLineWrap);
        return jTextArea;
    }
}
