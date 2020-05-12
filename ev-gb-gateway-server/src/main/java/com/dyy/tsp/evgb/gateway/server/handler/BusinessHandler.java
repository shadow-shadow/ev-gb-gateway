package com.dyy.tsp.evgb.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.evgb.gateway.protocol.common.CommonCache;
import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleCache;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import com.dyy.tsp.evgb.gateway.protocol.handler.IHandler;
import com.dyy.tsp.evgb.gateway.protocol.util.HelperKeyUtil;
import com.dyy.tsp.evgb.gateway.server.enumtype.GatewayCoreType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 业务处理核心类
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class BusinessHandler extends AbstractBusinessHandler implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessHandler.class);

    private ApplicationContext applicationContext;

    @Autowired
    private DebugHandler debugHandler;
    @Autowired
    private AsynRedisHandler asynRedisHandler;

    @Override
    public void doBusiness(EvGBProtocol protocol, Channel channel) {
        debugHandler.debugger(protocol);
        GatewayCoreType gatewayCoreType = GatewayCoreType.valuesOf(protocol.getCommandType().getId());
        if(gatewayCoreType.getHandler()!=null){
            String key = HelperKeyUtil.getKey(protocol.getVin());
            VehicleCache vehicleCache = this.findVehicleCache(key);
            CommandType commandType = protocol.getCommandType();
            //平台登入/登出,VIN规则不一样,不做校验
            if(!(commandType == CommandType.PLATFORM_LOGIN || commandType == CommandType.PLATFORM_LOGOUT)){
                if(vehicleCache == null){
                    LOGGER.warn("{} is not platform vehicle",protocol.getVin());
                    return;
                }
            }
            protocol.setVehicleCache(vehicleCache);
            IHandler handler = (IHandler) applicationContext.getBean(gatewayCoreType.getHandler());
            if(handler != null){
                handler.doBusiness(protocol,channel);
            }
            if(protocol.getBody() != null){
                ByteBuf byteBuf = protocol.getBody().getByteBuf();
                if(byteBuf!=null){
                    byteBuf.release();
                }
            }
        }
    }

    /**
     * 二级缓存,减少对Redis的压力
     * @param key
     * @return
     */
    private VehicleCache findVehicleCache(String key) {
        VehicleCache vehicleCache = CommonCache.vehicleCacheMap.get(key);
        if(vehicleCache!=null){
            return vehicleCache;
        }
        String cacheData = asynRedisHandler.getVehicleCache(key);
        if(StringUtils.isBlank(cacheData)){
            return null;
        }
        vehicleCache = JSONObject.parseObject(cacheData,VehicleCache.class);
        CommonCache.vehicleCacheMap.put(key,vehicleCache);
        return vehicleCache;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
