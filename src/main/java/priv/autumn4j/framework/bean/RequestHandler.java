package priv.autumn4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装请求处理者
 */
public class RequestHandler {

    private Class<?> controllerClazz;

    private Method actionMethod;

    public RequestHandler(Class<?> controllerClazz, Method actionMethod) {
        this.controllerClazz = controllerClazz;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClazz() {
        return controllerClazz;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
