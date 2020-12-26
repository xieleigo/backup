package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.bean.HomeWzBean;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

/**
 * 首页横向滑动
 */
public class HomepageFragmentHorizontaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {
    private List<HomeWzBean> mDataList;
    private Context context;

    public HomepageFragmentHorizontaAdapter(List<HomeWzBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<HomeWzBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_horizontallist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setArticleImage(context, mDataList.get(position).getProdImg(), recyclerViewHolder.adapter_horizontal_image);
        recyclerViewHolder.adapter_horizontal_info.setText(mDataList.get(position).getProdName());
        recyclerViewHolder.adapter_horizontal_name.setText(mDataList.get(position).getProdAuthorName());
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
        private TextView
                adapter_horizontal_info,
                adapter_horizontal_name;
        ImageView adapter_horizontal_image;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            adapter_horizontal_image = itemView.findViewById(R.id.adapter_horizontal_image);
            adapter_horizontal_info = itemView.findViewById(R.id.adapter_horizontal_info);
            adapter_horizontal_name = itemView.findViewById(R.id.adapter_horizontal_name);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
