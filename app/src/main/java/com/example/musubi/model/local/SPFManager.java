package com.example.musubi.model.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SPFManager {
    private final static String SPF_NAME = "MUSUBI";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SPFManager(Context context, String file_tag) {
        this.sharedPreferences = context.getSharedPreferences(SPF_NAME + file_tag, Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}
