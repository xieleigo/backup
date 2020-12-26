package io.dcloud.H5E4CA812.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.activity.BillingInformation;
import io.dcloud.H5E4CA812.view.adapter.BillingInformationAdapter;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 所有类型开票
 */
public class BillingInfoAllFragment extends BaseFragment {
    @BindView(R.id.recyclerview_billing)
    RecyclerView recyclerview_billing;
    BillingInformationAdapter adapter;
    private Context context;
    List<BillingInfoItmeBean> list;
    int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing_info_all, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        initData();
        return view;
    }

    public BillingInformationAdapter getAdapter() {
        return adapter;
    }

    public BillingInfoAllFragment(List mPseronDataList) {
        this.list = mPseronDataList;
    }


    private void initData() {
        adapter = new BillingInformationAdapter(list, context);
        recyclerview_billing.setLayoutManager(new LinearLayoutManager(context));
        recyclerview_billing.setAdapter(adapter);
        adapter.setOnCollectionListener(new BillingInformationAdapter.OnCollectionListener() {
            @Override
            public void onCollection(int postion) {
                Loading.getInstance(getContext()).show();
                Map map = new HashMap();
                map.put("UserInvoiceID", list.get(postion).getUserInvoiceID());
                execApi(ApiType.GetInvoiceDelete, map);
                pos = postion;
                SwipeLayoutManager.getInstance().closeCurrentLayout();
            }
        });
        recyclerview_billing.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //如果垂直滑动，则需要关闭已经打开的layout
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        SwipeLayoutManager.getInstance().clearCurrentLayout();
        LogUtil.e("onHiddenChanged", "CollectFragmentPeriodicalFragment");
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            if (req.getData().getRet()) {
                list.remove(pos);
                adapter.notifyDataSetChanged();
            } else {
                ToastUtil.makeText(getContext(), req.getData().getMsg());
            }
            Loading.getInstance(getContext()).hide();
        } else {
            if (req.getData() == null) {
                if (req.getErrorMsg().equals("authorization参数不能为空")) {
                    StringUtil.showLoginDialog(getActivity());
                    return;
                }
                LogUtil.i("Exception", req.toString());
            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ToastUtil.makeText(getContext(), req.getData().getMsg());
        Loading.getInstance(getContext()).hide();
    }
}
