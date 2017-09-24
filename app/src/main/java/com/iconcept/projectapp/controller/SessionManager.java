package com.iconcept.projectapp.controller;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "ProjectAppPref";

        // All Shared Preferences Keys
        private static final String AUTH_STRING = "AuthString";

        private static final String IS_LOGIN = "IsLoggedIn";

        // User ID (make variable public to access from outside)
        public static final String KEY_USERID = "userid";

        // User name (make variable public to access from outside)
        public static final String KEY_NAME = "name";

        // Email address (make variable public to access from outside)
        public static final String KEY_EMAIL = "email";

        //Status of user
        public static final String KEY_STATUS = "available";

        // Constructor
        public SessionManager(Context context){
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

    /**
     * Save Authentication String for REST api call
     * */
    public void saveAuthString(String authString){

        editor.putString(AUTH_STRING, authString);

        // commit changes
        editor.commit();
    }

    public String getAuthString(){
        return pref.getString(AUTH_STRING, "");
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String user_id, String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing user id in pref
        editor.putString(KEY_USERID, user_id);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putBoolean(KEY_STATUS, true);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Get user id
     * */
    public String getUserId(){
        return pref.getString(KEY_USERID, "");
    }

}
