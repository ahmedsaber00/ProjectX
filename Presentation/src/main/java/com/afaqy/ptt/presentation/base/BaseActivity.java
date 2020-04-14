package com.afaqy.ptt.presentation.base;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PreferenceControl.loadLanguage(this).equals(PreferenceControl.ARABIC)) {
            LanguageUtil.changeLanguageType(this, new Locale("en"));
        } else {
            LanguageUtil.changeLanguageType(this,new  Locale("ar"));
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang;
        try {
            if (!PreferenceControl.loadLanguage(newBase).equals(PreferenceControl.ARABIC)) {
                PreferenceControl.saveLanguage(newBase, PreferenceControl.ENGLISH);
                lang = PreferenceControl.ENGLISH;
            } else {
                lang = PreferenceControl.ARABIC;
            }
        } catch (Exception e) {
            lang = PreferenceControl.ENGLISH;
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, new Locale(lang)));
    }

}