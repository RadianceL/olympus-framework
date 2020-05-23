package com.el.register.core.cache;

import java.nio.channels.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务通道缓存
 * since 2020/2/3
 *
 * @author eddie
 */
public class ServerChannelCache {

    private static final Map<String, Channel> SERVER_CHANNEL_CACHE = new ConcurrentHashMap<>();



}
