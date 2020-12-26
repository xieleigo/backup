package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.view.fragment.AllOrderAllTypeFragment;
import io.dcloud.H5E4CA812.view.fragment.AllOrderCompleteFragment;
import io.dcloud.H5E4CA812.view.fragment.AllOrderUnCompleteFragment;
import com.google.android.material.tabs.TabLayout;

import io.dcloud.H5E4CA812.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 所有订单
 */
public class AllOrderActivity extends BaseActivity {
    @BindView(R.id.alloder_tabLayout)
    TabLayout mTabLayout;
    private Fragment currentFragment = new Fragment();
    AllOrderAllTypeFragment AllTypeFragment;
    AllOrderCompleteFragment CompleteFragment;
    AllOrderUnCompleteFragment UnCompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        ButterKnife.bind(this);
        initTitle();
        init();
    }


    private void init() {
        mTabLayout.addTab(mTabLayout.newTab().setText("全部订单"));
        mTabLayout.addTab(mTabLayout.newTab().setText("未支付"));
        mTabLayout.addTab(mTabLayout.newTab().setText("已完成"));
        mTabLayout.addOnTabSelectedListener(tabSelectedListener);
        AllTypeFragment = new AllOrderAllTypeFragment();
        CompleteFragment = new AllOrderCompleteFragment();
        UnCompleteFragment = new AllOrderUnCompleteFragment();
        switchFragment(AllTypeFragment);
    }

    /**
     * TabLayout 切换事件
     */
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    switchFragment(AllTypeFragment);
                    break;
                case 1:
                    switchFragment(CompleteFragment);
                    break;
                case 2:
                    switchFragment(UnCompleteFragment);
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

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("全部订单");
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

    }

    //动态替换fragment
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.allorder_info_fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }
}
