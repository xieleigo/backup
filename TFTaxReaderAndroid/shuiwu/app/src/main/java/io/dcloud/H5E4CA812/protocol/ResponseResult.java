package io.dcloud.H5E4CA812.protocol;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResponseResult implements Serializable {
    private int msgCode;
    private String msg;
    private Object data;
    private Boolean ret;
    private String access_token;
    private String expire_time;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        if (data == null) {
            return "";
        }
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getRet() {
        return ret;
    }

    public void setRet(Boolean ret) {
        this.ret = ret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "msgCode=" + msgCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", ret=" + ret +
                ", access_token='" + access_token + '\'' +
                ", expire_time='" + expire_time + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
