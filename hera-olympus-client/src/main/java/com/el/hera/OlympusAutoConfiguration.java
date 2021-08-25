package com.el.hera;

import com.el.base.utils.support.exception.ExtendRuntimeException;
import com.el.base.utils.support.exception.data.ErrorMessage;
import com.el.hera.cache.strategy.AdviceServer;
import com.el.hera.component.HeraProperties;
import com.el.hera.entity.enums.LoadBalancingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 约定配置类
 * since 2020/1/11
 *
 * @author eddie
 */
@Configuration
@EnableConfigurationProperties(HeraProperties.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OlympusAutoConfiguration {

    private final HeraProperties olympusProperties;

    @Bean
    public AdviceServer initAdviceServer(){
        LoadBalancingStrategy strategy = LoadBalancingStrategy.getLoadBalancingStrategyType(olympusProperties.getServer().getLoadBalancingStrategy());
        if (Objects.isNull(strategy)){
            strategy = LoadBalancingStrategy.DEFAULT;
        }
        Class<?> loadBalancingStrategy = strategy.getLoadBalancingStrategy();
        try {
            Object loadBalancingStrategyInstance = loadBalancingStrategy.getDeclaredConstructor().newInstance();
            if (loadBalancingStrategyInstance instanceof AdviceServer) {
                return (AdviceServer) loadBalancingStrategyInstance;
            }
            throw new ExtendRuntimeException(ErrorMessage.of("P-000-001-001", "获取的实例不是AdviceServer 内部核心配置异常"));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ExtendRuntimeException(ErrorMessage.of("P-000-001-001", "初始化loadBalancingStrategyInstance对象失败"), e);
        }
    }

}
