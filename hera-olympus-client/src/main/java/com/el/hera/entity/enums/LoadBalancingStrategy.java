package com.el.hera.entity.enums;

import com.el.hera.cache.strategy.DefaultAdviceServer;
import com.el.hera.cache.strategy.HashAdviceServer;
import org.apache.commons.lang3.StringUtils;

/**
 * 负载均衡策略
 * since 2020/2/3
 *
 * @author eddie
 */
public enum LoadBalancingStrategy {

    /**
     * 默认
     */
    DEFAULT(DefaultAdviceServer.class),
    /**
     * 基于HASH
     */
    HASH(HashAdviceServer.class);

    /**
     * 策略实例
     */
    private final Class<?> loadBalancingStrategy;

    LoadBalancingStrategy(Class<?> loadBalancingStrategy){
        this.loadBalancingStrategy = loadBalancingStrategy;
    }

    /**
     * 获取策略实例
     * @return      策略实例
     */
    public Class<?> getLoadBalancingStrategy() {
        return loadBalancingStrategy;
    }

    public static LoadBalancingStrategy getLoadBalancingStrategyType(String strategy){
        if (StringUtils.isNotBlank(strategy)) {
            for (LoadBalancingStrategy loadBalancingStrategy : values()) {
                if (loadBalancingStrategy.name().equalsIgnoreCase(strategy)) {
                    return loadBalancingStrategy;
                }
            }
        }

        return null;
    }
}
