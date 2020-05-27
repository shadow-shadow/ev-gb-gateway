package com.dyy.tsp.evgb.gateway.tcu.handler;

import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * 接收车载终端控制请求后做出响应
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class TerminalControlHandler extends AbstractBusinessHandler {

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        TerminalControlRequest terminalControlRequest = (TerminalControlRequest)protrocol.getBody().getJson().toJavaObject(TerminalControlRequest.class);
        doCommonResponse(ResponseType.SUCCESS,protrocol,terminalControlRequest.getBeanTime(),channel);
    }

}
