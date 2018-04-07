package com.istu.sisyuk.mobilestudent.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceHelper {

    private static final String KEY_TOKEN = "TOKEN";

    private SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            putString(KEY_TOKEN, token);
        } else {
            remove(KEY_TOKEN);
        }
    }

    public String getToken() {
        return preferences.getString(KEY_TOKEN, null);
    }

    private void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
