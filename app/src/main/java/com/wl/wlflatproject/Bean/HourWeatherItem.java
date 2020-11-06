package com.wl.wlflatproject.Bean;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/07/26 15:37
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class HourWeatherItem implements TempEntry {

    private int temp;
    private String desc;
    private String time;
    private String airQuality;


    @Override
    public int getTempValue() {
        return temp;
    }

    @Override
    public String getTempText() {
        return desc;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }
}
