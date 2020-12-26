package io.dcloud.H5E4CA812.util.addresspickerlib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import io.dcloud.H5E4CA812.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wepon on 2017/12/4.
 * 自定义仿京东地址选择器
 */

public class AddressPickerView extends RelativeLayout implements View.OnClickListener {
    // recyclerView 选中Item 的颜色
    private int defaultSelectedColor = Color.parseColor("#50AA00");
    // recyclerView 未选中Item 的颜色
    private int defaultUnSelectedColor = Color.parseColor("#262626");
    // 确定字体不可以点击时候的颜色
    private int defaultSureUnClickColor = Color.parseColor("#7F7F7F");
    // 确定字体可以点击时候的颜色
    private int defaultSureCanClickColor = Color.parseColor("#50AA00");

    private Context mContext;
    private TabLayout mTabLayout; // tabLayout
    private RecyclerView mRvList; // 显示数据的RecyclerView
    private String defaultProvince = "省份"; //显示在上面tab中的省份
    private String defaultCity = "城市"; //显示在上面tab中的城市
    private String defaultDistrict = "区县"; //显示在上面tab中的区县

    private List<Object> mNewRvData; // 用来在recyclerview显示的数据
    private List<NewAddresspickBean.ChildrenBeanX> Provinces; // 用来在recyclerview显示的数据
    private List<NewAddresspickBean.ChildrenBeanX.ChildrenBean> Citys; // 用来在recyclerview显示的数据
    private List<NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ> CountyLists; // 用来在recyclerview显示的数据
    private AddressAdapter mAdapter;   // recyclerview 的 adapter

    private NewAddresspickBean newAddresspickBean;
    private NewAddresspickBean.ChildrenBeanX mAddresSelectProvice;
    private NewAddresspickBean.ChildrenBeanX.ChildrenBean mAddressSelectCity;//选中 城市  bean
    private NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ mAddressSelectCounty;//选中 区县  bean
    private int mSelectProvicePosition = 0; //选中 省份 位置
    private int mSelectCityPosition = 0;//选中 城市  位置
    private int mSelectDistrictPosition = 0;//选中 区县  位置

    private OnAddressPickerSureListener mOnAddressPickerSureListener;
    private TextView mTvSure; //确定

    public AddressPickerView(Context context) {
        super(context);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        mContext = context;
        mNewRvData = new ArrayList<>();
        Provinces = new ArrayList<NewAddresspickBean.ChildrenBeanX>();
        Citys = new ArrayList<NewAddresspickBean.ChildrenBeanX.ChildrenBean>();
        CountyLists = new ArrayList<NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ>();

        // UI
        View rootView = inflate(mContext, R.layout.address_picker_view, this);
        // 确定
        mTvSure = rootView.findViewById(R.id.tvSure);
        mTvSure.setTextColor(defaultSureUnClickColor);
        mTvSure.setOnClickListener(this);
        // tablayout初始化
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tlTabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultProvince));
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultCity));
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultDistrict));

        mTabLayout.addOnTabSelectedListener(tabSelectedListener);
        // recyclerview adapter的绑定
        mRvList = (RecyclerView) rootView.findViewById(R.id.rvList);
        mRvList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new AddressAdapter();
        mRvList.setAdapter(mAdapter);
        // 初始化默认的本地数据  也提供了方法接收外面数据
        mRvList.post(new Runnable() {
            @Override
            public void run() {
                try {
                    initData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    private void initData() throws JSONException {

        StringBuilder jsonS = new StringBuilder();
        try {
            BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(mContext.getAssets().open("newaddress.json")));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonS.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将数据转换为对象
        JSONArray jsonArray = new JSONArray(jsonS.toString());
        List jsonArraylist = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonArraylist.add(jsonArray.get(i));
        }

        newAddresspickBean = new Gson().fromJson(jsonArraylist.get(0).toString(), NewAddresspickBean.class);

        Provinces = newAddresspickBean.getChildren();
        mNewRvData.clear();
        mNewRvData.addAll(Provinces);
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 开放给外部传入数据
     * 暂时就用这个Bean模型，如果数据不一致就需要各自根据数据来生成这个bean了
     */
    public void initData(NewAddresspickBean bean) {
        if (bean != null) {
            mAddressSelectCounty = null;
            mAddressSelectCity = null;
            mAddresSelectProvice = null;
            mTabLayout.getTabAt(0).select();

            newAddresspickBean = bean;
            Provinces = newAddresspickBean.getChildren();
            mNewRvData.clear();
            mNewRvData.addAll(Provinces);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvSure) {
            sure();
        }
    }

    //点确定
    private void sure() {
        if (mAddresSelectProvice != null &&
                mAddressSelectCity != null &&
                mAddressSelectCounty != null) {
            //   回调接口
            if (mOnAddressPickerSureListener != null) {
                mOnAddressPickerSureListener.onSureClick(newAddresspickBean.getAreaName() + " " + mAddresSelectProvice.getAreaName() + " " + mAddressSelectCity.getAreaName() + " " + mAddressSelectCounty.getAreaName() + " ",
                        newAddresspickBean.getAreaID(), mAddresSelectProvice.getAreaID(), mAddressSelectCity.getAreaID(), mAddressSelectCounty.getAreaID());
            }
        } else {
            Toast.makeText(mContext, "地址还没有选完整哦", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        newAddresspickBean = null;
    }

    /**
     * TabLayout 切换事件
     */
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mNewRvData.clear();
            switch (tab.getPosition()) {
                case 0:
                    Provinces = newAddresspickBean.getChildren();
                    mNewRvData.addAll(Provinces);
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectProvicePosition);
                    break;
                case 1:
                    // 点到城市的时候要判断有没有选择省份
                    if (mAddresSelectProvice != null) {
                        Citys = newAddresspickBean.getChildren().get(mSelectProvicePosition).getChildren();
                        mNewRvData.addAll(Citys);
                    } else {
                        Toast.makeText(mContext, "请您先选择省份", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectCityPosition);
                    break;
                case 2:
                    // 点到区的时候要判断有没有选择省份与城市
                    if (mAddresSelectProvice != null && mAddressSelectCity != null) {
                        CountyLists = newAddresspickBean.getChildren().get(mSelectProvicePosition).getChildren().get(mSelectCityPosition).getChildren();
                        if (CountyLists.size() <= 0) {
                            NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ bean = new NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ();
                            bean.setAreaName("全区");
                            bean.setAreaID(0000);
                            CountyLists.add(bean);
                        }

                        mNewRvData.addAll(CountyLists);
                    } else {
                        Toast.makeText(mContext, "请您先选择省份与城市", Toast.LENGTH_SHORT).show();
                    }
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectDistrictPosition);
                    break;
            }


        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };


    /**
     * 下面显示数据的adapter
     */
    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_text, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final int tabSelectPosition = mTabLayout.getSelectedTabPosition();
//            holder.mTitle.setText(mRvData.get(position).getN());


            holder.mTitle.setTextColor(defaultUnSelectedColor);
            // 设置选中效果的颜色
            switch (tabSelectPosition) {
                case 0:
                    if (mNewRvData.get(position) != null &&
                            mAddresSelectProvice != null && mSelectProvicePosition == position) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    holder.mTitle.setText(Provinces.get(position).getAreaName());
                    break;
                case 1:
                    holder.mTitle.setText(Citys.get(position).getAreaName());
                    if (mNewRvData.get(position) != null &&
                            mAddressSelectCity != null && mSelectCityPosition == position) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
                case 2:
                    holder.mTitle.setText(CountyLists.get(position).getAreaName());

                    if (mNewRvData.get(position) != null &&
                            mAddressSelectCounty != null && mSelectDistrictPosition == position) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
            }
            // 设置点击之后的事件
            holder.mTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击 分类别
                    switch (tabSelectPosition) {
                        case 0:
                            mSelectProvicePosition = position;
                            mAddresSelectProvice = (NewAddresspickBean.ChildrenBeanX) mNewRvData.get(position);
                            // 清空后面两个的数据
                            mAddressSelectCity = null;
                            mAddressSelectCounty = null;

                            mSelectCityPosition = 0;
                            mSelectDistrictPosition = 0;
                            mTabLayout.getTabAt(1).setText(defaultCity);
                            mTabLayout.getTabAt(2).setText(defaultDistrict);
                            // 设置这个对应的标题
                            mTabLayout.getTabAt(0).setText(mAddresSelectProvice.getAreaName());
                            // 跳到下一个选择
                            mTabLayout.getTabAt(1).select();
                            // 灰掉确定按钮
                            mTvSure.setTextColor(defaultSureUnClickColor);

                            break;
                        case 1:
                            mSelectCityPosition = position;
                            mAddressSelectCity = (NewAddresspickBean.ChildrenBeanX.ChildrenBean) mNewRvData.get(position);
                            // 清空后面一个的数据
                            mAddressSelectCounty = null;

                            mSelectDistrictPosition = 0;
                            mTabLayout.getTabAt(2).setText(defaultDistrict);
                            // 设置这个对应的标题
                            mTabLayout.getTabAt(1).setText(mAddressSelectCity.getAreaName());
                            // 跳到下一个选择
                            mTabLayout.getTabAt(2).select();
                            // 灰掉确定按钮
                            mTvSure.setTextColor(defaultSureUnClickColor);

                            break;
                        case 2:
                            mSelectDistrictPosition = position;
                            mAddressSelectCounty = (NewAddresspickBean.ChildrenBeanX.ChildrenBean.ChildrenBeanZ) mNewRvData.get(position);
                            // 没了，选完了，这个时候可以点确定了
                            mTabLayout.getTabAt(2).setText(mAddressSelectCounty.getAreaName());
                            notifyDataSetChanged();
                            // 确定按钮变亮
                            mTvSure.setVisibility(VISIBLE);
//                            mTvSure.setTextColor(defaultSureCanClickColor);
                            sure();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNewRvData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.itemTvTitle);
            }

        }
    }


    /**
     * 点确定回调这个接口
     */
    public interface OnAddressPickerSureListener {
        void onSureClick(String address, int CountryId, int provinceCode, int cityCode, int districtCode);
    }

    public void setOnAddressPickerSure(OnAddressPickerSureListener listener) {
        this.mOnAddressPickerSureListener = listener;
    }


}
