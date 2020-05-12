package com.dyy.tsp.evgb.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.asyn.TaskPool;
import com.dyy.tsp.evgb.gateway.protocol.entity.VehicleCache;
import com.dyy.tsp.redis.asynchronous.AsynRedisCallable;
import com.dyy.tsp.redis.asynchronous.RedisOperation;
import com.dyy.tsp.redis.enumtype.LibraryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("all")
@Service
public class AsynRedisHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsynRedisHandler.class);

    /**
     * 异步操作Redis设置缓存
     * @param redisKey
     * @param vehicleCache
     */
    public void setVehicleCache(String redisKey,VehicleCache vehicleCache){
        AsynRedisCallable asynRedisCallable = new AsynRedisCallable(LibraryType.SING_AND_TOKEN, RedisOperation.SET, redisKey, JSONObject.toJSONString(vehicleCache));
        FutureTask<String> callableTask = new FutureTask<>(asynRedisCallable);
        TaskPool.getInstance().execute(callableTask);
        try {
            callableTask.get(1, TimeUnit.SECONDS);
        } catch (
                TimeoutException e) {
            LOGGER.error("{} 异步设置缓存超时",redisKey);
        } catch (Exception e){
            LOGGER.error(redisKey+"异步设置缓存错误:",e);
        }
    }

    /**
     * 异步操作Redis获取缓存
     * @param key
     * @return
     */
    public String getVehicleCache(String key) {
        AsynRedisCallable asynRedisCallable = new AsynRedisCallable(LibraryType.SING_AND_TOKEN, RedisOperation.GET, key);
        FutureTask<String> callableTask = new FutureTask<>(asynRedisCallable);
        TaskPool.getInstance().execute(callableTask);
        String cacheData = null;
        try {
            cacheData = callableTask.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            LOGGER.error("{} 异步获取缓存超时",key);
        } catch (Exception e){
            LOGGER.error(key+"异步获取缓存错误:",e);
        }
        return cacheData;
    }
}
