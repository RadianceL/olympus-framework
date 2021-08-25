package com.el.protocol.core;

import com.alibaba.fastjson.JSON;
import com.el.base.utils.support.exception.ExtendRuntimeException;
import com.el.base.utils.support.exception.data.ErrorMessage;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import io.netty.channel.Channel;
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

    private final ClientType clientType;

    private static final Map<RequestPurpose, MethodHandler> METHOD_HANDLER_MAP = new ConcurrentHashMap<>(16);

    public void addHandler(RequestPurpose requestPurpose, MethodHandler methodHandler){
        METHOD_HANDLER_MAP.put(requestPurpose, methodHandler);
    }

    public void process(ChannelHandlerContext ctx, InterfaceTransformDefinition msg) throws ExtendRuntimeException {
        try {
            log.info(JSON.toJSONString(msg));
            RequestPurpose purpose = msg.getPurpose();
            MethodHandler methodHandler = METHOD_HANDLER_MAP.get(purpose);
            if (Objects.isNull(methodHandler)) {
                throw new ExtendRuntimeException(ErrorMessage.of("P-000-000-0001"));
            }
            log.info("接收到消息 {}", msg);
            InterfaceTransformDefinition interfaceTransformDefinition = methodHandler.processMethod(msg);

            if (!interfaceTransformDefinition.isSuccess()) {
                throw new ExtendRuntimeException(ErrorMessage.of("P-000-000-0001"));
            }

            if (ClientType.REGISTER.equals(this.clientType)) {
                ctx.writeAndFlush(interfaceTransformDefinition);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    public void disconnected(ChannelHandlerContext ctx, InterfaceTransformDefinition interfaceTransformDefinition) throws ExtendRuntimeException{
        Channel channel = ctx.channel();

        MethodHandler methodHandler = METHOD_HANDLER_MAP.get(RequestPurpose.OFFLINE);
        interfaceTransformDefinition.setClassInfo(channel);
        InterfaceTransformDefinition respInterfaceTransformDefinition = methodHandler.processMethod(interfaceTransformDefinition);

        if (!respInterfaceTransformDefinition.isSuccess()) {
            throw new ExtendRuntimeException(ErrorMessage.of("P-000-000-0001"));
        }
    }
}
