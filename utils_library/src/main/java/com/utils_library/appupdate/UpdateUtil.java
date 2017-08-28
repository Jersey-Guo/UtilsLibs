package com.utils_library.appupdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.utils_library.R;
import com.utils_library.netutil.NetUtil;
import com.utils_library.view.dialog.RxDialogLoading;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.service.DownloadService;
import com.vector.update_app.utils.AppUpdateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guojiadong
 * on 2017/8/28.
 */

public class UpdateUtil {
    private static String mUpdateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";

    public static void chackUpdate(final Activity context) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        final RxDialogLoading loading = new RxDialogLoading(context);
        Map<String, String> params = new HashMap<>();

        params.put("appKey", "ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f");
        params.put("appVersion", AppUpdateUtils.getVersionName(context));
        params.put("key1", "value2");
        params.put("key2", "value3");


        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(context)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(mUpdateUrl)

                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                .hideDialogOnDownloading(false)
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                .setTopPic(R.mipmap.top_8)
                //为按钮，进度条设置颜色。
                .setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")

                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(jsonObject.optString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(jsonObject.optString("new_version"))
                                    //（必须）下载地址
                                    .setApkFileUrl(jsonObject.optString("apk_file_url"))
                                    //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
                                    //（必须）更新内容
//                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //测试内容过度
//                                    .setUpdateLog("测试")
                                    .setUpdateLog("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12")
//                                    .setUpdateLog("今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说\r\n")
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(jsonObject.optString("target_size"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(true)
                                    //设置md5，可以不设置
                                    .setNewMd5(jsonObject.optString("new_md51"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        showDiyDialog(context, true, updateApp, updateAppManager);
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        loading.show();
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        loading.cancel();
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp() {
                        Toast.makeText(context,context.getResources().getString(R.string.no_new_version), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 自定义对话框
     */
    private static void showDiyDialog(final Activity context, final boolean isShowDownloadProgress, final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        String targetSize = updateApp.getTargetSize();
        String updateLog = updateApp.getUpdateLog();

        String msg = "";

        if (!TextUtils.isEmpty(targetSize)) {
            msg = context.getResources().getString(R.string.new_version_size) + targetSize + "\n\n";
        }

        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(String.format(context.getResources().getString(R.string.isupdate_hint), updateApp.getNewVersion()))
                .setMessage(msg)
                .setPositiveButton(context.getResources().getString(R.string.update_app), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //显示下载进度
                        if (isShowDownloadProgress) {
                            if (NetUtil.isWifi(context)) {
                                downloadAPK(context, updateAppManager);
                            } else {
                                showIsUpdate(context, updateAppManager);
                            }
                        } else {
                            //不显示下载进度
                            updateAppManager.download();
                        }

                        dialog.dismiss();
                    }
                });
        if (updateApp.isConstraint()) {
            dialog.setCancelable(false);
            dialog.setNegativeButton(context.getResources().getString(R.string.exit_app), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    context.finish();
                }
            });
        } else {
            dialog.setCancelable(true);
            dialog.setNegativeButton(context.getResources().getString(R.string.no_update), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        dialog.create().show();

    }

    private static void showIsUpdate(final Activity context, final UpdateAppManager updateAppManager) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setCancelable(false);
        adb.setMessage(context.getResources().getString(R.string.no_wifi_isupdate));

        // 设置取消按钮的监听
        adb.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        // 点击确定按钮
        adb.setPositiveButton(context.getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {

            @SuppressWarnings({"deprecation", "static-access"})
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK(context, updateAppManager);
            }
        });

        adb.setNegativeButton(context.getResources().getString(R.string.cancle), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        adb.show();
    }


    private static void downloadAPK(final Activity context, UpdateAppManager updateAppManager) {
        updateAppManager.download(new DownloadService.DownloadCallback() {
            @Override
            public void onStart() {
                HProgressDialogUtils.showHorizontalProgressDialog(context, context.getString(R.string.download_press), false);
            }

            /**
             * 进度
             *
             * @param progress  进度 0.00 -1.00 ，总大小
             * @param totalSize 总大小 单位B
             */
            @Override
            public void onProgress(float progress, long totalSize) {
                HProgressDialogUtils.setProgress(Math.round(progress * 100));
            }

            /**
             *
             * @param total 总大小 单位B
             */
            @Override
            public void setMax(long total) {

            }


            @Override
            public boolean onFinish(File file) {
                HProgressDialogUtils.cancel();
                return true;
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                HProgressDialogUtils.cancel();

            }
        });
    }
}
