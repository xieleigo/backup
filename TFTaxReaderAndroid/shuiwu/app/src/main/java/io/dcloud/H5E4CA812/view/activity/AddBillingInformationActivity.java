package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加发票
 */
public class AddBillingInformationActivity extends BaseActivity {
    @BindView(R.id.add_billing_info_person_but)
    Button add_billing_info_person_but;
    @BindView(R.id.add_billing_info_enterprise_but)
    Button add_billing_info_enterprise_but;
    @BindView(R.id.add_billing_enterprise_info_layout)
    ScrollView add_billing_enterprise_info_layout;
    @BindView(R.id.add_billing_pserson_info_layout)
    LinearLayout add_billing_pserson_info_layout;
    @BindView(R.id.add_billing_enterprise_person_reset)
    Button add_billing_enterprise_person_reset;
    @BindView(R.id.add_billing_enterprise_person_determine)
    Button add_billing_enterprise_person_determine;
    @BindView(R.id.add_billing_person_reset)
    Button add_billing_person_reset;
    @BindView(R.id.add_billing_person_determine)
    Button add_billing_person_determine;
    @BindView(R.id.add_billing_person_name)
    EditText add_billing_person_name;
    @BindView(R.id.add_billing_person_phone)
    EditText add_billing_person_phone;
    @BindView(R.id.add_billing_person_emil)
    EditText add_billing_person_emil;

    @BindView(R.id.add_billing_enterprise_edit_name)
    EditText add_billing_enterprise_edit_name;
    @BindView(R.id.add_billing_enterprise_edit_code)
    EditText add_billing_enterprise_edit_code;
    @BindView(R.id.add_billing_enterprise_edit_address)
    EditText add_billing_enterprise_edit_address;
    @BindView(R.id.add_billing_enterprise_edit_phone)
    EditText add_billing_enterprise_edit_phone;
    @BindView(R.id.add_billing_enterprise_edit_bank)
    EditText add_billing_enterprise_edit_bank;
    @BindView(R.id.add_billing_enterprise_edit_bankcode)
    EditText add_billing_enterprise_edit_bankcode;
    @BindView(R.id.add_billing_enterprise_edit_person_phone)
    EditText add_billing_enterprise_edit_person_phone;
    @BindView(R.id.add_billing_enterprise_edit_person_emil)
    EditText add_billing_enterprise_edit_person_emil;

    private boolean isEdit = false;
    BillingInfoItmeBean billingInfoItmeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_billing_information);
        ButterKnife.bind(this);
        initTitle();
        initData();
    }

    Bundle bundle;
    private int tabType;
    boolean isOrderTicke = false;

    private void initData() {
        bundle = getIntent().getExtras();
        isOrderTicke = bundle.getBoolean("isOrderTicke");
        tabType = getIntent().getIntExtra("tabType", 0);
        if (bundle != null) {
            billingInfoItmeBean = (BillingInfoItmeBean) bundle.getSerializable("BillingInfoItmeBean");
            if (billingInfoItmeBean != null) {
                isEdit = true;
                if (billingInfoItmeBean.getUserInvoiceReceiveType() == 1) {
                    initPerson(true);

                    add_billing_person_name.setText(billingInfoItmeBean.getUserInvoiceTitle());
                    add_billing_person_phone.setText(billingInfoItmeBean.getUserInvoicePhone());
                    if (billingInfoItmeBean.getUserInvoiceEmail() != null) {
                        add_billing_person_emil.setText(billingInfoItmeBean.getUserInvoiceEmail().toString());
                    }
                    setTitleName("编辑开票信息");
                } else {
                    initPerson(false);
                    add_billing_enterprise_edit_name.setText(billingInfoItmeBean.getUserInvoiceTitle());
                    add_billing_enterprise_edit_code.setText(billingInfoItmeBean.getUserInvoiceTaxpayerNo());
                    if (billingInfoItmeBean.getUserInvoiceorAddress() != null) {
                        add_billing_enterprise_edit_address.setText(billingInfoItmeBean.getUserInvoiceorAddress().toString());
                    }
                    if (billingInfoItmeBean.getUserInvoiceBuyerTel() != null) {
                        add_billing_enterprise_edit_phone.setText(billingInfoItmeBean.getUserInvoiceBuyerTel().toString());
                    }
                    if (billingInfoItmeBean.getUserInvoiceBuyerBankName() != null) {
                        add_billing_enterprise_edit_bank.setText(billingInfoItmeBean.getUserInvoiceBuyerBankName().toString());
                    }
                    if (billingInfoItmeBean.getUserInvoiceBankAcount() != null) {
                        add_billing_enterprise_edit_bankcode.setText(billingInfoItmeBean.getUserInvoiceBankAcount().toString());

                    }
                    add_billing_enterprise_edit_person_phone.setText(billingInfoItmeBean.getUserInvoicePhone());
                    if (billingInfoItmeBean.getUserInvoiceEmail() != null) {
                        add_billing_enterprise_edit_person_emil.setText(billingInfoItmeBean.getUserInvoiceEmail().toString());

                    }
                }
            }
        }
        if (tabType == 0) {
            initPerson(true);
        } else if (tabType == 2) {
            initPerson(false);
        } else if (tabType == 1) {
            initPerson(true);
        }
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("添加开票信息");
    }

    @OnClick({R.id.back_but, R.id.add_billing_info_person_but, R.id.add_billing_info_enterprise_but,
            R.id.add_billing_enterprise_person_reset, R.id.add_billing_enterprise_person_determine,
            R.id.add_billing_person_reset, R.id.add_billing_person_determine})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.add_billing_info_person_but:
                if (isEdit) {
                    ToastUtil.makeText(this, "正在编辑中不可切换");
                } else {
                    initPerson(true);
                }
                break;
            case R.id.add_billing_info_enterprise_but:
                if (isEdit) {
                    ToastUtil.makeText(this, "正在编辑中不可切换");
                } else {
                    initPerson(false);
                }
                break;
            case R.id.add_billing_enterprise_person_reset://企业
                add_billing_enterprise_edit_name.setText("");
                add_billing_enterprise_edit_code.setText("");
                add_billing_enterprise_edit_address.setText("");
                add_billing_enterprise_edit_phone.setText("");
                add_billing_enterprise_edit_bank.setText("");
                add_billing_enterprise_edit_bankcode.setText("");
                add_billing_enterprise_edit_person_phone.setText("");
                add_billing_enterprise_edit_person_emil.setText("");
                break;
            case R.id.add_billing_enterprise_person_determine://企业
                String qyName = add_billing_enterprise_edit_name.getText().toString().trim();
                String personCode = add_billing_enterprise_edit_code.getText().toString().trim();
                String qyphone = add_billing_enterprise_edit_person_phone.getText().toString().trim();


                if (StringUtil.isEmpty(qyName)) {
                    ShowToast(this, "请填写企业名称");
                    return;
                }
                if (StringUtil.isEmpty(personCode)) {
                    ShowToast(this, "请填写纳税人识别码");
                    return;
                }
                if (StringUtil.isEmpty(qyphone)) {
                    ShowToast(this, "请填写手机号码");
                    return;
                } else {
                    if (!StringUtil.isPhone(qyphone)) {
                        ShowToast(this, "请填写正确手机号码");
                        return;
                    }
                }
                if (!StringUtil.isShuihao(personCode)) {
                    ShowToast(this, "请填写正确的税号");
                    return;
                }
                if (StringUtil.isEmpty(add_billing_enterprise_edit_person_emil.getText().toString().trim())) {
                    ShowToast(this, "请填写邮箱");
                    return;
                }
                Map<String, String> paramss = new HashMap<>();
                if (billingInfoItmeBean == null) {
                    paramss.put("UserInvoiceID", "");
                } else {
                    paramss.put("UserInvoiceID", billingInfoItmeBean.getUserInvoiceID() + "");//不传
                }
                paramss.put("UserID", "");//不传
                paramss.put("UserInvoiceReceiveType", "2");
                paramss.put("UserInvoiceTitle", qyName);
                paramss.put("UserInvoiceContent", "");
                paramss.put("UserInvoicePhone", qyphone);
                paramss.put("UserInvoiceEmail", add_billing_enterprise_edit_person_emil.getText().toString().trim());
                paramss.put("UserInvoiceTaxpayerNo", personCode);
                paramss.put("UserInvoiceorAddress", add_billing_enterprise_edit_address.getText().toString().trim());
                paramss.put("UserInvoiceBuyerTel", add_billing_enterprise_edit_phone.getText().toString().trim());
                paramss.put("UserInvoiceBuyerBankName", add_billing_enterprise_edit_bank.getText().toString().trim());
                paramss.put("UserInvoiceBankAcount", add_billing_enterprise_edit_bankcode.getText().toString().trim());
                paramss.put("UserInvoiceDefault", "0");
                if (isEdit) {
                    execApi(ApiType.GetInvoiceEdit, paramss);
                } else {
                    execApi(ApiType.GetInvoiceAdd, paramss);
                }
                break;
            case R.id.add_billing_person_reset://个人重置
                add_billing_person_name.setText("");
                add_billing_person_phone.setText("");
                add_billing_person_emil.setText("");
                break;
            case R.id.add_billing_person_determine://个人提交
                String personName = add_billing_person_name.getText().toString().trim();
                String personPhone = add_billing_person_phone.getText().toString().trim();
                String personEmil = add_billing_person_emil.getText().toString().trim();
                if (StringUtil.isEmpty(personName)) {
                    ShowToast(this, "请填写收票人姓名");
                    return;
                }
                if (StringUtil.isEmpty(personPhone)) {
                    ShowToast(this, "请填写收票人手机");
                    return;
                }
                if (!StringUtil.isPhone(personPhone)) {
                    ShowToast(this, "请填写正确手机号码");
                    return;
                }
                if (StringUtil.isEmpty(personEmil)) {
                    ShowToast(this, "请填写邮箱");
                    return;
                }
                if (!StringUtil.isEmpty(personEmil) && !StringUtil.isEmail(personEmil)) {
                    ShowToast(this, "请填写正确邮箱");
                    return;
                }

                Map<String, String> params = new HashMap<>();
                if (billingInfoItmeBean == null) {
                    params.put("UserInvoiceID", "");
                } else {
                    params.put("UserInvoiceID", billingInfoItmeBean.getUserInvoiceID() + "");//不传
                }
                params.put("UserID", "");//不传
                params.put("UserInvoiceReceiveType", "1");
                params.put("UserInvoiceTitle", personName);
                params.put("UserInvoiceContent", "");
                params.put("UserInvoicePhone", personPhone);
                params.put("UserInvoiceEmail", personEmil);
                params.put("UserInvoiceTaxpayerNo", "");
                params.put("UserInvoiceorAddress", "");
                params.put("UserInvoiceBuyerTel", "");
                params.put("UserInvoiceBuyerBankName", "");
                params.put("UserInvoiceBankAcount", "");
                params.put("UserInvoiceDefault", "0");
                if (isEdit) {
                    execApi(ApiType.GetInvoiceEdit, params);
                } else {
                    execApi(ApiType.GetInvoiceAdd, params);
                }
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        LogUtil.i("Request", req.toString());
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(AddBillingInformationActivity.this);
                return;
            }
            LogUtil.i("Exception", req.toString());
        } else {
            if (req.getData().getRet()) {
                if (billingInfoItmeBean != null) {
                    if (isOrderTicke) {
                        onBackPressed();
                        return;
                    }
                    if (billingInfoItmeBean.isTransmit()) {
                        Intent mIntent = new Intent();
                        // 设置结果，并进行传送
                        this.setResult(11, mIntent);
                    } else {
                        startActivity(BillingInformation.class);
                    }
                } else {
                    startActivity(BillingInformation.class);
                }
                finish();
            } else {
                ToastUtil.makeText(this, req.getData().getMsg());
            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        if (req.getData() != null) {
            ToastUtil.makeText(this, req.getData().getMsg());
        }
        LogUtil.i("Request", req.toString());
    }

    private void initPerson(boolean ispson) {
        if (ispson) {
            add_billing_info_person_but.setTextColor(getResources().getColor(R.color.addBillingInfotext));
            add_billing_info_enterprise_but.setTextColor(getResources().getColor(R.color.black));
            add_billing_info_person_but.setBackground(getResources().getDrawable(R.drawable.add_billing_red));
            add_billing_info_enterprise_but.setBackground(getResources().getDrawable(R.drawable.add_billing_hui));
            add_billing_enterprise_info_layout.setVisibility(View.GONE);
            add_billing_pserson_info_layout.setVisibility(View.VISIBLE);
        } else {
            add_billing_info_enterprise_but.setTextColor(getResources().getColor(R.color.addBillingInfotext));
            add_billing_info_person_but.setTextColor(getResources().getColor(R.color.black));
            add_billing_info_enterprise_but.setBackground(getResources().getDrawable(R.drawable.add_billing_red));
            add_billing_info_person_but.setBackground(getResources().getDrawable(R.drawable.add_billing_hui));
            add_billing_enterprise_info_layout.setVisibility(View.VISIBLE);
            add_billing_pserson_info_layout.setVisibility(View.GONE);
        }
    }
}
