package com.utils_library.utils.fileutil;

import android.os.StatFs;
import android.text.TextUtils;

import com.utils_library.utils.commonutil.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guojiadong
 * 文件工具类
 */

public class FileOperate {
    /**
     * 拷贝文件
     *
     * @param sourcePath
     * @param targetPath
     * @param delete     是否删除原文件
     * @return
     */
    public static String copyfile(String sourcePath, String targetPath, Boolean delete) {
        File fromFile = new File(sourcePath);
        File toFile = new File(targetPath);
        if (!fromFile.exists()) {
            return null;
        }
        if (!fromFile.isFile()) {
            return null;
        }
        if (!fromFile.canRead()) {
            return null;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
            if (fromFile.exists() && delete) {
                fromFile.delete();
            }
        } catch (Exception ex) {
            LogUtil.e(true,"readfile", ex.getMessage());
        }
        return targetPath;
    }

    /**
     * 获取文件大小
     * @param f
     * @return
     */

    public static long getFileSizes(File f) {// 取得文件大小
        long s = 0;
        FileInputStream fis = null;
        try {
            if (f.exists()) {
                fis = new FileInputStream(f);
                f.createNewFile();
                s = fis.available();
            } else {
                LogUtil.e(true,"cn.teamtone", "文件不存在");
            }
        } catch (IOException e) {
            LogUtil.e(true,"e------",e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    LogUtil.e(true,"e------",e.toString());
                }
            }
        }
        if (s <= 1024) {
            s = 1;
        } else {
            s = s / 1024;
        }
        return s;
    }

    /**
     * 删除文件
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file != null && file.exists() && file.isFile()) {
                return file.delete();
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 删除目录
     * @param dir
     */
    public static void deleteDirectory(String dir) {
        if (TextUtils.isEmpty(dir)) {
            return;
        }
        File directoryOrFile = new File(dir);
        if (!directoryOrFile.exists()) {
            return;
        }

        if (!directoryOrFile.isDirectory()) {
            directoryOrFile.delete();
        } else {
            for (File child : directoryOrFile.listFiles()) {
                deleteDirectory(child.getAbsolutePath());
            }
        }

    }
    /**
     * 获取指定目录的可用大小
     */
    @SuppressWarnings("deprecation")
    public static long getAvailableSize(File file) {
        try {
            StatFs sf = new StatFs(file.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            return availCount * blockSize;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 获取文件路径
     * @param filePath
     * @return
     */
    public static String getFilepath(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();// 创建文件夹
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取文件
     * @param filePath
     * @return
     */
    public static File getFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();// 创建文件夹
        }
        return file;
    }
}
