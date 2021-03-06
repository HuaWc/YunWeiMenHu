package com.zds.base.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 作   者：Christ
 * 描   述:
 * 邮   箱: 627447123@qq.com
 * 日   期: 2017/11/17 15:19
 * 更新日期: 2017/11/17
 */
public class UriUtil {
    public static Uri getUri(Context context, String path) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", new File(path));
        } else {
            uri = Uri.fromFile(new File(path));
        }
        return uri;
    }

    public static Uri getUri(Context context, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static Uri getFromFile(String path) {
        return Uri.fromFile(new File(path));
    }
}
