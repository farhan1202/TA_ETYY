package com.dev.ta_etyy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context mContext;

    private static final String PREF_NAME = "Pref_SetUp";
    private static final String SESSION_KEY = "SESSION_USER";
    public static final String SP_ID = "spID";
    public static final String SP_NAMA = "spNAMA";

    public PrefManager(Context context){
        this.mContext = context;
        preferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    //SESSION LOGIN PREF
    public void saveSession(){
        editor.putBoolean(SESSION_KEY, true);
        editor.commit();
    }

    public boolean getSession(){
        return preferences.getBoolean(SESSION_KEY, false);
    }

    public void removeSession(){
        editor.putBoolean(SESSION_KEY, false);
        editor.commit();
    }

    public void spInt(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getId(){
        return preferences.getString(SP_ID, "");
    }

    public void spNama(String key, String token){
        editor.putString(key, token);
        editor.commit();
    }

    public  String getNama(){
        return preferences.getString(SP_NAMA, "");
    }
}
