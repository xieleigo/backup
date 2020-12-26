package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.activity.ZXDetailsAcitvity;
import io.dcloud.H5E4CA812.view.bean.HomeZXBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

/**
 * 首页质询
 */

public class HomepageFragmentZXAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<HomeZXBean> mDataList;
    private Context context;

    public HomepageFragmentZXAdapter(Context context, List<HomeZXBean> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<HomeZXBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_hompagefragment_zx, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.zx_text.setText(mDataList.get(position).getNews_Title());
        recyclerViewHolder.zx_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZXDetailsAcitvity.class);
//                Intent intent = new Intent(context, TestActivity.class);
                intent.putExtra("News_ID", mDataList.get(position).getNews_ID());
                context.startActivity(intent);

            }
        });
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onOpen(Object tag) {

    }

    @Override
    public void onClose(Object tag) {

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView zx_title, zx_text;
        private RelativeLayout zx_layout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            zx_title = itemView.findViewById(R.id.zx_title);
            zx_text = itemView.findViewById(R.id.zx_text);
            zx_layout = itemView.findViewById(R.id.zx_layout);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
