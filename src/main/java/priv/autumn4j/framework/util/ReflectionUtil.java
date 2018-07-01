package priv.autumn4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     *
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz) {
        Object instance;
        try {
            instance = clazz.newInstance();
            return instance;
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException("新建实例失败");
        }
    }

    /**
     * 调用方法
     *
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
            return result;
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException("方法调用失败");
        }
    }

    /**
     * 设置字段的值
     *
     * @param obj
     * @param field
     * @param value
     */
    public static void setFeild(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException("设置字段失败");
        }
    }
}

