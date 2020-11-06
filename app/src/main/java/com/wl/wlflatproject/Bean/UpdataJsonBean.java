package com.wl.wlflatproject.Bean;

public class UpdataJsonBean {

    /**
     * PUS : {"header":{"api_version":"1.0","message_type":"MSG_PRODUCT_UPGRADE_DOWN_REQ","seq_id":"1"},"body":{"token":"xxxxxxxxxxxxxxx","vendor_name":"general","platform":"android","endpoint_type":"z501","current_version":"1.0.0"}}
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
         * header : {"api_version":"1.0","message_type":"MSG_PRODUCT_UPGRADE_DOWN_REQ","seq_id":"1"}
         * body : {"token":"xxxxxxxxxxxxxxx","vendor_name":"general","platform":"android","endpoint_type":"z501","current_version":"1.0.0"}
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
             * message_type : MSG_PRODUCT_UPGRADE_DOWN_REQ
             * seq_id : 1
             */

            private String api_version;
            private String message_type;
            private String seq_id;

            public String getApi_version() {
                return api_version;
            }

            public void setApi_version(String api_version) {
                this.api_version = api_version;
            }

            public String getMessage_type() {
                return message_type;
            }

            public void setMessage_type(String message_type) {
                this.message_type = message_type;
            }

            public String getSeq_id() {
                return seq_id;
            }

            public void setSeq_id(String seq_id) {
                this.seq_id = seq_id;
            }
        }

        public static class BodyBean {
            /**
             * token : xxxxxxxxxxxxxxx
             * vendor_name : general
             * platform : android
             * endpoint_type : z501
             * current_version : 1.0.0
             */

            private String token;
            private String vendor_name;
            private String platform;
            private String endpoint_type;
            private String current_version;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getVendor_name() {
                return vendor_name;
            }

            public void setVendor_name(String vendor_name) {
                this.vendor_name = vendor_name;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getEndpoint_type() {
                return endpoint_type;
            }

            public void setEndpoint_type(String endpoint_type) {
                this.endpoint_type = endpoint_type;
            }

            public String getCurrent_version() {
                return current_version;
            }

            public void setCurrent_version(String current_version) {
                this.current_version = current_version;
            }
        }
    }
}
