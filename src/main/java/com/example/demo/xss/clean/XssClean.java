package com.example.demo.xss.clean;

/**
 * xss 清理器
 */
public interface XssClean {

    /**
     * 清理 html
     * @param html html
     * @return 清理后的数据
     */
    String clean(String html);

}
