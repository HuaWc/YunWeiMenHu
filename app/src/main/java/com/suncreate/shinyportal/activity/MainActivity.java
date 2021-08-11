package com.suncreate.shinyportal.activity;

import android.Manifest;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.suncreate.shinyportal.base.MyApplication;
import com.suncreate.shinyportal.fragment.FirstHomeFragment;
import com.suncreate.shinyportal.fragment.FirstPersonalFragment;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.updata.CretinAutoUpdateUtils;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.base.BaseActivity;
import com.supermap.data.Environment;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_tab_main)
    ImageView ivTabMain;
    @BindView(R.id.tv_tab_main)
    TextView tvTabMain;
    @BindView(R.id.rl_tab_main)
    RelativeLayout rlTabMain;
    @BindView(R.id.iv_tab_mine)
    ImageView ivTabMine;
    @BindView(R.id.tv_tab_mine)
    TextView tvTabMine;
    @BindView(R.id.rl_tab_mine)
    RelativeLayout rlTabMine;
    @BindView(R.id.main_bottom)
    LinearLayout mainBottom;
    @BindView(R.id.fragment_container)
    RelativeLayout fragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    /**
     * fragment
     */
    private FirstHomeFragment mHomeFragment;
    private FirstPersonalFragment personalFragment;

    private ImageView[] icons;
    private TextView[] texts;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initLogic() {
        //initSuperMapData();
        initTab();
        getRole();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        icons = new ImageView[2];
        icons[0] = ivTabMain;
        icons[1] = ivTabMine;

        texts = new TextView[2];
        texts[0] = tvTabMain;
        texts[1] = tvTabMine;

        // select first tab
        icons[0].setSelected(true);
        texts[0].setSelected(true);

        mHomeFragment = new FirstHomeFragment();
        personalFragment = new FirstPersonalFragment();
        fragments = new Fragment[]{mHomeFragment, personalFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mHomeFragment)
                .add(R.id.fragment_container, personalFragment).hide(personalFragment).show(mHomeFragment)
                .commit();
    }

    /**
     * on tab clicked
     *
     * @param view
     */
    public void onFirstTabClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_main:
                index = 0;
                break;
            case R.id.rl_tab_mine:
                index = 1;
                break;
            default:
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        icons[currentTabIndex].setSelected(false);
        texts[currentTabIndex].setSelected(false);
        // set current tab selected
        icons[index].setSelected(true);
        texts[index].setSelected(true);
        currentTabIndex = index;
    }

    public void change(int indexs) {
        index = indexs;
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                icons[currentTabIndex].setSelected(false);
                texts[currentTabIndex].setSelected(false);
                // set current tab selected
                icons[index].setSelected(true);
                texts[index].setSelected(true);
                currentTabIndex = index;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
/*        if (MyApplication.getInstance().checkUser()) {
            JPushInterface.setAlias(getApplicationContext(), 1, MyApplication.getInstance().getUserInfo().getMobile());
        } else {
            JPushInterface.setAlias(getApplicationContext(), 1, "");
        }

        AppConfig.checkVersion(mContext, true);*/
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(mContext).destroy();
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

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;

    //重写onBackPressed()方法,继承自退出的方法
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            // 退出
            finish();
        }

    }

    /**
     * 提前调用下
     * 防止进入具体功能页面之后才检测到token失效，多重弹窗
     */
    private void getRole() {
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getUserRole, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public static String SDCARD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    private void initSuperMapData() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                showLoading();
                File mapDataDir = new File(SDCARD + "Mobile GIS");
                if (!mapDataDir.exists()) {
                    mapDataDir.mkdir();
                }

                try {
                    CopyFiles("Mobile GIS", SDCARD + "Mobile GIS");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    executeData();
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

    private void executeData(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                String rootPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath().toString();
                Environment.setLicensePath(rootPath + "/Mobile GIS/License/");
                Environment.initialization(MainActivity.this);
                Environment.setOpenGLMode(true);
                dismissLoading();
            }
        });

    }

}
