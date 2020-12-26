package io.dcloud.H5E4CA812.protocol.callback;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import io.dcloud.H5E4CA812.protocol.interfaces.ProgressListener;
import io.dcloud.H5E4CA812.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DornResultCallback implements Callback {
    private String fileDir, filename;
    private ProgressListener listener;
    private Handler handler;
    private int dowdId = 0;

    public DornResultCallback(String fileDir, String filename, ProgressListener listener, Handler handler, int dowdId) {
        this.fileDir = fileDir;
        this.filename = filename;
        this.listener = listener;
        this.handler = handler;
        this.dowdId = dowdId;
    }

    public DornResultCallback(String fileDir, String filename, ProgressListener listener, Handler handler) {
        this.fileDir = fileDir;
        this.filename = filename;
        this.listener = listener;
        this.handler = handler;
    }

    public DornResultCallback(String fileDir, String filename, ProgressListener listener) {
        this.fileDir = fileDir;
        this.filename = filename;
        this.listener = listener;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogUtil.e("请求失败", e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            Log.v("下载文件", "the request was successfully received, understood, and accepted.");
            InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;//本次读取的字节数
            long currSize = 0;//当前已经读取的字节数
            //总大小
            long totalSize = Integer.valueOf(response.header("Content-Length", "-1"));
            FileOutputStream fos = null;
            try {
                is = response.body().byteStream(); //获取返回的Stream
                File dir = new File(fileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(fileDir, filename);

                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    currSize += len;
                    //文件总长度从外部获得；
                    //因为是单文件下载，所以id永远为0，表示不起作用
                    listener.onProgress(currSize, totalSize, totalSize == currSize);
                }
                fos.flush();
                Message message = new Message();
                message.what = 101;
                handler.sendMessage(message);
                //Log.e(TAG, "file has finished downloading.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.w("下载失败", "OkHttp response is not successful. Code is: " + response.code());
            Message message = new Message();
            message.what = -1;
            handler.sendMessage(message);
        }
    }
}
