package com.el.protocol.entity.enums;

/**
 * 通讯方式
 * 2019/10/27
 *
 * @author eddielee
 */
public enum CommunicationMode {

    /**
     * HTTP请求
     */
    HTTP1_1,

    /**
     * HTTP2请求
     */
    HTTP2,

    /**
     * 基于Netty的自定义协议
     */
    E_RPC

}
