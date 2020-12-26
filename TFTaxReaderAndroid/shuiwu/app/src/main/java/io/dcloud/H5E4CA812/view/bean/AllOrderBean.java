package io.dcloud.H5E4CA812.view.bean;

import java.util.List;

public class AllOrderBean {


    /**
     * OrderInvoice : {"InvoiceDetail":[],"OrderInvoiceReceiveType_str":"0","OrderInvoiceID":0,"OrderID":120,"UserInvoiceID":0,"OrderInvoiceReceiveType":0,"OrderInvoiceTitle":null,"OrderInvoiceContent":null,"OrderInvoicePhone":null,"OrderInvoiceEmail":null,"OrderInvoiceTaxpayerNo":null,"OrderInvoiceCode":null,"OrderInvoiceNumber":null,"OrderInvoiceDate":"0001-01-01 12:00:00","RecvPdf51Url":"https://einvoicelink.51fapiao.cn:8181/FPFX/actions/d0bce0637fa6a2e0a074d384e10a8df906826d","RecvPdfUrl":null,"OrderInvoiceorAddress":null,"OrderInvoiceBuyerTel":null,"OrderInvoiceBuyerBankName":null,"OrderInvoiceBankAcount":null,"OrderInvoiceReceiver":null,"OrderInvoicePayee":null,"OrderInvoiceChecker":null,"OrderInvoiceDrawer":null,"OrderInvoiceRemarks":null,"OrderInvoiceEXT":null,"OrderInvoiceMinus_flag":null,"OrderInvoiceProperty":0,"OrderInvoiceType":0,"OrderMoney":15,"OrderInvoiceValCode":null,"OrderInvoiceProv":null,"OrderInvoiceCity":null,"DoMain":"http://210.12.84.109/"}
     * OrderDetail : [{"Product":{"ArticleList":null,"ProdID":2500,"ProdName":"中国税务","ProdYear":2020,"ProdIssue":7,"ProdSumIssue":0,"ProdAbstract":"","ProdForm":10,"ProdImg":"/upload/2020/Layouts/ZS202007.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":15,"ProdIOSPrice":15,"ProdIsRCMD":1,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":11,"ProdUpdateDate":"2020-08-03 11:24:05","ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"2500","ReadSourceURL":"/Journal/Detail/id/2500","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":"","DoMain":"http://210.12.84.109/"},"OrderDetailID":128,"OrderID":120,"PubProductID":2500,"PubProductName":"中国税务2020年第7期","OrderDetailCount":1,"PubProductMedia":10,"OrderDetailtMoney":15,"PubProductPrice":15,"PubProductFeePrice":15,"PubProductIOSPrice":0,"PubProductIOSFeePrice":0,"OrderDetailSN":"fb395d71ce3e4ad586d5e9379c99d401","DoMain":"http://210.12.84.109/"}]
     * OrderConsignee : null
     * OrderID : 120
     * UserID : 4574
     * OrderSerialCode : 637326689483537364
     * OrderStatus : 3
     * PayModel : 4
     * OrderDate : 2020-08-10 03:09:08
     * OrderPayDate : null
     * OrderInvoiceStatus : 30
     * OrderInvoiceDueDate : 2020-08-10 03:09:08
     * OrderRemarks :
     * UserName : zhengborang
     * OrderMoney : 15.0
     * OrderIOSMoney : 0.0
     * OrderFeeMoney : 15.0
     * OrderIOSFeeMoney : 0.0
     * Order_tradeNo : 20200810742399186544234496
     * Order_fee : 0.0
     * Order_requestId : 3005affed06b4592b4f50af329bd7bd7
     * Order_failMsg :
     * Order_refundOrderId :
     * OrderForm : 10
     * Order_token :
     * DoMain : http://210.12.84.109/
     */

    private OrderInvoiceBean OrderInvoice;
    private Object OrderConsignee;
    private int OrderID;
    private int UserID;
    private String OrderSerialCode;
    private int OrderStatus;
    private int PayModel;
    private String OrderDate;
    private Object OrderPayDate;
    private int OrderInvoiceStatus;
    private String OrderInvoiceDueDate;
    private String OrderRemarks;
    private String UserName;
    private double OrderMoney;
    private double OrderIOSMoney;
    private double OrderFeeMoney;
    private double OrderIOSFeeMoney;
    private String Order_tradeNo;
    private double Order_fee;
    private String Order_requestId;
    private String Order_failMsg;
    private String Order_refundOrderId;
    private int OrderForm;
    private String Order_token;
    private String DoMain;
    private List<OrderDetailBean> OrderDetail;

    public OrderInvoiceBean getOrderInvoice() {
        return OrderInvoice;
    }

    public void setOrderInvoice(OrderInvoiceBean OrderInvoice) {
        this.OrderInvoice = OrderInvoice;
    }

    public Object getOrderConsignee() {
        return OrderConsignee;
    }

    public void setOrderConsignee(Object OrderConsignee) {
        this.OrderConsignee = OrderConsignee;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getOrderSerialCode() {
        return OrderSerialCode;
    }

    public void setOrderSerialCode(String OrderSerialCode) {
        this.OrderSerialCode = OrderSerialCode;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public int getPayModel() {
        return PayModel;
    }

    public void setPayModel(int PayModel) {
        this.PayModel = PayModel;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public Object getOrderPayDate() {
        return OrderPayDate;
    }

    public void setOrderPayDate(Object OrderPayDate) {
        this.OrderPayDate = OrderPayDate;
    }

    public int getOrderInvoiceStatus() {
        return OrderInvoiceStatus;
    }

    public void setOrderInvoiceStatus(int OrderInvoiceStatus) {
        this.OrderInvoiceStatus = OrderInvoiceStatus;
    }

    public String getOrderInvoiceDueDate() {
        return OrderInvoiceDueDate;
    }

    public void setOrderInvoiceDueDate(String OrderInvoiceDueDate) {
        this.OrderInvoiceDueDate = OrderInvoiceDueDate;
    }

    public String getOrderRemarks() {
        return OrderRemarks;
    }

    public void setOrderRemarks(String OrderRemarks) {
        this.OrderRemarks = OrderRemarks;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public double getOrderMoney() {
        return OrderMoney;
    }

    public void setOrderMoney(double OrderMoney) {
        this.OrderMoney = OrderMoney;
    }

    public double getOrderIOSMoney() {
        return OrderIOSMoney;
    }

    public void setOrderIOSMoney(double OrderIOSMoney) {
        this.OrderIOSMoney = OrderIOSMoney;
    }

    public double getOrderFeeMoney() {
        return OrderFeeMoney;
    }

    public void setOrderFeeMoney(double OrderFeeMoney) {
        this.OrderFeeMoney = OrderFeeMoney;
    }

    public double getOrderIOSFeeMoney() {
        return OrderIOSFeeMoney;
    }

    public void setOrderIOSFeeMoney(double OrderIOSFeeMoney) {
        this.OrderIOSFeeMoney = OrderIOSFeeMoney;
    }

    public String getOrder_tradeNo() {
        return Order_tradeNo;
    }

    public void setOrder_tradeNo(String Order_tradeNo) {
        this.Order_tradeNo = Order_tradeNo;
    }

    public double getOrder_fee() {
        return Order_fee;
    }

    public void setOrder_fee(double Order_fee) {
        this.Order_fee = Order_fee;
    }

    public String getOrder_requestId() {
        return Order_requestId;
    }

    public void setOrder_requestId(String Order_requestId) {
        this.Order_requestId = Order_requestId;
    }

    public String getOrder_failMsg() {
        return Order_failMsg;
    }

    public void setOrder_failMsg(String Order_failMsg) {
        this.Order_failMsg = Order_failMsg;
    }

    public String getOrder_refundOrderId() {
        return Order_refundOrderId;
    }

    public void setOrder_refundOrderId(String Order_refundOrderId) {
        this.Order_refundOrderId = Order_refundOrderId;
    }

    public int getOrderForm() {
        return OrderForm;
    }

    public void setOrderForm(int OrderForm) {
        this.OrderForm = OrderForm;
    }

    public String getOrder_token() {
        return Order_token;
    }

    public void setOrder_token(String Order_token) {
        this.Order_token = Order_token;
    }

    public String getDoMain() {
        return DoMain;
    }

    public void setDoMain(String DoMain) {
        this.DoMain = DoMain;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> OrderDetail) {
        this.OrderDetail = OrderDetail;
    }

    public static class OrderInvoiceBean {
        /**
         * InvoiceDetail : []
         * OrderInvoiceReceiveType_str : 0
         * OrderInvoiceID : 0
         * OrderID : 120
         * UserInvoiceID : 0
         * OrderInvoiceReceiveType : 0
         * OrderInvoiceTitle : null
         * OrderInvoiceContent : null
         * OrderInvoicePhone : null
         * OrderInvoiceEmail : null
         * OrderInvoiceTaxpayerNo : null
         * OrderInvoiceCode : null
         * OrderInvoiceNumber : null
         * OrderInvoiceDate : 0001-01-01 12:00:00
         * RecvPdf51Url : https://einvoicelink.51fapiao.cn:8181/FPFX/actions/d0bce0637fa6a2e0a074d384e10a8df906826d
         * RecvPdfUrl : null
         * OrderInvoiceorAddress : null
         * OrderInvoiceBuyerTel : null
         * OrderInvoiceBuyerBankName : null
         * OrderInvoiceBankAcount : null
         * OrderInvoiceReceiver : null
         * OrderInvoicePayee : null
         * OrderInvoiceChecker : null
         * OrderInvoiceDrawer : null
         * OrderInvoiceRemarks : null
         * OrderInvoiceEXT : null
         * OrderInvoiceMinus_flag : null
         * OrderInvoiceProperty : 0
         * OrderInvoiceType : 0
         * OrderMoney : 15.0
         * OrderInvoiceValCode : null
         * OrderInvoiceProv : null
         * OrderInvoiceCity : null
         * DoMain : http://210.12.84.109/
         */

        private String OrderInvoiceReceiveType_str;
        private int OrderInvoiceID;
        private int OrderID;
        private int UserInvoiceID;
        private int OrderInvoiceReceiveType;
        private Object OrderInvoiceTitle;
        private Object OrderInvoiceContent;
        private Object OrderInvoicePhone;
        private Object OrderInvoiceEmail;
        private Object OrderInvoiceTaxpayerNo;
        private Object OrderInvoiceCode;
        private Object OrderInvoiceNumber;
        private String OrderInvoiceDate;
        private String RecvPdf51Url;
        private Object RecvPdfUrl;
        private Object OrderInvoiceorAddress;
        private Object OrderInvoiceBuyerTel;
        private Object OrderInvoiceBuyerBankName;
        private Object OrderInvoiceBankAcount;
        private Object OrderInvoiceReceiver;
        private Object OrderInvoicePayee;
        private Object OrderInvoiceChecker;
        private Object OrderInvoiceDrawer;
        private Object OrderInvoiceRemarks;
        private Object OrderInvoiceEXT;
        private Object OrderInvoiceMinus_flag;
        private int OrderInvoiceProperty;
        private int OrderInvoiceType;
        private double OrderMoney;
        private Object OrderInvoiceValCode;
        private Object OrderInvoiceProv;
        private Object OrderInvoiceCity;
        private String DoMain;
        private List<?> InvoiceDetail;

        public String getOrderInvoiceReceiveType_str() {
            return OrderInvoiceReceiveType_str;
        }

        public void setOrderInvoiceReceiveType_str(String OrderInvoiceReceiveType_str) {
            this.OrderInvoiceReceiveType_str = OrderInvoiceReceiveType_str;
        }

        public int getOrderInvoiceID() {
            return OrderInvoiceID;
        }

        public void setOrderInvoiceID(int OrderInvoiceID) {
            this.OrderInvoiceID = OrderInvoiceID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public int getUserInvoiceID() {
            return UserInvoiceID;
        }

        public void setUserInvoiceID(int UserInvoiceID) {
            this.UserInvoiceID = UserInvoiceID;
        }

        public int getOrderInvoiceReceiveType() {
            return OrderInvoiceReceiveType;
        }

        public void setOrderInvoiceReceiveType(int OrderInvoiceReceiveType) {
            this.OrderInvoiceReceiveType = OrderInvoiceReceiveType;
        }

        public Object getOrderInvoiceTitle() {
            return OrderInvoiceTitle;
        }

        public void setOrderInvoiceTitle(Object OrderInvoiceTitle) {
            this.OrderInvoiceTitle = OrderInvoiceTitle;
        }

        public Object getOrderInvoiceContent() {
            return OrderInvoiceContent;
        }

        public void setOrderInvoiceContent(Object OrderInvoiceContent) {
            this.OrderInvoiceContent = OrderInvoiceContent;
        }

        public Object getOrderInvoicePhone() {
            return OrderInvoicePhone;
        }

        public void setOrderInvoicePhone(Object OrderInvoicePhone) {
            this.OrderInvoicePhone = OrderInvoicePhone;
        }

        public Object getOrderInvoiceEmail() {
            return OrderInvoiceEmail;
        }

        public void setOrderInvoiceEmail(Object OrderInvoiceEmail) {
            this.OrderInvoiceEmail = OrderInvoiceEmail;
        }

        public Object getOrderInvoiceTaxpayerNo() {
            return OrderInvoiceTaxpayerNo;
        }

        public void setOrderInvoiceTaxpayerNo(Object OrderInvoiceTaxpayerNo) {
            this.OrderInvoiceTaxpayerNo = OrderInvoiceTaxpayerNo;
        }

        public Object getOrderInvoiceCode() {
            return OrderInvoiceCode;
        }

        public void setOrderInvoiceCode(Object OrderInvoiceCode) {
            this.OrderInvoiceCode = OrderInvoiceCode;
        }

        public Object getOrderInvoiceNumber() {
            return OrderInvoiceNumber;
        }

        public void setOrderInvoiceNumber(Object OrderInvoiceNumber) {
            this.OrderInvoiceNumber = OrderInvoiceNumber;
        }

        public String getOrderInvoiceDate() {
            return OrderInvoiceDate;
        }

        public void setOrderInvoiceDate(String OrderInvoiceDate) {
            this.OrderInvoiceDate = OrderInvoiceDate;
        }

        public String getRecvPdf51Url() {
            return RecvPdf51Url;
        }

        public void setRecvPdf51Url(String RecvPdf51Url) {
            this.RecvPdf51Url = RecvPdf51Url;
        }

        public Object getRecvPdfUrl() {
            return RecvPdfUrl;
        }

        public void setRecvPdfUrl(Object RecvPdfUrl) {
            this.RecvPdfUrl = RecvPdfUrl;
        }

        public Object getOrderInvoiceorAddress() {
            return OrderInvoiceorAddress;
        }

        public void setOrderInvoiceorAddress(Object OrderInvoiceorAddress) {
            this.OrderInvoiceorAddress = OrderInvoiceorAddress;
        }

        public Object getOrderInvoiceBuyerTel() {
            return OrderInvoiceBuyerTel;
        }

        public void setOrderInvoiceBuyerTel(Object OrderInvoiceBuyerTel) {
            this.OrderInvoiceBuyerTel = OrderInvoiceBuyerTel;
        }

        public Object getOrderInvoiceBuyerBankName() {
            return OrderInvoiceBuyerBankName;
        }

        public void setOrderInvoiceBuyerBankName(Object OrderInvoiceBuyerBankName) {
            this.OrderInvoiceBuyerBankName = OrderInvoiceBuyerBankName;
        }

        public Object getOrderInvoiceBankAcount() {
            return OrderInvoiceBankAcount;
        }

        public void setOrderInvoiceBankAcount(Object OrderInvoiceBankAcount) {
            this.OrderInvoiceBankAcount = OrderInvoiceBankAcount;
        }

        public Object getOrderInvoiceReceiver() {
            return OrderInvoiceReceiver;
        }

        public void setOrderInvoiceReceiver(Object OrderInvoiceReceiver) {
            this.OrderInvoiceReceiver = OrderInvoiceReceiver;
        }

        public Object getOrderInvoicePayee() {
            return OrderInvoicePayee;
        }

        public void setOrderInvoicePayee(Object OrderInvoicePayee) {
            this.OrderInvoicePayee = OrderInvoicePayee;
        }

        public Object getOrderInvoiceChecker() {
            return OrderInvoiceChecker;
        }

        public void setOrderInvoiceChecker(Object OrderInvoiceChecker) {
            this.OrderInvoiceChecker = OrderInvoiceChecker;
        }

        public Object getOrderInvoiceDrawer() {
            return OrderInvoiceDrawer;
        }

        public void setOrderInvoiceDrawer(Object OrderInvoiceDrawer) {
            this.OrderInvoiceDrawer = OrderInvoiceDrawer;
        }

        public Object getOrderInvoiceRemarks() {
            return OrderInvoiceRemarks;
        }

        public void setOrderInvoiceRemarks(Object OrderInvoiceRemarks) {
            this.OrderInvoiceRemarks = OrderInvoiceRemarks;
        }

        public Object getOrderInvoiceEXT() {
            return OrderInvoiceEXT;
        }

        public void setOrderInvoiceEXT(Object OrderInvoiceEXT) {
            this.OrderInvoiceEXT = OrderInvoiceEXT;
        }

        public Object getOrderInvoiceMinus_flag() {
            return OrderInvoiceMinus_flag;
        }

        public void setOrderInvoiceMinus_flag(Object OrderInvoiceMinus_flag) {
            this.OrderInvoiceMinus_flag = OrderInvoiceMinus_flag;
        }

        public int getOrderInvoiceProperty() {
            return OrderInvoiceProperty;
        }

        public void setOrderInvoiceProperty(int OrderInvoiceProperty) {
            this.OrderInvoiceProperty = OrderInvoiceProperty;
        }

        public int getOrderInvoiceType() {
            return OrderInvoiceType;
        }

        public void setOrderInvoiceType(int OrderInvoiceType) {
            this.OrderInvoiceType = OrderInvoiceType;
        }

        public double getOrderMoney() {
            return OrderMoney;
        }

        public void setOrderMoney(double OrderMoney) {
            this.OrderMoney = OrderMoney;
        }

        public Object getOrderInvoiceValCode() {
            return OrderInvoiceValCode;
        }

        public void setOrderInvoiceValCode(Object OrderInvoiceValCode) {
            this.OrderInvoiceValCode = OrderInvoiceValCode;
        }

        public Object getOrderInvoiceProv() {
            return OrderInvoiceProv;
        }

        public void setOrderInvoiceProv(Object OrderInvoiceProv) {
            this.OrderInvoiceProv = OrderInvoiceProv;
        }

        public Object getOrderInvoiceCity() {
            return OrderInvoiceCity;
        }

        public void setOrderInvoiceCity(Object OrderInvoiceCity) {
            this.OrderInvoiceCity = OrderInvoiceCity;
        }

        public String getDoMain() {
            return DoMain;
        }

        public void setDoMain(String DoMain) {
            this.DoMain = DoMain;
        }

        public List<?> getInvoiceDetail() {
            return InvoiceDetail;
        }

        public void setInvoiceDetail(List<?> InvoiceDetail) {
            this.InvoiceDetail = InvoiceDetail;
        }
    }

    public static class OrderDetailBean {
        /**
         * Product : {"ArticleList":null,"ProdID":2500,"ProdName":"中国税务","ProdYear":2020,"ProdIssue":7,"ProdSumIssue":0,"ProdAbstract":"","ProdForm":10,"ProdImg":"/upload/2020/Layouts/ZS202007.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":15,"ProdIOSPrice":15,"ProdIsRCMD":1,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":11,"ProdUpdateDate":"2020-08-03 11:24:05","ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"2500","ReadSourceURL":"/Journal/Detail/id/2500","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":"","DoMain":"http://210.12.84.109/"}
         * OrderDetailID : 128
         * OrderID : 120
         * PubProductID : 2500
         * PubProductName : 中国税务2020年第7期
         * OrderDetailCount : 1
         * PubProductMedia : 10
         * OrderDetailtMoney : 15.0
         * PubProductPrice : 15.0
         * PubProductFeePrice : 15.0
         * PubProductIOSPrice : 0.0
         * PubProductIOSFeePrice : 0.0
         * OrderDetailSN : fb395d71ce3e4ad586d5e9379c99d401
         * DoMain : http://210.12.84.109/
         */

        private ProductBean Product;
        private int OrderDetailID;
        private int OrderID;
        private int PubProductID;
        private String PubProductName;
        private int OrderDetailCount;
        private int PubProductMedia;
        private double OrderDetailtMoney;
        private double PubProductPrice;
        private double PubProductFeePrice;
        private double PubProductIOSPrice;
        private double PubProductIOSFeePrice;
        private String OrderDetailSN;
        private String DoMain;

        public ProductBean getProduct() {
            return Product;
        }

        public void setProduct(ProductBean Product) {
            this.Product = Product;
        }

        public int getOrderDetailID() {
            return OrderDetailID;
        }

        public void setOrderDetailID(int OrderDetailID) {
            this.OrderDetailID = OrderDetailID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public int getPubProductID() {
            return PubProductID;
        }

        public void setPubProductID(int PubProductID) {
            this.PubProductID = PubProductID;
        }

        public String getPubProductName() {
            return PubProductName;
        }

        public void setPubProductName(String PubProductName) {
            this.PubProductName = PubProductName;
        }

        public int getOrderDetailCount() {
            return OrderDetailCount;
        }

        public void setOrderDetailCount(int OrderDetailCount) {
            this.OrderDetailCount = OrderDetailCount;
        }

        public int getPubProductMedia() {
            return PubProductMedia;
        }

        public void setPubProductMedia(int PubProductMedia) {
            this.PubProductMedia = PubProductMedia;
        }

        public double getOrderDetailtMoney() {
            return OrderDetailtMoney;
        }

        public void setOrderDetailtMoney(double OrderDetailtMoney) {
            this.OrderDetailtMoney = OrderDetailtMoney;
        }

        public double getPubProductPrice() {
            return PubProductPrice;
        }

        public void setPubProductPrice(double PubProductPrice) {
            this.PubProductPrice = PubProductPrice;
        }

        public double getPubProductFeePrice() {
            return PubProductFeePrice;
        }

        public void setPubProductFeePrice(double PubProductFeePrice) {
            this.PubProductFeePrice = PubProductFeePrice;
        }

        public double getPubProductIOSPrice() {
            return PubProductIOSPrice;
        }

        public void setPubProductIOSPrice(double PubProductIOSPrice) {
            this.PubProductIOSPrice = PubProductIOSPrice;
        }

        public double getPubProductIOSFeePrice() {
            return PubProductIOSFeePrice;
        }

        public void setPubProductIOSFeePrice(double PubProductIOSFeePrice) {
            this.PubProductIOSFeePrice = PubProductIOSFeePrice;
        }

        public String getOrderDetailSN() {
            return OrderDetailSN;
        }

        public void setOrderDetailSN(String OrderDetailSN) {
            this.OrderDetailSN = OrderDetailSN;
        }

        public String getDoMain() {
            return DoMain;
        }

        public void setDoMain(String DoMain) {
            this.DoMain = DoMain;
        }

        public static class ProductBean {
            /**
             * ArticleList : null
             * ProdID : 2500
             * ProdName : 中国税务
             * ProdYear : 2020
             * ProdIssue : 7
             * ProdSumIssue : 0
             * ProdAbstract :
             * ProdForm : 10
             * ProdImg : /upload/2020/Layouts/ZS202007.Source.jpg
             * ProdStatus : 10
             * ProdIsDiscount : 0
             * ProdPrice : 15.0
             * ProdIOSPrice : 15.0
             * ProdIsRCMD : 1
             * ProdCreator : 1
             * ProdCreateTime : 2020-03-24 12:00:00
             * ProdUpdateMan : 11
             * ProdUpdateDate : 2020-08-03 11:24:05
             * ReadingTypeID : 2
             * ReadTypeRootID : 1
             * ReadTypeRootCode : qk
             * ReadSourceParentID : 10
             * ReadSourceID : 2500
             * ReadSourceURL : /Journal/Detail/id/2500
             * ProdLevel : 1
             * ReadSourceType : 20
             * ProdType : 10
             * ProdShowStatus : 1
             * ProdIsFree : false
             * ProdAuthorName :
             * DoMain : http://210.12.84.109/
             */

            private Object ArticleList;
            private int ProdID;
            private String ProdName;
            private int ProdYear;
            private int ProdIssue;
            private int ProdSumIssue;
            private String ProdAbstract;
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
            private String ProdAuthorName;
            private String DoMain;

            public Object getArticleList() {
                return ArticleList;
            }

            public void setArticleList(Object ArticleList) {
                this.ArticleList = ArticleList;
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

            public int getProdSumIssue() {
                return ProdSumIssue;
            }

            public void setProdSumIssue(int ProdSumIssue) {
                this.ProdSumIssue = ProdSumIssue;
            }

            public String getProdAbstract() {
                return ProdAbstract;
            }

            public void setProdAbstract(String ProdAbstract) {
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

            public String getProdAuthorName() {
                return ProdAuthorName;
            }

            public void setProdAuthorName(String ProdAuthorName) {
                this.ProdAuthorName = ProdAuthorName;
            }

            public String getDoMain() {
                return DoMain;
            }

            public void setDoMain(String DoMain) {
                this.DoMain = DoMain;
            }
        }
    }
}

