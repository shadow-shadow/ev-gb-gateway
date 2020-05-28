package com.dyy.tsp.evgb.gateway.protocol.common;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公用内存
 * created by dyy
 */
@SuppressWarnings("all")
public class CommonCache {

    /**
     * 车架号作为key Channel作为Value
     */
    public static Map<String, Channel> vinChannelMap = new ConcurrentHashMap<>(2048);

    /**
     * Channel作为key 车架号作为value
     */
    public static Map<Channel,String> channelVinMap = new ConcurrentHashMap<>(2048);

    /**
     * Debug在线监控
     */
    public static Map<String,String> debugVinMap =new ConcurrentHashMap();

}
