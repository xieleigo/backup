package io.dcloud.H5E4CA812.protocol;
//{"data":{"task":"1","org":"1","blackTime":0,"now":1550734102158,"threshold":0.33,"version":"0"},"message":"验证成功","resultCode":0}
public class HeartbeatBean {
    public String task;
    public String org;
    public String blackTime;
    public String now;
    public String threshold;
    public String version;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(String blackTime) {
        this.blackTime = blackTime;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
