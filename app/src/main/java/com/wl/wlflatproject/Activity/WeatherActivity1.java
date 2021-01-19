package com.wl.wlflatproject.Activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wl.wlflatproject.MUtils.LocationUtils;
import com.wl.wlflatproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity1 extends AppCompatActivity {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.today_temp_tv)
    TextView todayTempTv;
    @BindView(R.id.today_weather_view)
    View todayWeatherView;
    @BindView(R.id.today_weather_tv)
    TextView todayWeatherTv;
    @BindView(R.id.today_extent_tv)
    TextView todayExtentTv;
    @BindView(R.id.today_temp_ll)
    LinearLayout todayTempLl;
    @BindView(R.id.second_weather_tv)
    TextView secondWeatherTv;
    @BindView(R.id.second_day_view)
    View secondDayView;
    @BindView(R.id.second_day_tv)
    TextView secondDayTv;
    @BindView(R.id.third_weather_tv)
    TextView thirdWeatherTv;
    @BindView(R.id.third_day_view)
    View thirdDayView;
    @BindView(R.id.third_day_tv)
    TextView thirdDayTv;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String param = bundle.getString("param");
        String param1 = bundle.getString("param1");
        String param2 = bundle.getString("param2");
        String param3 = bundle.getString("param3");
        String param4 = bundle.getString("param4");
        String param5 = bundle.getString("param5");
        String param6 = bundle.getString("param6");
        String param7 = bundle.getString("param7");
        String param8 = bundle.getString("param8");
        String param9 = bundle.getString("param9");
        String param10 = bundle.getString("param10");
        locationTv.setText(param + "");
        todayTempTv.setText(param1 + "");
        todayWeatherTv.setText(param2 + "");
        todayExtentTv.setText(param3 + "");
        secondWeatherTv.setText(param4);
        secondDayTv.setText(param5);
        thirdWeatherTv.setText(param6);
        thirdDayTv.setText(param7);
        setWeatherIcon(todayWeatherView, param8);
        setWeatherIcon(secondDayView, param9);
        setWeatherIcon(thirdDayView, param10);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void setWeatherIcon(View view, String code) {
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
    }

}
