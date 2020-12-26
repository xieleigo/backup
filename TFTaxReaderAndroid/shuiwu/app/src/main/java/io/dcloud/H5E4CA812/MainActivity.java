package io.dcloud.H5E4CA812;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import dcloud.next.easynavigation.view.EasyNavigationBar;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.ShareDataLocal;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.view.activity.LoginActivity;
import io.dcloud.H5E4CA812.view.fragment.CollectFragment;
import io.dcloud.H5E4CA812.view.fragment.HomePageFragment;
import io.dcloud.H5E4CA812.view.fragment.MyFragment;
import io.dcloud.H5E4CA812.view.fragment.ShoppingCartFragmen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    //
    private String[] tabText = {"首页",
//            "分类",
            "收藏", "购物车", "我的"};
    //未选中icon
    private int[] normalIcon = {R.drawable.homepage01,
//            R.drawable.classification01,
            R.drawable.collect01,
            R.drawable.shopping_cart01,
            R.drawable.my02};
    //选中时icon
    private int[] selectIcon = {R.drawable.homepage02,
//            R.drawable.classification02,
            R.drawable.collect02,
            R.drawable.shopping_cart02,
            R.drawable.my01};

    private List fragments = new ArrayList<>();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


//        new Loading(this).show();
        fragments.add(new HomePageFragment());
//        fragments.add(new ClassificationFragment());
        fragments.add(new CollectFragment());
        fragments.add(new ShoppingCartFragmen());
        fragments.add(new MyFragment());
//
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(false)
                .build();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 0);
            }
        }

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }

        checkPermission();


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            //TODO
        }

        if (StringUtil.isEmpty(GlobleApplication.getInstance().authorization) && ShareDataLocal.getInstance(this).getBoolean("IsLogin")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }
}
