package com.dyy.tsp.evgb.gateway.tcu.handler;

import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 接收车载终端控制请求后做出响应
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class TerminalControlHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalControlHandler.class);

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        TerminalControlRequest terminalControlRequest = (TerminalControlRequest)protrocol.getBody().getJson().toJavaObject(TerminalControlRequest.class);
        protrocol.setBody(new DataBody(terminalControlRequest.getBeanTime().encode()));
        protrocol.setResponseType(ResponseType.SUCCESS);
        channel.writeAndFlush(protrocol.encode());
        LOGGER.debug("{} {} {} 响应{}",protrocol.getVin(),terminalControlRequest.getBeanTime().toTimestamp(),protrocol.getCommandType().getDesc(),protrocol.getResponseType().getDesc());
    }

}
