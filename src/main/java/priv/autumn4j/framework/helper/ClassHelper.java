package priv.autumn4j.framework.helper;

import priv.autumn4j.framework.annotation.AutumnController;
import priv.autumn4j.framework.annotation.AutumnService;
import priv.autumn4j.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * 类操作助手
 */
public final class ClassHelper {

    /**
     * 存放basePackage包下的所有类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取基础包下所有service类
     *
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> serviceClassSet = new HashSet<Class<?>>();

        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(AutumnService.class)) {
                serviceClassSet.add(clazz);
            }
        }

        return serviceClassSet;
    }

    /**
     * 获取基础包下所有Controller类
     *
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> controllerClassSet = new HashSet<Class<?>>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(AutumnController.class)) {
                controllerClassSet.add(clazz);
            }
        }
        return controllerClassSet;
    }

    /**
     * 获取基础包下所有Bean类(需要ioc容器管理的)
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

}
