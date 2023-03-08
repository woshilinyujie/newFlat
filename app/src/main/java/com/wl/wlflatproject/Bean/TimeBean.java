package com.wl.wlflatproject.Bean;

public class TimeBean {

    private Integer code;
    private String msg;
    private String note;
    private DataBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private Long millisecond;
        private Integer second;

        public Long getMillisecond() {
            return millisecond;
        }

        public void setMillisecond(Long millisecond) {
            this.millisecond = millisecond;
        }

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }
    }
}
