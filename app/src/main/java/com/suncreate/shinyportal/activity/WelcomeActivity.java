package com.suncreate.shinyportal.activity;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.widget.ImageView;


import androidx.annotation.NonNull;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.base.MyApplication;
import com.supermap.data.Environment;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.img_welcome)
    ImageView imgWelcome;

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
        initSuperMapData();
    }

    private void executeDataAndStart(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();
                Environment.setLicensePath(rootPath + "/Mobile GIS/License/");
                Environment.initialization(WelcomeActivity.this);
                Environment.setOpenGLMode(true);
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
        },1500);
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
                }finally {
                    executeDataAndStart();
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("您拒绝了权限,超图将无法使用！");

            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
    }

    private boolean CopyFiles(String oldPath, String newPath) throws IOException {
        AssetManager mAssetManger = this.getAssets();
        String[] fileNames = mAssetManger.list(oldPath);// 获取assets目录下的所有文件及有文件的目录名
        if (fileNames.length > 0) {//如果是目录,如果是具体文件则长度为0
            File file = new File(newPath);
            file.mkdirs();//如果文件夹不存在，则创建
            for (String fileName : fileNames) {
                if (oldPath == "")   //assets中的oldPath是相对路径，不能够以“/”开头
                    CopyFiles(fileName, newPath + "/" + fileName);
                else
                    CopyFiles(oldPath + "/" + fileName, newPath + "/" + fileName);
            }
        } else {//如果是文件
            File file = new File(newPath);
            if (file.exists()) {
                return false;
            } else {
                InputStream is = mAssetManger.open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {//循环从输入流读取 buffer字节
                    fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流
                }
                fos.flush();//刷新缓冲区
                is.close();
                fos.close();
            }
        }
        return true;
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
}
