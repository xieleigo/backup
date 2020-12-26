package io.dcloud.H5E4CA812.protocol.callback;

import android.os.Handler;
import android.os.Message;

import io.dcloud.H5E4CA812.protocol.ResponseResult;
import io.dcloud.H5E4CA812.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultCallback implements Callback {
    Class<? extends ResponseResult> result;
    private Handler handler;

    public ResultCallback(Class<? extends ResponseResult> result, Handler handler) {
        this.result = result;
        this.handler = handler;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogUtil.e("请求失败", e.getMessage());
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(-100);
            }
        }).start();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        LogUtil.i("返回数据", response.request().toString());
        if (response.isSuccessful()) {
            final String result = response.body().string();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = result + "&&&" + response.request().url().toString();
                    handler.sendMessage(msg);
                    if (msg.obj != null) {
                        LogUtil.i("返回数据obj", msg.obj.toString());
                    }
                }
            }).start();
        } else {
            LogUtil.e("请求失败", "请求超时");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(-100);
                }
            }).start();
        }

    }
}
