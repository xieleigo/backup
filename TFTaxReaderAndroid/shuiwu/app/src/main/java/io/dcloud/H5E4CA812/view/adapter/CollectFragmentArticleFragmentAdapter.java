package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.activity.QiKanDetailsAcitvity;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.ArticleBean;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectFragmentArticleFragmentAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<ArticleBean> mDataList;
    private Context context;
    private int positions;

    public void setmDataList(List<ArticleBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    public CollectFragmentArticleFragmentAdapter(List<ArticleBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_collectfragment_articlefragment, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.num_itme_collectfagment_articlefragment.setText((position + 1) + ".");
        recyclerViewHolder.name_itme_collectfagment_articlefragment.setText(mDataList.get(position).getArticleTitle());
        recyclerViewHolder.author_itme_collectfagment_articlefragment.setText(mDataList.get(position).getArticleAuthorName() + " - ");
        recyclerViewHolder.author_type_itme_collectfagment_articlefragment.setText(mDataList.get(position).getPubIssueName() + " - ");
        recyclerViewHolder.author_num_itme_collectfagment_articlefragment.setText(mDataList.get(position).getPubIssueYear()+"年 " + mDataList.get(position).getPubIssueNum() + "期");

        recyclerViewHolder.swipeLayout.setTag(position);

        recyclerViewHolder.swipeLayout.setOnSwipeStateChangeListener(this);
        recyclerViewHolder.num_itme_collectfagment_articlefragment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QiKanDetailsAcitvity.class);
                intent.putExtra("ArticleID", Integer.valueOf(mDataList.get(position).getArticleID()));
                context.startActivity(intent);
            }
        });
        //删除
        recyclerViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positions = position;
                Loading.getInstance(context).show();
                Map map = new HashMap();
                map.put("ReadSourceID", mDataList.get(position).getArticleID());
                map.put("ReadFavoriteType", "30");
                execApi(ApiType.GetFavorCancel, map, context);
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
        private TextView tvDelete, tvZhiDing,
                num_itme_collectfagment_articlefragment,
                name_itme_collectfagment_articlefragment,
                author_itme_collectfagment_articlefragment,
                author_type_itme_collectfagment_articlefragment,
                author_num_itme_collectfagment_articlefragment;
        private SwipeLayout swipeLayout;
        private RelativeLayout num_itme_collectfagment_articlefragment_layout;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            num_itme_collectfagment_articlefragment_layout = itemView.findViewById(R.id.num_itme_collectfagment_articlefragment_layout);
            num_itme_collectfagment_articlefragment = itemView.findViewById(R.id.num_itme_collectfagment_articlefragment);
            name_itme_collectfagment_articlefragment = itemView.findViewById(R.id.name_itme_collectfagment_articlefragment);
            author_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_itme_collectfagment_articlefragment);
            author_type_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_type_itme_collectfagment_articlefragment);
            author_num_itme_collectfagment_articlefragment = itemView.findViewById(R.id.author_num_itme_collectfagment_articlefragment);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvZhiDing = itemView.findViewById(R.id.tv_zhiding);
            swipeLayout = itemView.findViewById(R.id.swipelayout);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnRecyItemClickListener {
        void onClick(View view, int position);
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        Loading.getInstance(context).hide();
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        SwipeLayoutManager.getInstance().clearCurrentLayout();
        if (req.getData() != null) {
            if (req.getData().getRet()) {
                mDataList.remove(mDataList.get(positions));
                notifyDataSetChanged();
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, req.getData().getMsg(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(context);
            }
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(context).hide();
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        if (req.getData() != null) {
            Toast.makeText(context, req.getData().getMsg(), Toast.LENGTH_SHORT).show();
        }
        if (req.getErrorMsg().equals("authorization参数不能为空")) {
            StringUtil.showLoginDialog(context);
        }
    }
}
