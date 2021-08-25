package com.el.register.core;

import com.el.protocol.core.client.ClientChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 核心服务线程
 * since 2020/1/31
 *
 * @author eddie
 */
@Slf4j
public class ServerCoreThread extends Thread{

    /**
     * register端口
     */
    private final int port;

    /**
     * 每多少秒续租一次
     */
    private final int leaseRenewalIntervalInSeconds;

    public ServerCoreThread(int port, int leaseRenewalIntervalInSeconds){
        this.port = port;
        this.leaseRenewalIntervalInSeconds = leaseRenewalIntervalInSeconds;
    }

    @Override
    public void run() {
        log.info("启动 - 初始化服务");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ClientChannel(leaseRenewalIntervalInSeconds))
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        ChannelFuture future;
        try {
            log.info("启动 - 初始化服务 端口为：{}", port);
            future = serverBootstrap.bind(port).sync();
            // 使主线程 wait 阻塞，后续逻辑执行
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
