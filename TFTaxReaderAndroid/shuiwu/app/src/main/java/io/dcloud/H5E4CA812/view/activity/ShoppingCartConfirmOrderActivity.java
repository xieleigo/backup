package io.dcloud.H5E4CA812.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.adapter.ShoppingCarConfirmOrderAdapter;
import io.dcloud.H5E4CA812.view.bean.CarItme;
import io.dcloud.H5E4CA812.view.bean.OrderDetailBeans;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;
import io.dcloud.H5E4CA812.view.bean.ShoppingCarDataNewBean;
import io.dcloud.H5E4CA812.view.bean.TestPayBean;
import io.dcloud.H5E4CA812.view.bean.ToCardItmeIDBean;
import io.dcloud.H5E4CA812.view.bean.WXRequestBean;
import io.dcloud.H5E4CA812.view.widget.ShoppingCartConfirmOrderPayPhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.ShoppingCartConfirmOrderTicketPhotoPopWin;

import com.google.gson.Gson;
import com.swwx.paymax.PayResult;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;

/**
 * 购物车订单详情适配器
 */
public class ShoppingCartConfirmOrderActivity extends BaseActivity implements PaymaxCallback {
    List<ShoppingCarDataNewBean> tempStoress = new ArrayList<>();
    @BindView(R.id.shopping_order_name)
    TextView shopping_order_name;
    @BindView(R.id.shopping_order_phone)
    TextView shopping_order_phone;
    @BindView(R.id.shopping_order_address)
    TextView shopping_order_address;
    @BindView(R.id.shopping_order_recycler)
    RecyclerView shopping_order_recycler;
    @BindView(R.id.shopping_order_num)
    TextView shopping_order_num;
    @BindView(R.id.shopping_order_total_price)
    TextView shopping_order_total_price;
    @BindView(R.id.shopping_confirm_order_payment_type_layout)
    RelativeLayout shopping_confirm_order_payment_type_layout;
    @BindView(R.id.shopping_confirm_order_ticket_type_layout)
    RelativeLayout shopping_confirm_order_ticket_type_layout;
    @BindView(R.id.shopping_confirm_order_payment_type_text)
    TextView shopping_confirm_order_payment_type_text;
    @BindView(R.id.shopping_confirm_order_ticket_type_text)
    TextView shopping_confirm_order_ticket_type_text;
    @BindView(R.id.shopping_order_address_layout)
    RelativeLayout shopping_order_address_layout;
    @BindView(R.id.shopping_order_address_layout_false)
    RelativeLayout shopping_order_address_layout_false;
    @BindView(R.id.shopping_order_address_layout_true)
    RelativeLayout shopping_order_address_layout_true;
    @BindView(R.id.mrderss_test)
    TextView mrderss_test;
    ReceivingAddressBean receivingAddressBean;
    private Boolean Istake = false;
    private int payType = 5;//默认支付宝 //微信 1
    List<ReceivingAddressBean> addressBeanList;
    ShoppingCarConfirmOrderAdapter adpter;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car_confirm_order);
        ButterKnife.bind(this);
        mActivity = this;
        initTitle();
        initData();
    }

    private void initData() {
        Loading.getInstance(this).show();

        execApi(ApiType.GetAddressGetDefault, null);//获取地址
        Gson gson = new Gson();
        String str = getIntent().getStringExtra("tempStoress");
        if (str.equals("ture")) {
            String cardItmeId = getIntent().getStringExtra("cardItmeId");
            Map map = new HashMap();
            map.put("CartItemIDS", cardItmeId);
            execApi(ApiType.GetCartGetCartByID, map);//获取地址
        }
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i < jsonArray.length(); i++) {
                ShoppingCarDataNewBean date = gson.fromJson(jsonArray.get(i).toString(), ShoppingCarDataNewBean.class);
                tempStoress.add(date);
            }
        } catch (Exception e) {
        }
        LogUtil.i("tempStorjson", tempStoress.toString());

        adpter = new ShoppingCarConfirmOrderAdapter(tempStoress, this, shopping_order_num
                , shopping_order_total_price);
        shopping_order_recycler.setLayoutManager(new GridLayoutManager(this, 1));
        shopping_order_recycler.setAdapter(adpter);
        shopping_order_recycler.setHasFixedSize(true);
        shopping_order_recycler.setNestedScrollingEnabled(false);
        int type = getIntent().getIntExtra("type", -1);
        if (type == 3) {
            shopping_confirm_order_ticket_type_layout.setVisibility(View.GONE);
        }
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("确认订单");
    }

    String UserInvoiceID = "";
    String UserAddress = "";
    List<CarItme> carItmes = new ArrayList<>();

    @OnClick({R.id.back_but, R.id.shopping_confirm_order_payment_type_layout,
            R.id.shopping_confirm_order_ticket_type_layout,
            R.id.shopping_order_address_layout, R.id.shopping_order_but})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.shopping_confirm_order_payment_type_layout:
                showPayBottom(view);
                break;
            case R.id.shopping_confirm_order_ticket_type_layout:
                showTicketBottom();
                break;
            case R.id.shopping_order_address_layout:
                Intent intent = new Intent(this, ReceivingAddressActivity.class);
                intent.putExtra("IsShoping", 100);
                startActivityForResultAddIntent(intent, 100);
                break;
            case R.id.shopping_order_but:
                for (int i = 0; i < tempStoress.size(); i++) {
                    for (int t = 0; t < tempStoress.get(i).getData().size(); t++) {
                        carItmes.add(new CarItme(tempStoress.get(i).getData().get(t).getCartItemID()));
                    }
                }
                if (receivingAddressBean != null) {
                    UserAddress = receivingAddressBean.getUserAddrID() + "";
                }
                if (!Istake) {
                    UserInvoiceID = "";
                }
                requestBean requestBean = new requestBean(UserInvoiceID, UserAddress
                        , carItmes, payType);
                execApiList(ApiType.GetOrderCreate, gson.toJson(requestBean));
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            receivingAddressBean = (ReceivingAddressBean) data.getSerializableExtra("ReceivingAddressBean");
            shopping_order_name.setText(receivingAddressBean.getUserAddressMan());
            shopping_order_phone.setText(receivingAddressBean.getUserAddressMobile());
            shopping_order_address.setText(receivingAddressBean.getUserAddressDetail());
            if (receivingAddressBean.getUserAddressIsDefault() == 1) {
                mrderss_test.setVisibility(View.VISIBLE);
            } else {
                mrderss_test.setVisibility(View.GONE);
            }
            shopping_order_address_layout_true.setVisibility(View.VISIBLE);
            shopping_order_address_layout_false.setVisibility(View.GONE);
        }
        if (requestCode == 11 && data != null) {
            showTicketBottom();
        }
    }

    Gson gson = new Gson();

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(this).hide();
        ToastUtil.makeText(this, req.toString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (receivingAddressBean != null) {
                        shopping_order_address_layout_true.setVisibility(View.VISIBLE);
                        shopping_order_address_layout_false.setVisibility(View.GONE);
                        shopping_order_name.setText(receivingAddressBean.getUserAddressMan());
                        shopping_order_phone.setText(receivingAddressBean.getUserAddressMobile());
                        shopping_order_address.setText(receivingAddressBean.getUserAddressDetail());
                        if (receivingAddressBean.getUserAddressIsDefault() == 1) {
                            mrderss_test.setVisibility(View.VISIBLE);
                        } else {
                            mrderss_test.setVisibility(View.GONE);
                        }
                    } else {
                        shopping_order_address_layout_true.setVisibility(View.GONE);
                        shopping_order_address_layout_false.setVisibility(View.VISIBLE);
                    }
                    break;
                case 200:
                    if (orderDetailBeans.getOrderStatus() == 3) {
                        Intent intent = new Intent(ShoppingCartConfirmOrderActivity.this, PaySuccessActivity.class);
                        intent.putExtra("OrderId", GlobleApplication.orderId);
                        startActivity(intent);
                    } else {
                        ToastUtil.makeText(ShoppingCartConfirmOrderActivity.this, "您的订单没有支付成功去全部订单继续支付");
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    TestPayBean testPayBean;
    WXRequestBean wxRequestBean;
    OrderDetailBeans orderDetailBeans;

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                if (req.getData().getUrl().contains("/Address/GetDefault")) {//获取默认地址
                    addressBeanList = new ArrayList<ReceivingAddressBean>();
                    receivingAddressBean = gson.fromJson(gson.toJson(req.getData().getData()), ReceivingAddressBean.class);

                    Message msg = new Message();
                    msg.what = 100;
                    handler.sendMessage(msg);
                }
                if (req.getData().getUrl().contains("/Order/FindDetail")) {
                    orderDetailBeans = gson.fromJson(gson.toJson(req.getData().getData()), OrderDetailBeans.class);
                    GlobleApplication.orderId = testPayBean.getOrderPKID();
                    Message msg = new Message();
                    msg.what = 200;
                    handler.sendMessage(msg);
                }

                if (req.getData().getUrl().contains("/Order/Create")) {
                    if (payType == 5) {
                        testPayBean = gson.fromJson(gson.toJson(req.getData().getData()), TestPayBean.class);
                        sendJson sendJson = new sendJson();
                        sendJson.setReturnCode(testPayBean.getReturnCode());
                        sendJson.setReturnMessage(testPayBean.getReturnMessage());
                        sendJson.setTradeNo(testPayBean.getTradeNo());
                        sendJson.setMerchantId(testPayBean.getMerchantId());
                        sendJson.setOrderId(testPayBean.getOrderId());
                        sendJson.setToken(testPayBean.getToken());
                        sendJson.setTradeType("ALIPAYAPP");
                        sendJson.setH5JumpUrl(testPayBean.getH5JumpUrl());
                        sendJson.setSuccess(true);
                        String send = gson.toJson(sendJson);
                        PaymaxSDK.payWithAliToken(send, this, this);
                        LogUtil.i("getData", send);
                    }
                    if (payType == 22) {
                        wxRequestBean = gson.fromJson(gson.toJson(req.getData().getData()), WXRequestBean.class);
                        GlobleApplication.getInstance().orderId = wxRequestBean.getOrderPKID() + "";
                        IWXAPI api = WXAPIFactory.createWXAPI(this, wxRequestBean.getAppid(), false);
                        api.registerApp(wxRequestBean.getAppid());
                        if (api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT) {
                            PayReq reqs = new PayReq();//PayReq就是订单信息对象
                            reqs.appId = wxRequestBean.getAppid();//你的微信appid
                            reqs.partnerId = wxRequestBean.getMch_id();//商户号
                            reqs.prepayId = wxRequestBean.getPrepay_id();//预支付交易会话ID
                            reqs.nonceStr = wxRequestBean.getNonce_str();//随机字符串
                            reqs.packageValue = "Sign=WXPay";//扩展字段,这里固定填写Sign=WXPay
                            String time = StringUtil.genTimeStamp() + "";
                            reqs.timeStamp = time;//时间戳
                            wxRequestBean.setTimeStamp(time);
                            reqs.sign = getsign(wxRequestBean);
                            api.sendReq(reqs);//将订单信息对象发送给微信服务器，即发送支付请求

                        } else {
                            ToastUtil.makeText(this, "微信版本过低");
                        }
                    }
                }
                if (req.getData().getUrl().contains("/Cart/GetCartByID")) {
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ToCardItmeIDBean itme = gson.fromJson(jsonArray.get(i).toString(), ToCardItmeIDBean.class);
                        toCardItmeIDBeanList.add(itme);
                    }


                    List<ShoppingCarDataNewBean.DataBean> list = new ArrayList<>();
                    ShoppingCarDataNewBean shoppingCarDataNewBean = new ShoppingCarDataNewBean();
                    shoppingCarDataNewBean.setAddDate(StringUtil.timeCurre());
                    shoppingCarDataNewBean.setSelect_shop(true);

                    for (int i = 0; i < toCardItmeIDBeanList.size(); i++) {
                        ShoppingCarDataNewBean.DataBean dataBean = new ShoppingCarDataNewBean.DataBean();
                        dataBean.setCartItemID(toCardItmeIDBeanList.get(i).getCartItemID());
                        dataBean.setProductCount(toCardItmeIDBeanList.get(i).getProductCount());
                        dataBean.setSelect(true);
                        ShoppingCarDataNewBean.DataBean.ProductBean productBean = new ShoppingCarDataNewBean.DataBean.ProductBean();
                        productBean.setProdPrice(toCardItmeIDBeanList.get(i).getProduct().getProdPrice());
                        productBean.setProdName(toCardItmeIDBeanList.get(i).getProduct().getProdName());
                        productBean.setProdImg(toCardItmeIDBeanList.get(i).getProduct().getProdImg());
                        productBean.setProdYear(toCardItmeIDBeanList.get(i).getProduct().getProdYear());
                        productBean.setProdIssue(toCardItmeIDBeanList.get(i).getProduct().getProdIssue());
                        dataBean.setProduct(productBean);
                        list.add(dataBean);
                    }
                    shoppingCarDataNewBean.setData(list);
                    tempStoress.clear();
                    tempStoress.add(shoppingCarDataNewBean);
                    adpter.setmDataList(tempStoress);
                }
            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(ShoppingCartConfirmOrderActivity.this);
            }
        }
        Loading.getInstance(this).hide();
    }

    List<ToCardItmeIDBean> toCardItmeIDBeanList = new ArrayList();

    @Override
    public void onPayFinished(PayResult result) {
        String msg = result.getDesc();
        switch (result.getCode()) {
            case PaymaxSDK.CODE_SUCCESS:
                msg = "Complete, Success!.";
                tepType = 1;
                break;

            case PaymaxSDK.CODE_ERROR_CHARGE_JSON:
                msg = "Json error.";
                break;

            case PaymaxSDK.CODE_FAIL_CANCEL:
                msg = "cancel pay.";
                break;

            case PaymaxSDK.CODE_ERROR_CHARGE_PARAMETER:
                msg = "pay failed";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_INSTALL:
                msg = "wx not install.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_SUPPORT_PAY:
                msg = "ex not support pay.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_UNKNOW:
                msg = "wechat failed.";
                break;

            case PaymaxSDK.CODE_ERROR_ALI_DEAL:
                msg = "alipay dealing.";
                break;

            case PaymaxSDK.CODE_ERROR_CONNECT:
                msg = " network connection failed.";
                break;

            case PaymaxSDK.CODE_ERROR_CHANNEL:
                msg = "channel error.";
                break;

            case PaymaxSDK.CODE_ERROR_LAK_USER_NO_NULL:
                msg = "lklpay user no is null.";
                break;

        }
        ToastUtil.makeText(this, msg);
    }

    private String getsign(WXRequestBean wxRequestBean) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        GlobleApplication.getInstance().WXappId = wxRequestBean.getAppid();
        parameters.put("appid", wxRequestBean.getAppid());
        parameters.put("noncestr", wxRequestBean.getNonce_str());
        parameters.put("package", "Sign=WXPay");
        parameters.put("partnerid", wxRequestBean.getMch_id());
        parameters.put("prepayid", wxRequestBean.getPrepay_id());
        parameters.put("timestamp", wxRequestBean.getTimeStamp());
        String sign = StringUtil.createSign("UTF-8", parameters);
        LogUtil.e("WX", "sign：" + sign);
        return sign;
    }


    public void showPayBottom(View view) {
        ShoppingCartConfirmOrderPayPhotoPopWin payPhotoPopWin = new ShoppingCartConfirmOrderPayPhotoPopWin(this);
        payPhotoPopWin.showAtLocation(findViewById(R.id.shopping_order_but), Gravity.CENTER, 0, 0);
        payPhotoPopWin.setOnChangeCountListener(new ShoppingCartConfirmOrderPayPhotoPopWin.OnChangeListener() {
            @Override
            public void onChange(int payType) {
                ShoppingCartConfirmOrderActivity.this.payType = payType;
                if (payType == 5) {
                    shopping_confirm_order_payment_type_text.setText("支付宝");
                } else if (payType == 22) {
                    shopping_confirm_order_payment_type_text.setText("微信");
                }
                payPhotoPopWin.dismiss();
            }
        });
    }

    public void showTicketBottom() {
        ShoppingCartConfirmOrderTicketPhotoPopWin takePhotoPopWin = new ShoppingCartConfirmOrderTicketPhotoPopWin(this, 0, 0);
        takePhotoPopWin.showAtLocation(findViewById(R.id.shopping_order_but), Gravity.CENTER, 0, 0);
        takePhotoPopWin.setOnChangeCountListener(new ShoppingCartConfirmOrderTicketPhotoPopWin.OnChangeListener() {
            @Override
            public void onChange(Boolean isOK, String type, String UserInvoiceID) {
                if (isOK) {
                    shopping_confirm_order_ticket_type_text.setText(type);
                    ShoppingCartConfirmOrderActivity.this.UserInvoiceID = UserInvoiceID;
                } else {
                    shopping_confirm_order_ticket_type_text.setText("不需要发票");
                }
                Istake = isOK;
            }
        });
    }

    public static class sendJson {
        private String secretKey;
        private String returnCode;
        private String returnMessage;
        private String tradeNo;
        private String merchantId;
        private String orderId;
        private String token;
        private String tradeType;
        private String xcxUsername;
        private String xcxReqpath;
        private String h5JumpUrl;
        private boolean success;

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMessage() {
            return returnMessage;
        }

        public void setReturnMessage(String returnMessage) {
            this.returnMessage = returnMessage;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getXcxUsername() {
            return xcxUsername;
        }

        public void setXcxUsername(String xcxUsername) {
            this.xcxUsername = xcxUsername;
        }

        public String getXcxReqpath() {
            return xcxReqpath;
        }

        public void setXcxReqpath(String xcxReqpath) {
            this.xcxReqpath = xcxReqpath;
        }

        public String getH5JumpUrl() {
            return h5JumpUrl;
        }

        public void setH5JumpUrl(String h5JumpUrl) {
            this.h5JumpUrl = h5JumpUrl;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return "sendJson{" +
                    "secretKey='" + secretKey + '\'' +
                    ", returnCode='" + returnCode + '\'' +
                    ", returnMessage='" + returnMessage + '\'' +
                    ", tradeNo='" + tradeNo + '\'' +
                    ", merchantId='" + merchantId + '\'' +
                    ", orderId='" + orderId + '\'' +
                    ", token='" + token + '\'' +
                    ", tradeType='" + tradeType + '\'' +
                    ", xcxUsername='" + xcxUsername + '\'' +
                    ", xcxReqpath='" + xcxReqpath + '\'' +
                    ", h5JumpUrl='" + h5JumpUrl + '\'' +
                    ", success=" + success +
                    '}';
        }
    }

    public class requestBean {
        String UserInvoiceID;
        String UserAddressID;
        String OrderForm;
        int PayModel;
        List<CarItme> CartItems;

        public requestBean(String UserInvoiceID, String UserAddressID, List<CarItme> carItmes, int PayModel) {
            if (StringUtil.isEmpty(UserInvoiceID)) {
                this.UserInvoiceID = "0";
            } else {
                this.UserInvoiceID = UserInvoiceID;
            }
            if (StringUtil.isEmpty(UserAddressID)) {
                this.UserAddressID = "0";
            } else {
                this.UserAddressID = UserAddressID;
            }
            this.OrderForm = "20";//android 20  ios 30
            this.CartItems = carItmes;
            this.PayModel = PayModel;
        }

        public int getTradeType() {
            return PayModel;
        }

        public void setTradeType(int tradeType) {
            this.PayModel = tradeType;
        }

        public List<CarItme> getCartItems() {
            return CartItems;
        }

        public String getUserInvoiceID() {
            return UserInvoiceID;
        }

        public void setUserInvoiceID(String userInvoiceID) {
            UserInvoiceID = userInvoiceID;
        }

        public String getUserAddressID() {
            return UserAddressID;
        }

        public void setUserAddressID(String userAddressID) {
            UserAddressID = userAddressID;
        }

        public void setCartItems(List<CarItme> cartItems) {
            CartItems = cartItems;
        }

        public String getOrderForm() {
            return OrderForm;
        }

        public void setOrderForm(String orderForm) {
            OrderForm = orderForm;
        }

        @Override
        public String toString() {
            return "requestBean{" +
                    "UserInvoiceID='" + UserInvoiceID + '\'' +
                    ", UserAddressID='" + UserAddressID + '\'' +
                    ", OrderForm='" + OrderForm + '\'' +
                    ", PayModel=" + PayModel +
                    ", CartItems=" + CartItems +
                    '}';
        }
    }

    int tepType = 0;//支付宝APP调取成功后返回状态改为1å

    @Override
    protected void onRestart() {
        super.onRestart();
        if (tepType == 1) {
            Map map = new HashMap();
            map.put("OrderID", testPayBean.getOrderPKID());
            execApi(ApiType.GetOrderFindDetail, map);
        }
    }
}
