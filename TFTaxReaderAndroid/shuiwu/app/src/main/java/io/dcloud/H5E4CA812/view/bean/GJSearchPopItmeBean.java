package io.dcloud.H5E4CA812.view.bean;

public class GJSearchPopItmeBean {

    /**
     * PubID : 10
     * PubName : 中国税务
     * PubMgrUnit : 国家税务总局
     * PubExeUnit : 中国税务杂志社
     */

    private int PubID;
    private String PubName;
    private String PubMgrUnit;
    private String PubExeUnit;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPubID() {
        return PubID;
    }

    public void setPubID(int PubID) {
        this.PubID = PubID;
    }

    public String getPubName() {
        return PubName;
    }

    public void setPubName(String PubName) {
        this.PubName = PubName;
    }

    public String getPubMgrUnit() {
        return PubMgrUnit;
    }

    public void setPubMgrUnit(String PubMgrUnit) {
        this.PubMgrUnit = PubMgrUnit;
    }

    public String getPubExeUnit() {
        return PubExeUnit;
    }

    public void setPubExeUnit(String PubExeUnit) {
        this.PubExeUnit = PubExeUnit;
    }

    @Override
    public String toString() {
        return "GJSearchPopItmeBean{" +
                "PubID=" + PubID +
                ", PubName='" + PubName + '\'' +
                ", PubMgrUnit='" + PubMgrUnit + '\'' +
                ", PubExeUnit='" + PubExeUnit + '\'' +
                '}';
    }
}
