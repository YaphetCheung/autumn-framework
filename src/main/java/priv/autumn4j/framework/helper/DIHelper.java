package priv.autumn4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import priv.autumn4j.framework.annotation.AutumnAutowired;
import priv.autumn4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手
 */
public final class DIHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (beanMap != null && beanMap.size() > 0) {
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClazz = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] beanFields = beanClazz.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field field : beanFields) {
                        if (field.isAnnotationPresent(AutumnAutowired.class)) {
                            Class<?> beanFieldClazz = field.getClass();
                            Object beanFieldInstance = beanMap.get(beanFieldClazz);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setFeild(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
