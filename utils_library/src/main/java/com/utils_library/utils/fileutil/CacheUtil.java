package com.utils_library.utils.fileutil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.utils_library.utils.commonutil.LogUtil;
import com.utils_library.utils.netutil.MyTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by guojiadong
 * on 2016/12/22.
 */

public class CacheUtil {
    /**
     * 本地缓存 写入json文件
     *
     * @param content  内容
     * @param fileName 文件名
     */
    public static void writeJsonCache(final Context context, final String content, final String fileName) {
        if (content == null || TextUtils.isEmpty(content) || TextUtils.isEmpty(fileName)) {
            return;
        }
        Runnable run = new Runnable() {
            @Override
            public void run() {

                String jsonPath = StorageUtils.getMainPath(context) + "/json/";
                File fileDir = new File(jsonPath);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                File file = new File(jsonPath, fileName);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream out = null;
                try {
                    file.createNewFile();
                    out = new FileOutputStream(file, true);
                    out.write(content.getBytes());
                } catch (IOException e) {
                    LogUtil.e(true,e);
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            LogUtil.e(true,e);
                        }
                    }
                }

            }
        };
        MyTask.runInBackground(run, true);
    }

    /**
     * 本地缓存 读取json文件
     *
     * @param fileName 文件名
     * @param updateUI 回调，在UI线程执行。
     * @throws IOException
     */
    public static void readJsonCache(Context context,String fileName, final CacheCallback updateUI) {
        if(context == null){
            return;
        }

        String jsonPath = StorageUtils.getMainPath(context) + "/json/";
        final File file = new File(jsonPath, fileName);
        if (!file.exists() || file.isDirectory()) {
            if (updateUI != null)
                updateUI.onResult(null);
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BufferedReader br = null;
                StringBuilder sb = new StringBuilder();
                try {
                    br = new BufferedReader(new FileReader(file));
                    String temp = br.readLine();
                    while (temp != null) {
                        sb.append(temp);
                        temp = br.readLine();
                    }
                } catch (IOException e) {
                    LogUtil.e(true,e);
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            LogUtil.e(true,e);
                        }
                    }
                }
                final String result = sb.toString();
                if (updateUI != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            updateUI.onResult(result);
                        }
                    });
                }
            }
        };

        MyTask.runInBackground(runnable, true);
    }

    /**
     * 获取所有缓存文件的字节数，耗时操作。缓存包括JSON缓存、图片缓存。
     *
     * @return 所有缓存文件的字节数。
     * @since 2015年7月20日
     */
    public static long getAllCacheSize(Context context) {
        if(context == null){
            return 0;
        }
        long size = 0;
        // JSON缓存
        String jsonPath = StorageUtils.getMainPath(context) + "/json/";
        size += getDirSize(new File(jsonPath));
        return size;
    }

    /**
     * 获取 path 路径下的所有文件和子目录所有文件的总和。
     *
     * @param file
     * @return
     * @since 2015年7月20日
     */
    public static long getDirSize(File file) {
        long size = 0;
        if (file != null && file.exists()) {
            if (file.isFile()) {
                size += file.length();
            } else if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null && subFiles.length > 0) {
                    for (File sub : subFiles) {
                        size += getDirSize(sub);
                    }
                }
            }
        }
        return size;
    }

    /**
     * 删除所有的JSON缓存。
     *
     * @since 2015年6月16日
     */
    public static void cleanAllJsonCache(Context context) {
        if(context == null){
            return;
        }
        FileOperate.deleteDirectory(StorageUtils.getMainPath(context) + "/json/");
    }

    /**
     * 清理所有的缓存，包括JSON缓存、图片缓存
     *
     * @since 2015年6月16日
     */
    public static void cleanAllCache(Context context) {
        if(context == null){
            return;
        }
        cleanAllJsonCache(context);
    }

    public interface CacheCallback {
        void onResult(String result);
    }
}
