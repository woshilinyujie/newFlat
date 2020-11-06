package com.wl.wlflatproject.Bean;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/07/29 14:16
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class Air24HourBean {


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
         * hourly : [{"aqi":"66","pm25":"4","pm10":"8","so2":"1","no2":"7","co":"0.298","o3":"137","time":"2019-07-29T14:00:00+08:00","quality":"良"},{"aqi":"63","pm25":"5","pm10":"11","so2":"1","no2":"9","co":"0.329","o3":"138","time":"2019-07-29T15:00:00+08:00","quality":"良"},{"aqi":"59","pm25":"7","pm10":"14","so2":"1","no2":"11","co":"0.348","o3":"131","time":"2019-07-29T16:00:00+08:00","quality":"良"},{"aqi":"57","pm25":"9","pm10":"18","so2":"1","no2":"16","co":"0.370","o3":"119","time":"2019-07-29T17:00:00+08:00","quality":"良"},{"aqi":"56","pm25":"12","pm10":"23","so2":"1","no2":"22","co":"0.413","o3":"109","time":"2019-07-29T18:00:00+08:00","quality":"良"},{"aqi":"55","pm25":"14","pm10":"28","so2":"1","no2":"27","co":"0.453","o3":"105","time":"2019-07-29T19:00:00+08:00","quality":"良"},{"aqi":"55","pm25":"15","pm10":"27","so2":"1","no2":"26","co":"0.457","o3":"106","time":"2019-07-29T20:00:00+08:00","quality":"良"},{"aqi":"54","pm25":"14","pm10":"24","so2":"1","no2":"23","co":"0.413","o3":"107","time":"2019-07-29T21:00:00+08:00","quality":"良"},{"aqi":"52","pm25":"13","pm10":"21","so2":"1","no2":"21","co":"0.377","o3":"106","time":"2019-07-29T22:00:00+08:00","quality":"良"},{"aqi":"51","pm25":"12","pm10":"19","so2":"1","no2":"18","co":"0.333","o3":"109","time":"2019-07-29T23:00:00+08:00","quality":"良"},{"aqi":"49","pm25":"12","pm10":"19","so2":"1","no2":"16","co":"0.322","o3":"108","time":"2019-07-30T00:00:00+08:00","quality":"优"},{"aqi":"48","pm25":"12","pm10":"20","so2":"1","no2":"16","co":"0.319","o3":"106","time":"2019-07-30T01:00:00+08:00","quality":"优"},{"aqi":"49","pm25":"13","pm10":"23","so2":"1","no2":"16","co":"0.323","o3":"103","time":"2019-07-30T02:00:00+08:00","quality":"优"},{"aqi":"50","pm25":"14","pm10":"24","so2":"2","no2":"16","co":"0.331","o3":"100","time":"2019-07-30T03:00:00+08:00","quality":"优"},{"aqi":"54","pm25":"16","pm10":"27","so2":"2","no2":"17","co":"0.341","o3":"96","time":"2019-07-30T04:00:00+08:00","quality":"良"},{"aqi":"62","pm25":"18","pm10":"30","so2":"2","no2":"20","co":"0.357","o3":"92","time":"2019-07-30T05:00:00+08:00","quality":"良"},{"aqi":"73","pm25":"19","pm10":"34","so2":"2","no2":"24","co":"0.388","o3":"89","time":"2019-07-30T06:00:00+08:00","quality":"良"},{"aqi":"85","pm25":"19","pm10":"35","so2":"2","no2":"25","co":"0.405","o3":"89","time":"2019-07-30T07:00:00+08:00","quality":"良"},{"aqi":"98","pm25":"18","pm10":"35","so2":"2","no2":"22","co":"0.403","o3":"98","time":"2019-07-30T08:00:00+08:00","quality":"良"},{"aqi":"108","pm25":"19","pm10":"42","so2":"3","no2":"21","co":"0.441","o3":"104","time":"2019-07-30T09:00:00+08:00","quality":"轻度污染"},{"aqi":"116","pm25":"21","pm10":"51","so2":"4","no2":"20","co":"0.488","o3":"119","time":"2019-07-30T10:00:00+08:00","quality":"轻度污染"},{"aqi":"117","pm25":"23","pm10":"60","so2":"5","no2":"16","co":"0.511","o3":"148","time":"2019-07-30T11:00:00+08:00","quality":"轻度污染"},{"aqi":"112","pm25":"25","pm10":"75","so2":"5","no2":"14","co":"0.511","o3":"174","time":"2019-07-30T12:00:00+08:00","quality":"轻度污染"},{"aqi":"105","pm25":"26","pm10":"90","so2":"5","no2":"12","co":"0.505","o3":"198","time":"2019-07-30T13:00:00+08:00","quality":"轻度污染"},{"aqi":"94","pm25":"27","pm10":"99","so2":"5","no2":"10","co":"0.480","o3":"208","time":"2019-07-30T14:00:00+08:00","quality":"良"},{"aqi":"83","pm25":"24","pm10":"103","so2":"4","no2":"9","co":"0.437","o3":"207","time":"2019-07-30T15:00:00+08:00","quality":"良"},{"aqi":"75","pm25":"22","pm10":"95","so2":"4","no2":"9","co":"0.396","o3":"195","time":"2019-07-30T16:00:00+08:00","quality":"良"},{"aqi":"70","pm25":"19","pm10":"88","so2":"3","no2":"10","co":"0.353","o3":"170","time":"2019-07-30T17:00:00+08:00","quality":"良"},{"aqi":"63","pm25":"15","pm10":"76","so2":"2","no2":"13","co":"0.316","o3":"127","time":"2019-07-30T18:00:00+08:00","quality":"良"},{"aqi":"60","pm25":"16","pm10":"71","so2":"2","no2":"17","co":"0.346","o3":"111","time":"2019-07-30T19:00:00+08:00","quality":"良"},{"aqi":"60","pm25":"19","pm10":"71","so2":"3","no2":"26","co":"0.404","o3":"100","time":"2019-07-30T20:00:00+08:00","quality":"良"},{"aqi":"61","pm25":"25","pm10":"72","so2":"4","no2":"33","co":"0.468","o3":"95","time":"2019-07-30T21:00:00+08:00","quality":"良"},{"aqi":"60","pm25":"25","pm10":"70","so2":"4","no2":"28","co":"0.442","o3":"96","time":"2019-07-30T22:00:00+08:00","quality":"良"},{"aqi":"58","pm25":"24","pm10":"66","so2":"4","no2":"25","co":"0.415","o3":"87","time":"2019-07-30T23:00:00+08:00","quality":"良"},{"aqi":"54","pm25":"21","pm10":"61","so2":"4","no2":"22","co":"0.376","o3":"75","time":"2019-07-31T00:00:00+08:00","quality":"良"},{"aqi":"46","pm25":"14","pm10":"48","so2":"2","no2":"15","co":"0.289","o3":"65","time":"2019-07-31T01:00:00+08:00","quality":"优"},{"aqi":"38","pm25":"9","pm10":"36","so2":"1","no2":"9","co":"0.175","o3":"48","time":"2019-07-31T02:00:00+08:00","quality":"优"},{"aqi":"34","pm25":"7","pm10":"30","so2":"1","no2":"8","co":"0.155","o3":"42","time":"2019-07-31T03:00:00+08:00","quality":"优"},{"aqi":"31","pm25":"6","pm10":"25","so2":"1","no2":"9","co":"0.164","o3":"43","time":"2019-07-31T04:00:00+08:00","quality":"优"},{"aqi":"35","pm25":"6","pm10":"22","so2":"1","no2":"12","co":"0.185","o3":"44","time":"2019-07-31T05:00:00+08:00","quality":"优"},{"aqi":"39","pm25":"8","pm10":"22","so2":"1","no2":"15","co":"0.229","o3":"44","time":"2019-07-31T06:00:00+08:00","quality":"优"},{"aqi":"43","pm25":"10","pm10":"25","so2":"1","no2":"17","co":"0.274","o3":"46","time":"2019-07-31T07:00:00+08:00","quality":"优"},{"aqi":"47","pm25":"10","pm10":"27","so2":"1","no2":"15","co":"0.270","o3":"56","time":"2019-07-31T08:00:00+08:00","quality":"优"},{"aqi":"51","pm25":"9","pm10":"28","so2":"1","no2":"12","co":"0.253","o3":"70","time":"2019-07-31T09:00:00+08:00","quality":"良"},{"aqi":"54","pm25":"8","pm10":"27","so2":"1","no2":"10","co":"0.239","o3":"87","time":"2019-07-31T10:00:00+08:00","quality":"良"},{"aqi":"56","pm25":"7","pm10":"26","so2":"1","no2":"8","co":"0.221","o3":"103","time":"2019-07-31T11:00:00+08:00","quality":"良"},{"aqi":"54","pm25":"5","pm10":"24","so2":"1","no2":"6","co":"0.202","o3":"109","time":"2019-07-31T12:00:00+08:00","quality":"良"},{"aqi":"51","pm25":"5","pm10":"23","so2":"1","no2":"5","co":"0.195","o3":"109","time":"2019-07-31T13:00:00+08:00","quality":"良"}]
         * last_update : 2019-07-29T00:17:14+08:00
         */

        private LocationBean location;
        private String last_update;
        private List<HourlyBean> hourly;

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
             * aqi : 66
             * pm25 : 4
             * pm10 : 8
             * so2 : 1
             * no2 : 7
             * co : 0.298
             * o3 : 137
             * time : 2019-07-29T14:00:00+08:00
             * quality : 良
             */

            private String aqi;
            private String pm25;
            private String pm10;
            private String so2;
            private String no2;
            private String co;
            private String o3;
            private String time;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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
