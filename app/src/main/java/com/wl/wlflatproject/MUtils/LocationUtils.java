package com.wl.wlflatproject.MUtils;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.wl.wlflatproject.AppContext;
import com.wl.wlflatproject.Bean.GDFutureWeatherBean;
import com.wl.wlflatproject.Bean.GDNowWeatherBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.wl.wlflatproject.Constant.WeatherUrl.GD_WEATHER_RUL;
import static com.wl.wlflatproject.Constant.WeatherUrl.GD_WEB_APP_KEY;
import static com.wl.wlflatproject.Constant.WeatherUrl.WEATHER_FUTURE_URL;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.MUtils
 * @Author: HSL
 * @Time: 2019/07/18 15:56
 * @E-mail: xxx@163.com
 * @Description: 高德定位工具类~
 */
public abstract class LocationUtils {

    /**
     * AMapLocationClient类对象
     */
    private AMapLocationClient mLocationClient = null;
    /**
     * 地图参数
     */
    private AMapLocationClientOption mLocationClientOption;
    /**
     * 城市编码
     */
    private String mCityCode;
    /**
     * 城区编码
     */
    private String mAdCode;

    private Disposable mDisposable;

    public LocationUtils() {
        createLocationClient();
        initLocationOption();
    }

    /**
     * 创建定位Client
     */
    private void createLocationClient() {
        //初始化定位
        mLocationClient = new AMapLocationClient(AppContext.getInstance());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    /**
     * 参数配置
     */
    private void initLocationOption() {
        //初始化AMapLocationClientOption对象
        mLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationClientOption.setInterval(2000);
        mLocationClientOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationClientOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationClientOption.setHttpTimeOut(30000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationClientOption);
    }

    /**
     * 查询当前天气的结果
     *
     * @param livesBean
     * @param aMapLocation
     */
    protected abstract void onQueryNowWeatherResult(GDNowWeatherBean.LivesBean livesBean, AMapLocation aMapLocation);

    /**
     * 查询未来天气结果
     *
     * @param forecastsBean
     * @param aMapLocation
     */
    protected abstract void onQueryFutureWeatherResult(GDFutureWeatherBean.ForecastsBean forecastsBean, AMapLocation aMapLocation);

    /**
     * 设置地图查询时间
     *
     * @param second
     */
    public void setIntervalTime(int second) {
        if (mLocationClient != null && mLocationClientOption != null) {
            mLocationClientOption.setInterval(second * 1000);
            mLocationClient.setLocationOption(mLocationClientOption);
        }
    }


    /**
     * 开始定位
     */
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    /**
     * 销毁定位服务
     */
    public void destroyLocationClient() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    /**
     * 声明定位回调监听器
     */
    private AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    mCityCode = aMapLocation.getCityCode();
                    mAdCode = aMapLocation.getAdCode();
                    intervalQueryWeather(aMapLocation);
                    Log.d("hsl666", "onLocationChanged: ==" + aMapLocation);
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    /**
     * 轮询查询天气
     *
     * @param aMapLocation
     */
    private void intervalQueryWeather(AMapLocation aMapLocation) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mDisposable = Observable.interval(0, 30, TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        queryNowWeather(aMapLocation);
                        queryFutureWeather(aMapLocation);
                    }
                });
    }

    /**
     * 查询实时天气
     *
     * @param aMapLocation
     */
    private void queryNowWeather(AMapLocation aMapLocation) {
        OkGo.<String>get(GD_WEATHER_RUL)
                .tag(this)
                .params("key", GD_WEB_APP_KEY)
                .params("city", mAdCode != null ? mAdCode : mCityCode)
                .params("extensions", "base")
                .params("output", "JSON")
                .cacheKey(GD_WEATHER_RUL + "/" + "base")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallBack() {
                    @Override
                    public void onFastSuccess(Response<String> response, boolean isCache) {
                        String body = response.body();
                        Log.d("hsl666", "onFastSuccess: ==" + body.toString());
                        try {
                            GDNowWeatherBean gdNowWeatherBean = GsonUtils.GsonToBean(body, GDNowWeatherBean.class);
                            if (gdNowWeatherBean.getInfocode().equals("10000")) {
                                List<GDNowWeatherBean.LivesBean> beanLives = gdNowWeatherBean.getLives();
                                GDNowWeatherBean.LivesBean livesBean = beanLives.get(0);
                                onQueryNowWeatherResult(livesBean, aMapLocation);
                            }
                        }catch (Exception e){
                            Log.e("天气报错",e.toString());
                        }
                    }
                });
    }


    /**
     * 查询未来3天天气
     *
     * @param aMapLocation
     */
    private void queryFutureWeather(AMapLocation aMapLocation) {
        OkGo.<String>get(GD_WEATHER_RUL)
                .tag(this)
                .params("key", GD_WEB_APP_KEY)
                .params("city", mAdCode != null ? mAdCode : mCityCode)
                .params("extensions", "all")
                .params("output", "JSON")
                .cacheKey(WEATHER_FUTURE_URL + "/" + "all")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallBack() {
                    @Override
                    public void onFastSuccess(Response<String> response, boolean isCache) {
                        try {
                            String body = response.body();
                            Log.d("hsl", "onFastSuccess: ==" + body);
                            GDFutureWeatherBean gdFutureWeatherBean = GsonUtils.GsonToBean(body, GDFutureWeatherBean.class);
                            if (gdFutureWeatherBean.getInfocode().equals("10000")) {
                                List<GDFutureWeatherBean.ForecastsBean> forecasts = gdFutureWeatherBean.getForecasts();
                                GDFutureWeatherBean.ForecastsBean forecastsBean = forecasts.get(0);
                                onQueryFutureWeatherResult(forecastsBean, aMapLocation);
                            }
                        }catch (Exception e){
                            Log.e("天气报错",e.toString());
                        }
                    }
                });
    }

    /**
     * 知心天气状态码
     *
     * @param webCode 晴：1  阴：2  雨：3  雪：4
     */
    @Deprecated
    public static String weatherStatusCode(String webCode) {
        int webCodeInt = Integer.parseInt(webCode);
        //晴
        if (webCodeInt <= 3) {
            return "1";
        } else if (webCodeInt <= 9) {
            return "2";
        } else if (webCodeInt <= 19) {
            return "3";
        } else if (webCodeInt <= 25) {
            return "4";
        } else {
            return "1";
        }
    }

    /**
     * 高德天气状态码
     *
     * @param weather
     * @return 晴：1  阴：2  雨：3  雪：4
     */
    public static String weatherCode(String weather) {
        if (weather.contains("晴")) {
            return "1";
        }
        if (weather.contains("雨")) {
            return "3";
        }
        if (weather.contains("雪")) {
            return "4";
        }
        return "2";
    }
}
