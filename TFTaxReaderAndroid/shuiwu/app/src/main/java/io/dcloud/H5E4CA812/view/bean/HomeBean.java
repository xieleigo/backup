package io.dcloud.H5E4CA812.view.bean;

public class HomeBean {


    /**
     * ProdID : 896
     * ProdName : 中国税务
     * ProdYear : 1984
     * ProdIssue : 3
     * ProdAbstract : null
     * ProdForm : 10
     * ProdImg : /upload/1984/Layouts/ZS198403.Source.jpg
     * ProdIsFree : false
     */

    private int ProdID;
    private String ProdName;
    private int ProdYear;
    private int ProdIssue;
    private Object ProdAbstract;
    private int ProdForm;
    private String ProdImg;
    private boolean ProdIsFree;
    private int ReadSourceID;

    public int getReadSourceID() {
        return ReadSourceID;
    }

    public void setReadSourceID(int readSourceID) {
        ReadSourceID = readSourceID;
    }

    public int getProdID() {
        return ProdID;
    }

    public void setProdID(int ProdID) {
        this.ProdID = ProdID;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String ProdName) {
        this.ProdName = ProdName;
    }

    public int getProdYear() {
        return ProdYear;
    }

    public void setProdYear(int ProdYear) {
        this.ProdYear = ProdYear;
    }

    public int getProdIssue() {
        return ProdIssue;
    }

    public void setProdIssue(int ProdIssue) {
        this.ProdIssue = ProdIssue;
    }

    public Object getProdAbstract() {
        return ProdAbstract;
    }

    public void setProdAbstract(Object ProdAbstract) {
        this.ProdAbstract = ProdAbstract;
    }

    public int getProdForm() {
        return ProdForm;
    }

    public void setProdForm(int ProdForm) {
        this.ProdForm = ProdForm;
    }

    public String getProdImg() {
        return ProdImg;
    }

    public void setProdImg(String ProdImg) {
        this.ProdImg = ProdImg;
    }

    public boolean isProdIsFree() {
        return ProdIsFree;
    }

    public void setProdIsFree(boolean ProdIsFree) {
        this.ProdIsFree = ProdIsFree;
    }
}
