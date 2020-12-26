package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivationCodeSuccess extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_activation_success_code);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName(" ");
        IsTitFleinish(true);
    }

    @OnClick({R.id.back_but, R.id.title_finish})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                onBackPressed();
                break;
            case R.id.title_finish:
                finish();
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

}
