package com.el.hera.core.handler;

import com.el.hera.cache.ServerCache;
import com.alibaba.fastjson.JSON;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.ServiceDefinition;
import com.el.protocol.entity.enums.RequestPurpose;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * since 2019/12/29
 *
 * @author eddie
 */
@Slf4j
@Component("refreshMethodHandler")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshMethodHandler implements MethodHandler {

    private ServerCache serverCache = null;

    @Override
    @SuppressWarnings("unchecked")
    public InterfaceTransformDefinition processMethod(InterfaceTransformDefinition interfaceTransformDefinition) {
        if (interfaceTransformDefinition.getClassInfo() instanceof Map) {
            Map<String, List<ServiceDefinition>> classInfo = (Map<String, List<ServiceDefinition>>) interfaceTransformDefinition.getClassInfo();
            serverCache.setServiceMap(classInfo);
            log.info(JSON.toJSONString(classInfo));
        }
        return InterfaceTransformDefinition.ofSuccess(RequestPurpose.REFRESH, "成功");
    }
}
