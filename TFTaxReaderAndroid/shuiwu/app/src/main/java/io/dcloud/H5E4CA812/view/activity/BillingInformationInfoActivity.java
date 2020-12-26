package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单详情  废弃
 */
public class BillingInformationInfoActivity extends BaseActivity {
    @BindView(R.id.bille_info_info_odercode)
    TextView bille_info_info_odercode;
    @BindView(R.id.bille_info_info_oder_time)
    TextView bille_info_info_oder_time;
    @BindView(R.id.bille_info_info_oder_num)
    TextView bille_info_info_oder_num;
    @BindView(R.id.bille_info_info_taitou)
    TextView bille_info_info_taitou;
    @BindView(R.id.bille_info_info_neirong)
    TextView bille_info_info_neirong;
    @BindView(R.id.bille_info_info_shuihao)
    TextView bille_info_info_shuihao;
    @BindView(R.id.bille_info_info_jine)
    TextView bille_info_info_jine;
    @BindView(R.id.bille_info_info_view_invoice)
    Button bille_info_info_view_invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_information_info);
        ButterKnife.bind(this);
        initTitle();
        initData();
    }

    @Override
    public void onResponsed(Request req) {

    }

    private void initData() {
        BillingInfoItmeBean billingInfoItmeBean = getIntent().getParcelableExtra("BillingInfoItmeBean");

        bille_info_info_odercode.setText("");
        bille_info_info_oder_time.setText("");
        bille_info_info_oder_num.setText("");
        bille_info_info_taitou.setText("");
        bille_info_info_neirong.setText("");
        bille_info_info_shuihao.setText("");
        bille_info_info_jine.setText("");
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("发票详情");
    }

    @OnClick({R.id.back_but, R.id.bille_info_info_view_invoice})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.bille_info_info_view_invoice:
                startActivity(BillingInformationInfoImageActivity.class);
                break;
        }
    }
}
