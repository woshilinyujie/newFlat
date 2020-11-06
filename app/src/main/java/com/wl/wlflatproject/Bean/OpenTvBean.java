package com.wl.wlflatproject.Bean;

public class OpenTvBean {
    private int cmd;
    private int ack;
    private String devType;
    private String devid;
    private String vendor;
    private int seqid;
    private int act;

    public int getCmd() {
        return cmd;
    }

    public int getAck() {
        return ack;
    }

    public String getDevType() {
        return devType;
    }

    public String getDevid() {
        return devid;
    }

    public String getVendor() {
        return vendor;
    }

    public int getSeqid() {
        return seqid;
    }

    public int getAct() {
        return act;
    }

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
        this.devid = devid;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }

    public void setAct(int act) {
        this.act = act;
    }
}
