package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.view.activity.HomepagerJournalDetails;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.bean.PeriodicalBean;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectFragmentPreriodicalFragmentAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<PeriodicalBean> mDataList;
    private Context context;
    private int positions;

    public CollectFragmentPreriodicalFragmentAdapter(List<PeriodicalBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<PeriodicalBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_collectfragment_preriodicalfragment, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setPHBImage(context, mDataList.get(position).getPubIssueCoverPath(), recyclerViewHolder.image_itme_collectfagment_preriodicalfragment);
        recyclerViewHolder.name_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getPubIssueName());
        recyclerViewHolder.num_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getPubIssueYear() + "年 第" + mDataList.get(position).getPubIssueNum() + "期" + " 总第" + mDataList.get(position).getPubIssueSumNum() + "期");
        try {
            recyclerViewHolder.info1_itme_collectfagment_preriodicalfragment.setText(". " + mDataList.get(position).getArticleList().get(0).getArticleTitle());
            recyclerViewHolder.info2_itme_collectfagment_preriodicalfragment.setText(". " + mDataList.get(position).getArticleList().get(1).getArticleTitle());
            recyclerViewHolder.info3_itme_collectfagment_preriodicalfragment.setText(". " + mDataList.get(position).getArticleList().get(2).getArticleTitle());
            recyclerViewHolder.info4_itme_collectfagment_preriodicalfragment.setText(". " + mDataList.get(position).getArticleList().get(2).getArticleTitle());

        } catch (Exception e) {

        }
        recyclerViewHolder.preriodicalfragment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomepagerJournalDetails.class);
                intent.putExtra("ReadSourceID", mDataList.get(position).getPubIssueID() + "");
                context.startActivity(intent);
            }
        });
        recyclerViewHolder.swipeLayout.setTag(position);
        recyclerViewHolder.swipeLayout.setOnSwipeStateChangeListener(this);

        //删除
        recyclerViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading.getInstance(context).show();
                positions = position;
                Map map = new HashMap();
                map.put("ReadSourceID", mDataList.get(position).getPubIssueID());
                map.put("ReadFavoriteType", "20");
                execApi(ApiType.GetFavorCancel, map, context);
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
        private TextView tvDelete, tvZhiDing,
                name_itme_collectfagment_preriodicalfragment,
                num_itme_collectfagment_preriodicalfragment,
                info1_itme_collectfagment_preriodicalfragment,
                info2_itme_collectfagment_preriodicalfragment,
                info3_itme_collectfagment_preriodicalfragment,
                info4_itme_collectfagment_preriodicalfragment;
        private ImageView image_itme_collectfagment_preriodicalfragment;
        private SwipeLayout swipeLayout;
        private RelativeLayout preriodicalfragment_layout;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            preriodicalfragment_layout = itemView.findViewById(R.id.preriodicalfragment_layout);
            name_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.name_itme_collectfagment_preriodicalfragment);
            num_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.num_itme_collectfagment_preriodicalfragment);
            info1_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info1_itme_collectfagment_preriodicalfragment);
            info2_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info2_itme_collectfagment_preriodicalfragment);
            info3_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info3_itme_collectfagment_preriodicalfragment);
            info4_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info4_itme_collectfagment_preriodicalfragment);
            image_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.image_itme_collectfagment_preriodicalfragment);
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
        }
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(context).hide();
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        if (req != null) {
            Toast.makeText(context, req.getData().getMsg(), Toast.LENGTH_SHORT).show();
        }
    }
}
