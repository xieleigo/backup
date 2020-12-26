package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.adapter.AllOrderCopyAdapter;
import io.dcloud.H5E4CA812.view.bean.JHMBean;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 激活码列表
 */
public class AllOrderCopyJHMActivity extends BaseActivity {
    @BindView(R.id.all_order_copy_recycler)
    RecyclerView all_order_copy_recycler;
    AllOrderCopyAdapter allOrderCopyAdapter;
    List<JHMBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allotder_copy_jhm);
        ButterKnife.bind(this);
        initTitle();
        init();
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("激活码");
    }

    private void init() {
        String OrderDetailID = getIntent().getStringExtra("OrderDetailID");
        Map map = new HashMap();
        map.put("OrderDetailID", OrderDetailID);
        execApi(ApiType.GetOrderGetActivityCode, map);
        list = new ArrayList<JHMBean>();
        allOrderCopyAdapter = new AllOrderCopyAdapter(list, this);
        all_order_copy_recycler.setLayoutManager(new LinearLayoutManager(this));
        all_order_copy_recycler.setAdapter(allOrderCopyAdapter);
    }

    @OnClick(R.id.back_but)
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }


    @Override
    public void onResponsed(Request req) {
        if (req.getData() != null) {
            try {
                Gson gson = new Gson();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JHMBean allOrderBean = gson.fromJson(jsonArray.get(i).toString(), JHMBean.class);
                        list.add(allOrderBean);
                    }
                    allOrderCopyAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(AllOrderCopyJHMActivity.this);
            }
        }

    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        LogUtil.i("onResponsedError", req.toString());
    }
}
