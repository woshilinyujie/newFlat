package com.wl.wlflatproject.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wl.wlflatproject.Bean.ConnectBean;
import com.wl.wlflatproject.Bean.MainMsgBean;
import com.wl.wlflatproject.MUtils.DeviceUtils;
import com.wl.wlflatproject.MUtils.GsonUtils;
import com.wl.wlflatproject.MUtils.SPUtil;
import com.wl.wlflatproject.MView.WaitDialogTime;
import com.wl.wlflatproject.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoorSelectActivity extends AppCompatActivity {
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.one_check)
    ImageView onCheck;
    @BindView(R.id.two_check)
    ImageView twoCheck;
    @BindView(R.id.three_check)
    ImageView threeCheck;
    private int select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_select_layout);
        ButterKnife.bind(this);
        initData();
    }



    private void initData() {
        select = SPUtil.getInstance(this).getSettingParam("doorSelect", 0);
        switch (select){
            case 0:
                onCheck.setBackgroundResource(R.drawable.device_select_icon);
                break;
            case 1:
                twoCheck.setBackgroundResource(R.drawable.device_select_icon);
                break;
            case 2:
                threeCheck.setBackgroundResource(R.drawable.device_select_icon);
                break;
        }
    }


    @OnClick({R.id.back, R.id.save,R.id.one_check,R.id.two_check,R.id.three_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.save:
                MainMsgBean mainMsgBean = new MainMsgBean();

                mainMsgBean.setFlag(21);
                mainMsgBean.setMsg(select+"");
                EventBus.getDefault().post(mainMsgBean);
                finish();
                break;
            case R.id.one_check:
                onCheck.setBackgroundResource(R.drawable.device_select_icon);
                twoCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                threeCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                select=0;
                break;
            case R.id.two_check:
                onCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                twoCheck.setBackgroundResource(R.drawable.device_select_icon);
                threeCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                select=1;
                break;
            case R.id.three_check:
                onCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                twoCheck.setBackgroundResource(R.drawable.device_unselect_icon);
                threeCheck.setBackgroundResource(R.drawable.device_select_icon);
                select=2;
                break;
        }
    }
}
