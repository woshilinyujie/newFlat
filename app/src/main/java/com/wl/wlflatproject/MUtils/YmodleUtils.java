package com.wl.wlflatproject.MUtils;

import android.content.Context;
import android.widget.Toast;

import com.van.uart.LastError;
import com.van.uart.UartManager;

public class YmodleUtils {


    public   UartManager mUartManager;

    /**
     * 发送开始升级指令,与单片机约定
     * 校验位需要重新计算55 02 30 00 01 04 35 C6 55 03
     */

    public YmodleUtils(Context context){
        try {
            mUartManager = new UartManager();
            mUartManager.open("ttyS4", UartManager.BaudRate.B9600);
        } catch (com.van.uart.LastError lastError) {
            Toast.makeText(context, lastError.toString(), Toast.LENGTH_SHORT).show();
            lastError.printStackTrace();
        }
    }
    public void writeData() {
        try {
            byte[] data = {0x55, 0x02, 0x30, 0x00, 0x01, 0x04, 0x35, (byte) 0xC6, 0x55, 0x03};
            mUartManager.write(data, data.length);
        } catch (LastError lastError) {
            lastError.printStackTrace();
        }
    }
}
