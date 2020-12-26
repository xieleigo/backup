package io.dcloud.H5E4CA812.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import io.dcloud.H5E4CA812.MainActivity;
import io.dcloud.H5E4CA812.R;

import butterknife.ButterKnife;

public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.AppTheme_Launcher);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        countDown();
    }

    private void countDown() {

        CountDownTimer timer = new CountDownTimer(100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }.start();


    }
}
