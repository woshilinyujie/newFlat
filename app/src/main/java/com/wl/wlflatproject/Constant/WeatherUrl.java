package com.wl.wlflatproject.Constant;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Constant
 * @Author: HSL
 * @Time: 2019/07/29 10:06
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public interface WeatherUrl {

    //*******************************************************************//
    //*****************知心天气接口(因产品费用过高目前已废弃)*****************//
    //*******************************************************************//
    /**
     * 未来几天天气情况
     */
    String WEATHER_FUTURE_URL = "https://api.seniverse.com/v3/weather/daily.json";
    /**
     * 实时天气情况
     */
    String WEATHER_NOW_URL = "https://api.seniverse.com/v3/weather/now.json";
    /**
     * 天气Key
     */
    String WEATHER_KEY = "Se2MSKcwak3col1dv";
    /**
     * 实时空气质量
     */
    String NOW_AIR_QUALITY_URL = "https://api.seniverse.com/v3/air/now.json";
    /**
     * 未来几天空气质量
     */
    String FUTURE_AIR_QUALITY_URL = "https://api.seniverse.com/v3/air/daily.json";

    /**
     * 24小时天气
     */
    String WEATHER_24_HOUR_URL = "https://api.seniverse.com/v3/weather/hourly.json";

    /**
     * 24小时空气质量
     */
    String AIR_24_HOUR_URL = "https://api.seniverse.com/v3/air/hourly.json";

    //*******************************************************************//
    //*****************************高德地图天气****************************//
    //*******************************************************************//

    /**
     * 高德天气URL
     */
    String GD_WEATHER_RUL = "https://restapi.amap.com/v3/weather/weatherInfo";
    /**
     * 高德WEB服务的APP_KEY
     */
    String GD_WEB_APP_KEY = "5cdd9d4364f7344dd25796692456bd8b";
}
