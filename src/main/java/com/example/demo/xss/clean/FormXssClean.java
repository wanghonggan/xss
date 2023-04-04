package com.example.demo.xss.clean;

import com.example.demo.xss.XssProperties;
import com.example.demo.xss.utils.XssHolder;
import com.example.demo.xss.utils.XssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;

/**
 * 表单 xss 处理
 */
@ControllerAdvice
public class FormXssClean {

    private final XssProperties properties;
    private final XssClean xssClean;

    public FormXssClean(@Qualifier("longmao.xss-com.example.demo.xss.XssProperties") XssProperties properties) {
        this.properties = properties;
        this.xssClean = new DefaultXssClean(properties);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 处理前端传来的表单字符串
        binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssClean, properties));
    }

    @Slf4j
    public static class StringPropertiesEditor extends PropertyEditorSupport {
        public StringPropertiesEditor(XssClean xssClean, XssProperties properties) {
            this.xssClean = xssClean;
            this.properties = properties;
        }

        public StringPropertiesEditor(Object source, XssClean xssClean, XssProperties properties) {
            super(source);
            this.xssClean = xssClean;
            this.properties = properties;
        }

        private final XssClean xssClean;
        private final XssProperties properties;

        @Override
        public String getAsText() {
            Object value = getValue();
            return value != null ? value.toString() : "";
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if(text == null) {
                setValue(null);
                return;
            }
            String encodeValue = XssUtils.trim(text, properties.isTrimText());
            if(XssHolder.isEnabled()) {
                 String value = xssClean.clean(encodeValue);
                 setValue(value);
                if(!value.equals(getValue())) { // 可以考虑去掉
//                    log.warn("Request parameter value:{} cleaned up by longmao-xss, current value is:{}.", text, value);
                }
            }else{
                setValue(encodeValue);
            }
        }

    }

}
