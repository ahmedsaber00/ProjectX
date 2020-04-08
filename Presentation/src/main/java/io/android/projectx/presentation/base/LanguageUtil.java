package io.android.projectx.presentation.base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public class LanguageUtil {
    public static void changeLanguageType(Context activity, Locale newLocale) {
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.setLocale(newLocale);
        Locale.setDefault(newLocale);
        resources.updateConfiguration(conf, dm);
    }

    public static Locale getLanguageType(Context context) {
        Log.i("=======", "context = " + context);
//        Resources resources = context.getResources();
        Resources resources = BaseApplication.getContext().getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        return config.getLocales().get(0);

    }
}
