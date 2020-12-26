package io.dcloud.H5E4CA812.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.protocol.RequestCert;
import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.R;

import java.net.URLEncoder;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public abstract class BaseActivity extends AppCompatActivity implements OnApiDataReceivedCallback {
    public Context mContext;

    /**
     * 设置屏幕横竖屏切换
     *
     * @param screenRoate true  竖屏     false  横屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private void setScreenRoate(Boolean screenRoate) {
        if (screenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @BindView(R.id.back_but)
    public ImageButton back_but;
    @BindView(R.id.title_name)
    public TextView title_name;
    @BindView(R.id.title_edittext)
    public EditText title_edittext;
    @BindView(R.id.title_edittext_layout)
    public LinearLayout title_edittext_layout;
    @BindView(R.id.title_finish)
    public TextView title_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenRoate(true);
        mContext = this;
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz, Intent intent) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }

    /**
     * 跳转页面并通过intent传值
     *
     * @param
     * @param requestCode 请求码
     */
    public void startActivityForResultAddIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }


    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz         所跳转的Activity类
     * @param bundle      跳转所携带的信息
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 设置Toast
     */
    public void ShowToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }


    /**
     * 执行网络请求
     *
     * @param api
     * @param postParams
     */
    public  void execApi(ApiType api, Map<String, String> postParams) {
        RequestBody body = null;
        try {
            if (postParams == null) {
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                        "");
            } else {
                body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                        getRequestData(postParams).toString().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(getApplicationContext()).getSSLSocketFactory(),
                RequestCert.getInstance(getApplicationContext()).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, mContext);
    }

    /**
     * 执行网络请求
     *
     * @param api
     */
    public void execApiList(ApiType api, String json) {
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                    json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(getApplicationContext()).getSSLSocketFactory(),
                RequestCert.getInstance(getApplicationContext()).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, this);
    }

    /**
     * 将map转换成请求参数
     *
     * @param params
     * @return
     */
    protected static StringBuffer getRequestData(Map<String, String> params) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey().equals("base64Pics")) {
                    stringBuffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
                } else {
                    stringBuffer.append(entry.getKey().toString());
                    stringBuffer.append("=".toString());
                    stringBuffer.append(String.valueOf(entry.getValue()));
                    stringBuffer.append("&");
                }
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("params", stringBuffer.toString());
        return stringBuffer;
    }

    /**
     * 网络请求返回值
     *
     * @param req
     */
    @Override
    public void onResponse(Request req) {
        if (req.isSuccess()) {
            onResponsed(req);
        } else {
            onResponsedError(req);
        }
    }

    /**
     * 网络返回数据回调方法 此方法只处理成功的请求, 不需要弹出错误信息和取消对话框显示,如果需要对错误的请求做单独处理,请重写
     * {@link #onResponsed}方法
     *
     * @param req
     */
    public abstract void onResponsed(Request req);

    /**
     * 当网络请求数据失败时执行此方法
     *
     * @param req
     */
    public void onResponsedError(Request req) {
        if (req.getErrorMsg().equals("authorization参数不能为空")) {
            return;
        }
        if (req.getErrorMsg().equals("需要购买")) {
            return;
        }
        ShowToast(mContext, req.getErrorMsg() + "");
        Log.w("req", req.getErrorMsg() + "");
    }

    /**
     * 返回键是否隐藏
     *
     * @param mboolean
     */
    public void IsBackBut(boolean mboolean) {
        if (mboolean) {
            back_but.setVisibility(View.VISIBLE);
        } else {
            back_but.setVisibility(View.GONE);
        }
    }

    /**
     * 名字是否隐藏
     *
     * @param mboolean
     */
    public void IsTitleName(boolean mboolean) {
        if (mboolean) {
            title_name.setVisibility(View.VISIBLE);
        } else {
            title_name.setVisibility(View.GONE);
        }
    }


    /**
     * 输入框是否隐藏
     *
     * @param mboolean
     */
    public void IsTitleEdittext(boolean mboolean) {
        if (mboolean) {
            title_edittext_layout.setVisibility(View.VISIBLE);
        } else {
            title_edittext_layout.setVisibility(View.GONE);
        }
    }

    /**
     * 完成是否隐藏
     *
     * @param mboolean
     */
    public void IsTitFleinish(boolean mboolean) {
        if (mboolean) {
            title_finish.setVisibility(View.VISIBLE);
        } else {
            title_finish.setVisibility(View.GONE);
        }
    }

    /**
     * 输入框是否隐藏
     *
     * @param name
     */
    public void setTitleName(String name) {
        title_name.setText(name);
    }
}
