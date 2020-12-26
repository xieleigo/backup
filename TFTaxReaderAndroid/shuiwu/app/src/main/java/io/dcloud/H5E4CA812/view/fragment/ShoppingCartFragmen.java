package io.dcloud.H5E4CA812.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.dcloud.H5E4CA812.base.BaseFragment;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.ShoppingCarAdapter;
import io.dcloud.H5E4CA812.view.bean.CarItme;
import io.dcloud.H5E4CA812.view.bean.ShoppingCarDataNewBean;
import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import com.google.gson.Gson;

import io.dcloud.H5E4CA812.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车
 */
public class ShoppingCartFragmen extends BaseFragment {
    @BindView(R.id.elv_shopping_car)
    ExpandableListView elvShoppingCar;
    @BindView(R.id.iv_select_all)
    ImageView ivSelectAll;
    @BindView(R.id.ll_select_all)
    LinearLayout llSelectAll;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.rl_total_price)
    RelativeLayout rlTotalPrice;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.iv_no_contant)
    ImageView ivNoContant;
    @BindView(R.id.rl_no_contant)
    RelativeLayout rlNoContant;
    List<ShoppingCarDataNewBean> newdatas;
    private Context context;
    private ShoppingCarAdapter shoppingCarAdapter;
    int pageindex = 1;
    private int Isnotif = 0;//是否是刷新

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, view);
        initTitle();
        context = getActivity();
        initExpandableListView();

        return view;
    }

    private void initTitle() {
        IsBackBut(false);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("购物车");
        IsEditBut(true);
    }


    boolean isEdit = false;//判断是否是编辑

    @OnClick(R.id.title_edit)
    void click(View view) {
        switch (view.getId()) {
            case R.id.title_edit:
                String edit = title_edit.getText().toString().trim();
                if (edit.equals("编辑")) {
                    isEdit = true;
                    title_edit.setText("完成");
                    rlTotalPrice.setVisibility(View.GONE);
                    btnOrder.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);
                    btnCollection.setVisibility(View.VISIBLE);
                } else if (edit.equals("完成")) {
                    isEdit = false;
                    title_edit.setText("编辑");
                    rlTotalPrice.setVisibility(View.VISIBLE);
                    btnOrder.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                    btnCollection.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (IsHint) {
            initData();
        }
    }

    boolean IsHint = false;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        IsHint = isVisibleToUser;
        if (isVisibleToUser) {
            initData();
        }
    }


    /**
     * 初始化数据
     */
    private void initData() {
        newdatas = new ArrayList<>();
        Map map = new HashMap();
        map.put("PageIndex", pageindex);
        map.put("PageSize", "999999");
        execApi(ApiType.GetCartInfo, map);
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        if (req.getErrorMsg().equals("authorization参数不能为空")) {
            StringUtil.showLoginDialog(getActivity());
        }
    }

    Gson gson = new Gson();

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            try {
                if (req.getData().getUrl().contains("/Cart/Info")) {//购物车详情
                    newdatas.clear();
                    JSONArray jsonArray = new JSONArray(gson.toJson(req.getData().getData()));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ShoppingCarDataNewBean itme = gson.fromJson(jsonArray.get(i).toString(), ShoppingCarDataNewBean.class);
                        newdatas.add(itme);
                    }
                }
                if (req.getData().getUrl().contains("/Cart/UpdateNumber") || req.getData().getUrl().contains("/Cart/Delete")
                        || req.getData().getUrl().contains("/Favor/BatchAdd")) {//更改商品详情||删除商品||收藏商品
                    if (req.getData().getRet()) {
                        if (!req.getData().getMsg().equals("更新成功")) {
                            ToastUtil.makeText(getContext(), req.getData().getMsg());
                        }

                    }
                }

            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(getActivity());
            }
        }
        initExpandableListViewData(newdatas);
    }


    /**
     * 初始化ExpandableListView
     * 创建数据适配器adapter，并进行初始化操作
     */
    private void initExpandableListView() {
        shoppingCarAdapter = new ShoppingCarAdapter(context, llSelectAll, ivSelectAll, btnOrder, btnDelete, btnCollection, rlTotalPrice, tvTotalPrice);
        elvShoppingCar.setAdapter(shoppingCarAdapter);

        elvShoppingCar.setOnScrollListener(new ExpandableListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    //如果垂直滑动，则需要关闭已经打开的layout
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
                    SwipeLayoutManager.getInstance().clearCurrentLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

        });
        //删除的回调
        shoppingCarAdapter.setOnDeleteListener(new ShoppingCarAdapter.OnDeleteListener() {
            @Override
            public void onDelete() {
                initDelete();
            }
        });
        //单个删除的回调
        shoppingCarAdapter.setOnDeletecheListener(new ShoppingCarAdapter.OnDeleteListenerche() {
            @Override
            public void onDeleteche(int groupPosition, int childPosition) {
                initDeleteItme(groupPosition, childPosition);
            }
        });
        //收藏的回调
        shoppingCarAdapter.setOnCollectionListener(new ShoppingCarAdapter.OnCollectionListener() {
            @Override
            public void onCollection() {
                initCollection();
                /**
                 * 实际开发中，在此请求删除接口，删除成功后，
                 * 通过initExpandableListViewData（）方法刷新购物车数据。
                 * 注：通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
                 *                  GoodsBean的isSelect属性，判断商品是否被选中，
                 *                  （true为选中，false为未选中）
                 */
            }
        });

        //修改商品数量的回调
        shoppingCarAdapter.setOnChangeCountListener(new ShoppingCarAdapter.OnChangeCountListener() {
            @Override
            public void onChangeCount(String goods_id, int num) {
                Map map = new HashMap();
                map.put("CartItemID", goods_id);
                map.put("Number", num);
                execApi(ApiType.GetCartUpdateNumber, map);
                /**
                 * 实际开发中，在此请求修改商品数量的接口，商品数量修改成功后，
                 * 通过initExpandableListViewData（）方法刷新购物车数据。
                 */
            }
        });
    }

    /**
     * 初始化ExpandableListView的数据
     * 并在数据刷新时，页面保持当前位置
     *
     * @param newdatas 购物车的数据
     */
    private void initExpandableListViewData(List<ShoppingCarDataNewBean> newdatas) {
        if (newdatas != null && newdatas.size() > 0) {
            //刷新数据时，保持当前位置
            shoppingCarAdapter.setData(newdatas);
            //使所有组展开
            for (int i = 0; i < shoppingCarAdapter.getGroupCount(); i++) {
                elvShoppingCar.expandGroup(i);
            }

            shoppingCarAdapter.notifyDataSetChanged();
//            使组点击无效果
            elvShoppingCar.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return true;
                }
            });
            title_edit.setVisibility(View.VISIBLE);
            title_edit.setText("编辑");
            rlNoContant.setVisibility(View.GONE);
            elvShoppingCar.setVisibility(View.VISIBLE);
            rl.setVisibility(View.VISIBLE);
            rlTotalPrice.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
            btnCollection.setVisibility(View.GONE);
        } else {
            title_edit.setVisibility(View.GONE);
            rlNoContant.setVisibility(View.VISIBLE);
            elvShoppingCar.setVisibility(View.GONE);
            rl.setVisibility(View.GONE);
        }
        if (isEdit) {
            title_edit.setText("完成");
            rlTotalPrice.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            btnCollection.setVisibility(View.VISIBLE);
        } else {
            title_edit.setText("编辑");
            rlTotalPrice.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
            btnCollection.setVisibility(View.GONE);
        }
    }

    /**
     * 判断是否要弹出删除的dialog
     * 通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
     * GoodsBean的isSelect属性，判断商品是否被选中，
     */
    List<CarItme> carItmes = new ArrayList<>();
    List<ScItme> scItmes = new ArrayList<>();

    private void initDelete() {
        //判断是否有店铺或商品被选中
        //true为有，则需要刷新数据；反之，则不需要；
        boolean hasSelect = false;
        //创建临时的List，用于存储没有被选中的购物车数据
        carItmes.clear();
        List<ShoppingCarDataNewBean> datasTemp = new ArrayList<>();
        for (int i = 0; i < newdatas.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = newdatas.get(i).getData();

            boolean isSelect_shop = newdatas.get(i).getSelect_shop();

            if (isSelect_shop) {
                hasSelect = true;
                for (int q = 0; q < goods.size(); q++) {
                    carItmes.add(new CarItme(newdatas.get(i).getData().get(q).getCartItemID()));
                }
                //跳出本次循环，继续下次循环。
                continue;
            } else {
                datasTemp.add(newdatas.get(i).clone());
                datasTemp.get(datasTemp.size() - 1).setData(new ArrayList<ShoppingCarDataNewBean.DataBean>());
            }

            for (int y = 0; y < goods.size(); y++) {
                ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                boolean isSelect = goodsBean.getSelect();

                if (isSelect) {
                    hasSelect = true;
                    carItmes.add(new CarItme(newdatas.get(i).getData().get(y).getCartItemID()));
                } else {
                    datasTemp.get(datasTemp.size() - 1).getData().add(goodsBean);
                }
            }
        }

        if (hasSelect) {
            showDeleteDialog(datasTemp);
        } else {
            ToastUtil.makeText(context, "请选择要删除的商品");
        }
    }

    private void initDeleteItme(int groupPosition, int childPosition) {
        SwipeLayoutManager.getInstance().closeCurrentLayout();
        SwipeLayoutManager.getInstance().clearCurrentLayout();
        if (childPosition >= 0) {
            newdatas.get(groupPosition).getData().remove(childPosition);
            if (newdatas.get(groupPosition).getData().size() == 0) {
                newdatas.remove(groupPosition);
            }
        }
        initExpandableListViewData(newdatas);
    }


    /**
     * 展示删除的dialog（可以自定义弹窗，不用删除即可）
     *
     * @param datasTemp
     */
    private void showDeleteDialog(final List<ShoppingCarDataNewBean> datasTemp) {
        View view = View.inflate(context, R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_message.setText("确定要删除商品吗？");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();

                execApiList(ApiType.GetCartUpdateDelete, gson.toJson(carItmes));

                newdatas = datasTemp;
                initExpandableListViewData(newdatas);
            }
        });
        //取消
        tv_logout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
            }
        });
    }

    /**
     * 判断是否要弹出删除的dialog
     * 通过bean类中的DatasBean的isSelect_shop属性，判断店铺是否被选中；
     * GoodsBean的isSelect属性，判断商品是否被选中，
     */
    private void initCollection() {
        //判断是否有店铺或商品被选中
        //true为有，则需要刷新数据；反之，则不需要；
        boolean hasSelect = false;
        //创建临时的List，用于存储没有被选中的购物车数据
        scItmes.clear();
        List<ShoppingCarDataNewBean> datasTemp = new ArrayList<>();
        for (int i = 0; i < newdatas.size(); i++) {
            List<ShoppingCarDataNewBean.DataBean> goods = newdatas.get(i).getData();

            boolean isSelect_shop = newdatas.get(i).getSelect_shop();

            if (isSelect_shop) {
                hasSelect = true;
                for (int q = 0; q < goods.size(); q++) {
                    scItmes.add(new ScItme(newdatas.get(i).getData().get(q).getProduct().getProdID()));
                }
                //跳出本次循环，继续下次循环。
                continue;
            } else {
                datasTemp.add(newdatas.get(i).clone());
                datasTemp.get(datasTemp.size() - 1).setData(new ArrayList<ShoppingCarDataNewBean.DataBean>());
            }

            for (int y = 0; y < goods.size(); y++) {
                ShoppingCarDataNewBean.DataBean goodsBean = goods.get(y);
                boolean isSelect = goodsBean.getSelect();

                if (isSelect) {
                    hasSelect = true;
                    scItmes.add(new ScItme(newdatas.get(i).getData().get(y).getProduct().getProdID()));
                } else {
                    datasTemp.get(datasTemp.size() - 1).getData().add(goodsBean);
                }
            }
        }

        if (hasSelect) {
            showCollectionDialog(scItmes);
            LogUtil.i("carItmes", scItmes.toString());
        } else {
            ToastUtil.makeText(context, "请选择要收藏的商品");
        }
    }


    /**
     * 展示收藏的dialog（可以自定义弹窗，不用删除即可）
     *
     * @param datasTemp
     */
    private void showCollectionDialog(final List<ScItme> datasTemp) {
        View view = View.inflate(context, R.layout.dialog_two_btn, null);
        final RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_message.setText("确定要收藏商品吗？");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
                execApiList(ApiType.GetFavorBatchAdd, gson.toJson(datasTemp));

            }
        });
        //取消
        tv_logout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
            }
        });
    }

    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };


    public class ScItme {
        int ReadSourceID;
        int ReadParentID;
        String ReadFavoriteType;

        public ScItme(int ReadSourceID) {
            this.ReadSourceID = ReadSourceID;
            this.ReadParentID = ReadSourceID;
            this.ReadFavoriteType = "20";
        }

        public int getReadSourceID() {
            return ReadSourceID;
        }

        public void setReadSourceID(int readSourceID) {
            ReadSourceID = readSourceID;
        }

        public int getReadParentID() {
            return ReadParentID;
        }

        public void setReadParentID(int readParentID) {
            ReadParentID = readParentID;
        }

        public String getReadFavoriteType() {
            return ReadFavoriteType;
        }

        public void setReadFavoriteType(String readFavoriteType) {
            ReadFavoriteType = readFavoriteType;
        }

        @Override
        public String toString() {
            return "ScItme{" +
                    "ReadSourceID=" + ReadSourceID +
                    ", ReadParentID=" + ReadParentID +
                    ", ReadFavoriteType='" + ReadFavoriteType + '\'' +
                    '}';
        }
    }
}
