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

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.view.adapter.BillingInformationPersonAdapter;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 开票信息个人
 */
public class BillingInfoPersonFragment extends BaseFragment {
    @BindView(R.id.recyclerview_billing_person)
    RecyclerView recyclerview_billing_person;
    BillingInformationPersonAdapter adapterPson;
    private List<BillingInfoItmeBean> mPseronDataList;
    private Context context;

    public BillingInfoPersonFragment(List mPseronDataList) {
        this.mPseronDataList = mPseronDataList;
    }

    public BillingInformationPersonAdapter getAdapterPson() {
        return adapterPson;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing_info_person, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        initData();
        return view;
    }

    private void initData() {

        adapterPson = new BillingInformationPersonAdapter(mPseronDataList, context);
        recyclerview_billing_person.setLayoutManager(new LinearLayoutManager(context));
        recyclerview_billing_person.setAdapter(adapterPson);
        recyclerview_billing_person.setOnScrollListener(new RecyclerView.OnScrollListener() {
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

}
