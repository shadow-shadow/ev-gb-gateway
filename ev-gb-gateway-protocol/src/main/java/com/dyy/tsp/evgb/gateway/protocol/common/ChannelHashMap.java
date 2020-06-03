package com.dyy.tsp.evgb.gateway.protocol.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义继承ConcurrentHashMap缓存Map类
 * 方便内存分析跟踪Map
 * @param <K>
 * @param <V>
 */
public class ChannelHashMap<K,V> extends ConcurrentHashMap {

    public ChannelHashMap() {
        super();
    }

    public ChannelHashMap(int initialCapacity) {
        super(initialCapacity);
    }
}
