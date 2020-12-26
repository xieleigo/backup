package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.ShareDataLocal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends BaseActivity {


    @BindView(R.id.me_account_quit)
    TextView me_account_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_account);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("我的账号");
    }

    @OnClick({R.id.back_but, R.id.me_account_quit})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.me_account_quit:
                execApi(ApiType.GetloginOut, null);
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {
        if (req.getData().getRet()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            ShareDataLocal.getInstance(this).putBoolean("IsLogin", false);
            GlobleApplication.getInstance().IsQuit = true;
            GlobleApplication.getInstance().authorization = "";
            startActivity(intent);
        }
    }
}
