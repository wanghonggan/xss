package com.example.demo.xss.clean;

import com.example.demo.xss.XssProperties;
import com.example.demo.xss.utils.XssUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * 默认的 xss 清理器
 */

public class DefaultXssClean implements XssClean {

    public DefaultXssClean(XssProperties properties) {
        this.properties = properties;
    }

    private final XssProperties properties;

    @Override
    public String clean(String bodyHtml) {
        // 1. 为空直接返回
        if (StringUtils.isEmpty(bodyHtml)) {
            return bodyHtml;
        }
        XssProperties.Mode mode = properties.getMode();
        if (XssProperties.Mode.escape == mode) {
            // html 转义
            return HtmlUtils.htmlEscape(bodyHtml, "UTF-8");
        } else {
            // jsoup html 清理
            Document.OutputSettings outputSettings = new Document.OutputSettings()
                    // 2. 转义，没找到关闭的方法，目前这个规则最少
                    .escapeMode(Entities.EscapeMode.xhtml)
                    // 3. 保留换行
                    .prettyPrint(properties.isPrettyPrint());
            Document dirty = Jsoup.parseBodyFragment(bodyHtml, "");
            Cleaner cleaner = new Cleaner(XssUtils.WHITE_LIST);
            Document clean = cleaner.clean(dirty);
            clean.outputSettings(outputSettings);
            // 4. 清理后的 html
            String escapedHtml = clean.body().html();
            if (properties.isEnableEscape()) {
                return escapedHtml;
            }
            // 5. 反转义
            return Entities.unescape(escapedHtml);
        }
    }


}
