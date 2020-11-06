package com.wl.wlflatproject.Bean;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/07/29 12:09
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class Weather24HourBean {


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
         * hourly : [{"time":"2019-07-29T12:00:00+08:00","text":"雷阵雨","code":"11","temperature":"29","humidity":"79","wind_direction":"西北","wind_speed":"11.88"},{"time":"2019-07-29T13:00:00+08:00","text":"雷阵雨","code":"11","temperature":"29","humidity":"71","wind_direction":"西北","wind_speed":"14.04"},{"time":"2019-07-29T14:00:00+08:00","text":"阴","code":"9","temperature":"29","humidity":"64","wind_direction":"西","wind_speed":"16.20"},{"time":"2019-07-29T15:00:00+08:00","text":"雷阵雨","code":"11","temperature":"28","humidity":"64","wind_direction":"东南","wind_speed":"15.84"},{"time":"2019-07-29T16:00:00+08:00","text":"雷阵雨","code":"11","temperature":"28","humidity":"64","wind_direction":"西北","wind_speed":"11.52"},{"time":"2019-07-29T17:00:00+08:00","text":"阴","code":"9","temperature":"27","humidity":"65","wind_direction":"西","wind_speed":"8.64"},{"time":"2019-07-29T18:00:00+08:00","text":"阴","code":"9","temperature":"27","humidity":"67","wind_direction":"东南","wind_speed":"17.64"},{"time":"2019-07-29T19:00:00+08:00","text":"阴","code":"9","temperature":"27","humidity":"69","wind_direction":"东南","wind_speed":"9.00"},{"time":"2019-07-29T20:00:00+08:00","text":"阴","code":"9","temperature":"26","humidity":"71","wind_direction":"西","wind_speed":"8.28"},{"time":"2019-07-29T21:00:00+08:00","text":"阴","code":"9","temperature":"26","humidity":"74","wind_direction":"西南","wind_speed":"14.40"},{"time":"2019-07-29T22:00:00+08:00","text":"阴","code":"9","temperature":"26","humidity":"77","wind_direction":"西南","wind_speed":"23.76"},{"time":"2019-07-29T23:00:00+08:00","text":"阴","code":"9","temperature":"26","humidity":"80","wind_direction":"西南","wind_speed":"14.40"},{"time":"2019-07-30T00:00:00+08:00","text":"多云","code":"4","temperature":"25","humidity":"81","wind_direction":"西南","wind_speed":"13.68"},{"time":"2019-07-30T01:00:00+08:00","text":"多云","code":"4","temperature":"25","humidity":"83","wind_direction":"西南","wind_speed":"13.68"},{"time":"2019-07-30T02:00:00+08:00","text":"晴","code":"1","temperature":"25","humidity":"84","wind_direction":"西南","wind_speed":"16.20"},{"time":"2019-07-30T03:00:00+08:00","text":"晴","code":"1","temperature":"24","humidity":"87","wind_direction":"西南","wind_speed":"10.44"},{"time":"2019-07-30T04:00:00+08:00","text":"晴","code":"1","temperature":"24","humidity":"90","wind_direction":"西南","wind_speed":"13.68"},{"time":"2019-07-30T05:00:00+08:00","text":"晴","code":"1","temperature":"24","humidity":"93","wind_direction":"西南","wind_speed":"11.52"},{"time":"2019-07-30T06:00:00+08:00","text":"晴","code":"0","temperature":"25","humidity":"84","wind_direction":"西南","wind_speed":"9.36"},{"time":"2019-07-30T07:00:00+08:00","text":"晴","code":"0","temperature":"26","humidity":"76","wind_direction":"西南","wind_speed":"6.84"},{"time":"2019-07-30T08:00:00+08:00","text":"多云","code":"4","temperature":"27","humidity":"67","wind_direction":"西南","wind_speed":"4.68"},{"time":"2019-07-30T09:00:00+08:00","text":"晴","code":"0","temperature":"29","humidity":"61","wind_direction":"西南","wind_speed":"6.12"},{"time":"2019-07-30T10:00:00+08:00","text":"晴","code":"0","temperature":"31","humidity":"55","wind_direction":"西南","wind_speed":"7.20"},{"time":"2019-07-30T11:00:00+08:00","text":"晴","code":"0","temperature":"32","humidity":"48","wind_direction":"西南","wind_speed":"8.64"}]
         */

        private LocationBean location;
        private List<HourlyBean> hourly;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
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

        public static class HourlyBean {
            /**
             * time : 2019-07-29T12:00:00+08:00
             * text : 雷阵雨
             * code : 11
             * temperature : 29
             * humidity : 79
             * wind_direction : 西北
             * wind_speed : 11.88
             */

            private String time;
            private String text;
            private String code;
            private String temperature;
            private String humidity;
            private String wind_direction;
            private String wind_speed;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_speed() {
                return wind_speed;
            }

            public void setWind_speed(String wind_speed) {
                this.wind_speed = wind_speed;
            }
        }
    }
}
