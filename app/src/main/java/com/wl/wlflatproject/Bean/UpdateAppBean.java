package com.wl.wlflatproject.Bean;

public class UpdateAppBean {

    /**
     * PUS : {"header":{"api_version":"1.0","return_string":"RETURN_SUCCESS_OK_STRING","seq_id":"1","http_code":"200","message_type":"MSG_PRODUCT_UPGRADE_DOWN_RSP"},"body":{"endpoint_type":"WL025S1","force_upgrade":"false","new_version":"2","url":"http://download.wonlycloud.com/general/android/smarthome_wl-release-v3.2.2.apk","platform":"android","vendor_name":"general"}}
     */

    private PUSBean PUS;

    public PUSBean getPUS() {
        return PUS;
    }

    public void setPUS(PUSBean PUS) {
        this.PUS = PUS;
    }

    public static class PUSBean {
        /**
         * header : {"api_version":"1.0","return_string":"RETURN_SUCCESS_OK_STRING","seq_id":"1","http_code":"200","message_type":"MSG_PRODUCT_UPGRADE_DOWN_RSP"}
         * body : {"endpoint_type":"WL025S1","force_upgrade":"false","new_version":"2","url":"http://download.wonlycloud.com/general/android/smarthome_wl-release-v3.2.2.apk","platform":"android","vendor_name":"general"}
         */

        private HeaderBean header;
        private BodyBean body;

        public HeaderBean getHeader() {
            return header;
        }

        public void setHeader(HeaderBean header) {
            this.header = header;
        }

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        public static class HeaderBean {
            /**
             * api_version : 1.0
             * return_string : RETURN_SUCCESS_OK_STRING
             * seq_id : 1
             * http_code : 200
             * message_type : MSG_PRODUCT_UPGRADE_DOWN_RSP
             */

            private String api_version;
            private String return_string;
            private String seq_id;
            private String http_code;
            private String message_type;

            public String getApi_version() {
                return api_version;
            }

            public void setApi_version(String api_version) {
                this.api_version = api_version;
            }

            public String getReturn_string() {
                return return_string;
            }

            public void setReturn_string(String return_string) {
                this.return_string = return_string;
            }

            public String getSeq_id() {
                return seq_id;
            }

            public void setSeq_id(String seq_id) {
                this.seq_id = seq_id;
            }

            public String getHttp_code() {
                return http_code;
            }

            public void setHttp_code(String http_code) {
                this.http_code = http_code;
            }

            public String getMessage_type() {
                return message_type;
            }

            public void setMessage_type(String message_type) {
                this.message_type = message_type;
            }
        }

        public static class BodyBean {
            /**
             * endpoint_type : WL025S1
             * force_upgrade : false
             * new_version : 2
             * url : http://download.wonlycloud.com/general/android/smarthome_wl-release-v3.2.2.apk
             * platform : android
             * vendor_name : general
             */

            private String endpoint_type;
            private String force_upgrade;
            private String new_version;
            private String url;
            private String platform;
            private String vendor_name;

            public String getEndpoint_type() {
                return endpoint_type;
            }

            public void setEndpoint_type(String endpoint_type) {
                this.endpoint_type = endpoint_type;
            }

            public String getForce_upgrade() {
                return force_upgrade;
            }

            public void setForce_upgrade(String force_upgrade) {
                this.force_upgrade = force_upgrade;
            }

            public String getNew_version() {
                return new_version;
            }

            public void setNew_version(String new_version) {
                this.new_version = new_version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getVendor_name() {
                return vendor_name;
            }

            public void setVendor_name(String vendor_name) {
                this.vendor_name = vendor_name;
            }
        }
    }
}
