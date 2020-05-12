package com.dyy.tsp.evgb.gateway.server.handler;

import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 心跳处理器。需要给终端响应
 * 国家要求实时信息上报为10秒-30秒。心跳应该合理设置间隔。完成补发信息上报。
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class HeartBeatHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        doHeartResponse(ResponseType.SUCCESS,protrocol,channel);
    }
}
