package com.el.hera.cache.strategy;

import com.el.protocol.entity.ServiceDefinition;

import java.util.List;

/**
 * since 2020/2/2
 *
 * @author eddie
 */
public interface AdviceServer {

    /**
     *
     * @param serviceDefinitions
     * @return
     */
    default ServiceDefinition getAdviceService(List<ServiceDefinition> serviceDefinitions){
        return null;
    }
}
