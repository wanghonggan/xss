package com.example.demo.databind;

import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestJson {

    /**
     * 参数名称，默认为""，会以参数名为key
     */
    String value() default "";

    /**
     * 参数是否是必填的，默认false，可以设置为true
     */
    boolean required() default false;

    /**
     * 当请求参数值为空或者不存在时用作回退的默认值
     */
    String defaultValue() default ValueConstants.DEFAULT_NONE;

    /**
     * 是否解析所有参数，默认为true，主要用作解析json对象的时候是否转为java对象
     */
    boolean parseAllFields() default true;
}
