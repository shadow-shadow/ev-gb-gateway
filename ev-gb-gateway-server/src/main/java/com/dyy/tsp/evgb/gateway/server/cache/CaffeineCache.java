package com.dyy.tsp.evgb.gateway.server.cache;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.evgb.gateway.protocol.dto.VehicleCache;
import com.dyy.tsp.redis.enumtype.LibraryType;
import com.dyy.tsp.redis.handler.RedisHandler;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * 采用Caffeine做本地缓存,防止内存溢出
 * 软引用：如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
 * 弱引用：弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存
 * created by dyy
 */
@Component
@SuppressWarnings("all")
public class CaffeineCache {

    private Cache<String,VehicleCache> VEHICLE_CACHE = Caffeine.newBuilder()
            //获取一次后超时时间,即本地缓存30秒内无命中即自动清理
            .expireAfterAccess(30, TimeUnit.SECONDS)
            //缓存最大容量10万
            .maximumSize(100000)
            //设置软引用
//                .weakKeys()
            .weakValues()
            .build();

    @Autowired
    private RedisHandler redisHandler;

    /**
     * 先从本地缓存中获取,获取不到从Redis中获取并自动加载到本地缓存
     * @param key
     * @return
     */
    public VehicleCache get(String key){
        return VEHICLE_CACHE.get(key, k -> getRedisCache(k));
    }

    /**
     * Put缓存
     * @param key
     * @param vehicleCache
     */
    public void put(String key, VehicleCache vehicleCache) {
        VEHICLE_CACHE.put(key,vehicleCache);
    }

    /**
     * 清除缓存
     * @param key
     */
    public void remove(String key) {
        VEHICLE_CACHE.invalidate(key);
    }

    /**
     * 缓存长度
     * @return
     */
    public Long size() {
        return VEHICLE_CACHE.estimatedSize();
    }

    /**
     * 从Redis中获取缓存
     * @param key
     * @return
     */
    private VehicleCache getRedisCache(String key) {
        String cacheData = redisHandler.getAsyn(LibraryType.SING_AND_TOKEN, key);
        return StringUtils.isBlank(cacheData) ? null : JSONObject.parseObject(cacheData,VehicleCache.class);
    }

    public Cache<String, VehicleCache> getVehicleCacheMap() {
        return VEHICLE_CACHE;
    }

}
