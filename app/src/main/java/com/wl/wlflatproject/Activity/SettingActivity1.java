package com.wl.wlflatproject.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qtimes.service.wonly.client.QtimesServiceManager;
import com.wl.wlflatproject.Bean.MainMsgBean;
import com.wl.wlflatproject.Bean.SetMsgBean;
import com.wl.wlflatproject.MUtils.SPUtil;
import com.wl.wlflatproject.MView.NormalDialog;
import com.wl.wlflatproject.MView.SetDialog;
import com.wl.wlflatproject.MView.WaitDialogTime;
import com.wl.wlflatproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity1 extends AppCompatActivity {

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.wait_time_tv)
    TextView waitTimeTv;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.num_rl)
    RelativeLayout numRl;
    @BindView(R.id.activation)
    RelativeLayout activation;
    @BindView(R.id.system)
    RelativeLayout system;
    @BindView(R.id.engineering_mode)
    RelativeLayout engineeringMode;
    @BindView(R.id.restart)
    RelativeLayout restart;
    @BindView(R.id.anti_pinch)
    RelativeLayout antiPinch;
    @BindView(R.id.experience)
    RelativeLayout experience;
    @BindView(R.id.entry_door)
    RelativeLayout entryDoor;
    @BindView(R.id.door_select)
    RelativeLayout doorSelect;
    private String value;
    private SetDialog setDialog;
    private SetDialog.ResultListener listener;
    private MainMsgBean mainMsgBean;
    private WaitDialogTime waitDialogTime;
    private NormalDialog normalDialog;
    private Intent setIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout1);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        setIntent = getIntent();
            numRl.setVisibility(View.VISIBLE);
            system.setVisibility(View.VISIBLE);
            activation.setVisibility(View.VISIBLE);
            antiPinch.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            engineeringMode.setVisibility(View.VISIBLE);
            experience.setVisibility(View.VISIBLE);
            entryDoor.setVisibility(View.VISIBLE);
            SPUtil instance = SPUtil.getInstance(this);
            if (instance.getSettingParam("open", false)) {
                numTv.setText("开");
            } else {
                numTv.setText("关");
            }
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        waitTimeTv.setText(intent.getStringExtra("openDoorWaitTime") + "秒");
        listener = new SetDialog.ResultListener() {
            @Override
            public void onResult(String value, int flag) {
                SettingActivity1.this.value = value;
                if (mainMsgBean == null)
                    mainMsgBean = new MainMsgBean();
                mainMsgBean.setMsg(value);
                mainMsgBean.setFlag(flag);
                EventBus.getDefault().post(mainMsgBean);
                if (waitDialogTime == null)
                    waitDialogTime = new WaitDialogTime(SettingActivity1.this, android.R.style.Theme_Translucent_NoTitleBar);
                waitDialogTime.show();
            }
        };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SetMsgBean setMsgBean) {
        if (waitDialogTime != null & waitDialogTime.isShowing())
            waitDialogTime.dismiss();
        switch (setMsgBean.getFlag()) {
            case 4:
                Toast.makeText(SettingActivity1.this, "设置等待时间成功", Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(value))
                    waitTimeTv.setText(value + "秒");
                break;


        }
    }


    @OnClick({R.id.door_select,R.id.entry_door,R.id.experience,R.id.back, R.id.wait_time, R.id.setting, R.id.num_rl, R.id.activation, R.id.system, R.id.engineering_mode, R.id.restart, R.id.anti_pinch})
    public void onViewClicked(View view) {
        if (setDialog == null) {
            setDialog = new SetDialog(SettingActivity1.this, R.style.mDialog);
            setDialog.setListener(listener);
        }
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.setting:
                Intent intent = new Intent(SettingActivity1.this, SettingActivity.class);
                intent.putExtras(setIntent);
                startActivityForResult(intent,500);
                break;
            case R.id.door_select:
                Intent intent2 = new Intent(SettingActivity1.this, DoorSelectActivity.class);
                intent2.putExtras(intent2);
                startActivity(intent2);
                break;
            case R.id.num_rl:
                Intent intent1 = new Intent(SettingActivity1.this, RoomCheckActivity.class);
                startActivityForResult(intent1, 100);
                break;
            case R.id.wait_time:
                setDialog.show(4);
                break;
            case R.id.experience:
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("体验改善界面");
                normalDialog.setContentText("点击确定进入体验改善界面");
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        Intent intent2 = new Intent();
                        intent2.setClassName("com.qtimes.wonly", "com.qtimes.wonly.activity.ticket.TicketListActivity");
                        startActivity(intent2);
                    }
                });
                break;
            case R.id.system://系统信息界面
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("系统信息界面");
                normalDialog.setContentText("点击确定进入系统信息界面");
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        Intent intent2 = new Intent();
                        intent2.setClassName("com.qtimes.wonly", "com.qtimes.wonly.activity.SystemInfoActivity");
                        startActivity(intent2);
                    }
                });
                break;
            case R.id.engineering_mode://工程模式
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("工程模式");
                normalDialog.setContentText("点击确定进入工程模式");
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        try {
                            if (!QtimesServiceManager.instance().isServerActive()) {
                                QtimesServiceManager.instance().connect(SettingActivity1.this);
                            }
                            QtimesServiceManager instance = QtimesServiceManager.instance();
                            instance.recoveryMode();
                        } catch (Exception e) {
                            String s = e.toString();
                            Toast.makeText(SettingActivity1.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.activation://设备激活页面

                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("设备激活页面");
                normalDialog.setContentText("点击确定进入设备激活页面");
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        Intent intent2 = new Intent();
                        intent2.setClassName("com.qtimes.wonly", "com.qtimes.wonly.activity.device.DevACActivity");
                        startActivity(intent2);
                    }
                });

            break;
            case R.id.restart://系统重启
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("系统重启");
                normalDialog.setContentText("点击确定系统重启");
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        QtimesServiceManager.instance().reboot();
                    }
                });

                break;
            case R.id.anti_pinch://防夹
                boolean a = QtimesServiceManager.instance().getAntiPinchStatus();
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("防夹");
                if(a){
                    normalDialog.setContentText("点击关闭防夹");
                }else {
                    normalDialog.setContentText("点击开启防夹");
                }
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        boolean b = QtimesServiceManager.instance().resetAntiPinch();
                    }
                });
                break;
            case R.id.entry_door://入户即关
                boolean status = QtimesServiceManager.instance().getWishesStatus();
                if (normalDialog == null)
                    normalDialog = new NormalDialog(this, R.style.mDialog);
                normalDialog.show();
                normalDialog.setTitleText("入户即关");
                if(status){
                    normalDialog.setContentText("点击关闭入户即关");
                }else {
                    normalDialog.setContentText("点击开启入户即关");
                }
                normalDialog.getConfirmTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        normalDialog.dismiss();
                        if (!QtimesServiceManager.instance().isServerActive()) {
                            QtimesServiceManager.instance().connect(SettingActivity1.this);
                        }
                        boolean b = QtimesServiceManager.instance().setWishesStatus(!status);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
                SPUtil instance = SPUtil.getInstance(this);
                if (instance.getSettingParam("open", false)) {
                    numTv.setText("开");
                } else {
                    numTv.setText("关");
                }
        }else if(resultCode==300){
            setIntent=data;
        }
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        super.onResume();
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
