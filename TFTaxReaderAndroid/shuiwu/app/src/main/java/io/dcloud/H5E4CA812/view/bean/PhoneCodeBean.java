package io.dcloud.H5E4CA812.view.bean;

public class PhoneCodeBean {

    /**
     * en : Angola
     * cn : 安哥拉
     * code : +0244
     */

    private String en;
    private String cn;
    private String code;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PhoneCodeBean{" +
                "en='" + en + '\'' +
                ", cn='" + cn + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
