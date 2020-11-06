package com.wl.wlflatproject.Bean;

public class StateBean {

    /**
     * cmd : 0x46
     * ack : 0
     * devType : WonlyNBLock
     * devId : xxxxx
     * vendor : general
     * seqId : 1
     * time : 1xxxxxxxx
     */

    private int cmd;
    private int ack;
    private String devType;
    private String devId;
    private String vendor;
    private int seqId;
    private long time;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
