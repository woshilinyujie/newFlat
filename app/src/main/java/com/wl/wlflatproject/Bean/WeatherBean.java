package com.wl.wlflatproject.Bean;

public class WeatherBean {
    private int cmd;
    private int ack;
    private String devType;
    private String devId;
    private String vendor;
    private int seqid;
    private String address;
    private String weather;
    private String humidity;
    private String temperature;
    private int time;

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public void setDevid(String devid) {
        this.devId = devid;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCmd() {
        return cmd;
    }

    public int getAck() {
        return ack;
    }

    public String getDevType() {
        return devType;
    }

    public String getDevId() {
        return devId;
    }

    public String getVendor() {
        return vendor;
    }

    public int getSeqid() {
        return seqid;
    }

    public String getAddress() {
        return address;
    }

    public String getWeather() {
        return weather;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public int getTime() {
        return time;
    }
}
