package io.dcloud.H5E4CA812.view.bean;

import java.util.List;

public class OrderDetailBeans {

    /**
     * OrderInvoice : {"InvoiceDetail":[],"OrderInvoiceReceiveType_str":"个人","OrderInvoiceID":7,"OrderID":3,"UserInvoiceID":2031,"OrderInvoiceReceiveType":1,"OrderInvoiceTitle":"张三","OrderInvoiceContent":null,"OrderInvoicePhone":"18708868382","OrderInvoiceEmail":null,"OrderInvoiceTaxpayerNo":"","OrderInvoiceCode":"","OrderInvoiceNumber":"","OrderInvoiceDate":"2020-06-28 10:49:15","RecvPdf51Url":"","RecvPdfUrl":"","OrderInvoiceorAddress":null,"OrderInvoiceBuyerTel":null,"OrderInvoiceBuyerBankName":null,"OrderInvoiceBankAcount":"0","OrderInvoiceReceiver":"","OrderInvoicePayee":"","OrderInvoiceChecker":"","OrderInvoiceDrawer":"","OrderInvoiceRemarks":"","OrderInvoiceEXT":"","OrderInvoiceMinus_flag":"","OrderInvoiceProperty":1,"OrderInvoiceType":1,"OrderMoney":0,"OrderInvoiceValCode":"","OrderInvoiceProv":"","OrderInvoiceCity":""}
     * OrderDetail : [{"Product":{"ArticleList":null,"ReadTypeName":null,"ProdID":895,"ProdName":"中国税务","ProdYear":1984,"ProdIssue":2,"ProdAbstract":null,"ProdForm":10,"ProdImg":"/upload/1984/Layouts/ZS198402.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":0.01,"ProdIOSPrice":0.01,"ProdIsRCMD":0,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":0,"ProdUpdateDate":null,"ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"895","ReadSourceURL":"/Journal/Detail/id/895","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":null,"ProdSumIssue":0},"OrderDetailID":3,"OrderID":3,"PubProductID":895,"PubProductName":"中国税务1984年第2期","OrderDetailCount":1,"PubProductMedia":10,"OrderDetailtMoney":0.01,"PubProductPrice":0.01,"PubProductFeePrice":0.01,"PubProductIOSPrice":0,"PubProductIOSFeePrice":0,"OrderDetailSN":"87387071c7974c7b8d95b738f30efd9e"}]
     * OrderConsignee : {"OrderConsigneeID":28,"AddressID":2005,"OrderID":3,"OrderCons":"秒阿娇肯德基","OrderConsProvince":"北京市","OrderConsCity":"北京市","OrderConsDetail":"而而哦","OrderConsMobile":"18301228765","OrderConsTel":"010-1911986","OrderConsZipCode":null,"OrderConsGender":0}
     * OrderID : 3
     * UserID : 4460
     * OrderSerialCode : 637289381555680864
     * OrderStatus : 1
     * PayModel : 5
     * OrderDate : 2020-06-28 10:49:15
     * OrderPayDate : null
     * OrderIsInvoiceStatus : 10
     * OrderIsInvoiceDueDate : 2020-06-28 10:49:15
     * OrderRemarks :
     * UserName : 18101211287
     * OrderMoney : 0.01
     * OrderIOSMoney : 0.0
     * OrderFeeMoney : 0.01
     * OrderIOSFeeMoney : 0.0
     * Order_tradeNo : 20200628726751058298281984
     * Order_fee : 0.0
     * Order_requestId : df09c6ab5aa2435a973f0c141a6042e6
     * Order_failMsg :
     * Order_refundOrderId :
     * OrderForm : 20
     * Order_token :
     */

    private OrderInvoiceBean OrderInvoice;
    private OrderConsigneeBean OrderConsignee;
    private int OrderID;
    private int UserID;
    private String OrderSerialCode;
    private int OrderStatus;
    private int PayModel;
    private String OrderDate;
    private Object OrderPayDate;
    private int OrderIsInvoiceStatus;
    private String OrderIsInvoiceDueDate;
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
    private int OrderInvoiceStatus;
    private List<OrderDetailBean> OrderDetail;

    public int getOrderInvoiceStatus() {
        return OrderInvoiceStatus;
    }

    public void setOrderInvoiceStatus(int orderInvoiceStatus) {
        OrderInvoiceStatus = orderInvoiceStatus;
    }


    public OrderInvoiceBean getOrderInvoice() {
        return OrderInvoice;
    }

    public void setOrderInvoice(OrderInvoiceBean OrderInvoice) {
        this.OrderInvoice = OrderInvoice;
    }

    public OrderConsigneeBean getOrderConsignee() {
        return OrderConsignee;
    }

    public void setOrderConsignee(OrderConsigneeBean OrderConsignee) {
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

    public int getOrderIsInvoiceStatus() {
        return OrderIsInvoiceStatus;
    }

    public void setOrderIsInvoiceStatus(int OrderIsInvoiceStatus) {
        this.OrderIsInvoiceStatus = OrderIsInvoiceStatus;
    }

    public String getOrderIsInvoiceDueDate() {
        return OrderIsInvoiceDueDate;
    }

    public void setOrderIsInvoiceDueDate(String OrderIsInvoiceDueDate) {
        this.OrderIsInvoiceDueDate = OrderIsInvoiceDueDate;
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

    public List<OrderDetailBean> getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> OrderDetail) {
        this.OrderDetail = OrderDetail;
    }

    public static class OrderInvoiceBean {
        /**
         * InvoiceDetail : []
         * OrderInvoiceReceiveType_str : 个人
         * OrderInvoiceID : 7
         * OrderID : 3
         * UserInvoiceID : 2031
         * OrderInvoiceReceiveType : 1
         * OrderInvoiceTitle : 张三
         * OrderInvoiceContent : null
         * OrderInvoicePhone : 18708868382
         * OrderInvoiceEmail : null
         * OrderInvoiceTaxpayerNo :
         * OrderInvoiceCode :
         * OrderInvoiceNumber :
         * OrderInvoiceDate : 2020-06-28 10:49:15
         * RecvPdf51Url :
         * RecvPdfUrl :
         * OrderInvoiceorAddress : null
         * OrderInvoiceBuyerTel : null
         * OrderInvoiceBuyerBankName : null
         * OrderInvoiceBankAcount : 0
         * OrderInvoiceReceiver :
         * OrderInvoicePayee :
         * OrderInvoiceChecker :
         * OrderInvoiceDrawer :
         * OrderInvoiceRemarks :
         * OrderInvoiceEXT :
         * OrderInvoiceMinus_flag :
         * OrderInvoiceProperty : 1
         * OrderInvoiceType : 1
         * OrderMoney : 0.0
         * OrderInvoiceValCode :
         * OrderInvoiceProv :
         * OrderInvoiceCity :
         */

        private String OrderInvoiceReceiveType_str;
        private int OrderInvoiceID;
        private int OrderID;
        private int UserInvoiceID;
        private int OrderInvoiceReceiveType;
        private String OrderInvoiceTitle;
        private Object OrderInvoiceContent;
        private String OrderInvoicePhone;
        private Object OrderInvoiceEmail;
        private String OrderInvoiceTaxpayerNo;
        private String OrderInvoiceCode;
        private String OrderInvoiceNumber;
        private String OrderInvoiceDate;
        private String RecvPdf51Url;
        private String RecvPdfUrl;
        private Object OrderInvoiceorAddress;
        private Object OrderInvoiceBuyerTel;
        private Object OrderInvoiceBuyerBankName;
        private String OrderInvoiceBankAcount;
        private String OrderInvoiceReceiver;
        private String OrderInvoicePayee;
        private String OrderInvoiceChecker;
        private String OrderInvoiceDrawer;
        private String OrderInvoiceRemarks;
        private String OrderInvoiceEXT;
        private String OrderInvoiceMinus_flag;
        private int OrderInvoiceProperty;
        private int OrderInvoiceType;
        private double OrderMoney;
        private String OrderInvoiceValCode;
        private String OrderInvoiceProv;
        private String OrderInvoiceCity;
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

        public String getOrderInvoiceTitle() {
            return OrderInvoiceTitle;
        }

        public void setOrderInvoiceTitle(String OrderInvoiceTitle) {
            this.OrderInvoiceTitle = OrderInvoiceTitle;
        }

        public Object getOrderInvoiceContent() {
            return OrderInvoiceContent;
        }

        public void setOrderInvoiceContent(Object OrderInvoiceContent) {
            this.OrderInvoiceContent = OrderInvoiceContent;
        }

        public String getOrderInvoicePhone() {
            return OrderInvoicePhone;
        }

        public void setOrderInvoicePhone(String OrderInvoicePhone) {
            this.OrderInvoicePhone = OrderInvoicePhone;
        }

        public Object getOrderInvoiceEmail() {
            return OrderInvoiceEmail;
        }

        public void setOrderInvoiceEmail(Object OrderInvoiceEmail) {
            this.OrderInvoiceEmail = OrderInvoiceEmail;
        }

        public String getOrderInvoiceTaxpayerNo() {
            return OrderInvoiceTaxpayerNo;
        }

        public void setOrderInvoiceTaxpayerNo(String OrderInvoiceTaxpayerNo) {
            this.OrderInvoiceTaxpayerNo = OrderInvoiceTaxpayerNo;
        }

        public String getOrderInvoiceCode() {
            return OrderInvoiceCode;
        }

        public void setOrderInvoiceCode(String OrderInvoiceCode) {
            this.OrderInvoiceCode = OrderInvoiceCode;
        }

        public String getOrderInvoiceNumber() {
            return OrderInvoiceNumber;
        }

        public void setOrderInvoiceNumber(String OrderInvoiceNumber) {
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

        public String getRecvPdfUrl() {
            return RecvPdfUrl;
        }

        public void setRecvPdfUrl(String RecvPdfUrl) {
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

        public String getOrderInvoiceBankAcount() {
            return OrderInvoiceBankAcount;
        }

        public void setOrderInvoiceBankAcount(String OrderInvoiceBankAcount) {
            this.OrderInvoiceBankAcount = OrderInvoiceBankAcount;
        }

        public String getOrderInvoiceReceiver() {
            return OrderInvoiceReceiver;
        }

        public void setOrderInvoiceReceiver(String OrderInvoiceReceiver) {
            this.OrderInvoiceReceiver = OrderInvoiceReceiver;
        }

        public String getOrderInvoicePayee() {
            return OrderInvoicePayee;
        }

        public void setOrderInvoicePayee(String OrderInvoicePayee) {
            this.OrderInvoicePayee = OrderInvoicePayee;
        }

        public String getOrderInvoiceChecker() {
            return OrderInvoiceChecker;
        }

        public void setOrderInvoiceChecker(String OrderInvoiceChecker) {
            this.OrderInvoiceChecker = OrderInvoiceChecker;
        }

        public String getOrderInvoiceDrawer() {
            return OrderInvoiceDrawer;
        }

        public void setOrderInvoiceDrawer(String OrderInvoiceDrawer) {
            this.OrderInvoiceDrawer = OrderInvoiceDrawer;
        }

        public String getOrderInvoiceRemarks() {
            return OrderInvoiceRemarks;
        }

        public void setOrderInvoiceRemarks(String OrderInvoiceRemarks) {
            this.OrderInvoiceRemarks = OrderInvoiceRemarks;
        }

        public String getOrderInvoiceEXT() {
            return OrderInvoiceEXT;
        }

        public void setOrderInvoiceEXT(String OrderInvoiceEXT) {
            this.OrderInvoiceEXT = OrderInvoiceEXT;
        }

        public String getOrderInvoiceMinus_flag() {
            return OrderInvoiceMinus_flag;
        }

        public void setOrderInvoiceMinus_flag(String OrderInvoiceMinus_flag) {
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

        public String getOrderInvoiceValCode() {
            return OrderInvoiceValCode;
        }

        public void setOrderInvoiceValCode(String OrderInvoiceValCode) {
            this.OrderInvoiceValCode = OrderInvoiceValCode;
        }

        public String getOrderInvoiceProv() {
            return OrderInvoiceProv;
        }

        public void setOrderInvoiceProv(String OrderInvoiceProv) {
            this.OrderInvoiceProv = OrderInvoiceProv;
        }

        public String getOrderInvoiceCity() {
            return OrderInvoiceCity;
        }

        public void setOrderInvoiceCity(String OrderInvoiceCity) {
            this.OrderInvoiceCity = OrderInvoiceCity;
        }

        public List<?> getInvoiceDetail() {
            return InvoiceDetail;
        }

        public void setInvoiceDetail(List<?> InvoiceDetail) {
            this.InvoiceDetail = InvoiceDetail;
        }
    }

    public static class OrderConsigneeBean {
        /**
         * OrderConsigneeID : 28
         * AddressID : 2005
         * OrderID : 3
         * OrderCons : 秒阿娇肯德基
         * OrderConsProvince : 北京市
         * OrderConsCity : 北京市
         * OrderConsDetail : 而而哦
         * OrderConsMobile : 18301228765
         * OrderConsTel : 010-1911986
         * OrderConsZipCode : null
         * OrderConsGender : 0
         */

        private int OrderConsigneeID;
        private int AddressID;
        private int OrderID;
        private String OrderCons;
        private String OrderConsProvince;
        private String OrderConsCity;
        private String OrderConsDetail;
        private String OrderConsMobile;
        private String OrderConsTel;
        private Object OrderConsZipCode;
        private int OrderConsGender;

        public int getOrderConsigneeID() {
            return OrderConsigneeID;
        }

        public void setOrderConsigneeID(int OrderConsigneeID) {
            this.OrderConsigneeID = OrderConsigneeID;
        }

        public int getAddressID() {
            return AddressID;
        }

        public void setAddressID(int AddressID) {
            this.AddressID = AddressID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public String getOrderCons() {
            return OrderCons;
        }

        public void setOrderCons(String OrderCons) {
            this.OrderCons = OrderCons;
        }

        public String getOrderConsProvince() {
            return OrderConsProvince;
        }

        public void setOrderConsProvince(String OrderConsProvince) {
            this.OrderConsProvince = OrderConsProvince;
        }

        public String getOrderConsCity() {
            return OrderConsCity;
        }

        public void setOrderConsCity(String OrderConsCity) {
            this.OrderConsCity = OrderConsCity;
        }

        public String getOrderConsDetail() {
            return OrderConsDetail;
        }

        public void setOrderConsDetail(String OrderConsDetail) {
            this.OrderConsDetail = OrderConsDetail;
        }

        public String getOrderConsMobile() {
            return OrderConsMobile;
        }

        public void setOrderConsMobile(String OrderConsMobile) {
            this.OrderConsMobile = OrderConsMobile;
        }

        public String getOrderConsTel() {
            return OrderConsTel;
        }

        public void setOrderConsTel(String OrderConsTel) {
            this.OrderConsTel = OrderConsTel;
        }

        public Object getOrderConsZipCode() {
            return OrderConsZipCode;
        }

        public void setOrderConsZipCode(Object OrderConsZipCode) {
            this.OrderConsZipCode = OrderConsZipCode;
        }

        public int getOrderConsGender() {
            return OrderConsGender;
        }

        public void setOrderConsGender(int OrderConsGender) {
            this.OrderConsGender = OrderConsGender;
        }
    }

    public static class OrderDetailBean {
        /**
         * Product : {"ArticleList":null,"ReadTypeName":null,"ProdID":895,"ProdName":"中国税务","ProdYear":1984,"ProdIssue":2,"ProdAbstract":null,"ProdForm":10,"ProdImg":"/upload/1984/Layouts/ZS198402.Source.jpg","ProdStatus":10,"ProdIsDiscount":0,"ProdPrice":0.01,"ProdIOSPrice":0.01,"ProdIsRCMD":0,"ProdCreator":1,"ProdCreateTime":"2020-03-24 12:00:00","ProdUpdateMan":0,"ProdUpdateDate":null,"ReadingTypeID":2,"ReadTypeRootID":1,"ReadTypeRootCode":"qk","ReadSourceParentID":10,"ReadSourceID":"895","ReadSourceURL":"/Journal/Detail/id/895","ProdLevel":1,"ReadSourceType":20,"ProdType":10,"ProdShowStatus":1,"ProdIsFree":false,"ProdAuthorName":null,"ProdSumIssue":0}
         * OrderDetailID : 3
         * OrderID : 3
         * PubProductID : 895
         * PubProductName : 中国税务1984年第2期
         * OrderDetailCount : 1
         * PubProductMedia : 10
         * OrderDetailtMoney : 0.01
         * PubProductPrice : 0.01
         * PubProductFeePrice : 0.01
         * PubProductIOSPrice : 0.0
         * PubProductIOSFeePrice : 0.0
         * OrderDetailSN : 87387071c7974c7b8d95b738f30efd9e
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

        public static class ProductBean {
            /**
             * ArticleList : null
             * ReadTypeName : null
             * ProdID : 895
             * ProdName : 中国税务
             * ProdYear : 1984
             * ProdIssue : 2
             * ProdAbstract : null
             * ProdForm : 10
             * ProdImg : /upload/1984/Layouts/ZS198402.Source.jpg
             * ProdStatus : 10
             * ProdIsDiscount : 0
             * ProdPrice : 0.01
             * ProdIOSPrice : 0.01
             * ProdIsRCMD : 0
             * ProdCreator : 1
             * ProdCreateTime : 2020-03-24 12:00:00
             * ProdUpdateMan : 0
             * ProdUpdateDate : null
             * ReadingTypeID : 2
             * ReadTypeRootID : 1
             * ReadTypeRootCode : qk
             * ReadSourceParentID : 10
             * ReadSourceID : 895
             * ReadSourceURL : /Journal/Detail/id/895
             * ProdLevel : 1
             * ReadSourceType : 20
             * ProdType : 10
             * ProdShowStatus : 1
             * ProdIsFree : false
             * ProdAuthorName : null
             * ProdSumIssue : 0
             */

            private Object ArticleList;
            private Object ReadTypeName;
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
            private Object ProdUpdateDate;
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

            public Object getArticleList() {
                return ArticleList;
            }

            public void setArticleList(Object ArticleList) {
                this.ArticleList = ArticleList;
            }

            public Object getReadTypeName() {
                return ReadTypeName;
            }

            public void setReadTypeName(Object ReadTypeName) {
                this.ReadTypeName = ReadTypeName;
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

            public Object getProdUpdateDate() {
                return ProdUpdateDate;
            }

            public void setProdUpdateDate(Object ProdUpdateDate) {
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
        }
    }
}
