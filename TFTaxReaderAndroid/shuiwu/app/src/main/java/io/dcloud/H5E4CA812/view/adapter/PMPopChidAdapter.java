package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.bean.QKTypeBean;

import java.util.List;

public class PMPopChidAdapter extends BaseAdapter {
    private List<QKTypeBean.ChildrenBean> list;
    private Context mContext;

    public PMPopChidAdapter(List<QKTypeBean.ChildrenBean> list, Context context) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pm_pop_chid, null);
        TextView name = convertView.findViewById(R.id.pm_pop_text);
        name.setText(list.get(position).getReadTypeName());
        RelativeLayout layout = convertView.findViewById(R.id.pm_pop_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.Read(list.get(position).getReadTypeID(), list.get(position).getReadTypeName());
                }
            }
        });
        return convertView;
    }

    private OnRedListener listener;

    public void setOnRedListener(OnRedListener listener) {
        this.listener = listener;
    }

    public interface OnRedListener {
        void Read(int ReadTypeID, String name);
    }
}
