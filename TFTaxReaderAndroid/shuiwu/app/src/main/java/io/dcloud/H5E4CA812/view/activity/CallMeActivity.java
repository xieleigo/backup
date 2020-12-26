package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallMeActivity extends BaseActivity {
    @BindView(R.id.call_me_webview)
    WebView call_me_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_me);
        ButterKnife.bind(this);
        initTitle();
        call_me_webview.loadUrl("http://210.12.84.109/home/about");
    }

    @Override
    public void onResponsed(Request req) {

    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("联系我们");
    }

    @OnClick(R.id.back_but)
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }


}
