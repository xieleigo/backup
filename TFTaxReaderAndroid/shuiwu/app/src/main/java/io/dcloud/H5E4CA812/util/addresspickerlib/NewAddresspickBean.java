package io.dcloud.H5E4CA812.util.addresspickerlib;

import java.util.List;

public class NewAddresspickBean {



    private int LevelID;
    private int AreaID;
    private String AreaName;
    private Object AreaAbbr;
    private int AreaSort;
    private int AreaParentID;
    private String AreaTreePath;
    private String AreaFullName;
    private List<ChildrenBeanX> children;

    @Override
    public String toString() {
        return "NewAddresspickBean{" +
                "LevelID=" + LevelID +
                ", AreaID=" + AreaID +
                ", AreaName='" + AreaName + '\'' +
                ", AreaAbbr=" + AreaAbbr +
                ", AreaSort=" + AreaSort +
                ", AreaParentID=" + AreaParentID +
                ", AreaTreePath='" + AreaTreePath + '\'' +
                ", AreaFullName='" + AreaFullName + '\'' +
                ", children=" + children +
                '}';
    }

    public int getLevelID() {
        return LevelID;
    }

    public void setLevelID(int LevelID) {
        this.LevelID = LevelID;
    }

    public int getAreaID() {
        return AreaID;
    }

    public void setAreaID(int AreaID) {
        this.AreaID = AreaID;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
    }

    public Object getAreaAbbr() {
        return AreaAbbr;
    }

    public void setAreaAbbr(Object AreaAbbr) {
        this.AreaAbbr = AreaAbbr;
    }

    public int getAreaSort() {
        return AreaSort;
    }

    public void setAreaSort(int AreaSort) {
        this.AreaSort = AreaSort;
    }

    public int getAreaParentID() {
        return AreaParentID;
    }

    public void setAreaParentID(int AreaParentID) {
        this.AreaParentID = AreaParentID;
    }

    public String getAreaTreePath() {
        return AreaTreePath;
    }

    public void setAreaTreePath(String AreaTreePath) {
        this.AreaTreePath = AreaTreePath;
    }

    public String getAreaFullName() {
        return AreaFullName;
    }

    public void setAreaFullName(String AreaFullName) {
        this.AreaFullName = AreaFullName;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public static class ChildrenBeanX {
        /**
         * LevelID : 1
         * children : [{"LevelID":2,"children":[],"AreaID":2,"AreaName":"东城区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市东城区"},{"LevelID":2,"children":[],"AreaID":3,"AreaName":"西城区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市西城区"},{"LevelID":2,"children":[],"AreaID":4,"AreaName":"朝阳区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市朝阳区"},{"LevelID":2,"children":[],"AreaID":5,"AreaName":"丰台区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市丰台区"},{"LevelID":2,"children":[],"AreaID":6,"AreaName":"石景山区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市石景山区"},{"LevelID":2,"children":[],"AreaID":7,"AreaName":"海淀区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市海淀区"},{"LevelID":2,"children":[],"AreaID":8,"AreaName":"门头沟区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市门头沟区"},{"LevelID":2,"children":[],"AreaID":9,"AreaName":"房山区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市房山区"},{"LevelID":2,"children":[],"AreaID":10,"AreaName":"通州区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市通州区"},{"LevelID":2,"children":[],"AreaID":11,"AreaName":"顺义区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市顺义区"},{"LevelID":2,"children":[],"AreaID":12,"AreaName":"昌平区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市昌平区"},{"LevelID":2,"children":[],"AreaID":13,"AreaName":"大兴区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市大兴区"},{"LevelID":2,"children":[],"AreaID":14,"AreaName":"怀柔区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市怀柔区"},{"LevelID":2,"children":[],"AreaID":15,"AreaName":"平谷区","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市平谷区"},{"LevelID":2,"children":[],"AreaID":16,"AreaName":"密云县","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市密云县"},{"LevelID":2,"children":[],"AreaID":17,"AreaName":"延庆县","AreaAbbr":null,"AreaSort":0,"AreaParentID":1,"AreaTreePath":",4000,1,","AreaFullName":"中国北京市延庆县"}]
         * AreaID : 1
         * AreaName : 北京市
         * AreaAbbr : null
         * AreaSort : 0
         * AreaParentID : 4000
         * AreaTreePath : ,4000,
         * AreaFullName : 中国北京市
         */

        private int LevelID;
        private int AreaID;
        private String AreaName;
        private Object AreaAbbr;
        private int AreaSort;
        private int AreaParentID;
        private String AreaTreePath;
        private String AreaFullName;
        private List<ChildrenBean> children;

        @Override
        public String toString() {
            return "ChildrenBeanX{" +
                    "LevelID=" + LevelID +
                    ", AreaID=" + AreaID +
                    ", AreaName='" + AreaName + '\'' +
                    ", AreaAbbr=" + AreaAbbr +
                    ", AreaSort=" + AreaSort +
                    ", AreaParentID=" + AreaParentID +
                    ", AreaTreePath='" + AreaTreePath + '\'' +
                    ", AreaFullName='" + AreaFullName + '\'' +
                    ", children=" + children +
                    '}';
        }

        public int getLevelID() {
            return LevelID;
        }

        public void setLevelID(int LevelID) {
            this.LevelID = LevelID;
        }

        public int getAreaID() {
            return AreaID;
        }

        public void setAreaID(int AreaID) {
            this.AreaID = AreaID;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public Object getAreaAbbr() {
            return AreaAbbr;
        }

        public void setAreaAbbr(Object AreaAbbr) {
            this.AreaAbbr = AreaAbbr;
        }

        public int getAreaSort() {
            return AreaSort;
        }

        public void setAreaSort(int AreaSort) {
            this.AreaSort = AreaSort;
        }

        public int getAreaParentID() {
            return AreaParentID;
        }

        public void setAreaParentID(int AreaParentID) {
            this.AreaParentID = AreaParentID;
        }

        public String getAreaTreePath() {
            return AreaTreePath;
        }

        public void setAreaTreePath(String AreaTreePath) {
            this.AreaTreePath = AreaTreePath;
        }

        public String getAreaFullName() {
            return AreaFullName;
        }

        public void setAreaFullName(String AreaFullName) {
            this.AreaFullName = AreaFullName;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * LevelID : 2
             * children : []
             * AreaID : 2
             * AreaName : 东城区
             * AreaAbbr : null
             * AreaSort : 0
             * AreaParentID : 1
             * AreaTreePath : ,4000,1,
             * AreaFullName : 中国北京市东城区
             */

            private int LevelID;
            private int AreaID;
            private String AreaName;
            private Object AreaAbbr;
            private int AreaSort;
            private int AreaParentID;
            private String AreaTreePath;
            private String AreaFullName;
            private List<ChildrenBeanZ> children;

            @Override
            public String toString() {
                return "ChildrenBean{" +
                        "LevelID=" + LevelID +
                        ", AreaID=" + AreaID +
                        ", AreaName='" + AreaName + '\'' +
                        ", AreaAbbr=" + AreaAbbr +
                        ", AreaSort=" + AreaSort +
                        ", AreaParentID=" + AreaParentID +
                        ", AreaTreePath='" + AreaTreePath + '\'' +
                        ", AreaFullName='" + AreaFullName + '\'' +
                        ", children=" + children +
                        '}';
            }

            public int getLevelID() {
                return LevelID;
            }

            public void setLevelID(int LevelID) {
                this.LevelID = LevelID;
            }

            public int getAreaID() {
                return AreaID;
            }

            public void setAreaID(int AreaID) {
                this.AreaID = AreaID;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public Object getAreaAbbr() {
                return AreaAbbr;
            }

            public void setAreaAbbr(Object AreaAbbr) {
                this.AreaAbbr = AreaAbbr;
            }

            public int getAreaSort() {
                return AreaSort;
            }

            public void setAreaSort(int AreaSort) {
                this.AreaSort = AreaSort;
            }

            public int getAreaParentID() {
                return AreaParentID;
            }

            public void setAreaParentID(int AreaParentID) {
                this.AreaParentID = AreaParentID;
            }

            public String getAreaTreePath() {
                return AreaTreePath;
            }

            public void setAreaTreePath(String AreaTreePath) {
                this.AreaTreePath = AreaTreePath;
            }

            public String getAreaFullName() {
                return AreaFullName;
            }

            public void setAreaFullName(String AreaFullName) {
                this.AreaFullName = AreaFullName;
            }

            public List<ChildrenBeanZ> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanZ> children) {
                this.children = children;
            }


            public static class ChildrenBeanZ {
                private int LevelID;
                private int AreaID;
                private String AreaName;
                private Object AreaAbbr;
                private int AreaSort;
                private int AreaParentID;
                private String AreaTreePath;
                private String AreaFullName;
                private List<?> children;

                @Override
                public String toString() {
                    return "ChildrenBeanZ{" +
                            "LevelID=" + LevelID +
                            ", AreaID=" + AreaID +
                            ", AreaName='" + AreaName + '\'' +
                            ", AreaAbbr=" + AreaAbbr +
                            ", AreaSort=" + AreaSort +
                            ", AreaParentID=" + AreaParentID +
                            ", AreaTreePath='" + AreaTreePath + '\'' +
                            ", AreaFullName='" + AreaFullName + '\'' +
                            ", children=" + children +
                            '}';
                }

                public int getLevelID() {
                    return LevelID;
                }

                public void setLevelID(int levelID) {
                    LevelID = levelID;
                }

                public int getAreaID() {
                    return AreaID;
                }

                public void setAreaID(int areaID) {
                    AreaID = areaID;
                }

                public String getAreaName() {
                    return AreaName;
                }

                public void setAreaName(String areaName) {
                    AreaName = areaName;
                }

                public Object getAreaAbbr() {
                    return AreaAbbr;
                }

                public void setAreaAbbr(Object areaAbbr) {
                    AreaAbbr = areaAbbr;
                }

                public int getAreaSort() {
                    return AreaSort;
                }

                public void setAreaSort(int areaSort) {
                    AreaSort = areaSort;
                }

                public int getAreaParentID() {
                    return AreaParentID;
                }

                public void setAreaParentID(int areaParentID) {
                    AreaParentID = areaParentID;
                }

                public String getAreaTreePath() {
                    return AreaTreePath;
                }

                public void setAreaTreePath(String areaTreePath) {
                    AreaTreePath = areaTreePath;
                }

                public String getAreaFullName() {
                    return AreaFullName;
                }

                public void setAreaFullName(String areaFullName) {
                    AreaFullName = areaFullName;
                }

                public List<?> getChildren() {
                    return children;
                }

                public void setChildren(List<?> children) {
                    this.children = children;
                }

            }

        }
    }
}
