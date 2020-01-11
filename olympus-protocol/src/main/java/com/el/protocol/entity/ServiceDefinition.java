package com.el.protocol.entity;

import com.el.protocol.entity.enums.CommunicationMode;
import com.el.protocol.entity.enums.RegisterIdentity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务定义
 * 2019/10/27
 *
 * @author eddielee
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDefinition {

    /**
     * 远程服务IP
     */
    private String remoteServerIp;

    /**
     * 远程服务IP
     */
    private Integer remoteServerPort;

    /**
     * 远程服务域名
     */
    private String remoteNameServer;

    /**
     * 远程服务名
     */
    private String remoteServerName;

    /**
     * 是否需要从盖亚配置中心寻找配置
     */
    private boolean isNeedConfiguration;

    /**
     * 心跳检测频率 ms
     */
    private int heartAttack;

    /**
     * 接口服务列表
     */
    private String[] interfaceServerNames;

    /**
     * 注册身份
     */
    private RegisterIdentity registerIdentity;

    /**
     * 注册模式
     */
    private CommunicationMode communicationMode;
}
