package com.wl.wlflatproject.Presenter;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.MsgContent;
import com.lib.SDKCONST;
import com.lib.sdk.bean.PtzCtrlInfoBean;
import com.manager.account.BaseAccountManager;
import com.manager.account.LocalAccountManager;
import com.manager.db.DevDataCenter;
import com.manager.db.XMDevInfo;
import com.manager.device.DeviceManager;
import com.manager.device.media.MediaManager;
import com.manager.device.media.attribute.PlayerAttribute;
import com.manager.device.media.monitor.MonitorManager;
import com.wl.wlflatproject.Activity.MainActivity;
import com.wl.wlflatproject.AppContext;
import com.wl.wlflatproject.MUtils.FaceModelUtils;
import com.wl.wlflatproject.MView.WaitDialogTime1;
import com.wl.wlflatproject.R;
import com.xm.activity.base.XMBasePresenter;
import com.xm.linke.face.FaceDetector;
import com.xm.linke.face.FaceFeature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 设备预览界面,可以控制播放,停止,码流切换,截图,录制,全屏,信息.
 * 保存图像视频,语音对话.设置预设点并控制方向
 * Created by jiangping on 2018-10-23.
 */
public class DevMonitorPresenter extends XMBasePresenter<DeviceManager> implements
        DevMonitorContract.IDevMonitorPresenter, MediaManager.OnMediaManagerYUVListener {
    private MonitorManager mediaManager;
    private DevMonitorContract.IDevMonitorView iDevMonitorView;
    private int playState = 100;
    private String faceModePath;
    private boolean isOK = true;
    private long oldTimes;
    private int channelId;
    private String videoUuid;
    private boolean fullScreen = false;
    private WaitDialogTime1 mWaitDlg1;
    private WifiManager wifiManager;
    private String ssid;
    private RelativeLayout mFunVideoView;
    private TextView time;
    private ImageView bg;
    private final DevSnConnectPresenter devSnConnectPresenter;
    private List<String> devList = new ArrayList();
    private final DevListConnectPresenter devListConnectPresenter;

    public DevMonitorPresenter(DevMonitorContract.IDevMonitorView iDevMonitorView, ImageView bg, RelativeLayout mFunVideoView, TextView tiem) {
        this.mFunVideoView = mFunVideoView;
        devSnConnectPresenter = new DevSnConnectPresenter();
        devListConnectPresenter = new DevListConnectPresenter();
        this.time = tiem;
        this.bg = bg;
        this.iDevMonitorView = iDevMonitorView;
        String dirName = File.separator + ("com.wl.wlflatproject".hashCode() & 0x7fffffff);
        faceModePath = FaceModelUtils.moveAssertFaceModelToSDCard(iDevMonitorView.getContext(),
                Environment.getExternalStorageDirectory().getPath() + dirName);
    }

    @Override
    protected DeviceManager getManager() {
        return DeviceManager.getInstance();
    }

    @Override
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Override
    public void initMonitor(ViewGroup viewGroup) {
        mediaManager = manager.createMonitorPlayer(viewGroup, getDevId());
        /**
         * 设置设备列表缩略图保存路径，必须要在视频出图之前调用
         */
        mediaManager.setSaveThumbnailPath(AppContext.PATH_PHOTO_TEMP);
        mediaManager.setOnMediaManagerListener(this);
    }

    @Override
    public void startMonitor() {
        if (wifiManager == null)
            wifiManager = (WifiManager) iDevMonitorView.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ssid = wifiInfo.getSSID();
        if (!TextUtils.isEmpty(ssid) && !ssid.equals("<unknown ssid>")) {
            if (devList.size() == 0) {
                return;
            }
            if (mWaitDlg1 == null)
                mWaitDlg1 = new WaitDialogTime1(iDevMonitorView.getContext(), android.R.style.Theme_Translucent_NoTitleBar);
            mWaitDlg1.show();
            mWaitDlg1.setWaitText("连接设备中");
            LocalAccountManager.getInstance().updateAllDevStateFromServer(devList, new BaseAccountManager.OnDevStateListener() {
                @Override
                public void onUpdateDevState(String devId) {
                    XMDevInfo xmDevInfo = DevDataCenter.getInstance().getDevInfo(devId);
                    if (xmDevInfo.getDevState() != 0) {
                        if (xmDevInfo.getDevState() == XMDevInfo.SLEEP_UNWAKE) {
                            Toast.makeText(iDevMonitorView.getContext(), "设备不在线", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mediaManager.setStreamType(SDKCONST.StreamType.Extra);
                        mediaManager.setChnId(channelId);
                        mediaManager.startMonitor();
                    }else{
                        Toast.makeText(iDevMonitorView.getContext(), "设备不在线", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onUpdateCompleted() {

                }
            });


        } else {
            Toast.makeText(iDevMonitorView.getContext(), "平板wifi不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void startMonitor(String username, String pwd) {
        manager.loginDev(mediaManager.getDevId(), username, pwd, new DeviceManager.OnDevManagerListener() {
            @Override
            public void onSuccess(String devId, int operationType, Object result) {
                mediaManager.startMonitor();
            }

            @Override
            public void onFailed(String devId, int msgId, String jsonName, int errorId) {
                if (iDevMonitorView != null) {
                    iDevMonitorView.onPlayState(0, errorId);
                }
            }
        });
    }

    @Override
    public void stopMonitor() {
        mediaManager.stopPlay();
    }

    @Override
    public void destroyMonitor() {
        isOK = true;
        mediaManager.destroyPlay();
    }

    @Override
    public int getErrorId() {
        return 0;
    }

    @Override
    public int getPlayState() {
        return playState;
    }

    @Override
    public void capture() {
        mediaManager.capture(AppContext.PATH_PHOTO);
    }

    @Override
    public void startRecord() {
        if (!mediaManager.isRecord()) {
            mediaManager.startRecord(AppContext.PATH_VIDEO);
        }
    }

    @Override
    public void stopRecord() {
        if (mediaManager.isRecord()) {
            mediaManager.stopRecord();
        }
    }

    @Override
    public boolean isRecording() {
        return mediaManager.isRecord();
    }

    @Override
    public void openVoice() {
        mediaManager.openVoiceBySound();
    }

    @Override
    public void closeVoice() {
        mediaManager.closeVoiceBySound();
    }

    @Override
    public void startIntercomAndTalk() {
        mediaManager.startTalkByHalfDuplex(iDevMonitorView.getContext());
    }

    @Override
    public void stopTalkAndHear() {
        mediaManager.stopTalkByHalfDuplex();
    }

    @Override
    public void stopIntercom() {
        mediaManager.destroyTalk();
    }

    @Override
    public int changeStream() {
        mediaManager.setStreamType(mediaManager.getStreamType() == SDKCONST.StreamType.Extra
                ? SDKCONST.StreamType.Main : SDKCONST.StreamType.Extra);
        mediaManager.stopPlay();
        mediaManager.startMonitor();
        return mediaManager.getStreamType();
    }

    @Override
    public int getStreamType() {
        return mediaManager.getStreamType();
    }

    public void devicePTZControl(int nPTZCommand, boolean bStop) {
        PtzCtrlInfoBean ptzCtrlInfoBean = new PtzCtrlInfoBean();
        ptzCtrlInfoBean.setDevId(mediaManager.getDevId());
        ptzCtrlInfoBean.setPtzCommandId(nPTZCommand);
        ptzCtrlInfoBean.setStop(bStop);
        manager.devPTZControl(ptzCtrlInfoBean, null);
    }

    public void setTalkSound(Boolean sound) {
        if (sound) {
            mediaManager.openVoiceBySound();
        } else {
            mediaManager.closeVoiceBySound();
        }
    }

    public void test(String devName) {
        manager.modifyDevName(getDevId(), devName, new DeviceManager.OnDevManagerListener() {
            @Override
            public void onSuccess(String devId, int operationType, Object result) {

            }

            @Override
            public void onFailed(String devId, int msgId, String jsonName, int errorId) {

            }
        });
    }

    @Override
    public void onResultYUVData(PlayerAttribute attribute, final int width, final int height, final byte[] data) {
        if (isOK) {
            long curTimes = System.currentTimeMillis();
            if (curTimes - oldTimes > 1000) {
                oldTimes = System.currentTimeMillis();
                isOK = false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (width > 0 && height > 0) {
                            int[] rgb = new int[width * height];
                            FaceDetector.decodeYUV420P(rgb, data, width, height);
                            FaceFeature[] faceFeatures = FaceDetector.faceDetectV2(rgb, width, height, faceModePath);
                            if (faceFeatures != null) {
                                iDevMonitorView.onUpdateFaceFrameView(faceFeatures, width, height);
                            } else {
                                iDevMonitorView.onUpdateFaceFrameView(null, width, height);
                            }
                            isOK = true;
                        }
                    }
                }).start();
            }
        }
    }

    @Override
    public void onMediaPlayState(PlayerAttribute attribute, int state) {
        this.playState = state;
        if (state == 0) {
            if (mWaitDlg1 != null && mWaitDlg1.isShowing())
                mWaitDlg1.dismiss();
            mFunVideoView.setVisibility(View.VISIBLE);
            Log.e("摄像头------", state + "");
            time.setVisibility(View.GONE);
            setTalkSound(false);
            openVoice();
            if (fullScreen)
                iDevMonitorView.getActivity().setFullScreen();
        } else if (state == 4) {
            if (null != mFunVideoView) {
                mFunVideoView.setVisibility(View.INVISIBLE);
                bg.setBackgroundResource(R.drawable.bg1);
                time.setVisibility(View.VISIBLE);
                closeVoice();
            }
        }
        if (iDevMonitorView != null) {
            iDevMonitorView.onPlayState(state, 0);
        }
    }

    @Override
    public void onFailed(PlayerAttribute attribute, int msgId, int errorId) {
        if (iDevMonitorView != null) {
            iDevMonitorView.onPlayState(0, errorId);
        }
    }

    @Override
    public void onShowRateAndTime(PlayerAttribute attribute, boolean isShowTime, String time, String rate) {

    }

    @Override
    public void onVideoBufferEnd(PlayerAttribute attribute, MsgContent ex) {

    }

    public void setVideoUuid(String videoUuid) {
        this.videoUuid = videoUuid;
        setDevId(videoUuid);
        devList.clear();
        devList.add(videoUuid);
        String localPath = Environment.getExternalStorageDirectory().getPath();//手机本地路径
        LocalAccountManager.getInstance().login(localPath, new BaseAccountManager.OnAccountManagerListener() {
            @Override
            public void onSuccess(int msgId) {
                devSnConnectPresenter.addDev(videoUuid, null, null, 21);
            }

            @Override
            public void onFailed(int msgId, int errorId) {

            }

            @Override
            public void onFunSDKResult(Message msg, MsgContent ex) {

            }
        });
    }

    public String getVideoUuid() {
        return videoUuid;
    }

    public void setScreen(Boolean b) {
        this.fullScreen = b;
    }
}

