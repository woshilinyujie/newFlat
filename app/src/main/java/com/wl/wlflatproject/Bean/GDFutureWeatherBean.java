package com.wl.wlflatproject.Bean;

import java.util.List;

/**
 * @Project: wl_flat_android
 * @Package: com.wl.wlflatproject.Bean
 * @Author: HSL
 * @Time: 2019/08/01 14:15
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class GDFutureWeatherBean {


    /**
     * status : 1
     * count : 1
     * info : OK
     * infocode : 10000
     * forecasts : [{"city":"郑州市","adcode":"410100","province":"河南","reporttime":"2019-08-01 13:44:06","casts":[{"date":"2019-08-01","week":"4","dayweather":"中雨","nightweather":"中雨","daytemp":"33","nighttemp":"25","daywind":"东南","nightwind":"东南","daypower":"4","nightpower":"4"},{"date":"2019-08-02","week":"5","dayweather":"小雨","nightweather":"小雨","daytemp":"32","nighttemp":"25","daywind":"东","nightwind":"东","daypower":"4","nightpower":"4"},{"date":"2019-08-03","week":"6","dayweather":"小雨","nightweather":"多云","daytemp":"32","nighttemp":"24","daywind":"东北","nightwind":"东北","daypower":"4","nightpower":"4"},{"date":"2019-08-04","week":"7","dayweather":"多云","nightweather":"多云","daytemp":"33","nighttemp":"24","daywind":"东","nightwind":"东","daypower":"4","nightpower":"4"}]}]
     */

    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<ForecastsBean> forecasts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<ForecastsBean> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastsBean> forecasts) {
        this.forecasts = forecasts;
    }

    public static class ForecastsBean {
        /**
         * city : 郑州市
         * adcode : 410100
         * province : 河南
         * reporttime : 2019-08-01 13:44:06
         * casts : [{"date":"2019-08-01","week":"4","dayweather":"中雨","nightweather":"中雨","daytemp":"33","nighttemp":"25","daywind":"东南","nightwind":"东南","daypower":"4","nightpower":"4"},{"date":"2019-08-02","week":"5","dayweather":"小雨","nightweather":"小雨","daytemp":"32","nighttemp":"25","daywind":"东","nightwind":"东","daypower":"4","nightpower":"4"},{"date":"2019-08-03","week":"6","dayweather":"小雨","nightweather":"多云","daytemp":"32","nighttemp":"24","daywind":"东北","nightwind":"东北","daypower":"4","nightpower":"4"},{"date":"2019-08-04","week":"7","dayweather":"多云","nightweather":"多云","daytemp":"33","nighttemp":"24","daywind":"东","nightwind":"东","daypower":"4","nightpower":"4"}]
         */

        private String city;
        private String adcode;
        private String province;
        private String reporttime;
        private List<CastsBean> casts;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getReporttime() {
            return reporttime;
        }

        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public static class CastsBean {
            /**
             * date : 2019-08-01
             * week : 4
             * dayweather : 中雨
             * nightweather : 中雨
             * daytemp : 33
             * nighttemp : 25
             * daywind : 东南
             * nightwind : 东南
             * daypower : 4
             * nightpower : 4
             */

            private String date;
            private String week;
            private String dayweather;
            private String nightweather;
            private String daytemp;
            private String nighttemp;
            private String daywind;
            private String nightwind;
            private String daypower;
            private String nightpower;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getDayweather() {
                return dayweather;
            }

            public void setDayweather(String dayweather) {
                this.dayweather = dayweather;
            }

            public String getNightweather() {
                return nightweather;
            }

            public void setNightweather(String nightweather) {
                this.nightweather = nightweather;
            }

            public String getDaytemp() {
                return daytemp;
            }

            public void setDaytemp(String daytemp) {
                this.daytemp = daytemp;
            }

            public String getNighttemp() {
                return nighttemp;
            }

            public void setNighttemp(String nighttemp) {
                this.nighttemp = nighttemp;
            }

            public String getDaywind() {
                return daywind;
            }

            public void setDaywind(String daywind) {
                this.daywind = daywind;
            }

            public String getNightwind() {
                return nightwind;
            }

            public void setNightwind(String nightwind) {
                this.nightwind = nightwind;
            }

            public String getDaypower() {
                return daypower;
            }

            public void setDaypower(String daypower) {
                this.daypower = daypower;
            }

            public String getNightpower() {
                return nightpower;
            }

            public void setNightpower(String nightpower) {
                this.nightpower = nightpower;
            }
        }
    }
}
