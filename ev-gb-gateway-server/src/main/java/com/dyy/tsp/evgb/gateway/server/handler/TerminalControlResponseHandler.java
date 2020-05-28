package com.dyy.tsp.evgb.gateway.server.handler;

import com.dyy.tsp.evgb.gateway.protocol.dto.CommandDownResponse;
import com.dyy.tsp.evgb.gateway.protocol.entity.BeanTime;
import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandDownHelperType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 车载终端控制响应处理器
 * created by dyy
 */
@SuppressWarnings("all")
@Service
public class TerminalControlResponseHandler extends AbstractBusinessHandler {

    @Autowired
    private ForwardHandler forwardHandler;

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        BeanTime beanTime = (BeanTime)protrocol.getBody().getJson().toJavaObject(BeanTime.class);
        CommandDownResponse commandDownResponse = new CommandDownResponse();
        commandDownResponse.setReuqestTime(beanTime.toTimestamp());
        commandDownResponse.setResponseType(protrocol.getResponseType());
        commandDownResponse.setVin(protrocol.getVin());
        commandDownResponse.setCommand(CommandDownHelperType.valuesOf(protrocol.getCommandType().name()));
        commandDownResponse.setResponseTime(protrocol.getGatewayReceiveTime());
        forwardHandler.sendToRemoteResponse(commandDownResponse);
    }

}
