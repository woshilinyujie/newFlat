package com.wl.wlflatproject.MUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 保存配置信息
 */
public class PreferencesUtils {


    /**
     * 保存字符串
     *
     * @param key    对应KEY
     * @param values 对应值
     */
    public static void saveString(Context context,String key, String values) {
        SharedPreferences sp = context.getSharedPreferences("sp", 0);
        sp.edit().putString(key, values).commit();
    }

    /**
     * 获取字符串的值
     *
     * @param key
     * @return
     */
    public static String getString(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("sp", 0);
        return sp.getString(key, null);
    }

}
