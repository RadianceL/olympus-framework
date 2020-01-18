package com.el.protocol.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;

/**
 * 服务基本信息
 * 2019/11/5
 *
 * @author eddielee
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseServiceAddr {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 注册中心地址
     */
    private String ipAddr;

    /**
     * 注册中心名称
     */
    private int port;
}
