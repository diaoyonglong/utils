package com.luffy.utilslib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by lvlufei on 2018/1/1
 *
 * @desc APP-辅助工具
 */
public class AppUtils {

    private AppUtils() {
    }

    public static AppUtils getInstance() {
        return AppUtilsHelper.mAppUtils;
    }

    private static class AppUtilsHelper {
        private static AppUtils mAppUtils;

        static {
            mAppUtils = new AppUtils();
        }
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context
     * @return
     */
    public String getPackName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            //得到当前应用
            if (info.pid == pid)
                //返回包名
                return info.processName;
        }
        return "";
    }

    /**
     * 获取用程序名称
     *
     * @param context
     * @return
     */
    public String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用程序名称-通过包名
     *
     * @param context
     * @param packageName
     * @return
     */
    public String getAppName(Context context, String packageName) {
        String appName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 获取用程序图标-通过包名
     *
     * @param context
     * @param packageName
     * @return
     */
    public Drawable getAppIcon(Context context, String packageName) {
        Drawable appIcon = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appIcon = packageManager.getApplicationIcon(applicationInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    /**
     * 获取应用版本名称
     *
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}