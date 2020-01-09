package com.el.protocol.entity.enums;

/**
 * 访问目的
 * since 2019/12/22
 *
 * @author eddie
 */
public enum RequestPurpose {

    /** 注册 */
    REGISTER,

    /** 下线 */
    OFFLINE,

    /** 请求 */
    REQUEST,

    /** 请求返回 */
    REQUEST_BACK,

    /** 返回值 */
    RETURN,

    /** 刷新 */
    REFRESH,

    /** 心跳 */
    HEARTBEAT;
}
