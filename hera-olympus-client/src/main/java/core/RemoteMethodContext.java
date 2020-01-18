package core;

import com.el.protocol.entity.RemoteInterfaceDefinition;
import org.springframework.stereotype.Component;
import utils.ReflexUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 远程方法上下文
 * since 2019/12/22
 *
 * @author eddie
 */
@Component
public class RemoteMethodContext {

    private static Map<String, RemoteInterfaceDefinition> remoteMethodContext = new ConcurrentHashMap<>(64);

    public void registerMethod(Object interfaceObj){
        Class<?> interfaceClass = interfaceObj.getClass();
        RemoteInterfaceDefinition remoteInterfaceDefinition = ReflexUtils.declaredMethods(interfaceClass);
        remoteInterfaceDefinition.setInterfaceName(interfaceClass);
        remoteInterfaceDefinition.setInterfaceObject(interfaceObj);

        remoteMethodContext.put(interfaceClass.getName(), remoteInterfaceDefinition);
    }

    public RemoteInterfaceDefinition getRemoteInterfaceDefinition(String targetInterfaceCalssName){
        return remoteMethodContext.get(targetInterfaceCalssName);
    }
}
