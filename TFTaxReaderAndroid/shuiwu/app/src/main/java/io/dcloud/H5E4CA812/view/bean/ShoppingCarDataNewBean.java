package io.dcloud.H5E4CA812.view.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShoppingCarDataNewBean implements Cloneable, Parcelable {

    /**
     * addDate : 2020-05-26
     * data : [{"CartItemID":61,"ProductCount":1,"Product":{"ProdID":896,"ProdName":"中国税务","ProdYear":1984,"ProdIssue":3,"ProdAbstract":null,"ProdForm":10,"ProdImg":"/upload/1984/Layouts/ZS198403.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":0,"ProdIOSPrice":0,"ProdIsRCMD":1,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":11,"ProdUpdateDate":"2020-05-20 02:14:24","ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"896","ReadSourceURL":"/Journal/Detail/id/896","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":null,"ProdSumIssue":0}},{"Product":{"ProdID":28623,"ProdName":"利改税第二步改革是城市经济改革的重大突破","ProdYear":1984,"ProdIssue":1,"ProdAbstract":null,"ProdForm":10,"ProdImg":"/upload/1984/Layouts/ZS198401.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":1.6,"ProdIOSPrice":1.6,"ProdIsRCMD":1,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":0,"ProdUpdateDate":null,"ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"28623","ReadSourceURL":"/Journal/Article/id/28623","ProdLevel":1,"ReadSourceType":30,"ProdType":10,"ProdShowStatus":0,"ProdIsFree":false,"ProdAuthorName":"顾树祯","ProdSumIssue":0},"CartItemID":54,"ProductCount":7}]
     */

    private String addDate;
    private List<DataBean> data;
    private boolean isSelect_shop = false;

    public ShoppingCarDataNewBean(Parcel in) {
        addDate = in.readString();
        isSelect_shop = in.readByte() != 0;
    }

    public ShoppingCarDataNewBean() {

    }

    public static final Creator<ShoppingCarDataNewBean> CREATOR = new Creator<ShoppingCarDataNewBean>() {
        @Override
        public ShoppingCarDataNewBean createFromParcel(Parcel in) {
            return new ShoppingCarDataNewBean(in);
        }

        @Override
        public ShoppingCarDataNewBean[] newArray(int size) {
            return new ShoppingCarDataNewBean[size];
        }
    };

    public ShoppingCarDataNewBean clone() {
        ShoppingCarDataNewBean o = null;
        try {
            o = (ShoppingCarDataNewBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public boolean getSelect_shop() {
        return isSelect_shop;
    }

    public void setSelect_shop(boolean select_shop) {
        isSelect_shop = select_shop;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataBeanX{" +
                "addDate='" + addDate + '\'' +
                ", data=" + data +
                ", isSelect_shop=" + isSelect_shop +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addDate);
        dest.writeByte((byte) (isSelect_shop ? 1 : 0));
    }

    public static class DataBean implements Parcelable {
        /**
         * CartItemID : 61
         * ProductCount : 1
         * Product : {"ProdID":896,"ProdName":"中国税务","ProdYear":1984,"ProdIssue":3,"ProdAbstract":null,"ProdForm":10,"ProdImg":"/upload/1984/Layouts/ZS198403.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":0,"ProdIOSPrice":0,"ProdIsRCMD":1,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":11,"ProdUpdateDate":"2020-05-20 02:14:24","ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"896","ReadSourceURL":"/Journal/Detail/id/896","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":null,"ProdSumIssue":0}
         */

        private int CartItemID;
        private int ProductCount;
        private ProductBean Product;
        private boolean isSelect = false;

        protected DataBean(Parcel in) {
            CartItemID = in.readInt();
            ProductCount = in.readInt();
            isSelect = in.readByte() != 0;
        }

        public DataBean() {
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public boolean getSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getCartItemID() {
            return CartItemID;
        }

        public void setCartItemID(int CartItemID) {
            this.CartItemID = CartItemID;
        }

        public int getProductCount() {
            return ProductCount;
        }

        public void setProductCount(int ProductCount) {
            this.ProductCount = ProductCount;
        }

        public ProductBean getProduct() {
            return Product;
        }

        public void setProduct(ProductBean Product) {
            this.Product = Product;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "CartItemID=" + CartItemID +
                    ", ProductCount=" + ProductCount +
                    ", Product=" + Product +
                    ", isSelect=" + isSelect +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(CartItemID);
            dest.writeInt(ProductCount);
            dest.writeByte((byte) (isSelect ? 1 : 0));
        }

        public static class ProductBean implements Parcelable {
            /**
             * ProdID : 896
             * ProdName : 中国税务
             * ProdYear : 1984
             * ProdIssue : 3
             * ProdAbstract : null
             * ProdForm : 10
             * ProdImg : /upload/1984/Layouts/ZS198403.Source.jpg
             * ProdStatus : 10
             * ProdIsDiscount : 0
             * ProdPrice : 0.0
             * ProdIOSPrice : 0.0
             * ProdIsRCMD : 1
             * ProdCreator : 1
             * ProdCreateTime : 2020-03-24 12:00:00
             * ProdUpdateMan : 11
             * ProdUpdateDate : 2020-05-20 02:14:24
             * ReadingTypeID : 2
             * ReadTypeRootID : 1
             * ReadTypeRootCode : qk
             * ReadSourceParentID : 10
             * ReadSourceID : 896
             * ReadSourceURL : /Journal/Detail/id/896
             * ProdLevel : 1
             * ReadSourceType : 20
             * ProdType : 10
             * ProdShowStatus : 1
             * ProdIsFree : false
             * ProdAuthorName : null
             * ProdSumIssue : 0
             */

            private int ProdID;
            private String ProdName;
            private int ProdYear;
            private int ProdIssue;
            private Object ProdAbstract;
            private int ProdForm;
            private String ProdImg;
            private int ProdStatus;
            private int ProdIsDiscount;
            private double ProdPrice;
            private double ProdIOSPrice;
            private int ProdIsRCMD;
            private int ProdCreator;
            private String ProdCreateTime;
            private int ProdUpdateMan;
            private String ProdUpdateDate;
            private int ReadingTypeID;
            private int ReadTypeRootID;
            private String ReadTypeRootCode;
            private int ReadSourceParentID;
            private String ReadSourceID;
            private String ReadSourceURL;
            private int ProdLevel;
            private int ReadSourceType;
            private int ProdType;
            private int ProdShowStatus;
            private boolean ProdIsFree;
            private Object ProdAuthorName;
            private int ProdSumIssue;

            protected ProductBean(Parcel in) {
                ProdID = in.readInt();
                ProdName = in.readString();
                ProdYear = in.readInt();
                ProdIssue = in.readInt();
                ProdForm = in.readInt();
                ProdImg = in.readString();
                ProdStatus = in.readInt();
                ProdIsDiscount = in.readInt();
                ProdPrice = in.readDouble();
                ProdIOSPrice = in.readDouble();
                ProdIsRCMD = in.readInt();
                ProdCreator = in.readInt();
                ProdCreateTime = in.readString();
                ProdUpdateMan = in.readInt();
                ProdUpdateDate = in.readString();
                ReadingTypeID = in.readInt();
                ReadTypeRootID = in.readInt();
                ReadTypeRootCode = in.readString();
                ReadSourceParentID = in.readInt();
                ReadSourceID = in.readString();
                ReadSourceURL = in.readString();
                ProdLevel = in.readInt();
                ReadSourceType = in.readInt();
                ProdType = in.readInt();
                ProdShowStatus = in.readInt();
                ProdIsFree = in.readByte() != 0;
                ProdSumIssue = in.readInt();
            }

            public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
                @Override
                public ProductBean createFromParcel(Parcel in) {
                    return new ProductBean(in);
                }

                @Override
                public ProductBean[] newArray(int size) {
                    return new ProductBean[size];
                }
            };

            public ProductBean() {
                super();
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

            public int getProdStatus() {
                return ProdStatus;
            }

            public void setProdStatus(int ProdStatus) {
                this.ProdStatus = ProdStatus;
            }

            public int getProdIsDiscount() {
                return ProdIsDiscount;
            }

            public void setProdIsDiscount(int ProdIsDiscount) {
                this.ProdIsDiscount = ProdIsDiscount;
            }

            public double getProdPrice() {
                return ProdPrice;
            }

            public void setProdPrice(double ProdPrice) {
                this.ProdPrice = ProdPrice;
            }

            public double getProdIOSPrice() {
                return ProdIOSPrice;
            }

            public void setProdIOSPrice(double ProdIOSPrice) {
                this.ProdIOSPrice = ProdIOSPrice;
            }

            public int getProdIsRCMD() {
                return ProdIsRCMD;
            }

            public void setProdIsRCMD(int ProdIsRCMD) {
                this.ProdIsRCMD = ProdIsRCMD;
            }

            public int getProdCreator() {
                return ProdCreator;
            }

            public void setProdCreator(int ProdCreator) {
                this.ProdCreator = ProdCreator;
            }

            public String getProdCreateTime() {
                return ProdCreateTime;
            }

            public void setProdCreateTime(String ProdCreateTime) {
                this.ProdCreateTime = ProdCreateTime;
            }

            public int getProdUpdateMan() {
                return ProdUpdateMan;
            }

            public void setProdUpdateMan(int ProdUpdateMan) {
                this.ProdUpdateMan = ProdUpdateMan;
            }

            public String getProdUpdateDate() {
                return ProdUpdateDate;
            }

            public void setProdUpdateDate(String ProdUpdateDate) {
                this.ProdUpdateDate = ProdUpdateDate;
            }

            public int getReadingTypeID() {
                return ReadingTypeID;
            }

            public void setReadingTypeID(int ReadingTypeID) {
                this.ReadingTypeID = ReadingTypeID;
            }

            public int getReadTypeRootID() {
                return ReadTypeRootID;
            }

            public void setReadTypeRootID(int ReadTypeRootID) {
                this.ReadTypeRootID = ReadTypeRootID;
            }

            public String getReadTypeRootCode() {
                return ReadTypeRootCode;
            }

            public void setReadTypeRootCode(String ReadTypeRootCode) {
                this.ReadTypeRootCode = ReadTypeRootCode;
            }

            public int getReadSourceParentID() {
                return ReadSourceParentID;
            }

            public void setReadSourceParentID(int ReadSourceParentID) {
                this.ReadSourceParentID = ReadSourceParentID;
            }

            public String getReadSourceID() {
                return ReadSourceID;
            }

            public void setReadSourceID(String ReadSourceID) {
                this.ReadSourceID = ReadSourceID;
            }

            public String getReadSourceURL() {
                return ReadSourceURL;
            }

            public void setReadSourceURL(String ReadSourceURL) {
                this.ReadSourceURL = ReadSourceURL;
            }

            public int getProdLevel() {
                return ProdLevel;
            }

            public void setProdLevel(int ProdLevel) {
                this.ProdLevel = ProdLevel;
            }

            public int getReadSourceType() {
                return ReadSourceType;
            }

            public void setReadSourceType(int ReadSourceType) {
                this.ReadSourceType = ReadSourceType;
            }

            public int getProdType() {
                return ProdType;
            }

            public void setProdType(int ProdType) {
                this.ProdType = ProdType;
            }

            public int getProdShowStatus() {
                return ProdShowStatus;
            }

            public void setProdShowStatus(int ProdShowStatus) {
                this.ProdShowStatus = ProdShowStatus;
            }

            public boolean isProdIsFree() {
                return ProdIsFree;
            }

            public void setProdIsFree(boolean ProdIsFree) {
                this.ProdIsFree = ProdIsFree;
            }

            public Object getProdAuthorName() {
                return ProdAuthorName;
            }

            public void setProdAuthorName(Object ProdAuthorName) {
                this.ProdAuthorName = ProdAuthorName;
            }

            public int getProdSumIssue() {
                return ProdSumIssue;
            }

            public void setProdSumIssue(int ProdSumIssue) {
                this.ProdSumIssue = ProdSumIssue;
            }

            @Override
            public String toString() {
                return "ProductBean{" +
                        "ProdID=" + ProdID +
                        ", ProdName='" + ProdName + '\'' +
                        ", ProdYear=" + ProdYear +
                        ", ProdIssue=" + ProdIssue +
                        ", ProdAbstract=" + ProdAbstract +
                        ", ProdForm=" + ProdForm +
                        ", ProdImg='" + ProdImg + '\'' +
                        ", ProdStatus=" + ProdStatus +
                        ", ProdIsDiscount=" + ProdIsDiscount +
                        ", ProdPrice=" + ProdPrice +
                        ", ProdIOSPrice=" + ProdIOSPrice +
                        ", ProdIsRCMD=" + ProdIsRCMD +
                        ", ProdCreator=" + ProdCreator +
                        ", ProdCreateTime='" + ProdCreateTime + '\'' +
                        ", ProdUpdateMan=" + ProdUpdateMan +
                        ", ProdUpdateDate='" + ProdUpdateDate + '\'' +
                        ", ReadingTypeID=" + ReadingTypeID +
                        ", ReadTypeRootID=" + ReadTypeRootID +
                        ", ReadTypeRootCode='" + ReadTypeRootCode + '\'' +
                        ", ReadSourceParentID=" + ReadSourceParentID +
                        ", ReadSourceID='" + ReadSourceID + '\'' +
                        ", ReadSourceURL='" + ReadSourceURL + '\'' +
                        ", ProdLevel=" + ProdLevel +
                        ", ReadSourceType=" + ReadSourceType +
                        ", ProdType=" + ProdType +
                        ", ProdShowStatus=" + ProdShowStatus +
                        ", ProdIsFree=" + ProdIsFree +
                        ", ProdAuthorName=" + ProdAuthorName +
                        ", ProdSumIssue=" + ProdSumIssue +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(ProdID);
                dest.writeString(ProdName);
                dest.writeInt(ProdYear);
                dest.writeInt(ProdIssue);
                dest.writeInt(ProdForm);
                dest.writeString(ProdImg);
                dest.writeInt(ProdStatus);
                dest.writeInt(ProdIsDiscount);
                dest.writeDouble(ProdPrice);
                dest.writeDouble(ProdIOSPrice);
                dest.writeInt(ProdIsRCMD);
                dest.writeInt(ProdCreator);
                dest.writeString(ProdCreateTime);
                dest.writeInt(ProdUpdateMan);
                dest.writeString(ProdUpdateDate);
                dest.writeInt(ReadingTypeID);
                dest.writeInt(ReadTypeRootID);
                dest.writeString(ReadTypeRootCode);
                dest.writeInt(ReadSourceParentID);
                dest.writeString(ReadSourceID);
                dest.writeString(ReadSourceURL);
                dest.writeInt(ProdLevel);
                dest.writeInt(ReadSourceType);
                dest.writeInt(ProdType);
                dest.writeInt(ProdShowStatus);
                dest.writeByte((byte) (ProdIsFree ? 1 : 0));
                dest.writeInt(ProdSumIssue);
            }
        }
    }
}
