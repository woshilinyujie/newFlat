package com.wl.wlflatproject.MService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.wl.wlflatproject.Activity.MainActivity;

import java.io.IOException;

public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent reboot = new Intent(Intent.ACTION_REBOOT);
        reboot.putExtra("nowait", 1);
        reboot.putExtra("interval", 1);
        reboot.putExtra("window", 0);
        context.sendBroadcast(reboot);
        PowerManager pManager=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        pManager.reboot("");
    }
}