package io.dcloud.H5E4CA812.protocol;

import android.content.Context;

import io.dcloud.H5E4CA812.R;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * @创建者 bing
 * @创建时间 2019/6/14 14:27
 * @描述 https://blog.csdn.net/soslinken/article/details/68066955
 */
public class RequestCert {
    public static Context mContext;
    public static RequestCert mRequestCert;

    public static RequestCert getInstance(Context context) {
        if (null == mRequestCert) {
            mRequestCert = new RequestCert(context);
        }
        return mRequestCert;
    }

    public RequestCert(Context context) {
        mContext = context;
    }

    private static SSLSocketFactory mSSLSocketFactory = null;
    private static MyX509TrustManager mMyX509TrustManager = null;

    private static final String KEY_STORE_PASSWORD = "Satag0316";//证书密码（应该是客户端证书密码）

    public SSLSocketFactory getSSLSocketFactory() {
        if (null == mSSLSocketFactory) {
            InputStream client_input = mContext.getResources().openRawResource(R.raw.ctax);//客户端证书
            try {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray());

                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());

                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);

                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagerFactory.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

                mSSLSocketFactory = sslContext.getSocketFactory();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    client_input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mSSLSocketFactory;
    }

    public MyX509TrustManager getMyX509TrustManager() {
        if (null == mMyX509TrustManager) {
            mMyX509TrustManager = new MyX509TrustManager(mContext);
        }
        return mMyX509TrustManager;
    }


    private static class MyX509TrustManager implements X509TrustManager {
        private Context mContext;

        public MyX509TrustManager(Context context) {
            this.mContext = context;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (chain == null) {
                throw new CertificateException("checkServerTrusted: X509Certificate array is null");
            }
            if (chain.length < 1) {
                throw new CertificateException("checkServerTrusted: X509Certificate is empty");
            }
            if (!(null != authType && authType.equals("ECDHE_RSA"))) {
                throw new CertificateException("checkServerTrusted: AuthType is not ECDHE_RSA");
            }

            //检查所有证书
            try {
                TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
                factory.init((KeyStore) null);
                for (TrustManager trustManager : factory.getTrustManagers()) {
                    ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            //获取本地证书中的信息
            String clientEncoded = "";
            String clientSubject = "";
            String clientIssUser = "";
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                InputStream inputStream = mContext.getAssets().open("baidu.cer");
                X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
                clientEncoded = new BigInteger(1, clientCertificate.getPublicKey().getEncoded()).toString(16);
                clientSubject = clientCertificate.getSubjectDN().getName();
                clientIssUser = clientCertificate.getIssuerDN().getName();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //获取网络中的证书信息
            X509Certificate certificate = chain[0];
            PublicKey publicKey = certificate.getPublicKey();
            String serverEncoded = new BigInteger(1, publicKey.getEncoded()).toString(16);

            if (!clientEncoded.equals(serverEncoded)) {
                throw new CertificateException("server's PublicKey is not equals to client's PublicKey");
            }
            String subject = certificate.getSubjectDN().getName();
            if (!clientSubject.equals(subject)) {
                throw new CertificateException("server's subject is not equals to client's subject");
            }
            String issuser = certificate.getIssuerDN().getName();
            if (!clientIssUser.equals(issuser)) {
                throw new CertificateException("server's issuser is not equals to client's issuser");
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
