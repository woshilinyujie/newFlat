package com.wl.wlflatproject.MUtils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogUtils {
    public void getLog() {
        String[] running = new String[]{"logcat", "-s", "adb logcat *: V"};
        Process exec = null;
        try {
            exec = Runtime.getRuntime().exec(running);
            final InputStream is = exec.getInputStream();
            new Thread() {
                @Override
                public void run() {
                    FileOutputStream os = null;
                    try {
                        //新建一个路径信息
                        os = new FileOutputStream(Environment.getDataDirectory()+"/Log.txt");
                        int len = 0;
                        byte[] buf = new byte[1024];
                        while (-1 != (len = is.read(buf))) {
                            os.write(buf, 0, len);
                            os.flush();
                        }
                    } catch (Exception e) {
                        Log.d("writelog",
                                "read logcat process failed. message: "
                                        + e.getMessage());
                    } finally {
                        if (null != os) {
                            try {
                                os.close();
                                os = null;
                            } catch (IOException e) {
                                // Do nothing
                            }
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

