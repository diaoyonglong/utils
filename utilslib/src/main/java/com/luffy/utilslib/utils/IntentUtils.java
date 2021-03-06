package com.luffy.utilslib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by lvlufei on 2018/1/1
 *
 * @desc 外部跳转-辅助工具
 */
public class IntentUtils {

    private IntentUtils() {
    }

    public static IntentUtils getInstance() {
        return IntentUtilsHelper.mIntentUtils;
    }

    /**
     * 静态内部类实现单例
     */
    private static class IntentUtilsHelper {
        private static IntentUtils mIntentUtils;

        static {
            mIntentUtils = new IntentUtils();
        }
    }

    /**
     * 打开浏览器
     *
     * @param mContext 上下文对象
     * @param url      网址
     */
    public void openBrowser(Context mContext, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param context
     * @param phoneNum 电话号码
     */
    public void openDiallPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 打开网络设置界面
     *
     * @param context
     */
    public void openSetting(Context context) {
        Intent mIntent = new Intent();
        if (Build.VERSION.SDK_INT > 10) {
            mIntent.setAction(Settings.ACTION_SETTINGS);
        } else {
            mIntent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
        }
        context.startActivity(mIntent);
    }
}