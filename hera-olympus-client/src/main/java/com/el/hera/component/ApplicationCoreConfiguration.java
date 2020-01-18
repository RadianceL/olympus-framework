package com.el.hera.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目核心配置
 * since 2019/12/22
 *
 * @author eddie
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.hera")
public class ApplicationCoreConfiguration {

    private String ip;

    private int port;

    private boolean enable;

}
