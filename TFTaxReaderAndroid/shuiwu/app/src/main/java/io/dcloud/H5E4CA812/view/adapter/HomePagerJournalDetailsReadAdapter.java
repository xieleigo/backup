package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.constant.bean.ItmeHomepagerJournalDetailsReadAdpterListbean;
import io.dcloud.H5E4CA812.view.activity.QiKanDetailsAcitvity;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class HomePagerJournalDetailsReadAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean> mDataList;
    private Context mContext;

    public HomePagerJournalDetailsReadAdapter(List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean> dataList, Context mContext) {
        this.mDataList = dataList;
        this.mContext = mContext;
    }

    public void setmDataList(List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hoempager_journal_details_read_adpter, parent, false);
        return new HomePagerJournalDetailsReadAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HomePagerJournalDetailsReadAdapter.RecyclerViewHolder recyclerViewHolder = (HomePagerJournalDetailsReadAdapter.RecyclerViewHolder) holder;
        recyclerViewHolder.itme_hjdra_name.setText(mDataList.get(position).getPubIssueCataName());
        ItmeHomePagerJournalDetailsReadListAdapter adapter = new ItmeHomePagerJournalDetailsReadListAdapter(mDataList.get(position).getArticleList(), mContext);
        adapter.setOnRedListener(new ItmeHomePagerJournalDetailsReadListAdapter.OnRedListener() {
            @Override
            public void Read(ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean.ArticleListBean articleListBean) {
                Intent intent = new Intent(mContext, QiKanDetailsAcitvity.class);
                intent.putExtra("ArticleID", articleListBean.getArticleID());
                mContext.startActivity(intent);
            }
        });
//        recyclerViewHolder.itme_hjdra_list.setDivider(null);
//        recyclerViewHolder.itme_hjdra_list.setAdapter(adapter);
//        setPullLvHeight(recyclerViewHolder.itme_hjdra_list);
        recyclerViewHolder.itme_hjdra_list.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewHolder.itme_hjdra_list.setAdapter(adapter);

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
        if (mDataList != null && mDataList.size() > 0) {
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
        private TextView itme_hjdra_name;
        private RecyclerView itme_hjdra_list;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            itme_hjdra_name = itemView.findViewById(R.id.itme_hjdra_name);
            itme_hjdra_list = itemView.findViewById(R.id.itme_hjdra_list);
        }
    }

    //解决RecyclerView嵌套ListView只显示一行数据
    private void setPullLvHeight(ListView listView) {
        int totalHeight = 0;
        ListAdapter adapter = listView.getAdapter();
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}