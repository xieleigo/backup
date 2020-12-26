package io.dcloud.H5E4CA812.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.protocol.RequestCert;
import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;
import io.dcloud.H5E4CA812.util.LogUtil;

import io.dcloud.H5E4CA812.R;

import java.net.URLEncoder;
import java.util.Map;

import butterknife.BindView;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.activity.BillingInformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class BaseFragment extends Fragment implements OnApiDataReceivedCallback {
    @BindView(R.id.back_but)
    public ImageButton back_but;
    @BindView(R.id.title_name)
    public TextView title_name;
    @BindView(R.id.title_edittext)
    public EditText title_edittext;
    @BindView(R.id.title_edittext_layout)
    public LinearLayout title_edittext_layout;
    @BindView(R.id.title_edit)
    public TextView title_edit;
    @BindView(R.id.title_gj_search)
    public TextView title_gj_search;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
     * 返回键是否隐藏
     *
     * @param mboolean
     */
    public void IsEditBut(boolean mboolean) {
        if (mboolean) {
            title_edit.setVisibility(View.VISIBLE);
        } else {
            title_edit.setVisibility(View.GONE);
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
     * 设置Toast
     */
    public void ShowToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 输入框是否隐藏
     *
     * @param name
     */
    public void setTitleName(String name) {
        title_name.setText(name);
    }


    @Override
    public void onResponse(Request req) {

    }

    /**
     * 执行网络请求
     *
     * @param api
     * @param postParams
     */
    public void execApi(ApiType api, Map<String, String> postParams) {
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
        req.setSslSocketFactory(RequestCert.getInstance(getContext()).getSSLSocketFactory(),
                RequestCert.getInstance(getContext()).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, getContext());
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
        req.setSslSocketFactory(RequestCert.getInstance(getContext()).getSSLSocketFactory(),
                RequestCert.getInstance(getContext()).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, getContext());
    }

    /**
     * 执行网络请求
     *
     * @param api
     */
    public void execApione(ApiType api, String obj) {
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                    "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(getContext()).getSSLSocketFactory(),
                RequestCert.getInstance(getContext()).getMyX509TrustManager());
        api.setOneParameter(obj);
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, getContext());
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
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz, Intent intent) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * 当网络请求数据失败时执行此方法
     *
     * @param req
     */
    public void onResponsedError(Request req) {
        if (req.getErrorMsg().equals("")) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(getContext());
                return;
            }
        }
        ShowToast(getContext(), req.getErrorMsg() + "");
        Log.w("req", req.getErrorMsg() + "");
    }
}
