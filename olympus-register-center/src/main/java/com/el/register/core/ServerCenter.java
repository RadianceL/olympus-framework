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
    private final RequestMethodHandler requestMethodHandler = RequestMethodHandler.getInstance(ClientType.REGISTER);

    private void init(){
        log.info("启动 - 初始化处理器");
        requestMethodHandler.addHandler(RequestPurpose.REGISTER, serviceRegisterMethodHandler);
        requestMethodHandler.addHandler(RequestPurpose.REFRESH, serviceRefreshMethodHandler);
    }

    @PostConstruct
    private void start() {
        init();
        int leaseRenewalIntervalInSeconds = olympusProperties.getInstance().getLeaseRenewalIntervalInSeconds();
        if (leaseRenewalIntervalInSeconds <= 0) {
            log.error("续约时间小于等于0秒");
            return;
        }
        int port = olympusProperties.getServer().getPort();
        ServerCoreThread serverCoreThread = new ServerCoreThread(port, leaseRenewalIntervalInSeconds);
        serverCoreThread.start();
    }
}
