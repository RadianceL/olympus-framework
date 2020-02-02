package com.el.hera.cache;

import com.el.hera.cache.strategy.AdviceServer;
import com.el.protocol.entity.ServiceDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 客户端服务缓存
 * since 2019/12/25
 *
 * @author eddie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class ServerCache {

    private Map<String, List<ServiceDefinition>> serviceMap;

    private final AdviceServer adviceServer;

    public void setServiceMap(Map<String, List<ServiceDefinition>> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public ServiceDefinition getAdviceService(String serviceName){
        List<ServiceDefinition> serviceDefinitions = this.serviceMap.get(serviceName);
        ServiceDefinition adviceService = adviceServer.getAdviceService(serviceDefinitions);
        if (Objects.isNull(adviceService)) {
            return serviceDefinitions.get(0);
        }
        return adviceService;
    }

}
