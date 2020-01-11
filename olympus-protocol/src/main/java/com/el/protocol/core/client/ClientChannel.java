package com.el.protocol.core.client;

import com.el.protocol.core.coder.SocketDecoder;
import com.el.protocol.core.coder.SocketEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 2019/11/3
 *
 * @author eddielee
 */
public class ClientChannel extends ChannelInitializer<SocketChannel> {

    private static final ChannelHandler INSTANCE = new ClientHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 编码器
        pipeline.addLast(new SocketEncoder());
        // 解码器
        pipeline.addLast(new SocketDecoder());
        // 心跳 触发channel处理器中 - userEventTriggered方法，处理idleStateEvent即可
        pipeline.addLast(new IdleStateHandler(5, 5, 0, TimeUnit.SECONDS));
        // channel处理器
        pipeline.addLast(INSTANCE);
    }

}
