/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.suncreate.shinyportal.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;
import androidx.core.content.ContextCompat;

import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.anhui.police.auth.sdk.AuthConfig;
import com.anhui.police.auth.sdk.AuthSDK;
import com.anhui.police.auth.sdk.AuthType;
import com.anhui.police.market.sdk.MarketConfigure;
import com.suncreate.shinyportal.activity.LoginActivity;
import com.suncreate.shinyportal.activity.WelcomeActivity;
import com.suncreate.shinyportal.entity.UserInfo;
import com.suncreate.shinyportal.entity.VersionInfo;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.updata.CretinAutoUpdateUtils;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.util.GDLocationUtil;
import com.suncreate.shinyportal.view.dialog.CommonDialog;
import com.suncreate.shinyportal.wxapi.WeChatConstans;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.base.SelfAppContext;
import com.zds.base.util.StringUtil;
import com.zds.base.util.Utils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * @author Administrator
 */
public class MyApplication extends SelfAppContext {

    public static Context applicationContext;
    private static MyApplication instance;
    private UploadManager uploadManager;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;
        init();
        //JPushInterface.init(this);
        GDLocationUtil.init(this);


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AuthSDK.getDefault().onTerminate();
    }

    private IWXAPI mIWXAPI;

    public IWXAPI registerWx() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID, true);
        mIWXAPI.registerApp(WeChatConstans.APP_ID);
        return mIWXAPI;
    }

    /**
     * 初始化
     */
    private void init() {
        // 图片压缩
        Tiny.getInstance().init(this);
        // 下拉刷新上拉加载
        setRefush();
        // 版本更新
        //setUpDataApp();
        // util
        Utils.init(this);
        //七牛
        //uploadManager = new UploadManager(new Configuration.Builder().build());
        //bugly bug
        //CrashReport.initCrashReport(getApplicationContext(), "42a24f434c", false);
        initMarket();
        initAuth();
    }

    private void initMarket() {
        MarketConfigure.init(this, "96b4d514ed3349f4ac72bb4c6824e364", MarketConfigure.MarketDeviceType.DEVICE_TYPE_PHONE);
        MarketConfigure.setLogEnabled(true);
        MarketConfigure.setUpgradeMethod(true, this.getResources().getColor(R.color.colorPrimary));
    }

    private void initAuth() {
        AuthSDK.init("7a12e6a15a58441e8fc34bb5ab902cab", this);
    }


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 获取软件版本名称
     *
     * @return
     */
    public String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * 获取软件版本号
     *
     * @return
     */
    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * 获取AppId
     *
     * @return
     */
    public String getApplicationId() {
        return getPackageInfo().packageName;
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取缓存用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        UserInfo userInfo = Storage.GetUserInfo();
        return userInfo == null ? new UserInfo() : userInfo;
    }

    public String getToken() {
        UserInfo userInfo = getUserInfo();
        return userInfo.getToken();
    }

    private CommonDialog commonDialog;


    /**
     * 保存缓存用户信息
     *
     * @param user
     */
    public void saveUserInfo(final UserInfo user) {
        if (user != null) {
            Storage.saveUsersInfo(user);
        }
    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUser() {
        if (StringUtil.isEmpty(getUserInfo().getToken())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置cooks
     *
     * @param url
     * @param
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setCook(String url) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(getApplicationContext());
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            if (checkUser()) {
                cookieManager.setCookie(url
                        , "70b9___ewei_shopv2_member_session_1=" + getUserInfo().getToken() + ";path=/");//cookies是在HttpClient中获得的cookie
            } else {
                cookieManager.setCookie(url
                        , "70b9___ewei_shopv2_member_session_1=;path=/");//cookies是在HttpClient中获得的cookie

            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush();
            }

        } catch (Exception e) {

        }


    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUserToLogin(Context context) {
/*        if (StringUtil.isEmpty(getUserInfo().getCookievalue())) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return false;
        } else {
            return true;
        }*/
        return true;
    }

    /**
     * 清除缓存用户信息
     *
     * @param
     */
    public void cleanUserInfo() {
        Storage.ClearUserInfo();
    }


    /**
     * 设置版本更新
     */
    private void setUpDataApp() {

        //版本更新
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api
                .setBaseUrl(AppConfig.checkVersion)
                //设置是否显示忽略此版本
                .setIgnoreThisVersion(true)
                //设置下载显示形式 对话框或者通知栏显示 二选一
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_PROGRESS)
                //设置下载时展示的图标
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_POST)
                //设置下载时展示的应用名称
                .setAppName(getResources().getString(R.string.app_name))
                //设置自定义的Model类
                .setTransition(new VersionInfo())
                .build();
        CretinAutoUpdateUtils.init(builder);
    }


    public void toLogin(Context context) {
/*        ActivityStackManager.getInstance().killAllActivity();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ToastUtil.toast("登录已失效，请您重新登陆~");*/


        ActivityStackManager.getInstance().killAllActivity();
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ToastUtil.toast("登录已失效，请您重新登陆~");
    }

    /**
     * 设置 刷新
     */
    private void setRefush() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.text_gray));
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(true);
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setBackgroundResource(android.R.color.white);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });
    }

    public UploadManager getUpM() {
        return uploadManager;
    }


    public void upPic(final String path, final String key, final String token, final UpCompletionHandler upCompletionHandler) {
        Tiny.getInstance().source(path).asFile().compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile, Throwable t) {
                try {
                    if (isSuccess) {
                        getUpM().put(outfile, key, token,
                                upCompletionHandler, null);
                    } else {
                        upCompletionHandler.complete(key, null, null);
                    }
                } catch (Exception e) {
                    upCompletionHandler.complete(key, null, null);

                }
            }
        });

    }

    public void upVideo(final String path, final String key, final String token, final UpCompletionHandler upCompletionHandler) {
        getUpM().put(path, key, token, upCompletionHandler, null);
    }


    public void showAllScreenBase64ImageDialog(Activity activity, String base64){
        //展示在dialog上面的大图
        Dialog dialog = new Dialog(activity,R.style.AllScreenImage);

        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(attributes);

        ImageView imageView = new ImageView(activity);

        //宽高
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //将Base64编码字符串解码成Bitmap
        byte[] decodedString = Base64.decode(base64.split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //设置ImageView图片
        imageView.setImageBitmap(decodedByte);

        dialog.setContentView(imageView);

        //大图的点击事件（点击让他消失）
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
