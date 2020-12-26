package io.dcloud.H5E4CA812.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.widget.PhonePhotoPopWin;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.util.TimeCount;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.update_pass_codebut)
    TextView update_pass_codebut;
    @BindView(R.id.set_new_pass)
    Button set_new_pass;
    @BindView(R.id.register_phone)
    EditText register_phone;
    @BindView(R.id.forget_paw_phone_tepy)
    TextView forget_paw_phone_tepy;
    @BindView(R.id.update_pass_code)
    EditText update_pass_code;

    String registerPhone;
    String updateCode;
    String SMSCode = "";
    String registerCodeString;
    Bundle bundle;

    @Override
    public void onResponsed(Request req) {
        SMSCode = req.getData().getData().toString();
//        if (!StringUtil.isEmpty(SMSCode) && SMSCode.length() == 4) {
//            update_pass_code.setText(SMSCode);
//        }
        Loading.getInstance(this).hide();
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ToastUtil.makeText(ForgetPasswordActivity.this,req.getErrorMsg());
        Loading.getInstance(this).hide();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        initTitle();
        ForgetPasswordActivityPostion = 36;
        bundle = getIntent().getExtras();
        register_phone.setText(bundle.getString("user"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("忘记密码");
    }

    @OnClick({R.id.back_but, R.id.update_pass_codebut, R.id.set_new_pass, R.id.forget_paw_phone_tepy})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.update_pass_codebut:
                registerPhone = register_phone.getText().toString().trim();
                if (StringUtil.isEmpty(registerPhone)) {
                    ShowToast(this, "请输入手机号");
                    return;
                }
                if (!StringUtil.isPhone(registerPhone)) {
                    ShowToast(this, "请输入正确手机号");
                    return;
                }

                TimeCount timeCount = new TimeCount(60 * 1000, 1000, update_pass_codebut, "重新发送");
                timeCount.start();
                Loading.getInstance(this).show();
                Map<String, String> paramss = new HashMap<>();
                registerCodeString = forget_paw_phone_tepy.getText().toString().trim();
                if (registerCodeString.contains("+")) {
                    registerCodeString = registerCodeString.substring(1, registerCodeString.length());
                }
                paramss.put("NKValidateCodeReceive", registerPhone);
                paramss.put("NKValidateCodeType", "3");
                paramss.put("UserMobileAreaCode", registerCodeString);
                paramss.put("UserRegIP", StringUtil.getIpAddressString());
                execApi(ApiType.GetSMSGetCode, paramss);

                break;
            case R.id.set_new_pass:
                Bundle bundle = new Bundle();
                updateCode = update_pass_code.getText().toString().trim();
                registerPhone = register_phone.getText().toString().trim();
                if (StringUtil.isEmpty(registerPhone)) {
                    ShowToast(this, "请输入手机号");
                    return;
                }
                if (!StringUtil.isPhone(registerPhone)) {
                    ShowToast(this, "请输入正确手机号");
                    return;
                }
                if (StringUtil.isEmpty(updateCode)) {
                    ShowToast(this, "请输验证码");
                    return;
                }
                if (!SMSCode.equals(updateCode)) {
                    ShowToast(this, "验证码错误");
                    return;
                }

                bundle.putString("updateCode", updateCode);
                bundle.putString("registerPhone", registerPhone);
                bundle.putString("UserMobileAreaCode", registerCodeString);
                startActivity(SetPasswordActivity.class, bundle);
                break;
            case R.id.forget_paw_phone_tepy:
                showPhonePopFormBottom(view);
                break;
        }
    }

    public static int ForgetPasswordActivityPostion;

    public void showPhonePopFormBottom(View view) {
        PhonePhotoPopWin takePhotoPopWin = new PhonePhotoPopWin(this, forget_paw_phone_tepy, ForgetPasswordActivityPostion, 0);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.set_new_pass), Gravity.CENTER, 0, 0);
    }


}
