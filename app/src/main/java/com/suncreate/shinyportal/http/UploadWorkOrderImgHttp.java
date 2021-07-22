package com.suncreate.shinyportal.http;

import android.content.Context;

import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christ on 2021/7/19.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class UploadWorkOrderImgHttp {

    public static void upload(Context mContext, String fileType, File file, UploadResult uploadResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileType", fileType);
        hashMap.put("file", file);
        ApiClient.requestNetPostFile(mContext, AppConfig.uploadCommon, "上传中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                Object pic = FastJsonUtil.getObject(FastJsonUtil.getString(json, "ptAttachment"), Object.class);
                String path = FastJsonUtil.getString(json, "filePath");
                uploadResult.onSuccess(pic, path);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    public static void delete(Context mContext, String fileName, DeleteResult deleteResult) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("fileName", fileName);
        ApiClient.requestNetPost(mContext, AppConfig.deleteFileCommon, "删除中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                deleteResult.onSuccess();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }

    public interface UploadResult {
        void onSuccess(Object pic, String path);
    }

    public interface DeleteResult {
        void onSuccess();
    }
}
