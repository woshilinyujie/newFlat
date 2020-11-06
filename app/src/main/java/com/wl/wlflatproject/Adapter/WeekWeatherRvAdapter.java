package com.wl.wlflatproject.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.wlflatproject.Bean.WeekWeatherItem;
import com.wl.wlflatproject.MUtils.DateUtils;
import com.wl.wlflatproject.R;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Adapter
 * @Author: HSL
 * @Time: 2019/07/26 15:26
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class WeekWeatherRvAdapter extends BaseQuickAdapter<WeekWeatherItem, BaseViewHolder> {

    public WeekWeatherRvAdapter() {
        super(R.layout.item_rv_week_weather);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeekWeatherItem item) {
        helper.setGone(R.id.split_view, helper.getAdapterPosition() != 0);
        DateUtils dateUtils = DateUtils.getInstance();
        //日期
        helper.setText(R.id.date_tv, dateUtils.descriptiveData(item.getDate()));
        //温度
        helper.setText(R.id.temp_tv, String.format("%s/%s℃", item.getHigh(), item.getLow()));
        //iCON TEXT
        String weatherText = "晴";
        int weatherIcon = R.drawable.sun_icon;
        switch (item.getCode()) {
            case "1":
                weatherText = "晴天";
                weatherIcon = R.drawable.sun_icon;
                break;
            case "2":
                weatherText = "阴天";
                weatherIcon = R.drawable.cloud_icon;
                break;
            case "3":
                weatherText = "雨天";
                weatherIcon = R.drawable.rain_icon;
                break;
            case "4":
                weatherText = "雪天";
                weatherIcon = R.drawable.snow_icon;
                break;
            default:
        }
        helper.setBackgroundRes(R.id.weather_view, weatherIcon);
        helper.setText(R.id.weather_tv, weatherText);
        //空气质量
        helper.setText(R.id.air_quality_tv, item.getAirQuality());
    }
}
