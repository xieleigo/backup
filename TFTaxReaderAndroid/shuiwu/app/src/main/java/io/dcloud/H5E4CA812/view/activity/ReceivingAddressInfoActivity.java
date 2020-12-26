package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.widget.addressPhotoPopWin;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceivingAddressInfoActivity extends BaseActivity {

    @BindView(R.id.receiving_address_info_name)
    EditText receiving_address_info_name;
    @BindView(R.id.receiving_address_select_layout)
    RelativeLayout receiving_address_select_layout;
    @BindView(R.id.receiving_address_select_info)
    TextView receiving_address_select_info;
    @BindView(R.id.receiving_address_info_address)
    EditText receiving_address_info_address;
    @BindView(R.id.receiving_address_info_phone)
    EditText receiving_address_info_phone;
    @BindView(R.id.receiving_address_info_gdphone)
    EditText receiving_address_info_gdphone;
    //    @BindView(R.id.receiving_address_info_emil)
//    EditText receiving_address_info_emil;
    @BindView(R.id.receiving_address_info_emilnum)
    EditText receiving_address_info_emilnum;
    @BindView(R.id.receiving_address_info_mrt_layout)
    RelativeLayout receiving_address_info_mrt_layout;
    @BindView(R.id.receiving_address_info_mr)
    ImageView receiving_address_info_mr;
    @BindView(R.id.receiving_address_info_determine)
    Button receiving_address_info_determine;
    @BindView(R.id.addressCode)
    TextView addressCode;

    private int IsAddress;
    String name = "";
    String addressbig = "";
    String address = "";
    String phone = "";
    String gdphone;
    String emilnum;
    ReceivingAddressBean receivingAddressBean;
    Boolean IsAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_address_info);
        ButterKnife.bind(this);
        initTitle();
        initDate();
    }

    private void initDate() {
        receivingAddressBean = (ReceivingAddressBean) getIntent().getSerializableExtra("receivingAddressBean");
        if (receivingAddressBean != null) {
            IsAdd = false;
            receiving_address_info_name.setText(receivingAddressBean.getUserAddressMan());
            receiving_address_select_info.setText(receivingAddressBean.getUserAddressDistrict());
            receiving_address_info_address.setText(receivingAddressBean.getUserAddressDetail());
            receiving_address_info_phone.setText(receivingAddressBean.getUserAddressMobile());
            receiving_address_info_gdphone.setText(receivingAddressBean.getUserAddressPhone());
            receiving_address_info_emilnum.setText(receivingAddressBean.getUserAddressZIPCode());
            IsAddress = receivingAddressBean.getUserAddressIsDefault();
            if (receivingAddressBean.getUserAddressIsDefault() == 1) {
                receiving_address_info_mr.setBackground(getDrawable(R.mipmap.button_open));
            } else {
                receiving_address_info_mr.setBackground(getDrawable(R.mipmap.button_clos));
            }
        } else {
            IsAdd = true;
        }

    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("设置地址");
    }

    @OnClick({R.id.back_but, R.id.receiving_address_select_layout,
            R.id.receiving_address_info_determine,
            R.id.receiving_address_info_mrt_layout})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.receiving_address_select_layout:
                showaddressPopFormBottom(view, receiving_address_select_info, addressCode);
                break;
            case R.id.receiving_address_info_mrt_layout:
                if (IsAddress == 0) {
                    IsAddress = 1;
                    receiving_address_info_mr.setBackground(getDrawable(R.mipmap.button_open));

                } else {
                    IsAddress = 0;
                    receiving_address_info_mr.setBackground(getDrawable(R.mipmap.button_clos));
                }
                break;
            case R.id.receiving_address_info_determine:
                name = receiving_address_info_name.getText().toString().trim();
                addressbig = receiving_address_select_info.getText().toString().trim();
                address = receiving_address_info_address.getText().toString().trim();
                phone = receiving_address_info_phone.getText().toString().trim();
                if (StringUtil.isEmpty(name)) {
                    ShowToast(this, "请输入收货人");
                    return;
                }
                if (StringUtil.isEmpty(addressbig)) {
                    ShowToast(this, "请选择所在地区");
                    return;
                }
                if (StringUtil.isEmpty(address)) {
                    ShowToast(this, "请输入地址");
                    return;
                }
                if (StringUtil.isEmpty(phone)) {
                    ShowToast(this, "请输入手机号");
                    return;
                }
                String addressinfo = receiving_address_select_info.getText().toString().trim();
                String guojia = "";
                String sheng = "";
                String xingzhengqu = "";
                String shi;
                if (addressinfo.split(" ").length < 2) {
                    guojia = receivingAddressBean.getUserAddressCountry();
                    sheng = receivingAddressBean.getUserAddressProv();
                    xingzhengqu = receivingAddressBean.getUserAddressDistrict();
                    shi = receivingAddressBean.getUserAddressStreet();
                } else {
                    guojia = addressinfo.split(" ")[0];
                    sheng = addressinfo.split(" ")[1];
                    xingzhengqu = addressinfo.split(" ")[1];
                    shi = addressinfo.split(" ")[2];
                }
                String gdphone = receiving_address_info_gdphone.getText().toString().trim();
                if (StringUtil.isEmpty(gdphone)) {
                    gdphone = "";
                }
                String emilnum = receiving_address_info_emilnum.getText().toString().trim();
                if (StringUtil.isEmpty(gdphone)) {
                    emilnum = "";
                }
                Map<String, String> params = new HashMap<>();
                if (receivingAddressBean == null) {
                    params.put("UserAddrID", "");
                } else {
                    params.put("UserAddrID", receivingAddressBean.getUserAddrID() + "");
                }
                params.put("UserAddressMan", name);
                params.put("UserAddressCountry", guojia);//国家
                params.put("UserAddressProv", sheng);//省
                params.put("UserAddressCity", sheng);//市
                params.put("UserAddressDistrict", xingzhengqu);//行政区
                params.put("UserAddressStreet", shi);//街道
                params.put("UserAddressDetail", receiving_address_select_info.getText() + address);
                params.put("UserAddressMobile", phone);
                params.put("UserAddressPhone", gdphone);
                params.put("UserAddressZIPCode", emilnum);
                params.put("UserAddressIsDefault", IsAddress + "");
                params.put("UserAddressManGender", "");
                Loading.getInstance(this).show();
                if (IsAdd) {
                    execApi(ApiType.GetAddressAdd, params);
                } else {
                    execApi(ApiType.GetAddressEdit, params);
                }

                break;
        }
    }

    public void showaddressPopFormBottom(View view, TextView textView, TextView addressCode) {
        addressPhotoPopWin takePhotoPopWin = new addressPhotoPopWin(this, this, textView, addressCode);
        takePhotoPopWin.showAtLocation(findViewById(R.id.receiving_address_info_determine), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        Loading.getInstance(this).hide();
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(ReceivingAddressInfoActivity.this);
            }
        }
        LogUtil.i("Request", req.getData().toString());
        if (req.getData().getRet()) {
            startActivity(ReceivingAddressActivity.class);
        } else {
            ToastUtil.makeText(this, req.getData().getMsg());
        }
    }

    @Override
    public void onResponsedError(Request req) {
        Loading.getInstance(this).hide();
        super.onResponsedError(req);
    }
}

