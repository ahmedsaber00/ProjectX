package io.android.projectx.presentation.base

import android.content.Context
import android.content.SharedPreferences

public class PreferenceControl {

    companion object {
        private const val SHARED_PREFS = "sharedPrefs"
        private const val KEY = "Token"

        @JvmStatic
        fun saveData(context: Context,token:String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            val editor: android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(KEY, token)
            editor.apply()
    }
        @JvmStatic
        fun loadData(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY, "").toString()
        }

    }

}