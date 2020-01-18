package core.proxy;

import java.lang.reflect.Proxy;

/**
 * 动态代理管理器
 * since 2019/12/29
 *
 * @author eddie
 */
public class ProxyManager {

    /**
     * 获取远程服务
     * @param cls 被代理的接口类
     * @return    代理对象
     */
    public <T> T getRemoteService(Class<T> cls) {
        return (T) Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[]{cls},
                new RemoteMethodProxy());
    }

}
