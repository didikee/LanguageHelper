package com.github.didikee.language;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * user author: didikee
 * create time: 12/21/18 6:46 PM
 * description: 
 */
public class LanguageActivity extends AppCompatActivity {
    private String oldLanguageCode;
    private String oldCountryCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oldLanguageCode = LanguageHelper.getSpLanguageCode(this);
        oldCountryCode = LanguageHelper.getSpCountryCode(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.attachBaseContext(base));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String spLanguageCode = LanguageHelper.getSpLanguageCode(this);
        String spCountryCode = LanguageHelper.getSpCountryCode(this);

        if (!spLanguageCode.equalsIgnoreCase(oldLanguageCode) || !spCountryCode.equalsIgnoreCase(oldCountryCode)) {
            oldLanguageCode = spLanguageCode;
            oldCountryCode = spCountryCode;
            recreate();
        }

    }
}
