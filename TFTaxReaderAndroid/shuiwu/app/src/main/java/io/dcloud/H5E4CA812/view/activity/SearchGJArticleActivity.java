package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.adapter.SearchArticleAdapter;
import io.dcloud.H5E4CA812.view.bean.SearchArticleBean;
import io.dcloud.H5E4CA812.view.widget.down_load.LoadMoreWrapper;
import io.dcloud.H5E4CA812.view.widget.down_load.OnScrollListener;

public class SearchGJArticleActivity extends BaseActivity {
    @BindView(R.id.recyclerview_search_article)
    RecyclerView recyclerview_search_article;
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;

    SearchArticleAdapter searchArticleAdapter;
    private List<SearchArticleBean.DataBean> mDataList;
    Bundle bundle;
    String searchStr = "";

    private int pageindex = 1;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int Isnotif = 0;//是否是刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        bundle = getIntent().getExtras();
        ButterKnife.bind(this);
        initTitle();
        initListView();
        initDate();
    }

    private void initListView() {
        mDataList = new ArrayList<>();
        searchArticleAdapter = new SearchArticleAdapter(mDataList, this);
        mLoadMoreWrapper = new LoadMoreWrapper(searchArticleAdapter);
        recyclerview_search_article.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_search_article.setAdapter(mLoadMoreWrapper);
        recyclerview_search_article.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING);
                Isnotif = 1;
                initDate();
            }
        });

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                Isnotif = 0;
                initDate();
            }
        });
    }

    String gj_search_lm, gj_search_pm, gj_search_zz, gj_search_gjc, gj_search_qw, gj_search_km, gj_search_nq1, gj_search_nq2, gj_search_nq3, gj_search_nq4;

    private void initDate() {
        Loading.getInstance(this).show();
        gj_search_lm = getIntent().getStringExtra("gj_search_lm");
        gj_search_pm = getIntent().getStringExtra("gj_search_pm");
        gj_search_zz = getIntent().getStringExtra("gj_search_zz");
        gj_search_gjc = getIntent().getStringExtra("gj_search_gjc");
        gj_search_qw = getIntent().getStringExtra("gj_search_qw");
        gj_search_km = getIntent().getStringExtra("gj_search_km");
        gj_search_nq1 = getIntent().getStringExtra("gj_search_nq1");
        gj_search_nq2 = getIntent().getStringExtra("gj_search_nq2");
        gj_search_nq3 = getIntent().getStringExtra("gj_search_nq3");
        gj_search_nq4 = getIntent().getStringExtra("gj_search_nq4");
        Map map = new HashMap();
        if (!StringUtil.isEmpty(gj_search_lm)) {
            map.put("CataName", gj_search_lm);
        }
        if (!StringUtil.isEmpty(gj_search_pm)) {
            map.put("TitleName", gj_search_pm);
        }
        if (!StringUtil.isEmpty(gj_search_zz)) {
            map.put("AuthorName", gj_search_zz);
        }
        if (!StringUtil.isEmpty(gj_search_gjc)) {
            map.put("KeyWord", gj_search_gjc);
        }
        if (!StringUtil.isEmpty(gj_search_qw)) {
            map.put("Content", gj_search_qw);
        }
        if (!StringUtil.isEmpty(gj_search_km)) {
            map.put("MagazineIDs", gj_search_km);
        }
        if (!StringUtil.isEmpty(gj_search_nq1)) {
            map.put("BeginYear", gj_search_nq1);
        }
        if (!StringUtil.isEmpty(gj_search_nq2)) {
            map.put("EndYear", gj_search_nq2);
        }
        if (!StringUtil.isEmpty(gj_search_nq3)) {
            map.put("BeginNo", gj_search_nq3);
        }
        if (!StringUtil.isEmpty(gj_search_nq4)) {
            map.put("EndNo", gj_search_nq4);
        }
//        {
//            "CataName": "string",//栏目
//                "TitleName": "string",//篇名
//                "AuthorName": "string",//作者
//                "KeyWord": "string",--关键词
//            "Content": "string",--全文
//            "MagazineIDs": "string",--刊名id 逗号分割“,”
//            "BeginYear": 0,--开始年份
//            "BeginNo": 0,--开始期号
//            "EndYear": 0,--结束年份
//            "EndNo": 0,--结束期号
//            "PageIndex": 0,--页码
//            "PageSize": 0--页面大小
//        }
        map.put("PageIndex", pageindex);
        map.put("PageSize", 20);
        execApi(ApiType.GetArticleAdvSearch, map);
        pageindex++;
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("文章");
    }

    @OnClick({R.id.back_but})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }

    }

    @Override
    public void onResponsed(Request req) {

    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                Gson gson = new Gson();
                if (Isnotif == 0) {
                    mDataList.clear();
                }
                JSONObject jsonObject = new JSONObject(gson.toJson(req.getData().getData()));
                SearchArticleBean itme = gson.fromJson(jsonObject.toString(), SearchArticleBean.class);
                if (itme.getData() != null && itme.getData().size() > 0) {
                    for (int i = 0; i < itme.getData().size(); i++) {
                        mDataList.add(itme.getData().get(i));
                    }
                    searchArticleAdapter.setmDataList(mDataList);
                    mLoadMoreWrapper.notifyDataSetChanged();
                } else {
                    if (Isnotif == 1) {
                        mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_END);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Loading.getInstance(this).hide();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(this).hide();
        mSwipeLayout.setRefreshing(false);
    }
}
