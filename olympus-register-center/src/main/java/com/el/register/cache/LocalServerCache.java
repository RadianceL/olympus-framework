package com.el.register.cache;

import com.el.protocol.entity.ServiceDefinition;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端服务缓存
 * since 2019/12/25
 *
 * @author eddie
 */
@Component
public final class LocalServerCache {

    private static final Map<String, Date> SERVICE_REGISTER_TIME = new ConcurrentHashMap<>(64);

    private static final Map<String, List<ServiceDefinition>> SERVICE_MAP = new ConcurrentHashMap<>(64);

    public void registerService(ServiceDefinition serviceDefinition){
        List<ServiceDefinition> serviceDefinitions = SERVICE_MAP.get(serviceDefinition.getRemoteServerName());
        if (Objects.nonNull(serviceDefinitions)) {
            if (serviceDefinitions.contains(serviceDefinition)) {
                return;
            }
            SERVICE_REGISTER_TIME.put(serviceDefinition.getRemoteServerName(), new Date());
            serviceDefinitions.add(serviceDefinition);
            return;
        }
        SERVICE_REGISTER_TIME.put(serviceDefinition.getRemoteServerName(), new Date());
        SERVICE_MAP.put(serviceDefinition.getRemoteServerName(), Lists.newArrayList(serviceDefinition));
    }

    public Map<String, List<ServiceDefinition>> getServiceMap(){
        return SERVICE_MAP;
    }

    public void refreshServiceCache(){
        Set<String> serviceRegisterKeys = SERVICE_REGISTER_TIME.keySet();

        for (String serviceRegisterKey : serviceRegisterKeys) {
            Date date = SERVICE_REGISTER_TIME.get(serviceRegisterKey);
            //比较时间，如果超过一定时间（可配置），干掉
        }
    }
}
