package io.dcloud.H5E4CA812.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.protocol.ApiType;
import okhttp3.OkHttpClient;

public class ImageViewUtil {
    public static void setImage(Context context, String imageUrl, ImageView imageView) {

        Glide.with(context).load("https://" + ApiType.localDefValue + imageUrl).placeholder(R.drawable.nocover).into(imageView);
    }
    public static void setBillingInformationInfoImage(Context context, String imageUrl, ImageView imageView) {

        Glide.with(context).load(imageUrl).placeholder(R.drawable.nocover).into(imageView);


    }
    public static void setLBTImage(Context context, String imageUrl, ImageView imageView) {

        Glide.with(context).load(imageUrl).placeholder(R.drawable.nocover).into(imageView);
    }

    public static void setArticleImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).placeholder(R.drawable.article).into(imageView);
    }

    public static void setPHBImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context).load("https://" + ApiType.localDefValue + imageUrl).placeholder(R.drawable.nocover).into(imageView);

    }
}
