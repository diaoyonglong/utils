package com.luffy.utilslib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

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
     * @param isDarkColor
     */
    public void setStatusBar(Activity mActivity, int colorId, boolean isDarkColor) {
        setStatusBar(mActivity, colorId, isDarkColor, false);
    }

    /**
     * 设置状态栏
     *
     * @param mActivity
     * @param colorId
     * @param isDarkColor
     * @param isDrawable  true-drawable;false-color
     */
    public void setStatusBar(Activity mActivity, int colorId, boolean isDarkColor, boolean isDrawable) {
        /*5.0及以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏颜色
            if (isDrawable) {
                // 设置状态栏透明
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 生成一个状态栏大小的矩形
                View statusView = createStatusView(mActivity, colorId, isDrawable);
                // 添加 statusView 到布局中
                ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
                //在一个界面在来回切换顶部状态栏的时候导致透明度的状态栏不能显示 需remove掉
                if (decorView.getChildCount() >= 2) {
                    decorView.removeViewAt(1);
                }
                decorView.addView(statusView);
                // 设置根布局的参数
                setRootView(mActivity);
            } else {
                View decorView = mActivity.getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                mActivity.getWindow().setStatusBarColor(mActivity.getResources().getColor(colorId));
            }
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

    /**
     * 生成一个状态栏大小的矩形
     *
     * @param activity
     * @param color
     * @param isDrawable
     * @return
     */
    @SuppressLint("NewApi")
    private static View createStatusView(Activity activity, int color, boolean isDrawable) {
        // 绘制一个和状态栏一样高的矩形
        // 获得状态栏高度
        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusView.setLayoutParams(params);
        if (isDrawable) {
            statusView.setBackground(ContextCompat.getDrawable(activity, color));
        } else {
            statusView.setBackgroundColor(ContextCompat.getColor(activity, color));
        }
        return statusView;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }
}
