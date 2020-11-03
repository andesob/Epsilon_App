package no.ntnu.epsilon_app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedUserPrefs {

    private final static String SHARED_USER_PREF_NAME = "shared_user_prefs";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedUserPrefs (Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_USER_PREF_NAME, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public String getToken(){
        return sharedPreferences.getString("token","");
    }
    public void setToken(String token){
        editor.putString("token",token).apply();
    }
}
