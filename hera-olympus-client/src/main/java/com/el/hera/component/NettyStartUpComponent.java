package com.el.hera.component;

import com.el.protocol.core.RequestMethodHandler;
import com.el.protocol.core.handler.MethodHandler;
import com.el.protocol.entity.InterfaceTransformDefinition;
import com.el.protocol.entity.enums.ClientType;
import com.el.protocol.entity.enums.RequestPurpose;
import com.el.protocol.server.ClientCenter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Netty启动类
 * since 2019/12/22
 *
 * @author eddie
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NettyStartUpComponent {

    /**
     * 客户端 调用远程方法处理器
     */
    private final MethodHandler remoteMethodHandle;

    /**
     * 客户端 服务列表刷新处理器
     */
    private final MethodHandler refreshMethodHandler;

    /**
     * 项目配置类
     */
    private final ApplicationCoreConfiguration applicationCoreConfiguration;

    /**
     * 注册控制器
     */
    private RequestMethodHandler requestMethodHandler = RequestMethodHandler.getInstance(ClientType.CLIENT);

    /**
     * 客户端
     */
    private static final ClientCenter CLIENT_CENTER = new ClientCenter();

    @PostConstruct
    public void startup(){
        init();
        CLIENT_CENTER.run(applicationCoreConfiguration.getIp(), applicationCoreConfiguration.getPort());
        this.refreshServerList();
    }

    /**
     * 刷新列表
     */
    @Scheduled(fixedRate = 10000)
    public void refreshServerList() {
        InterfaceTransformDefinition interfaceTransformDefinition = new InterfaceTransformDefinition();
        interfaceTransformDefinition.setPurpose(RequestPurpose.REFRESH);
        CLIENT_CENTER.sendMessage(interfaceTransformDefinition);
    }

    private void init(){
        requestMethodHandler.addHandler(RequestPurpose.REQUEST, remoteMethodHandle);
        requestMethodHandler.addHandler(RequestPurpose.REFRESH, refreshMethodHandler);
    }


}
