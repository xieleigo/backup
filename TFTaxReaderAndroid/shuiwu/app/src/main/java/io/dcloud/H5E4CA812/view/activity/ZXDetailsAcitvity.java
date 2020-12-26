package io.dcloud.H5E4CA812.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.DrawableGetter;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.DownloadUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.zxing.QRCodeDecoder;
import io.dcloud.H5E4CA812.view.bean.ZXDetailsBean;


public class ZXDetailsAcitvity extends BaseActivity {
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.qikan_detail_scrollview)
    ScrollView qikan_detail_scrollview;
    @BindView(R.id.buttgrop)
    LinearLayout buttgrop;
    @BindView(R.id.layout_title)
    RelativeLayout layout_title;

    int newsId = 0;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qikan_wanzang_detail);
        ButterKnife.bind(this);
        initTitle();
        this.context = ZXDetailsAcitvity.this;
        newsId = getIntent().getIntExtra("News_ID", 0);
        Map map = new HashMap();
        map.put("NewsID", newsId);
        execApi(ApiType.GetArticleNewsDeteil, map);


//        WindowManager wm = (WindowManager) this
//                .getSystemService(Context.WINDOW_SERVICE);
//        AllPingmuW = wm.getDefaultDisplay().getWidth();
//        AllPingmuH = wm.getDefaultDisplay().getHeight();
//        allHeight = AllPingmuH - layout_title.getHeight() - buttgrop.getHeight() - 350;
//        qikan_detail_scrollview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return true;
//            }
//        });
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("资讯详情");
    }

    int pageNum = 0;
    int AllPingmuH;
    int AllPingmuW;
    int allHeight;

    @OnClick({R.id.back_but, R.id.shangyiye
            , R.id.nextpage, R.id.homepage, R.id.lastpage})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
            case R.id.shangyiye:

                pageNum--;
                if (pageNum < 0) {
                    pageNum = 0;
                }
                alpha(view);
                qikan_detail_scrollview.scrollTo(0, allHeight * pageNum);
                break;
            case R.id.nextpage:
                if (allHeight * pageNum > textView.getHeight()) {
                    return;
                }
                alpha(view);
                pageNum++;
                qikan_detail_scrollview.scrollTo(0, allHeight * pageNum);
                break;
            case R.id.homepage:
                alpha(view);
                pageNum = 0;
                qikan_detail_scrollview.scrollTo(0, allHeight * pageNum);
                break;
            case R.id.lastpage:
                alpha(view);
                qikan_detail_scrollview.scrollTo(0, allHeight + textView.getHeight());
                break;
            case R.id.layout_back_but:
                this.onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    File file;
    String fileNamestr;
    String path = "/sdcard/shuiwuyuedu/files";

    @Override
    public void onResponsed(Request req) {
        if (req.getData().getData() != null) {
            Gson gson = new Gson();
            ZXDetailsBean zxDetailsBean = gson.fromJson(gson.toJson(req.getData().getData()), ZXDetailsBean.class);
//            initHtmlText(zxDetailsBean.getNews_Content_HTML());
            RichText.initCacheDir(this);

            RichText.from(zxDetailsBean.getNews_Content_HTML()).bind(this)
                    .size(0, 0)
                    .imageLongClick(new OnImageLongClickListener() {
                        @Override
                        public boolean imageLongClicked(List<String> imageUrls, int position) {

                            StringUtil.isExist("/sdcard/shuiwuyuedu");
                            StringUtil.isExist(path);
                            if (!imageUrls.get(position).contains("http")) {
                                return false;
                            }
                            String fileName[] = imageUrls.get(position).split("/");
                            fileNamestr = fileName[fileName.length - 1];
                            file = new File(path + "/" + fileNamestr);
                            if (file.exists()) {
                                shibeiEWM();
                                return false;
                            } else {
                                DownloadUtil.get().download(imageUrls.get(position), path, fileNamestr, new DownloadUtil.OnDownloadListener() {
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
                            return true;
                        }
                    })
                    .imageClick(new OnImageClickListener() {
                        @Override
                        public void imageClicked(List<String> imageUrls, int position) {
                            Intent intent = new Intent(ZXDetailsAcitvity.this, ImageActivity.class);
                            intent.putExtra("list", StringUtil.ListToString(imageUrls));
                            intent.putExtra("postion", position);
                            startActivity(intent);
                        }
                    })
                    .scaleType(ImageHolder.ScaleType.fit_xy) // 图片缩放方式
                    .into(textView);

//            RichText
//                    .from(text) // 数据源
//                    .type(RichText.TYPE_MARKDOWN) // 数据格式,不设置默认是Html,使用fromMarkdown的默认是Markdown格式
//                    .autoFix(true) // 是否自动修复，默认true
//                    .autoPlay(true) // gif图片是否自动播放
//                    .showBorder(true) // 是否显示图片边框
//                    .borderColor(Color.RED) // 图片边框颜色
//                    .borderSize(10) // 边框尺寸
//                    .borderRadius(50) // 图片边框圆角弧度
//                    .scaleType(ImageHolder.ScaleType.FIT_CENTER) // 图片缩放方式
//                    .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
//                    .fix(imageFixCallback) // 设置自定义修复图片宽高
//                    .fixLink(linkFixCallback) // 设置链接自定义回调
//                    .noImage(true) // 不显示并且不加载图片
//                    .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效）
//            true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
//                    .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
//                    .imageClick(onImageClickListener) // 设置图片点击回调
//                    .imageLongClick(onImageLongClickListener) // 设置图片长按回调
//                    .urlClick(onURLClickListener) // 设置链接点击回调
//                    .urlLongClick(onUrlLongClickListener) // 设置链接长按回调
//                    .placeHolder(placeHolder) // 设置加载中显示的占位图
//                    .error(errorImage) // 设置加载失败的错误图
//                    .cache(Cache.ALL) // 缓存类型，默认为Cache.ALL（缓存图片和图片大小信息和文本样式信息）
//                    .imageGetter(yourImageGetter) // 设置图片加载器，默认为DefaultImageGetter，使用okhttp实现
//                    .imageDownloader(yourImageDownloader) // 设置DefaultImageGetter的图片下载器
//                    .bind(tag) // 绑定richText对象到某个object上，方便后面的清理
//                    .done(callback) // 解析完成回调
//                    .into(textView); // 设置目标TextView
        }

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

    /**
     * 淡入淡出动画方法
     *
     * @param v
     */
    public void alpha(View v) {
        // 创建透明度动画，第一个参数是开始的透明度，第二个参数是要转换到的透明度
        AlphaAnimation alphaAni = new AlphaAnimation(0.2f, 1);

        //设置动画执行的时间，单位是毫秒
        alphaAni.setDuration(1500);

        // 设置动画结束后停止在哪个状态（true表示动画完成后的状态）
        // alphaAni.setFillAfter(true);

        // true动画结束后回到开始状态
        // alphaAni.setFillBefore(true);

        // 设置动画重复次数
        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
        alphaAni.setRepeatCount(0);

        // 设置动画模式（Animation.REVERSE设置循环反转播放动画,Animation.RESTART每次都从头开始）
        alphaAni.setRepeatMode(Animation.REVERSE);

        // 启动动画
        qikan_detail_scrollview.startAnimation(alphaAni);
    }

}
