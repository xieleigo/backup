package io.dcloud.H5E4CA812.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.ShareDataLocal;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.activity.AllOrderActivity;
import io.dcloud.H5E4CA812.view.activity.BillingInformation;
import io.dcloud.H5E4CA812.view.activity.BindActivationCodes;
import io.dcloud.H5E4CA812.view.activity.CallMeActivity;
import io.dcloud.H5E4CA812.view.activity.EssentialInformationActivity;
import io.dcloud.H5E4CA812.view.activity.ReceivingAddressActivity;
import io.dcloud.H5E4CA812.view.activity.MyAccountActivity;
import io.dcloud.H5E4CA812.view.activity.UpdatePasswordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.bind_activation_code_layout)
    RelativeLayout bind_activation_code_layout;
    @BindView(R.id.person_name)
    TextView person_name;
    @BindView(R.id.fragment_my)
    ScrollView fragment_my;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        initTitle();
        person_name.setText(ShareDataLocal.getInstance(getContext()).getStringPreference("user", ""));
        return view;
    }

    private void initTitle() {
        IsBackBut(false);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("会员中心");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (IsHint) {
            initIsLogingView();
        }
    }

    boolean IsHint = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        IsHint = isVisibleToUser;
        if (isVisibleToUser) {
            initIsLogingView();
        }
    }

    private void initIsLogingView() {
        if (StringUtil.isEmpty(GlobleApplication.getInstance().authorization)) {
            fragment_my.setVisibility(View.GONE);
            StringUtil.showLoginDialog(getActivity());
        } else {
            fragment_my.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.bind_activation_code_layout, R.id.call_us_layout, R.id.update_pass_layout, R.id.pserson_info_layout,
            R.id.basic_information_layout, R.id.ticket_info_layout, R.id.address_layout, R.id.all_order_layout})
    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bind_activation_code_layout:
                intent = new Intent(getContext(), BindActivationCodes.class);
                startActivity(intent);
                break;
            case R.id.call_us_layout:
                intent = new Intent(getContext(), CallMeActivity.class);
                startActivity(intent);
                break;
            case R.id.update_pass_layout:
                intent = new Intent(getContext(), UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.pserson_info_layout:
                intent = new Intent(getContext(), MyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.basic_information_layout:
                intent = new Intent(getContext(), EssentialInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.ticket_info_layout:
                intent = new Intent(getContext(), BillingInformation.class);
                startActivity(intent);
                break;
            case R.id.address_layout:
                intent = new Intent(getContext(), ReceivingAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.all_order_layout:
                intent = new Intent(getContext(), AllOrderActivity.class);
                startActivity(intent);
                break;
        }
    }

}
