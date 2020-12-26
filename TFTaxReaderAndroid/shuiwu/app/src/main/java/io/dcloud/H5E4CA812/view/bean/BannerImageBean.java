package io.dcloud.H5E4CA812.view.bean;

public class BannerImageBean {

    /**
     * ImgSRC : http://210.12.84.109/Content/img/home_images/rotation.png
     * ImgURL : #
     */

    private String ImgSRC;
    private String ImgURL;

    public String getImgSRC() {
        return ImgSRC;
    }

    public void setImgSRC(String ImgSRC) {
        this.ImgSRC = ImgSRC;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String ImgURL) {
        this.ImgURL = ImgURL;
    }

    @Override
    public String toString() {
        return "BannerImageBean{" +
                "ImgSRC='" + ImgSRC + '\'' +
                ", ImgURL='" + ImgURL + '\'' +
                '}';
    }
}
