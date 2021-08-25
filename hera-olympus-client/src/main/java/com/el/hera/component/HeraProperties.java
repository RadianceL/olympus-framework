package com.el.hera.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 注册中心配置类
 * since 2020/1/11
 *
 * @author eddie
 */
@Data
@Primary
@Component
@ConfigurationProperties(prefix = "spring.hera")
public class HeraProperties {

    private String ip;

    private int port;

    private boolean enable;

    @NestedConfigurationProperty
    private Client server = new Client();

    @NestedConfigurationProperty
    private Instance instance = new Instance();

    @Data
    public static class Client{

        /**
         * 注册中心地址
         */
        private List<String> serverAddress;

        /**
         * 负载均衡策略
         */
        private String loadBalancingStrategy;
    }

    @Data
    public static class Instance {

        /**
         * 每多少秒续租一次
         */
        private int leaseRenewalIntervalInSeconds;

    }

}
