package io.dcloud.H5E4CA812.view.bean;

import java.io.Serializable;

public class ArticleBean implements Serializable {

    /**
     * ArticleID : 28842
     * PubID : 10
     * ArticleTitle : 他受到了法律的制裁
     * ArticleAuthorName : 沈阳市税务局监察室,阎明厚
     * ArticleEngAuthorName : null
     * PubIssueName : 中国税务
     * PubIssueYear : 1985
     * PubIssueNum : 1
     */

    private int ArticleID;
    private int PubID;
    private String ArticleTitle;
    private String ArticleAuthorName;
    private Object ArticleEngAuthorName;
    private String PubIssueName;
    private int PubIssueYear;
    private int PubIssueNum;

    public int getArticleID() {
        return ArticleID;
    }

    public void setArticleID(int ArticleID) {
        this.ArticleID = ArticleID;
    }

    public int getPubID() {
        return PubID;
    }

    public void setPubID(int PubID) {
        this.PubID = PubID;
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

    public String getPubIssueName() {
        return PubIssueName;
    }

    public void setPubIssueName(String PubIssueName) {
        this.PubIssueName = PubIssueName;
    }

    public int getPubIssueYear() {
        return PubIssueYear;
    }

    public void setPubIssueYear(int PubIssueYear) {
        this.PubIssueYear = PubIssueYear;
    }

    public int getPubIssueNum() {
        return PubIssueNum;
    }

    public void setPubIssueNum(int PubIssueNum) {
        this.PubIssueNum = PubIssueNum;
    }
}
