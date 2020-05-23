package com.el.protocol.core.client;

import com.el.common.support.exception.ExtendRuntimeException;
import com.el.common.support.exception.data.ErrorMessage;
import com.el.protocol.core.coder.SocketDecoder;
import com.el.protocol.core.coder.SocketEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 2019/11/3
 *
 * @author eddielee
 */
@AllArgsConstructor
public class ClientChannel extends ChannelInitializer<SocketChannel> {

    private static final ChannelHandler INSTANCE = new ClientHandler();

    private final int allIdleTime;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        if (allIdleTime <= 0) {
            throw new ExtendRuntimeException(ErrorMessage.of("0"));
        }

        // 编码器
        pipeline.addLast("encoder", new SocketEncoder());

        // 解码器
        pipeline.addLast("decoder",new SocketDecoder());

        // 心跳 触发channel处理器中 - userEventTriggered方法，处理idleStateEvent即可
        pipeline.addLast("server-idle-handler", new IdleStateHandler(0, 0, allIdleTime, MILLISECONDS));

        // channel处理器
        pipeline.addLast("shared-handler", INSTANCE);
    }

}
