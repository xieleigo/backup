package io.dcloud.H5E4CA812.view.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.util.PwdCheckUtil;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.CodeUtils;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.TimeCount;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.widget.AgreementPhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.PhonePhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.addressPhotoPopWin;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActvity extends BaseActivity {
    @BindView(R.id.register_codeimage)
    ImageView register_codeimage;
    @BindView(R.id.register_codebut)
    TextView register_codebut;
    @BindView(R.id.register_drss_layout)
    LinearLayout register_drss_layout;
    @BindView(R.id.register_drss)
    TextView register_drss;
    @BindView(R.id.register_drss_code)
    TextView register_drss_code;
    @BindView(R.id.register_but)
    Button register_but;
    @BindView(R.id.register_phone)
    EditText register_phone;
    //    @BindView(R.id.register_image_code)
//    EditText register_image_code;
    @BindView(R.id.is_agreement)
    ImageView is_agreement;
    @BindView(R.id.agreement_text)
    TextView agreement_text;
    Boolean iSAgreement = false;
    @BindView(R.id.register_agin_pass)
    EditText register_agin_pass;
    @BindView(R.id.register_pass)
    EditText register_pass;
    @BindView(R.id.register_drss_arrow)
    ImageView register_drss_arrow;
    @BindView(R.id.register_code)
    EditText register_code;
    @BindView(R.id.register_phone_tepy)
    TextView register_phone_tepy;


    String SMSCode = "";

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            LogUtil.i("onResponsed", req.getData().toString());
            if (req.getData().getRet()) {
                ShowToast(mContext, req.getData().getMsg());
                if (req.getData().getMsg().equals("注册成功")) {
                    Map map = new HashMap();
                    map.put("user", registerCodeString);
                    map.put("password", registerAgin_pass);
                    EventBus.getDefault().post(map);
                    finish();
                }
            }
            SMSCode = req.getData().getData().toString();
//            if (!StringUtil.isEmpty(SMSCode) && SMSCode.length() == 4) {
//                register_code.setText(SMSCode);
//            }
            Loading.getInstance(this).hide();
        }
    }


    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        if (req.getErrorMsg().equals("手机号已被注册")) {
            timeCount.cancel();
            timeCount.onFinish();
        }
        Loading.getInstance(this).hide();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_register);
        ButterKnife.bind(this);
        initTitle();
        getCode();
        RegisterActvityPostion = 36;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("手机注册");
    }

    String registerCodeString;
    String AreaFullName;
    String AreaTreePath;
    String UserMobileAreaCode;
    TimeCount timeCount;
    String registerAgin_pass;

    @OnClick({R.id.agreement_text, R.id.back_but, R.id.is_agreement, R.id.register_codebut,
            R.id.register_drss_layout, R.id.register_codeimage, R.id.register_but, R.id.register_drss_arrow,
            R.id.register_phone_tepy})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.register_codebut:
                registerCodeString = register_phone.getText().toString().trim();
                if (StringUtil.isEmpty(registerCodeString)) {

                    ShowToast(this, "请输入手机号");
                    return;
                }
                if (!StringUtil.isPhone(registerCodeString)) {
                    ShowToast(this, "请输入正确手机号");
                    return;
                }

//                String register_image_codes = register_image_code.getText().toString().trim();
//                if (StringUtil.isEmpty(register_image_codes)) {
//                    ShowToast(this, "请输入图片验证码");
//                    return;
//                }
//                if (!codeUtils.getCode().equalsIgnoreCase(register_image_codes)) {
//                    ShowToast(this, "请输入正确图片验证码");
//                    return;
//                }
                Loading.getInstance(this).show();
                UserMobileAreaCode = register_phone_tepy.getText().toString();
                Map<String, String> paramsss = new HashMap<>();
                paramsss.put("NKValidateCodeReceive", registerCodeString);
                paramsss.put("NKValidateCodeType", "1");
                if (UserMobileAreaCode.contains("+")) {
                    UserMobileAreaCode = UserMobileAreaCode.substring(1, UserMobileAreaCode.length());
                }
                paramsss.put("UserMobileAreaCode", UserMobileAreaCode);
                paramsss.put("UserRegIP", StringUtil.getIpAddressString());
                execApi(ApiType.GetSMSGetCode, paramsss);

                timeCount = new TimeCount(60 * 1000, 1000, register_codebut, "获取验证码");
                timeCount.start();
                break;
            case R.id.register_drss_layout:
                showaddressPopFormBottom(view, register_drss, register_drss_code);
                break;
            case R.id.register_codeimage:
                getCode();
                break;
            case R.id.register_but:
                String registerPass = register_pass.getText().toString().trim();
                registerAgin_pass = register_agin_pass.getText().toString().trim();

                if (!SMSCode.equals(register_code.getText().toString().trim())) {
                    ShowToast(this, "验证码错误");
                    return;
                }
                if (StringUtil.isEmpty(registerPass)) {
                    ShowToast(this, "请输密码");
                    return;
                }
                if (!PwdCheckUtil.isContainAll(registerPass)) {
                    ShowToast(this, "输入密码不符合规则");
                }
                if (StringUtil.isEmpty(registerAgin_pass)) {
                    ShowToast(this, "请输确认密码");
                    return;
                }
                if (!registerAgin_pass.equals(registerPass)) {
                    ShowToast(this, "两次密码不一致");
                    return;
                }
                SMSCode = register_code.getText().toString().trim();
                if (StringUtil.isEmpty(SMSCode)) {
                    ShowToast(this, "请输入验证码");
                    return;
                }
                AreaFullName = register_drss.getText().toString().trim();
                AreaTreePath = register_drss_code.getText().toString().trim();
                if (StringUtil.isEmpty(AreaFullName)) {
                    ShowToast(this, "请选择地区");
                    return;
                }
                registerCodeString = register_phone.getText().toString().trim();
                if (!iSAgreement) {
                    ShowToast(this, "请选择注册协议");
                    return;
                }
                UserMobileAreaCode = register_phone_tepy.getText().toString();
                Map<String, String> paramss = new HashMap<>();
                paramss.put("UserName", registerCodeString);
                paramss.put("UserMobile", registerCodeString);
                paramss.put("UserRegIP", StringUtil.getIpAddressString());
                paramss.put("AreaTreePath", AreaTreePath);
                paramss.put("AreaFullName", AreaFullName);
                paramss.put("UserPWD", StringUtil.encryptString(registerPass, GlobleApplication.passWordKEY));
//                paramss.put("UserPWD",
                paramss.put("UserMobileAreaCode", UserMobileAreaCode);
                paramss.put("UseFrom", "10");
                paramss.put("SMSCode", SMSCode);
                LogUtil.i("GetRegister", "UserName:" + registerCodeString
                        + " UserMobile:" + registerCodeString
                        + " UserRegIP:" + StringUtil.getIpAddressString()
                        + " AreaTreePath:" + AreaTreePath
                        + " AreaFullName:" + AreaFullName
                        + " UserPWD:" + StringUtil.encryptString(registerPass, GlobleApplication.passWordKEY)
                        + " UserMobileAreaCode:" + UserMobileAreaCode
                        + " UseFrom:" + "10"
                        + " SMSCode:" + SMSCode);
                execApi(ApiType.GetRegister, paramss);
                break;
            case R.id.is_agreement:
                if (iSAgreement) {
                    iSAgreement = false;
                    is_agreement.setBackgroundResource(R.drawable.is1);
                    agreement_text.setTextColor(getResources().getColor(R.color.black));
                } else {
                    iSAgreement = true;
                    is_agreement.setBackgroundResource(R.drawable.is2);
                    agreement_text.setTextColor(getResources().getColor(R.color.Button_login));
                }
                break;
            case R.id.agreement_text:
                showAgreementPopFormBottom(view);

                break;
            case R.id.register_drss_arrow:
                showaddressPopFormBottom(view, register_drss, register_drss_code);


                break;

            case R.id.register_phone_tepy:
                showPhonePopFormBottom(view);


                break;

        }
    }

    //获取验证码
    CodeUtils codeUtils;

    private void getCode() {
        codeUtils = CodeUtils.getInstance();

        Bitmap bitmap = codeUtils.createBitmap();

        register_codeimage.setImageBitmap(bitmap);
    }


    public void showaddressPopFormBottom(View view, TextView textView, TextView register_drss_code) {
        addressPhotoPopWin takePhotoPopWin = new addressPhotoPopWin(this, this, textView, register_drss_code);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.register_but), Gravity.CENTER, 0, 0);
    }

    public void showAgreementPopFormBottom(View view) {
        AgreementPhotoPopWin takePhotoPopWin = new AgreementPhotoPopWin(this, null);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.register_but), Gravity.CENTER, 0, 0);
    }

    public static int RegisterActvityPostion;

    public void showPhonePopFormBottom(View view) {
        PhonePhotoPopWin takePhotoPopWin = new PhonePhotoPopWin(this, register_phone_tepy, RegisterActvityPostion, 1);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.register_but), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
