package com.wl.wlflatproject.MUtils;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;

import static android.content.Context.KEYGUARD_SERVICE;

public class DeviceUtils {
    //获取序列号
    public static String getSerialNumber(Context context){

        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

    }

    /**
     * 唤醒手机屏幕并解锁
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public static void wakeUpAndUnlock(Context context) {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isInteractive();
        if (!screenOn) {
            // ScreenOn是LogCat里用的Tag
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "ScreenOn");
            wl.acquire(1000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

}
