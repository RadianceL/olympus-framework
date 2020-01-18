package com.el.register.core;

import com.el.protocol.core.RequestMethodHandler;
import com.el.protocol.core.client.ClientChannel;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.register.config.OlympusProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 服务端中心
 * 2019/11/3
 *
 * @author eddielee
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServerCenter {

    /**
     * 系统配置
     */
    private final OlympusProperties olympusProperties;

    /**
     * 注册中心 服务注册方法处理器
     */
    private final MethodHandler serviceRegisterMethodHandler;

    /**
     * 注册中心 服务列表刷新处理器
     */
    private final MethodHandler serviceRefreshMethodHandler;

    /**
     * 注册控制器
     */
    private RequestMethodHandler requestMethodHandler = RequestMethodHandler.getInstance(ClientType.REGISTER);


    private void init(){
        log.info("启动 - 初始化处理器");
        requestMethodHandler.addHandler(RequestPurpose.REGISTER, serviceRegisterMethodHandler);
        requestMethodHandler.addHandler(RequestPurpose.REFRESH, serviceRefreshMethodHandler);
    }

    @PostConstruct
    private void start() {
        init();
        log.info("启动 - 初始化服务");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ClientChannel(olympusProperties.getInstance().getLeaseRenewalIntervalInSeconds()))
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        ChannelFuture future;
        try {
            log.info("启动 - 初始化服务完成 端口号为：{}", olympusProperties.getServer().getPort());
            future = serverBootstrap.bind(olympusProperties.getServer().getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
