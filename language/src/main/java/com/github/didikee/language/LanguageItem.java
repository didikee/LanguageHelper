package com.github.didikee.language;

import java.util.Locale;

/**
 * user author: didikee
 * create time: 12/21/18 5:58 PM
 * description: 描述语言的实体类
 */
public class LanguageItem {
    public String title;
    public Locale locale;

    public LanguageItem(String title, Locale locale) {
        this.title = title;
        this.locale = locale;
    }
}
