package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.OrderDetailAdapter;
import io.dcloud.H5E4CA812.view.bean.OrderDetailBeans;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.order_detail_recycler)
    RecyclerView order_detail_recycler;
    @BindView(R.id.order_detail_state)
    TextView order_detail_state;
    @BindView(R.id.order_detail_states)
    TextView order_detail_states;
    @BindView(R.id.order_detail_address_layout_true)
    RelativeLayout order_detail_address_layout_true;
    @BindView(R.id.order_detail_address_layout_false)
    RelativeLayout order_detail_address_layout_false;

    @BindView(R.id.order_detail_num)
    TextView order_detail_num;
    @BindView(R.id.order_detail_creat_time)
    TextView order_detail_creat_time;
    @BindView(R.id.order_detail_pay_time)
    TextView order_detail_pay_time;
    @BindView(R.id.order_detail_name)
    TextView order_detail_name;
    @BindView(R.id.order_detail_phone)
    TextView order_detail_phone;
    @BindView(R.id.order_detail_address)
    TextView order_detail_address;


    List<OrderDetailBeans> newdatas;
    OrderDetailAdapter adapter;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initTitle();
        initView();
    }

    private void initView() {
        orderId = getIntent().getIntExtra("orderid", 0);
        Map map = new HashMap();
        map.put("OrderID", orderId);
        execApi(ApiType.GetOrderFindDetail, map);

        newdatas = new ArrayList<OrderDetailBeans>();
        adapter = new OrderDetailAdapter(newdatas, this);
        order_detail_recycler.setLayoutManager(new LinearLayoutManager(this));
        order_detail_recycler.setAdapter(adapter);
    }

    private void initLayoutView() {
        order_detail_num.setText("订单编号：" + orderDetailBeans.getOrderSerialCode());
        order_detail_creat_time.setText("创建时间：" + orderDetailBeans.getOrderDate());
        if (orderDetailBeans.getOrderPayDate() == null) {
            order_detail_pay_time.setText("付款时间：");
        } else {
            order_detail_pay_time.setText("付款时间：" + orderDetailBeans.getOrderPayDate());
        }
        if (orderDetailBeans.getOrderStatus() == 1) {
            order_detail_state.setText("当前订单状态：" + "待支付");
            order_detail_states.setText("待支付");
        } else if (orderDetailBeans.getOrderStatus() == 3) {
            order_detail_state.setText("当前订单状态：" + "交易成功");
            order_detail_states.setText("交易成功");
        }
        if (orderDetailBeans.getOrderConsignee() != null) {
            order_detail_address_layout_true.setVisibility(View.VISIBLE);
            order_detail_name.setText(orderDetailBeans.getOrderConsignee().getOrderCons());
            order_detail_phone.setText(orderDetailBeans.getOrderConsignee().getOrderConsMobile());
            order_detail_address.setText(orderDetailBeans.getOrderConsignee().getOrderConsDetail());

        } else {
            order_detail_address_layout_true.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (reqType == 1) {
            Map map = new HashMap();
            map.put("OrderID", orderId);
            execApi(ApiType.GetOrderFindDetail, map);
        }
    }

    @OnClick(R.id.back_but)
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("订单详情");
    }

    OrderDetailBeans orderDetailBeans;
    Gson gson = new Gson();
    int reqType = 0;

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            if (reqType == 1) {
                OrderDetailBeans orderDetailBeans = gson.fromJson(gson.toJson(req.getData().getData()), OrderDetailBeans.class);
                if (orderDetailBeans.getOrderStatus() == 3) {
                    Intent intent = new Intent(OrderDetailActivity.this, PaySuccessActivity.class);
                    intent.putExtra("OrderId", orderDetailBeans.getOrderID());
                    startActivity(intent);
                } else {
                    ToastUtil.makeText(OrderDetailActivity.this, "您的订单没有支付成功去全部订单继续支付");
                }
            }
            if (reqType == 0) {
                if (req.getData().getRet()) {
                    orderDetailBeans = gson.fromJson(gson.toJson(req.getData().getData()), OrderDetailBeans.class);
                    List<OrderDetailBeans> list = new ArrayList();
                    list.add(orderDetailBeans);
                    adapter.setmDataList(list);
                    initLayoutView();
                }
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(OrderDetailActivity.this);
                return;
            }
        }
    }
}
