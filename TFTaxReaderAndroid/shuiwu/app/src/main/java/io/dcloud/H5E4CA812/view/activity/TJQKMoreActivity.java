package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentPHBAdapter;
import io.dcloud.H5E4CA812.view.bean.HomeBean;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.util.loading.Loading;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推荐期刊更多页面
 */
public class TJQKMoreActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recyclerview_tjqk_more)
    RecyclerView mRecyclerView;
    HomepageFragmentPHBAdapter homepageFragmentPHBAdapter;
    List<HomeBean> PHBlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tjqk_more);
        ButterKnife.bind(this);
        initTitle();
        initListView();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PHBlist.clear();
                Loading.getInstance(TJQKMoreActivity.this).show();
                execApi(ApiType.GetProductRecd, null);//排行榜
            }
        });
    }

    private void initListView() {
        Loading.getInstance(TJQKMoreActivity.this).show();
        execApi(ApiType.GetProductRecd, null);//排行榜
        PHBlist = new ArrayList<HomeBean>();

        homepageFragmentPHBAdapter = new HomepageFragmentPHBAdapter(this, PHBlist);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(homepageFragmentPHBAdapter);
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
                    HomeBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeBean.class);
                    PHBlist.add(itme);
                }
                homepageFragmentPHBAdapter.setmDataList(PHBlist);
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
    List<HomeBean> searchData = new ArrayList<>();

    public void searchResetData(String s) {
        searchData.clear();
        //如果为null，直接使用全部数据
        if (s.equals("")) {
            searchData.addAll(PHBlist);
        } else {
            //否则，匹配相应的数据
            for (int i = 0; i < PHBlist.size(); i++) {
                if (PHBlist.get(i).getProdName().contains(s)) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                    searchData.add(PHBlist.get(i));
                }
            }
        }
        //刷新数据
        homepageFragmentPHBAdapter.setmDataList(searchData);
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
                        ToastUtil.makeText(TJQKMoreActivity.this, "搜索内容不可以为空");
                    } else {
                        Intent intent = new Intent(TJQKMoreActivity.this, SearchArticleActivity.class);
                        intent.putExtra("searchStr", v.getText().toString());
                        startActivity(intent);
                    }
                    StringUtil.hideInput(TJQKMoreActivity.this);
                    return true;
                }
                return false;
            }
        });
    }
}
