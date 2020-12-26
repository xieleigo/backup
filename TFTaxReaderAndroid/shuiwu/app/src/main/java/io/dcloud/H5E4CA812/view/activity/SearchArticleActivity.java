package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.adapter.SearchArticleAdapter;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.SearchArticleBean;
import io.dcloud.H5E4CA812.view.widget.down_load.LoadMoreWrapper;
import io.dcloud.H5E4CA812.view.widget.down_load.OnScrollListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchArticleActivity extends BaseActivity {
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

    private void initDate() {
        Loading.getInstance(this).show();
        searchStr = getIntent().getStringExtra("searchStr");
        Map map = new HashMap();
        map.put("KeyWord", searchStr);
        map.put("PageIndex", pageindex);
        map.put("PageSize", 20);
        execApi(ApiType.GetArticleSearch, map);
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
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(SearchArticleActivity.this);
                return;
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
