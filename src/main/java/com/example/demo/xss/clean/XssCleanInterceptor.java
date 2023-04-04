package com.example.demo.xss.clean;


import com.example.demo.xss.XssProperties;
import com.example.demo.xss.utils.XssHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * xss 处理拦截器
 */
public class XssCleanInterceptor implements AsyncHandlerInterceptor {

    public XssCleanInterceptor(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    private final XssProperties xssProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 非控制器请求直接跳出
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 2. 没有开启
        if (!xssProperties.isEnabled()) {
            return true;
        }
        // 3. 处理 XssIgnore 注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        XssIgnore xssCleanIgnore = handlerMethod.getMethodAnnotation(XssIgnore.class);
        if (xssCleanIgnore == null) {
            XssHolder.setEnable();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        XssHolder.remove();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        XssHolder.remove();
    }

}
