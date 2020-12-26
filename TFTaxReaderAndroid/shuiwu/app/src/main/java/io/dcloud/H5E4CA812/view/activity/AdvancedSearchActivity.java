package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.GJSearchPopAdapter;
import io.dcloud.H5E4CA812.view.bean.GJSearchPopItmeBean;
import io.dcloud.H5E4CA812.view.widget.GJSearhPhotoPopWin;

/**
 * 高级搜索
 */

public class AdvancedSearchActivity extends BaseActivity {
    @BindView(R.id.gj_search_lm)
    EditText gj_search_lm;
    @BindView(R.id.gj_search_pm)
    EditText gj_search_pm;
    @BindView(R.id.gj_search_zz)
    EditText gj_search_zz;
    @BindView(R.id.gj_search_gjc)
    EditText gj_search_gjc;
    @BindView(R.id.gj_search_qw)
    EditText gj_search_qw;
    @BindView(R.id.gj_search_km)
    TextView gj_search_km;
    @BindView(R.id.gj_search_nq1)
    EditText gj_search_nq1;
    @BindView(R.id.gj_search_nq2)
    EditText gj_search_nq2;
    @BindView(R.id.gj_search_nq3)
    EditText gj_search_nq3;
    @BindView(R.id.gj_search_nq4)
    EditText gj_search_nq4;
    @BindView(R.id.gj_search_js)
    Button gj_search_js;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        ButterKnife.bind(this);
    }

    List<GJSearchPopItmeBean> allIds = new ArrayList<>();

    @OnClick({R.id.gj_search_js, R.id.gj_search_km})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.gj_search_js:
                if (StringUtil.isEmpty(gj_search_lm.getText().toString())
                        && StringUtil.isEmpty(gj_search_pm.getText().toString())
                        && StringUtil.isEmpty(gj_search_zz.getText().toString())
                        && StringUtil.isEmpty(gj_search_gjc.getText().toString())
                        && StringUtil.isEmpty(gj_search_qw.getText().toString())
                        && StringUtil.isEmpty(gj_search_nq1.getText().toString())
                        && StringUtil.isEmpty(gj_search_nq2.getText().toString())
                        && StringUtil.isEmpty(gj_search_nq3.getText().toString())
                        && StringUtil.isEmpty(gj_search_nq4.getText().toString())
                        && allIds.size() == 0
                ) {
                    ToastUtil.makeText(this, "请您至少输入一个条件进行查询");
                    return;
                }
                Intent intent = new Intent(AdvancedSearchActivity.this, SearchGJArticleActivity.class);
                intent.putExtra("gj_search_lm", gj_search_lm.getText().toString());
                intent.putExtra("gj_search_pm", gj_search_pm.getText().toString());
                intent.putExtra("gj_search_zz", gj_search_zz.getText().toString());
                intent.putExtra("gj_search_gjc", gj_search_gjc.getText().toString());
                intent.putExtra("gj_search_qw", gj_search_qw.getText().toString());
                String ids = "";
                for (int i = 0; i < allIds.size(); i++) {
                    ids = ids + allIds.get(i).getPubID() + ",";
                }
                intent.putExtra("gj_search_km", ids);
                intent.putExtra("gj_search_nq1", gj_search_nq1.getText().toString());
                intent.putExtra("gj_search_nq2", gj_search_nq2.getText().toString());
                intent.putExtra("gj_search_nq3", gj_search_nq3.getText().toString());
                intent.putExtra("gj_search_nq4", gj_search_nq4.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.gj_search_km:
                execApi(ApiType.GetPublicationGetAllPublication, null);
                break;
        }

    }

    List<GJSearchPopItmeBean> popList = new ArrayList<>();

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                Gson gson = new Gson();
                if (req.getData().getUrl().contains("/Publication/GetAllPublication")) {//最新期刊
                    popList.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GJSearchPopItmeBean itme = gson.fromJson(jsonArray.get(i).toString(), GJSearchPopItmeBean.class);
                        itme.setSelected(false);
                        popList.add(itme);
                    }
                    GJSearhPhotoPopWin takePhotoPopWin = new GJSearhPhotoPopWin(this, gj_search_km, popList);
                    takePhotoPopWin.showAtLocation(findViewById(R.id.gj_search_km), Gravity.CENTER, 0, 0);
                    takePhotoPopWin.setOnItemClickListener(new GJSearhPhotoPopWin.OnRecyItemClickListener() {
                        @Override
                        public void onClick(List<GJSearchPopItmeBean> ids) {
                            allIds = ids;
                            String name = "";
                            for (int i = 0; i < allIds.size(); i++) {
                                if (allIds.size() == (i + 1)) {
                                    name = name + allIds.get(i).getPubName();
                                } else {
                                    name = name + allIds.get(i).getPubName() + ",";
                                }
                            }
                            gj_search_km.setText(name);
                            takePhotoPopWin.dismiss();
                        }
                    });
                }
            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(AdvancedSearchActivity.this);
            }
        }

    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);

        ToastUtil.makeText(this, req.getErrorMsg());
    }

    @Override
    public void onResponsed(Request req) {

    }

}
