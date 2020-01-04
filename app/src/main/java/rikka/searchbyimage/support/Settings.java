package rikka.searchbyimage.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Rikka on 2016/4/3.
 */
public class Settings {
    public static final String DOWNLOAD_FILE_CRASH = "download_file_crash";
    public static final String DOWNLOAD_URL = "download_url";
    public static final String DOWNLOAD_IMAGE = "download_image";

    public static final String SETTINGS_EVERY_TIME = "setting_each_time";
    public static final String SHOW_RESULT_IN = "show_result_in";
    public static final String SHOW_NOTIFICATION = "notification_result";
    public static final String ENGINE_ID = "search_engine_preference";
    public static final String NIGHT_MODE = "night_mode";
    public static final String DONATED = "donated";

    public static final String LAST_INSTALLED_VERSION = "last_installed_version";

    public static final String SUCCESSFULLY_UPLOAD_COUNT = "successfully_upload_count";
    public static final String HIDE_DONATE_REQUEST = "hide_donate_request";

    private static Settings sInstance;
    private SharedPreferences mPrefs;

    private Settings(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context)/*context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE)*/;
    }

    public static synchronized Settings instance(Context context) {
        if (sInstance == null) {
            sInstance = new Settings(context.getApplicationContext());
        }

        return sInstance;
    }

    public SharedPreferences.Editor edit() {
        return mPrefs.edit();
    }

    public void putBoolean(String key, boolean value) {
        mPrefs.edit()
                .putBoolean(key, value)
                .apply();

    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

    public void putInt(String key, int value) {
        mPrefs.edit()
                .putInt(key, value)
                .apply();

    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public Settings putString(String key, String value) {
        mPrefs.edit()
                .putString(key, value)
                .apply();

        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public Settings putIntToString(String key, int value) {
        mPrefs.edit()
                .putString(key, Integer.toString(value))
                .apply();

        return this;
    }

    public int getIntFromString(String key, int defValue) {
        return Integer.parseInt(mPrefs.getString(key, Integer.toString(defValue)));
    }
/*
    public Settings putGSON(String key, Object obj) {
        mPrefs.edit()
                .putString(key, new Gson().toJson(obj))
                .apply();

        return this;
    }

    public <T> T getGSON(String key, Class<T> c) {
        return new Gson().fromJson(mPrefs.getString(key, ""), c);
    }
*/
}
