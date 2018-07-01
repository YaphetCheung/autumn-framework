package priv.autumn4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutumnRequestParameter {
    /**
     * 请求参数名称
     *
     * @return
     */
    String value();

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue();

    /**
     * 是否必须
     *
     * @return
     */
    boolean required() default true;

}
