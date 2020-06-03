package com.dyy.tsp.evgb.gateway.protocol.common;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公用内存
 * 当网关百万连接,远程控制指令下发处理根据VIN获取通道时,单个内存Map存在性能瓶颈,通过均匀分布算法分多个小Map存储,提高应用性能
 * created by dyy
 */
@SuppressWarnings("all")
public class CommonCache {

    /**
     * 内存Map初始化容量
     */
    private static Integer initSize = 2048;

    /**
     * 内存Map分片个数
     */
    private static Integer count = 10;

    /**
     * Debug在线监控
     */
    public static Map<String,String> debugVinMap = new ConcurrentHashMap<>();

    /**
     * 获取VIN与通道对应关系内存集合
     * @param vin
     * @return
     */
    public static Map<String,Channel> getVinChannelMap(String vin){
        return VinChannelMapType.valuesOf(getIndexNode(vin, count)).getMap();
    }

    /**
     * 根据Channel获取VIN集合
     * @param vin
     * @return
     */
    public static Map<Channel,String> getChannelVinMap(Channel channel){
        return ChannelVinMapType.valuesOf(getIndexNode(String.valueOf(channel.hashCode()), count)).getMap();
    }

    /**
     * 计算哈希值
     * @param key
     * @return
     */
    private static int fnvHash(String key) {
        final int p = 16777619;
        long hash = (int) 2166136261L;
        for (int i = 0, n = key.length(); i < n; i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return ((int) hash & 0x7FFFFFFF) ;
    }

    /**
     * 取模运算获取内存分片下标
     * @param key
     * @return
     */
    private static int getIndexNode(String key,Integer count){
        return (fnvHash(String.valueOf(Long.valueOf(getNumStr(key))))%count)+1;
    }

    /**
     * 取出VIN码中数字组合
     * @param key
     * @return
     */
    private static String getNumStr(String key){
        String result ="";
        if(key != null && !"".equals(key)){
            for(int i=0;i<key.length();i++){
                if(key.charAt(i)>=48 && key.charAt(i)<=57){
                    result+=key.charAt(i);
                }
            }
        }
        return result;
    }

    static enum VinChannelMapType{
        VIN_CHANNEL_MAP_1(1,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_2(2,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_3(3,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_4(4,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_5(5,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_6(6,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_7(7,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_8(8,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_9(9,new ChannelHashMap<>(initSize)),
        VIN_CHANNEL_MAP_10(10,new ChannelHashMap<>(initSize)),
        ;
        private Integer index;
        private Map<String, Channel> map;

        VinChannelMapType(Integer index, Map<String, Channel> map) {
            this.index = index;
            this.map = map;
        }

        public Integer getIndex() {
            return index;
        }

        public Map<String, Channel> getMap() {
            return map;
        }

        public static VinChannelMapType valuesOf(int index) {
            for (VinChannelMapType enums : VinChannelMapType.values()) {
                if (enums.getIndex().intValue()==index) {
                    return enums;
                }
            }
            return null;
        }
    }

    static enum ChannelVinMapType{
        CHANNEL_VIN_MAP_1(1,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_2(2,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_3(3,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_4(4,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_5(5,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_6(6,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_7(7,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_8(8,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_9(9,new ChannelHashMap<>(initSize)),
        CHANNEL_VIN_MAP_10(10,new ChannelHashMap<>(initSize)),
        ;
        private Integer index;
        private Map<Channel, String> map;

        ChannelVinMapType(Integer index, Map<Channel, String> map) {
            this.index = index;
            this.map = map;
        }

        public Integer getIndex() {
            return index;
        }

        public Map<Channel, String> getMap() {
            return map;
        }

        public static ChannelVinMapType valuesOf(int index) {
            for (ChannelVinMapType enums : ChannelVinMapType.values()) {
                if (enums.getIndex().intValue()==index) {
                    return enums;
                }
            }
            return null;
        }
    }
}
