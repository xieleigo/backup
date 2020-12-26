package io.dcloud.H5E4CA812.view.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.bean.ArticleBean;
import io.dcloud.H5E4CA812.view.bean.PeriodicalBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

/**
 * 收藏
 */
public class CollectFragment extends BaseFragment {
    @BindView(R.id.fragmet_collect_fragment)
    FrameLayout fragmet_collect;
    @BindView(R.id.but_collect_fragment_title1)
    Button but_collect_fragment_title1;
    @BindView(R.id.but_collect_fragment_title2)
    Button but_collect_fragment_title2;
    @BindView(R.id.iv_tab_bottom_img)
    ImageView iv_tab_bottom_img;

    private Fragment currentFragment = new Fragment();

    CollectFragmentPeriodicalFragment collectFragmentPeriodicalFragment;
    CollectFragmentArticleFragment collectFragmentArticleFragment;

    int widthPixel = 0;

    private boolean isQK = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(this, view);

        initTitleButton();
        initTitle();

        collectFragmentPeriodicalFragment = new CollectFragmentPeriodicalFragment();
        collectFragmentArticleFragment = new CollectFragmentArticleFragment();
        switchFragment(collectFragmentPeriodicalFragment);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.i("isVisibleToUser", isVisibleToUser + "    isVisibleToUser");
        if (isVisibleToUser) {
            collectFragmentPeriodicalFragment.initListView();
        }
    }

    private void initTitleButton() {
        but_collect_fragment_title1.setTextColor(getActivity().getResources().getColor(R.color.CollectFragment_buttont_Text));
        but_collect_fragment_title2.setTextColor(getActivity().getResources().getColor(R.color.black));
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        widthPixel = outMetrics.widthPixels;
        ViewGroup.LayoutParams para;
        para = iv_tab_bottom_img.getLayoutParams();
        para.width = widthPixel / 2;
        iv_tab_bottom_img.setLayoutParams(para);
        initImageView();
    }

    private void initTitle() {
        IsBackBut(false);
        IsTitleEdittext(true);
        IsTitleName(false);
        title_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                StringUtil.hideInput(getActivity());
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchResetData(v.getText().toString());
                }
                return false;
            }
        });
    }

    @OnClick({R.id.but_collect_fragment_title1, R.id.but_collect_fragment_title2})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.but_collect_fragment_title1:
                isQK = true;
                move(widthPixel / 2, 0);
                switchFragment(collectFragmentPeriodicalFragment);
                but_collect_fragment_title1.setTextColor(getActivity().getResources().getColor(R.color.CollectFragment_buttont_Text));
                but_collect_fragment_title2.setTextColor(getActivity().getResources().getColor(R.color.black));
                if (collectFragmentPeriodicalFragment.commonAdapter != null) {
                    searchResetData("");
                }
                title_edittext.setText("");
                SwipeLayoutManager.getInstance().closeCurrentLayout();
                SwipeLayoutManager.getInstance().clearCurrentLayout();
                break;
            case R.id.but_collect_fragment_title2:
                SwipeLayoutManager.getInstance().closeCurrentLayout();
                SwipeLayoutManager.getInstance().clearCurrentLayout();
                isQK = false;
                move(0, widthPixel / 2);
                but_collect_fragment_title2.setTextColor(getActivity().getResources().getColor(R.color.CollectFragment_buttont_Text));
                but_collect_fragment_title1.setTextColor(getActivity().getResources().getColor(R.color.black));
                switchFragment(collectFragmentArticleFragment);
                if (collectFragmentArticleFragment.commonAdapter != null) {
                    searchResetData("");
                }
                title_edittext.setText("");
                break;
        }
    }

    /**
     * 搜索数据
     *
     * @param s 搜索字符
     */
    List<ArticleBean> searchData = new ArrayList<>();
    List<PeriodicalBean> searchDatas = new ArrayList<>();

    public void searchResetData(String s) {
        searchData.clear();
        searchDatas.clear();
        //如果为null，直接使用全部数据
        if (isQK) {
            if (s.equals("")) {
                searchDatas.addAll(collectFragmentPeriodicalFragment.mDataList);
            } else {
                //否则，匹配相应的数据
                for (int i = 0; i < collectFragmentPeriodicalFragment.mDataList.size(); i++) {
                    if ((collectFragmentPeriodicalFragment.mDataList.get(i).getPubIssueName() + "").contains(s)) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                        searchDatas.add(collectFragmentPeriodicalFragment.mDataList.get(i));
                    }
                }
            }
            //刷新数据
            collectFragmentPeriodicalFragment.commonAdapter.setmDataList(searchDatas);
        } else {
            if (s.equals("")) {
                searchData.addAll(collectFragmentArticleFragment.mDataList);
            } else {
                //否则，匹配相应的数据
                for (int i = 0; i < collectFragmentArticleFragment.mDataList.size(); i++) {
                    if ((collectFragmentArticleFragment.mDataList.get(i).getArticleTitle()).contains(s)) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                        searchData.add(collectFragmentArticleFragment.mDataList.get(i));
                    }
                }
            }
            //刷新数据
            collectFragmentArticleFragment.commonAdapter.setmDataList(searchData);
        }
    }


    //动态替换fragment
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragmet_collect_fragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    /**
     * 下划线图片宽度
     */
    private int lineWidth;
    /**
     * 偏移量（手机屏幕宽度 / 选项卡总数 - 选项卡长度） / 2
     */
    private int offset = 0;
    /**
     * 选项卡总数
     */
    private static final int TAB_COUNT = 2;

    /**
     * 初始化底部下划线
     */
    private void initImageView() {
        // 获取图片宽度
        lineWidth = BitmapFactory.decodeResource(getResources(), R.drawable.zgsw).getWidth();
        // Android提供的DisplayMetrics可以很方便的获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels; // 获取分辨率宽度
        offset = (screenW / TAB_COUNT - lineWidth) / 2;  // 计算偏移值
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        // 设置下划线初始位置
        iv_tab_bottom_img.setImageMatrix(matrix);
    }

    Animation animation;

    private void move(int width1, int width) {
        animation = new TranslateAnimation(width1, width, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        // 给图片添加动画
        iv_tab_bottom_img.startAnimation(animation);
    }

}
