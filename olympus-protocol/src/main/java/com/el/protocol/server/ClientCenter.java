package com.el.protocol.server;

import com.el.protocol.core.client.ClientChannel;
import com.el.protocol.entity.InterfaceTransformDefinition;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 客户端中心
 * 2019/11/3
 *
 * @author eddielee
 */
@Slf4j
public class ClientCenter {

    private static Channel channel;

    public boolean run(String ip, int port) {
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap clientBootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannel());
        try {
            ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress(ip, port)).sync();
            channel = channelFuture.channel();
        } catch (InterruptedException e) {
            log.error("初始化服务异常", e);
            group.shutdownGracefully();
        }
        return true;
    }

    public void sendMessage(InterfaceTransformDefinition msg) {
        channel.eventLoop().execute(() -> {
            try {
                if (channel.isOpen()) {
                    channel.writeAndFlush(msg);
                } else {
                    throw new RuntimeException("渠道状态关闭");
                }
            } catch (Exception e) {
                log.error("通讯失败", e);
            }
        });
    }

    public InterfaceTransformDefinition getMessage(InterfaceTransformDefinition msg) {
        AtomicReference<InterfaceTransformDefinition> result = new AtomicReference<>();
        channel.eventLoop().execute(() -> {
            try {
                if (channel.isOpen()) {
                    ChannelFuture channelFuture = channel.writeAndFlush(msg).sync();
                    channelFuture.addListener(future -> result.set((InterfaceTransformDefinition) future.getNow()));
                } else {
                    throw new RuntimeException("渠道状态关闭");
                }
            } catch (Exception e) {
                log.error("通讯失败", e);
            }
        });
        return result.get();
    }
}
