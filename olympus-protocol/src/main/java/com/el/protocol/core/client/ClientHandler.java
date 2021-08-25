package com.el.protocol.core.client;

import com.el.base.utils.support.exception.ExtendRuntimeException;
import com.el.protocol.core.RequestMethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Channel处理器
 * 2019/11/5
 *
 * @author eddielee
 */
@Slf4j
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 内存分配工具 （可复用）
     */
    private static final ByteBufAllocator byteBufAllocator = new PooledByteBufAllocator();

    private final RequestMethodHandler requestMethodHandler = RequestMethodHandler.getInstance(ClientType.CLIENT);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ExtendRuntimeException {
        if (Objects.nonNull(ctx) && ctx instanceof InterfaceTransformDefinition) {
            requestMethodHandler.process(ctx, (InterfaceTransformDefinition) msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws ExtendRuntimeException {
        InterfaceTransformDefinition reqInterfaceTransformDefinition = new InterfaceTransformDefinition();
        reqInterfaceTransformDefinition.setPurpose(RequestPurpose.OFFLINE);
        requestMethodHandler.disconnected(ctx, reqInterfaceTransformDefinition);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("已经[{}]秒未收到信息，判断服务已经下线", "3");
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("渠道发生异常", cause);
        super.exceptionCaught(ctx, cause);
    }
}
