package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;

import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;

import butterknife.ButterKnife;

public class InquiryInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_info);
        ButterKnife.bind(this);
    }

    @Override
    public void onResponsed(Request req) {

    }
}
