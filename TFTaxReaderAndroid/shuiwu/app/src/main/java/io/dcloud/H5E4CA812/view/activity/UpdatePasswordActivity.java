package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.PwdCheckUtil;
import io.dcloud.H5E4CA812.util.ShareDataLocal;
import io.dcloud.H5E4CA812.util.StringUtil;

import io.dcloud.H5E4CA812.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.util.ToastUtil;

public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.update_old_pass)
    EditText update_old_pass;
    @BindView(R.id.update_new_pass)
    EditText update_new_pass;
    @BindView(R.id.update_new_pass_again)
    EditText update_new_pass_again;
    @BindView(R.id.update_pass_but)
    Button update_pass_but;
    String password;
    Bundle bundle;
    String setPassAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("修改密码");
    }

    @OnClick({R.id.back_but, R.id.update_pass_but})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.update_pass_but:
                StringUtil.hideShowKeyboard(UpdatePasswordActivity.this);
                password = update_new_pass.getText().toString().trim();
                setPassAgain = update_new_pass.getText().toString().trim();
                String oldpass = update_old_pass.getText().toString().trim();
                if (StringUtil.isEmpty(oldpass)) {
                    ShowToast(this, "请输密码");
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    ShowToast(this, "请输新密码");
                    return;
                }
                if (!PwdCheckUtil.isContainAll(password)) {
                    ShowToast(this, "输入新密码不符合规则");
                }
                if (StringUtil.isEmpty(setPassAgain)) {
                    ShowToast(this, "请输确认新密码");
                    return;
                }
                if (!setPassAgain.equals(password)) {
                    ShowToast(this, "两次新密码不一致");
                    return;
                }

                Map<String, String> paramss = new HashMap<>();
                String oldkey = StringUtil.encryptString(oldpass, GlobleApplication.passWordKEY);
                String setPassAgainkey = StringUtil.encryptString(setPassAgain, GlobleApplication.passWordKEY);
                if (oldkey.contains("+")) {
                    oldkey = oldkey.replace("+", "%2b");
                }
                if (setPassAgainkey.contains("+")) {
                    setPassAgainkey = setPassAgainkey.replace("+", "%2b");
                }
                paramss.put("newPass", setPassAgainkey);
                paramss.put("oldPass", oldkey);
                execApi(ApiType.GetUppass, paramss);

                bundle = new Bundle();
                bundle.putString("setPassAgain", setPassAgain);
                bundle.putString("UserName", ShareDataLocal.getInstance(this).getStringPreference("user", ""));
//                startActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {
        if (req.getData() != null && req.getData().getRet()) {
            ShareDataLocal.getInstance(this).putString("pwd", setPassAgain);
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtras(bundle);
//            startActivity(intent);
            ToastUtil.makeText(this, req.getData().getMsg());
            return;
        } else {
            ToastUtil.makeText(this, req.getData().getMsg());
        }
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(UpdatePasswordActivity.this);
            }
        }
        LogUtil.i("onResponsed", req.getData().toString());
    }

    @Override
    public void onResponsedError(Request req) {
        LogUtil.i("onResponsed", req.getData().toString());
        ToastUtil.makeText(this, req.getErrorMsg());
    }
}
