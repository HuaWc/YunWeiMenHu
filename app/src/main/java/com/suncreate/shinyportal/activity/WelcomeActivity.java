package com.suncreate.shinyportal.activity;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anhui.police.auth.sdk.AuthSDK;
import com.anhui.police.auth.sdk.AuthUser;
import com.anhui.police.auth.sdk.IAuthListener;
import com.anhui.police.auth.sdk.ITokenListener;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.base.MyApplication;
import com.suncreate.shinyportal.entity.UserInfo;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.img_welcome)
    ImageView imgWelcome;

    private String getToken = null;


    public static String SDCARD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initLogic() {
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something
                if (MyApplication.getInstance().checkUser()) {
                    if (MyApplication.getInstance().getUserInfo().isRemember()) {
                        toTheActivity(MainActivity.class);
                    } else {
                        MyApplication.getInstance().cleanUserInfo();
                        toTheActivity(LoginActivity.class);
                    }
                } else {
                    MyApplication.getInstance().cleanUserInfo();
                    toTheActivity(LoginActivity.class);
                }

                finish();

            }
        }, 3000);*/
        //imgWelcome.setImageResource(R.mipmap.img_welcome);
        //initSuperMapData();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                newAuthLogin();
            }
        }, 2500);
    }

/*    private void executeDataAndStart() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();
                Environment.setLicensePath(rootPath + "/Mobile GIS/License/");
                Environment.initialization(WelcomeActivity.this);
                Environment.setOpenGLMode(true);


*//*                if (MyApplication.getInstance().checkUser()) {
                    if (MyApplication.getInstance().getUserInfo().isRemember()) {
                        toTheActivity(MainActivity.class);
                    } else {
                        MyApplication.getInstance().cleanUserInfo();
                        toTheActivity(LoginActivity.class);
                    }
                } else {
                    MyApplication.getInstance().cleanUserInfo();
                    toTheActivity(LoginActivity.class);
                }*//*

                newAuthLogin();


            }
        }, 1500);
    }


    private void initSuperMapData() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                File mapDataDir = new File(SDCARD + "Mobile GIS");
                if (!mapDataDir.exists()) {
                    mapDataDir.mkdir();
                }

                try {
                    CopyFiles("Mobile GIS", SDCARD + "Mobile GIS");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    executeDataAndStart();
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("??????????????????,????????????????????????");

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
    }

    private boolean CopyFiles(String oldPath, String newPath) throws IOException {
        AssetManager mAssetManger = this.getAssets();
        String[] fileNames = mAssetManger.list(oldPath);// ??????assets????????????????????????????????????????????????
        if (fileNames.length > 0) {//???????????????,?????????????????????????????????0
            File file = new File(newPath);
            file.mkdirs();//????????????????????????????????????
            for (String fileName : fileNames) {
                if (oldPath == "")   //assets??????oldPath?????????????????????????????????/?????????
                    CopyFiles(fileName, newPath + "/" + fileName);
                else
                    CopyFiles(oldPath + "/" + fileName, newPath + "/" + fileName);
            }
        } else {//???????????????
            File file = new File(newPath);
            if (file.exists()) {
                return false;
            } else {
                InputStream is = mAssetManger.open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//???????????????????????? buffer??????
                    fos.write(buffer, 0, byteCount);//???????????????????????????????????????
                }
                fos.flush();//???????????????
                is.close();
                fos.close();
            }
        }
        return true;
    }*/

    private void newAuthLogin() {
        AuthSDK.getDefault().getToken(WelcomeActivity.this, new ITokenListener() {
            @Override
            public void onSuccess(int code, String token) {
                getToken = token;
                AuthSDK.getDefault().getUserInfo(WelcomeActivity.this, getToken, new IAuthListener() {
                    @Override
                    public void onAuthError(int errorCode, @Nullable String errorMsg) {
                        Log.e("mistake", String.format("err: code = %s, msg = %s", errorCode, errorMsg));
                        ToastUtil.toast(errorMsg);

                    }

                    @Override
                    public void onSuccess(String token, @Nullable AuthUser user, @Nullable String level) {
                        //?????????????????????????????????????????????????????????
                        Map<String,Object> hashMap = new HashMap<>();
                        hashMap.put("userName",user.getUserName());
                        hashMap.put("realName",user.getRealName());
                        hashMap.put("orgId",user.getOrgId());
                        hashMap.put("orgName",user.getOrgName());
                        hashMap.put("phoneNum",user.getPhoneNum());
                        hashMap.put("workPhone",user.getWorkPhone());
                        hashMap.put("email",user.getEmail());
                        hashMap.put("code",user.getCode());
                        hashMap.put("policeTypeId",user.getPoliceTypeId());
                        hashMap.put("policeTypeName",user.getPoliceTypeName());
                        hashMap.put("userNum",user.getUserNum());
                        hashMap.put("token",token);
                        ApiClient.requestNetPost(WelcomeActivity.this, AppConfig.checkToken, "?????????", hashMap, new ResultListener() {
                            @Override
                            public void onSuccess(String json, String msg) {
                                ToastUtil.toast(msg);
                                UserInfo userInfo = FastJsonUtil.getObject(json, UserInfo.class);
                                if (userInfo != null) {
                                    //??????????????????
                                    MyApplication.getInstance().cleanUserInfo();
                                    MyApplication.getInstance().saveUserInfo(userInfo);
                                    toTheActivity(MainActivity.class);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(String msg) {
                                ToastUtil.toast(msg);
                            }
                        });


                    }
                });

            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Log.e("mistake", String.format("err: code = %s, msg = %s", errorCode, errorMsg));
                ToastUtil.toast(errorMsg);
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AuthSDK.getDefault().onActivityResult(requestCode, resultCode, data);
    }
}
