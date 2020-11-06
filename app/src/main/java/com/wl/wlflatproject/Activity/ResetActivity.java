package com.wl.wlflatproject.Activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.wl.wlflatproject.Bean.ConnectBean;
import com.wl.wlflatproject.Bean.MainMsgBean;
import com.wl.wlflatproject.MUtils.DeviceUtils;
import com.wl.wlflatproject.MUtils.GsonUtils;
import com.wl.wlflatproject.MView.WaitDialogTime;
import com.wl.wlflatproject.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetActivity extends AppCompatActivity {
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.save)
    Button save;
    ConnectBean connectBean = null;
    private WaitDialogTime dialogTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ConnectBean connectBean) {
            if(dialogTime!=null &&dialogTime.isShowing()){
                dialogTime.dismiss();
            }
        setResult(100);
        finish();
    }



    @OnClick({R.id.back, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                if (!TextUtils.isEmpty(edit.getText().toString())) {
                    int i = Integer.parseInt(edit.getText().toString());
                    if(connectBean==null){
                        connectBean=new ConnectBean();
                        connectBean.setCmd(0x1101);
                        connectBean.setAck(0);
                        connectBean.setDevType("WL025S1");
                        connectBean.setDevid(DeviceUtils.getSerialNumber(this));
                        connectBean.setVendor("general");
                        connectBean.setSeqid(1);
                    }
                    connectBean.setResetNum(i);
                    long time = System.currentTimeMillis() / 1000;
                    connectBean.setTime(time);
                    String json = GsonUtils.GsonString(connectBean);
                    MainMsgBean msgBean =new MainMsgBean();
                    msgBean.setFlag(10);
                    msgBean.setMsg(json);
                    EventBus.getDefault().post(msgBean);
                    if (dialogTime == null)
                        dialogTime = new WaitDialogTime(this, android.R.style.Theme_Translucent_NoTitleBar);
                    dialogTime.show();
                } else {
                    Toast.makeText(ResetActivity.this, "请输入人数", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
