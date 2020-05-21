package com.dyy.tsp.evgb.gateway.exe.event;

import java.awt.event.FocusEvent;

public interface IFocus {

    /**
     * 获取焦点
     */
    void focusGained(FocusEvent e);

    /**
     * 失去焦点
     */
    void focusLost(FocusEvent e);
}
