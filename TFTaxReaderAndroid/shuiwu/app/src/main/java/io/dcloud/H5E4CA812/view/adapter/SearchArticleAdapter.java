package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.view.activity.QiKanDetailsAcitvity;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.bean.SearchArticleBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class SearchArticleAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<SearchArticleBean.DataBean> mDataList;
    private Context context;

    public void setmDataList(List<SearchArticleBean.DataBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public SearchArticleAdapter(List<SearchArticleBean.DataBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_search_article, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.name_itme_collectfagment_articlefragment.setText(mDataList.get(position).getArticleTitle());
        if (mDataList.get(position).getArticleAuthorName() == null) {
            recyclerViewHolder.author_itme_collectfagment_articlefragment.setText("");
        } else {
            recyclerViewHolder.author_itme_collectfagment_articlefragment.setText(mDataList.get(position).getArticleAuthorName() + "");

        }
        recyclerViewHolder.author_type_itme_collectfagment_articlefragment.setText(mDataList.get(position).getPubIssueName());
        String num;
        if (mDataList.get(position).getPubIssueNum() < 10) {
            num = mDataList.get(position).getPubIssueYear() + "年" + "0" + mDataList.get(position).getPubIssueNum() + "期";
        } else {
            num = mDataList.get(position).getPubIssueYear() + "年" + mDataList.get(position).getPubIssueNum() + "期";
        }
        recyclerViewHolder.author_num_itme_collectfagment_articlefragment.setText(num);
        recyclerViewHolder.num_itme_collectfagment_articlefragment.setText((position + 1) + ". ");
        recyclerViewHolder.articlefragment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QiKanDetailsAcitvity.class);
                intent.putExtra("ArticleID", Integer.valueOf(mDataList.get(position).getArticleID()));
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
        private RelativeLayout articlefragment_layout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            articlefragment_layout = itemView.findViewById(R.id.articlefragment_layout);
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
