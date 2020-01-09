package com.el.protocol.core.handler;

import com.el.protocol.entity.InterfaceTransformDefinition;

/**
 * 方法处理器
 * since 2019/12/28
 *
 * @author eddie
 */
public interface MethodHandler {

    /**
     *
     */
    InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition);
}
