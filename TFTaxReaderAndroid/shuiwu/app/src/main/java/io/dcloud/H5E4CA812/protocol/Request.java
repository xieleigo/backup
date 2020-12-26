package io.dcloud.H5E4CA812.protocol;import android.app.Activity;import android.app.ActivityManager;import android.content.ComponentName;import android.content.Context;import android.content.DialogInterface;import android.os.Handler;import android.os.Message;import android.text.TextUtils;import android.util.Log;import android.view.KeyEvent;import android.view.View;import android.widget.TextView;import android.widget.Toast;import com.google.gson.Gson;import java.io.BufferedInputStream;import java.io.IOException;import java.net.URLDecoder;import java.net.URLEncoder;import java.security.GeneralSecurityException;import java.security.cert.CertificateException;import java.security.cert.X509Certificate;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Arrays;import java.util.Collections;import java.util.Date;import java.util.concurrent.TimeUnit;import javax.net.ssl.HostnameVerifier;import javax.net.ssl.SSLContext;import javax.net.ssl.SSLSession;import javax.net.ssl.SSLSocketFactory;import javax.net.ssl.X509TrustManager;import io.dcloud.H5E4CA812.GlobleApplication;import io.dcloud.H5E4CA812.R;import io.dcloud.H5E4CA812.protocol.bean.FileBean;import io.dcloud.H5E4CA812.protocol.callback.DornResultCallback;import io.dcloud.H5E4CA812.protocol.callback.OnApiDataReceivedCallback;import io.dcloud.H5E4CA812.protocol.callback.ResultCallback;import io.dcloud.H5E4CA812.protocol.interfaces.ProgressListener;import io.dcloud.H5E4CA812.util.LogUtil;import io.dcloud.H5E4CA812.util.SHA1Util;import io.dcloud.H5E4CA812.util.StringUtil;import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;import okhttp3.Call;import okhttp3.CipherSuite;import okhttp3.ConnectionSpec;import okhttp3.MediaType;import okhttp3.OkHttpClient;import okhttp3.RequestBody;import okhttp3.TlsVersion;import okio.Buffer;public class Request {    private String errorMsg;    private boolean success;    private ApiType api;    private RequestBody requestBody;    private ResponseResult data;    private OnApiDataReceivedCallback callback;    private ProgressListener listener;    private String fileDir, fileName;    private FileBean file;    private String putData;    private SSLSocketFactory sslSocketFactory;    private X509TrustManager trustManagers;    private int downId;    private boolean isProgress = false;    private String loadUrl;//下载文件地址    Handler handler = new Handler() {        @Override        public void handleMessage(Message msg) {            super.handleMessage(msg);            switch (msg.what) {                case 100:                    String result = (String) msg.obj;                    jsonToObj(result, api.getClazz());                    break;                case 101:                    Log.i("Request", "下载完成");                    break;                case -1:                    Log.i("Request", "下载失败");                    break;                case -100://                    Toast.makeText(GlobleApplication.applicationContext, "网络请求失败，请稍后再试！", Toast.LENGTH_LONG).show();                    errorMsg = "网络连接失败";                    done();                    break;                case 300://                    GlobleApplication.getInstance().showDeleteDialog();                    break;                default:                    break;            }        }    };    public String getLoadUrl() {        return loadUrl;    }    public void setLoadUrl(String loadUrl) {        this.loadUrl = loadUrl;    }    public String getFileName() {        return fileName;    }    public void setFileName(String fileName) {        this.fileName = fileName;    }    public int getDownId() {        return downId;    }    public void setDownId(int downId) {        this.downId = downId;    }    public ProgressListener getListener() {        return listener;    }    public void setListener(ProgressListener listener) {        this.listener = listener;    }    public String getPutData() {        return putData;    }    public void setPutData(String putData) {        this.putData = putData;    }    public String getFileDir() {        return fileDir;    }    public void setFileDir(String fileDir) {        this.fileDir = fileDir;    }    public String getFilename() {        return fileName;    }    public void setFilename(String fileName) {        this.fileName = fileName;    }    public boolean isProgress() {        return isProgress;    }    public void setProgress(boolean progress) {        isProgress = progress;    }    public FileBean getFile() {        return file;    }    public void setFile(FileBean file) {        this.file = file;    }    public String getErrorMsg() {        return errorMsg;    }    public void setErrorMsg(String errorMsg) {        this.errorMsg = errorMsg;    }    public boolean isSuccess() {        return success;    }    public void setSuccess(boolean success) {        this.success = success;    }    public OnApiDataReceivedCallback getCallback() {        return callback;    }    public void setCallback(OnApiDataReceivedCallback callback) {        this.callback = callback;    }    public ApiType getApi() {        return api;    }    public void setApi(ApiType api) {        this.api = api;    }    public void setSslSocketFactory(SSLSocketFactory mSSLSocketFactory, X509TrustManager mTrustManagers) {        this.sslSocketFactory = mSSLSocketFactory;        this.trustManagers = mTrustManagers;    }    public RequestBody getRequestBody() {        return requestBody;    }    public void setRequestBody(RequestBody requestBody) {        this.requestBody = requestBody;    }    public ResponseResult getData() {        return data;    }    public void setData(ResponseResult data) {        this.data = data;    }    private void postRquest(ApiType api) {        String nonce = StringUtil.genRandomNum(32);//        long timestamp = System.currentTimeMillis();        long timestamp = Long.valueOf(getDateStr(System.currentTimeMillis()));        String Signature = SHA1Util.getSHA("appSecret=" + "F3494C964062400C9B25B3A27B2BE178" + "&nonce=" + nonce + "&timestamp=" + timestamp);        LogUtil.i("Header", "nonce： " + nonce + " --timestamp: " + timestamp + " --Signature: " + Signature);        try {            OkHttpClient mOkHttpClient = new OkHttpClient.Builder()                    .retryOnConnectionFailure(true)                    .readTimeout(10000, TimeUnit.SECONDS)//设置读取超时时间                    .writeTimeout(10000, TimeUnit.SECONDS)//设置写的超时时间                    .connectTimeout(10000, TimeUnit.MILLISECONDS)//设置连接超时时间//                    .sslSocketFactory(sslSocketFactory, trustManagers)//                    .hostnameVerifier(new RequestCert.TrustAllHostnameVerifier())                    .followSslRedirects(true)                    .connectionSpecs(Collections.singletonList(getConnectionSpec()))                    .build();            okhttp3.Request request = new okhttp3.Request.Builder()                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")                    .addHeader("nonce", nonce)                    .addHeader("timestamp", timestamp + "")                    .addHeader("Signature", Signature)                    .addHeader("authorization", GlobleApplication.authorization)                    .url(api.getOpt())                    .post(requestBody)                    .build();            LogUtil.e("Request", requestBody.toString());            Call call = mOkHttpClient.newCall(request);            call.enqueue(new ResultCallback(api.getClazz(), handler));        } catch (Exception e) {            LogUtil.e("Request", e.toString());        }    }    private static ConnectionSpec getConnectionSpec() {        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).cipherSuites(CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA).build();        return spec;    }    private void getRquest(ApiType api) {        try {            OkHttpClient mOkHttpClient = new OkHttpClient.Builder()                    .readTimeout(1000, TimeUnit.SECONDS)//设置读取超时时间                    .writeTimeout(1000, TimeUnit.SECONDS)//设置写的超时时间                    .connectTimeout(1000, TimeUnit.SECONDS)//设置连接超时时间                    .sslSocketFactory(sslSocketFactory, trustManagers)                    .hostnameVerifier(new RequestCert.TrustAllHostnameVerifier())                    .build();            okhttp3.Request request = new okhttp3.Request.Builder()                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")                    .addHeader("Connection", "close")                    .url(api.getOpt() + "?" + getParameters(requestBody))                    .get()                    .build();            Call call = mOkHttpClient.newCall(request);            call.enqueue(new ResultCallback(api.getClazz(), handler));        } catch (Exception e) {            LogUtil.e("Request", e.toString());        }    }    public static final MediaType MEDIA_TYPE_MARKDOWN            = MediaType.parse("text/x-markdown; charset=utf-8");    private void downFile(ApiType api) {        try {            OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()                    .connectTimeout(600 * 1000, TimeUnit.MINUTES)                    .readTimeout(600 * 1000, TimeUnit.MINUTES)                    .writeTimeout(600 * 1000, TimeUnit.MINUTES)                    .sslSocketFactory(sslSocketFactory, trustManagers)                    .hostnameVerifier(new RequestCert.TrustAllHostnameVerifier())                    .build();            okhttp3.Request request;            request = new okhttp3.Request.Builder()                    .url(loadUrl)                    .build();            if (isProgress && listener != null) {                Call call = mOkHttpClient.newCall(request);                call.enqueue(new DornResultCallback(fileDir, fileName, listener, handler));            } else {                Call call = mOkHttpClient.newCall(request);                call.enqueue(new ResultCallback(api.getClazz(), handler));            }        } catch (Exception e) {            LogUtil.e("Request", e.toString());        }    }    private void putFile(ApiType api) {        try {            OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()                    .connectTimeout(1000, TimeUnit.SECONDS)                    .readTimeout(1000, TimeUnit.SECONDS)                    .sslSocketFactory(sslSocketFactory, trustManagers)                    .hostnameVerifier(new RequestCert.TrustAllHostnameVerifier())                    .build();            okhttp3.Request request;            if (isProgress) {                request = new okhttp3.Request.Builder()                        .url(api.getOpt())                        .post(requestBody)                        .build();            } else {                request = new okhttp3.Request.Builder()                        .url(api.getOpt())                        .post(requestBody)                        .build();            }            Call call = mOkHttpClient.newCall(request);            call.enqueue(new ResultCallback(api.getClazz(), handler));        } catch (Exception e) {            LogUtil.e("Request", e.toString());        }    }    public void executeNetworkApi(final OnApiDataReceivedCallback Callback, Context mContext) {        this.callback = Callback;        if (api == null) {            if (!api.getRequestMethod().equals(ApiType.RequestMethod.FILE)) {                if (TextUtils.isEmpty(putData)) {                    errorMsg = "参数为空";                }            }            //防止在处理返回数据时发生异常崩溃            try {//                Callback.onResponse(this);            } catch (Exception e) {                e.printStackTrace();            }            return;        } else {            if (!api.getRequestMethod().equals("FILE")) {                LogUtil.i("Request api params", api.getOpt() + "?" + getParameters(requestBody));            }            switch (new NetWorkUtil().isNetworkUsable(mContext)) {                case 1:                    Toast.makeText(mContext, "请连接网络", Toast.LENGTH_LONG).show();                    this.done();                    break;                case 2:                    Toast.makeText(mContext, "当前网络不可用", Toast.LENGTH_LONG).show();                    this.done();                    break;                case 100:                    extRquest(api);                    break;                default:                    break;            }        }    }    private void extRquest(ApiType api) {        LogUtil.i("请求方式", api.getRequestMethod() + "");        if (api.getRequestMethod().getRequestMethodName().equals("POST")) {            postRquest(api);        } else if (api.getRequestMethod().getRequestMethodName().equals("GET")) {            getRquest(api);        } else if (api.getRequestMethod().getRequestMethodName().equals("FILE")) {            putFile(api);        } else if (api.getRequestMethod().getRequestMethodName().equals("DOWNFILE")) {            downFile(api);        }    }    private void jsonToObj(String json, Class<? extends ResponseResult> obj) {        try {            Gson gson = new Gson();            String jxgson = json.split("&&&")[0];            String urlgson = json.split("&&&")[1];            ResponseResult result = gson.fromJson(jxgson, ResponseResult.class);            result.setUrl(urlgson);            if (result.getRet()) {                if (!StringUtil.isEmpty(result.getAccess_token())) {                    GlobleApplication.authorization = result.getAccess_token();                    LogUtil.i("authorization", GlobleApplication.authorization);                }                success = true;                this.data = result;            } else {                success = false;                this.errorMsg = result.getMsg();            }            this.done();        } catch (Exception e) {            Log.e("Exception", e.toString());            handler.sendEmptyMessage(-100);        }    }    /**     * 完成请求     */    public void done() {        callback.onResponse(this);    }    public String getParameters(RequestBody requestBody) {        try {            final Buffer buffer = new Buffer();            requestBody.writeTo(buffer);            //            BufferedInputStream bufferedInputStream = new BufferedInputStream(buffer.inputStream());            final StringBuffer resultBuffer = new StringBuffer();            byte[] inputBytes = new byte[1024];            while (true) {                int count = bufferedInputStream.read(inputBytes);                if (count <= 0) {                    break;                }                resultBuffer.append(new String(Arrays.copyOf(inputBytes, count)));            }            final String parameter = URLDecoder.decode(resultBuffer.toString());            bufferedInputStream.close();            return parameter;        } catch (IOException e) {            e.printStackTrace();        } catch (Exception e) {            e.printStackTrace();        }        return null;    }    //Day:时间戳减少10秒    public static String getDateStr(long day) {        LogUtil.i("Header", "当前时间戳:" + day);        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        Date nowDate = null;        try {            nowDate = df.parse(timeStamp2Date(day));        } catch (ParseException e) {            e.printStackTrace();        }        //如果需要向后计算日期 -改为+        Date newDate2 = new Date(nowDate.getTime() - (long) 10 * 1000);        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        String dateOk = "";        try {            dateOk = String.valueOf(simpleDateFormat.parse(simpleDateFormat.format(newDate2)).getTime());        } catch (ParseException e) {            e.printStackTrace();        }        return dateOk;    }    public static String timeStamp2Date(long time) {        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        return sdf.format(new Date(time));    }}