package com.wl.wlflatproject.MView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wl.wlflatproject.R;


public class WaitDialogTime1 extends Dialog {

    private Context mContext;

    private TextView mWaitTv = null;
    private String mWaitingTxt = null;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    sendEmptyMessageDelayed(2,4000);
                    break;
                case 2:
                    Toast.makeText(mContext,"设备无响应",Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
            }
        }
    };
    private RelativeLayout rl;

    public WaitDialogTime1(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_dialog);
        mWaitTv = (TextView) findViewById(R.id.wait_tv);
        setCancelable(true);
        rl = findViewById(R.id.dialog_rl);
        if (mWaitingTxt != null && !mWaitingTxt.isEmpty()) {
            mWaitTv.setVisibility(View.VISIBLE);
            mWaitTv.setText(mWaitingTxt);
        } else {
            mWaitTv.setVisibility(View.GONE);
        }
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setWaitText(String text) {
        mWaitingTxt = text;
        if (mWaitTv == null) {
            return;
        }
        if (mWaitingTxt != null && !mWaitingTxt.isEmpty()) {
            mWaitTv.setVisibility(View.VISIBLE);
            mWaitTv.setText(text);
        } else {
            mWaitTv.setVisibility(View.GONE);
        }
    }


    public String getTextS(){

        return  mWaitTv.getText().toString();
    }

    @Override
    public void show() {
        handler.sendEmptyMessageDelayed(1,9000);
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            try {
                super.show();
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void dismiss() {
        handler.removeCallbacksAndMessages(null);
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            try {
                super.dismiss();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void cancel() {
        handler.removeCallbacksAndMessages(null);
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            try {
                super.cancel();
            } catch (Exception e) {
            }
        }
    }
}
