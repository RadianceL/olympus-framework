package cache;

import com.el.protocol.entity.ServiceDefinition;

import java.util.List;
import java.util.Map;

/**
 * 客户端服务缓存
 * since 2019/12/25
 *
 * @author eddie
 */
public final class ServerCache {

    private static class ServerCacheHolder{
        private static final ServerCache SERVER_CACHE = new ServerCache();
    }

    private ServerCache() {}

    private Map<String, List<ServiceDefinition>> serviceMap;

    public void setServiceMap(Map<String, List<ServiceDefinition>> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public Map<String, List<ServiceDefinition>> getServiceMap(){
        return serviceMap;
    }

    public static ServerCache getInstance() {
        return ServerCacheHolder.SERVER_CACHE;
    }

}
