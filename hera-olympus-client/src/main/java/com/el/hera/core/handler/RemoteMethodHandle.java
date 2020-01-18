package com.el.hera.core.handler;

import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.RemoteInterfaceDefinition;
import com.el.protocol.entity.RemoteMethodDefinition;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.hera.core.RemoteMethodContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 远程服务处理类
 * since 2019/12/22
 *
 * @author eddie
 */
@Slf4j
@Component("remoteMethodHandle")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemoteMethodHandle implements MethodHandler {

    private final RemoteMethodContext remoteMethodContext;

    public void registerRemoteMethodInterface(Object cls){
        remoteMethodContext.registerMethod(cls);
    }

    @Override
    public InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition) {
        RemoteInterfaceDefinition remoteInterfaceDefinition = remoteMethodContext.
                getRemoteInterfaceDefinition(interfaceTransformDefinition.getClassName());
        RemoteMethodDefinition remoteMethodDefinition = remoteInterfaceDefinition.
                getRemoteMethodDefinition(interfaceTransformDefinition.getClassName(), interfaceTransformDefinition.getMethodName());
        if (Objects.isNull(remoteMethodDefinition)) {
            throw new RuntimeException("方法不存在异常");
        }

        Object interfaceObject = remoteInterfaceDefinition.getInterfaceObject();
        Class<?> interfaceName = remoteInterfaceDefinition.getInterfaceName();
        InterfaceTransformDefinition resultInterfaceTransformDefinition = new InterfaceTransformDefinition();
        try {
            Method method = interfaceName.getMethod(interfaceTransformDefinition.getMethodName(), remoteMethodDefinition.getParameterTypes());

            Object invoke = method.invoke(interfaceObject, interfaceTransformDefinition.getParameterMap());
            resultInterfaceTransformDefinition.setPurpose(RequestPurpose.RETURN);
            resultInterfaceTransformDefinition.setClassName(remoteMethodDefinition.getResultType().getTypeName());
            resultInterfaceTransformDefinition.setClassInfo(invoke);
            return resultInterfaceTransformDefinition;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("远程方法调用失败");
        }
    }
}
