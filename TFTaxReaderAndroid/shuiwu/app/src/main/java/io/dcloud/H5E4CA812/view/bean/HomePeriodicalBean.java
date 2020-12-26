package io.dcloud.H5E4CA812.view.bean;

import java.util.List;

public class HomePeriodicalBean {

    /**
     * ProdID : 2495
     * ProdName : 中国税务
     * ProdYear : 2020
     * ProdIssue : 5
     * ProdAbstract : null
     * ProdForm : 10
     * ProdImg : Layouts/ZS202005.Source.jpg
     * ProdIsFree : false
     * ReadSourceID : 2495
     * ReadingTypeID : 2
     * ArticleList : [{"ArticleID":75210,"ArticleTitle":"巩固提升\u201c非接触式\u201d办税缴费努力实现常态化与长效化","ArticleAuthorName":"本刊特约评论员","ArticleEngAuthorName":null},{"ArticleID":75211,"ArticleTitle":"要闻","ArticleAuthorName":null,"ArticleEngAuthorName":null},{"ArticleID":75212,"ArticleTitle":"税收大数据搭桥解困助力鄂企复产复销","ArticleAuthorName":"郭瑞轩","ArticleEngAuthorName":null},{"ArticleID":75213,"ArticleTitle":"让\u201c非接触式\u201d办税缴费走向常态化与长效化","ArticleAuthorName":null,"ArticleEngAuthorName":null}]
     */

    private int ProdID;
    private String ProdName;
    private int ProdYear;
    private int ProdIssue;
    private Object ProdAbstract;
    private int ProdForm;
    private String ProdImg;
    private boolean ProdIsFree;
    private String ReadSourceID;
    private int ReadingTypeID;
    private List<ArticleListBean> ArticleList;

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

    public String getReadSourceID() {
        return ReadSourceID;
    }

    public void setReadSourceID(String ReadSourceID) {
        this.ReadSourceID = ReadSourceID;
    }

    public int getReadingTypeID() {
        return ReadingTypeID;
    }

    public void setReadingTypeID(int ReadingTypeID) {
        this.ReadingTypeID = ReadingTypeID;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class ArticleListBean {
        /**
         * ArticleID : 75210
         * ArticleTitle : 巩固提升“非接触式”办税缴费努力实现常态化与长效化
         * ArticleAuthorName : 本刊特约评论员
         * ArticleEngAuthorName : null
         */

        private int ArticleID;
        private String ArticleTitle;
        private String ArticleAuthorName;
        private Object ArticleEngAuthorName;

        public int getArticleID() {
            return ArticleID;
        }

        public void setArticleID(int ArticleID) {
            this.ArticleID = ArticleID;
        }

        public String getArticleTitle() {
            return ArticleTitle;
        }

        public void setArticleTitle(String ArticleTitle) {
            this.ArticleTitle = ArticleTitle;
        }

        public String getArticleAuthorName() {
            return ArticleAuthorName;
        }

        public void setArticleAuthorName(String ArticleAuthorName) {
            this.ArticleAuthorName = ArticleAuthorName;
        }

        public Object getArticleEngAuthorName() {
            return ArticleEngAuthorName;
        }

        public void setArticleEngAuthorName(Object ArticleEngAuthorName) {
            this.ArticleEngAuthorName = ArticleEngAuthorName;
        }
    }
}
