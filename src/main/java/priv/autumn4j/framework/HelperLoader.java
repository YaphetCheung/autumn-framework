package priv.autumn4j.framework;

import priv.autumn4j.framework.helper.*;
import priv.autumn4j.framework.util.ClassUtil;

public class HelperLoader {
    public static void init() {
        //依次初始化配置助手,类操作助手,bean容器助手,依赖注入助手,控制器助手
        Class<?>[] classes = {
                ConfigHelper.class, ClassHelper.class, BeanHelper.class, DIHelper.class, ControllerHelper.class
        };
        for (Class<?> clazz : classes) {
            ClassUtil.loadClass(clazz.getName(), true);
        }

    }

}
