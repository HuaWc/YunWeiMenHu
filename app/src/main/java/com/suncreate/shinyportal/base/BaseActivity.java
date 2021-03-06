package com.suncreate.shinyportal.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.suncreate.shinyportal.R;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.util.BarUtils;
import com.zds.base.view.Loading_view;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * @author Administrator
 */
@SuppressLint({"NewApi", "Registered"})
public abstract class BaseActivity extends AppCompatActivity {
    protected InputMethodManager inputMethodManager;
    protected RelativeLayout mToolbar;
    protected TextView title;
    protected ImageView img_right;
    protected TextView toolbar_subtitle;
    private LinearLayout ll_back;
    private View bar;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        ActivityStackManager.getInstance().addActivity(new WeakReference<Activity>(this));
        EventBus.getDefault().register(this);
        mContext = this;
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        initContentView(savedInstanceState);
        immersionBar = ImmersionBar.with(this);
        initToolBar();
        initLogic();
        initMap(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void initMap(Bundle savedInstanceState) {

    }

    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    protected void toTheActivity(Class<?> mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void toTheActivity(Class<?> mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * ?????????????????????
     */
    protected void setBarWhite() {
        initBar();
        if (bar != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight(this);
            bar.setLayoutParams(params);
            bar.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    /**
     * ?????????Toolbar
     */
    protected void initToolBar() {
        try {
            mToolbar = (RelativeLayout) findViewById(R.id.toolbar);
            if (null != mToolbar) {
                title = (TextView) findViewById(R.id.toolbar_title);
                img_right = (ImageView) findViewById(R.id.img_right);
                toolbar_subtitle = (TextView) findViewById(R.id.toolbar_subtitle);
            }
            ll_back = (LinearLayout) findViewById(R.id.ll_back);
            if (ll_back != null) {
                ll_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideSoftKeyboard();
                        finish();
                    }
                });
            }
            bar = findViewById(R.id.bar);
            setBarWhite();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????? title
     *
     * @param string
     */
    public void setTitle(String string) {
        if (null != title) {
            title.setText(string);
        }
    }

    /**
     * ?????? title
     *
     * @param id
     */
    @Override
    public void setTitle(int id) {
        if (null != title) {
            title.setText(id);
        }
    }

    /**
     * ??????onCreate?????????
     */
    protected abstract void initContentView(Bundle bundle);


    /**
     * ???????????????
     */
    protected abstract void initLogic();

    /**
     * EventBus????????????
     *
     * @param center ????????????????????????
     */
    protected abstract void onEventComing(EventCenter center);

    /**
     * EventBus????????????
     *
     * @param center ????????????
     */
    @Subscribe
    public void onEventMainThread(EventCenter center) {
        if (null != center) {
            onEventComing(center);
        }
    }

    /**
     * Bundle  ????????????
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        hideSoftKeyboard();
        EventBus.getDefault().unregister(this);
        ActivityStackManager.getInstance().removeActivity(new WeakReference<Activity>(this));
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        super.onDestroy();
    }

    /**
     * ??????
     *
     * @param msg
     */
    public void toast(String msg) {
        ToastUtil.toast(msg);
    }

    Loading_view dialog;

    /**
     * ???????????????
     */
    public void showLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage("???????????????...");
            return;
        }
        dialog = new Loading_view(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("???????????????...");
        dialog.show();
    }

    /**
     * ???????????????
     *
     * @param message
     */
    protected void showLoading(String message) {
        if (dialog != null && dialog.isShowing()) {
            dialog.setMessage(message);
            return;
        }
        dialog = new Loading_view(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(message);
        dialog.show();
    }

    /**
     * ???????????????
     */
    protected void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * ???????????????
     *
     * @param view
     */
    protected void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * ???????????????
     */
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * ???????????????
     */
    protected void hideSoftKeyboard2() {
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                0);
    }

    /**
     * ???????????????
     */
    protected void hideSoftKeyboard3() {
        inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public ImmersionBar immersionBar;

    public void initBar() {
        if (immersionBar != null) {
            immersionBar.statusBarDarkFont(true, 0.2f).keyboardEnable(true).init();  //???????????????????????????
        }
    }

    public void setBarDarkFont(Boolean isBarDarkFont) {
        if (immersionBar != null) {
            //???????????????????????????
            immersionBar.statusBarDarkFont(isBarDarkFont, 0.2f).init();
        }
    }

    public void setBarDarkFontNoAlpha(Boolean isBarDarkFont) {
        if (immersionBar != null) {
            //???????????????????????????
            immersionBar.statusBarDarkFont(isBarDarkFont).init();
        }
    }

    //    ????????????????????????
    public void initkeyboardShow() {
        immersionBar.statusBarDarkFont(true).keyboardEnable(true)  //?????????????????????????????????????????????????????????false???????????????????????????????????????????????????mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //???????????????????????????
                .setOnKeyboardListener(new OnKeyboardListener() {    //?????????????????????
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        //  LogUtils.e(isPopup);  //isPopup???true????????????????????????false??????????????????
                    }
                })
                .init();  //???????????????????????????
    }

    /**
     * ??????View????????????
     */
    public void startFlick(View view) {
        if (null == view) {
            return;
        }
        if (view.getAnimation() != null) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0.4f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    /**
     * ??????View????????????
     */
    public void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }

    public void webViewSet(WebView webview) {
        webview.setInitialScale(25);
        WebSettings settings = webview.getSettings();
        //????????????
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);// ??????????????????????????????
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    protected void toLogin() {
        new AlertDialog.Builder(this).setTitle("??????").setMessage("?????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(new Intent(mContext, LoginActivity.class));
            }
        }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissLoading();
            }
        }).show();
    }
}


