package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.view.activity.ShoppingCartConfirmOrderActivity;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.bean.ShoppingCarDataNewBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购物车的adapter
 * 因为使用的是ExpandableListView，所以继承BaseExpandableListAdapter
 */
public class ShoppingCarAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final LinearLayout llSelectAll;
    private final ImageView ivSelectAll;
    private final Button btnOrder;
    private final Button btnDelete;
    private final RelativeLayout rlTotalPrice;
    private final TextView tvTotalPrice;
    //    private List<ShoppingCarDataBean.DatasBean> data;
    private List<ShoppingCarDataNewBean> data;
    private boolean isSelectAll = false;
    private double total_price;
    private final Button btnCollection;

    public ShoppingCarAdapter(Context context, LinearLayout llSelectAll,
                              ImageView ivSelectAll, Button btnOrder, Button btnDelete, Button btnCollection,
                              RelativeLayout rlTotalPrice, TextView tvTotalPrice) {
        this.context = context;
        this.llSelectAll = llSelectAll;
        this.ivSelectAll = ivSelectAll;
        this.btnOrder = btnOrder;
        this.btnDelete = btnDelete;
        this.btnCollection = btnCollection;
        this.rlTotalPrice = rlTotalPrice;
        this.tvTotalPrice = tvTotalPrice;
    }

    /**
     * 自定义设置数据方法；
     * 通过notifyDataSetChanged()刷新数据，可保持当前位置
     *
     * @param data 需要刷新的数据
     */
    public void setData(List<ShoppingCarDataNewBean> data) {
        this.data = data;

    }

    @Override
    public int getGroupCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_shopping_title, null);

            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        final ShoppingCarDataNewBean datasBean = data.get(groupPosition);
        //店铺ID
//        String store_id = datasBean.getStore_id();
        //店铺名称
        String store_name = datasBean.getAddDate();

        if (store_name != null) {
            groupViewHolder.tvStoreName.setText(store_name);
        } else {
            groupViewHolder.tvStoreName.setText("");
        }

        //店铺内的商品都选中的时候，店铺的也要选中
        for (int i = 0; i < datasBean.getData().size(); i++) {
            ShoppingCarDataNewBean.DataBean goodsBean = datasBean.getData().get(i);
            boolean isSelect = goodsBean.getSelect();
            if (isSelect) {
                datasBean.setSelect_shop(true);
            } else {
                datasBean.setSelect_shop(false);
                break;
            }
        }

        //因为set之后要重新get，所以这一块代码要放到一起执行
        //店铺是否在购物车中被选中
        final boolean isSelect_shop = datasBean.getSelect_shop();
        if (isSelect_shop) {
            groupViewHolder.ivSelect.setImageResource(R.drawable.button_red_mark);
        } else {
            groupViewHolder.ivSelect.setImageResource(R.drawable.button_white);
        }

        //店铺选择框的点击事件
        groupViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasBean.setSelect_shop(!isSelect_shop);

                List<ShoppingCarDataNewBean.DataBean> goods = datasBean.getData();
                for (int i = 0; i < goods.size(); i++) {
                    ShoppingCarDataNewBean.DataBean goodsBean = goods.get(i);
                    goodsBean.setSelect(!isSelect_shop);
                }
                notifyDataSetChanged();
            }
        });

        //当所有的选择框都是选中的时候，全选也要选中
        w:
        for (int i = 0; i < data.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = data.get(i).getData();
            for (int y = 0; y < goods.size(); y++) {
                ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                boolean isSelect = goodsBean.getSelect();
                if (isSelect) {
                    isSelectAll = true;
                } else {
                    isSelectAll = false;
                    break w;//根据标记，跳出嵌套循环
                }
            }
        }
        if (isSelectAll) {
            ivSelectAll.setBackgroundResource(R.drawable.button_red_mark);
        } else {
            ivSelectAll.setBackgroundResource(R.drawable.button_white);
        }

        //全选的点击事件
        llSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = !isSelectAll;

                if (isSelectAll) {
                    for (int i = 0; i < data.size(); i++) {
                        List<ShoppingCarDataNewBean.DataBean> goods = data.get(i).getData();
                        for (int y = 0; y < goods.size(); y++) {
                            ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                            goodsBean.setSelect(true);
                        }
                    }
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        List<ShoppingCarDataNewBean.DataBean> goods = data.get(i).getData();
                        for (int y = 0; y < goods.size(); y++) {
                            ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                            goodsBean.setSelect(false);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        //合计的计算
        total_price = 0.0;
        tvTotalPrice.setText("¥0.00");
        for (int i = 0; i < data.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = data.get(i).getData();
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

        //去结算的点击事件
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建临时的List，用于存储有商品被选中的店铺
                List<ShoppingCarDataNewBean> tempStores = new ArrayList<>();

                for (int i = 0; i < data.size(); i++) {
                    //店铺中是否有商品被选中
                    boolean hasGoodsSelect = false;
                    //创建临时的List，用于存储被选中的商品
                    List<ShoppingCarDataNewBean.DataBean> tempGoods = new ArrayList<>();
                    ShoppingCarDataNewBean storesBean = data.get(i);
                    List<ShoppingCarDataNewBean.DataBean> goods = storesBean.getData();
                    for (int y = 0; y < goods.size(); y++) {
                        ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                        boolean isSelect = goodsBean.getSelect();
                        if (isSelect) {
                            hasGoodsSelect = true;
                            tempGoods.add(goodsBean);
                        }
                    }
                    if (hasGoodsSelect) {
                        ShoppingCarDataNewBean storeBean = new ShoppingCarDataNewBean();
                        storeBean.setAddDate(storesBean.getAddDate());
                        storeBean.setData(tempGoods);
                        tempStores.add(storeBean);
                    }
                }

                if (tempStores != null && tempStores.size() > 0) {//如果有被选中的
                    /**
                     * 实际开发中，如果有被选中的商品，
                     * 则跳转到确认订单页面，完成后续订单流程。
                     */
                    Gson gson = new Gson();
                    String tempStorjson = gson.toJson(tempStores);
                    Intent intent = new Intent(context, ShoppingCartConfirmOrderActivity.class);
                    intent.putExtra("tempStoress", tempStorjson);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "请选择要购买的商品", Toast.LENGTH_LONG).show();
                }
            }
        });

        //删除的点击事件
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实际开发中，通过回调请求后台接口实现删除操作
                 */
                if (mDeleteListener != null) {
                    mDeleteListener.onDelete();
                }
            }
        });
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 实际开发中，通过回调请求后台接口实现收藏操作
                 */
                if (mCollectionListener != null) {
                    mCollectionListener.onCollection();
                }
            }
        });
        return convertView;
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.ll)
        LinearLayout ll;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //------------------------------------------------------------------------------------------------
    @Override
    public int getChildrenCount(int groupPosition) {
        if (data.get(groupPosition).getData() != null && data.get(groupPosition).getData().size() > 0) {
            return data.get(groupPosition).getData().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getData().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_shopping_chid, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final ShoppingCarDataNewBean datasBean = data.get(groupPosition);
        //店铺ID
//        String store_id = datasBean.getStore_id();
        //店铺名称
        String store_name = datasBean.getAddDate();
        //店铺是否在购物车中被选中
        final boolean isSelect_shop = datasBean.getSelect_shop();
        final ShoppingCarDataNewBean.DataBean goodsBean = datasBean.getData().get(childPosition);
        //商品图片
        String goods_image = goodsBean.getProduct().getProdImg();
        //商品ID
        final String goods_id = goodsBean.getCartItemID() + "";
        //商品名称
        String goods_name = goodsBean.getProduct().getProdName();
        //商品价格
        String goods_price = goodsBean.getProduct().getProdPrice() + "";
        //商品数量
        String goods_num = goodsBean.getProductCount() + "";
        //商品是否被选中
        final boolean isSelect = goodsBean.getSelect();

        if (goodsBean.getProduct().getProdType() == 30) {
            childViewHolder.kanshu.setText(goodsBean.getProduct().getProdYear() + "年 期刊（年度套餐）");
        } else if (goodsBean.getProduct().getProdType() == 10) {
            childViewHolder.kanshu.setText(goodsBean.getProduct().getProdYear() + "年" + goodsBean.getProduct().getProdIssue() + "期");
        } else {
            childViewHolder.kanshu.setVisibility(View.GONE);
        }
        ImageViewUtil.setImage(context, goods_image, childViewHolder.ivPhoto);
        if (goods_name != null) {
            childViewHolder.tvName.setText(goods_name);
        } else {
            childViewHolder.tvName.setText("");
        }
        if (goods_price != null) {
            childViewHolder.tvPriceValue.setText(goods_price);
        } else {
            childViewHolder.tvPriceValue.setText("");
        }
        if (goods_num != null) {
            childViewHolder.tvEditBuyNumber.setText(goods_num);
        } else {
            childViewHolder.tvEditBuyNumber.setText("");
        }

        //商品是否被选中
        if (isSelect) {
            childViewHolder.ivSelect.setImageResource(R.drawable.button_red_mark);
        } else {
            childViewHolder.ivSelect.setImageResource(R.drawable.button_white);
        }

        //商品选择框的点击事件
        childViewHolder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsBean.setSelect(!isSelect);
                if (!isSelect == false) {
                    datasBean.setSelect_shop(false);
                }
                notifyDataSetChanged();
            }
        });

        childViewHolder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteListenerche.onDeleteche(groupPosition, childPosition);
            }
        });

        //加号的点击事件
        childViewHolder.ivEditAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟加号操作
                String num = goodsBean.getProductCount() + "";
                Integer integer = Integer.valueOf(num);
                integer++;
                goodsBean.setProductCount(integer);
                notifyDataSetChanged();

                /**
                 * 实际开发中，通过回调请求后台接口实现数量的加减
                 */
                if (mChangeCountListener != null) {
                    mChangeCountListener.onChangeCount(goods_id, integer);
                }
            }
        });
        //减号的点击事件
        childViewHolder.ivEditSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //模拟减号操作
                String num = goodsBean.getProductCount() + "";
                Integer integer = Integer.valueOf(num);
                if (integer > 1) {
                    integer--;
                    goodsBean.setProductCount(integer);

                    /**
                     * 实际开发中，通过回调请求后台接口实现数量的加减
                     */
                    if (mChangeCountListener != null) {
                        mChangeCountListener.onChangeCount(goods_id, integer);
                    }
                } else {
                    Toast.makeText(context, "商品不能再减少了", Toast.LENGTH_LONG).show();
                }
                notifyDataSetChanged();
            }
        });

        if (childPosition == data.get(groupPosition).getData().size() - 1) {
            childViewHolder.view.setVisibility(View.GONE);
            childViewHolder.viewLast.setVisibility(View.VISIBLE);
        } else {
            childViewHolder.view.setVisibility(View.VISIBLE);
            childViewHolder.viewLast.setVisibility(View.GONE);
        }


        return convertView;
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
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
        @BindView(R.id.tv_delete)
        View tv_delete;
        @BindView(R.id.kanshu)
        TextView kanshu;


        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //-----------------------------------------------------------------------------------------------

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //删除的回调
    public interface OnDeleteListener {
        void onDelete();
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    private OnDeleteListener mDeleteListener;

    //删除的子回调
    public interface OnDeleteListenerche {
        void onDeleteche(int groupPosition, int childPosition);
    }

    public void setOnDeletecheListener(OnDeleteListenerche listenerche) {
        mDeleteListenerche = listenerche;
    }

    private OnDeleteListenerche mDeleteListenerche;

    //收藏的回调
    public interface OnCollectionListener {
        void onCollection();
    }

    public void setOnCollectionListener(OnCollectionListener listener) {
        mCollectionListener = listener;
    }

    private OnCollectionListener mCollectionListener;


    //修改商品数量的回调
    public interface OnChangeCountListener {
        void onChangeCount(String goods_id, int num);
    }

    public void setOnChangeCountListener(OnChangeCountListener listener) {
        mChangeCountListener = listener;
    }

    private OnChangeCountListener mChangeCountListener;
}
