package com.el.hera.core.proxy;

import com.el.hera.cache.ServerCache;
import com.el.common.support.exception.ExtendRuntimeException;
import com.el.common.support.exception.data.ErrorMessage;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.protocol.server.ClientCenter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 本地接口动态扩展
 * since 2019/12/29
 *
 * @author eddie
 */
public class RemoteMethodProxy implements InvocationHandler {

    private ServerCache serverCache = null;

    private ClientCenter clientCenter = new ClientCenter();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceInterfaceName = proxy.getClass().getName();
        ServiceDefinition serviceDefinition = serverCache.getAdviceService(serviceInterfaceName);
        clientCenter.run(serviceDefinition.getRemoteNameServer(), serviceDefinition.getRemoteServerPort());

        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setPurpose(RequestPurpose.REQUEST);
        interfaceTransformDefinition.setMethodName(method.getName());
        interfaceTransformDefinition.setParameterMap(args);
        InterfaceTransformDefinition message = clientCenter.getMessage(interfaceTransformDefinition);
        return message.getClassInfo();
    }
}
