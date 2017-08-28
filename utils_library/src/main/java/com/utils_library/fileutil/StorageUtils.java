package com.utils_library.fileutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import com.utils_library.commonutil.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StorageUtils {

    private static final String APP = "/app/";
    private static final String IMAGE = "/image/";
    private static final String FILE = "/file/";
    private static final String SD_CARD = "sdCard";
    private static final String EXTERNAL_SD_CARD = "externalSdCard";
    private static final String EMULATED_SD_CARD = "emulatedsdCard";
    public static final String TAKE_PHOTO_PATH = "/takePhoto/";
    /**
     * 获取根目录，会根据系统中存在的一个或多个SD卡，剩余容量，自动确定目录路径。
     *
     * @param context 上下文
     */
    public static String getMainPath(Context context) {

        String path = "";
        Map<String, ExternalStorageInfo> allStorage = getAllStorage(context);

        Collection<ExternalStorageInfo> storageList = allStorage.values();
        List<ExternalStorageInfo> allInfoList = new ArrayList<>(storageList);
        if (storageList.size() > 0) {
            //排序
            Collections.sort(allInfoList, new StorageCompare());
            path = allInfoList.get(0).getVolumePath();
        } else if (TextUtils
                .equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return path;
    }

    /**
     * img的地址
     * @param filePath 图片地址
     * @return uri
     */
    public static Uri getImgUri(String filePath) {
        File file = new File(Environment.getExternalStorageDirectory(), filePath + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return Uri.fromFile(file);
    }


    /**
     * 获取其他可用地址
     *
     * @param currentPath
     * @param filename
     * @return
     */
    public static String getAnotherPath(Context context, int filetype, String currentPath, String filename) {
        if (context == null) {
            return "";
        }
        if (currentPath == null) {
            currentPath = "";
        }
        String path = "";
        Map<String, ExternalStorageInfo> allStorage = getAllStorage(context);

        Collection<ExternalStorageInfo> storageList = allStorage.values();
        List<ExternalStorageInfo> allInfoList = new ArrayList<>(storageList);
        if (storageList.size() > 0) {
            //排序
            Collections.sort(allInfoList, new StorageCompare());
            for (int i = 0; i < allInfoList.size(); i++) {
                if (!currentPath.startsWith(allInfoList.get(i).volumePath)) {
                    path = allInfoList.get(0).getVolumePath();
                }
            }
        }
        if (TextUtils.isEmpty(path) && TextUtils
                .equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (currentPath.startsWith(path)) {
                path = "";
            }
        }
        if (TextUtils.isEmpty(path)) {
            return path;
        }

        return path;
    }

    private static class StorageCompare implements Comparator<ExternalStorageInfo> {
        @Override
        public int compare(ExternalStorageInfo arg0, ExternalStorageInfo arg1) {
            return longToCompareInt(arg0.getAvailableSize() - arg1.getAvailableSize());
        }

        private int longToCompareInt(long result) {
            return result > 0 ? -1 : (result < 0 ? 1 : 0);
        }
    }

    public static List<ExternalStorageInfo> getAllStorageInfo(Context context) { // NO_UCD (use default)
        Map<String, ExternalStorageInfo> allStorage = getAllStorage(context);
        Collection<ExternalStorageInfo> storageList = allStorage.values();
        return new ArrayList<>(storageList);
    }

    private static String getCacheDir(Context context) {
        if (context == null) {
            return "";
        }
        File cacheDir = context.getFilesDir();
        return cacheDir.getAbsolutePath() + "/";
    }

    /**
     * 获取应用下载地址
     *
     * @return 应用下载地址
     */
    public static String getAppPath(Context context) {
        if (context == null) {
            return "";
        }
        String path = getMainPath(context);
        path = TextUtils.isEmpty(path) ? getCacheDir(context) : (path + APP);
        return path;
    }

    /**
     * 获取图片缓存地址
     *
     * @return 图片缓存地址
     */
    public static String getImageCachePath(Context context) {
        if (context == null) {
            return "";
        }
        String path = getMainPath(context);
        path = TextUtils.isEmpty(path) ? getCacheDir(context) : (path + IMAGE);
        return path;
    }

    static class ExternalStorageInfo {
        String volumePath;// 路径
        private long totalSize;// 总大小
        private long availableSize;// 可见大小

        @Override
        public String toString() {
            return "ExternalStorageInfo [volumePath=" + volumePath + ", totalSize=" + totalSize + ", availableSize=" + availableSize + "]";
        }

        // getter and setter
        public String getVolumePath() {
            return volumePath;
        }

        public void setVolumePath(String volumePath) {
            this.volumePath = volumePath;
        }

        public long getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(long totalSize) {
            this.totalSize = totalSize;
        }

        public long getAvailableSize() {
            return availableSize;
        }

        public void setAvailableSize(long availableSize) {
            this.availableSize = availableSize;
        }

    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    private static long getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return blockSize * totalBlocks / 1024;
    }

    /**
     * 是否含有虚拟sdcard
     *
     * @param info
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isEmulatedSdCard(ExternalStorageInfo info) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {

            long romTotalSize = getRomTotalSize();

            if (Environment.isExternalStorageEmulated() /*&& info
                                                        .getVolumePath().contains("emulated")*/ && info
                    .getTotalSize() == romTotalSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有的SD卡
     *
     * @param context
     * @return
     * @since 2015年7月16日
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static Map<String, ExternalStorageInfo> getAllStorage(Context context) {
        Map<String, ExternalStorageInfo> map = new HashMap<>();
        ExternalStorageInfo info = new ExternalStorageInfo();

        // 1 常规判断方法
        try {
            String sdcardDir = Environment.getExternalStorageDirectory().getPath();
            StatFs stat = new StatFs(sdcardDir);

            info.setVolumePath(sdcardDir);
            info.setTotalSize((long) stat.getBlockSize() * (long) stat.getBlockCount() / 1024);
            info.setAvailableSize((long) stat.getBlockSize() * (long) stat.getAvailableBlocks() / 1024);
            if (info.getTotalSize() != 0) {
                // Environment.isExternalStorageEmulated() 模拟地址 用的跟rom一个地址
                // 不算外包存储
                // Returns whether the device has an external storage device
                // which
                // is emulated. If true, the device does not have real external
                // storage, and the directory returned by
                // getExternalStorageDirectory() will be allocated using a
                // portion
                // of the internal storage system.

                if (isEmulatedSdCard(info)) {
                    map.put(EMULATED_SD_CARD, info);
                } else {
                    map.put(SD_CARD, info);
                }
            }

            // 2.读文件方法
            File vold_fstab = new File("/system/etc/vold.fstab");
            if (vold_fstab.exists()) {
                String extsdpath = null;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(vold_fstab));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.startsWith("dev_mount"))
                            continue;
                        String[] lineList = line.split(" ");
                        if (lineList.length < 3)
                            continue;
                        if (lineList[1].equals("sdcard2") || lineList[1].equals("ext_card")) {
                            extsdpath = lineList[2];
                            if (extsdpath.contains("::")) {
                                extsdpath.replace("::", "");
                            }
                        }
                    }
                    br.close();
                    if (extsdpath != null && !sdcardDir.trim().equals(extsdpath.trim())) {
                        //						extsdpath = Utils.transHightSDKPath(extsdpath) ;
                        //						Utils.EXTERNAL_SDCARD_PATH = extsdpath;
                        StatFs extsf = new StatFs(extsdpath);
                        info = new ExternalStorageInfo();
                        info.setVolumePath(extsdpath);
                        info.setTotalSize((long) extsf.getBlockSize() * (long) extsf.getBlockCount() / 1024);
                        info.setAvailableSize((long) extsf.getBlockSize() * (long) extsf.getAvailableBlocks() / 1024);
                        File file = new File(extsdpath);
                        if (info.getTotalSize() != 0 && file.canWrite()) {
                            LogUtil.d(true, "sdcard:" + extsdpath);
                            if (isEmulatedSdCard(info)) {
                                map.put(EMULATED_SD_CARD, info);
                            } else {
                                map.put(EXTERNAL_SD_CARD, info);
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e(true, e);
                }
            } // if
        } catch (Exception e) {
            LogUtil.e(true, e);
        }

        // 3.读隐藏函数方法 (3.0以上手机才有此方法)

        // 获取sdcard的路径：外置和内置
        try {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method mMethod = sm.getClass().getMethod("getVolumePaths", (Class<?>[]) null);
            String[] paths = (String[]) mMethod.invoke(sm, (Object[]) null);

            if (paths != null) {
                File file;
                for (int i = 0; i < paths.length; i++) {
                    String path = paths[i];
                    if (path == null) {
                        continue;
                    }
                    StatFs extsf = new StatFs(path);
                    info = new ExternalStorageInfo();
                    info.setVolumePath(path);
                    info.setTotalSize((long) extsf.getBlockSize() * (long) extsf.getBlockCount() / 1024);
                    info.setAvailableSize((long) extsf.getBlockSize() * (long) extsf.getAvailableBlocks() / 1024);
                    file = new File(path);
                    if (!file.canWrite()) {
                        continue;
                    }

                    if (map.get(SD_CARD) != null && map.get(SD_CARD).getVolumePath().equals(path)) {
                        continue;
                    }

                    if (map.get(EXTERNAL_SD_CARD) != null && map.get(EXTERNAL_SD_CARD).getVolumePath().equals(path)) {
                        continue;
                    }

                    if (info.getTotalSize() == 0) {
                        continue;
                    }
                    LogUtil.d(true, "======file:" + path);

                    if (i == 0) {
                        if (isEmulatedSdCard(info)) {
                            map.put(EMULATED_SD_CARD, info);
                        } else {
                            map.put(SD_CARD, info);
                        }
                    } else if (i == 1) {
                        if (isEmulatedSdCard(info)) {
                            map.put(EMULATED_SD_CARD, info);
                        } else {
                            map.put(EXTERNAL_SD_CARD, info);
                        }
                    } else {
                        if (isEmulatedSdCard(info)) {
                            map.put(EMULATED_SD_CARD, info);
                        } else {
                            map.put(SD_CARD + "_" + map.size(), info);
                        }
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LogUtil.e(true, e);
        }

        List<String> removes = new ArrayList<>();
        for (Entry<String, ExternalStorageInfo> entry : map.entrySet()) {
            ExternalStorageInfo es = entry.getValue();
            File f = new File(es.volumePath + "/test" + System.nanoTime());
            if (!f.exists()) {
                if (f.mkdirs()) {
                    f.delete();
                } else {
                    //记录该目录不可用
                    removes.add(entry.getKey());
                }
            }
        }
        for (int j = 0; j < removes.size(); j++) {
            map.remove(removes.get(j));
        }

        return map;
    }

    /**
     * 创建文件父目录
     *
     * @param file
     * @return
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
            }
        }
        return isMkdirs;
    }

    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            LogUtil.e(true, "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            LogUtil.e(true, "获取失败!");
        }
        return FormetFileSize(blockSize);
    }

    /**
     * 获取指定文件大小
     *
     * @param file 文件
     * @return 文件大小，B
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                LogUtil.e(true, "文件不存在!");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (File aFlist : flist) {
            if (aFlist.isDirectory()) {
                size = size + getFileSizes(aFlist);
            } else {
                size = size + getFileSize(aFlist);
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}