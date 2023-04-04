package com.example.demo.xss.clean;

import com.example.demo.xss.XssProperties;
import com.example.demo.xss.utils.XssHolder;
import com.example.demo.xss.utils.XssUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class JacksonXssClean extends XssCleanJsonDeserializer {


    private static final Logger log = LoggerFactory.getLogger(JacksonXssClean.class);

    public JacksonXssClean(@Qualifier("longmao.xss-com.example.demo.xss.XssProperties") XssProperties properties) {
        this.properties = properties;
        this.xssClean = new DefaultXssClean(properties);
    }

    private final XssProperties properties;
    private final XssClean xssClean;

    @Override
    public String clean(String text) {
        String value = XssUtils.trim(text, properties.isTrimText());
        if(XssHolder.isEnabled()) {
            String result = xssClean.clean(value);
            if(!result.equals(value)) {
                log.warn("[longmao-xss] Json property value:{} cleaned up by longmao-xss, current value is:{}.", text, value);
            }
            return result;
        }else{
            return value;
        }
    }

}
