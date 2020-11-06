package com.wl.wlflatproject.Bean;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/07/29 11:01
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class FutureAirQualityBean {

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
         * daily : [{"aqi":"85","pm25":"16","pm10":"26","so2":"2","no2":"17","co":"0.499","o3":"142","date":"2019-07-29","quality":"良"},{"aqi":"117","pm25":"20","pm10":"57","so2":"3","no2":"18","co":"0.404","o3":"179","date":"2019-07-30","quality":"轻度污染"},{"aqi":"56","pm25":"9","pm10":"28","so2":"2","no2":"14","co":"0.247","o3":"107","date":"2019-07-31","quality":"良"},{"aqi":"115","pm25":"17","pm10":"28","so2":"2","no2":"17","co":"0.438","o3":"176","date":"2019-08-01","quality":"轻度污染"},{"aqi":"96","pm25":"23","pm10":"37","so2":"1","no2":"19","co":"0.659","o3":"156","date":"2019-08-02","quality":"良"}]
         * last_update : 2019-07-29T00:15:46+08:00
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
             * aqi : 85
             * pm25 : 16
             * pm10 : 26
             * so2 : 2
             * no2 : 17
             * co : 0.499
             * o3 : 142
             * date : 2019-07-29
             * quality : 良
             */

            private String aqi;
            private String pm25;
            private String pm10;
            private String so2;
            private String no2;
            private String co;
            private String o3;
            private String date;
            private String quality;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }
        }
    }
}
