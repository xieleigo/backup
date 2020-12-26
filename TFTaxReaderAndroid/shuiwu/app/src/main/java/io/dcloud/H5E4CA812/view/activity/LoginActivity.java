package io.dcloud.H5E4CA812.view.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.MainActivity;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.ShareDataLocal;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.LoginBean;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.name_text)
    TextView name_text;
    @BindView(R.id.login_register_text)
    TextView login_register_text;
    @BindView(R.id.login_back_white)
    RelativeLayout login_back_white;
    @BindView(R.id.user_layout)
    LinearLayout user_layout;
    @BindView(R.id.user_image)
    ImageView user_image;
    @BindView(R.id.user_text)
    EditText user_text;
    @BindView(R.id.pass_layout)
    LinearLayout pass_layout;
    @BindView(R.id.pass_image)
    ImageView pass_image;
    @BindView(R.id.pass_text)
    EditText pass_text;
    @BindView(R.id.login_but)
    Button login_but;
    @BindView(R.id.qqtopline_layout)
    LinearLayout qqtopline_layout;
    @BindView(R.id.login_viewline1)
    View login_viewline1;
    @BindView(R.id.login_viewline2)
    View login_viewline2;
    @BindView(R.id.login_viewline3)
    View login_viewline3;

    @BindView(R.id.register_but)
    Button register_but;
    @BindView(R.id.qq_but)
    ImageButton qq_but;
    @BindView(R.id.weibo_but)
    ImageButton weibo_but;
    @BindView(R.id.weixin_but)
    ImageButton weixin_but;
    @BindView(R.id.is_loging)
    ImageView is_loging;
    @BindView(R.id.is_loging_lilayout)
    LinearLayout is_loging_lilayout;
    @BindView(R.id.login_forget_password)
    TextView login_forget_password;
    @BindView(R.id.back_home)
    ImageButton back_home;

    Boolean IsLogin = false;//是否记住登录
    private String user;
    private String pwd;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (String) bundle.get("UserName");
            pwd = (String) bundle.get("setPassAgain");
        } else {
            user = ShareDataLocal.getInstance(this).getStringPreference("user", "");
            pwd = ShareDataLocal.getInstance(this).getStringPreference("pwd", "");
        }
        if (!StringUtil.isEmpty(user)) {
            user_text.setText(user);
        }
        if (!StringUtil.isEmpty(pwd)) {
            pass_text.setText(pwd);
        }
        if (ShareDataLocal.getInstance(this).getBoolean("IsLogin")) {
            IsLogin = true;
            is_loging.setBackgroundResource(R.drawable.check_mark01);
        }
        if (IsLogin) {
            login();
        }
    }

    @Override
    public void onResponsed(Request req) {
        LogUtil.i("Request", req.toString());
        if (req.getData() != null && req.getData().getRet()) {
            Gson gson = new Gson();
            LoginBean loginBean = gson.fromJson(gson.toJson(req.getData().getData()), LoginBean.class);
            ShareDataLocal.getInstance(this).putBoolean("IsLogin", IsLogin);
            ShareDataLocal.getInstance(this).putString("user", user);
            ShareDataLocal.getInstance(this).putString("pwd", pwd);
            ShareDataLocal.getInstance(this).putInt("UserID", loginBean.getUserID());
            Loading.getInstance(this).hide();
            if (GlobleApplication.getInstance().IsQuit || IsLogin) {
                startActivity(MainActivity.class);
            }
            finish();
        }
        LogUtil.i("Request", req.toString());
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(this).hide();
    }

    @OnClick({R.id.login_but, R.id.register_but, R.id.is_loging_lilayout, R.id.login_forget_password, R.id.back_home})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.login_but:
                user = user_text.getText().toString().trim();
                pwd = pass_text.getText().toString().trim();
                if (StringUtil.isEmpty(user)) {
                    ShowToast(this, "请输入用户名");
                    return;
                }
                if (StringUtil.isEmpty(pwd)) {
                    ShowToast(this, "请输入密码");
                    return;
                }
                login();

//                startActivity(MainActivity.class);
                break;
            case R.id.register_but:
                startActivity(RegisterActvity.class);

                break;
            case R.id.is_loging_lilayout:
                if (IsLogin) {
                    IsLogin = false;
                    is_loging.setBackgroundResource(R.drawable.check_mark02);
                } else {
                    IsLogin = true;
                    is_loging.setBackgroundResource(R.drawable.check_mark01);
                }
                break;
            case R.id.login_forget_password:
                user = user_text.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("user", user);
                Intent intent = new Intent(this, ForgetPasswordActivity.class);
                intent.putExtras(bundle);
                startActivityForResultAddIntent(intent, 2);
                break;
            case R.id.back_home:
                if (GlobleApplication.getInstance().IsQuit) {
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    onBackPressed();
                }
                break;

        }
    }

    //接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(Map map) {
        pwd = map.get("password").toString();
        user = map.get("user").toString();
        LoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user_text.setText(user);
                pass_text.setText(pwd);
            }
        });
        LogUtil.i("LoginActivity", "user_text：" + user_text.getText().toString().trim() + "  pass_text：" + pass_text.getText().toString().trim());
    }


    private void login() {
        Loading.getInstance(LoginActivity.this).show();

        Map<String, String> params = new HashMap<>();
        params.put("UserName", user.trim());
        String key = StringUtil.encryptString(pwd, GlobleApplication.passWordKEY).trim();
        if (key.contains("+")) {
            key = key.replace("+", "%2b");
        }
        params.put("UserPWD", key);
        params.put("MacID", StringUtil.getMacFromHardware(LoginActivity.this));

        LogUtil.i("LoginActivity", key);
        execApi(ApiType.Getlogin, params);
    }
}
