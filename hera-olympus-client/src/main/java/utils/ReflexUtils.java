package utils;

import com.el.protocol.entity.RemoteInterfaceDefinition;
import com.el.protocol.entity.RemoteMethodDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具
 * since 2019/12/23
 *
 * @author eddie
 */
public class ReflexUtils {

    public static RemoteInterfaceDefinition declaredMethods(Class<?> cls){
        RemoteInterfaceDefinition remoteInterfaceDefinition = new RemoteInterfaceDefinition();
        Method[] declaredMethods = cls.getDeclaredMethods();

        for (Method method : declaredMethods) {
            RemoteMethodDefinition remoteMethodDefinition = new RemoteMethodDefinition();
            Type genericReturnType = method.getGenericReturnType();

            if (genericReturnType instanceof ParameterizedType) {
                remoteMethodDefinition.setResultType(Object.class);
            }else {
                remoteMethodDefinition.setResultType(genericReturnType);
            }

            remoteMethodDefinition.setMethodName(method.getName());
            remoteInterfaceDefinition.putRemoteMethodDefinition(remoteMethodDefinition);
        }

        return remoteInterfaceDefinition;
    }
}
