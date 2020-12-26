package io.dcloud.H5E4CA812;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.dcloud.H5E4CA812.util.MyActivityManager;
import io.dcloud.H5E4CA812.view.activity.SplashActivity;
import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;
import xcrash.ICrashCallback;
import xcrash.TombstoneManager;
import xcrash.TombstoneParser;
import xcrash.XCrash;


public class GlobleApplication extends Application {
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static Activity ALLactivity;
    public static String TAG = "GlobleApplication";
    private static GlobleApplication instance;
    public static Typeface typeface;
    public static String passWordKEY = "bb564fa7";
    public static boolean IsQuit = false;
    // 页面管理
    private static Stack<Activity> mActivityStack;

    // 单例模式
    public static GlobleApplication getInstance() {
        if (instance == null) {
            instance = new GlobleApplication();
        }
        return instance;
    }

    public static ExecutorService singleTaskExecutor;
    public static String authorization = "";

    public static String WXappId = "wx8592574799e9e9ba";

    public static String orderId = "";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        singleTaskExecutor = Executors.newCachedThreadPool();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The callback when App process crashed.
        ICrashCallback callback = new ICrashCallback() {
            @Override
            public void onCrash(String logPath, String emergency) {
                Log.d(TAG, "appCrash" + logPath + emergency);
                if (emergency != null) {
                    debug(logPath, emergency);

                    // Disk is exhausted, send crash report immediately.
                    sendThenDeleteCrashLog(logPath, emergency);
                } else {
                    // Add some expanded sections. Send crash report at the next time APP startup.

                    // OK
                    TombstoneManager.appendSection(logPath, "expanded_key_1", "expanded_content");
                    TombstoneManager.appendSection(logPath, "expanded_key_2", "expanded_content_row_1\nexpanded_content_row_2");

                    // Invalid. (Do NOT include multiple consecutive newline characters ("\n\n") in the content string.)
                    // TombstoneManager.appendSection(logPath, "expanded_key_3", "expanded_content_row_1\n\nexpanded_content_row_2");

                    debug(logPath, null);
                }
            }
        };

        // Initialize xCrash.
        XCrash.init(this, new XCrash.InitParameters()
                .setAppVersion("1.2.3-beta456-patch789")
                .setJavaRethrow(true)
                .setJavaLogCountMax(10)
                .setJavaDumpAllThreadsWhiteList(new String[]{"^main$", "^Binder:.*", ".*Finalizer.*"})
                .setJavaDumpAllThreadsCountMax(10)
                .setJavaCallback(callback)
                .setNativeRethrow(true)
                .setNativeLogCountMax(10)
                .setNativeDumpAllThreadsWhiteList(new String[]{"^xcrash\\.sample$", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"})
                .setNativeDumpAllThreadsCountMax(10)
                .setNativeCallback(callback)
                .setPlaceholderCountMax(3)
                .setPlaceholderSizeKb(512)
                .setLogFileMaintainDelayMs(1000));

        // Send all pending crash log files.
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (File file : TombstoneManager.getAllTombstones()) {
                    sendThenDeleteCrashLog(file.getAbsolutePath(), null);
                }
            }
        }).start();

    }

    private void sendThenDeleteCrashLog(String logPath, String emergency) {
        // Parse
        //Map<String, String> map = TombstoneParser.parse(logPath, emergency);
        //String crashReport = new JSONObject(map).toString();

        // Send the crash report to server-side.
        // ......

        // If the server-side receives successfully, delete the log file.
        //
        // Note: When you use the placeholder file feature,
        //       please always use this method to delete tombstone files.
        //
        //TombstoneManager.deleteTombstone(logPath);
    }

    private void debug(String logPath, String emergency) {
        Log.d("xcrash_sample", "logPath: " + (logPath != null ? logPath : "(null)") + ", emergency: " + (emergency != null ? emergency : "(null)"));
        String time = this.format.format(new Date());
        String fileName = "xCrash_" + time + ".log";
        String path = "/sdcard/shuiwu/xCrash/";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Parse and save the crash info to a JSON file for debugging.
        FileWriter writer = null;
        try {
            File debug = new File(path + fileName);
            debug.createNewFile();
            writer = new FileWriter(debug, false);
            writer.write(new JSONObject(TombstoneParser.parse(logPath, emergency)).toString());
        } catch (Exception e) {
            Log.d("xcrash_sample", "debug failed", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                }
            }
        }
//        restartApp();
    }


    public void restartApp() {
        Log.d("xcrash_sample", "restartApp");
        //APP奔溃重启，
        Intent intent = new Intent(getApplicationContext().getApplicationContext(),
                SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0, intent, intent.getFlags());
        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100,
                restartIntent); // 1秒钟后重启应用
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(2);
        System.gc();
    }

}
