package com.github.didikee.language;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * user author: didikee
 * create time: 12/21/18 5:22 PM
 * description: 
 */
public class LanguageUiHelper {
    private static final String TAG = "LanguageUiHelper";

    public static void showChangeLanguageDialog(final Activity activity, final LanguageItem[] languageItems, String title) {
        if (languageItems == null || languageItems.length <= 0 || activity == null || activity.isFinishing()) {
            Log.d(TAG, "Do nothing: activity has finished!");
            return;
        }
//        Resources resources = activity.getResources();
//        final String[] language = new String[]{
//                resources.getString(R.string.auto),// 0 自动切换
//                resources.getString(R.string.chinese),// 1中文简体
//                resources.getString(R.string.chinese_tw),// 2中文繁体
//                resources.getString(R.string.english),// 3英文
//                resources.getString(R.string.japanese),// 4日语
//        };

        int currentIndex = 0;
        String spLanguageCode = LanguageHelper.getSpLanguageCode(activity);
        String spCountryCode = LanguageHelper.getSpCountryCode(activity);
        Locale oldLocale = LanguageHelper.createLocale(spLanguageCode, spCountryCode);

        // 或者当前语言在列表中的位置
        String languageOld = oldLocale.getLanguage();
        String countryOld = oldLocale.getCountry();
        if (TextUtils.isEmpty(languageOld)) {
            currentIndex = 0;
        } else {
            for (int i = 0; i < languageItems.length; i++) {
                LanguageItem item = languageItems[i];
                if (item.locale == null) {
                    continue;
                }
                if (languageOld.equalsIgnoreCase(item.locale.getLanguage()) && countryOld.equalsIgnoreCase(item.locale.getCountry())) {
                    currentIndex = i;
                    break;
                }
            }
        }
        String[] titles = new String[languageItems.length];
        for (int i = 0; i < languageItems.length; i++) {
            titles[i] = languageItems[i].title;
        }

        final int finalCurrentIndex = currentIndex;
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setSingleChoiceItems(titles, currentIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (finalCurrentIndex == which) {
                            return;
                        }
                        Locale selectedLocale = languageItems[which].locale;
                        String language = "";
                        String country = "";
                        if (selectedLocale == null) {
                            LanguageHelper.setSPLanguage(activity, "", "");
                        } else {
                            language = selectedLocale.getLanguage();
                            country = selectedLocale.getCountry();
                            LanguageHelper.setSPLanguage(activity, language, country);
                        }
                        LanguageHelper.changeAppLanguage(activity, language, country);
                        activity.recreate();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
