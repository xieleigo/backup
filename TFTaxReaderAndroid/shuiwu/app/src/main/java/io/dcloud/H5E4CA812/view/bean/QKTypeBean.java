package io.dcloud.H5E4CA812.view.bean;

import java.util.List;

public class QKTypeBean {

    /**
     * children : [{"children":[],"ReadTypeID":12,"ReadTypeName":"测试二级","ReadTypeSort":0,"ReadTypeParentID":11,"ReadTypeTreePath":"11,12","ReadTypeCode":"2222"}]
     * ReadTypeID : 11
     * ReadTypeName : 测试数据
     * ReadTypeSort : 0
     * ReadTypeParentID : 0
     * ReadTypeTreePath : 11
     * ReadTypeCode : 123
     */

    private int ReadTypeID;
    private String ReadTypeName;
    private int ReadTypeSort;
    private int ReadTypeParentID;
    private String ReadTypeTreePath;
    private String ReadTypeCode;
    private List<ChildrenBean> children;

    public int getReadTypeID() {
        return ReadTypeID;
    }

    public void setReadTypeID(int ReadTypeID) {
        this.ReadTypeID = ReadTypeID;
    }

    public String getReadTypeName() {
        return ReadTypeName;
    }

    public void setReadTypeName(String ReadTypeName) {
        this.ReadTypeName = ReadTypeName;
    }

    public int getReadTypeSort() {
        return ReadTypeSort;
    }

    public void setReadTypeSort(int ReadTypeSort) {
        this.ReadTypeSort = ReadTypeSort;
    }

    public int getReadTypeParentID() {
        return ReadTypeParentID;
    }

    public void setReadTypeParentID(int ReadTypeParentID) {
        this.ReadTypeParentID = ReadTypeParentID;
    }

    public String getReadTypeTreePath() {
        return ReadTypeTreePath;
    }

    public void setReadTypeTreePath(String ReadTypeTreePath) {
        this.ReadTypeTreePath = ReadTypeTreePath;
    }

    public String getReadTypeCode() {
        return ReadTypeCode;
    }

    public void setReadTypeCode(String ReadTypeCode) {
        this.ReadTypeCode = ReadTypeCode;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * children : []
         * ReadTypeID : 12
         * ReadTypeName : 测试二级
         * ReadTypeSort : 0
         * ReadTypeParentID : 11
         * ReadTypeTreePath : 11,12
         * ReadTypeCode : 2222
         */

        private int ReadTypeID;
        private String ReadTypeName;
        private int ReadTypeSort;
        private int ReadTypeParentID;
        private String ReadTypeTreePath;
        private String ReadTypeCode;
        private List<?> children;

        public int getReadTypeID() {
            return ReadTypeID;
        }

        public void setReadTypeID(int ReadTypeID) {
            this.ReadTypeID = ReadTypeID;
        }

        public String getReadTypeName() {
            return ReadTypeName;
        }

        public void setReadTypeName(String ReadTypeName) {
            this.ReadTypeName = ReadTypeName;
        }

        public int getReadTypeSort() {
            return ReadTypeSort;
        }

        public void setReadTypeSort(int ReadTypeSort) {
            this.ReadTypeSort = ReadTypeSort;
        }

        public int getReadTypeParentID() {
            return ReadTypeParentID;
        }

        public void setReadTypeParentID(int ReadTypeParentID) {
            this.ReadTypeParentID = ReadTypeParentID;
        }

        public String getReadTypeTreePath() {
            return ReadTypeTreePath;
        }

        public void setReadTypeTreePath(String ReadTypeTreePath) {
            this.ReadTypeTreePath = ReadTypeTreePath;
        }

        public String getReadTypeCode() {
            return ReadTypeCode;
        }

        public void setReadTypeCode(String ReadTypeCode) {
            this.ReadTypeCode = ReadTypeCode;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }
}
