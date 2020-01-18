package com.el.register;

import com.el.register.config.OlympusProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 约定配置类
 * since 2020/1/11
 *
 * @author eddie
 */
@Configuration
@EnableConfigurationProperties(OlympusProperties.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OlympusAutoConfiguration {

    private final OlympusProperties olympusProperties;

}
