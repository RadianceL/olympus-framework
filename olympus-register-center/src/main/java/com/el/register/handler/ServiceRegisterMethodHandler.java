package com.el.register.handler;

import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.register.cache.LocalServerCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册中心 服务注册方法处理器
 * since 2019/12/28
 *
 * @author eddie
 */
@Component("serviceRegisterMethodHandler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceRegisterMethodHandler implements MethodHandler {

    private final LocalServerCache localServerCache;

    @Override
    public InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition) {
        Object service = interfaceTransformDefinition.getClassInfo();

        if (service instanceof ServiceDefinition) {
            ServiceDefinition serviceDefinition = (ServiceDefinition) service;
            localServerCache.registerService(serviceDefinition);
            return InterfaceTransformDefinition.ofSuccess(RequestPurpose.REGISTER, "成功");
        }
        return InterfaceTransformDefinition.ofSuccess(RequestPurpose.REGISTER, "类型不符合，期望`ServiceDefinition`");
    }
}
