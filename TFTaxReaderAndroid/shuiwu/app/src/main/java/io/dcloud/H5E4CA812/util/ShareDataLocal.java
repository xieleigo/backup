package io.dcloud.H5E4CA812.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import io.dcloud.H5E4CA812.protocol.ApiType;

/**
 * @author tongzhiwei
 * @Description:缓存xml控件
 */
public class ShareDataLocal {
    public static Editor editor;
    public static SharedPreferences sharedPreferences;
    private static ShareDataLocal cm = null;

    public static ShareDataLocal getInstance(Context context) {
        if (cm == null) {
            cm = new ShareDataLocal();
            sharedPreferences = context.getSharedPreferences("localdata",
                    Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return cm;
    }

    public static ShareDataLocal getInstance(Context context, String sharedName) {
        cm = new ShareDataLocal();
        sharedPreferences = context.getSharedPreferences(sharedName,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return cm;
    }

    public static void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public String getFilePath() {
        return sharedPreferences.getString("mpath", "");
    }

    public void setFilePath(String path) {
        editor.putString("mpath", path);
        editor.commit();
    }

    /**
     * 保存boolean类型
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存int类型
     */
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存float类型
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 保存String类型
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存long类型
     */
    public void putLongPreference(String key, long time) {
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, time);
        editor.commit();
    }

    /**
     * 获取boolean类型
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取int类型
     */
    public int getIntPreference(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * 获取float类型
     */
    public float getFloatPreference(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    /**
     * 获取String类型
     */
    public String getStringPreference(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }
    /**
     * 获取long类型,默认值-1
     */
    public long getLongPreference(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public String getHost() {
        return sharedPreferences.getString("host", ApiType.localDefValue);
    }

    public void setHost(String result) {
        editor.putString("host", result);
        editor.commit();
    }

    public boolean getEquipSubmit() {
        return sharedPreferences.getBoolean("EquipSubmit", false);
    }

    public void setEquipSubmit(boolean result) {
        editor.putBoolean("EquipSubmit", result);
        editor.commit();
    }

    public boolean isVerify() {
        return sharedPreferences.getBoolean("Verify", false);
    }

    public void setVerify(boolean result) {
        editor.putBoolean("Verify", result);
        editor.commit();
    }

}
