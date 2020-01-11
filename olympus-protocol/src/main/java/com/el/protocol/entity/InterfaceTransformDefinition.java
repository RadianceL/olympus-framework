package com.el.protocol.entity;

import com.el.protocol.entity.enums.RequestPurpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口远程调用协议体
 * since 2019/12/14
 *
 * @author eddie
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceTransformDefinition {

    /**
     * 类名
     */
    private String className;

    /**
     * 访问目的
     */
    private RequestPurpose purpose;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 传递对象信息
     */
    private Object classInfo;

    /**
     * 传递参数Map
     */
    private Object[] parameterMap;

    /**
     * 扩展字段 key=value
     */
    private String extendFields;

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 描述信息
     */
    private String descMessage;

    public static InterfaceTransformDefinition ofError(RequestPurpose purpose, String errorMessage) {
        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setPurpose(purpose);
        interfaceTransformDefinition.setSuccess(false);
        interfaceTransformDefinition.setDescMessage(errorMessage);
        return interfaceTransformDefinition;
    }

    public static InterfaceTransformDefinition ofSuccess(RequestPurpose purpose, String descMessage) {
        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setPurpose(purpose);
        interfaceTransformDefinition.setSuccess(true);
        interfaceTransformDefinition.setDescMessage(descMessage);
        return interfaceTransformDefinition;
    }

}
