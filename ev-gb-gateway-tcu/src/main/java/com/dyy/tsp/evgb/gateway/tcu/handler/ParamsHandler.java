package com.dyy.tsp.evgb.gateway.tcu.handler;

import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.ResponseType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Instant;

/**
 * 查询参数/设置参数处理器
 */
@Service
@SuppressWarnings("all")
public class ParamsHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParamsHandler.class);

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        this.doSetOrQueryRequest(protrocol,channel);
    }

    /**
     * 查询/设置参数请求
     * @param protrocol
     * @param channel
     */
    private void doSetOrQueryRequest(EvGBProtocol protrocol, Channel channel) {
        protrocol.setResponseType(ResponseType.SUCCESS);
        if(protrocol.getCommandType() == CommandType.QUERY_COMMAND){
            QueryParamsResponse response = new QueryParamsResponse();
            response.setBeanTime(new BeanTime(Instant.now().toEpochMilli()));
            response.setCount((short)16);
            Params params = new Params();
            params.setLocalStorageTimeCycleOfVehicleTerminal(10);
            params.setInformationReportingCycle(10);
            params.setAlarmInformationReportingCycle(10);
            params.setRemoteServiceManagementPlatformHostLength((short)9);
            params.setRemoteServiceManagementPlatformHost("localhost");
            params.setRemoteServiceManagementPlatformPort(8111);
            params.setHardwareVersion("12345");
            params.setSoftwareVersion("12345");
            params.setHeartbeatSendingCycle((short)9);
            params.setTboxResponseTimeOut(60);
            params.setTspResponseTimeOut(60);
            params.setLoginTimeInterval((short)5);
            params.setCommonPlatformHostLength((short)9);
            params.setCommonPlatformHost("localhost");
            params.setCommonPlatformPort(9090);
            params.setUnderSamplingMonitoring((short)1);
            response.setParams(params);
            protrocol.setBody(new DataBody(response.encode()));
        }else{
            protrocol.setBody(null);
        }
        channel.writeAndFlush(protrocol.encode());
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("{} response {}",protrocol.getVin(),protrocol.getCommandType().getDesc());
        }
    }

}
