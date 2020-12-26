package io.dcloud.H5E4CA812.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.PaySuccessActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里可以不填写
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, GlobleApplication.WXappId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * 处理结果回调
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (resp.errCode) {
                case 0://支付成功
                    ToastUtil.makeText(this, "支付成功");
                    Log.d(TAG, "onResp: resp.errCode = 0   支付成功");
                    Intent intent = new Intent(this, PaySuccessActivity.class);
                    intent.putExtra("OrderId", GlobleApplication.orderId);
                    LogUtil.e("OrderId", GlobleApplication.orderId);
                    startActivity(intent);
                    break;
                case -1://错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    ToastUtil.makeText(this, "支付错误" + resp.errCode);
                    Log.d(TAG, "onResp: resp.errCode = -1  支付错误");
                    break;
                case -2://用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    Log.d(TAG, "onResp: resp.errCode = -2  用户取消");
                    ToastUtil.makeText(this, "用户取消" + resp.errCode);
                    break;
            }

            finish();//这里需要关闭该页面
        }

    }

}