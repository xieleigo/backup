package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.PwdCheckUtil;
import io.dcloud.H5E4CA812.util.StringUtil;

import io.dcloud.H5E4CA812.R;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {
    Bundle bundle;
    @BindView(R.id.set_pass)
    EditText set_pass;
    @BindView(R.id.set_pass_again)
    EditText set_pass_again;
    String userName;
    String password;
    String UserMobileAreaCode;

    @Override
    public void onResponsed(Request req) {
        if (req.getData() != null) {
            ShowToast(this, req.getData().getMsg());
            if (req.getData().getRet()) {
                if (req.getData().getMsg().equals("密码找回成功")) {
                    Map map = new HashMap();
                    map.put("user", userName);
                    map.put("password", password);
                    EventBus.getDefault().post(map);
                    startActivity(LoginActivity.class);
                }
            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ShowToast(this, req.getErrorMsg());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        bundle = getIntent().getExtras();
        userName = bundle.getString("registerPhone");
        UserMobileAreaCode = bundle.getString("UserMobileAreaCode");
        ButterKnife.bind(this);
        initTitle();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("设置密码");
    }

    @OnClick({R.id.back_but, R.id.set_pass_but})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.set_pass_but:
                StringUtil.hideShowKeyboard(this);
                password = set_pass.getText().toString().trim();
                String setPassAgain = set_pass_again.getText().toString().trim();
                if (StringUtil.isEmpty(password)) {
                    ShowToast(this, "请输密码");
                    return;
                }
                if (!PwdCheckUtil.isContainAll(password)) {
                    ShowToast(this, "输入密码不符合规则");
                    return;
                }
                if (StringUtil.isEmpty(setPassAgain)) {
                    ShowToast(this, "请输确认密码");
                    return;
                }
                if (!setPassAgain.equals(password)) {
                    ShowToast(this, "两次密码不一致");
                    return;
                }

                Map<String, String> paramss = new HashMap<>();
                paramss.put("UserName", bundle.getString("registerPhone"));
                paramss.put("VeriCode", bundle.getString("updateCode"));
                String key = StringUtil.encryptString(setPassAgain, GlobleApplication.passWordKEY).trim();
                paramss.put("NewPass", key);
                LogUtil.i("LoginActivity", "修改后：" + key);
                paramss.put("UserMobileAreaCode", UserMobileAreaCode);
                paramss.put("UserRegIP", StringUtil.getIpAddressString());
                execApi(ApiType.GetFindPass, paramss);

                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                bundle.putString("setPassAgain", setPassAgain);
                intent.putExtras(bundle);
                startActivity(LoginActivity.class, bundle);

                break;
        }

    }

}
