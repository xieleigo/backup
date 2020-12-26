package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivationCodes extends BaseActivity {
    @BindView(R.id.bind_code)
    EditText bind_code;
    @BindView(R.id.bind_code_pwd)
    EditText bind_code_pwd;
    @BindView(R.id.bind_code_cancel)
    Button bind_code_cancel;
    @BindView(R.id.bind_code_determine)
    Button bind_code_determine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_activation_code);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("绑定阅读码");
    }

    @OnClick({R.id.back_but, R.id.bind_code_cancel, R.id.bind_code_determine})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                onBackPressed();
                break;
            case R.id.bind_code_cancel:
                onBackPressed();
                break;
            case R.id.bind_code_determine:
                String code = bind_code.getText().toString().trim();
                String paw = bind_code_pwd.getText().toString().trim();
                if (StringUtil.isEmpty(code)) {
                    ShowToast(this, "请输入激活码");
                    return;
                }
                if (StringUtil.isEmpty(paw)) {
                    ShowToast(this, "请输入密码");
                    return;
                }
                Map map = new HashMap();
                map.put("ActivationCodeSN", code);
                map.put("ActivationCodePWD", paw);
                execApi(ApiType.GetBindCode, map);

                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(BindActivationCodes.this);
                return;
            }
        }
        if (req.getData() != null && req.getData().getRet()) {
            startActivity(BindActivationCodeSuccess.class);
        } else {
            ToastUtil.makeText(this, "激活失败");
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ToastUtil.makeText(this, req.getErrorMsg());
    }
}
