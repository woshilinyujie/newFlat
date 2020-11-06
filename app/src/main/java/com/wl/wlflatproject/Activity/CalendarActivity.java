package com.wl.wlflatproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.wl.wlflatproject.Bean.CalendarParam;
import com.wl.wlflatproject.MUtils.DateUtils;
import com.wl.wlflatproject.MUtils.LunarUtils;
import com.wl.wlflatproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: HSL
 * @Time: 2019/7/26 13:57
 * @E-mail: xxx@163.com
 * @Description: 日历~
 */
public class CalendarActivity extends AppCompatActivity implements CalendarView.OnCalendarSelectListener {

    public static final String PARAM = "param";

    @BindView(R.id.current_month_tv)
    TextView currentMonthTv;
    @BindView(R.id.current_week_tv)
    TextView currentWeekTv;
    @BindView(R.id.current_year_tv)
    TextView currentYearTv;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.calendar_view)
    CalendarView calendarView;
    @BindView(R.id.lunar_date_tv)
    TextView lunarDateTv;
    @BindView(R.id.temp_tv)
    TextView tempTv;
    @BindView(R.id.weather_tv)
    TextView weatherTv;
    @BindView(R.id.gan_zhi_tv)
    TextView ganZhiTv;
    @BindView(R.id.location_tv)
    TextView locationTv;

    private CalendarParam mParam;

    public static void start(Context context, CalendarParam param) {
        Intent starter = new Intent(context, CalendarActivity.class);
        starter.putExtra(PARAM, param);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
        mParam = (CalendarParam) getIntent().getSerializableExtra(PARAM);
        initView();
        hideBottomUIMenu();
    }

    private void initView() {
        calendarView.setOnCalendarSelectListener(this);
        //当前月
        currentMonthTv.setText(calendarView.getCurMonth() + "月");
        //当前周
        currentWeekTv.setText(DateUtils.getInstance().getWeekday2(System.currentTimeMillis()));
        //当前年
        currentYearTv.setText(calendarView.getCurYear() + "年");
        //猪 贰零壹玖 润 六月 小廿八 己亥 辛未 戊辰
        String[] lunar = LunarUtils.getLunar(calendarView.getCurYear() + "",
                calendarView.getCurMonth() + "",
                calendarView.getCurDay() + "");
        //农历
        lunarDateTv.setText(String.format("%s-%s-%s", "农历", lunar[3] + "月", lunar[4]));
        //温度
        tempTv.setText(mParam.temp + "℃");
        //天气
        weatherTv.setText(mParam.weather);
        //天干地支
        ganZhiTv.setText(String.format("%s %s %s", lunar[5] + lunar[0] + "年", lunar[6] + "月", lunar[7] + "日"));
        //地点
        locationTv.setText(mParam.location);
    }

    @OnClick(R.id.back_iv)
    public void onBackClicked() {
        finish();
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //月
        currentMonthTv.setText(calendar.getMonth() + "月");
        //年
        currentYearTv.setText(calendar.getYear() + "年");
        Calendar lunarCalendar = calendar.getLunarCalendar();
        //猪 贰零壹玖 润 六月 小廿八 己亥 辛未 戊辰
        String[] lunar = LunarUtils.getLunar(calendar.getYear() + "",
                calendar.getMonth() + "",
                calendar.getDay() + "");
        //农历
        lunarDateTv.setText(String.format("%s-%s-%s", "农历", lunar[3] + "月", lunar[4]));
        //天干地支
        ganZhiTv.setText(String.format("%s %s %s", lunar[5] + lunar[0] + "年", lunar[6] + "月", lunar[7] + "日"));
        //周
        if (calendar.isCurrentDay()) {
            currentWeekTv.setText(DateUtils.getInstance().getWeekday2(System.currentTimeMillis()));
        } else {
            String weekStr = "周日";
            switch (calendar.getWeek()) {
                case 0:
                    weekStr = "周日";
                    break;
                case 1:
                    weekStr = "周一";
                    break;
                case 2:
                    weekStr = "周二";
                    break;
                case 3:
                    weekStr = "周三";
                    break;
                case 4:
                    weekStr = "周四";
                    break;
                case 5:
                    weekStr = "周五";
                    break;
                case 6:
                    weekStr = "周六";
                    break;
                default:
            }
            currentWeekTv.setText(weekStr);
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
