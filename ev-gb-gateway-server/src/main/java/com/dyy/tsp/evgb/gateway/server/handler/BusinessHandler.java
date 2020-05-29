package com.dyy.tsp.evgb.gateway.server.handler;

import com.dyy.tsp.evgb.gateway.protocol.dto.ProblemVehicle;
import com.dyy.tsp.evgb.gateway.protocol.entity.EvGBProtocol;
import com.dyy.tsp.evgb.gateway.protocol.dto.VehicleCache;
import com.dyy.tsp.evgb.gateway.protocol.enumtype.CommandType;
import com.dyy.tsp.evgb.gateway.protocol.handler.AbstractBusinessHandler;
import com.dyy.tsp.evgb.gateway.protocol.handler.IHandler;
import com.dyy.tsp.evgb.gateway.protocol.util.HelperKeyUtil;
import com.dyy.tsp.evgb.gateway.server.cache.CaffeineCache;
import com.dyy.tsp.evgb.gateway.server.enumtype.GatewayCoreType;
import com.dyy.tsp.redis.handler.RedisHandler;
import io.netty.channel.Channel;
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
    private ForwardHandler forwardHandler;
    @Autowired
    private DebugHandler debugHandler;
    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private CaffeineCache caffeineCache;

    @Override
    public void doBusiness(EvGBProtocol protocol, Channel channel) {
        GatewayCoreType gatewayCoreType = GatewayCoreType.valuesOf(protocol.getCommandType().getId());
        if(gatewayCoreType.getHandler()!=null && gatewayCoreType!=null){
            debugHandler.debugger(protocol);
            String key = HelperKeyUtil.getKey(protocol.getVin());
            VehicleCache vehicleCache = caffeineCache.get(key);
            CommandType commandType = protocol.getCommandType();
            //平台登入/登出,VIN规则不一样,不做校验
            if(!(commandType == CommandType.PLATFORM_LOGIN || commandType == CommandType.PLATFORM_LOGOUT)){
                if(vehicleCache == null){
                    String vin = protocol.getVin();
                    LOGGER.warn("{} is not platform vehicle",vin);
                    forwardHandler.sendToVehicle(new ProblemVehicle(vin,protocol.getGatewayReceiveTime(),protocol.getCommandType()));
                    channel.close();
                    return;
                }
            }
            protocol.setVehicleCache(vehicleCache);
            IHandler handler = (IHandler) applicationContext.getBean(gatewayCoreType.getHandler());
            if(handler != null){
                handler.doBusiness(protocol,channel);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
