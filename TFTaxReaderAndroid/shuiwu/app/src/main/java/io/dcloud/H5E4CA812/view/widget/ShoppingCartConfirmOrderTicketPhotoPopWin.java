package io.dcloud.H5E4CA812.view.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.protocol.RequestCert;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.BillingInformation;
import io.dcloud.H5E4CA812.view.adapter.ShoppingTicketPopAdapter;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ShoppingCartConfirmOrderTicketPhotoPopWin extends PopupWindow implements OnApiDataReceivedCallback {

    @BindView(R.id.shopping_ticket_pop_image_open)
    ImageView shopping_ticket_pop_image_open;
    @BindView(R.id.shopping_ticket_pop_image_cols)
    ImageView shopping_ticket_pop_image_cols;
    @BindView(R.id.shopping_ticket_pop_type_pseron)
    Button shopping_ticket_pop_type_pseron;
    @BindView(R.id.shopping_ticket_pop_type_qy)
    Button shopping_ticket_pop_type_qy;
    @BindView(R.id.shopping_ticket_pop_quit)
    TextView shopping_ticket_pop_quit;
    @BindView(R.id.recyclerview_ticke_person)
    RecyclerView recyclerview_ticke_person;
    @BindView(R.id.recyclerview_ticke_gs)
    RecyclerView recyclerview_ticke_gs;
    @BindView(R.id.recyclerview_ticke_scroll)
    ScrollView recyclerview_ticke_scroll;
    @BindView(R.id.ticke_person_layout)
    RelativeLayout ticke_person_layout;
    @BindView(R.id.ticke_gs_layout)
    RelativeLayout ticke_gs_layout;


    private Activity activity;
    private View view;
    private int isTicket;
    private int isPerson;
    ShoppingTicketPopAdapter personadapter;
    ShoppingTicketPopAdapter gsadapter;

    public ShoppingCartConfirmOrderTicketPhotoPopWin(Activity activity, int isTicket, int isPerson) {
        this.activity = activity;
        this.isTicket = isTicket;
        this.isPerson = isPerson;
        this.view = LayoutInflater.from(activity).inflate(R.layout.shopping_ticket_pop, null);
        ButterKnife.bind(this, view);
        init();
        initView();

    }


    List<BillingInfoItmeBean> personlist = new ArrayList();
    List<BillingInfoItmeBean> gslist = new ArrayList();

    private void initView() {
        execApi(ApiType.GetInvoiceInfo);
        personadapter = new ShoppingTicketPopAdapter(this, personlist, activity, true);
        recyclerview_ticke_person.setLayoutManager(new LinearLayoutManager(activity));
        recyclerview_ticke_person.setAdapter(personadapter);

        gsadapter = new ShoppingTicketPopAdapter(this, personlist, activity, false);
        recyclerview_ticke_gs.setLayoutManager(new LinearLayoutManager(activity));
        recyclerview_ticke_gs.setAdapter(gsadapter);
    }

    String strtype = "";
    String UserInvoiceID = "";

    @OnClick({R.id.shopping_ticket_pop_image_open, R.id.shopping_ticket_pop_image_cols,
            R.id.shopping_ticket_pop_type_pseron,
            R.id.shopping_ticket_pop_quit,
            R.id.shopping_ticket_pop_type_qy,
            R.id.ticke_person_layout,
            R.id.ticke_gs_layout,
            R.id.add_billing_enterprise_person_determine})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.shopping_ticket_pop_image_open:
                isTicket = 0;
                shopping_ticket_pop_image_open.setBackground(activity.getDrawable(R.drawable.button_red_mark));
                shopping_ticket_pop_image_cols.setBackground(activity.getDrawable(R.drawable.button_white));
                break;
            case R.id.shopping_ticket_pop_image_cols:
                isTicket = 1;
                shopping_ticket_pop_image_cols.setBackground(activity.getDrawable(R.drawable.button_red_mark));
                shopping_ticket_pop_image_open.setBackground(activity.getDrawable(R.drawable.button_white));
                OnChangeListener.onChange(false, strtype, null);
                dismiss();
                break;
            case R.id.shopping_ticket_pop_type_pseron:
                isPerson = 0;
                shopping_ticket_pop_type_pseron.setBackground(activity.getDrawable(R.drawable.add_billing_red));
                shopping_ticket_pop_type_pseron.setTextColor(activity.getResources().getColor(R.color.addBillingInfotext));
                shopping_ticket_pop_type_qy.setBackground(activity.getDrawable(R.drawable.add_billing_hui));
                shopping_ticket_pop_type_qy.setTextColor(activity.getResources().getColor(R.color.black));
                recyclerview_ticke_gs.setVisibility(View.GONE);
                recyclerview_ticke_person.setVisibility(View.VISIBLE);
                break;
            case R.id.shopping_ticket_pop_type_qy:
                isPerson = 1;
                shopping_ticket_pop_type_qy.setBackground(activity.getDrawable(R.drawable.add_billing_red));
                shopping_ticket_pop_type_pseron.setBackground(activity.getDrawable(R.drawable.add_billing_hui));
                shopping_ticket_pop_type_qy.setTextColor(activity.getResources().getColor(R.color.addBillingInfotext));
                shopping_ticket_pop_type_pseron.setTextColor(activity.getResources().getColor(R.color.black));
                recyclerview_ticke_person.setVisibility(View.GONE);
                recyclerview_ticke_gs.setVisibility(View.VISIBLE);
                break;
            case R.id.shopping_ticket_pop_quit:
                dismiss();
                OnChangeListener.onChange(false, strtype, null);
                break;
            case R.id.ticke_person_layout:
                activity.startActivity(new Intent(activity, BillingInformation.class));
                break;
            case R.id.ticke_gs_layout:
                activity.startActivity(new Intent(activity, BillingInformation.class));
                break;
            case R.id.add_billing_enterprise_person_determine:
                if (isPerson == 0) {
                    for (int i = 0; i < personadapter.getmDataList().size(); i++) {
                        if (personadapter.getmDataList().get(i).isSelect()) {
                            UserInvoiceID = personadapter.getmDataList().get(i).getUserInvoiceID() + "";
                        }
                    }
                    strtype = shopping_ticket_pop_type_pseron.getText().toString();
                } else {
                    for (int i = 0; i < gsadapter.getmDataList().size(); i++) {
                        if (gsadapter.getmDataList().get(i).isSelect()) {
                            UserInvoiceID = gsadapter.getmDataList().get(i).getUserInvoiceID() + "";
                        }
                    }
                    strtype = shopping_ticket_pop_type_qy.getText().toString();
                }
                if (StringUtil.isEmpty(UserInvoiceID)) {
                    ToastUtil.makeText(activity, "请选择发票");
                }
                if (OnChangeListener != null) {
                    if (StringUtil.isEmpty(strtype)) {
                        OnChangeListener.onChange(false, strtype, null);
                    } else {
                        OnChangeListener.onChange(true, strtype, UserInvoiceID);
                    }
                }
                dismiss();
                break;
        }

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

    @Override
    public void onResponse(Request req) {
        if (req.getData() != null) {
            if (req.getData().toString() != null) {
                gslist.clear();
                personlist.clear();
                try {
                    Gson gson = new Gson();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BillingInfoItmeBean itme = gson.fromJson(jsonArray.get(i).toString(), BillingInfoItmeBean.class);
                        if (itme.getUserInvoiceReceiveType() == 1) {//1个人
                            personlist.add(itme);
                        } else {
                            gslist.add(itme);
                        }
                    }
                    personadapter.setmDataList(personlist);
                    gsadapter.setmDataList(gslist);
                } catch (Exception e) {
                    LogUtil.i("Exception", e.toString());
                }
            }
        } else {
            if (req.getData() == null) {
                if (req.getErrorMsg().equals("authorization参数不能为空")) {
                    StringUtil.showLoginDialog(activity);
                    return;
                }
                LogUtil.i("Exception", req.toString());
            }
        }

        if (isPerson == 0 && personlist.size() < 1) {
            ticke_person_layout.setVisibility(View.VISIBLE);
            ticke_gs_layout.setVisibility(View.GONE);
        }
        if (isPerson == 1 && gslist.size() < 1) {
            ticke_person_layout.setVisibility(View.GONE);
            ticke_gs_layout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 执行网络请求
     *
     * @param api
     */
    public void execApi(ApiType api) {
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), "");
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

    //是否开发票
    public interface OnChangeListener {
        void onChange(Boolean isOK, String Type, String UserInvoiceID);
    }

    public void setOnChangeCountListener(OnChangeListener listener) {
        OnChangeListener = listener;
    }

    private OnChangeListener OnChangeListener;

}
