package io.dcloud.H5E4CA812.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.view.activity.AdvancedSearchActivity;
import io.dcloud.H5E4CA812.view.activity.SearchArticleActivity;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentPHBAdapter;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentZXZKAdapter;
import io.dcloud.H5E4CA812.view.bean.BannerImageBean;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.ArticleMoreActivity;
import io.dcloud.H5E4CA812.view.activity.HomePageFragmentNewPeriodicalMoreActivity;
import io.dcloud.H5E4CA812.view.activity.HomepagerJournalDetails;
import io.dcloud.H5E4CA812.view.activity.InquiryMoreActivity;
import io.dcloud.H5E4CA812.view.activity.QiKanDetailsAcitvity;
import io.dcloud.H5E4CA812.view.activity.TJQKMoreActivity;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentHorizontaAdapter;
import io.dcloud.H5E4CA812.view.adapter.HomepageFragmentZXAdapter;
import io.dcloud.H5E4CA812.view.adapter.ImageAdapter;
import io.dcloud.H5E4CA812.view.bean.DataBean;
import io.dcloud.H5E4CA812.view.bean.HomeBean;
import io.dcloud.H5E4CA812.view.bean.HomePeriodicalBean;
import io.dcloud.H5E4CA812.view.bean.HomeWzBean;
import io.dcloud.H5E4CA812.view.bean.HomeZXBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomePageFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.new_new_periodical_more)
    TextView new_new_periodical_more;
    //    @BindView(R.id.swipe_ly)
//    SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.horizontal_lv)
    RecyclerView horizontal_lv;
    @BindView(R.id.recyclerview_homepagefragment_phb)
    RecyclerView mRecyclerViewPHB;
    @BindView(R.id.recyclerview_homepagefragment_zxzk)
    RecyclerView recyclerview_homepagefragment_zxzk;
    @BindView(R.id.recyclerview_homepagefragment_zx)
    RecyclerView recyclerview_homepagefragment_zx;

    ImageAdapter imageAdapter;
    HomepageFragmentZXZKAdapter homepageFragmentZXZKAdapter;
    HomepageFragmentHorizontaAdapter hadapter;
    HomepageFragmentPHBAdapter homepageFragmentPHBAdapter;
    HomepageFragmentZXAdapter zxAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, view);
        initTitle();


        initDate();
        initView();
        return view;
    }

    List<DataBean> Imagelist;
    List<HomePeriodicalBean> Newestlist;
    List<HomeWzBean> TJlist;
    List<HomeBean> PHBlist;
    List<HomeZXBean> ZXlist;

    private void initView() {
        Imagelist = new ArrayList<>();
        imageAdapter = new ImageAdapter(Imagelist, getContext());
        banner.setAdapter(imageAdapter);

        Newestlist = new ArrayList<>();
        homepageFragmentZXZKAdapter = new HomepageFragmentZXZKAdapter(Newestlist, getContext());
        recyclerview_homepagefragment_zxzk.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerview_homepagefragment_zxzk.setAdapter(homepageFragmentZXZKAdapter);
        homepageFragmentZXZKAdapter.setOnItemClickListener(new OnRecyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), HomepagerJournalDetails.class);
                intent.putExtra("ReadSourceID", Newestlist.get(position).getReadSourceID() + "");
                startActivity(intent);
            }
        });

        TJlist = new ArrayList<>();
        LinearLayoutManager ms = new LinearLayoutManager(getContext());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        horizontal_lv.setLayoutManager(ms);
        hadapter = new HomepageFragmentHorizontaAdapter(TJlist, getContext());
        horizontal_lv.setAdapter(hadapter);
        hadapter.setOnItemClickListener(new OnRecyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), QiKanDetailsAcitvity.class);
                intent.putExtra("ArticleID", Integer.valueOf(TJlist.get(position).getReadSourceID()));
                startActivity(intent);
            }
        });


        homepageFragmentPHBAdapter = new HomepageFragmentPHBAdapter(getContext(), PHBlist);
        mRecyclerViewPHB.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecyclerViewPHB.setAdapter(homepageFragmentPHBAdapter);

        ZXlist = new ArrayList<>();
        zxAdapter = new HomepageFragmentZXAdapter(getContext(), ZXlist);
        recyclerview_homepagefragment_zx.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerview_homepagefragment_zx.setAdapter(zxAdapter);

        PHBlist = new ArrayList<>();
        execApi(ApiType.GetProductRotchart, null);//轮播图
        execApi(ApiType.GetProductLast, null);//最新期刊
        execApi(ApiType.GetProductRecd, null);//排行榜

        Map map = new HashMap();
        map.put("NewsID", "");
        map.put("Number", "2");
        execApi(ApiType.GetArticleGetTopNews, map);//质询
        execApi(ApiType.GetArticleRecd, null);//推荐文章
    }

    private void initDate() {
        title_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtil.isEmpty(v.getText().toString())) {
                        ToastUtil.makeText(getContext(), "搜索内容不可以为空");
                    } else {
                        Intent intent = new Intent(getContext(), SearchArticleActivity.class);
                        intent.putExtra("searchStr", v.getText().toString());
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void initTitle() {
        IsBackBut(false);
        IsTitleEdittext(true);
        IsTitleName(false);
        title_gj_search.setVisibility(View.VISIBLE);
    }

    List list;

    @OnClick({R.id.new_new_periodical_more, R.id.recommendation_query_logimage_more, R.id.recommend_list_more,
            R.id.ranking_list_more, R.id.title_gj_search})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.new_new_periodical_more:
                startActivity(new Intent(getActivity(), HomePageFragmentNewPeriodicalMoreActivity.class));
                break;
            case R.id.recommendation_query_logimage_more:
                startActivity(new Intent(getActivity(), InquiryMoreActivity.class));
                break;
            case R.id.recommend_list_more:
                startActivity(new Intent(getActivity(), ArticleMoreActivity.class));
                break;
            case R.id.ranking_list_more:
                startActivity(new Intent(getActivity(), TJQKMoreActivity.class));
                break;
            case R.id.title_gj_search:
                startActivity(new Intent(getActivity(), AdvancedSearchActivity.class));
                break;


        }
    }


    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                Gson gson = new Gson();
                if (req.getData().getUrl().contains("/Product/Rotchart")) {//轮播图
                    Imagelist.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BannerImageBean itme = gson.fromJson(jsonArray.get(i).toString(), BannerImageBean.class);
                        DataBean dataBean = new DataBean();
                        dataBean.setImageUrl(itme.getImgSRC());
                        Imagelist.add(dataBean);
                    }
                    Message msg = new Message();
                    msg.what = 100;
                    handler.sendMessage(msg);
                }
                if (req.getData().getUrl().contains("/Product/Last")) {//最新期刊
                    Newestlist.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < 2; i++) {
                        HomePeriodicalBean itme = gson.fromJson(jsonArray.get(i).toString(), HomePeriodicalBean.class);
                        Newestlist.add(itme);
                    }
                    Message msg = new Message();
                    msg.what = 200;
                    handler.sendMessage(msg);
                }
                if (req.getData().getUrl().contains("/Product/Recd")) {//推荐期刊（排行榜）
                    PHBlist.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HomeBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeBean.class);
                        PHBlist.add(itme);
                    }
                    Message msg = new Message();
                    msg.what = 300;
                    handler.sendMessage(msg);
                }
                if (req.getData().getUrl().contains("/Article/GetTopNews")) {//推荐质询
                    ZXlist.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HomeZXBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeZXBean.class);
                        ZXlist.add(itme);
                    }
                    Message msg = new Message();
                    msg.what = 400;
                    handler.sendMessage(msg);
                }
                if (req.getData().getUrl().contains("/Article/Recd")) {//推荐文章
                    TJlist.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        HomeWzBean itme = gson.fromJson(jsonArray.get(i).toString(), HomeWzBean.class);
                        TJlist.add(itme);
                    }
                    Message msg = new Message();
                    msg.what = 500;
                    handler.sendMessage(msg);
                }


            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        }
    }


    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        LogUtil.i("Request", req.getData().toString());
        if (req.getErrorMsg().equals("authorization参数不能为空")) {
            StringUtil.showLoginDialog(getActivity());
        }
    }


    private void setBanner() {
        imageAdapter.updateData(Imagelist);
        banner.setIndicator(new CircleIndicator(HomePageFragment.this.getActivity()));
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    setBanner();
                    break;
                case 200:
                    homepageFragmentZXZKAdapter.setmDataList(Newestlist);
                    break;
                case 300:
                    homepageFragmentPHBAdapter.setmDataList(PHBlist);
                    break;
                case 400:
                    zxAdapter.setmDataList(ZXlist);
                    break;
                case 500:
                    hadapter.setmDataList(TJlist);
                    break;

                default:
                    break;
            }
        }
    };

}
