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
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentZXAdapter;
import io.dcloud.H5E4CA812.view.bean.HomeZXBean;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 质询更多页面
 */
public class InquiryMoreActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recyclerview_inquiry_more)
    RecyclerView mRecyclerView;
    HomepageFragmentZXAdapter zxAdapter;
    List<HomeZXBean> ZXlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_more);
        ButterKnife.bind(this);
        initTitle();
        initListView();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ZXlist.clear();
                Loading.getInstance(InquiryMoreActivity.this).show();
                execApi(ApiType.GetArticleGetTopNews, null);//质询
            }
        });
    }

    private void initListView() {
        Map map = new HashMap();
        map.put("NewsID", "");
        map.put("Number", "2");
        Loading.getInstance(InquiryMoreActivity.this).show();
        execApi(ApiType.GetArticleGetTopNews, null);//质询
        ZXlist = new ArrayList<>();
        zxAdapter = new HomepageFragmentZXAdapter(this, ZXlist);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecyclerView.setAdapter(zxAdapter);
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
                    HomeZXBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeZXBean.class);
                    ZXlist.add(itme);
                }
                zxAdapter.setmDataList(ZXlist);
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
    List<HomeZXBean> searchData = new ArrayList<>();

    public void searchResetData(String s) {
        searchData.clear();
        //如果为null，直接使用全部数据
        if (s.equals("")) {
            searchData.addAll(ZXlist);
        } else {
            //否则，匹配相应的数据
            for (int i = 0; i < ZXlist.size(); i++) {
                if (ZXlist.get(i).getNews_Title().contains(s)) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                    searchData.add(ZXlist.get(i));
                }
            }
        }
        //刷新数据
        zxAdapter.setmDataList(searchData);
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
                        ToastUtil.makeText(InquiryMoreActivity.this, "搜索内容不可以为空");
                    } else {
                        Intent intent = new Intent(InquiryMoreActivity.this, SearchArticleActivity.class);
                        intent.putExtra("searchStr", v.getText().toString());
                        startActivity(intent);
                    }
                    StringUtil.hideInput(InquiryMoreActivity.this);
                    return true;
                }
                return false;
            }
        });
    }
}
