package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.bean.QKTypeBean;

import java.util.List;

/**
 * 最新期刊更更多搜索弹出框
 */

public class PMPopAdapter extends RecyclerBaseAdatpter {
    private List<QKTypeBean> mDataList;
    private List<QKTypeBean.ChildrenBean> mDataChidList;
    private Context context;

    public PMPopAdapter(Context context, List<QKTypeBean> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<QKTypeBean> mDataList) {
        this.mDataList = mDataList;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pm_pop, parent, false);
        return new RecyclerViewHolder(view);
    }

    PMPopChidAdapter chidAdapter;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        mDataChidList = mDataList.get(position).getChildren();
        chidAdapter = new PMPopChidAdapter(mDataChidList, context);
        chidAdapter.setOnRedListener(new PMPopChidAdapter.OnRedListener() {
            @Override
            public void Read(int ReadTypeID, String name) {
                if (listener != null) {
                    listener.onClick(ReadTypeID, name);
                }
            }
        });
        recyclerViewHolder.receiving_pop_chid.setAdapter(chidAdapter);
        setPullLvHeight(recyclerViewHolder.receiving_pop_chid);
        recyclerViewHolder.receiving_pop_chid.setDivider(null);

        recyclerViewHolder.pm_pop_text.setText(mDataList.get(position).getReadTypeName());
        recyclerViewHolder.pm_pop_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(mDataList.get(position).getReadTypeID(), mDataList.get(position).getReadTypeName());
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView pm_pop_text;
        private RelativeLayout pm_pop_layout;
        private ListView receiving_pop_chid;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            pm_pop_text = itemView.findViewById(R.id.pm_pop_text);
            pm_pop_layout = itemView.findViewById(R.id.pm_pop_layout);
            receiving_pop_chid = itemView.findViewById(R.id.pop_chid_listview);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyItemClickListener {
        void onClick(int ReadTypeID, String name);
    }


}
