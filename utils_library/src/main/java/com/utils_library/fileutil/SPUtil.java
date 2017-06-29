package com.utils_library.fileutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class SPUtil {
    /**
     * 文字间隔符。
     */
    public static final String STRING_SPLITER = ";;";

    /**
     * xml文件中键值的get和set方法,方法中需要传入4个参数Context、xml文件的名称xmlName、
     * 键名称keyName以及键值keyValue
     *
     * @param context
     * @param xmlName
     * @param key
     * @param defaultValue
     * @return
     */
    @SuppressLint("InlinedApi")
    public static boolean getBoolean(Context context, String xmlName, String key, boolean defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getBoolean(key, defaultValue);
    }

    @SuppressLint("InlinedApi")
    public static void setBoolean(Context context, String xmlName, String key, boolean value) {

        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (null != settingPre) {
            settingPre.edit().putBoolean(key, value).commit();
        }
    }

    @SuppressLint("InlinedApi")
    public static int getInt(Context context, String xmlName, String key, int defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getInt(key, defaultValue);
    }

    @SuppressLint("InlinedApi")
    public static void setInt(Context context, String xmlName, String key, int value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putInt(key, value).commit();
        }
    }

    @SuppressLint("InlinedApi")
    public static long getLong(Context context, String xmlName, String key, long defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getLong(key, defaultValue);
    }

    @SuppressLint("InlinedApi")
    public static void setLong(Context context, String xmlName, String key, long value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putLong(key, value).commit();
        }

    }

    @SuppressLint("InlinedApi")
    public static String getString(Context context, String xmlName, String key, String defaultValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? defaultValue : settingPre.getString(key, defaultValue);
    }

    @SuppressLint("InlinedApi")
    public static void setString(Context context, String xmlName, String key, String value) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putString(key, value).commit();
        }
    }

    @SuppressLint("InlinedApi")
    public static Float getFloat(Context context, String xmlName, String keyName, Float keyValue) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        return null == settingPre ? keyValue : settingPre.getFloat(keyName, keyValue);
    }

    @SuppressLint("InlinedApi")
    public static void setFloat(Context context, String xmlName, String keyName, Float keyValue) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().putFloat(keyName, keyValue).commit();
        }
    }

    /**
     * 获取字符串集合。已做客户端版本判断。
     *
     * @param context
     * @param xmlName   xml名称。
     * @param keyName   KEY.
     * @param defValues 缺省值。
     * @return 【不要修改返回的Set集合】Note that you must not modify the set instance returned by this call. The consistency of
     * the stored data is not guaranteed if you do, nor is your ability to modify the instance at all.
     * @see SharedPreferences#getStringSet(String, Set)
     * @since 2014年12月8日
     */
    @SuppressLint({"NewApi", "InlinedApi"})
    public static Set<String> getStringSet(Context context, String xmlName, String keyName, Set<String> defValues) {
        SharedPreferences settingPre;
        if (context == null) {
            return defValues;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            return settingPre.getStringSet(keyName, defValues);
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
            String values = settingPre.getString(keyName, null);
            if (!TextUtils.isEmpty(values)) {
                if (defValues == null) {
                    defValues = new HashSet<>();
                }
                defValues.clear();
                String[] strs = values.split(STRING_SPLITER);
                Collections.addAll(defValues, strs);
            }
            return defValues;
        }
    }

    /**
     * 存储字符串集合。已做客户端版本判断。<br>
     * <p>Note that you must not modify the set instance returned by this call.<br>
     * The consistency of the stored data is not guaranteed if you do, nor is your ability to modify the instance at
     * all.</p>
     * 不能更新的问题就出在getStringSet的object和putStringSet的object不能是同一个，不能在get之后，进行更改，然后又put进去，这样是无法更改的。
     *
     * @param context
     * @param xmlName xml名称。
     * @param keyName KEY.
     * @param values  集合的值。
     * @since 2014年12月8日
     */
    @SuppressLint({"NewApi", "InlinedApi"})
    public static void setStringSet(Context context, String xmlName, String keyName, Set<String> values) {
        SharedPreferences settingPre;
        if (context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            SharedPreferences.Editor edit = settingPre.edit();
            edit.clear();
            edit.putStringSet(keyName, values).commit();
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_WRITEABLE);
            // 低版本系统转换为String存储
            StringBuilder builder = new StringBuilder();
            if (values != null && !values.isEmpty()) {
                for (String v : values) {
                    builder.append(v).append(STRING_SPLITER);
                }
            }
            SharedPreferences.Editor edit = settingPre.edit();
            edit.putString(keyName, builder.toString()).commit();
        }
    }

    /**
     * 删除一条记录。
     *
     * @param context
     * @param xmlName
     * @param keyName
     * @since 2014年12月6日
     */
    @SuppressLint("InlinedApi")
    public static void remove(Context context, String xmlName, String keyName) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().remove(keyName).commit();
        }
    }

    /**
     * 删除SharePreference创建的Xml文件。
     *
     * @param context
     * @param xmlName
     * @since 2014年12月6日
     */
    @SuppressLint("InlinedApi")
    public static void deleteXML(Context context, String xmlName) {
        SharedPreferences settingPre = getShareModeWrite(context, xmlName);
        if (settingPre != null) {
            settingPre.edit().clear().commit();
        }
    }

    /**
     * 用于设置key的名称和默认值,key值运用泛型
     */
    public static class KEY<T> {
        public final String key;// 键的名称
        public final T defaultValue;// 键的默认值

        public KEY(String key, T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }
    }

    /**
     * 从SharePreference中直接获取一个Map.
     *
     * @param context
     * @param xmlName
     * @return Map
     * @since 2015年2月9日
     */
    @SuppressLint("InlinedApi")
    public static Map<String, ?> getAll(Context context, String xmlName) {
        SharedPreferences settingPre = getShareModeRead(context, xmlName);
        if (settingPre == null) {
            return null;
        }
        return settingPre.getAll();

    }

    /**
     * 将一个Map存入SharePreference。
     *
     * @param context
     * @param xmlName
     * @param map
     * @since 2015年2月9日
     */
    @SuppressLint("InlinedApi")
    public static void putAll(Context context, String xmlName, Map<String, String> map) {
        if (context == null || map == null) {
            return;
        }
        SharedPreferences settingPre;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
        } else {
            settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
        }
        SharedPreferences.Editor edit = settingPre.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                edit.putString(entry.getKey(), entry.getValue());
            }
        }
        edit.commit();
    }

    private static SharedPreferences getShareModeRead(Context context, String xmlName) {
        if (context != null) {
            SharedPreferences settingPre;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            } else {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_READABLE);
            }
            return settingPre;
        }
        return null;
    }

    private static SharedPreferences getShareModeWrite(Context context, String xmlName) {
        if (context != null) {
            SharedPreferences settingPre;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_MULTI_PROCESS);
            } else {
                settingPre = context.getSharedPreferences(xmlName, Context.MODE_WORLD_WRITEABLE);
            }
            return settingPre;
        }
        return null;
    }
}
