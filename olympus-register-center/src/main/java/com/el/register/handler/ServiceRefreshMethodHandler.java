package com.el.register.handler;

import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import com.el.protocol.entity.enums.CommunicationMode;
import com.el.protocol.entity.enums.RegisterIdentity;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.register.cache.LocalServerCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 注册中心 服务列表刷新处理器
 * since 2019/12/28
 *
 * @author eddie
 */
@Slf4j
@Component("serviceRefreshMethodHandler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceRefreshMethodHandler implements MethodHandler {

    private final LocalServerCache localServerCache;

    @Override
    public InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition) {
        ServiceDefinition serviceDefinition = new ServiceDefinition();
        serviceDefinition.setCommunicationMode(CommunicationMode.E_RPC);
        serviceDefinition.setInterfaceServerNames(new String[] {"123.456.789"});
        serviceDefinition.setRegisterIdentity(RegisterIdentity.SERVER_CUSTOM);
        serviceDefinition.setRemoteServerIp("127.0.0.1");
        serviceDefinition.setRemoteServerName(String.valueOf(Math.random()));
        localServerCache.registerService(serviceDefinition);
        Map<String, List<ServiceDefinition>> serviceMap = localServerCache.getServiceMap();
        InterfaceTransformDefinition callBackInterfaceTransformDefinition = InterfaceTransformDefinition.ofSuccess(RequestPurpose.REFRESH, "成功");
        callBackInterfaceTransformDefinition.setClassName(serviceMap.getClass().getName());
        callBackInterfaceTransformDefinition.setClassInfo(serviceMap);
        return callBackInterfaceTransformDefinition;
    }
}
