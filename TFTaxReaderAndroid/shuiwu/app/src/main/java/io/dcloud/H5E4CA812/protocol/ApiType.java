package io.dcloud.H5E4CA812.protocol;


import io.dcloud.H5E4CA812.util.StringUtil;

public enum ApiType {


    GetSMSGetCode("/SMS/GetCode", ResponseResult.class),//短信

    GetRegister("/user/register", ResponseResult.class),//注册

    Getlogin("/user/login", ResponseResult.class),//登录

    GetFindPass("/user/FindPass", ResponseResult.class),//找回密码

    GetUppass("/user/Uppass", ResponseResult.class),//修改密码

    GetInfo("/user/Info", ResponseResult.class),//获取基本信息

    GetUpInfo("/user/UpInfo", ResponseResult.class),//修改基本信息

    GetloginOut("/user/LoginOut", ResponseResult.class),//注销登录

    GetBindCode("/user/BindCode", ResponseResult.class),//绑定激活码

    //Article
    GetArticleIndex("/Article/Index", ResponseResult.class),//获取文章信息

    GetArticleRecd("/Article/Recd", ResponseResult.class),//推荐文章--

    GetArticleGetTopNews("/Article/GetTopNews", ResponseResult.class),//获取首页质询--

    GetArticleNewsDeteil("/Article/NewsDeteil", ResponseResult.class),//获取质询详情--

    GetArticleSearch("/Article/Search", ResponseResult.class),//搜索文章接口？？

    GetArticleDetail("/Article/Detail", ResponseResult.class),//文章简介？？

    GetArticleGetHtmlContent("/Article/GetHtmlContent", ResponseResult.class),//文章内容-----？

    GetArticleAdvSearch("/Article/AdvSearch", ResponseResult.class),//文章内容----？

    //ShoppingCart
    GetCartInfo("/Cart/Info", ResponseResult.class),//获取购物车

    GetCartUpdateNumber("/Cart/UpdateNumber", ResponseResult.class),//更改购物车商品数量

    GetCartUpdateDelete("/Cart/Delete", ResponseResult.class),//删除商品


    GetFavorFind("/Favor/Find", ResponseResult.class),//查询,返回用户所收藏的数据

    GetFavorAdd("/Favor/Add", ResponseResult.class),//添加收藏

    GetFavorCancel("/Favor/Cancel", ResponseResult.class),//取消收藏

    GetFavorBatchAdd("/Favor/BatchAdd", ResponseResult.class),//批量添加收藏

    GetOrderCreate("/Order/Create", ResponseResult.class),//创建订单

    GetOrderFind("/Order/Find", ResponseResult.class),//获取全部订单 0全部；1未完成；3已完成

    GetOrderSecPay("/Order/SecPay", ResponseResult.class),

    GetOrderRebuildInvoice("/Order/RebuildInvoice", ResponseResult.class),

    GetOrderFindDetail("/Order/FindDetail", ResponseResult.class),//根据订单编号获取订单详情数据

    GetOrderGetActivityCode("/Order/GetActivityCode", ResponseResult.class),//获取激活码

    GetProductReadType("/Product/ReadType", ResponseResult.class),//获取刊期分类

    GetProductSearch("/Product/Search", ResponseResult.class),//

    GetProductIndex("/Product/Index", ResponseResult.class),//获取刊期数据

    GetProductGetYearPubIssue("/Product/GetYearPubIssue", ResponseResult.class),//获取刊期查询所有年份

    GetProductGetPubIssueByYear("/Product/GetPubIssueByYear", ResponseResult.class),//更具年份查询

    GetProductGetIssueNum("/Product/GetIssueNum", ResponseResult.class),//期刊详情页面,根据期刊和年份获取对应的期刊价格

    GetInvoiceInfo("/Invoice/Info", ResponseResult.class),//获取用户地址

    GetInvoiceDelete("/Invoice/Delete", ResponseResult.class),//删除用户地址

    GetInvoiceAdd("/Invoice/Add", ResponseResult.class),//添加发票信息

    GetInvoiceEdit("/Invoice/Edit", ResponseResult.class),//添加发票信息

    GetInvoiceSetDefault("/Invoice/SetDefault", ResponseResult.class),//设置默认发票

    //home
    GetProductRotchart("/Product/Rotchart", ResponseResult.class),//获取轮播图

    GetProductLast("/Product/Last", ResponseResult.class),//获取最新期刊

    GetProductRecd("/Product/Recd", ResponseResult.class),//获取期刊

    GetProductDetail("/Product/Detail", ResponseResult.class),//获取期刊详情

    GetCartAdd("/Cart/Add", ResponseResult.class),//加入购物车

    GetCartBatchAdd("/Cart/BatchAdd", ResponseResult.class),//加入购物车

    GetCartGetCartByID("/Cart/GetCartByID", ResponseResult.class),//根据carditme请求数据

    GetPublicationGetPubIssueList("/Publication/GetPubIssueList", ResponseResult.class),//根据code搜索

    GetPublicationGetAllPublication("/Publication/GetAllPublication", ResponseResult.class),//根据code搜索

    //Address
    GetAddressInfo("/Address/Info", ResponseResult.class),//获取用户地址

    GetAddressEdit("/Address/Edit", ResponseResult.class),//编辑收货地址

    GetAddressDelete("/Address/Delete", ResponseResult.class),//删除收货地址

    GetAddressSetDefault("/Address/SetDefault", ResponseResult.class),//设置默认收货地址

    GetAddressGetDefault("/Address/GetDefault", ResponseResult.class),//获取默认地址，如没有默认地址给地址列表中一个地址

    GetAddressAdd("/Address/Add", ResponseResult.class);//添加收货地址


    /**
     * 服务器基地址
     */
    private String host;


    private String opt;
    private Class<? extends ResponseResult> clazz;
    private RequestMethod requestMethod = RequestMethod.POST;
//    public  String http = "https://";
        public String http = "http://";
    //    public static String localDefValue = "172.25.75.47:83";
//    private String projectName = "/v1";
    public static String localDefValue = "210.12.84.109";
    private String projectName = "/API/v1";
//    public static String localDefValue = "mag.ctax.org.cn";
//    private String projectName = "/api/v1";


    private String getUrl() {
        return localDefValue;
    }

    private String oneParameter = "";

    public String getOneParameter() {
        return oneParameter;
    }

    public void setOneParameter(String oneParameter) {
        this.oneParameter = oneParameter;
    }

    /**
     * 构造函数
     *
     * @param opt 接口地址
     */
    ApiType(String opt) {
        host = http + getUrl() + "/" + projectName;

        this.opt = opt;
        this.requestMethod = RequestMethod.POST;
    }

    /**
     * 构造函数
     *
     * @param opt   接口
     * @param clazz 接收文件的bean文件
     */
    ApiType(String opt, Class<? extends ResponseResult> clazz) {
        host = http + getUrl() + "/" + projectName;
        this.opt = opt;
        this.clazz = clazz;
        this.requestMethod = RequestMethod.POST;
    }

    /**
     * 构造函数
     *
     * @param host  基地址
     * @param opt   接口
     * @param clazz 接收的bean文件
     */
    ApiType(String host, String opt, Class<? extends ResponseResult> clazz) {
//        String url = new DbDao(GlobleApplication.applicationContext).selectTrueIP();
        host = http + getUrl() + "/" + projectName;

        this.host = host;
        this.opt = opt;
        this.clazz = clazz;
        this.requestMethod = RequestMethod.POST;
    }

    /**
     * 构造函数
     *
     * @param opt           接口地址
     * @param clazz         接收数据的bean文件
     * @param requestMethod 请求类型
     */
    ApiType(String opt, Class<? extends ResponseResult> clazz, RequestMethod requestMethod) {
//        String url = new DbDao(GlobleApplication.applicationContext).selectTrueIP();
        host = http + getUrl() + "/" + projectName;

        this.opt = opt;
        this.clazz = clazz;
        this.requestMethod = requestMethod;
    }

    public String getHost() {
        return host;
    }

    public String getOpt() {
        if (!StringUtil.isEmpty(getOneParameter())) {
            return host + opt + getOneParameter();
        } else {
            return host + opt;
        }
    }

    public String getOpts() {
        return opt;

    }

    public Class<? extends ResponseResult> getClazz() {
        return clazz;
    }


    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setHost(String host) {
//        String url = new DbDao(GlobleApplication.applicationContext).selectTrueIP();
        this.host = http + host + projectName;
//        this.host = host;
    }

    /**
     * 设置请求类型
     *
     * @param method
     * @return
     */
    public ApiType setMethod(RequestMethod method) {
        requestMethod = method;
        return this;
    }

    public enum RequestMethod {
        POST("POST"), GET("GET"), FILE("FILE"), DOWNFILE("DOWNFILE");
        private String requestMethodName = "POST";

        RequestMethod(String requestMethodName) {
            this.requestMethodName = requestMethodName;
        }

        public String getRequestMethodName() {
            return requestMethodName;
        }
    }
}
