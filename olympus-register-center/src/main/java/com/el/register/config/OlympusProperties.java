package com.el.register.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * 注册中心配置类
 * since 2020/1/11
 *
 * @author eddie
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.register")
public class OlympusProperties {

    @NestedConfigurationProperty
    private Server server = new Server();

    @NestedConfigurationProperty
    private Instance instance = new Instance();

    @Data
    public static class Server{
        /**
         * register端口
         */
        private int port;
    }

    @Data
    public static class Instance {
        /**
         * 每多少秒续租一次
         */
        private int leaseRenewalIntervalInSeconds;

        /**
         * 每多少秒失效
         */
        private int leaseExpirationDurationInSeconds;
    }

}
