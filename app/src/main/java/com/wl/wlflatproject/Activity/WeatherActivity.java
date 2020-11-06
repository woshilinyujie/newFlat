package com.wl.wlflatproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wl.wlflatproject.Adapter.HourWeatherRvAdapter;
import com.wl.wlflatproject.Adapter.WeekWeatherRvAdapter;
import com.wl.wlflatproject.Bean.Air24HourBean;
import com.wl.wlflatproject.Bean.FutureAirQualityBean;
import com.wl.wlflatproject.Bean.FutureWeatherBean;
import com.wl.wlflatproject.Bean.HourWeatherItem;
import com.wl.wlflatproject.Bean.NowAirQuality;
import com.wl.wlflatproject.Bean.NowWeatherBean;
import com.wl.wlflatproject.Bean.Weather24HourBean;
import com.wl.wlflatproject.Bean.WeekWeatherItem;
import com.wl.wlflatproject.MUtils.DateUtils;
import com.wl.wlflatproject.MUtils.GsonUtils;
import com.wl.wlflatproject.MUtils.LocationUtils;
import com.wl.wlflatproject.MUtils.PreferencesUtils;
import com.wl.wlflatproject.MUtils.StringCallBack;
import com.wl.wlflatproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wl.wlflatproject.Constant.WeatherUrl.AIR_24_HOUR_URL;
import static com.wl.wlflatproject.Constant.WeatherUrl.FUTURE_AIR_QUALITY_URL;
import static com.wl.wlflatproject.Constant.WeatherUrl.NOW_AIR_QUALITY_URL;
import static com.wl.wlflatproject.Constant.WeatherUrl.WEATHER_24_HOUR_URL;
import static com.wl.wlflatproject.Constant.WeatherUrl.WEATHER_FUTURE_URL;
import static com.wl.wlflatproject.Constant.WeatherUrl.WEATHER_KEY;
import static com.wl.wlflatproject.Constant.WeatherUrl.WEATHER_NOW_URL;

/**
 * @Author: HSL
 * @Time: 2019/7/26 13:56
 * @E-mail: xxx@163.com
 * @Description: 天气~
 */
public class WeatherActivity extends AppCompatActivity {

    public static final String AMAP = "amap";
    public static final String WEEK_WEATHER = "week_weather";
    public static final String HOUR_WEATHER = "hour_weather";

    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.weather_view)
    View weatherView;
    @BindView(R.id.temp_tv)
    TextView tempTv;
    @BindView(R.id.weather_tv)
    TextView weatherTv;
    @BindView(R.id.air_quality_tv)
    TextView airQualityTv;
    @BindView(R.id.future_rain_tv)
    TextView futureRainTv;
    @BindView(R.id.week_weather_rv)
    RecyclerView weekWeatherRv;
    @BindView(R.id.hour_weather_rv)
    RecyclerView hourWeatherRv;

    private List<WeekWeatherItem> mWeekWeatherItems = new ArrayList<>();
    private List<HourWeatherItem> mHourWeatherItems = new ArrayList<>();
    private WeekWeatherRvAdapter mWeekWeatherRvAdapter;
    private HourWeatherRvAdapter mHourWeatherRvAdapter;
    private AMapLocation mAMapLocation;
    private String mCity;
    private String mDistrict;

    public static void start(Context context, AMapLocation aMapLocation) {
        Intent starter = new Intent(context, WeatherActivity.class);
        starter.putExtra(AMAP, aMapLocation);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        mAMapLocation = getIntent().getParcelableExtra(AMAP);
        initView();
        initData();
        hideBottomUIMenu();
    }

    private void initView() {
        //地点
        mCity = mAMapLocation.getCity();
        mDistrict = mAMapLocation.getDistrict();
        String street = mAMapLocation.getStreet();
        if (street != null) {
            addressTv.setText(String.format("%s%s", mDistrict, street));
        } else {
            addressTv.setText(String.format("%s%s", mCity, mDistrict));
        }
        //未来一周天气
        weekWeatherRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mWeekWeatherRvAdapter = new WeekWeatherRvAdapter();
        mWeekWeatherRvAdapter.setEnableLoadMore(false);
        weekWeatherRv.setAdapter(mWeekWeatherRvAdapter);
        String weekJson = PreferencesUtils.getString(this, WEEK_WEATHER);
        if (weekJson != null && !weekJson.equals("")) {
            ArrayList<WeekWeatherItem> items = GsonUtils.jsonToArrayList(weekJson, WeekWeatherItem.class);
            mWeekWeatherRvAdapter.setNewData(items);
        }
        //24小时天气
        hourWeatherRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mHourWeatherRvAdapter = new HourWeatherRvAdapter();
        mHourWeatherRvAdapter.setEnableLoadMore(false);
        hourWeatherRv.setAdapter(mHourWeatherRvAdapter);
        String hourJson = PreferencesUtils.getString(this, HOUR_WEATHER);
        if (hourJson != null && !hourJson.equals("")) {
            ArrayList<HourWeatherItem> items = GsonUtils.jsonToArrayList(hourJson, HourWeatherItem.class);
            mHourWeatherRvAdapter.notifyDataChanged(items);
        }
    }

    private void initData() {
        requestNowWeather();
        requestNowAirQuality();
        requestFutureWeather();
        request24HourWeather();
    }

    /**
     * 返回
     */
    @OnClick(R.id.back_iv)
    public void onBackClicked() {
        finish();
    }

    /**
     * 实时天气
     */
    private void requestNowWeather() {
        OkGo.<String>get(WEATHER_NOW_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .params("unit", "c")
                .cacheKey(WEATHER_NOW_URL)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallBack() {
                    @Override
                    public void onFastSuccess(Response<String> response, boolean isCache) {
                        String body = response.body();
                        NowWeatherBean nowWeatherBean = GsonUtils.GsonToBean(body, NowWeatherBean.class);
                        List<NowWeatherBean.ResultsBean> results = nowWeatherBean.getResults();
                        if (results == null || results.isEmpty()) {
                            return;
                        }
                        NowWeatherBean.ResultsBean resultsBean = results.get(0);
                        NowWeatherBean.ResultsBean.NowBean now = resultsBean.getNow();
                        String code = LocationUtils.weatherStatusCode(now.getCode());
                        switch (code) {
                            case "1":
                                weatherTv.setText("晴");
                                weatherView.setBackgroundResource(R.drawable.sun_icon);
                                break;
                            case "2":
                                weatherTv.setText("阴");
                                weatherView.setBackgroundResource(R.drawable.cloud_icon);
                                break;
                            case "3":
                                weatherTv.setText("雨");
                                weatherView.setBackgroundResource(R.drawable.rain_icon);
                                break;
                            case "4":
                                weatherTv.setText("雪");
                                weatherView.setBackgroundResource(R.drawable.snow_icon);
                                break;
                            default:
                        }
                        //温度
                        tempTv.setText(now.getFeels_like());
                    }
                });
    }

    /**
     * 查询未来天气
     */
    private void requestFutureWeather() {
        OkGo.<String>get(WEATHER_FUTURE_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .params("unit", "c")
                .params("start", "0")
                .params("days", "5")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        FutureWeatherBean futureWeatherBean = GsonUtils.GsonToBean(body, FutureWeatherBean.class);
                        List<FutureWeatherBean.ResultsBean> results = futureWeatherBean.getResults();
                        if (results == null || results.isEmpty()) {
                            return;
                        }
                        FutureWeatherBean.ResultsBean resultsBean = results.get(0);
                        List<FutureWeatherBean.ResultsBean.DailyBean> dailyBeans = resultsBean.getDaily();
                        for (FutureWeatherBean.ResultsBean.DailyBean dailyBean : dailyBeans) {
                            WeekWeatherItem item = new WeekWeatherItem();
                            item.setHigh(dailyBean.getHigh());
                            item.setLow(dailyBean.getLow());
                            boolean night = DateUtils.getInstance().isNight();
                            String weatherStatusCode = LocationUtils.weatherStatusCode(
                                    night ? dailyBean.getCode_night() : dailyBean.getCode_day());
                            item.setCode(weatherStatusCode);
                            item.setDate(dailyBean.getDate());
                            mWeekWeatherItems.add(item);
                        }
                        requestFutureAirQuality();
                    }
                });
    }

    /**
     * 实时空气质量
     */
    private void requestNowAirQuality() {
        OkGo.<String>get(NOW_AIR_QUALITY_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .cacheKey(NOW_AIR_QUALITY_URL)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallBack() {
                    @Override
                    public void onFastSuccess(Response<String> response, boolean isCache) {
                        String body = response.body();
                        NowAirQuality nowAirQuality = GsonUtils.GsonToBean(body, NowAirQuality.class);
                        NowAirQuality.ResultsBean resultsBean = nowAirQuality.getResults().get(0);
                        NowAirQuality.ResultsBean.AirBean beanAir = resultsBean.getAir();
                        NowAirQuality.ResultsBean.AirBean.CityBean cityBean = beanAir.getCity();
                        airQualityTv.setText(String.format("%s %s", "空气" + cityBean.getQuality(), cityBean.getAqi()));
                    }
                });
    }

    /**
     * 获取未来5天空气质量（最多可以获取5天）
     */
    private void requestFutureAirQuality() {
        OkGo.<String>get(FUTURE_AIR_QUALITY_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        FutureAirQualityBean futureAirQualityBean = GsonUtils.GsonToBean(body, FutureAirQualityBean.class);
                        FutureAirQualityBean.ResultsBean resultsBean = futureAirQualityBean.getResults().get(0);
                        List<FutureAirQualityBean.ResultsBean.DailyBean> beanDaily = resultsBean.getDaily();
                        for (int i = 0; i < beanDaily.size(); i++) {
                            FutureAirQualityBean.ResultsBean.DailyBean bean = beanDaily.get(i);
                            WeekWeatherItem item = mWeekWeatherItems.get(i);
                            item.setAirQuality(bean.getQuality());
                        }
                        mWeekWeatherRvAdapter.setNewData(mWeekWeatherItems);
                        PreferencesUtils.saveString(WeatherActivity.this,
                                WEEK_WEATHER,
                                GsonUtils.GsonString(mWeekWeatherItems));
                    }
                });
    }

    /**
     * 24小时天气
     */
    private void request24HourWeather() {
        OkGo.<String>get(WEATHER_24_HOUR_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .params("start", 0)
                .params("hours", 24)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Log.d("hsl777", "onSuccess: " + body);
                        Weather24HourBean weather24HourBean = GsonUtils.GsonToBean(body, Weather24HourBean.class);
                        Weather24HourBean.ResultsBean resultsBean = weather24HourBean.getResults().get(0);
                        boolean after2HourStatus = false;
                        List<Weather24HourBean.ResultsBean.HourlyBean> hourlyBeans = resultsBean.getHourly();
                        for (int i = 0; i < hourlyBeans.size(); i++) {
                            Weather24HourBean.ResultsBean.HourlyBean bean = hourlyBeans.get(i);
                            String weatherStatusCode = LocationUtils.weatherStatusCode(bean.getCode());
                            if (i == 1 || i == 2) {
                                if (weatherStatusCode.equals("3")) {
                                    after2HourStatus = true;
                                } else {
                                    after2HourStatus = false;
                                }
                            }
                            HourWeatherItem item = new HourWeatherItem();
                            item.setTemp(Integer.parseInt(bean.getTemperature()));
                            item.setDesc(bean.getTemperature() + "℃");
                            item.setTime(bean.getTime());
                            mHourWeatherItems.add(item);
                        }
                        //未来两小时天气
                        String after2HourStr = after2HourStatus ? "有雨" : "无雨";
                        futureRainTv.setText("未来两小时" + after2HourStr);
                        //获取24小时空气质量
                        request24HourAirQuality();
                    }
                });
    }

    /**
     * 获取24小时空气质量
     */
    private void request24HourAirQuality() {
        OkGo.<String>get(AIR_24_HOUR_URL)
                .tag(this)
                .params("key", WEATHER_KEY)
                .params("location", mDistrict != null ? mDistrict : mCity)
                .params("language", "zh-Hans")
                .params("days", 2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Air24HourBean air24HourBean = GsonUtils.GsonToBean(body, Air24HourBean.class);
                        Air24HourBean.ResultsBean resultsBean = air24HourBean.getResults().get(0);
                        List<Air24HourBean.ResultsBean.HourlyBean> hourlyBeans = resultsBean.getHourly();
                        for (int i = 0; i < mHourWeatherItems.size(); i++) {
                            HourWeatherItem item = mHourWeatherItems.get(i);
                            Air24HourBean.ResultsBean.HourlyBean bean = hourlyBeans.get(i);
                            String airQuality = "";
                            if (Long.parseLong(bean.getAqi()) > 100) {
                                airQuality = bean.getAqi() + "差";
                            } else {
                                airQuality = bean.getAqi() + bean.getQuality();
                            }
                            item.setAirQuality(airQuality);
                        }
                        mHourWeatherRvAdapter.notifyDataChanged(mHourWeatherItems);
                        PreferencesUtils.saveString(WeatherActivity.this,
                                HOUR_WEATHER,
                                GsonUtils.GsonString(mHourWeatherItems));
                    }
                });
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
