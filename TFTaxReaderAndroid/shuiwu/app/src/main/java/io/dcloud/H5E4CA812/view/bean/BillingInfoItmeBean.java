package io.dcloud.H5E4CA812.view.bean;

import java.io.Serializable;

public class BillingInfoItmeBean implements Serializable {

    /**
     * UserInvoiceID : 1
     * UserID : 6
     * UserInvoiceReceiveType : 1
     * UserInvoiceTitle : 北京
     * UserInvoiceContent :
     * UserInvoicePhone : 17310271995
     * UserInvoiceEmail : null
     * UserInvoiceTaxpayerNo : 2222
     * UserInvoiceorAddress : null
     * UserInvoiceBuyerTel : null
     * UserInvoiceBuyerBankName : null
     * UserInvoiceBankAcount : null
     * UserInvoiceDefault : 1
     */

    private int UserInvoiceID;
    private int UserID;
    private int UserInvoiceReceiveType;
    private String UserInvoiceTitle;
    private String UserInvoiceContent;
    private String UserInvoicePhone;
    private Object UserInvoiceEmail;
    private String UserInvoiceTaxpayerNo;
    private Object UserInvoiceorAddress;
    private Object UserInvoiceBuyerTel;
    private Object UserInvoiceBuyerBankName;
    private Object UserInvoiceBankAcount;
    private int UserInvoiceDefault;
    private boolean isSelect = false;//是否选中
    private boolean isTransmit = false;//是否传递

    public boolean isTransmit() {
        return isTransmit;
    }

    public void setTransmit(boolean transmit) {
        isTransmit = transmit;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getUserInvoiceID() {
        return UserInvoiceID;
    }

    public void setUserInvoiceID(int UserInvoiceID) {
        this.UserInvoiceID = UserInvoiceID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getUserInvoiceReceiveType() {
        return UserInvoiceReceiveType;
    }

    public void setUserInvoiceReceiveType(int UserInvoiceReceiveType) {
        this.UserInvoiceReceiveType = UserInvoiceReceiveType;
    }

    public String getUserInvoiceTitle() {
        return UserInvoiceTitle;
    }

    public void setUserInvoiceTitle(String UserInvoiceTitle) {
        this.UserInvoiceTitle = UserInvoiceTitle;
    }

    public String getUserInvoiceContent() {
        return UserInvoiceContent;
    }

    public void setUserInvoiceContent(String UserInvoiceContent) {
        this.UserInvoiceContent = UserInvoiceContent;
    }

    public String getUserInvoicePhone() {
        return UserInvoicePhone;
    }

    public void setUserInvoicePhone(String UserInvoicePhone) {
        this.UserInvoicePhone = UserInvoicePhone;
    }

    public Object getUserInvoiceEmail() {
        return UserInvoiceEmail;
    }

    public void setUserInvoiceEmail(Object UserInvoiceEmail) {
        this.UserInvoiceEmail = UserInvoiceEmail;
    }

    public String getUserInvoiceTaxpayerNo() {
        return UserInvoiceTaxpayerNo;
    }

    public void setUserInvoiceTaxpayerNo(String UserInvoiceTaxpayerNo) {
        this.UserInvoiceTaxpayerNo = UserInvoiceTaxpayerNo;
    }

    public Object getUserInvoiceorAddress() {
        return UserInvoiceorAddress;
    }

    public void setUserInvoiceorAddress(Object UserInvoiceorAddress) {
        this.UserInvoiceorAddress = UserInvoiceorAddress;
    }

    public Object getUserInvoiceBuyerTel() {
        return UserInvoiceBuyerTel;
    }

    public void setUserInvoiceBuyerTel(Object UserInvoiceBuyerTel) {
        this.UserInvoiceBuyerTel = UserInvoiceBuyerTel;
    }

    public Object getUserInvoiceBuyerBankName() {
        return UserInvoiceBuyerBankName;
    }

    public void setUserInvoiceBuyerBankName(Object UserInvoiceBuyerBankName) {
        this.UserInvoiceBuyerBankName = UserInvoiceBuyerBankName;
    }

    public Object getUserInvoiceBankAcount() {
        return UserInvoiceBankAcount;
    }

    public void setUserInvoiceBankAcount(Object UserInvoiceBankAcount) {
        this.UserInvoiceBankAcount = UserInvoiceBankAcount;
    }

    public int getUserInvoiceDefault() {
        return UserInvoiceDefault;
    }

    public void setUserInvoiceDefault(int UserInvoiceDefault) {
        this.UserInvoiceDefault = UserInvoiceDefault;
    }
}
