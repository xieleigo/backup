package io.dcloud.H5E4CA812.view.bean;

import java.io.Serializable;

public class ReceivingAddressBean implements Serializable {
    /**
     * UserAddrID : 25
     * UserID : 3842
     * UserAddressMan :  string
     * UserAddressCountry :  string
     * UserAddressProv :  string
     * UserAddressCity :  string
     * UserAddressDistrict :  string
     * UserAddressStreet :  string
     * UserAddressDetail :  string
     * UserAddressMobile :  string
     * UserAddressPhone :  string
     * UserAddressZIPCode :  string
     * UserAddressIsDefault : 0
     * UserAddressManGender : 0
     */

    private int UserAddrID;
    private int UserID;
    private String UserAddressMan;
    private String UserAddressCountry;
    private String UserAddressProv;
    private String UserAddressCity;
    private String UserAddressDistrict;
    private String UserAddressStreet;
    private String UserAddressDetail;
    private String UserAddressMobile;
    private String UserAddressPhone;
    private String UserAddressZIPCode;
    private int UserAddressIsDefault;
    private int UserAddressManGender;

    public int getUserAddrID() {
        return UserAddrID;
    }

    public void setUserAddrID(int UserAddrID) {
        this.UserAddrID = UserAddrID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserAddressMan() {
        return UserAddressMan;
    }

    public void setUserAddressMan(String UserAddressMan) {
        this.UserAddressMan = UserAddressMan;
    }

    public String getUserAddressCountry() {
        return UserAddressCountry;
    }

    public void setUserAddressCountry(String UserAddressCountry) {
        this.UserAddressCountry = UserAddressCountry;
    }

    public String getUserAddressProv() {
        return UserAddressProv;
    }

    public void setUserAddressProv(String UserAddressProv) {
        this.UserAddressProv = UserAddressProv;
    }

    public String getUserAddressCity() {
        return UserAddressCity;
    }

    public void setUserAddressCity(String UserAddressCity) {
        this.UserAddressCity = UserAddressCity;
    }

    public String getUserAddressDistrict() {
        return UserAddressDistrict;
    }

    public void setUserAddressDistrict(String UserAddressDistrict) {
        this.UserAddressDistrict = UserAddressDistrict;
    }

    public String getUserAddressStreet() {
        return UserAddressStreet;
    }

    public void setUserAddressStreet(String UserAddressStreet) {
        this.UserAddressStreet = UserAddressStreet;
    }

    public String getUserAddressDetail() {
        return UserAddressDetail;
    }

    public void setUserAddressDetail(String UserAddressDetail) {
        this.UserAddressDetail = UserAddressDetail;
    }

    public String getUserAddressMobile() {
        return UserAddressMobile;
    }

    public void setUserAddressMobile(String UserAddressMobile) {
        this.UserAddressMobile = UserAddressMobile;
    }

    public String getUserAddressPhone() {
        return UserAddressPhone;
    }

    public void setUserAddressPhone(String UserAddressPhone) {
        this.UserAddressPhone = UserAddressPhone;
    }

    public String getUserAddressZIPCode() {
        return UserAddressZIPCode;
    }

    public void setUserAddressZIPCode(String UserAddressZIPCode) {
        this.UserAddressZIPCode = UserAddressZIPCode;
    }

    public int getUserAddressIsDefault() {
        return UserAddressIsDefault;
    }

    public void setUserAddressIsDefault(int UserAddressIsDefault) {
        this.UserAddressIsDefault = UserAddressIsDefault;
    }

    public int getUserAddressManGender() {
        return UserAddressManGender;
    }

    public void setUserAddressManGender(int UserAddressManGender) {
        this.UserAddressManGender = UserAddressManGender;
    }

    @Override
    public String toString() {
        return "ReceivingAddressBean{" +
                "UserAddrID=" + UserAddrID +
                ", UserID=" + UserID +
                ", UserAddressMan='" + UserAddressMan + '\'' +
                ", UserAddressCountry='" + UserAddressCountry + '\'' +
                ", UserAddressProv='" + UserAddressProv + '\'' +
                ", UserAddressCity='" + UserAddressCity + '\'' +
                ", UserAddressDistrict='" + UserAddressDistrict + '\'' +
                ", UserAddressStreet='" + UserAddressStreet + '\'' +
                ", UserAddressDetail='" + UserAddressDetail + '\'' +
                ", UserAddressMobile='" + UserAddressMobile + '\'' +
                ", UserAddressPhone='" + UserAddressPhone + '\'' +
                ", UserAddressZIPCode='" + UserAddressZIPCode + '\'' +
                ", UserAddressIsDefault=" + UserAddressIsDefault +
                ", UserAddressManGender=" + UserAddressManGender +
                '}';
    }
}
