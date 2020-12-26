package io.dcloud.H5E4CA812.wxapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lkl.http.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.ToastUtil;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
        iwxapi = WXAPIFactory.createWXAPI(this, GlobleApplication.WXappId, false);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    // APP请求微信响应结果回调
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                ToastUtil.makeText(WXEntryActivity.this, "分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtil.makeText(WXEntryActivity.this, "分享被取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtil.makeText(WXEntryActivity.this, "分享被拒绝");
                break;
        }
        finish(); // 注意在：如果不finishing会有一层白色的activity
    }
}
