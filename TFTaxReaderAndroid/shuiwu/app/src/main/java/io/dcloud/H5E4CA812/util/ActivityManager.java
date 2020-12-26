package io.dcloud.H5E4CA812.util;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 页面管理工具类
 */

public class ActivityManager {

    private static ActivityManager mActivityManager;
    // 页面管理
    private static Stack<Activity> mActivityStack;
    private static List<WeakReference<Activity>> mActivityRefStack;

    public ActivityManager() {
        mActivityRefStack = new ArrayList<WeakReference<Activity>>();
    }

    public static ActivityManager getInstance() {
        if (null == mActivityManager) {
            mActivityManager = new ActivityManager();
        }
        return mActivityManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void pushActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for(int i=0;i<mActivityStack.size();i++){
            Activity activity = mActivityStack.get(i);
            if (activity.getClass().equals(cls)) {
                mActivityStack.remove(i);
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getApplicationContext().getPackageName());
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
