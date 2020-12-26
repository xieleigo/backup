package io.dcloud.H5E4CA812.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.adapter.CollectFragmentPreriodicalFragmentAdapter;
import io.dcloud.H5E4CA812.view.bean.PeriodicalBean;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收藏 期刊
 */
public class CollectFragmentPeriodicalFragment extends BaseFragment {
    @BindView(R.id.recyclerview_periodical__fragment)
    RecyclerView mRecyclerView;

    List<PeriodicalBean> mDataList;
    CollectFragmentPreriodicalFragmentAdapter commonAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect_fragment_periodical, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public void initView() {
        initListView();
    }


    void initListView() {
        Map map = new HashMap();
        map.put("ReadFavoriteType", "20");
        execApi(ApiType.GetFavorFind, map);

        mDataList = new ArrayList<>();
        commonAdapter = new CollectFragmentPreriodicalFragmentAdapter(mDataList, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(commonAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView view, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //如果垂直滑动，则需要关闭已经打开的layout
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        if (hidden) {
            initListView();
        }
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                Gson gson = new Gson();
                mDataList.clear();
                JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    PeriodicalBean itme = gson.fromJson(jsonArray.get(i).toString(), PeriodicalBean.class);
                    mDataList.add(itme);
                }
                commonAdapter.setmDataList(mDataList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(getActivity());
            }
        }
    }


}
