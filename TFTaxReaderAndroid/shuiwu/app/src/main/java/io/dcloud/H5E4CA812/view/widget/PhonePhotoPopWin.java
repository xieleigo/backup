package io.dcloud.H5E4CA812.view.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.view.activity.RegisterActvity;
import io.dcloud.H5E4CA812.view.bean.PhoneCodeBean;
import com.google.gson.Gson;

import io.dcloud.H5E4CA812.view.activity.ForgetPasswordActivity;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.LogUtil;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhonePhotoPopWin extends PopupWindow {

    private Context mContext;

    private View view;
    private int mSelectPosition;
    private int defaultSelectedColor = Color.parseColor("#50AA00");
    private int defaultUnSelectedColor = Color.parseColor("#262626");
    private PhonePhotoPopWin.AddressAdapter mAdapter;   // recyclerview 的 adapter
    @BindView(R.id.phone_code_recycler_view)
    RecyclerView recyclerView;
    private TextView textView;
    private int type;

    public PhonePhotoPopWin(Context mContext, TextView textView, int postion, int type) {
        this.mContext = mContext;
        this.textView = textView;
        this.mSelectPosition = postion;
        this.type = type;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.phone_photo_popwin, null);
        ButterKnife.bind(this, view);

        readPhoneCode();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PhonePhotoPopWin.AddressAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.smoothScrollToPosition(mSelectPosition);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

    List<PhoneCodeBean> phoneList;


    private void readPhoneCode() {
        StringBuilder jsonS = new StringBuilder();
        try {
            BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(mContext.getAssets().open("phone_area_code_jsondata.json")));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonS.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        phoneList = new ArrayList<>();
        List<String> phoneList1 = new ArrayList<>();
        // 将数据转换为对象
        try {
            JSONArray jsonArray = new JSONArray(jsonS.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                phoneList1.add(jsonArray.get(i).toString());
            }
            for (int i = 0; i < phoneList1.size(); i++) {
                PhoneCodeBean phoneCodeBean = new PhoneCodeBean();
                phoneCodeBean = new Gson().fromJson(phoneList1.get(i), PhoneCodeBean.class);
                phoneList.add(phoneCodeBean);
            }
            LogUtil.i("PhonePhotoPopWin", phoneList.toString());
        } catch (Exception e) {
            LogUtil.i("PhonePhotoPopWin", e.getMessage());
        }
    }

    /**
     * 下面显示数据的adapter
     */
    class AddressAdapter extends RecyclerView.Adapter<PhonePhotoPopWin.AddressAdapter.ViewHolder> {

        @Override
        public PhonePhotoPopWin.AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhonePhotoPopWin.AddressAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.phone_photo_popwin_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final PhonePhotoPopWin.AddressAdapter.ViewHolder holder, final int position) {

            holder.phone_photo_code_text.setText(phoneList.get(position).getCode());
            holder.mTitle.setText(phoneList.get(position).getCn());
            holder.mTitle.setTextColor(defaultUnSelectedColor);
            holder.phone_photo_code_text.setTextColor(defaultUnSelectedColor);
            if (mSelectPosition == position) {
                holder.mTitle.setTextColor(defaultSelectedColor);
                holder.phone_photo_code_text.setTextColor(defaultSelectedColor);
            }

            // 设置点击之后的事件
            holder.phone_photo_code_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 1) {
                        RegisterActvity.RegisterActvityPostion = position;
                    } else {
                        ForgetPasswordActivity.ForgetPasswordActivityPostion = position;
                    }
                    mSelectPosition = position;

                    textView.setText(phoneList.get(position).getCode());
                    LogUtil.i("position", "position:" + position);
                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return phoneList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle, phone_photo_code_text;
            LinearLayout phone_photo_code_layout;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.phone_photo_text);
                phone_photo_code_text = (TextView) itemView.findViewById(R.id.phone_photo_code_text);
                phone_photo_code_layout = (LinearLayout) itemView.findViewById(R.id.phone_photo_code_layout);
            }

        }
    }

}
