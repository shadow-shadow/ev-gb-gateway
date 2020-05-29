package com.dyy.tsp.evgb.gateway.server.listener;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.protocol.common.CommonCache;
import com.dyy.tsp.evgb.gateway.protocol.dto.CommandDownRequest;
import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.util.HelperKeyUtil;
import com.dyy.tsp.evgb.gateway.server.cache.CaffeineCache;
import com.dyy.tsp.evgb.gateway.server.config.EvGBProperties;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 指令下发处理器
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class CommandDownHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandDownHandler.class);

    @Autowired
    private CaffeineCache caffeineCache;
    @Autowired
    private EvGBProperties evGBProperties;

    /**
     * 远程控制指令下发
     * @param message
     * @param topic
     */
    public void doBusiness(String message,String topic){
        LOGGER.debug("receive command request {}",message);
        CommandDownRequest request = JSONObject.parseObject(message, CommandDownRequest.class);
        switch (request.getCommand()){
            case REMOTE_CONTROL:
                this.doRemoteControl(request);
                break;
            case QUERY_COMMAND:
            case SET_COMMAND:
                this.doParamsRequest(request);
                break;
            case OPEN_DEBUG:
                if(evGBProperties.getOnlineMonitoringFlag()){
                    CommonCache.debugVinMap.put(request.getVin(),request.getCommand().name());
                    LOGGER.debug("{} webSocket console open",request.getVin());
                }else{
                    LOGGER.warn("Gateway is not open onlineMonitoring,Please modify the nacos configuration");
                }
                break;
            case CLOSE_DEBUG:
                if(evGBProperties.getOnlineMonitoringFlag()){
                    CommonCache.debugVinMap.remove(request.getVin());
                    LOGGER.debug("{} webSocket console close",request.getVin());
                }else{
                    LOGGER.debug("Gateway is not open onlineMonitoring");
                }
                break;
            case CLEAR_CAHCE:
                caffeineCache.remove(HelperKeyUtil.getKey(request.getVin()));
                LOGGER.debug("{} clear cache",request.getVin());
                break;
            default:
                break;

        }
    }

    /**
     * 车载终端控制
     * @param request
     */
    private void doRemoteControl(CommandDownRequest request) {
        Channel channel = getChannel(request.getVin());
        if(channel!=null && channel.isActive()){
            EvGBProtocol protocol = new EvGBProtocol();
            protocol.setCommandType(CommandType.valuesOf(request.getCommand().name()));
            protocol.setVin(request.getVin());
            TerminalControlRequest terminalControlRequest = new TerminalControlRequest();
            TerminalControlType terminalControlType = request.getTerminalControlType();
            terminalControlRequest.setBeanTime(new BeanTime(request.getTime()));
            terminalControlRequest.setTerminalControlType(terminalControlType);
            protocol.setBody(new DataBody(terminalControlRequest.encode()));
            ByteBuf encode = protocol.encode();
            encode.markReaderIndex();
            byte[] array = new byte[encode.writerIndex()];
            encode.readBytes(array);
            String hex = ByteUtil.byteToHex(array);
            encode.resetReaderIndex();
            channel.writeAndFlush(encode);
            LOGGER.debug("{} {} {} {} 下发成功 {}",request.getVin(),request.getTime(),protocol.getCommandType().getDesc(),terminalControlType.getId(),hex);
        }else{
            LOGGER.debug("{} {} {} this node is not have channel",request.getVin(),request.getTime(),request.getCommand().getDesc());
        }
    }

    /**
     * 查询/设置参数指令处理
     * @param request
     */
    private void doParamsRequest(CommandDownRequest request) {
        Channel channel = getChannel(request.getVin());
        if(channel!=null && channel.isActive()){
            EvGBProtocol protocol = new EvGBProtocol();
            protocol.setCommandType(CommandType.valuesOf(request.getCommand().name()));
            protocol.setVin(request.getVin());
            ByteBuf bodyBuf = null;
            switch (protocol.getCommandType()){
                case QUERY_COMMAND:
                    QueryParamsRequest queryParamsRequest = request.getQueryParamsRequest();
                    queryParamsRequest.setBeanTime(new BeanTime(request.getTime()));
                    bodyBuf=queryParamsRequest.encode();
                    break;
                case SET_COMMAND:
                    SetParamsRequest setParamsRequest = request.getSetParamsRequest();
                    setParamsRequest.setBeanTime(new BeanTime(request.getTime()));
                    bodyBuf=setParamsRequest.encode();
                    break;
            }
            if(bodyBuf!=null){
                protocol.setBody(new DataBody(bodyBuf));
                ByteBuf encode = protocol.encode();
                encode.markReaderIndex();
                byte[] array = new byte[encode.writerIndex()];
                encode.readBytes(array);
                String hex = ByteUtil.byteToHex(array);
                encode.resetReaderIndex();
                channel.writeAndFlush(encode);
                LOGGER.debug("{} {} {} 下发成功 {}",request.getVin(),request.getTime(),protocol.getCommandType().getDesc(),hex);
            }
        }else{
            LOGGER.debug("{} {} {} this node is not have {} channel",request.getVin(),request.getTime(),request.getCommand().getDesc());
        }

    }

    /**
     * 获取设备连接
     * @param vin
     * @return
     */
    private Channel getChannel(String vin){
        return CommonCache.vinChannelMap.get(vin);
    }

}
