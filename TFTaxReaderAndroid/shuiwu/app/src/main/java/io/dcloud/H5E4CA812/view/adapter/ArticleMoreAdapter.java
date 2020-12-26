package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.bean.HomeWzBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class ArticleMoreAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<HomeWzBean> mDataList;
    private Context context;

    public void setmDataList(List<HomeWzBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public ArticleMoreAdapter(List<HomeWzBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_article_more, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.name_itme_collectfagment_articlefragment.setText(mDataList.get(position).getProdName());
        recyclerViewHolder.author_itme_collectfagment_articlefragment.setText(mDataList.get(position).getProdAuthorName());
        recyclerViewHolder.author_type_itme_collectfagment_articlefragment.setText(mDataList.get(position).getReadTypeName());
        String num;
        if (mDataList.get(position).getProdIssue() < 10) {
            num = mDataList.get(position).getProdYear() + "年" + "0" + mDataList.get(position).getProdIssue() + "期";
        } else {
            num = mDataList.get(position).getProdYear() + "年" + mDataList.get(position).getProdIssue() + "期";
        }
        recyclerViewHolder.author_num_itme_collectfagment_articlefragment.setText(num);
        recyclerViewHolder.num_itme_collectfagment_articlefragment.setText((position+1)+". ");
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
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public void onOpen(Object tag) {

    }

    @Override
    public void onClose(Object tag) {

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView
                num_itme_collectfagment_articlefragment,
                name_itme_collectfagment_articlefragment,
                author_itme_collectfagment_articlefragment,
                author_type_itme_collectfagment_articlefragment,
                author_num_itme_collectfagment_articlefragment;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            num_itme_collectfagment_articlefragment = itemView.findViewById(R.id.num_itme_collectfagment_articlefragment);
            name_itme_collectfagment_articlefragment = itemView.findViewById(R.id.name_itme_collectfagment_articlefragment);
            author_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_itme_collectfagment_articlefragment);
            author_type_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_type_itme_collectfagment_articlefragment);
            author_num_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_num_itme_collectfagment_articlefragment);

        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
