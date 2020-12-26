package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.view.bean.GJSearchPopItmeBean;
import io.dcloud.H5E4CA812.view.bean.QKTypeBean;

/**
 * 高级搜索刊名
 */

public class GJSearchPopAdapter extends RecyclerBaseAdatpter {
    private List<GJSearchPopItmeBean> mDataList;
    private Context context;

    public GJSearchPopAdapter(Context context, List<GJSearchPopItmeBean> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<GJSearchPopItmeBean> mDataList) {
        this.mDataList = mDataList;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gj_search, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.gj_search_pop_text.setText(mDataList.get(position).getPubName());
        if (mDataList.get(position).isSelected()) {
            recyclerViewHolder.is_select.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.is_select.setVisibility(View.GONE);
        }
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.get(position).setSelected(!mDataList.get(position).isSelected());
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<GJSearchPopItmeBean> getmDataList() {
        return mDataList;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView gj_search_pop_text, is_select;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            gj_search_pop_text = itemView.findViewById(R.id.gj_search_pop_text);
            is_select = itemView.findViewById(R.id.is_select);
        }

    }


}
