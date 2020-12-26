package io.dcloud.H5E4CA812.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.AllOrderCopyJHMActivity;
import io.dcloud.H5E4CA812.view.activity.BillingInformationInfoImageActivity;
import io.dcloud.H5E4CA812.view.activity.ShoppingCartConfirmOrderActivity;
import io.dcloud.H5E4CA812.view.bean.OrderDetailBeans;
import io.dcloud.H5E4CA812.view.bean.TestPayBean;
import io.dcloud.H5E4CA812.view.bean.WXRequestBean;
import io.dcloud.H5E4CA812.view.widget.AllOrderPayPhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.ShoppingCartConfirmOrderPayPhotoPopWin;

import com.google.gson.Gson;
import com.swwx.paymax.PayResult;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.view.widget.ShoppingCartConfirmOrderTicketPhotoPopWin;

/**
 * 全部订单适配器
 */

public class OrderDetailAdapter extends RecyclerBaseAdatpter implements PaymaxCallback {
    private List<OrderDetailBeans> mDataList;
    Activity activity;
    private int bukaifapiaoType;

    private double total_price;

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public OrderDetailAdapter(List<OrderDetailBeans> dataList, Activity activity) {
        this.mDataList = dataList;
        this.activity = activity;
    }

    public void setmDataList(List<OrderDetailBeans> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_all_order_title, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvTitle.setText(mDataList.get(position).getOrderDate());
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
        bukaifapiaoType = mDataList.get(position).getOrderInvoiceStatus();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity);
        manager.setAutoMeasureEnabled(true);
        ((RecyclerViewHolder) holder).rcvChild.setLayoutManager(manager);
        ((RecyclerViewHolder) holder).rcvChild.setAdapter(new ChildAdapter(mDataList.get(position).getOrderDetail(), position));

        total_price = 0.0;
        for (int t = 0; t < mDataList.get(position).getOrderDetail().size(); t++) {
            total_price = total_price + mDataList.get(position).getOrderDetail().get(t).getPubProductPrice() * mDataList.get(position).getOrderDetail().get(t).getOrderDetailCount();
        }

    }


    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public RecyclerView rcvChild;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_store_name);
            rcvChild = (RecyclerView) itemView.findViewById(R.id.shopping_order_title_recycler);
        }
    }


    public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {
        public List<OrderDetailBeans.OrderDetailBean> childList;
        public int parentIndex;

        public ChildAdapter(List<OrderDetailBeans.OrderDetailBean> childList, int parentIndex) {
            this.childList = childList;
            this.parentIndex = parentIndex;
        }

        @Override
        public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_all_order_child, parent, false);
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildViewHolder holder, final int position) {
            OrderDetailBeans.OrderDetailBean childBean = childList.get(position);
            ImageViewUtil.setImage(activity, childBean.getProduct().getProdImg(), holder.ivPhoto);
            holder.prd_name.setText(childBean.getProduct().getProdName());
            if (childBean.getProduct().getProdType() == 30) {
                holder.goods_journal_null.setText(childBean.getProduct().getProdYear() + "年 期刊（年度套餐）");
            } else if (childBean.getProduct().getProdType() == 10) {
                holder.goods_journal_null.setText(childBean.getProduct().getProdYear() + "年" + childBean.getProduct().getProdIssue() + "期");
            } else {
                holder.goods_journal_null.setVisibility(View.GONE);
            }
            holder.goods_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPayBottom(childBean.getOrderID());
                }
            });

            //商品名称
            String goods_name = childBean.getPubProductName();
            //商品价格
            String goods_price = childBean.getPubProductPrice() + "";
            //商品数量
            int goods_num = childBean.getOrderDetailCount();
            //商品ID
//            final String goods_id = childBean.getCartItemID() + "";
            if (goods_name != null) {
                holder.prd_name.setText(goods_name);
            } else {
                holder.prd_name.setText("");
            }
            holder.goods_journal_num.setText("x " + goods_num);
            holder.goods_journal_pre.setText("￥" + goods_price);

            if (mDataList.get(parentIndex).getOrderStatus() == 1) {
                holder.goods_journal_layout.setVisibility(View.GONE);
            }
            if (position == childList.size() - 1) {
                holder.view.setVisibility(View.GONE);
                holder.viewLast.setVisibility(View.VISIBLE);
                holder.goods_journal_yf_layout.setVisibility(View.VISIBLE);
                holder.goods_journal_ddzje.setText(decimalFormat.format(total_price));
                if (mDataList.get(parentIndex).getOrderStatus() == 3) {
                    holder.goods_pay_layout.setVisibility(View.GONE);
                    holder.goods_journal_layout.setVisibility(View.VISIBLE);
                } else if (mDataList.get(parentIndex).getOrderStatus() == 1) {
                    holder.goods_pay_layout.setVisibility(View.VISIBLE);
                    holder.goods_journal_layout.setVisibility(View.GONE);
                } else if (mDataList.get(parentIndex).getOrderStatus() == 1) {
                    holder.goods_pay_layout.setVisibility(View.VISIBLE);
                }
                if (bukaifapiaoType == 10) {
                    holder.goods_billing.setVisibility(View.VISIBLE);
                    holder.goods_billing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTicketBottom(childBean.getOrderID() + "");
                        }
                    });
                }
            } else {
                holder.view.setVisibility(View.VISIBLE);
                holder.viewLast.setVisibility(View.GONE);

//                    holder.goods_journal_layout.setVisibility(View.VISIBLE);
//                    holder.goods_pay_layout.setVisibility(View.GONE);

            }
            holder.goods_journal_fzjhm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AllOrderCopyJHMActivity.class);
                    intent.putExtra("OrderDetailID", ((int) childBean.getOrderDetailID()) + "");
                    activity.startActivity(intent);

                }
            });
            holder.goods_journal_lookfp.setVisibility(View.GONE);
        }


        @Override
        public int getItemCount() {
            return childList == null ? 0 : childList.size();
        }

        public void setData(List<OrderDetailBeans.OrderDetailBean> childList) {
            this.childList = childList;
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_photo)
            ImageView ivPhoto;
            @BindView(R.id.prd_name)
            TextView prd_name;
            @BindView(R.id.goods_journal_null)
            TextView goods_journal_null;
            @BindView(R.id.goods_journal_num)
            TextView goods_journal_num;
            @BindView(R.id.goods_journal_pre)
            TextView goods_journal_pre;
            @BindView(R.id.goods_journal_yf)
            TextView goods_journal_yf;
            @BindView(R.id.goods_journal_ddzje)
            TextView goods_journal_ddzje;
            @BindView(R.id.goods_pay_layout)
            RelativeLayout goods_pay_layout;
            @BindView(R.id.goods_pay)
            TextView goods_pay;
            @BindView(R.id.goods_journal_layout)
            RelativeLayout goods_journal_layout;
            @BindView(R.id.goods_journal_yf_layout)
            RelativeLayout goods_journal_yf_layout;
            @BindView(R.id.goods_journal_fzjhm)
            TextView goods_journal_fzjhm;
            @BindView(R.id.goods_journal_lookfp)
            TextView goods_journal_lookfp;
            @BindView(R.id.order_layout)
            LinearLayout order_layout;
            @BindView(R.id.goods_billing)
            TextView goods_billing;

            @BindView(R.id.view)
            View view;
            @BindView(R.id.view_last)
            View viewLast;

            ChildViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    int payType = 5;
    String reqOrderID;

    public void showPayBottom(double OrderID) {
        AllOrderPayPhotoPopWin payPhotoPopWin = new AllOrderPayPhotoPopWin(activity);
        payPhotoPopWin.showAtLocation(activity.findViewById(R.id.goods_pay), Gravity.CENTER, 0, 0);
        payPhotoPopWin.setOnChangeCountListener(new AllOrderPayPhotoPopWin.OnChangeListener() {
            @Override
            public void onChange(int payType) {
                OrderDetailAdapter.this.payType = payType;
                Map map = new HashMap();
                map.put("OrderForm", "20");//订单来源（PC=10,安卓=20,IOS=30）
                map.put("PayModel", payType);//微信APP支付是22
                map.put("OrderID", (int) OrderID);//
                reqOrderID = ((int) OrderID) + "";
                execApi(ApiType.GetOrderSecPay, map, activity);
                payPhotoPopWin.dismiss();
            }

        });
    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    Gson gson = new Gson();
    TestPayBean testPayBean;
    WXRequestBean wxRequestBean;

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(activity);
                return;
            }
            LogUtil.i("req", req.getErrorMsg());
            return;
        }
        if (req.getData().getUrl().contains("/Order/SecPay")) {//获取默认地址
            if (payType == 5) {
                testPayBean = gson.fromJson(gson.toJson(req.getData().getData()), TestPayBean.class);
                ShoppingCartConfirmOrderActivity.sendJson sendJson = new ShoppingCartConfirmOrderActivity.sendJson();
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
                PaymaxSDK.payWithAliToken(send, activity, this);
                LogUtil.i("getData", send);
            }
            if (payType == 22) {
                wxRequestBean = gson.fromJson(gson.toJson(req.getData().getData()), WXRequestBean.class);
                GlobleApplication.getInstance().orderId = wxRequestBean.getOrderPKID() + "";
                IWXAPI api = WXAPIFactory.createWXAPI(activity, wxRequestBean.getAppid(), false);
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
                    ToastUtil.makeText(activity, "微信版本过低");
                }
            }
        }
        if (req.getData().getUrl().contains("/Order/RebuildInvoice")) {
            if (req.getData().getRet()) {
                ToastUtil.makeText(activity, req.getData().getMsg());
            }
        }
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

    private AllOrderAdapter.OnReOrderListener OrderListener;

    public void setOnReOrderListenerListener(AllOrderAdapter.OnReOrderListener OrderListener) {
        this.OrderListener = OrderListener;
    }

    public interface OnReOrderListener {
        void onReOreder(int repType, String reqOrderID);

    }

    @Override
    public void onPayFinished(PayResult payResult) {
        String msg = payResult.getDesc();
        switch (payResult.getCode()) {
            case PaymaxSDK.CODE_SUCCESS:
                msg = "Complete, Success!.";
                if (OrderListener != null) {
                    OrderListener.onReOreder(1, reqOrderID);
                }
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
        ToastUtil.makeText(activity, msg);
    }

    public void showTicketBottom(String OrderId) {
        ShoppingCartConfirmOrderTicketPhotoPopWin takePhotoPopWin = new ShoppingCartConfirmOrderTicketPhotoPopWin(activity, 0, 0);
        takePhotoPopWin.showAtLocation(activity.findViewById(R.id.order_detail_num), Gravity.CENTER, 0, 0);
        takePhotoPopWin.setOnChangeCountListener(new ShoppingCartConfirmOrderTicketPhotoPopWin.OnChangeListener() {
            @Override
            public void onChange(Boolean isOK, String type, String UserInvoiceID) {
                if (!StringUtil.isEmpty(UserInvoiceID)) {
                    Map map = new HashMap();
                    map.put("OrderID", OrderId);
                    map.put("UserInvoiceID", UserInvoiceID);
                    execApi(ApiType.GetOrderRebuildInvoice, map, activity);
                }
            }
        });
    }
}
