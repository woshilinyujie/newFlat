package com.wl.wlflatproject.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.lib.EFUN_ERROR;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.manager.db.DevDataCenter;
import com.manager.db.XMDevInfo;
import com.manager.device.config.PwdErrorManager;
import com.qtimes.service.wonly.client.QtimesServiceManager;
import com.wl.wlflatproject.Bean.BaseBean;
import com.wl.wlflatproject.Bean.CalendarParam;
import com.wl.wlflatproject.Bean.CheckNumBean;
import com.wl.wlflatproject.Bean.ConnectBean;
import com.wl.wlflatproject.Bean.GDFutureWeatherBean;
import com.wl.wlflatproject.Bean.GDNowWeatherBean;
import com.wl.wlflatproject.Bean.MainMsgBean;
import com.wl.wlflatproject.Bean.OpenTvBean;
import com.wl.wlflatproject.Bean.SetMsgBean;
import com.wl.wlflatproject.Bean.StateBean;
import com.wl.wlflatproject.Bean.UpdataJsonBean;
import com.wl.wlflatproject.Bean.UpdateAppBean;
import com.wl.wlflatproject.Bean.WeatherBean;
import com.wl.wlflatproject.MUtils.CodeUtils;
import com.wl.wlflatproject.MUtils.Constants;
import com.wl.wlflatproject.MUtils.DateUtil;
import com.wl.wlflatproject.MUtils.DateUtils;
import com.wl.wlflatproject.MUtils.DeviceUtils;
import com.wl.wlflatproject.MUtils.DpUtils;
import com.wl.wlflatproject.MUtils.GsonUtils;
import com.wl.wlflatproject.MUtils.IntentUtil;
import com.wl.wlflatproject.MUtils.LocationUtils;
import com.wl.wlflatproject.MUtils.LunarUtils;
import com.wl.wlflatproject.MUtils.RbMqUtils;
import com.wl.wlflatproject.MUtils.SPUtil;
import com.wl.wlflatproject.MUtils.SerialPortUtil;
import com.wl.wlflatproject.MUtils.VersionUtils;
import com.wl.wlflatproject.MUtils.YmodleUtils;
import com.wl.wlflatproject.MView.CodeDialog;
import com.wl.wlflatproject.MView.NormalDialog;
import com.wl.wlflatproject.MView.WaitDialogTime;
import com.wl.wlflatproject.Presenter.DevMonitorContract;
import com.wl.wlflatproject.Presenter.DevMonitorPresenter;
import com.wl.wlflatproject.R;
import com.xm.linke.face.FaceFeature;
import com.xm.ui.dialog.XMPromptDlg;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.sir.ymodem.YModem;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, DevMonitorContract.IDevMonitorView {
    public static int checkNum = 0;//人流检测人数
    public static int checkNumRect = 0;//重置人流检测
    public boolean isDbugOpen = false;
    public static boolean newID=true;
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.open)
    LinearLayout open;
    @BindView(R.id.fun_view)
    RelativeLayout funView;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.lock_bt)
    LinearLayout lockBt;
    @BindView(R.id.video_iv)
    LinearLayout videoIv;
    @BindView(R.id.full_screen)
    LinearLayout fullScreen;
    @BindView(R.id.onoff_bt)
    ImageView onoffBt;
    @BindView(R.id.bufang_bt)
    ImageView bufangBt;
    @BindView(R.id.code_bt)
    ImageView codeBt;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.today_weather_view)
    View todayWeatherView;
    @BindView(R.id.today_temp_tv)
    TextView todayTempTv;
    @BindView(R.id.today_weather_tv)
    TextView todayWeatherTv;
    @BindView(R.id.second_day_view)
    View secondDayView;
    @BindView(R.id.second_day_tv)
    TextView secondDayTv;
    @BindView(R.id.third_day_view)
    View thirdDayView;
    @BindView(R.id.third_day_tv)
    TextView thirdDayTv;
    @BindView(R.id.weather_ll)
    LinearLayout weatherLl;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.week_cn_tv)
    TextView weekCnTv;
    @BindView(R.id.week_en_tv)
    TextView weekEnTv;
    @BindView(R.id.changkai)
    TextView changKai;
    @BindView(R.id.calendar_cn_tv)
    TextView calendarCnTv;
    @BindView(R.id.calendar_ll)
    RelativeLayout calendarLl;
    @BindView(R.id.setting)
    TextView setting;
    @BindView(R.id.swtich)
    TextView switchMq;
    @BindView(R.id.today_extent_tv)
    TextView todayExtentTv;
    @BindView(R.id.second_weather_tv)
    TextView secondWeatherTv;
    @BindView(R.id.third_weather_tv)
    TextView thirdWeatherTv;
    @BindView(R.id.today_temp_ll)
    LinearLayout todayTempLl;
    @BindView(R.id.door_select_ll)
    LinearLayout doorSelectLl;
    @BindView(R.id.lock_single)
    LinearLayout lockSingle;
    @BindView(R.id.lock_double)
    LinearLayout lockDouble;
    private int version;
    private NormalDialog normalDialog;
    /* 更新进度条 */
    private ProgressBar mProgress;
    private AlertDialog mDownloadDialog;
    private int fHeight = 0;
    private RbMqUtils rbmq;
    public SerialPortUtil serialPort;
    private StateBean bean = new StateBean();
    private WaitDialogTime dialogTime;
    private CodeDialog codeDialog;
    private int changkaiFlag = 3;
    private String openDegree = "--";//开门角度
    private String openDegreeRepair = "--";//开门角度修复值
    private String openDoorWaitTime = "--";//开门等待时间
    private String openDoorSpeed = "--";//开门速度
    private String closeDoorSpeed = "--";//关门速度
    public static String videoWIfi;
    public static String videOldWIfi;
    private String leftDegreeRepair = "--";//右角度修复值
    private String rightDegreeRepair = "--";//左角度修复值
    private String closePower = "--";//关门力度
    private boolean watherClick = false;
    static String lcddisplay = "/sys/gpio_test_attr/lcd_power";
    File file = new File(lcddisplay);
    Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        dateTv.setText(dateUtils.dateFormat(currentTimeMillis));
                        if (wifiManager == null)
                            wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        String ssid = wifiInfo.getSSID();
                        if (!TextUtils.isEmpty(ssid) && !ssid.equals("<unknown ssid>")) {
                            bean.setTime(currentTimeMillis/ 1000);
                            stateJson = GsonUtils.GsonString(bean);
                            rbmq.pushMsg(id + "#" + stateJson);
                        } else {
                            Toast.makeText(MainActivity.this, "WIFI不可用", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "心跳包报错：" + e.toString(), Toast.LENGTH_SHORT).show();
                    } finally {
                        sendEmptyMessageDelayed(0, 60000);
                    }
                    break;
                case 1:
                    if (isFull)
                        setScreen();
                    devMonitorPresenter.stopMonitor();
                    Log.e("有人离开停止视频", "..");
                    break;
                case 2:
                    if (mDownloadDialog != null) {
                        mDownloadDialog.dismiss();
                        mDownloadDialog = null;
                    }
                    OkGo.getInstance().cancelTag(MainActivity.this);
                    requestPermission();
                    handler.sendEmptyMessageDelayed(2, 24 * 60 * 60 * 1000);
                    break;
                case 3:
                    handler.sendEmptyMessageDelayed(3, 1000 * 3 * 60);
                    break;
                case 4:
                    String s = dateUtils.dateFormat6(System.currentTimeMillis());
                    time.setText(s);
                    int fdCount = getFdCount();
                    Log.e("句柄数量---", fdCount + "");
                    handler.sendEmptyMessageDelayed(4, 1000);
                    break;
                case 5:
                    serialPort.sendDate("+DATATOPAD\r\n".getBytes());
                    break;
                case 6:
                    Log.e("发送摄像头id", "。。。。");
                    serialPort.sendDate("+PublishVideoSn：\r\n".getBytes());
                    serialPort.sendDate("+Publishwifissid：\r\n".getBytes());
                    break;
                case 7:
//                    fHeight = funView.getMeasuredHeight();
                    setScreen();
                    break;
                case 8:
                    num.setText("当前室内人数：" + checkNum + "人");
                    break;
                case 9:
                    setting.setVisibility(View.GONE);
                    break;
                case 10:
                    if (!QtimesServiceManager.instance().isServerActive()) {
                        QtimesServiceManager.instance().connect(MainActivity.this);
                    }
                    handler.sendEmptyMessageDelayed(10, 30 * 60 * 1000);
                    break;
                case 13:
                    hideBottomUIMenu();
                    break;
                case 14:
                    requestPermission();
                    sendEmptyMessageDelayed(14, 3600 * 1000);
                    break;
            }
        }
    };
    private String id;
    private String stateJson;
    private WifiManager wifiManager;
    private NetStatusReceiver receiver;
    private AMapLocation mAMapLocation;
    private long mExitTime;
    private boolean isFull = false;
    private PowerManager.WakeLock wl;
    private FileOutputStream fout;
    private PrintWriter printWriter;
    private DateUtils dateUtils;
    private String msg;
    private int screenWidth;
    private CheckNumBean checkNumBean;
    private QtimesServiceManager.QtimesDoorServiceListener checkListener;
    private int screenHight;
    private DevMonitorPresenter devMonitorPresenter;
    private String mTodayCode = "";
    private String mSecondCode = "";
    private String mThirdCode = "";
    private YmodleUtils ymodleUtils;
    private ExecutorService threads;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        setMq();
        initSerialPort();
        initCalendar();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        hideBottomUIMenu();
    }

    private void initData() {
        threads = Executors.newFixedThreadPool(1);
        devMonitorPresenter = new DevMonitorPresenter(this, bg, funView, time);
        devMonitorPresenter.setChannelId(0);
        normalDialog = new NormalDialog(this, R.style.mDialog);
        int select = SPUtil.getInstance(this).getSettingParam("doorSelect", 0);
        if (select == 1) {//母门
            doorSelectLl.setVisibility(View.VISIBLE);
            videoIv.setVisibility(View.GONE);
            lockBt.setVisibility(View.GONE);
            lockSingle.setVisibility(View.VISIBLE);
            lockDouble.setVisibility(View.VISIBLE);
            SPUtil.getInstance(this).setSettingParam("doorSelect", 1);
        } else if (select == 2) {//子门
            doorSelectLl.setVisibility(View.GONE);
            SPUtil.getInstance(this).setSettingParam("doorSelect", 2);
        } else if (select == 0) {//随心门
            doorSelectLl.setVisibility(View.VISIBLE);
            videoIv.setVisibility(View.VISIBLE);
            lockBt.setVisibility(View.VISIBLE);
            lockSingle.setVisibility(View.GONE);
            lockDouble.setVisibility(View.GONE);

            SPUtil.getInstance(this).setSettingParam("doorSelect", 0);
        }


        fHeight = DpUtils.dip2px(this, 300);
        setScreen();
        checkNum = SPUtil.getInstance(this).getSettingParam("checkNum", 0);
        SPUtil instance = SPUtil.getInstance(this);
        if (instance.getSettingParam("open", false)) {
            open.setVisibility(View.VISIBLE);
            handler.sendEmptyMessageDelayed(8, 1000);
        }
        WindowManager windowManager = getWindowManager();
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHight = windowManager.getDefaultDisplay().getHeight();
        EventBus.getDefault().register(this);

        dateUtils = DateUtils.getInstance();
        handler.removeMessages(2);
        handler.removeMessages(3);
        handler.removeMessages(4);
        if (dialogTime == null)
            dialogTime = new WaitDialogTime(this, android.R.style.Theme_Translucent_NoTitleBar);
        requestPermission();
        //生成id
        if(newID){
            id = CodeUtils.getMacAddr();
        }else{
            id = DeviceUtils.getSerialNumber(this);
        }
        Log.e("获得Mac地址", id + "");
        rbmq = new RbMqUtils();
        bean.setAck(0);
        bean.setCmd(0x46);
        bean.setDevType("WL025S1");
        bean.setDevId(id);
        bean.setSeqId(1);
        bean.setTime(System.currentTimeMillis() / 1000);
        bean.setVendor("general");
        stateJson = GsonUtils.GsonString(bean);
        receiver = new NetStatusReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, intentFilter);
        codeBt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (setting.getVisibility() == View.VISIBLE) {
                    setting.setVisibility(View.GONE);
                } else {
                    setting.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(9, 5000);
                }
                return true;
            }
        });
        codeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog.show();
                handler.sendEmptyMessageDelayed(13, 500);
            }
        });
        handler.sendEmptyMessageDelayed(2, 24 * 60 * 60 * 1000);
        handler.sendEmptyMessageDelayed(3, 1000 * 3 * 60);
        handler.sendEmptyMessage(4);
        handler.sendEmptyMessageDelayed(6, 1000);
        handler.sendEmptyMessageDelayed(14, 3600 * 1000 * 2);
        handler.sendEmptyMessageDelayed(10, 10000);
        codeDialog = new CodeDialog(MainActivity.this, R.style.ActionSheetDialogStyle);
    }


    @OnClick({R.id.lock_single, R.id.lock_double, R.id.swtich, R.id.changkai, R.id.setting, R.id.lock_bt, R.id.onoff_bt,
            R.id.bufang_bt, R.id.fun_view,
            R.id.weather_ll, R.id.calendar_ll, R.id.video_iv})
    public void onViewClicked(View view) {
        handler.removeMessages(3);
        handler.sendEmptyMessageDelayed(3, 1000 * 3 * 60);
        switch (view.getId()) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity1.class);
                intent.putExtra("openDegree", openDegree);
                intent.putExtra("openDoorWaitTime", openDoorWaitTime);
                intent.putExtra("openDoorSpeed", openDoorSpeed);
                intent.putExtra("closeDoorSpeed", closeDoorSpeed);
                intent.putExtra("leftDegreeRepair", leftDegreeRepair);
                intent.putExtra("rightDegreeRepair", rightDegreeRepair);
                intent.putExtra("closePower", closePower);
                intent.putExtra("openDegreeRepair", openDegreeRepair);
                startActivity(intent);
                break;
            case R.id.swtich:
                if (RbMqUtils.MQIP.equals("rmq.wonlycloud.com")) {
                    RbMqUtils.MQIP = "116.62.46.10";
                    rbmq.setUpConnectionFactory();
                    rbmq.mClose(true);
                    switchMq.setText("当前-->测试环境");
                } else {
                    RbMqUtils.MQIP = "rmq.wonlycloud.com";
                    rbmq.setUpConnectionFactory();
                    rbmq.mClose(true);
                    switchMq.setText("当前-->正式环境");
                }
                break;
            case R.id.fun_view:
                if (!isFull) {
                    setFullScreen();
                } else {
                    setScreen();
                }
                break;
            case R.id.lock_bt://开门
                dialogTime.show();
                serialPort.sendDate("+COPEN:1\r\n".getBytes());
                handler.sendEmptyMessageDelayed(13, 500);
                break;
            case R.id.lock_single://单开
                dialogTime.show();
                serialPort.sendDate("+CINOPEN:1\r\n".getBytes());
                handler.sendEmptyMessageDelayed(13, 500);
                break;
            case R.id.lock_double://双开
                dialogTime.show();
                serialPort.sendDate("+CINOPEN:2\r\n".getBytes());
                handler.sendEmptyMessageDelayed(13, 500);
                break;
            case R.id.video_iv:
                if (!(devMonitorPresenter.getPlayState() == 0)) {
                    handler.removeMessages(1);
                    handler.sendEmptyMessageDelayed(1, 60000);
                    if (devMonitorPresenter.getVideoUuid() == null) {
                        Toast.makeText(MainActivity.this, "未检测到摄像头（如果有摄像头请尝试通过王力智能客户端配置WIFI）", Toast.LENGTH_SHORT).show();
                        handler.sendEmptyMessageDelayed(6, 0);
                    } else {
                        devMonitorPresenter.setScreen(true);
                        devMonitorPresenter.startMonitor();
                    }
                    handler.sendEmptyMessageDelayed(13, 500);
                } else {
                    devMonitorPresenter.stopMonitor();
                }

                break;
            case R.id.changkai:
                if (changkaiFlag == 1) {
                    dialogTime.show();
                    serialPort.sendDate("+ALWAYSOPEN\r\n".getBytes());
                } else if (changkaiFlag == 2) {
                    dialogTime.show();
                    serialPort.sendDate("+CLOSEALWAYSOPEN\r\n".getBytes());
                }
                handler.sendEmptyMessageDelayed(13, 500);
                break;
            case R.id.onoff_bt:
                break;
            case R.id.bufang_bt:
                break;
            case R.id.weather_ll:
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    watherClick = true;
                    mLocationUtils.startLocation();

                } else {
                    Toast.makeText(this, "WiFi不可用或已断开", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.calendar_ll:
                //日历
                if (mAMapLocation == null) {
                    Toast.makeText(this, "数据获取中...", Toast.LENGTH_SHORT).show();
                    return;
                }
                CalendarParam calendarParam = new CalendarParam();
                calendarParam.temp = todayTempTv.getText().toString();
                calendarParam.weather = todayWeatherTv.getText().toString();
                String city = mAMapLocation.getCity();
                String district = mAMapLocation.getDistrict();
                calendarParam.location = district == null ? city : district;
                CalendarActivity.start(this, calendarParam);
                break;
            default:
        }
    }

    /**
     * 服务器socket
     */
    public void setMq() {
        //发送端
        rbmq.publishToAMPQ("");
        //接收端
        String s = id + "_robot";
        rbmq.subscribe(s);
        rbmq.setUpConnectionFactory();
        rbmq.setRbMsgListener(new RbMqUtils.OnRbMsgListener() {
            @Override
            public void AcceptMsg(String msg) {//服务器返回数据
                Log.e("服务器发给平板---", msg);
                BaseBean baseBean = null;
                try {
                    baseBean = GsonUtils.GsonToBean(msg, BaseBean.class);
                } catch (Exception e) {

                }

                if (baseBean != null) {
                    switch (baseBean.getCmd()) {
                        case 0x1001://通知小管家
                            OpenTvBean openTvBean = GsonUtils.GsonToBean(msg, OpenTvBean.class);
                            if (openTvBean.getAct() == 1) {
                                Log.e("小管家开视频---", s);
                                handler.removeMessages(1);
                                handler.removeMessages(3);
                                handler.sendEmptyMessageDelayed(3, 1000 * 3 * 60);
                                if (devMonitorPresenter.getVideoUuid() == null) {
                                    Toast.makeText(MainActivity.this, "请检查摄像头是否配置wifi", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!(devMonitorPresenter.getPlayState() != 0)) {
                                        devMonitorPresenter.setScreen(true);
                                        devMonitorPresenter.startMonitor();
                                    }
                                }
                            }
                            break;
//                        case 0x1101://重置人流检测
//                            ConnectBean connectBean = GsonUtils.GsonToBean(msg, ConnectBean.class);
//                            if (connectBean.getAck() == 1) {
//                                checkNumRect = 0;
//                                checkNum = connectBean.getResetNum();
//
//                                SPUtil.getInstance(MainActivity.this).setSettingParam("checkNumRect", checkNumRect);
//                                SPUtil.getInstance(MainActivity.this).setSettingParam("checkNum", checkNum);
//
//                                num.setText("当前室内人数：" + connectBean.getResetNum() + "人");
//                                open.setVisibility(View.VISIBLE);
//                                Toast.makeText(MainActivity.this, "重置成功", Toast.LENGTH_LONG);
//                                EventBus.getDefault().post(new ConnectBean());
//                            } else {
//                                Toast.makeText(MainActivity.this, "重置失败", Toast.LENGTH_LONG);
//                            }
//                            break;
//                        case 0x1100://人数变动通知
//                            CheckNumBean checkNumBean = GsonUtils.GsonToBean(msg, CheckNumBean.class);
//                            if (checkNumBean.getAck() == 1) {
//                                num.setText("当前室内人数：" + checkNumBean.getTotalNum() + "人");
//                                checkNum = +checkNumBean.getTotalNum();
//                                SPUtil.getInstance(MainActivity.this).setSettingParam("checkNum", checkNum);
//                            }
//                            break;
                    }
                } else {
                    String s = "+MIPLWRITE:" + msg.length() + "," + msg + "\r\n";
                    serialPort.sendDate(s.getBytes());
                }
            }
        });
        handler.removeMessages(0);

        handler.sendEmptyMessageDelayed(0, 10000);
    }

    /**
     * 串口socket
     */
    private void initSerialPort() {
        serialPort = SerialPortUtil.getInstance();
        handler.sendEmptyMessageDelayed(5, 2000);
        serialPort.readCode(new SerialPortUtil.DataListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void getData(String data) {//串口返回数据
                runOnUiThread(new Runnable() {

                    private SetMsgBean setMsgBean;

                    @Override
                    public void run() {
                        if (data.contains("AT+CDOOR=")) {
                            String[] split = data.split("=");
                            switch (split[1]) {
                                case "1"://表示故障1
                                    Toast.makeText(MainActivity.this, "故障1", Toast.LENGTH_SHORT).show();
                                    break;
                                case "2"://表示故障2
                                    Toast.makeText(MainActivity.this, "故障2", Toast.LENGTH_SHORT).show();
                                    break;
                                case "C"://表示故障3
                                    Toast.makeText(MainActivity.this, "故障3", Toast.LENGTH_SHORT).show();
                                    break;
                                case "D"://表示故障4
                                    Toast.makeText(MainActivity.this, "故障4", Toast.LENGTH_SHORT).show();
                                    break;
                                case "A"://表示报警1
                                    Toast.makeText(MainActivity.this, "报警1", Toast.LENGTH_SHORT).show();
                                    break;
                                case "B"://表示报警2
                                    Toast.makeText(MainActivity.this, "报警2", Toast.LENGTH_SHORT).show();
                                    break;
                                case "E"://表示假锁状态
                                    Toast.makeText(MainActivity.this, "假锁", Toast.LENGTH_SHORT).show();
                                    break;
                                case "8"://8表示开门成功
                                    if (isDbugOpen) {
                                        isDbugOpen = false;
                                        if (setMsgBean == null)
                                            setMsgBean = new SetMsgBean();
                                        setMsgBean.setFlag(7);
                                        EventBus.getDefault().post(bean);
                                    }
                                    //mq  通知小管家开门成功  并且关闭视频
                                    OpenTvBean bean = new OpenTvBean();
                                    bean.setCmd(0x1009);
                                    bean.setAck(0);
                                    bean.setDevType("WL025S1");
                                    bean.setDevid(id);
                                    bean.setVendor("general");
                                    bean.setSeqid(1);
                                    rbmq.pushMsg(id + "#" + GsonUtils.GsonString(bean));
//                                    Toast.makeText(MainActivity.this, "开门成功", Toast.LENGTH_SHORT).show();
                                    if (dialogTime != null & dialogTime.isShowing())
                                        dialogTime.dismiss();
                                    break;
                                case "9"://表示关门成功
//                                    Toast.makeText(MainActivity.this, "关门成功", Toast.LENGTH_SHORT).show();
                                    if (dialogTime != null & dialogTime.isShowing())
                                        dialogTime.dismiss();
                            }
                        } else if (data.contains("AT+DEFAULT=")) {
                            String[] s = data.split("=");
                            String[] split = s[1].split(",");
                            switch (Integer.parseInt(split[0])) {
                                case 1://代表开门角度
                                    openDegree = split[1];
                                    break;
                                case 2://开门等待时间
                                    openDoorWaitTime = split[1];
                                    break;
                                case 3://开门速度
                                    openDoorSpeed = split[1];
                                    break;
                                case 4://关门速度
                                    closeDoorSpeed = split[1];
                                    break;
                                case 5://右角度修复值
                                    rightDegreeRepair = split[1];
                                    break;
                                case 6://左角度修复值
                                    leftDegreeRepair = split[1];
                                    break;
                                case 7://已经常开
                                    if (changkaiFlag != 2) {
                                        changkaiFlag = 2;
                                        changKai.setText("取消常开");
                                        changKai.setBackgroundResource(R.drawable.lock);
                                        if (QtimesServiceManager.instance().isServerActive()) {
                                            QtimesServiceManager.instance().setLongOpenState(true);
                                        }
                                    }
                                    break;
                                case 8://没有开启常开
                                    if (changkaiFlag != 1) {
                                        changKai.setText("常开");
                                        changkaiFlag = 1;
                                        changKai.setBackgroundResource(R.drawable.video);
                                        if (QtimesServiceManager.instance().isServerActive()) {
                                            QtimesServiceManager.instance().setLongOpenState(false);
                                        }
                                    }
                                    break;
                                case 9://关门力度
                                    closePower = split[1];
                                    break;
                                case 10://开门角度修复值
                                    openDegreeRepair = split[1];
                                    break;
                            }
                        } else if (data.contains("AT+LEFTANGLEREPAIR=1")) { //左角度修复值
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            setMsgBean.setFlag(1);
                            if (!TextUtils.isEmpty(msg))
                                leftDegreeRepair = msg;
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+RIGHTANGLEREPAIR=1")) {//右角度修复值
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                rightDegreeRepair = msg;
                            setMsgBean.setFlag(2);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+ANGLEREPAIR=1")) {//开门角度修复值
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                openDegreeRepair = msg;
                            setMsgBean.setFlag(12);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+OPENANGLE=1")) {//开门角度
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                openDegree = msg;
                            setMsgBean.setFlag(3);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+OPENWAITTIME=1")) {//等待时间
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                openDoorWaitTime = msg;
                            setMsgBean.setFlag(4);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+OPENSPEED=1")) {//开门速度
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                openDoorSpeed = msg;
                            setMsgBean.setFlag(5);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+CLOSESPEED=1")) {//关门速度AT+ALWAYSOPEN=1
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                closeDoorSpeed = msg;
                            setMsgBean.setFlag(6);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+ALWAYSOPEN=1")) {//常开
                            changkaiFlag = 2;
                            if (dialogTime != null & dialogTime.isShowing()) ;
                            dialogTime.dismiss();
                            changKai.setText("取消常开");
                            changKai.setBackgroundResource(R.drawable.lock);
                            if (QtimesServiceManager.instance().isServerActive()) {
                                QtimesServiceManager.instance().setLongOpenState(true);
                            }
                        } else if (data.contains("AT+CLOSEALWAYSOPEN=1")) {//取消常开
                            changkaiFlag = 1;
                            if (dialogTime != null & dialogTime.isShowing()) ;
                            dialogTime.dismiss();
                            changKai.setText("常开");
                            changKai.setBackgroundResource(R.drawable.video);
                            if (QtimesServiceManager.instance().isServerActive()) {
                                QtimesServiceManager.instance().setLongOpenState(false);
                            }
                        } else if (data.contains("AT+CDWAKE=1")) {    //有人   但是不打开视频
                        } else if (data.contains("AT+CDBELL=1")) {   //门铃
                            handler.removeMessages(1);
                            handler.sendEmptyMessageDelayed(1, 20000);
                            Log.e("有人按门铃", "..");
                            handler.removeMessages(3);
                            handler.sendEmptyMessageDelayed(3, 1000 * 30);
                            if (devMonitorPresenter.getVideoUuid() == null) {
                                Toast.makeText(MainActivity.this, "请检查摄像头是否配置wifi", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!(devMonitorPresenter.getPlayState() != 0)) {
                                    devMonitorPresenter.setScreen(true);
                                    devMonitorPresenter.startMonitor();
                                }
                            }
                        } else if (data.contains("AT+CLOSESTRENGTH=1")) {         //关门力度
                            if (setMsgBean == null)
                                setMsgBean = new SetMsgBean();
                            if (!TextUtils.isEmpty(msg))
                                openDoorSpeed = msg;
                            setMsgBean.setFlag(8);
                            EventBus.getDefault().post(setMsgBean);
                        } else if (data.contains("AT+CDECT=")) {
                            String[] split = data.split("=");
                            String[] split1 = split[1].split(",");
                            switch (split1[0]) {
                                case "0"://表示前板检测到遮挡  门外
                                    if (split1[1].equals("0")) {//人离开
                                        handler.sendEmptyMessageDelayed(1, 20000);
                                        Log.e("" +
                                                "", "..");
                                    } else {//人靠近
                                        handler.removeMessages(1);
                                        Log.e("检测有人", "..");
                                        handler.removeMessages(3);
                                        handler.sendEmptyMessageDelayed(3, 1000 * 30);
                                        if (devMonitorPresenter.getVideoUuid() == null) {
                                            Toast.makeText(MainActivity.this, "请检查摄像头是否配置wifi", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (!(devMonitorPresenter.getPlayState() == 0)) {
                                                devMonitorPresenter.setScreen(true);
                                                devMonitorPresenter.startMonitor();
                                            }
                                        }
                                    }
                                    break;
                                case "1"://表示后板检测到遮挡 门内

                                    break;
                            }
                        } else if (data.contains("AT+MIPLNOTIFY=")) {//加密消息上报给服务器
                            String[] split = data.split(",");
                            String s = split[split.length - 1];
                            String[] split1 = s.split("\r\n");
                            String s1 = id + "#" + split1[0];
                            rbmq.pushMsg(s1);
//                            handler.removeMessages(0);
//                            handler.sendEmptyMessageDelayed(0, 10000);
                        } else if (data.contains("AT+CGSN=1")) {//上传id
                            serialPort.sendDate(("+CGSN:" + id + "\r\n").getBytes());
                        } else if (data.contains("AT+CGATT?")) {//服务器是否链接
                            serialPort.sendDate((rbmq.isConnection() + "\r\n").getBytes());
                        } else if (data.contains("AT+CCLK?")) {//上报时间
                            serialPort.sendDate(("+CCLK:" + System.currentTimeMillis() + "\r\n").getBytes());
                        } else if (data.contains("AT+VIDEOSN=")) { //设置摄像头id
                            Log.e("收到摄像头id", "----" + data);
                            try {
                                String[] split = data.split("=");
                                if (split.length > 1) {
                                    devMonitorPresenter.setVideoUuid(split[1]);
                                    devMonitorPresenter.initMonitor(funView);
                                }
                            } catch (Exception e) {

                            }
                        } else if (data.contains("AT+WIFISSID=")) { //设置摄像头wifi
                            Log.e("收到摄像头wifi", "----" + data);
                            try {
                                String[] split = data.split("=");
                                videOldWIfi = videoWIfi;
                                if (split.length > 1) {
                                    videoWIfi = split[1];
                                }
                            } catch (Exception e) {

                            }
                        } else if (data.contains("AT+PEOPLECHECK=")) {
                            String[] split = data.split("=");
                            switch (split[1]) {
                                case "1"://毫米波雷达  进去一个人
//                                    checkNum = +checkNumBean.getTotalNum();
//                                    num.setText("当前室内人数：" + checkNumBean.getTotalNum() + "人");
                                    checkNum = checkNum + 1;
                                    num.setText("当前室内人数：" + checkNum + "人");
                                    SPUtil.getInstance(MainActivity.this).setSettingParam("checkNum", checkNum);
                                    break;
                                case "2"://毫米波雷达  出去一个人
                                    checkNum = checkNum - 1;
                                    if (checkNum < 0) {
                                        checkNum = 0;
                                    }
                                    num.setText("当前室内人数：" + checkNum + "人");
                                    SPUtil.getInstance(MainActivity.this).setSettingParam("checkNum", checkNum);
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }


//    public void sendCheckNum(int i) {
//        if (i == 0) {
//            checkNumRect = checkNumRect - 1;
//        } else {
//            checkNumRect = checkNumRect + 1;
//        }
//        SPUtil.getInstance(MainActivity.this).setSettingParam("checkNumRect", checkNumRect);
//        if (checkNumBean == null) {
//            checkNumBean = new CheckNumBean();
//            checkNumBean.setCmd(0x1100);
//            checkNumBean.setAck(0);
//            checkNumBean.setDevType("WL025S1");
//            checkNumBean.setDevid(DeviceUtils.getSerialNumber(MainActivity.this));
//            checkNumBean.setVendor("general");
//            checkNumBean.setSeqid(1);
//        }
//        checkNumBean.setLockNum(checkNumRect);
//        checkNumBean.setTotalNum(0);
//        long l = System.currentTimeMillis() / 1000;
//        checkNumBean.setTime(l);
//        rbmq.pushMsg(DeviceUtils.getSerialNumber(MainActivity.this) + "#" + GsonUtils.GsonString(checkNumBean));
//    }


    //---------------------eventBus----------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainMsgBean msgBean) {
        msg = msgBean.getMsg();
        int flag = msgBean.getFlag();
        switch (flag) {
            case 1://左角度修复值
                serialPort.sendDate(("+LEFTANGLEREPAIR:" + msg + "\r\n").getBytes());
                break;
            case 2://右角度修复值
                serialPort.sendDate(("+RIGHTANGLEREPAIR:" + msg + "\r\n").getBytes());
                break;
            case 3://开门角度
                serialPort.sendDate(("+OPENANGLE:" + msg + "\r\n").getBytes());
                break;
            case 4://等待时间
                serialPort.sendDate(("+OPENWAITTIME:" + msg + "\r\n").getBytes());
                break;
            case 5://开门速度
                serialPort.sendDate(("+OPENSPEED:" + msg + "\r\n").getBytes());
                break;
            case 6://关门速度
                serialPort.sendDate(("+CLOSESPEED:" + msg + "\r\n").getBytes());
                break;
            case 7://开门
                isDbugOpen = true;
                serialPort.sendDate("+COPEN:1\r\n".getBytes());
                break;
            case 8://关门力度
                switch (msg) {
                    case "减速一档":
                        serialPort.sendDate("+CLOSESTRENGTH:9\r\n".getBytes());
                        break;
                    case "减速二档":
                        serialPort.sendDate("+CLOSESTRENGTH:8\r\n".getBytes());
                        break;
                    case "减速三档":
                        serialPort.sendDate("+CLOSESTRENGTH:7\r\n".getBytes());
                        break;
                    case "减速关闭":
                        serialPort.sendDate("+CLOSESTRENGTH:0\r\n".getBytes());
                        break;
                }
                break;
            case 9://开启人流检测
//                if (!QtimesServiceManager.instance().isServerActive()) {
//                    QtimesServiceManager.instance().connect(this);
//                }
//                QtimesServiceManager.instance().setListener(checkListener);
                open.setVisibility(View.VISIBLE);
                num.setText("当前室内人数：" + checkNum + "人");
                break;
            case 10://设置人数
//                rbmq.pushMsg(DeviceUtils.getSerialNumber(this) + "#" + msgBean.getMsg());
                checkNum = msgBean.getNum();
                handler.sendEmptyMessage(8);
                SPUtil.getInstance(this).setSettingParam("checkNum", checkNum);
                break;
            case 11://关闭人流检测
                open.setVisibility(View.GONE);
                break;
            case 12://关闭人流检测
                serialPort.sendDate(("+ANGLEREPAIR:" + msg + "\r\n").getBytes());
                break;
            case 21://设置门类型
                if (msg.equals("1")) {//母门
                    doorSelectLl.setVisibility(View.VISIBLE);
                    videoIv.setVisibility(View.GONE);
                    lockBt.setVisibility(View.GONE);
                    lockSingle.setVisibility(View.VISIBLE);
                    lockDouble.setVisibility(View.VISIBLE);
                    SPUtil.getInstance(this).setSettingParam("doorSelect", 1);
                } else if (msg.equals("2")) {//子门
                    doorSelectLl.setVisibility(View.GONE);
                    SPUtil.getInstance(this).setSettingParam("doorSelect", 2);
                } else if (msg.equals("0")) {//随心门
                    doorSelectLl.setVisibility(View.VISIBLE);
                    videoIv.setVisibility(View.VISIBLE);
                    lockBt.setVisibility(View.VISIBLE);
                    lockSingle.setVisibility(View.GONE);
                    lockDouble.setVisibility(View.GONE);

                    SPUtil.getInstance(this).setSettingParam("doorSelect", 0);
                }
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT);
                break;
        }
    }

    /**
     * 初始化日历
     */
    private void initCalendar() {
        DateUtils instance = DateUtils.getInstance();
        //日期
        dateTv.setText(instance.dateFormat(System.currentTimeMillis()));
        //星期
        weekCnTv.setText(instance.getWeekday(System.currentTimeMillis(), true));
        weekEnTv.setText(instance.getWeekday(System.currentTimeMillis(), false));
        //农历
        //猪 贰零壹玖 润 六月 小廿八 己亥 辛未 戊辰
        Calendar calendar = Calendar.getInstance();
        String[] lunar = LunarUtils.getLunar(
                calendar.get(Calendar.YEAR) + "",
                (calendar.get(Calendar.MONTH) + 1) + "",
                calendar.get(Calendar.DAY_OF_MONTH) + "");
        calendarCnTv.setText(String.format("农历-%s月-%s", lunar[3], lunar[4]));
    }

//    /**
//     * 注册时间广播
//     */
//    private void registerTimeReceiver() {
//        mTimeReceiver = new TimeReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Intent.ACTION_TIME_TICK);
//        registerReceiver(mTimeReceiver, filter);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationUtils.startLocation();
        hideBottomUIMenu();
//        handler.sendEmptyMessageDelayed(7, 2000);/
//        setNavigationBar(this,false);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLocationUtils.stopLocation();
    }

    @Override
    protected void onDestroy() {
        try {
            if (fout != null) {
                fout.close();
                fout = null;
            }
            if (printWriter != null) {
                printWriter.close();
                printWriter = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rbmq.flag = false;
        rbmq.mClose(false);
        handler.removeMessages(0);
        handler.removeMessages(1);
        handler.removeMessages(2);
        handler.removeMessages(3);
        handler.removeMessages(4);
        serialPort.close();
        serialPort.flag = false;
        devMonitorPresenter.stopMonitor();
        mLocationUtils.destroyLocationClient();
        unregisterReceiver(receiver);
//        if(mTimeReceiver!=null)
//        unregisterReceiver(mTimeReceiver);
        wl.release();
        EventBus.getDefault().unregister(this);
        try {
            QtimesServiceManager.instance().disconnect(this);
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    public class NetStatusReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    mLocationUtils.startLocation();
//                    handler.removeMessages(0);
//                    handler.sendEmptyMessageDelayed(0, 10000);
                    rbmq.clearQueue();
                    Log.d("hsl666", "onReceive: ==可用");
                    initCalendar();
                } else {
                    Toast.makeText(context, "WiFi不可用或已断开", Toast.LENGTH_SHORT).show();
                    Log.d("hsl666", "onReceive: ==不可用");
                }
//
            }
        }
    }

    /**
     * 时间更新的广播
     */
    public class TimeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                DateUtils dateUtils = DateUtils.getInstance();
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour == 0) {
                    //日期
                    dateTv.setText(dateUtils.dateFormat(System.currentTimeMillis()));
                    //星期
                    weekCnTv.setText(dateUtils.getWeekday(System.currentTimeMillis(), true));
                    weekEnTv.setText(dateUtils.getWeekday(System.currentTimeMillis(), false));
                    //农历
                    //农历
                    //猪 贰零壹玖 润 六月 小廿八 己亥 辛未 戊辰
                    String[] lunar = LunarUtils.getLunar(
                            calendar.get(Calendar.YEAR) + "",
                            (calendar.get(Calendar.MONTH) + 1) + "",
                            calendar.get(Calendar.DAY_OF_MONTH) + "");
                    calendarCnTv.setText(String.format("农历-%s月-%s", lunar[3], lunar[4]));
                }
            }
        }
    }

    /**
     * 定位工具类
     */
    private LocationUtils mLocationUtils = new LocationUtils() {

        @Override
        protected void onQueryNowWeatherResult(GDNowWeatherBean.LivesBean livesBean, AMapLocation aMapLocation) {
            mAMapLocation = aMapLocation;
            //地址
            StringBuffer locationBuffer = new StringBuffer();
            String city = aMapLocation.getCity();
            String district = aMapLocation.getDistrict();
            locationBuffer.append(city);
            if (district != null) {
                locationBuffer.append(district);
            }
            locationTv.setText(locationBuffer);
            //今日天气
            //ICON Text
            mTodayCode = setWeatherIcon(todayWeatherView, livesBean.getWeather());
            //实时时间
            String dayOrMonthOrYear = DateUtils.getInstance().getDayOrMonthOrYear(System.currentTimeMillis());
            setWeatherText(todayWeatherTv, livesBean.getWeather(), dayOrMonthOrYear, false);
            //体感温度
            todayTempTv.setText(livesBean.getTemperature());
            if (watherClick) {
                watherClick = false;
                WeatherBean bean = new WeatherBean();
                bean.setCmd(0x1002);
                bean.setAck(0);
                bean.setDevType("WonlyRangeHood");
                bean.setDevid(id);
                bean.setVendor("general");
                bean.setSeqid(1);
                bean.setAddress(locationBuffer.toString());
                bean.setWeather(livesBean.getWeather());
                bean.setHumidity(livesBean.getHumidity());
                bean.setTemperature(livesBean.getTemperature());
                long timeStamp = dateUtils.date2TimeStamp(livesBean.getReporttime(), "yyyy-MM-dd HH:mm:ss");
                bean.setTime((int) (timeStamp / 1000));
                rbmq.pushMsg(id + "#" + GsonUtils.GsonString(bean));


                Bundle bundle = new Bundle();
                bundle.putString("param", locationBuffer.toString());
                bundle.putString("param1", livesBean.getTemperature());
                bundle.putString("param2", todayWeatherTv.getText().toString());
                bundle.putString("param3", todayExtentTv.getText().toString());
                bundle.putString("param4", secondWeatherTv.getText().toString());
                bundle.putString("param5", secondDayTv.getText().toString());
                bundle.putString("param6", thirdWeatherTv.getText().toString());
                bundle.putString("param7", thirdDayTv.getText().toString());
                bundle.putString("param8", mTodayCode);
                bundle.putString("param9", mSecondCode);
                bundle.putString("param10", mThirdCode);
                Intent intent = new Intent(MainActivity.this, WeatherActivity1.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        }

        @Override
        protected void onQueryFutureWeatherResult(GDFutureWeatherBean.ForecastsBean forecastsBean, AMapLocation aMapLocation) {
            List<GDFutureWeatherBean.ForecastsBean.CastsBean> beanCasts = forecastsBean.getCasts();
            DateUtils dateUtils = DateUtils.getInstance();
            boolean night = dateUtils.isNight();
            //当前天气
            GDFutureWeatherBean.ForecastsBean.CastsBean todayWeather = beanCasts.get(0);
            todayExtentTv.setText(todayWeather.getDaytemp() + "°" + " /  " + todayWeather.getNighttemp() + "°");

            //后两天天气
            GDFutureWeatherBean.ForecastsBean.CastsBean secondWeather = beanCasts.get(1);
            mSecondCode = setWeatherIcon(secondDayView, night ? secondWeather.getNightweather() : secondWeather.getDayweather());
            setWeatherText(secondDayTv,
                    secondWeatherTv,
                    "明天",
                    night ? secondWeather.getNightweather() : secondWeather.getDayweather(),
                    secondWeather.getDaytemp(),
                    secondWeather.getNighttemp());
            GDFutureWeatherBean.ForecastsBean.CastsBean thirdWeather = beanCasts.get(2);
            mThirdCode = setWeatherIcon(thirdDayView, night ? thirdWeather.getNightweather() : thirdWeather.getDayweather());
            setWeatherText(thirdDayTv,
                    thirdWeatherTv,
                    "后天",
                    night ? thirdWeather.getNightweather() : thirdWeather.getDayweather(),
                    thirdWeather.getDaytemp(),
                    thirdWeather.getNighttemp());
        }
    };

    /**
     * 设置天气ICON
     *
     * @param view
     * @param weather
     */
    private String setWeatherIcon(View view, String weather) {
        String code = LocationUtils.weatherCode(weather);
        switch (code) {
            case "1":
                view.setBackgroundResource(R.drawable.sun_icon);
                break;
            case "2":
                view.setBackgroundResource(R.drawable.cloud_icon);
                break;
            case "3":
                view.setBackgroundResource(R.drawable.rain_icon);
                break;
            case "4":
                view.setBackgroundResource(R.drawable.snow_icon);
                break;
            default:
        }
        return code;
    }

    /**
     * 设置天气文本
     *
     * @param tv
     * @param weather
     * @param showDate
     */
    private void setWeatherText(TextView tv, String weather, String date, boolean showDate) {
        String code = LocationUtils.weatherCode(weather);
        StringBuffer content = new StringBuffer();
        if (showDate) {
            String format11 = DateUtils.getInstance().dateFormat11(date);
            content.append(format11 + " ");
        }
        switch (code) {
            case "1":
                content.append("晴");
                break;
            case "2":
                content.append("阴");
                break;
            case "3":
                content.append("雨");
                break;
            case "4":
                content.append("雪");
                break;
            default:
        }
        tv.setText(content.toString());
    }

    /**
     * 设置天气文本
     *
     * @param tv
     * @param date
     * @param weather
     * @param dayTemp
     * @param nightTemp
     */
    private void setWeatherText(TextView tv, TextView tvW, String date, String weather, String dayTemp, String nightTemp) {
        String code = LocationUtils.weatherCode(weather);
        String w = "晴";
        switch (code) {
            case "1":
                w = "晴";
                break;
            case "2":
                w = "阴";
                break;
            case "3":
                w = "雨";
                break;
            case "4":
                w = "雪";
                break;
            default:
        }
        tvW.setText(date + " · " + w);
        tv.setText(dayTemp + "°" + " /  " + nightTemp + "°");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }

        return true;
    }


    public void setFullScreen() {
        hideBottomUIMenu();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.setLayoutParams(layoutParams);
        funView.setLayoutParams(layoutParams1);
        isFull = true;
    }

    public void setScreen() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, fHeight);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, fHeight);
        layoutParams1.rightMargin = screenWidth / 4;
        layoutParams1.leftMargin = screenWidth / 4;
        rl.setLayoutParams(layoutParams);
        funView.setLayoutParams(layoutParams1);
        isFull = false;
    }


    private void requestPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        checkUpdate();
                        if (mLocationUtils != null) {
                            mLocationUtils.startLocation();
                        }
                        initCalendar();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        checkUpdate();
                    }
                })
                .start();
    }

    protected void checkUpdate() {
        version = VersionUtils.getVersionCode(this);
        requestAppUpdate(version, new DataRequestListener<UpdateAppBean>() {
            @Override
            public void success(UpdateAppBean data) {
                downloadApp(data.getPUS().getBody().getUrl());
            }

            @Override
            public void fail(String msg) {

            }
        });

//        requestFileUpdate(version);
    }

    /**
     * apk升级
     *
     * @param version
     * @param listener
     */
    private void requestAppUpdate(int version, final DataRequestListener<UpdateAppBean> listener) {
        UpdataJsonBean updataJsonBean = new UpdataJsonBean();
        UpdataJsonBean.PUSBean pusBean = new UpdataJsonBean.PUSBean();
        UpdataJsonBean.PUSBean.BodyBean bodyBean = new UpdataJsonBean.PUSBean.BodyBean();
        UpdataJsonBean.PUSBean.HeaderBean headerBean = new UpdataJsonBean.PUSBean.HeaderBean();

        bodyBean.setToken("");
        bodyBean.setVendor_name("general");
        bodyBean.setPlatform("android");
        if(newID){
            bodyBean.setEndpoint_type("WL025S1-H-N");
        }else{
            bodyBean.setEndpoint_type("WL025S1-H");
        }
        bodyBean.setCurrent_version(version + "");

        headerBean.setApi_version("1.0");
        headerBean.setMessage_type("MSG_PRODUCT_UPGRADE_DOWN_REQ");
        headerBean.setSeq_id("1");

        pusBean.setBody(bodyBean);
        pusBean.setHeader(headerBean);
        updataJsonBean.setPUS(pusBean);

        String s = GsonUtils.GsonString(updataJsonBean);
        String path = "";
        path = "https://pus.wonlycloud.com:10400";
        OkGo.<String>post(path).upJson(s).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.body();
                Gson gson = new Gson();
                try {
                    UpdateAppBean updateAppBean = gson.fromJson(s, UpdateAppBean.class);
                    if (Integer.parseInt(updateAppBean.getPUS().getBody().getNew_version()) > version) {
                        listener.success(updateAppBean);
                    }
                } catch (Exception e) {
                    Log.e("升级接口报错", e.toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
                listener.fail("服务器连接失败");
            }
        });
    }

    /**
     * 固件升级
     *
     * @param version
     */
    private void requestFileUpdate(int version) {
        UpdataJsonBean updataJsonBean = new UpdataJsonBean();
        UpdataJsonBean.PUSBean pusBean = new UpdataJsonBean.PUSBean();
        UpdataJsonBean.PUSBean.BodyBean bodyBean = new UpdataJsonBean.PUSBean.BodyBean();
        UpdataJsonBean.PUSBean.HeaderBean headerBean = new UpdataJsonBean.PUSBean.HeaderBean();

        bodyBean.setToken("");
        bodyBean.setVendor_name("general");
        bodyBean.setPlatform("android");
        bodyBean.setEndpoint_type("haomibo");
        bodyBean.setCurrent_version(version + "");

        headerBean.setApi_version("1.0");
        headerBean.setMessage_type("MSG_PRODUCT_UPGRADE_DOWN_REQ");
        headerBean.setSeq_id("1");

        pusBean.setBody(bodyBean);
        pusBean.setHeader(headerBean);
        updataJsonBean.setPUS(pusBean);

        String s = GsonUtils.GsonString(updataJsonBean);
        String path = "";
        path = "https://pus.wonlycloud.com:10400";
        OkGo.<String>post(path).upJson(s).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String s = response.body();
                Gson gson = new Gson();
                try {
                    UpdateAppBean updateAppBean = gson.fromJson(s, UpdateAppBean.class);
                    if (version > 0) {
                        downloadFile(updateAppBean.getPUS().getBody().getUrl());
                    }
                } catch (Exception e) {
                    Log.e("升级接口报错", e.toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
            }
        });
    }


    //下载apk文件并跳转(第二次请求，get)
    private void downloadApp(String apk_url) {
        OkGo.<File>get(apk_url).tag(this).execute(new FileCallback() {
            @Override
            public void onError(Response<File> response) {
                if (mDownloadDialog != null) {
                    mDownloadDialog.dismiss();
                    mDownloadDialog = null;
                }
            }

            @Override
            public void onSuccess(Response<File> response) {
                if (mDownloadDialog != null && mDownloadDialog.isShowing()) {
                    mDownloadDialog.dismiss();
                    mDownloadDialog = null;
                }
                String filePath = response.body().getAbsolutePath();
                boolean b = installApp(filePath);
            }

            @Override
            public void downloadProgress(Progress progress) {
                if (mDownloadDialog == null) {
                    // 构造软件下载对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("正在更新");
                    // 给下载对话框增加进度条
                    final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    View v = inflater.inflate(R.layout.item_progress, null);
                    mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
                    builder.setView(v);
                    mDownloadDialog = builder.create();
                    mDownloadDialog.show();
                }
                mProgress.setProgress((int) (progress.fraction * 100));
            }
        });
    }

    //下载固件
    private void downloadFile(String apk_url) {
        OkGo.<File>get(apk_url).tag(this).execute(new FileCallback() {
            @Override
            public void onError(Response<File> response) {
                Log.e("固件下载", "下载失败");
            }

            @Override
            public void onSuccess(Response<File> response) {
                String filePath = response.body().getAbsolutePath();
                File file = new File(filePath);
                Log.e("固件下载", "下载成功：" + filePath);
                if (file.exists()) {
                    Constants.sCurrentPro = 0;
                    Constants.sCountPro = 0;
                    if (ymodleUtils == null)
                        ymodleUtils = new YmodleUtils(MainActivity.this);
                    ymodleUtils.writeData();
                    threads.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (ymodleUtils.mUartManager.isOpen()) {
                                    new YModem(ymodleUtils.mUartManager).send(file);
                                }
                            } catch (IOException e) {
                                Log.e("gh0st", e.toString());
                            }
                        }
                    });

                }
            }
        });
    }

    public interface DataRequestListener<T> {
        //请求成功
        void success(T data);

        //请求失败
        void fail(String msg);
    }


    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏


        if (Build.VERSION.SDK_INT < 16) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }


    public static int getFdCount() {
        File fdFile = new File("/proc/" + android.os.Process.myPid() + "/fd");
        File[] files = fdFile.listFiles();
        return null == files ? 0 : files.length;
    }


    public boolean installApp(String apkPath) {
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
            process = new ProcessBuilder("pm", "install", "-r", "-i", "com.wl.wlflatproject", apkPath).start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {
            Log.e("静默安装报错", e.toString());
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {

            }
            if (process != null) {
                process.destroy();
            }
        }
        Log.e("result", "" + errorMsg.toString());
        return successMsg.toString().equalsIgnoreCase("success");
    }


    @Override
    public void onPlayState(int state, int errorId) {
        if (errorId == EFUN_ERROR.EE_DVR_PASSWORD_NOT_VALID) {
            XMDevInfo devInfo = DevDataCenter.getInstance().getDevInfo(devMonitorPresenter.getDevId());
            XMPromptDlg.onShowPasswordErrorDialog(this, devInfo.getSdbDevInfo(), 0, new PwdErrorManager.OnRepeatSendMsgListener() {
                @Override
                public void onSendMsg(int msgId) {
                    devMonitorPresenter.startMonitor();
                }
            });
        } else if (errorId < 0) {
            Toast.makeText(MainActivity.this, "打开视频失败", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUpdateFaceFrameView(FaceFeature[] faceFeatures, int width, int height) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public MainActivity getActivity() {
        return this;
    }

}
