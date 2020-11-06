package com.wl.wlflatproject.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.suke.widget.SwitchButton;
import com.wl.wlflatproject.Bean.MainMsgBean;
import com.wl.wlflatproject.MUtils.SPUtil;
import com.wl.wlflatproject.R;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomCheckActivity extends AppCompatActivity {
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.switch_bt)
    SwitchButton switchBt;
    @BindView(R.id.wait_time)
    RelativeLayout waitTime;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.num_rl)
    RelativeLayout numRl;
    @BindView(R.id.reset)
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_check);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if(SPUtil.getInstance(this).getSettingParam("open",false)){
            numRl.setVisibility(View.VISIBLE);
            reset.setVisibility(View.VISIBLE);
            switchBt.setChecked(true);
            numTv.setText(MainActivity.checkNum+"");
        }
        switchBt.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    numRl.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.VISIBLE);
                    SPUtil.getInstance(RoomCheckActivity.this).setSettingParam("open",true);
                    numTv.setText(MainActivity.checkNum+"");
                    MainMsgBean setMsgBean = new  MainMsgBean();
                    setMsgBean.setFlag(9);
                    EventBus.getDefault().post(setMsgBean);
                }else{
                    numRl.setVisibility(View.GONE);
                    reset.setVisibility(View.GONE);
                    SPUtil.getInstance(RoomCheckActivity.this).setSettingParam("open",false);
                    MainMsgBean setMsgBean = new  MainMsgBean();
                    setMsgBean.setFlag(11);
                    EventBus.getDefault().post(setMsgBean);
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.num_rl, R.id.reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                setResult(200);
                finish();
                break;
            case R.id.reset:
                Intent intent=new Intent(RoomCheckActivity.this,ResetActivity.class);
                startActivityForResult(intent,200);
                break;
        }
    }

    @Override
    protected void onResume() {
        hideBottomUIMenu();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            numTv.setText(MainActivity.checkNum + "");
        }
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
