package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.EventBusCarrier;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.fragment.BillingInfoAllFragment;
import io.dcloud.H5E4CA812.view.fragment.BillingInfoGSFragment;
import io.dcloud.H5E4CA812.view.fragment.BillingInfoPersonFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看发票
 */
public class BillingInformation extends BaseActivity {
    @BindView(R.id.billing_tabLayout)
    TabLayout mTabLayout; // tabLayout
    @BindView(R.id.add_billing_info)
    Button add_billing_info;


    private Fragment currentFragment = new Fragment();
    BillingInfoAllFragment billingInfoAllFragment;
    BillingInfoPersonFragment billingInfoPersonFragment;
    BillingInfoGSFragment billingInfoGSFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);
        ButterKnife.bind(this);
        initTitle();
        EventBus.getDefault().register(this);  //事件的注册
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        execApi(ApiType.GetInvoiceInfo, null);
    }

    private void init() {
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        mTabLayout.addTab(mTabLayout.newTab().setText("个人"));
        mTabLayout.addTab(mTabLayout.newTab().setText("企业"));
        mTabLayout.addOnTabSelectedListener(tabSelectedListener);
        billingInfoAllFragment = new BillingInfoAllFragment(list);
        billingInfoGSFragment = new BillingInfoGSFragment(gslist);
        billingInfoPersonFragment = new BillingInfoPersonFragment(personlist);
    }


    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("开票信息");
    }

    private int tabType = 0;
    /**
     * TabLayout 切换事件
     */
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    tabType = 0;
                    switchFragment(billingInfoAllFragment);
                    break;
                case 1:
                    tabType = 1;
                    switchFragment(billingInfoPersonFragment);
                    break;
                case 2:
                    tabType = 2;
                    switchFragment(billingInfoGSFragment);
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    //动态替换fragment
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragmet_billing_info_fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    @OnClick({R.id.back_but, R.id.add_billing_info})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.add_billing_info:
                Intent intent = new Intent(this, AddBillingInformationActivity.class);
                intent.putExtra("tabType", tabType);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        LogUtil.i("Request", req.toString());

        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(BillingInformation.this);
                return;
            }
            LogUtil.i("Exception", req.toString());
        }
        if (req.getData().toString() != null) {
            list.clear();
            gslist.clear();
            personlist.clear();
            try {
                Gson gson = new Gson();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    BillingInfoItmeBean itme = gson.fromJson(jsonArray.get(i).toString(), BillingInfoItmeBean.class);
                    list.add(itme);
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUserInvoiceReceiveType() == 1) {//1个人
                        personlist.add(list.get(i));
                    } else {
                        gslist.add(list.get(i));
                    }
                }

            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        }
        if (billingInfoAllFragment == null) {
            billingInfoAllFragment = new BillingInfoAllFragment(list);
        } else {
            if (billingInfoAllFragment.getAdapter() != null) {
                billingInfoAllFragment.getAdapter().setmDataList(list);
            }
        }
        if (billingInfoGSFragment == null) {
            billingInfoGSFragment = new BillingInfoGSFragment(gslist);
        } else {
            if (billingInfoGSFragment.getAdapter() != null) {
                billingInfoGSFragment.getAdapter().setmDataList(gslist);
            }
        }
        if (billingInfoPersonFragment == null) {
            billingInfoPersonFragment = new BillingInfoPersonFragment(personlist);
        } else {
            if (billingInfoPersonFragment.getAdapterPson() != null) {
                billingInfoPersonFragment.getAdapterPson().setmDataList(personlist);
            }
        }

        if (tabType == 0) {
            switchFragment(billingInfoAllFragment);
        }
        if (tabType == 1) {
            switchFragment(billingInfoPersonFragment);
        }
        if (tabType == 2) {
            switchFragment(billingInfoGSFragment);
        }
    }

    @Override
    public void onResponsed(Request req) {
    }

    List<BillingInfoItmeBean> list = new ArrayList();
    List<BillingInfoItmeBean> personlist = new ArrayList();
    List<BillingInfoItmeBean> gslist = new ArrayList();

    // 普通事件的处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(EventBusCarrier carrier) {
        if (carrier.getEventType().equals("1")) {
            execApi(ApiType.GetInvoiceInfo, null);
        }
    }

 /*   // 粘性事件的处理
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleEvent(EventBusCarrier carrier) {
        String content = (String) carrier.getObject();
        show.setText(content);
    }*/

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this); //解除注册
        super.onDestroy();
    }
}
