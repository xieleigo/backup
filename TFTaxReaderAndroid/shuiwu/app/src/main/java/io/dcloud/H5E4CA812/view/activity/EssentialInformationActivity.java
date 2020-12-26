package io.dcloud.H5E4CA812.view.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.bean.UserInfoBean;
import io.dcloud.H5E4CA812.view.widget.addressPhotoPopWin;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EssentialInformationActivity extends BaseActivity {
    @BindView(R.id.essential_info_address_layou)
    RelativeLayout essential_info_address_layou;
    @BindView(R.id.essential_info_birthday_layout)
    RelativeLayout essential_info_birthday_layout;
    @BindView(R.id.essential_info_address_code)
    TextView essential_info_address_code;
    @BindView(R.id.essential_info_address_edit)
    TextView essential_info_address_edit;
    @BindView(R.id.essential_info_edit)
    TextView essential_info_edit;
    @BindView(R.id.essential_info_accont_text)
    TextView essential_info_accont_text;
    @BindView(R.id.essential_info_nickname_edit)
    EditText essential_info_nickname_edit;
    @BindView(R.id.essential_info_name_edit)
    EditText essential_info_name_edit;
    @BindView(R.id.essential_info_phone_edit)
    EditText essential_info_phone_edit;
    @BindView(R.id.essential_info_sex_group)
    RadioGroup essential_info_sex_group;
    @BindView(R.id.essential_info_sex_man)
    RadioButton essential_info_sex_man;
    @BindView(R.id.essential_info_sex_female)
    RadioButton essential_info_sex_female;
    @BindView(R.id.essential_info_sex_bm)
    RadioButton essential_info_sex_bm;
    @BindView(R.id.essential_info_reset)
    Button essential_info_reset;
    @BindView(R.id.essential_info_determine)
    Button essential_info_determine;
    UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_information);
        ButterKnife.bind(this);
        initTitle();
        initdata();
    }

    private void initdata() {
        execApi(ApiType.GetInfo, null);
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("基本信息");
    }

    String accont;
    String nickname;
    String username;
    String sex;
    String birthday;
    String phone;
    String address;

    @OnClick({R.id.back_but, R.id.essential_info_address_layou, R.id.essential_info_birthday_layout, R.id.essential_info_reset, R.id.essential_info_determine})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.essential_info_address_layou:
                showaddressPopFormBottom(view, essential_info_address_edit, essential_info_address_code);
                break;
            case R.id.essential_info_birthday_layout:
                showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        essential_info_edit.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, essential_info_edit.getText().toString());
                break;
            case R.id.essential_info_reset:
//                essential_info_nickname_edit.setText("");
//                essential_info_name_edit.setText("");
//                essential_info_phone_edit.setText("");
//                essential_info_address_edit.setText("");
//                essential_info_edit.setText("");

                essential_info_accont_text.setText(userInfoBean.getUserName());
                essential_info_nickname_edit.setText(userInfoBean.getUserNickName());
                essential_info_name_edit.setText(userInfoBean.getUserRealName());
                essential_info_edit.setText(userInfoBean.getUserBirthDate() + "");
                //UserGender性别
                if (userInfoBean.getUserGender() == 1) {
                    essential_info_sex_man.setChecked(true);
                } else if (userInfoBean.getUserGender() == 2) {
                    essential_info_sex_female.setChecked(true);
                } else if (userInfoBean.getUserGender() == 3) {
                    essential_info_sex_bm.setChecked(true);
                }
                essential_info_phone_edit.setText(userInfoBean.getUserMobile());
                essential_info_address_edit.setText(userInfoBean.getAreaFullName());

                break;
            case R.id.essential_info_determine:
                accont = essential_info_accont_text.getText().toString().trim();
                nickname = essential_info_nickname_edit.getText().toString().trim();
                username = essential_info_name_edit.getText().toString().trim();
                birthday = essential_info_edit.getText().toString().trim();
                phone = essential_info_phone_edit.getText().toString().trim();
                address = essential_info_address_edit.getText().toString().trim();
                if (essential_info_sex_man.isChecked()) {
                    sex = "1";
                }
                if (essential_info_sex_female.isChecked()) {
                    sex = "2";
                }
                if (essential_info_sex_bm.isChecked()) {
                    sex = "3";
                }
                if (StringUtil.isEmpty(nickname)) {
                    ShowToast(this, "请输入昵称");
                    return;
                }
                if (StringUtil.isEmpty(username)) {
                    ShowToast(this, "请输入姓名");
                    return;
                }
//                if (StringUtil.isEmpty(birthday)) {
//                    ShowToast(this, "请输入生日");
//                    return;
//                }
//                if (StringUtil.isEmpty(phone)) {
//                    ShowToast(this, "请输入手机号码");
//                    return;
//                }
//                if (!StringUtil.isPhone(phone)) {
//                    ShowToast(this, "请输入正确的手机号");
//                    return;
//                }
                if (StringUtil.isEmpty(address)) {
                    ShowToast(this, "请选择地址");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("UserNickName", nickname);
                params.put("UserRealName", username);
                params.put("UserGender", sex);
                params.put("UserBirthDate", birthday);
                params.put("AreaTreePath", essential_info_address_code.getText().toString());

//            {
//                "UserNickName": "string",
//                    "UserRealName": "string",
//                    "UserGender": 1,
//                    "UserBirthDate": "2020-06-01T07:27:37.122Z",
//                    "AreaTreePath": "string"
//            }
                execApi(ApiType.GetUpInfo, params);
                break;
        }
    }

    public void showaddressPopFormBottom(View view, TextView textView, TextView register_drss_code) {
        addressPhotoPopWin takePhotoPopWin = new addressPhotoPopWin(this, this, textView, register_drss_code);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.essential_info_address_layou), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onResponsed(Request req) {
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);

        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(EssentialInformationActivity.this);
                return;
            }
        }
        if (req.getData() != null && req.getData().getRet()) {
            if (StringUtil.isEmpty(req.getData().getData().toString())) {
                ToastUtil.makeText(this, req.getData().getMsg());

                finish();
            } else {
                Gson gson = new Gson();
                userInfoBean = gson.fromJson(gson.toJson(req.getData().getData()), UserInfoBean.class);
                LogUtil.i("userInfoBean", userInfoBean.toString());
                essential_info_accont_text.setText(userInfoBean.getUserName());
                essential_info_nickname_edit.setText(userInfoBean.getUserNickName());
                essential_info_name_edit.setText(userInfoBean.getUserRealName());
                if (StringUtil.isEmpty(userInfoBean.getUserBirthDate() + "")) {
                    essential_info_edit.setText("");
                } else {
                    ;
                    essential_info_edit.setText(userInfoBean.getUserBirthDate().toString().split(" ")[0]);
                }
                //UserGender性别
                if (userInfoBean.getUserGender() == 1) {
                    essential_info_sex_man.setChecked(true);
                } else if (userInfoBean.getUserGender() == 2) {
                    essential_info_sex_female.setChecked(true);
                } else if (userInfoBean.getUserGender() == 3) {
                    essential_info_sex_bm.setChecked(true);
                }
                essential_info_phone_edit.setText(userInfoBean.getUserMobile());
                essential_info_address_edit.setText(userInfoBean.getAreaFullName());
                essential_info_address_code.setText(userInfoBean.getAreaTreePath());
            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ToastUtil.makeText(this, req.getErrorMsg());
    }

    /**
     * @param listener 选择日期确定后执行的接口
     * @param curDate  当前显示的日期
     * @return
     * @description 选择日期弹出框
     * @author wqy
     * @time 2020-1-6 14:23
     */
    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        try {
            year = Integer.parseInt(curDate.substring(0, curDate.indexOf("-")));
            month = Integer.parseInt(curDate.substring(curDate.indexOf("-") + 1, curDate.lastIndexOf("-"))) - 1;
            day = Integer.parseInt(curDate.substring(curDate.lastIndexOf("-") + 1, curDate.length()));
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);
        datePickerDialog.show();
    }
}
