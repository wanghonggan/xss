package com.example.demo.xss;

import com.example.demo.xss.clean.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(
        prefix = XssProperties.PREFIX,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnClass({ WebMvcConfigurer.class, Jsoup.class})
@Configuration
public class XssConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(XssConfiguration.class);
    private final XssProperties xssProperties;

    public XssConfiguration(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
        log.info("==================== LongMaoSpringBootStarter ==================== longmao xss");
    }

    @Bean
    public XssClean xssClean(@Qualifier("longmao.xss-com.example.demo.xss.XssProperties") XssProperties properties) {
        return new DefaultXssClean(properties);
    }

    @Bean
    public FormXssClean formListXssClean(@Qualifier("longmao.xss-com.example.demo.xss.XssProperties") XssProperties properties) {
        return new FormXssClean(properties);
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer bodyXssClean(@Qualifier("longmao.xss-com.example.demo.xss.XssProperties") XssProperties properties) {
        return builder -> builder.deserializerByType(String.class, new JacksonXssClean(properties));
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = xssProperties.getPathPatterns();
        if (patterns.isEmpty()) {
            patterns.add("/**");
        }
        XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
        registry.addInterceptor(interceptor)
                .addPathPatterns(patterns)
                .excludePathPatterns(xssProperties.getPathExcludePatterns())
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}
