package com.suncreate.shinyportal.http;

import android.content.Context;

import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christ on 2021/7/21.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class CollectEventHttp {
    public static void collect(Context mContext, String id, CollectResult collectResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("alarmId", id);
        ApiClient.requestNetGet(mContext, AppConfig.CollectAdd, "收藏中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("收藏成功");
                collectResult.result(json);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    public static void del(Context mContext, String id, CollectResult collectResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("collectId", id);
        ApiClient.requestNetGet(mContext, AppConfig.CollectDel, "取消中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("取消成功");
                collectResult.result(json);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public interface CollectResult {
        void result(String json);
    }
}
