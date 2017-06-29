package com.utils_library.activity;

import android.app.Activity;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * activity实例引用管理
 */
public class ActivityInstanceRef {
    private static WeakReference<Activity> curAct;
    private static WeakHashMap<Activity, Object> acList = new WeakHashMap<>();

    /**
     * 设置当前activity
     *
     * @param act
     */
    public static void setCurActivity(Activity act) {
        curAct = new WeakReference<>(act);
        acList.put(act, null);
    }

    public static Activity getCurActivity() {
        if (acList == null || acList.isEmpty())
            return null;
        WeakReference<Activity> wr = curAct;
        if (wr != null) {
            return wr.get();
        }
        return null;
    }

    public static void clearCurActivity(Activity act) {
        Object remove = acList.remove(act);
        if (remove != null && TextUtils
            .equals(remove.getClass().getSimpleName(), curAct.get().getClass().getSimpleName())) {

        }
    }

    public static void destroyAllActivity() {
        if (null != acList && !acList.isEmpty()) {
            Object[] keys = acList.keySet().toArray();
            for (Object obj : keys) {
                Activity act = (Activity)obj;
                if (act != null) {
                    act.finish();
                }
            }
        }
    }
    public static void closeActivity(String  activity) {
        if (null != acList && !acList.isEmpty()) {
            Object[] keys = acList.keySet().toArray();
            for (Object obj : keys) {
                if (obj != null && activity != null && TextUtils.equals(obj.getClass().getSimpleName(),activity)) {
                    Activity act = (Activity)obj;
                    act.finish();
                }
            }
        }
    }
}
