package com.el.protocol;

import com.alibaba.fastjson.JSON;
import com.el.protocol.core.RequestMethodHandler;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.protocol.server.ClientCenter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * since 2019/12/22
 *
 * @author eddie
 */
@Slf4j
public class Main {
    private static final ClientCenter ABSTRACT_CLIENT_CENTER = new ClientCenter();
    /**
     * 注册控制器
     */
    private static RequestMethodHandler requestMethodHandler = RequestMethodHandler.getInstance(ClientType.CLIENT);

    public static void main(String[] args) {
        ABSTRACT_CLIENT_CENTER.run("127.0.0.1", 9901);
        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setPurpose(RequestPurpose.REFRESH);

        requestMethodHandler.addHandler(RequestPurpose.REFRESH, new RefreshMethodHandler());

        ABSTRACT_CLIENT_CENTER.sendMessage(interfaceTransformDefinition);
    }

    static class RefreshMethodHandler implements MethodHandler {

        @Override
        public InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition) {


            if (interfaceTransformDefinition.getClassInfo() instanceof Map) {
                Map<String, List<ServiceDefinition>> classInfo = (Map<String, List<ServiceDefinition>>) interfaceTransformDefinition.getClassInfo();
                log.info(JSON.toJSONString(classInfo));
            }
            return InterfaceTransformDefinition.ofSuccess(RequestPurpose.REFRESH, "成功");
        }
    }
}
