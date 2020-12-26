package io.dcloud.H5E4CA812.view.activity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.DownloadUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.zxing.QRCodeDecoder;
import io.dcloud.H5E4CA812.view.bean.ArticleDetailBean;
import io.dcloud.H5E4CA812.view.widget.DingyuePhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;
import io.dcloud.H5E4CA812.view.widget.SharePhotoPopWin;


public class QiKanDetailsAcitvity extends BaseActivity {
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.author_gs)
    TextView author_gs;
    @BindView(R.id.zhaiyao)
    TextView zhaiyao;
    @BindView(R.id.ydqw_but)
    Button ydqw_but;
    @BindView(R.id.dy_button_image)
    ImageView dy_button_image;
    @BindView(R.id.scimage)
    ImageView scimage;
    @BindView(R.id.down)
    ImageView down;
    @BindView(R.id.qikan_detail_scrollview)
    ScrollView qikan_detail_scrollview;
    @BindView(R.id.qikan_detail_title)
    RelativeLayout qikan_detail_title;
    @BindView(R.id.buttgrop)
    LinearLayout buttgrop;
    @BindView(R.id.shangyipain)
    Button shangyipain;
    @BindView(R.id.xiayipian)
    Button xiayipian;
    @BindView(R.id.huidaodingbu)
    Button huidaodingbu;
    @BindView(R.id.fenxiang)
    ImageView fenxiang;

    int ArticleID = 0;
    Map map = new HashMap();
    int pageNum = 0;
    int allHeight;
    int goumai = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qikan_detail);
        ButterKnife.bind(this);
        initTitle();
        ArticleID = getIntent().getIntExtra("ArticleID", 0);
        map.clear();
        map.put("ArticleID", ArticleID);
        execApi(ApiType.GetArticleDetail, map);

        map.clear();
        map.put("ArticleID", ArticleID);
        execApi(ApiType.GetArticleGetHtmlContent, map);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!StringUtil.isEmpty(GlobleApplication.getInstance().authorization)) {
            map.clear();
            map.put("ArticleID", ArticleID);
            execApi(ApiType.GetArticleGetHtmlContent, map);
        }
    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("期刊阅读详情");
    }


    @OnClick({R.id.layout_back_but, R.id.ydqw_but, R.id.dy_button_image, R.id.scimage, R.id.down, R.id.shangyiye
            , R.id.nextpage, R.id.homepage, R.id.lastpage, R.id.shangyipain, R.id.xiayipian, R.id.huidaodingbu, R.id.fenxiang})
    void click(View view) {
        switch (view.getId()) {
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
            case R.id.ydqw_but:
                if (StringUtil.isEmpty(GlobleApplication.authorization)) {
                    StringUtil.showLoginDialog(QiKanDetailsAcitvity.this);
                    return;
                }
                map.clear();
                map.put("ArticleID", ArticleID);
                execApi(ApiType.GetArticleGetHtmlContent, map);
                goumai++;
                break;
            case R.id.dy_button_image:

                if (StringUtil.isEmpty(GlobleApplication.authorization)) {
                    StringUtil.showLoginDialog(QiKanDetailsAcitvity.this);
                    return;
                }
                if (isSubscribe) {
                    showdingyuetishiDialog(QiKanDetailsAcitvity.this);
                    return;
                }

                break;
            case R.id.scimage:
                if (StringUtil.isEmpty(GlobleApplication.authorization)) {
                    StringUtil.showLoginDialog(QiKanDetailsAcitvity.this);
                    return;
                }
                map.clear();
                if (articleDetailBean.isIsFavorite()) {
                    map.put("ReadSourceID", articleDetailBean.getArticleID());
                    map.put("ReadFavoriteType", "30");
                    execApi(ApiType.GetFavorCancel, map);
                } else {
                    map.put("ReadSourceID", articleDetailBean.getArticleID());
                    map.put("ReadParentID", articleDetailBean.getPubIssueID());
                    map.put("ReadFavoriteType", "30");
                    execApi(ApiType.GetFavorAdd, map);
                }
                break;
            case R.id.down:

                break;
            case R.id.shangyipain:
                map.clear();
                map.put("ArticleID", ArticleID);
                map.put("SkipIndex", "-1");
                execApi(ApiType.GetArticleDetail, map);
                execApi(ApiType.GetArticleGetHtmlContent, map);
                qikan_detail_scrollview.fullScroll(View.FOCUS_UP);
                break;
            case R.id.xiayipian:
                map.clear();
                map.put("ArticleID", ArticleID);
                map.put("SkipIndex", "1");
                execApi(ApiType.GetArticleDetail, map);
                execApi(ApiType.GetArticleGetHtmlContent, map);
                qikan_detail_scrollview.fullScroll(View.FOCUS_UP);
                break;
            case R.id.huidaodingbu:
                qikan_detail_scrollview.fullScroll(View.FOCUS_UP);
                break;
            case R.id.fenxiang:
                SharePhotoPopWin sharePhotoPopWin = new SharePhotoPopWin(QiKanDetailsAcitvity.this, articleDetailBean.getSourceUrl(), articleDetailBean.getArticleTitle(), articleDetailBean.getArticleAbstract());
                sharePhotoPopWin.showAtLocation(findViewById(R.id.author), Gravity.CENTER, 0, 0);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    Gson gson = new Gson();
    ArticleDetailBean articleDetailBean;

    @Override
    public void onResponsed(Request req) {

    }

    File file;
    String fileNamestr;
    String path = "/sdcard/shuiwuyuedu/files";
    boolean isSubscribe = false;

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            if (req.getData().getUrl().contains("/Article/Detail")) {
                articleDetailBean = gson.fromJson(gson.toJson(req.getData().getData()), ArticleDetailBean.class);
                title_text.setText(articleDetailBean.getArticleTitle());
                num.setText("《" + articleDetailBean.getPubName() + "》" + articleDetailBean.getPubIssueYear()
                        + "年 第" + articleDetailBean.getPubIssueNum() + "期");
                author.setText("作者：" + articleDetailBean.getArticleAuthorName());
                author_gs.setText("作者单位：" + articleDetailBean.getArticleUnitName());
                zhaiyao.setText("   " + articleDetailBean.getArticleAbstract());
                if (articleDetailBean.isIsFavorite()) {
                    scimage.setBackground(this.getDrawable(R.drawable.qksc02));
                } else {
                    scimage.setBackground(this.getDrawable(R.drawable.qksc01));
                }
                ArticleID = articleDetailBean.getArticleID();
                if (articleDetailBean.isAllowBuy()) {
                    dy_button_image.setVisibility(View.VISIBLE);
                } else {
                    dy_button_image.setVisibility(View.GONE);
                }
            }
            if (req.getData().getUrl().contains("/Favor/Cancel")) {
                if (req.getData().getMsg().equals("取消收藏成功")) {
                    articleDetailBean.setIsFavorite(false);
                    scimage.setBackground(this.getDrawable(R.drawable.qksc01));
                }
            }

            if (req.getData().getUrl().contains("/Article/GetHtmlContent")) {
                if (req.getData().getRet()) {
                    isSubscribe = true;
                    RichText.initCacheDir(this);
                    RichText.from(req.getData().getData().toString()).bind(this)
                            .showBorder(false)
                            .imageLongClick(new OnImageLongClickListener() {
                                @Override
                                public boolean imageLongClicked(List<String> imageUrls, int position) {
                                    showLoginDialog(QiKanDetailsAcitvity.this, imageUrls, position);
                                    return true;
                                }
                            })
                            .imageClick(new OnImageClickListener() {
                                @Override
                                public void imageClicked(List<String> imageUrls, int position) {
                                    Intent intent = new Intent(QiKanDetailsAcitvity.this, ImageActivity.class);
                                    intent.putExtra("list", StringUtil.ListToString(imageUrls));
                                    intent.putExtra("postion", position);
                                    startActivity(intent);
                                }
                            })
                            .into(textView);
                    ydqw_but.setVisibility(View.GONE);
                    shangyipain.setVisibility(View.VISIBLE);
                    xiayipian.setVisibility(View.VISIBLE);
                    huidaodingbu.setVisibility(View.VISIBLE);
                }
            }
            if (req.getData().getUrl().contains("/Favor/Add")) {
                if (req.getData().getRet()) {
                    articleDetailBean.setIsFavorite(true);
                    scimage.setBackground(this.getDrawable(R.drawable.qksc02));
                    ToastUtil.makeText(this, req.getData().getMsg());
                }
            }
            if (req.getData().getUrl().contains("/Cart/Add") || req.getData().getUrl().contains("/Favor/Cancel")) {
                if (req.getData().getRet()) {
                    ToastUtil.makeText(this, req.getData().getMsg());
                }
            }
        } else {
            if (req.getErrorMsg().equals("需要购买")) {
                if (goumai > 0) {
                    showPop();
                }
            }
        }
    }


    public void showPop() {
        if (articleDetailBean != null) {
            DingyuePhotoPopWin takePhotoPopWin = new DingyuePhotoPopWin(
                    QiKanDetailsAcitvity.this,
                    articleDetailBean.getArticleID(),
                    articleDetailBean.getPubIssueID(),
                    articleDetailBean.getPubID(),
                    articleDetailBean.getPubIssueYear(),
                    articleDetailBean.getPubIssueNum(),
                    articleDetailBean.getArticleTitle(),
                    articleDetailBean.getArticlePrice() + "",
                    articleDetailBean.getPubName() + ""
            );
            takePhotoPopWin.showAtLocation(findViewById(R.id.text), Gravity.CENTER, 0, 0);
        }
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


    public void showLoginDialog(Context context, List<String> imageUrls, int position) {
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
                if (!imageUrls.get(position).contains("http")) {
                    return;
                }
                String fileName[] = imageUrls.get(position).split("/");
                fileNamestr = fileName[fileName.length - 1];
                file = new File(path + "/" + fileNamestr);
                if (file.exists()) {
                    shibeiEWM();
                    return;
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
                roundCornerDialog.dismiss();
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


    public void showdingyuetishiDialog(Context context) {
        View view = View.inflate(context, R.layout.login_go_btn, null);
        RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_logout_cancel.setVisibility(View.GONE);
        tv_message.setText("你已经拥有该文章刊阅览权限，如继续购买时，系统自动分配阅读码，阅读码可以转移给其他用户使用。");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
                showPop();
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
