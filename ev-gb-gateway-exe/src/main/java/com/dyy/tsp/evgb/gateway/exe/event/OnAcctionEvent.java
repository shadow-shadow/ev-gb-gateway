package com.dyy.tsp.evgb.gateway.exe.event;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

@Slf4j
public class OnAcctionEvent implements ActionListener {

    private IAction action;

    private long time = Instant.now().toEpochMilli();

    public OnAcctionEvent(IAction action){
        this.action = action;
    }

    /**
     * 按钮点击
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(action == null){
            log.error("按钮事件action为空!等待初始化!");
            return;
        }
        long currentTime = Instant.now().toEpochMilli();
        //防止连续点击
        if((currentTime - time) < 300){
            return;
        }
        time = currentTime;
        action.actionPerformed(e);
    }


}
