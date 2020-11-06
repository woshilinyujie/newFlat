package com.wl.wlflatproject.MView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wl.wlflatproject.R;


public class SetDialog extends Dialog {

    private NumberPickerView start;
    private String s[]=new String[]{"67","72","77","82","87","92","97","102","107","112","117","122","127",
            "132","137","142","147","152","157","162","167","172","177","182","187"};//修正角度
    private String s1[]=new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};//速度
    private String s2[]=new String[]{"72","77","82","87"};//开门角度
    private String s3[]=new String[]{"2","4","6","8","10","12","14","16","18","20","22","24","26","28","30"};//等待时间
    private TextView name;
    private View back;
    private Button complete;
    private String value;
    private ResultListener listener;
    private int flag;

    public SetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initData(context);
    }

    private void initData(Context context) {
        View inflate = View.inflate(context, R.layout.set_dialog_layout,null);
        start = inflate.findViewById(R.id.start);
        name = inflate.findViewById(R.id.dialog_name);
        back = inflate.findViewById(R.id.back);
        complete = inflate.findViewById(R.id.complete);
        setContentView(inflate);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = start.getContentByCurrValue();
                listener.onResult(value,flag);
                dismiss();
            }
        });
    }



    public void show(int flag){
        this.flag=flag;
        switch (flag){
            case 1://左角度修正
                start.refreshByNewDisplayedValues(s);
                name.setText("左角度修复值");
                break;
            case 2://右角度修正
                start.refreshByNewDisplayedValues(s);
                name.setText("右角度修复值");
                break;
            case 3://开门角度
                start.refreshByNewDisplayedValues(s2);
                name.setText("开门角度");
                break;
            case 4://等待时间
                start.refreshByNewDisplayedValues(s3);
                name.setText("等待时间");
                break;
            case 5://开门速度
                start.refreshByNewDisplayedValues(s1);
                name.setText("开门速度");
                break;
            case 6://关门速度
                start.refreshByNewDisplayedValues(s1);
                name.setText("关门速度");
                break;
        }
        show();
    }


    public void setListener(ResultListener listener){
        this.listener=listener;
    }
    public interface  ResultListener{
        public void onResult(String value,int flag);
    }
}
