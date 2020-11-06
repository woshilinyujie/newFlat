package com.wl.wlflatproject.MService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;

import com.wl.wlflatproject.MUtils.DateUtils;

public class DataService extends BroadcastReceiver {
    private static final String ACTION_TIME_CHANGED = "android.intent.action.TIME_TICK";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_TIME_CHANGED.equals(action)) {
            String s = DateUtils.getInstance().dateFormat8(System.currentTimeMillis());
            if(DateUtils.getInstance().dateFormat8(System.currentTimeMillis()).equals("14:04")){
                int a=100;
            }
        }
    }
}
