package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.constant.bean.ItmeHomepagerJournalDetailsReadAdpterListbean;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.view.activity.QiKanDetailsAcitvity;

import java.util.List;

public class ItmeHomePagerJournalDetailsReadListAdapter extends RecyclerBaseAdatpter {
    private List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean.ArticleListBean> list;
    private Context mContext;

    public ItmeHomePagerJournalDetailsReadListAdapter(List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean.ArticleListBean> list, Context context) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_homepager_journal_details_read_list_adapter, parent, false);
        return new ItmeHomePagerJournalDetailsReadListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItmeHomePagerJournalDetailsReadListAdapter.RecyclerViewHolder recyclerViewHolder = (ItmeHomePagerJournalDetailsReadListAdapter.RecyclerViewHolder) holder;
        recyclerViewHolder.name.setText(list.get(position).getArticleTitle());
        recyclerViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Read(list.get(position));
                }
            }
        });
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout layout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itme_homepager_journal_details_read_list_adapter_name);
            layout = itemView.findViewById(R.id.itme_homepager_journal_details_read_list_adapter_layout);

        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;

    }


    private OnRedListener listener;

    public void setOnRedListener(OnRedListener listener) {
        this.listener = listener;
    }

    public interface OnRedListener {
        void Read(ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean.ArticleListBean articleListBean);
    }
}
