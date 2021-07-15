package com.hwc.yunweimenhu.http;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import com.hwc.yunweimenhu.entity.VersionInfo;
import com.hwc.yunweimenhu.updata.CretinAutoUpdateUtils;
import com.hwc.yunweimenhu.BuildConfig;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.upDated.interfaces.ForceExitCallBack;
import com.zds.base.upDated.model.UpdateEntity;
import com.zds.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 *
 * @author Administrator
 */
public class AppConfig {
    /**
     * 服务器
     */
    public static final String baseService = BuildConfig.BASEURL;
    /**
     * 主地址
     */
    public static final String mainUrl = baseService;







    /**
     * 版本更新
     */
    public static String checkVersion = mainUrl + "app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&r=goods.api.versioninfo&httpsource=fromapp";



    /**
     * 检查版本
     */
    public static void checkVersion(final Context context, boolean isInge) {
        if (isInge) {
            CretinAutoUpdateUtils.getInstance(context).check(new ForceExitCallBack() {
                @Override
                public void exit() {
                    ((Activity) context).finish();
                }

                @Override
                public void isHaveVersion(boolean isHave) {

                }

                @Override
                public void cancel() {

                }
            });
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("versions", SystemUtil.getAppVersionNumber());
            map.put("platform", 1);
            ApiClient.requestNetGet(context, checkVersion, "正在检测...", map, new ResultListener() {
                @Override
                public void onSuccess(String json, String msg) {
                    String mes = FastJsonUtil.getString(json, "newversion");
                    final VersionInfo versionInfo = FastJsonUtil.getObject(mes, VersionInfo.class);
                    if (versionInfo != null) {
                        if (versionInfo.getVersionCodes() > SystemUtil.getAppVersionNumber()) {
                            new AlertDialog.Builder(context).setTitle("新版本" + versionInfo.getVersionNames()).setMessage(versionInfo.getUpdateLogs()).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    UpdateEntity updateEntity = new UpdateEntity();
                                    updateEntity.setVersionCode(versionInfo.getVersionCodes());
                                    updateEntity.setIsForceUpdate(versionInfo.getIsForceUpdates());
                                    updateEntity.setPreBaselineCode(versionInfo.getPreBaselineCodes());
                                    updateEntity.setVersionName(versionInfo.getVersionNames());
                                    updateEntity.setDownurl(versionInfo.getDownurls());
                                    updateEntity.setUpdateLog(versionInfo.getUpdateLogs());
                                    updateEntity.setSize(versionInfo.getApkSizes());
                                    updateEntity.setHasAffectCodes(versionInfo.getHasAffectCodess());
                                    UpdateEntity var8 = updateEntity;
                                    CretinAutoUpdateUtils.getInstance(context).startUpdate(var8);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface anInterface, int i) {
                                    anInterface.dismiss();
                                }
                            }).show();

                        } else {
                            ToastUtil.toast("当前已是最新版本");
                        }

                    } else {
                        ToastUtil.toast("请求数据失败");
                    }
                }

                @Override
                public void onFailure(String msg) {
                    ToastUtil.toast(msg);
                }
            });
        }
    }

}
