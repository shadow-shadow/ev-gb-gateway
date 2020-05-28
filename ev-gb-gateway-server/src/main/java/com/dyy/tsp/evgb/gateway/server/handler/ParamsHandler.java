package com.dyy.tsp.evgb.gateway.server.handler;

import com.dyy.tsp.evgb.gateway.protocol.dto.CommandDownResponse;
import com.dyy.tsp.evgb.gateway.protocol.entity.BeanTime;
import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.protocol.entity.PlatformLogout;
import com.dyy.tsp.evgb.gateway.protocol.entity.QueryParamsResponse;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandDownHelperType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 查询参数/设置参数处理器
 */
@Service
@SuppressWarnings("all")
public class ParamsHandler extends AbstractBusinessHandler {

    @Autowired
    private ForwardHandler forwardHandler;

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        switch (protrocol.getCommandType()){
            case SET_COMMAND: {
                this.doQueryResponse(protrocol);
                break;
            }
            case QUERY_COMMAND:{
                this.doSetResponse(protrocol);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 设置参数响应
     * @param protrocol
     */
    private void doSetResponse(EvGBProtocol protrocol) {
        BeanTime beanTime = (BeanTime)protrocol.getBody().getJson().toJavaObject(BeanTime.class);
        CommandDownResponse commandDownResponse = new CommandDownResponse();
        commandDownResponse.setReuqestTime(beanTime.toTimestamp());
        commandDownResponse.setResponseType(protrocol.getResponseType());
        commandDownResponse.setVin(protrocol.getVin());
        commandDownResponse.setCommand(CommandDownHelperType.valuesOf(protrocol.getCommandType().name()));
        commandDownResponse.setResponseTime(protrocol.getGatewayReceiveTime());
        forwardHandler.sendToRemoteResponse(commandDownResponse);
    }

    /**
     * 查询参数响应
     * @param protrocol
     */
    private void doQueryResponse(EvGBProtocol protrocol) {
        QueryParamsResponse queryParamsResponse = (QueryParamsResponse)protrocol.getBody().getJson().toJavaObject(QueryParamsResponse.class);
        CommandDownResponse commandDownResponse = new CommandDownResponse();
        commandDownResponse.setReuqestTime(queryParamsResponse.getBeanTime().toTimestamp());
        commandDownResponse.setResponseType(protrocol.getResponseType());
        commandDownResponse.setQueryParamsResponse(queryParamsResponse);
        commandDownResponse.setVin(protrocol.getVin());
        commandDownResponse.setCommand(CommandDownHelperType.valuesOf(protrocol.getCommandType().name()));
        commandDownResponse.setResponseTime(protrocol.getGatewayReceiveTime());
        forwardHandler.sendToRemoteResponse(commandDownResponse);
    }

}
