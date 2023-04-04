package com.example.demo.xss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties(XssProperties.class)
@ConfigurationProperties(XssProperties.PREFIX)
@Configuration
public class XssProperties {

    public static final String PREFIX = "longmao.xss";

    /**
     * 开启xss
     */
    private boolean enabled = true;
    /**
     * 全局：对文件进行首尾 trim
     */
    private boolean trimText = true;
    /**
     * 模式：clear 清理（默认），escape 转义
     */
    private Mode mode = Mode.clear;
    /**
     * [clear 专用] prettyPrint，默认关闭： 保留换行
     */
    private boolean prettyPrint = false;
    /**
     * [clear 专用] 使用转义，默认关闭
     */
    private boolean enableEscape = false;
    /**
     * 拦截的路由，默认为空
     */
    private List<String> pathPatterns = new ArrayList<>();
    /**
     * 放行的路由，默认为空
     */
    private List<String> pathExcludePatterns = new ArrayList<>();

    public enum Mode {
        /**
         * 清理
         */
        clear,
        /**
         * 转义
         */
        escape;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTrimText() {
        return trimText;
    }

    public void setTrimText(boolean trimText) {
        this.trimText = trimText;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public boolean isEnableEscape() {
        return enableEscape;
    }

    public void setEnableEscape(boolean enableEscape) {
        this.enableEscape = enableEscape;
    }

    public List<String> getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(List<String> pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public List<String> getPathExcludePatterns() {
        return pathExcludePatterns;
    }

    public void setPathExcludePatterns(List<String> pathExcludePatterns) {
        this.pathExcludePatterns = pathExcludePatterns;
    }
}
