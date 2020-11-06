package com.wl.wlflatproject.Bean;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/07/19 09:02
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class FutureWeatherBean {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * daily : [{"date":"2019-07-19","text_day":"多云","code_day":"4","text_night":"雷阵雨","code_night":"11","high":"33","low":"25","precip":"","wind_direction":"东南","wind_direction_degree":"135","wind_speed":"10","wind_scale":"2"},{"date":"2019-07-20","text_day":"雷阵雨","code_day":"11","text_night":"多云","code_night":"4","high":"31","low":"24","precip":"","wind_direction":"南","wind_direction_degree":"180","wind_speed":"10","wind_scale":"2"},{"date":"2019-07-21","text_day":"多云","code_day":"4","text_night":"多云","code_night":"4","high":"35","low":"26","precip":"","wind_direction":"西南","wind_direction_degree":"225","wind_speed":"10","wind_scale":"2"},{"date":"2019-07-22","text_day":"雷阵雨","code_day":"11","text_night":"雷阵雨","code_night":"11","high":"30","low":"23","precip":"","wind_direction":"北","wind_direction_degree":"0","wind_speed":"10","wind_scale":"2"},{"date":"2019-07-23","text_day":"多云","code_day":"4","text_night":"晴","code_night":"1","high":"32","low":"23","precip":"","wind_direction":"北","wind_direction_degree":"0","wind_speed":"10","wind_scale":"2"}]
         * last_update : 2019-07-19T08:00:00+08:00
         */

        private LocationBean location;
        private String last_update;
        private List<DailyBean> daily;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public List<DailyBean> getDaily() {
            return daily;
        }

        public void setDaily(List<DailyBean> daily) {
            this.daily = daily;
        }

        public static class LocationBean {
            /**
             * id : WX4FBXXFKE4F
             * name : 北京
             * country : CN
             * path : 北京,北京,中国
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
             */

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }
        }

        public static class DailyBean {
            /**
             * date : 2019-07-19
             * text_day : 多云
             * code_day : 4
             * text_night : 雷阵雨
             * code_night : 11
             * high : 33
             * low : 25
             * precip :
             * wind_direction : 东南
             * wind_direction_degree : 135
             * wind_speed : 10
             * wind_scale : 2
             */

            private String date;
            private String text_day;
            private String code_day;
            private String text_night;
            private String code_night;
            private String high;
            private String low;
            private String precip;
            private String wind_direction;
            private String wind_direction_degree;
            private String wind_speed;
            private String wind_scale;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getText_day() {
                return text_day;
            }

            public void setText_day(String text_day) {
                this.text_day = text_day;
            }

            public String getCode_day() {
                return code_day;
            }

            public void setCode_day(String code_day) {
                this.code_day = code_day;
            }

            public String getText_night() {
                return text_night;
            }

            public void setText_night(String text_night) {
                this.text_night = text_night;
            }

            public String getCode_night() {
                return code_night;
            }

            public void setCode_night(String code_night) {
                this.code_night = code_night;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getPrecip() {
                return precip;
            }

            public void setPrecip(String precip) {
                this.precip = precip;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_direction_degree() {
                return wind_direction_degree;
            }

            public void setWind_direction_degree(String wind_direction_degree) {
                this.wind_direction_degree = wind_direction_degree;
            }

            public String getWind_speed() {
                return wind_speed;
            }

            public void setWind_speed(String wind_speed) {
                this.wind_speed = wind_speed;
            }

            public String getWind_scale() {
                return wind_scale;
            }

            public void setWind_scale(String wind_scale) {
                this.wind_scale = wind_scale;
            }
        }
    }
}
