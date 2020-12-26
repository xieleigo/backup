package io.dcloud.H5E4CA812.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.view.activity.ForgetPasswordActivity;
import io.dcloud.H5E4CA812.view.activity.RegisterActvity;
import io.dcloud.H5E4CA812.view.adapter.GJSearchPopAdapter;
import io.dcloud.H5E4CA812.view.bean.GJSearchPopItmeBean;
import io.dcloud.H5E4CA812.view.bean.PhoneCodeBean;

public class GJSearhPhotoPopWin extends PopupWindow {

    private Context mContext;

    private View view;
    @BindView(R.id.gj_search_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.finish)
    TextView finish;
    private TextView textView;
    public GJSearchPopAdapter gjSearchPopAdapter;

    public GJSearhPhotoPopWin(Context mContext, TextView textView, List<GJSearchPopItmeBean> popList) {
        this.mContext = mContext;
        this.textView = textView;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.gj_search_photo_popwin, null);
        ButterKnife.bind(this, view);
        initView();
        gjSearchPopAdapter = new GJSearchPopAdapter(mContext, popList);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        recyclerView.setAdapter(gjSearchPopAdapter);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    List<GJSearchPopItmeBean> ids = new ArrayList<>();
                    for (int i = 0; i < gjSearchPopAdapter.getmDataList().size(); i++) {
                        if (gjSearchPopAdapter.getmDataList().get(i).isSelected()) {
                            ids.add(gjSearchPopAdapter.getmDataList().get(i));
                        }
                    }
                    if (ids.size() == 0) {
                        dismiss();
                    } else {
                        listener.onClick(ids);
                    }
                }
            }
        });
    }

    private void initView() {
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyItemClickListener {
        void onClick(List<GJSearchPopItmeBean> ids);
    }
}
