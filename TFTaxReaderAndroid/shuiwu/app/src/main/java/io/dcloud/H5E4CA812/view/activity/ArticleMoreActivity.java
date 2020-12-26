package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.HomeWzBean;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.adapter.ArticleMoreAdapter;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文章更多页面
 */
public class ArticleMoreActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recyclerview_article_more)
    RecyclerView mRecyclerView;
    List<HomeWzBean> mDataList;
    ArticleMoreAdapter articleMoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_more);
        ButterKnife.bind(this);
        initTitle();
        initListView();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList.clear();
                Loading.getInstance(ArticleMoreActivity.this).show();
                execApi(ApiType.GetArticleRecd, null);//推荐文章
            }
        });
    }

    private void initListView() {

        execApi(ApiType.GetArticleRecd, null);//推荐文章
        mDataList = new ArrayList<HomeWzBean>();

        articleMoreAdapter = new ArticleMoreAdapter(mDataList, this);
        articleMoreAdapter.setOnItemClickListener(new OnRecyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(ArticleMoreActivity.this, QiKanDetailsAcitvity.class);
                intent.putExtra("ArticleID", Integer.valueOf(mDataList.get(position).getReadSourceID()));
                startActivity(intent);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(articleMoreAdapter);
    }


    @Override
    public void onResponsed(Request req) {
        mSwipeLayout.setRefreshing(false);
        Loading.getInstance(this).hide();
        if (req.getData() != null && req.getData().getData() != null) {
            try {
                Gson gson = new Gson();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    HomeWzBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeWzBean.class);
                    mDataList.add(itme);
                }
                articleMoreAdapter.setmDataList(mDataList);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(this).hide();
    }

    /**
     * 搜索数据
     *
     * @param s 搜索字符
     */
    List<HomeWzBean> searchData = new ArrayList<>();

    public void searchResetData(String s) {
        searchData.clear();
        //如果为null，直接使用全部数据
        if (s.equals("")) {
            searchData.addAll(mDataList);
        } else {
            //否则，匹配相应的数据
            for (int i = 0; i < mDataList.size(); i++) {
                if (mDataList.get(i).getProdAuthorName().contains(s)) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                    searchData.add(mDataList.get(i));
                }
            }
        }
        //刷新数据
        articleMoreAdapter.setmDataList(searchData);
    }

    @OnClick({R.id.back_but})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(true);
        IsTitleName(false);
        title_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    if (StringUtil.isEmpty(v.getText().toString())) {
                        ToastUtil.makeText(ArticleMoreActivity.this, "搜索内容不可以为空");
                    } else {
                        Intent intent = new Intent(ArticleMoreActivity.this, SearchArticleActivity.class);
                        intent.putExtra("searchStr", v.getText().toString());
                        startActivity(intent);
                    }
                    StringUtil.hideInput(ArticleMoreActivity.this);
                    return true;
                }
                return false;
            }
        });
    }
}
