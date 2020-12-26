package io.dcloud.H5E4CA812.view.bean;

public class WXRequestBean {

    /**
     * orderPKID : 270
     * OrderID : 637292131541415011
     * appid : wx8592574799e9e9ba
     * mch_id : 1503726251
     * nonce_str : XRsTFHHh9p4DmyGV
     * sign : D1BF18FAD9347D7BC8CF19D6B6D0A631CDF2BCD6B4BE18D32D541862F118E972
     * timeStamp : 1593587554
     * appSign : 397c903e956d52a870700c45d5f49e0a920e50ee4605307a166e79964b8e340f
     * result_code : SUCCESS
     * trade_type : APP
     * prepay_id : wx01151213043539514b1c50161346379600
     */

    private int orderPKID;
    private String OrderID;
    private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String timeStamp;
    private String appSign;
    private String result_code;
    private String trade_type;
    private String prepay_id;

    public int getOrderPKID() {
        return orderPKID;
    }

    public void setOrderPKID(int orderPKID) {
        this.orderPKID = orderPKID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppSign() {
        return appSign;
    }

    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    @Override
    public String toString() {
        return "WXRequestBean{" +
                "orderPKID=" + orderPKID +
                ", OrderID='" + OrderID + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", appSign='" + appSign + '\'' +
                ", result_code='" + result_code + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", prepay_id='" + prepay_id + '\'' +
                '}';
    }
}
