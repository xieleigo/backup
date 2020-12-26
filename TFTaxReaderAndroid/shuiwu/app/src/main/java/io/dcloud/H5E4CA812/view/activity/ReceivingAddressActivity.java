package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.ReceivingAddressAdapter;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址
 */

public class ReceivingAddressActivity extends BaseActivity {
    @BindView(R.id.recyclerview_homepagefragment_newperiodical_more)
    RecyclerView mRecyclerView;
    private List<ReceivingAddressBean> mDataList;
    ReceivingAddressAdapter commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_address);
        ButterKnife.bind(this);
        initTitle();
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        execApi(ApiType.GetAddressInfo, null);
    }

    private void initListView() {
        mDataList = new ArrayList<>();
        commonAdapter = new ReceivingAddressAdapter(mDataList, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(commonAdapter);
        commonAdapter.setOnItemClickListener(new OnRecyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (getIntent().getIntExtra("IsShoping", 0) == 100) {
                    Intent intent = new Intent();
                    intent.putExtra("ReceivingAddressBean", mDataList.get(position));
                    setResult(100, intent);
                    finish();
                }
            }
        });
        commonAdapter.setOnSetingListener(new ReceivingAddressAdapter.OnSetingListener() {
            @Override
            public void onSeting() {
                execApi(ApiType.GetAddressInfo, null);
            }
        });
    }

    @OnClick({R.id.back_but, R.id.add_address_info})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.add_address_info:
                Intent intents = new Intent(mContext, ReceivingAddressInfoActivity.class);
                startActivity(intents);
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);

        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(ReceivingAddressActivity.this);
                return;
            }
        }
        LogUtil.i("Request", req.toString());
        mDataList.clear();
        if (req.getData().getRet()) {
            try {
                Gson gson = new Gson();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    ReceivingAddressBean itme = gson.fromJson(jsonArray.get(i).toString(), ReceivingAddressBean.class);
                    mDataList.add(itme);
                }
                commonAdapter.setmDataList(mDataList);
            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            ToastUtil.makeText(this, req.getData().getMsg());
        }

    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        LogUtil.i("Request", req.toString());
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("收货地址");
    }

}
