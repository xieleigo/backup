package io.dcloud.H5E4CA812.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.adapter.AllOrderAdapter;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.AllOrderBean;
import io.dcloud.H5E4CA812.view.widget.down_load.LoadMoreWrapper;
import io.dcloud.H5E4CA812.view.widget.down_load.OnScrollListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllOrderCompleteFragment extends BaseFragment {
    @BindView(R.id.all_complete_order_recycler)
    RecyclerView all_complete_order_recycler;
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    AllOrderAdapter allOrderAdapter;
    private int pageindex = 1;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int Isnotif = 0;//是否是刷新
    List<AllOrderBean> newdatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_allorder_complete, container, false);
        ButterKnife.bind(this, view);
        initView();
        initDate();
        return view;
    }


    private void initView() {
        newdatas = new ArrayList<AllOrderBean>();
        allOrderAdapter = new AllOrderAdapter(newdatas, getActivity());
        mLoadMoreWrapper = new LoadMoreWrapper(allOrderAdapter);
        all_complete_order_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        all_complete_order_recycler.setAdapter(mLoadMoreWrapper);

        all_complete_order_recycler.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING);
                Isnotif = 1;
                initDate();
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                initDate();
            }
        });
    }

    private void initDate() {
        Loading.getInstance(getActivity()).show();
        Map map = new HashMap();
        map.put("OrderStatus", "1");
        map.put("PageIndex", pageindex);
        map.put("PageSize", 5);
        execApi(ApiType.GetOrderFind, map);
        pageindex++;

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        Gson gson = new Gson();
        if (Isnotif == 0) {
            newdatas.clear();
        }
        if (req.getData() != null) {
            try {
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                if (jsonArray.length() != 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        AllOrderBean allOrderBean = gson.fromJson(jsonArray.get(i).toString(), AllOrderBean.class);
                        newdatas.add(allOrderBean);
                    }
                    allOrderAdapter.setmDataList(newdatas);
                    mLoadMoreWrapper.notifyDataSetChanged();
                } else {
                    if (Isnotif == 1) {
//                        Message msg = new Message();
//                        msg.what = 100;
//                        handler.sendMessage(msg);
                        mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_END);
                    }
                }

            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        }else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(getActivity());
                return;
            }
        }
        Isnotif = 0;
        Loading.getInstance(getActivity()).hide();
        LogUtil.i("req", req.getData().toString());
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(getActivity()).hide();
        LogUtil.i("req", req.getData().toString());
        mSwipeLayout.setRefreshing(false);
    }
}
