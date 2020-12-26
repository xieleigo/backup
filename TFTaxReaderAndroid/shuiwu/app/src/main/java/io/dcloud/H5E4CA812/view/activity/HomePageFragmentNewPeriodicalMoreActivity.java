package io.dcloud.H5E4CA812.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentNewPeriodicalMoreAdapter;
import io.dcloud.H5E4CA812.view.bean.HomePeriodicalBean;
import io.dcloud.H5E4CA812.view.widget.down_load.LoadMoreWrapper;
import io.dcloud.H5E4CA812.view.widget.down_load.OnScrollListener;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.adapter.PMPopAdapter;
import io.dcloud.H5E4CA812.view.bean.QKTypeBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 最新期刊更多页面
 */
public class HomePageFragmentNewPeriodicalMoreActivity extends BaseActivity {
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recyclerview_homepagefragment_newperiodical_more)
    RecyclerView mRecyclerView;
    @BindView(R.id.journal_details_title_image)
    ImageView journal_details_title_image;
    @BindView(R.id.journal_details_title_name)
    TextView journal_details_title_name;


    private List<HomePeriodicalBean> mDataList;
    HomepageFragmentNewPeriodicalMoreAdapter commonAdapter;

    private int pageindex = 1;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int Isnotif = 0;//是否是刷新
    private int ReadTypeID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagefragment_newperiodicalmore);
        ButterKnife.bind(this);
        initTitle();
        initListView();
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageindex = 1;
                Isnotif = 0;
                initDate();
            }
        });
    }

    private void initListView() {
        mDataList = new ArrayList<>();
        initDate();
        commonAdapter = new HomepageFragmentNewPeriodicalMoreAdapter(mDataList, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(commonAdapter);
        mLoadMoreWrapper = new LoadMoreWrapper(commonAdapter);
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING);
                Isnotif = 1;
                initDate();
            }
        });

        commonAdapter.setOnItemClickListener(new OnRecyItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(HomePageFragmentNewPeriodicalMoreActivity.this, HomepagerJournalDetails.class);
                intent.putExtra("ReadSourceID", mDataList.get(position).getReadSourceID() + "");
                startActivity(intent);
            }
        });
    }

    private void initDate() {
//        Loading.getInstance(this).show();
        Map map = new HashMap();
        map.put("ReadType", ReadTypeID);
        map.put("PageIndex", pageindex);
        map.put("SearchType", 1);
        map.put("PageSize", 16);
        execApi(ApiType.GetPublicationGetPubIssueList, map);
        pageindex++;
    }


    @Override
    public void onResponsed(Request req) {
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
//        Loading.getInstance(this).hide();
        if (req.getData() != null) {
            if (req.getData().getData() != null) {
                try {
                    Gson gson = new Gson();
                    if (req.getData().getUrl().contains("/Publication/GetPubIssueList")) {
                        if (Isnotif == 0) {
                            mDataList.clear();
                        }
                        JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                HomePeriodicalBean itme = gson.fromJson(jsonArray.get(i).toString(), HomePeriodicalBean.class);
                                mDataList.add(itme);
                            }
                            commonAdapter.setmDataList(mDataList);
                            mLoadMoreWrapper.notifyDataSetChanged();
                        } else {
                            if (Isnotif == 1) {
                                mLoadMoreWrapper.setLoadStateNotify(mLoadMoreWrapper.LOADING_END);
                                return;
                            }
                            mDataList.clear();
                            commonAdapter.setmDataList(mDataList);
                            mLoadMoreWrapper.notifyDataSetChanged();
                        }
                    }
                    if (req.getData().getUrl().contains("/Product/ReadType")) {
                        popList.clear();
                        JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            QKTypeBean itme = gson.fromJson(jsonArray.get(i).toString(), QKTypeBean.class);
                            popList.add(itme);
                        }
                        pmPopAdapter.setmDataList(popList);
                    }
                } catch (Exception e) {

                }
            }
            mSwipeLayout.setRefreshing(false);
            Isnotif = 0;
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(HomePageFragmentNewPeriodicalMoreActivity.this);
                return;
            }

        }

    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        mSwipeLayout.setRefreshing(false);
        Loading.getInstance(this).hide();
    }

    int type = 0;

    @OnClick({R.id.back_but, R.id.journal_details_title_layout})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.journal_details_title_layout:

                initPopupWindow();
                mPW.showAsDropDown(view);
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
//                    searchResetData(v.getText().toString());
                    StringUtil.hideInput(HomePageFragmentNewPeriodicalMoreActivity.this);
                    if (StringUtil.isEmpty(v.getText().toString())) {
                        ToastUtil.makeText(HomePageFragmentNewPeriodicalMoreActivity.this, "搜索内容不可以为空");
                    } else {
                        Intent intent = new Intent(HomePageFragmentNewPeriodicalMoreActivity.this, SearchArticleActivity.class);
                        intent.putExtra("searchStr", v.getText().toString());
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    PopupWindow mPW;
    PMPopAdapter pmPopAdapter;
    List<QKTypeBean> popList = new ArrayList<>();

    /**
     * 初始化界面
     */
    private void initPopupWindow() {
        journal_details_title_image.setBackground(getResources().getDrawable(R.drawable.arrow02));
        View view = View.inflate(this, R.layout.activoty_periodica_more_pop,
                null);
        RecyclerView recyclerView = view.findViewById(R.id.pm_pop_recycler);
        popList.clear();
        execApi(ApiType.GetProductReadType, null);

        pmPopAdapter = new PMPopAdapter(this, popList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(pmPopAdapter);
        pmPopAdapter.setOnItemClickListener(new PMPopAdapter.OnRecyItemClickListener() {
            @Override
            public void onClick(int ReadTypeID, String name) {
                journal_details_title_name.setText(name);
                journal_details_title_image.setBackground(getResources().getDrawable(R.drawable.arrow01));
                HomePageFragmentNewPeriodicalMoreActivity.this.ReadTypeID = ReadTypeID;
                pageindex = 1;
                mSwipeLayout.setRefreshing(true);
                initDate();
                mPW.dismiss();
            }
        });
        // 初始化弹出窗体,设定大小
        mPW = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT - 300);

        // 设置参数
        // 设置焦点，保证里面的组件可以点击
        mPW.setFocusable(true);
        // 设置背景，好处：1、外部点击生效 2、可以播放动画
        mPW.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 透明
        mPW.setOutsideTouchable(true);

        // 为显自然，加个渐变动画
        ScaleAnimation mPopupAnimation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
        mPopupAnimation.setDuration(400);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPW.dismiss();
                return true;
            }
        });
        mPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                journal_details_title_image.setBackground(getResources().getDrawable(R.drawable.arrow01));
            }
        });
    }

}
