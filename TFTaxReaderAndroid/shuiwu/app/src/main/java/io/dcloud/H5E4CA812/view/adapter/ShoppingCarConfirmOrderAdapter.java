package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.bean.ShoppingCarDataNewBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车订单详情适配器
 */

public class ShoppingCarConfirmOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShoppingCarDataNewBean> mDataList;
    Context context;
    TextView shopping_order_num;
    TextView tvTotalPrice;

    private int ordernum = 0;
    private double total_price;


    public ShoppingCarConfirmOrderAdapter(List<ShoppingCarDataNewBean> dataList, Context context,
                                          TextView shopping_order_num, TextView tvTotalPrice) {
        this.mDataList = dataList;
        this.context = context;
        this.shopping_order_num = shopping_order_num;
        this.tvTotalPrice = tvTotalPrice;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_title, parent, false);

        return new RecyclerViewHolder(view);
    }

    public void setmDataList(List<ShoppingCarDataNewBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.iv_select.setVisibility(View.GONE);
        recyclerViewHolder.tvTitle.setText(mDataList.get(position).getAddDate());
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });

        ChildAdapter childAdapter = (ChildAdapter) ((RecyclerViewHolder) holder).rcvChild.getAdapter();
        //适配器复用
        if (childAdapter == null) {
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
            manager.setAutoMeasureEnabled(true);
            ((RecyclerViewHolder) holder).rcvChild.setLayoutManager(manager);
            ((RecyclerViewHolder) holder).rcvChild.setAdapter(new ChildAdapter(mDataList.get(position).getData(), position));
        } else {
            childAdapter.setData(mDataList.get(position).getData()); //重新设置数据
            childAdapter.notifyDataSetChanged();
        }

        total_price = 0.0;
        tvTotalPrice.setText("¥0.00");
        for (int i = 0; i < mDataList.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = mDataList.get(i).getData();
            for (int y = 0; y < goods.size(); y++) {
                ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                boolean isSelect = goodsBean.getSelect();
                if (isSelect) {
                    String num = goodsBean.getProductCount() + "";
                    String price = goodsBean.getProduct().getProdPrice() + "";

                    double v = Double.parseDouble(num);
                    double v1 = Double.parseDouble(price);

                    total_price = total_price + v * v1;

                    //让Double类型完整显示，不用科学计数法显示大写字母E
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    tvTotalPrice.setText("¥" + decimalFormat.format(total_price));
                }
            }
        }
        shopping_order_num.setText("共" + ordernum + "件");
        ordernum = 0;
        for (int i = 0; i < mDataList.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = mDataList.get(i).getData();
            for (int y = 0; y < goods.size(); y++) {
                ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                String num = goodsBean.getProductCount() + "";
                ordernum = Integer.parseInt(num) + ordernum;
                shopping_order_num.setText("共" + ordernum + "件");
            }
        }

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public RecyclerView rcvChild;
        public ImageView iv_select;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_store_name);
            rcvChild = (RecyclerView) itemView.findViewById(R.id.shopping_order_title_recycler);
        }
    }

    public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {
        public List<ShoppingCarDataNewBean.DataBean> childList;
        public int parentIndex;

        public ChildAdapter(List<ShoppingCarDataNewBean.DataBean> childList, int parentIndex) {
            this.childList = childList;
            this.parentIndex = parentIndex;
        }

        @Override
        public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_car_confirm_order_child, parent, false);
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildViewHolder holder, final int position) {
            ShoppingCarDataNewBean.DataBean childBean = childList.get(position);
            holder.tvName.setText(childBean.getProduct().getProdName());
            ImageViewUtil.setImage(context, childBean.getProduct().getProdImg(), holder.ivPhoto);
            if (childBean.getProduct().getProdType() == 30) {
                holder.tv_num.setText(childBean.getProduct().getProdYear() + "年 期刊（年度套餐）");
            } else if (childBean.getProduct().getProdType() == 10) {
                holder.tv_num.setText(childBean.getProduct().getProdYear() + "年" + childBean.getProduct().getProdIssue() + "期");
            } else {
                holder.tv_num.setVisibility(View.GONE);
            }
            if (position == childList.size() - 1) {
                holder.view.setVisibility(View.GONE);
                holder.viewLast.setVisibility(View.VISIBLE);
            } else {
                holder.view.setVisibility(View.VISIBLE);
                holder.viewLast.setVisibility(View.GONE);
            }
            //商品名称
            String goods_name = childBean.getProduct().getProdName();
            //商品价格
            String goods_price = childBean.getProduct().getProdPrice() + "";
            //商品数量
            String goods_num = childBean.getProductCount() + "";
            //商品ID
            final String goods_id = childBean.getCartItemID() + "";
            //加号的点击事件
            holder.ivEditAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //模拟加号操作
                    String num = goods_num + "";
                    Integer integer = Integer.valueOf(num);
                    integer++;
                    childBean.setProductCount(integer);
                    ShoppingCarConfirmOrderAdapter.this.notifyDataSetChanged();

                    /**
                     * 实际开发中，通过回调请求后台接口实现数量的加减
                     */
                    if (mChangeCountListener != null) {
                        mChangeCountListener.onChangeCount(goods_id, integer);
                    }
                }
            });
            //减号的点击事件
            holder.ivEditSubtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //模拟减号操作
                    String num = goods_num + "";
                    Integer integer = Integer.valueOf(num);
                    if (integer > 1) {
                        integer--;
                        childBean.setProductCount(integer);

                        /**
                         * 实际开发中，通过回调请求后台接口实现数量的加减
                         */
                        if (mChangeCountListener != null) {
                            mChangeCountListener.onChangeCount(goods_id, integer);
                        }
                    } else {
                        Toast.makeText(context, "商品不能再减少了", Toast.LENGTH_LONG).show();
                    }
                    ShoppingCarConfirmOrderAdapter.this.notifyDataSetChanged();
                }
            });

            if (goods_name != null) {
                holder.tvName.setText(goods_name);
            } else {
                holder.tvName.setText("");
            }
            if (goods_price != null) {
                holder.tvPriceValue.setText(goods_price);
            } else {
                holder.tvPriceValue.setText("");
            }
            if (goods_num != null) {
                holder.tvEditBuyNumber.setText(goods_num);
            } else {
                holder.tvEditBuyNumber.setText("");
            }

        }


        @Override
        public int getItemCount() {
            return childList == null ? 0 : childList.size();
        }

        public void setData(List<ShoppingCarDataNewBean.DataBean> childList) {
            this.childList = childList;
        }

        public class ChildViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv_photo)
            ImageView ivPhoto;
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_num)
            TextView tv_num;
            @BindView(R.id.tv_price_key)
            TextView tvPriceKey;
            @BindView(R.id.tv_price_value)
            TextView tvPriceValue;
            @BindView(R.id.iv_edit_subtract)
            ImageView ivEditSubtract;
            @BindView(R.id.tv_edit_buy_number)
            TextView tvEditBuyNumber;
            @BindView(R.id.iv_edit_add)
            ImageView ivEditAdd;
            @BindView(R.id.view)
            View view;
            @BindView(R.id.view_last)
            View viewLast;
            ChildViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    //修改商品数量的回调
    public interface OnChangeCountListener {
        void onChangeCount(String goods_id);
    }

    public void setOnChangeCountListener(ShoppingCarAdapter.OnChangeCountListener listener) {
        mChangeCountListener = listener;
    }

    private ShoppingCarAdapter.OnChangeCountListener mChangeCountListener;

}
