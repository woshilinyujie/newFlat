package com.wl.wlflatproject.MUtils;

import android.util.Log;

import com.wl.wlflatproject.Activity.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class SerialPortUtil {
    public SerialPort mSerialPort;
    public BufferedReader inputStream;
    public OutputStream outputStream;
    public DataListener listener;
    private static SerialPortUtil mSerialPortUtil = null;
    private Thread thread;
    public boolean flag=true;

    public static SerialPortUtil getInstance() {
        if (mSerialPortUtil == null) {
            mSerialPortUtil = new SerialPortUtil();
        } else {
            return mSerialPortUtil;
        }
        return mSerialPortUtil;
    }


    //链接串口
    public SerialPortUtil() {
        startSerialPortUtil();
    }

    private void init() {
        if (mSerialPort == null) {
            String path;
//            if(MainActivity.isHIgh){
                path = "/dev/ttyS4";//串口地址
//            }else{
//                path = "/dev/ttyS3";//串口地址
//            }
            int baurate = 9600;
            try {
                mSerialPort = new SerialPort(new File(path), baurate, 0);
                inputStream=new BufferedReader(new InputStreamReader(mSerialPort.getInputStream()));
                outputStream = mSerialPort.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startSerialPortUtil(){
                init();
    }


    //发送数据给串口
    public void sendDate(byte[] writeBytes) {
        if (outputStream != null) {
            try {
                outputStream.write(writeBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //接收串口数据
    public void readCode(DataListener listener) {
        if (inputStream != null) {
            this.listener=listener;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag) {
                        try {
                            String s=inputStream.readLine();
                            if(s.length()>0 &&listener!=null){//有数据返回
                                Log.e("buffer",s);
                                listener.getData(s);

                            }
                        } catch (Exception e) {
                            Log.e("串口错误",e.toString());
                            String s = e.toString();
                            close();
                        }
                    }
                }
            }).start();
        }
    }
    public void close(){
        if(mSerialPort!=null){
            mSerialPort.close();
            mSerialPort=null;
            mSerialPortUtil=null;
            inputStream=null;
            outputStream=null;
        }
    }
    public interface DataListener{
        void getData(String data);
    }

}

