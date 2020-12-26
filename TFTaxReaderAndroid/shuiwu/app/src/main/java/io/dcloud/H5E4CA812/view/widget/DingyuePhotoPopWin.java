package io.dcloud.H5E4CA812.view.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.protocol.RequestCert;
import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.QKDetailsSearchActivity;
import io.dcloud.H5E4CA812.view.activity.ShoppingCartConfirmOrderActivity;
import io.dcloud.H5E4CA812.view.bean.DingYuePhotpPopBean;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DingyuePhotoPopWin extends PopupWindow implements OnApiDataReceivedCallback {
    @BindView(R.id.add_showpingcar)
    TextView add_showpingcar;
    @BindView(R.id.to_showping)
    TextView to_showping;
    @BindView(R.id.quit)
    TextView quit;
    @BindView(R.id.benqi_text)
    TextView benqi_text;
    @BindView(R.id.danqi_text)
    TextView danqi_text;
    @BindView(R.id.allyear_text)
    TextView allyear_text;
    @BindView(R.id.quannian_layout)
    RelativeLayout quannian_layout;
    @BindView(R.id.benqi_layout)
    RelativeLayout benqi_layout;
    @BindView(R.id.danqi_layout)
    GridLayout danqi_layout;

    @BindView(R.id.benqi_year)
    TextView benqi_year;
    @BindView(R.id.benqi_qi)
    TextView benqi_qi;
    @BindView(R.id.iv_edit_subtract)
    ImageView iv_edit_subtract;
    @BindView(R.id.iv_edit_add)
    ImageView iv_edit_add;
    @BindView(R.id.tv_edit_buy_number)
    TextView tv_edit_buy_number;
    @BindView(R.id.benqi_qianqian)
    TextView benqi_qianqian;

    @BindView(R.id.quannian_year)
    TextView quannian_year;
    @BindView(R.id.quannian_subtract)
    ImageView quannian_subtract;
    @BindView(R.id.quannian_add)
    ImageView quannian_add;
    @BindView(R.id.quannian_number)
    TextView quannian_number;
    @BindView(R.id.quannian_qianqian)
    TextView quannian_qianqian;

    @BindView(R.id.benpian_text)
    TextView benpian_text;
    @BindView(R.id.benpian_name)
    TextView benpian_name;
    @BindView(R.id.benpian_qikan_name)
    TextView benpian_qikan_name;
    @BindView(R.id.benpian_num)
    TextView benpian_num;
    @BindView(R.id.benpian_qian)
    TextView benpian_qian;
    @BindView(R.id.benpian_layout)
    RelativeLayout benpian_layout;

    @BindView(R.id.lineblow)
    View lineblow;

    Activity activity;
    View view;
    long ReadSourceID;
    private int type = 0;
    private boolean isToCard = true;

    public DingyuePhotoPopWin(Activity activity, long ReadSourceID, int PubIssueID, int PubID, int Year) {
        this.view = LayoutInflater.from(activity).inflate(R.layout.dialog_dingyue, null);
        ButterKnife.bind(this, view);
        this.ReadSourceID = ReadSourceID;
        this.activity = activity;
        init();
        benpian_text.setVisibility(View.GONE);
        benpian_layout.setVisibility(View.GONE);
        benqi_text.setTextColor(activity.getResources().getColor(R.color.ffffff));
        benqi_text.setBackgroundResource(R.color.CollectFragment_buttont_Text);
        benqi_layout.setVisibility(View.VISIBLE);

        map.put("PubIssueID", PubIssueID);
        map.put("PubID", PubID);
        map.put("Year", Year);
        execApi(ApiType.GetProductGetIssueNum, map);
    }

    public DingyuePhotoPopWin(Activity activity,
                              int ArticleID,
                              int PubIssueID,
                              int PubID,
                              int Year,
                              int benqiNum,
                              String title,
                              String qianshu,
                              String qikanname) {
        this.view = LayoutInflater.from(activity).inflate(R.layout.dialog_dingyue, null);
        ButterKnife.bind(this, view);
        this.activity = activity;
        init();

        benpian_text.setVisibility(View.VISIBLE);
        benpian_layout.setVisibility(View.VISIBLE);
        benqi_text.setTextColor(activity.getResources().getColor(R.color.black));
        benqi_text.setBackgroundResource(R.color.background_gray);
        danqi_layout.setVisibility(View.GONE);
        benqi_layout.setVisibility(View.GONE);
        quannian_layout.setVisibility(View.GONE);
        add_showpingcar.setVisibility(View.GONE);
        lineblow.setVisibility(View.GONE);
        type = 3;
        benpian_name.setText(title);
        benpian_qikan_name.setText("《" + qikanname + "》");
        benpian_num.setText(" - " + Year + "年第" + benqiNum + "期");
        benpian_qian.setText("￥" + qianshu);
        map.put("PubIssueID", PubIssueID);
        map.put("PubID", PubID);
        map.put("Year", Year);
        map.put("ArticleID", ArticleID);

        execApi(ApiType.GetProductGetIssueNum, map);
    }


    List<CheckBox> listCheckBox;


    private void initView() {
        benqi_year.setText(dingYuePhotpPopBean.getCurrProduct().getProdYear() + "年");
        benqi_qi.setText("第" + dingYuePhotpPopBean.getCurrProduct().getProdIssue() + "期");
        tv_edit_buy_number.setText(benqiNum + "");
        benqi_qianqian.setText("￥ " + dingYuePhotpPopBean.getCurrProduct().getProdPrice() + "");
        //--------
        listCheckBox = new ArrayList<CheckBox>();
        for (int i = 0; i < dingYuePhotpPopBean.getBatchProduct().size(); i++) {
            CheckBox checkBox = new CheckBox(activity);
            checkBox.setText("[" + dingYuePhotpPopBean.getBatchProduct().get(i).getProdIssue() + "]");
            listCheckBox.add(checkBox);
        }
        for (int i = 0; i < listCheckBox.size(); i++) {
            danqi_layout.addView(listCheckBox.get(i));
        }
        //---------
        quannian_year.setText(dingYuePhotpPopBean.getYearProduct().getProdYear() + "年");
        quannian_number.setText(allYearNum + "");
        quannian_qianqian.setText("￥ " + dingYuePhotpPopBean.getYearProduct().getProdPrice());

        ReadSourceID = dingYuePhotpPopBean.getCurrProduct().getProdID();
    }


    Map map = new HashMap();
    Gson gson = new Gson();
    private int benqiNum = 1;
    private int allYearNum = 1;
    List<sendBean> shenBeanList = new ArrayList<sendBean>();

    @OnClick({R.id.to_showping, R.id.add_showpingcar,
            R.id.quit, R.id.benqi_text, R.id.danqi_text, R.id.allyear_text,
            R.id.iv_edit_subtract,
            R.id.iv_edit_add,
            R.id.quannian_subtract,
            R.id.quannian_add,
            R.id.benpian_text
    })
    public void click(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_subtract:
                if (benqiNum <= 1) {
                    ToastUtil.makeText(activity, "不能再少了");
                    return;
                }
                benqiNum--;
                tv_edit_buy_number.setText(benqiNum + "");
                benqi_qianqian.setText("￥ " + (dingYuePhotpPopBean.getCurrProduct().getProdPrice() * benqiNum));
                break;
            case R.id.iv_edit_add:
                benqiNum++;
                tv_edit_buy_number.setText(benqiNum + "");
                benqi_qianqian.setText("￥ " + (dingYuePhotpPopBean.getCurrProduct().getProdPrice() * benqiNum));
                break;
            case R.id.quannian_subtract:
                if (allYearNum <= 1) {
                    ToastUtil.makeText(activity, "不能再少了");
                    return;
                }
                allYearNum--;
                quannian_number.setText(allYearNum + "");
                quannian_qianqian.setText("￥ " + (dingYuePhotpPopBean.getYearProduct().getProdPrice() * allYearNum));
                break;
            case R.id.quannian_add:
                allYearNum++;
                quannian_number.setText(allYearNum + "");
                quannian_qianqian.setText("￥ " + (dingYuePhotpPopBean.getYearProduct().getProdPrice() * allYearNum));
                break;


            case R.id.benqi_text:
                type = 0;
                ReadSourceID = dingYuePhotpPopBean.getYearProduct().getProdID();
                benqi_text.setTextColor(activity.getResources().getColor(R.color.ffffff));
                benqi_text.setBackgroundResource(R.color.CollectFragment_buttont_Text);

                danqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                danqi_text.setBackgroundResource(R.color.background_gray);

                allyear_text.setTextColor(activity.getResources().getColor(R.color.black));
                allyear_text.setBackgroundResource(R.color.background_gray);
                benpian_text.setTextColor(activity.getResources().getColor(R.color.black));
                benpian_text.setBackgroundResource(R.color.background_gray);
                benpian_layout.setVisibility(View.GONE);
                benqi_layout.setVisibility(View.VISIBLE);
                quannian_layout.setVisibility(View.GONE);
                danqi_layout.setVisibility(View.GONE);
                to_showping.setVisibility(View.VISIBLE);
                lineblow.setVisibility(View.VISIBLE);
                add_showpingcar.setVisibility(View.VISIBLE);
                lineblow.setVisibility(View.VISIBLE);
                break;
            case R.id.danqi_text:
                ReadSourceID = dingYuePhotpPopBean.getYearProduct().getProdID();
                type = 1;
                benqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                benqi_text.setBackgroundResource(R.color.background_gray);

                danqi_text.setTextColor(activity.getResources().getColor(R.color.ffffff));
                danqi_text.setBackgroundResource(R.color.CollectFragment_buttont_Text);

                allyear_text.setTextColor(activity.getResources().getColor(R.color.black));
                allyear_text.setBackgroundResource(R.color.background_gray);
                benpian_text.setTextColor(activity.getResources().getColor(R.color.black));
                benpian_text.setBackgroundResource(R.color.background_gray);
                benpian_layout.setVisibility(View.GONE);
                benqi_layout.setVisibility(View.GONE);
                quannian_layout.setVisibility(View.GONE);
                danqi_layout.setVisibility(View.VISIBLE);
                to_showping.setVisibility(View.GONE);
                lineblow.setVisibility(View.GONE);
                add_showpingcar.setVisibility(View.VISIBLE);
                lineblow.setVisibility(View.GONE);
                break;
            case R.id.allyear_text:
                ReadSourceID = dingYuePhotpPopBean.getCurrProduct().getProdID();

                type = 2;
                benqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                benqi_text.setBackgroundResource(R.color.background_gray);

                danqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                danqi_text.setBackgroundResource(R.color.background_gray);

                allyear_text.setTextColor(activity.getResources().getColor(R.color.ffffff));
                allyear_text.setBackgroundResource(R.color.CollectFragment_buttont_Text);

                benpian_text.setTextColor(activity.getResources().getColor(R.color.black));
                benpian_text.setBackgroundResource(R.color.background_gray);
                benpian_layout.setVisibility(View.GONE);
                benqi_layout.setVisibility(View.GONE);
                danqi_layout.setVisibility(View.GONE);
                quannian_layout.setVisibility(View.VISIBLE);
                to_showping.setVisibility(View.VISIBLE);
                lineblow.setVisibility(View.VISIBLE);
                add_showpingcar.setVisibility(View.VISIBLE);
                lineblow.setVisibility(View.VISIBLE);
                break;
            case R.id.benpian_text:
                ReadSourceID = dingYuePhotpPopBean.getYearProduct().getProdID();
                type = 3;
                benpian_text.setTextColor(activity.getResources().getColor(R.color.ffffff));
                benpian_text.setBackgroundResource(R.color.CollectFragment_buttont_Text);

                benqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                benqi_text.setBackgroundResource(R.color.background_gray);

                danqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                danqi_text.setBackgroundResource(R.color.background_gray);

                allyear_text.setTextColor(activity.getResources().getColor(R.color.black));
                allyear_text.setBackgroundResource(R.color.background_gray);
                benpian_layout.setVisibility(View.VISIBLE);
                benqi_text.setTextColor(activity.getResources().getColor(R.color.black));
                benqi_text.setBackgroundResource(R.color.background_gray);
                danqi_layout.setVisibility(View.GONE);
                benqi_layout.setVisibility(View.GONE);
                quannian_layout.setVisibility(View.GONE);
                to_showping.setVisibility(View.VISIBLE);
                add_showpingcar.setVisibility(View.GONE);
                lineblow.setVisibility(View.GONE);
                break;
            case R.id.add_showpingcar:
                isToCard = true;
                addCard();
                break;
            case R.id.to_showping:
                isToCard = false;
                addCard();
                break;
            case R.id.quit:
                dismiss();
                break;

        }
    }

    private void addCard() {
        String shenJson = "";
        if (type == 0) {
            shenBeanList.clear();
            sendBean sendBean = new sendBean(dingYuePhotpPopBean.getCurrProduct().getProdID(), benqiNum);
            shenBeanList.add(sendBean);
        }
        if (type == 1) {
            shenBeanList.clear();
            for (int i = 0; i < listCheckBox.size(); i++) {
                if (listCheckBox.get(i).isChecked()) {
                    sendBean sendBean = new sendBean(dingYuePhotpPopBean.getBatchProduct().get(i).getProdID(), 1);
                    shenBeanList.add(sendBean);
                }
            }
            if (shenBeanList.size() <= 0) {
                ToastUtil.makeText(activity, "请选择要购买的产品");
                return;
            }
        }
        if (type == 2) {
            shenBeanList.clear();
            sendBean sendBean = new sendBean(dingYuePhotpPopBean.getYearProduct().getProdID(), allYearNum);
            shenBeanList.add(sendBean);
        }
        if (type == 3) {
            shenBeanList.clear();
            sendBean sendBean = new sendBean(dingYuePhotpPopBean.getArticleProduct().getProdID(), 1);
            shenBeanList.add(sendBean);
        }
        shenJson = gson.toJson(shenBeanList);
        execApiList(ApiType.GetCartBatchAdd, shenJson);
    }

    DingYuePhotpPopBean dingYuePhotpPopBean;

    @Override
    public void onResponse(Request req) {
        try {
            if (req.getData() != null) {
                if (req.getData().getUrl().contains("/Product/GetIssueNum")) {
                    JSONObject jsonObject = new JSONObject(gson.toJson(req.getData().getData()));
                    dingYuePhotpPopBean = gson.fromJson(jsonObject.toString(), DingYuePhotpPopBean.class);
                    initView();
                }
                if (req.getData().getUrl().contains("/Cart/BatchAdd")) {
                    if (req.getData().getRet()) {
                        if (isToCard) {
                            ToastUtil.makeText(activity, req.getData().getMsg());
                        } else {
                            JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                            String cardItmeId = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                cardItmeId = cardItmeId + jsonArray.get(i).toString() + ",";
                            }
                            Intent intent = new Intent(activity, ShoppingCartConfirmOrderActivity.class);
                            intent.putExtra("tempStoress", "ture");
                            intent.putExtra("cardItmeId", cardItmeId);
                            intent.putExtra("type", type);
                            activity.startActivity(intent);
                        }
                    }
                    dismiss();
                }
            } else {
                if (req.getErrorMsg().equals("authorization参数不能为空")) {
                    StringUtil.showLoginDialog(activity);
                    return;
                }
                LogUtil.i("Exception", req.toString());
            }
        } catch (Exception e) {
            LogUtil.i("Exception", e.toString());
        }
    }

    private class sendBean {
        private int ProductID;
        private int ProductCount;
        private int Type;

        public sendBean(int ProductID, int ProductCount) {
            this.ProductID = ProductID;
            this.ProductCount = ProductCount;
            if (isToCard) {
                Type = 0;//添加购物车
            } else {
                Type = 1;//直接购买
            }

        }

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int productID) {
            ProductID = productID;
        }

        public int getProductCount() {
            return ProductCount;
        }

        public void setProductCount(int productCount) {
            ProductCount = productCount;
        }

        public int getType() {
            return Type;
        }

        public void setType(int type) {
            Type = type;
        }

        @Override
        public String toString() {
            return "shenBean{" +
                    "ProductID=" + ProductID +
                    ", ProductCount=" + ProductCount +
                    ", Type=" + Type +
                    '}';
        }
    }

    /**
     * 执行网络请求
     *
     * @param api
     * @param postParams
     */
    public void execApi(ApiType api, Map<String, String> postParams) {
        RequestBody body = null;
        try {
            if (postParams == null) {
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                        "");
            } else {
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                        getRequestData(postParams).toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(activity).getSSLSocketFactory(),
                RequestCert.getInstance(activity).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, activity);
    }

    /**
     * 执行网络请求
     *
     * @param api
     */
    public void execApiList(ApiType api, String json) {
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(activity).getSSLSocketFactory(),
                RequestCert.getInstance(activity).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, activity);
    }

    /**
     * 将map转换成请求参数
     *
     * @param params
     * @return
     */
    protected static StringBuffer getRequestData(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey().equals("base64Pics")) {
                    stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
                } else {
                    stringBuffer.append(entry.getKey().toString());
                    stringBuffer.append("=".toString());
                    stringBuffer.append(String.valueOf(entry.getValue()));
                    stringBuffer.append("&");
                }
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("params", stringBuffer.toString());
        return stringBuffer;
    }

    private void init() {
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = view.findViewById(R.id.pop_layout).getTop();
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
                return true;
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
    }
}
