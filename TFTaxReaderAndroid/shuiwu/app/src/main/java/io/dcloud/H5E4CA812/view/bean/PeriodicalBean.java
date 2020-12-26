package io.dcloud.H5E4CA812.view.bean;

import java.util.List;

/**
 * 期刊实体类
 */
public class PeriodicalBean {

    /**
     * PubIssueID : 918
     * PubIssueName : 税务研究
     * PubIssueYear : 1986
     * PubIssueSumNum : 10
     * PubIssueIsFree : false
     * PubIssueCoverPath : /upload/1986/Layouts/SY198603.Source.jpg
     * ArticleList : [{"ArticleID":29362,"ArticleTitle":"关于进一步完善税制若干问题的探讨","ArticleAuthorName":"于文弘","ArticleEngAuthorName":null},{"ArticleID":29363,"ArticleTitle":"试论我国社会主义税制结构的模式问题","ArticleAuthorName":"侯梦蟾","ArticleEngAuthorName":null},{"ArticleID":29364,"ArticleTitle":"社会主义税收征纳关系的探讨","ArticleAuthorName":"李大明","ArticleEngAuthorName":null},{"ArticleID":29365,"ArticleTitle":"关于公平税负几个问题的浅析","ArticleAuthorName":"陈大杰,刘楚汉","ArticleEngAuthorName":null}]
     */

    private int PubIssueID;
    private int PubIssueNum;
    private String PubIssueName;
    private int PubIssueYear;
    private int PubIssueSumNum;
    private boolean PubIssueIsFree;
    private String PubIssueCoverPath;

    private List<ArticleListBean> ArticleList;

    public int getPubIssueNum() {
        return PubIssueNum;
    }

    public void setPubIssueNum(int pubIssueNum) {
        PubIssueNum = pubIssueNum;
    }

    public int getPubIssueID() {
        return PubIssueID;
    }

    public void setPubIssueID(int PubIssueID) {
        this.PubIssueID = PubIssueID;
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

    public int getPubIssueSumNum() {
        return PubIssueSumNum;
    }

    public void setPubIssueSumNum(int PubIssueSumNum) {
        this.PubIssueSumNum = PubIssueSumNum;
    }

    public boolean isPubIssueIsFree() {
        return PubIssueIsFree;
    }

    public void setPubIssueIsFree(boolean PubIssueIsFree) {
        this.PubIssueIsFree = PubIssueIsFree;
    }

    public String getPubIssueCoverPath() {
        return PubIssueCoverPath;
    }

    public void setPubIssueCoverPath(String PubIssueCoverPath) {
        this.PubIssueCoverPath = PubIssueCoverPath;
    }

    public List<ArticleListBean> getArticleList() {
        return ArticleList;
    }

    public void setArticleList(List<ArticleListBean> ArticleList) {
        this.ArticleList = ArticleList;
    }

    public static class ArticleListBean {
        /**
         * ArticleID : 29362
         * ArticleTitle : 关于进一步完善税制若干问题的探讨
         * ArticleAuthorName : 于文弘
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
