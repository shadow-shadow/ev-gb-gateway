package com.dyy.tsp.evgb.gateway.server.listener;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.util.ByteUtil;
import com.dyy.tsp.evgb.gateway.protocol.common.CommonCache;
import com.dyy.tsp.evgb.gateway.protocol.entity.*;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.util.HelperKeyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 指令下发处理器
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class CommandDownHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandDownHandler.class);

    /**
     * 远程控制指令下发
     * @param message
     * @param topic
     */
    public void doBusiness(String message,String topic){
        boolean debugEnabled = LOGGER.isDebugEnabled();
        if(debugEnabled){
            LOGGER.debug("receive command request {}",message);
        }
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
                CommonCache.debugVinMap.put(request.getVin(),request.getCommand().name());
                if(debugEnabled){
                    LOGGER.debug("{} webSocket console open",request.getVin());
                }
                break;
            case CLOSE_DEBUG:
                CommonCache.debugVinMap.remove(request.getVin());
                if(debugEnabled){
                    LOGGER.debug("{} webSocket console close",request.getVin());
                }
                break;
            case CLEAR_CAHCE:
                CommonCache.vehicleCacheMap.remove(HelperKeyUtil.getKey(request.getVin()));
                if(debugEnabled){
                    LOGGER.debug("{} clear cache",request.getVin());
                }
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
        boolean debugEnabled = LOGGER.isDebugEnabled();
        Channel channel = getChannel(request.getVin());
        if(channel!=null && channel.isActive()){


        }else{
            if(debugEnabled){
                LOGGER.debug("{} {} {} this node is not have channel",request.getVin(),request.getTime(),request.getCommand().getDesc());
            }
        }
    }

    /**
     * 查询/设置参数指令处理
     * @param request
     */
    private void doParamsRequest(CommandDownRequest request) {
        boolean debugEnabled = LOGGER.isDebugEnabled();
        Channel channel = getChannel(request.getVin());
        if(channel!=null && channel.isActive()){
            EvGBProtocol protocol = new EvGBProtocol();
            protocol.setCommandType(CommandType.valuesOf(request.getCommand().name()));
            protocol.setVin(request.getVin());
            ByteBuf bodyBuf = null;
            switch (protocol.getCommandType()){
                case QUERY_COMMAND:
                    QueryParamsRequest queryParamsRequest = new QueryParamsRequest();
                    queryParamsRequest.setBeanTime(new BeanTime(request.getTime()));
                    queryParamsRequest.setCount(request.getCount());
                    queryParamsRequest.setIds(request.getIds());
                    bodyBuf=queryParamsRequest.encode();
                    break;
                case SET_COMMAND:
                    SetParamsRequest setParamsRequest = new SetParamsRequest();
                    setParamsRequest.setBeanTime(new BeanTime(request.getTime()));
                    setParamsRequest.setCount(request.getCount());
                    setParamsRequest.setParams(request.getParams());
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
            if(debugEnabled){
                LOGGER.debug("{} {} {} this node is not have {} channel",request.getVin(),request.getTime(),request.getCommand().getDesc());
            }
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
