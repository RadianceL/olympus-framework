package com.el.protocol.core;

import com.alibaba.fastjson.JSON;
import com.el.common.support.exception.ExtendRuntimeException;
import com.el.common.support.exception.data.ErrorMessage;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求方法处理器
 * since 2019/12/28
 *
 * @author eddie
 */
@Slf4j
public class RequestMethodHandler {

    private static RequestMethodHandler registerInstance = new RequestMethodHandler(ClientType.REGISTER);

    private RequestMethodHandler (ClientType clientType){
        this.clientType = clientType;
    }

    public static RequestMethodHandler getInstance(ClientType clientType) {
        if (ClientType.REGISTER.equals(clientType)) {
            return RequestMethodServerHandlerHolder.REGISTER_INSTANCE;
        }
        return RequestMethodClientHandlerHolder.CLIENT_INSTANCE;
    }

    private static class RequestMethodServerHandlerHolder {
        private static final RequestMethodHandler REGISTER_INSTANCE = new RequestMethodHandler(ClientType.REGISTER);
    }

    private static class RequestMethodClientHandlerHolder {
        private static final RequestMethodHandler CLIENT_INSTANCE = new RequestMethodHandler(ClientType.CLIENT);
    }

    private ClientType clientType;

    private static Map<RequestPurpose, MethodHandler> methodHandlerMap = new ConcurrentHashMap<>(16);

    public void addHandler(RequestPurpose requestPurpose, MethodHandler methodHandler){
        methodHandlerMap.put(requestPurpose, methodHandler);
    }

    public void process(ChannelHandlerContext ctx, Object msg) {
        try {
            if (Objects.nonNull(msg) && msg instanceof InterfaceTransformDefinition) {
                InterfaceTransformDefinition data = (InterfaceTransformDefinition) msg;
                log.info(JSON.toJSONString(data));
                RequestPurpose purpose = data.getPurpose();
                MethodHandler methodHandler = methodHandlerMap.get(purpose);
                if (Objects.isNull(methodHandler)) {
                    throw new ExtendRuntimeException(ErrorMessage.of("P-000-000-0001"));
                }
                log.info("接收到消息 {}", data);
                InterfaceTransformDefinition interfaceTransformDefinition = methodHandler.processMethod(data);

                if (!interfaceTransformDefinition.isSuccess()) {
                    throw new ExtendRuntimeException(ErrorMessage.of("P-000-000-0001"));
                }

                if (ClientType.REGISTER.equals(this.clientType)) {
                    ctx.writeAndFlush(interfaceTransformDefinition);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
