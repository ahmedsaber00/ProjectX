package io.android.ptt.presentation.base

import android.content.Context
import android.content.SharedPreferences

public class PreferenceControl {

    companion object {
        private const val SHARED_PREFS = "sharedPrefs"
        private const val KEY_Token = "Token"
        private const val KEY_LANGUAGE = "language"
        public const val ARABIC = "arabic"
        public const val ENGLISH = "english"

        @JvmStatic
        fun saveToken(context: Context, token:String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            val editor: android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(KEY_Token, token)
            editor.apply()
    }
        @JvmStatic
        fun loadToken(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_Token, "").toString()
        }

        @JvmStatic
        fun saveLanguage(context: Context, token:String) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            val editor: android.content.SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(KEY_LANGUAGE, token)
            editor.apply()
    }
        @JvmStatic
        fun loadLanguage(context: Context): String {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS, android.content.Context.MODE_PRIVATE)
            return sharedPreferences.getString(KEY_LANGUAGE, "").toString()
        }

    }

}