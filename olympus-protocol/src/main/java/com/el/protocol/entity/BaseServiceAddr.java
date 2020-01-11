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

    @NonNull
    private String serviceName;

    @NonNull
    private String ipAddr;

    @NonNull
    private int port;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseServiceAddr that = (BaseServiceAddr) o;
        return this.port == that.port && this.serviceName.equals(that.serviceName) && this.ipAddr.equals(that.ipAddr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, ipAddr, port);
    }
}
