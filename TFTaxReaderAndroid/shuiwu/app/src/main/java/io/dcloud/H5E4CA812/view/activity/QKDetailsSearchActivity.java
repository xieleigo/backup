package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.adapter.QKDetailsYearAdapter;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.adapter.QKDetailsQKAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 期刊详情搜索
 */
public class QKDetailsSearchActivity extends BaseActivity {

    @BindView(R.id.year_recyclerview)
    RecyclerView year_recyclerview;
    @BindView(R.id.year_details_recyclerview)
    RecyclerView year_details_recyclerview;

    QKDetailsYearAdapter qkDetailsYearAdapter;
    QKDetailsQKAdapter qkDetailsQKAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qkdetails_search_);
        ButterKnife.bind(this);
        initView();
        initTitle();

    }

    List<YearBeanDetails> qkList;
    List<YearBean> yearList;

    private void initView() {
        yearList = new ArrayList();
        qkDetailsYearAdapter = new QKDetailsYearAdapter(yearList, this);
        year_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        year_recyclerview.setAdapter(qkDetailsYearAdapter);
        qkDetailsYearAdapter.setOnSendClickListener(new QKDetailsYearAdapter.OnSendListener() {
            @Override
            public void send(String year) {
                initYearDateDetails(year);
            }
        });
        qkList = new ArrayList<>();
        qkDetailsQKAdapter = new QKDetailsQKAdapter(QKDetailsSearchActivity.this, qkList);
        year_details_recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        year_details_recyclerview.setAdapter(qkDetailsQKAdapter);
        PubID = getIntent().getIntExtra("PubID", 0);
        name = getIntent().getStringExtra("name");
        initYearDate();
    }

    int PubID = 0;
    String name = "";

    private void initYearDate() {
        Map map = new HashMap();
        map.put("PubID", PubID);
        execApi(ApiType.GetProductGetYearPubIssue, map);
    }

    private void initYearDateDetails(String selectYear) {
        Map map = new HashMap();
        map.put("PubID", PubID);
        map.put("Year", selectYear);
        execApi(ApiType.GetProductGetPubIssueByYear, map);
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName(name + "往期");
    }

    @OnClick(R.id.back_but)
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(QKDetailsSearchActivity.this);
                return;
            }
            LogUtil.i("Exception", req.toString());
        }
        Gson gson = new Gson();
        try {
            if (req.getData().getUrl().contains("Product/GetYearPubIssue")) {
                yearList.clear();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    YearBean itme = gson.fromJson(jsonArray.get(i).toString(), YearBean.class);
                    yearList.add(itme);
                }
                yearList.get(0).setIsback(true);
                qkDetailsYearAdapter.setmDataList(yearList);
                initYearDateDetails(yearList.get(0).Year);
            }
            if (req.getData().getUrl().contains("Product/GetPubIssueByYear")) {
                qkList.clear();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    YearBeanDetails itme = gson.fromJson(jsonArray.get(i).toString(), YearBeanDetails.class);
                    qkList.add(itme);
                }
                qkDetailsQKAdapter.setmDataList(qkList);
            }

        } catch (Exception e) {
            LogUtil.i("Exception", e.toString());
        }

    }

    public class YearBean {
        String Year;
        int PubID;
        boolean isback = false;

        public String getYear() {
            return Year;
        }

        public void setYear(String year) {
            Year = year;
        }

        public int getPubID() {
            return PubID;
        }

        public void setPubID(int pubID) {
            PubID = pubID;
        }

        public Boolean getIsback() {
            return isback;
        }

        public void setIsback(Boolean isback) {
            this.isback = isback;
        }

        @Override
        public String toString() {
            return "YearBean{" +
                    "Year='" + Year + '\'' +
                    ", PubID=" + PubID +
                    ", isback=" + isback +
                    '}';
        }
    }

    public class YearBeanDetails {

        /**
         * ProdName : 中国税务
         * ProdIssue : 1
         * ProdSumIssue : 0
         * ReadSourceID : 983
         * ProdImg : /upload/1987/Layouts/ZS198701.Source.jpg
         */

        private String ProdName;
        private int ProdIssue;
        private int ProdSumIssue;
        private String ReadSourceID;
        private String ProdImg;
        private int ProdYear;

        public int getProdYear() {
            return ProdYear;
        }

        public void setProdYear(int prodYear) {
            ProdYear = prodYear;
        }

        public boolean isProdIsFree() {
            return ProdIsFree;
        }

        public void setProdIsFree(boolean prodIsFree) {
            ProdIsFree = prodIsFree;
        }

        private boolean ProdIsFree;

        public String getProdName() {
            return ProdName;
        }

        public void setProdName(String ProdName) {
            this.ProdName = ProdName;
        }

        public int getProdIssue() {
            return ProdIssue;
        }

        public void setProdIssue(int ProdIssue) {
            this.ProdIssue = ProdIssue;
        }

        public int getProdSumIssue() {
            return ProdSumIssue;
        }

        public void setProdSumIssue(int ProdSumIssue) {
            this.ProdSumIssue = ProdSumIssue;
        }

        public String getReadSourceID() {
            return ReadSourceID;
        }

        public void setReadSourceID(String ReadSourceID) {
            this.ReadSourceID = ReadSourceID;
        }

        public String getProdImg() {
            return ProdImg;
        }

        public void setProdImg(String ProdImg) {
            this.ProdImg = ProdImg;
        }

        @Override
        public String toString() {
            return "YearBeanDetails{" +
                    "ProdName='" + ProdName + '\'' +
                    ", ProdIssue=" + ProdIssue +
                    ", ProdSumIssue=" + ProdSumIssue +
                    ", ReadSourceID='" + ReadSourceID + '\'' +
                    ", ProdImg='" + ProdImg + '\'' +
                    ", ProdYear=" + ProdYear +
                    ", ProdIsFree=" + ProdIsFree +
                    '}';
        }
    }
}
