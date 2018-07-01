package priv.autumn4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import priv.autumn4j.framework.annotation.AutumnAction;
import priv.autumn4j.framework.bean.RequestHandler;
import priv.autumn4j.framework.bean.RequestInfo;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ControllerHelper {

    private static final Map<RequestInfo, RequestHandler> ACTION_MAP = new HashMap<RequestInfo, RequestHandler>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        for (Class<?> controllerClazz : controllerClassSet) {
            Method[] methods = controllerClazz.getMethods();
            if (ArrayUtils.isNotEmpty(methods)) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AutumnAction.class)) {
                        AutumnAction action = method.getAnnotation(AutumnAction.class);
                        String mapping = action.value();
                        if (mapping.matches("/\\w*:\\w+")) {
                            String[] pathAndMethod = mapping.split(":");
                            if (ArrayUtils.isNotEmpty(pathAndMethod) && pathAndMethod.length == 2) {
                                String requestPath = pathAndMethod[0];
                                String requestMethod = pathAndMethod[1];
                                RequestInfo requestInfo = new RequestInfo(requestMethod, requestPath);
                                RequestHandler requestHandler = new RequestHandler(controllerClazz, method);
                                ACTION_MAP.put(requestInfo, requestHandler);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 获取requestHandler
     *
     * @param requestPath
     * @param requestMethod
     * @return
     */
    public static RequestHandler getRequestHandler(String requestPath, String requestMethod) {
        RequestInfo requestInfo = new RequestInfo(requestMethod, requestPath);
        return ACTION_MAP.get(requestInfo);
    }

}
