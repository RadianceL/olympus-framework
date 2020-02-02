package com.el.hera.cache.strategy;

import com.el.protocol.entity.ServiceDefinition;

import java.util.List;
import java.util.Objects;

/**
 * 默认策略
 * since 2020/2/3
 *
 * @author eddie
 */
public class DefaultAdviceServer implements AdviceServer {

    @Override
    public ServiceDefinition getAdviceService(List<ServiceDefinition> serviceDefinitions) {
        if (Objects.nonNull(serviceDefinitions)){
            return serviceDefinitions.get(0);
        }

        return null;
    }
}
