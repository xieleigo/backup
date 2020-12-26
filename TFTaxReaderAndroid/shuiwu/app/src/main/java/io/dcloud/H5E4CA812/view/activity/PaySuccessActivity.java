package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;

public class PaySuccessActivity extends BaseActivity {
    String OrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
        initTitle();
        OrderId = getIntent().getStringExtra("OrderId");
    }

    private void initTitle() {
        IsBackBut(false);
        IsTitleEdittext(false);
        IsTitleName(true);
        IsTitFleinish(true);
        setTitleName("");
    }

    @OnClick({R.id.back_but, R.id.title_edittext_layout, R.id.title_finish, R.id.pay_success_but})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.title_finish:
                this.finish();
                if (ShoppingCartConfirmOrderActivity.mActivity != null) {
                    ShoppingCartConfirmOrderActivity.mActivity.finish();
                }
                break;
            case R.id.pay_success_but:
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra("orderid", Integer.valueOf(OrderId));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }
}

