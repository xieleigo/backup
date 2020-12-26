package io.dcloud.H5E4CA812.protocol;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by sjh on 2016/12/6.
 */

public class RequestParams extends HashMap<String, String> {
    private Context mContext;
    public Context getmContext() {

        return mContext;
    }

    public void setmContext(Context mContext) {

        this.mContext = mContext;
    }
    public RequestParams(Context context) {

        if (mContext == null) {
            mContext = context;
        }
        initParams();
    }

    public RequestParams() {
        mContext = App.getApp();
        initParams();
    }
    private void initParams() {
        //TODO put一些必带的值
    }
    /**
     * put a int value
     * @param key
     * @param val
     */
    public void put(String key, int val){
        put(key, String.valueOf(val));
    }
    /**
     * put a double value
     * @param key
     * @param val
     */
    public void put(String key, double val){
        put(key, String.valueOf(val));
    }
}
