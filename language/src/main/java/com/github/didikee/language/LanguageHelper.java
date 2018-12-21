package com.github.didikee.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by didikee on 2017/9/9.
 */

public final class LanguageHelper {
    public static final String SP_LANGUAGE_CODE = "sp__language_code";
    public static final String SP_COUNTRY_CODE = "sp__country_code";
    // 世界上10大语种(实际上9中,中文占了)
//    public static final String JA = "ja";// 日本
//    public static final String ZH = "zh-rCN";// 简体中文
//    public static final String ZH_TW = "zh-rTW";// 繁体中文
//    public static final String EN = "en";// 英语
//    public static final String ES = "es";// 西班牙语
//    public static final String AR = "ar";// 阿拉伯语
//    public static final String HI = "hi";// 印地语
//    public static final String PT = "pt";// 葡萄牙语
//    public static final String RU = "ru";// 俄语
//    public static final String DE = "de";// 德语
////    public static final String DE = "de";// 孟加拉

    public static String getSpLanguageCode(@NonNull Context context) {
        return (String) SPUtil.get(context, SP_LANGUAGE_CODE, "");
    }

    public static String getSpCountryCode(@NonNull Context context) {
        return (String) SPUtil.get(context, SP_COUNTRY_CODE, "");
    }

    public static void setSPLanguage(@NonNull Context context, String languageCode, String countryCode) {
        SPUtil.put(context, SP_LANGUAGE_CODE, TextUtils.isEmpty(languageCode) ? "" : languageCode);
        SPUtil.put(context, SP_COUNTRY_CODE, TextUtils.isEmpty(countryCode) ? "" : countryCode);
    }

    /**
     * 设置语言
     * @param context
     * @param languageCode
     * @param countryCode
     * @return
     */
    public static void setLanguage(@NonNull Context context, String languageCode, String countryCode) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale locale = createLocale(languageCode, countryCode);
        if (Build.VERSION.SDK_INT >= 17) {
            Locale.setDefault(locale);
            config.setLocale(locale);
            context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            //设置本地语言
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    public static void changeAppLanguage(Context context, String newLanguageCode, String newCountryCode) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        // app locale
        Locale locale = createLocale(newLanguageCode, newCountryCode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Locale.setDefault(locale);
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        // updateConfiguration
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }

    /**
     * 获取本地化的实体类，兼容java
     * @param languageCode zh 代表中文
     * @param countryCode CN 简体
     *                    TW 繁体
     * @return locale
     */
    public static Locale createLocale(@Nullable String languageCode, @Nullable String countryCode) {
        if (TextUtils.isEmpty(languageCode)) {
            return getSystemDefaultLanguage();
        }
        return new Locale(languageCode, countryCode);
    }

    /**
     * 绑定语言
     * @param context
     * @return
     */
    public static Context attachBaseContext(Context context) {
        String spCountryCode = getSpCountryCode(context);
        String spLanguageCode = getSpLanguageCode(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, spLanguageCode, spCountryCode);
        } else {
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String languageCode, String countryCode) {
        Resources resources = context.getResources();
        Locale locale = createLocale(languageCode, countryCode);

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
    }

    /**
     * 获取系统的语言
     * @return
     */
    @Deprecated
    public static String getSystemLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();

        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static Locale getSystemDefaultLanguage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return LocaleList.getDefault().get(0);
        } else {
            return Locale.getDefault();
        }
    }

    public static boolean isChinaLanguage() {
        String systemLanguage = getSystemLanguage().toLowerCase();//zh-CN
        return (systemLanguage.contains("zh") && systemLanguage.contains("cn"));

    }
}
