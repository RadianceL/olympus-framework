package com.el.protocol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 远程调用接口定义
 * since 2019/12/23
 *
 * @author eddie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteInterfaceDefinition {

    /**
     * 接口类名
     */
    private Class<?> interfaceName;
    /**
     * 接口实例
     */
    private Object interfaceObject;

    /**
     * 方法名称 && 方法定义
     */
    private static Map<String, RemoteMethodDefinition> methodDefinitionMap = new ConcurrentHashMap<>(64);

    private static final String SPLIT_KEY = "|";

    public void putRemoteMethodDefinition(RemoteMethodDefinition remoteMethodDefinition) {
        if (Objects.isNull(interfaceName)) {
            throw new RuntimeException("接口类名不能为空");
        }
        String methodName = remoteMethodDefinition.getMethodName();
        String key = interfaceName.getName().concat(SPLIT_KEY).concat(methodName);
        methodDefinitionMap.put(key, remoteMethodDefinition);
    }

    public RemoteMethodDefinition getRemoteMethodDefinition(String targetInterface, String methodName){
        String key = targetInterface.concat(SPLIT_KEY).concat(methodName);
        return methodDefinitionMap.get(key);
    }
}
