package com.el.protocol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

/**
 * 远程方法定义
 * since 2019/12/22
 *
 * @author eddie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoteMethodDefinition  {

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数
     */
    private Class<?>[] parameterTypes;

    /**
     * 返回类型
     */
    private Type resultType;

}
