package com.wl.wlflatproject.Presenter;

import android.content.Context;
import android.view.ViewGroup;

import com.wl.wlflatproject.Activity.MainActivity;
import com.xm.linke.face.FaceFeature;

/**
 * 设备预览界面,可以控制播放,停止,码流切换,截图,录制,全屏,信息.
 * 保存图像视频,语音对话.设置预设点并控制方向
 * Created by jiangping on 2018-10-23.
 */
public class DevMonitorContract {
    public interface IDevMonitorView {
        void onPlayState(int state, int errorId);
        void onUpdateFaceFrameView(FaceFeature[] faceFeatures, int width, int height);
        Context getContext();
        MainActivity getActivity();
    }

    public interface IDevMonitorPresenter {
        void setChannelId(int channelId);
        /**
         * 初始化实时预览
         * @param viewGroup
         */
        void initMonitor(ViewGroup viewGroup);

        /**
         * 开启实时预览
         */
        void startMonitor();

        /**
         * 开启实时预览
         * @param username 设备登录名
         * @param pwd 设备密码
         */
        void startMonitor(String username, String pwd);

        /**
         * 停止实时预览
         */
        void stopMonitor();

        /**
         * 销毁实时预览
         */
        void destroyMonitor();

        int getErrorId();

        /**
         * 获取播放状态
         * @return
         */
        int getPlayState();

        /**
         * 抓图
         */
        void capture();

        /**
         * 开始视频剪切
         */
        void startRecord();

        /**
         * 停止视频剪切
         */
        void stopRecord();

        /**
         * 当前是否处于视频剪切中
         * @return
         */
        boolean isRecording();

        /**
         * 打开音频
         */
        void openVoice();

        /**
         * 关闭音频
         */
        void closeVoice();

        /**
         * 开始对讲,只说话
         */
        void startIntercomAndTalk();

        /**
         * 停止说话开始听
         */
        void stopTalkAndHear();

        /**
         * 停止对讲
         */
        void stopIntercom();

        /**
         * 切换主副码流
         * @return 返回改变后的码流类型
         */
        int changeStream();

        /**
         * 获取当前播放的码流类型
         * @return
         */
        int getStreamType();
    }
}

