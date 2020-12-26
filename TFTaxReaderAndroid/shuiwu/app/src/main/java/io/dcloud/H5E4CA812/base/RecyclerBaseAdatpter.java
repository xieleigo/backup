package io.dcloud.H5E4CA812.base;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.protocol.RequestCert;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;
import io.dcloud.H5E4CA812.util.loading.Loading;

import java.net.URLEncoder;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RecyclerBaseAdatpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnApiDataReceivedCallback {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 执行网络请求
     *
     * @param api
     * @param postParams
     */
    public void execApi(ApiType api, Map<String, String> postParams, Context context) {
        Loading.getInstance(context).show();
        RequestBody body = null;
        try {
            body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                    getRequestData(postParams).toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        api.setHost(ApiType.localDefValue);
        Request req = new Request();
        req.setSslSocketFactory(RequestCert.getInstance(context).getSSLSocketFactory(),
                RequestCert.getInstance(context).getMyX509TrustManager());
        req.setApi(api);
        req.setRequestBody(body);
        req.executeNetworkApi(this, context);
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
     * 当网络请求数据失败时执行此方法
     *
     * @param req
     */
    public void onResponsedError(Request req) {
        Log.w("req", req.getErrorMsg() + "");
    }


    @Override
    public void onResponse(Request req) {

    }
}
