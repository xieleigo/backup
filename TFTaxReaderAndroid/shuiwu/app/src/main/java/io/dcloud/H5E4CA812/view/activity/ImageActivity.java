package io.dcloud.H5E4CA812.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.DownloadUtil;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.zxing.QRCodeDecoder;
import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;
import io.dcloud.H5E4CA812.view.widget.photoview.PhotoView;

public class ImageActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pagenum)
    TextView pagenum;

    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_pager);
        ButterKnife.bind(this);

        sDrawables = StringUtil.StringToList(getIntent().getStringExtra("list"));
        position = getIntent().getIntExtra("postion", 0);
        pagenum.setText((position + 1) + "/" + sDrawables.size());
        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(position);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagenum.setText((position + 1) + "/" + sDrawables.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    List<Object> sDrawables = new ArrayList<>();
    File file;
    String fileNamestr;
    String path = "/sdcard/shuiwuyuedu/files";

    class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return sDrawables.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            ImageViewUtil.setArticleImage(ImageActivity.this, (String) sDrawables.get(position), photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showLoginDialog(ImageActivity.this);

                    return true;
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100:
                        shibeiEWM();
                        break;
                    case 200:
                        LogUtil.i("Message", msg.obj + "");
                        break;
                    case 300:
                        break;
                }
            }
        };

        private void shibeiEWM() {
            Bitmap bitmap = BitmapFactory.decodeFile(path + "/" + fileNamestr);
            Matrix matrix = new Matrix();
            matrix.setScale(2, 2);
            Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            decode(result, "");
        }

        private void decode(final Bitmap bitmap, final String errorTip) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github
        .com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(bitmap);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
//                    Toast.makeText(ZXDetailsAcitvity.this, errorTip, Toast.LENGTH_SHORT).show();
                    } else {
//                    Toast.makeText(ZXDetailsAcitvity.this, result, Toast.LENGTH_SHORT).show();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                        startActivity(browserIntent);
                    }
                }
            }.execute();
        }

        public void showLoginDialog(Context context) {
            View view = View.inflate(context, R.layout.login_go_btn, null);
            RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
            roundCornerDialog.show();
            roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

            TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
            TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
            TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
            tv_message.setText("是否要跳转浏览器观看？");

            //确定
            tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringUtil.isExist("/sdcard/shuiwuyuedu");
                    StringUtil.isExist(path);
                    String fileName[] = ((String) sDrawables.get(position)).split("/");
                    if (!((String) sDrawables.get(position)).contains("http")) {
                        return;
                    }
                    fileNamestr = fileName[fileName.length - 1];
                    file = new File(path + "/" + fileNamestr);
                    if (file.exists()) {
                        shibeiEWM();
                        return;
                    } else {
                        DownloadUtil.get().download((String) sDrawables.get(position), path, fileNamestr, new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(File file) {
                            }

                            @Override
                            public void onDownloading(int progress) {
                                LogUtil.i("onDownloading", progress + "");
                                if (progress == 100) {
                                    Message msgs = new Message();
                                    msgs.obj = path + "/" + fileNamestr;
                                    msgs.what = 100;
                                    handler.sendMessage(msgs);
                                }
                            }

                            @Override
                            public void onDownloadFailed(Exception e) {
                                Message msg = new Message();
                                msg.obj = e.toString();
                                msg.what = 200;
                                handler.sendMessage(msg);
                            }
                        });
                    }
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

        public DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    public void onResponsed(Request req) {

    }


}

