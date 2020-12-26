package io.dcloud.H5E4CA812.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import io.dcloud.H5E4CA812.R;

public class TimeCount extends CountDownTimer {
    private TextView mSendCode;
    private String toast;

    public TimeCount(long millisInFuture, long countDownInterval, TextView mSendCode, String toast) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        this.mSendCode = mSendCode;
        this.toast = toast;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        mSendCode.setText(toast);
        mSendCode.setEnabled(true);
        mSendCode.setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        mSendCode.setEnabled(false);
//        mSendCode.setBackgroundResource(R.drawable.bg_solid_gray_ten);
        mSendCode.setBackgroundResource(R.color.colorPrimary);
        mSendCode.setText(millisUntilFinished / 1000 + "S");
    }
}
