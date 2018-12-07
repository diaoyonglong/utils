package com.luffy.utilslib.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lvlufei on 2018/7/29
 *
 * @desc 状态栏-辅助工具
 */
public class StatusBarUtils {

    private StatusBarUtils() {

    }

    public static StatusBarUtils getInstance() {
        return StatusBarUtils.StatusBarUtilsHelper.mStatusBarUtils;
    }

    private static class StatusBarUtilsHelper {
        private static StatusBarUtils mStatusBarUtils;

        static {
            mStatusBarUtils = new StatusBarUtils();
        }
    }

    /**
     * 设置状态栏
     *
     * @param activity
     * @param hideStatusBarBackground
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置状态栏为透明
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //如果为全透明模式，取消设置Window半透明的Flag
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            //处理导航栏
            handlerNavigationBar(activity);
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        //view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * 设置状态栏
     *
     * @param mActivity
     * @param colorId
     */
    public void setStatusBar(Activity mActivity, int colorId, boolean isDarkColor) {
        /*5.0及以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = mActivity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色
            setStatusBarColor(mActivity, colorId);
        }
        /*4.4到5.0*/
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
        }
        /*6.0以后可以对状态栏文字颜色和图标进行修改*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarIconColor(mActivity, isDarkColor);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param mActivity
     * @param colorId
     */
    private void setStatusBarColor(Activity mActivity, int colorId) {
        /*5.0及以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mActivity.getWindow().setStatusBarColor(mActivity.getResources().getColor(colorId));
        }
    }

    /**
     * 设置状态栏图标和文字颜色
     *
     * @param mActivity
     * @param isDarkColor ture:实现状态栏图标和文字颜色为暗色;false:实现状态栏图标和文字颜色为浅色
     */
    private void setStatusBarIconColor(Activity mActivity, boolean isDarkColor) {
        /*实现状态栏图标和文字颜色为暗色*/
        if (isDarkColor) {
            /*6.0以后可以对状态栏文字颜色和图标进行修改*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        /*实现状态栏图标和文字颜色为浅色*/
        else {
            /*6.0以后可以对状态栏文字颜色和图标进行修改*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    /**
     * 处理导航栏
     * <p>
     * 解决：全屏时，由于视图布局会填充到状态栏和导航栏下方，如果不使用android:fitsSystemWindows=”true”属性，就会使底部导航栏和应用底部按钮重叠，导视按钮点击失效。
     *
     * @param mActivity
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void handlerNavigationBar(Activity mActivity) {
        if (ScreenUtils.getInstance().hasNavigationBar(mActivity)) {
            mActivity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, ScreenUtils.getInstance().getNavigationBarHeight(mActivity));
        }
    }
}
