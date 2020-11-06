package com.wl.wlflatproject.Adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wl.wlflatproject.Bean.HourWeatherItem;
import com.wl.wlflatproject.MUtils.DateUtils;
import com.wl.wlflatproject.MView.WeatherChartView;
import com.wl.wlflatproject.R;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Adapter
 * @Author: HSL
 * @Time: 2019/07/26 15:37
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class HourWeatherRvAdapter extends BaseQuickAdapter<HourWeatherItem, BaseViewHolder> {

    public HourWeatherRvAdapter() {
        super(R.layout.item_rv_hour_weather);
    }

    @Override
    protected void convert(BaseViewHolder helper, HourWeatherItem item) {
        //空气质量
        helper.setText(R.id.air_quality_tv, item.getAirQuality());
        //时间 2019-07-29T12:00:00+08:00
        //转成 2019-07-29 12:00:00
        String time = item.getTime().replace("T", " ");
        String timeStr = time.substring(0, time.indexOf("+"));
        DateUtils dateUtils = DateUtils.getInstance();
        //转成时间戳
        long timeStamp = dateUtils.date2TimeStamp(timeStr, "yyyy-MM-dd HH:mm:ss");
        String hourStr = dateUtils.dateFormat8(timeStamp);
        //获取当前时间 时的两位
        String currentTime = dateUtils.dateFormat9(System.currentTimeMillis());
        //获取天气时间 时的两位
        String weatherTime = dateUtils.dateFormat9(timeStamp);
        helper.setTextColor(R.id.time_tv, Color.parseColor("#aaaeb2"));
        helper.setText(R.id.time_tv, hourStr);
        //当前时间 时不为00，天气时间为00，判定为明天
        if (!currentTime.equals("00") && weatherTime.equals("00")) {
            helper.setText(R.id.time_tv, "明天");
            helper.setTextColor(R.id.time_tv, Color.parseColor("#31ABD3"));
        }
        //判定为现在
        if (currentTime.equals(weatherTime)) {
            helper.setText(R.id.time_tv, "现在");
            helper.setTextColor(R.id.time_tv, Color.parseColor("#31ABD3"));
            WeatherChartView.mNowPosition = helper.getAdapterPosition();
        }
        //图标绘制
        WeatherChartView chartView = helper.getView(R.id.temp_wcv);
        chartView.drawChartView(getData(), helper.getAdapterPosition());
    }

    public void notifyDataChanged(List<HourWeatherItem> data) {
        int maxTemp = 0;
        int minTemp = 0;
        for (int i = 0; i < data.size(); i++) {
            int temp = data.get(i).getTemp();
            if (temp > maxTemp) {
                maxTemp = temp;
            }
            if (temp < minTemp) {
                minTemp = temp;
            }
        }
        WeatherChartView.mMaxTemp = maxTemp;
        WeatherChartView.mMinTemp = minTemp;
        WeatherChartView.mNowPosition = 0;
        setNewData(data);
    }
}
