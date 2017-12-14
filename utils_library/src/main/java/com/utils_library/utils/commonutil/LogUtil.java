package com.utils_library.utils.commonutil;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class LogUtil {
    /**
     * cannot be instantiated
     */
    private LogUtil(){

        throw new UnsupportedOperationException("cannot be instantiated");
    }
    private static String classname;
    private static ArrayList<String> methods;

    static {
        classname = LogUtil.class.getName();
        methods = new ArrayList<>();

        Method[] ms = LogUtil.class.getDeclaredMethods();
        for (Method m : ms) {
            methods.add(m.getName());
        }
    }

    /**
     * 输出IS_OUTPUT_LOG级别的日志，自动获取类名作为TAG。
     *
     * @param msg 输出内容，带有方法名和行号。
     */
    public static void d(boolean isLog,String msg) {
        if (isLog) {
            String[] content = getMsgAndTagWithLineNumber(isLog,msg);
            Log.d(content[0], content[1]);
        }
    }

    /**
     * 输出IS_OUTPUT_LOG级别的日志。
     *
     * @param tag TAG.
     * @param msg 输出内容，带有方法名和行号。
     */
    public static void d(boolean isLog,String tag, String msg) {
        if (isLog) {
            Log.d(tag, getMsgWithLineNumber(isLog,msg));
        }
    }

    /**
     * 输出IS_OUTPUT_LOG级别的异常日志。
     *
     * @param t 异常对象。
     */
    public static void d(boolean isLog,Throwable t) {
        d(isLog,t.getMessage());
    }

    /**
     * 输出Error级别的日志。
     *
     * @param msg 输出内容，带有方法名和行号。
     */
    public static void e(boolean isLog,String msg) {
        if (isLog) {
            String[] content = getMsgAndTagWithLineNumber(isLog,msg);
            Log.e(content[0], content[1]);
        }
    }

    /**
     * 输出Error级别的日志。
     *
     * @param tag TAG.
     * @param msg 输出内容，带有方法名和行号。
     */
    public static void e(boolean isLog,String tag, String msg) {
        if (isLog) {
            Log.e(tag, getMsgWithLineNumber(isLog,msg));
        }
    }

    /**
     * 输出Error级别的异常日志。
     *
     * @param t 异常对象。
     */
    public static void e(boolean isLog,Throwable t) {
        if (isLog && t != null) {
            t.printStackTrace();
        }
    }

    /**
     * 获取日志信息的TAG、带行号的内容。
     *
     * @param msg 日志内容。
     * @return TAG、带行号的内容组成的字符串数组。
     */
    private static String[] getMsgAndTagWithLineNumber(boolean isLog,String msg) {
        try {
            for (StackTraceElement st : (new Throwable()).getStackTrace()) {
                if (!classname.equals(st.getClassName()) && !methods.contains(st.getMethodName())) {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String tag = st.getClassName().substring(b);
                    String message = st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                    return new String[]{tag, message};
                }

            }
        } catch (Exception e) {
            LogUtil.e(isLog,e);
        }
        return new String[]{"Lishang", msg};
    }

    /**
     * 获取带行号的日志信息内容。
     *
     * @param msg 日志内容。
     * @return 带行号的日志信息内容。
     */
    private static String getMsgWithLineNumber(boolean isLog,String msg) {
        try {
            for (StackTraceElement st : (new Throwable()).getStackTrace()) {
                if (!classname.equals(st.getClassName()) && !methods.contains(st.getMethodName())) {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String tag = st.getClassName().substring(b);
                    return tag + "->" + st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                }

            }
        } catch (Exception e) {
            LogUtil.e(isLog,e);
        }
        return msg;
    }

    /**
     * 输出Info级别的日志。
     *
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void i(boolean isLog,String msg) {
        if (isLog) {
            String[] content = getMsgAndTagWithLineNumber(isLog,msg);
            Log.i(content[0], content[1]);
        }
    }

    /**
     * 输出Info级别的日志。
     *
     * @param tag TAG.
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void i(boolean isLog,String tag, String msg) {
        if (isLog) {
            Log.i(tag, getMsgWithLineNumber(isLog,msg));
        }
    }

    /**
     * 输出Info级别的异常日志。
     *
     * @param t 异常对象。
     */
    public static void i(boolean isLog,Throwable t) {
        i(isLog,t.getMessage());
    }

    /**
     * 输出Vorbose级别的日志。
     *
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void v(boolean isLog,String msg) {
        if (isLog) {
            String[] content = getMsgAndTagWithLineNumber(isLog,msg);
            Log.v(content[0], content[1]);
        }
    }

    /**
     * 输出Vorbose级别的日志。
     *
     * @param tag TAG.
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void v(boolean isLog,String tag, String msg) {
        if (isLog) {
            Log.v(tag, getMsgWithLineNumber(isLog,msg));
        }
    }

    /**
     * 输出Vorbose级别的异常日志。
     *
     * @param t 异常对象。
     */
    public static void v(boolean isLog,Throwable t) {
        v(isLog,t.getMessage());
    }

    /**
     * 输出Warn级别的日志。
     *
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void w(boolean isLog,String msg) {
        if (isLog) {
            String[] content = getMsgAndTagWithLineNumber(isLog,msg);
            Log.w(content[0], content[1]);
        }
    }

    /**
     * 输出Warn级别的日志。
     *
     * @param tag TAG.
     * @param msg 日志内容，带有方法名和行号。
     */
    public static void w(boolean isLog,String tag, String msg) {
        if (isLog) {
            Log.w(tag, getMsgWithLineNumber(isLog,msg));
        }
    }

    /**
     * 输出Warn级别的异常日志。
     *
     * @param t 异常对象。
     */
    public static void w(boolean isLog,Throwable t) {
        w(isLog,t.getMessage());
    }
}
