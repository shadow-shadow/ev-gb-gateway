package com.dyy.tsp.evgb.gateway.server.handler;

import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
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
        //TODO 转发查询参数响应结果
    }

    /**
     * 查询参数响应
     * @param protrocol
     */
    private void doQueryResponse(EvGBProtocol protrocol) {
        //TODO 转发设置参数响应结果
    }

}
