package com.suncreate.shinyportal.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.suncreate.shinyportal.base.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.zds.base.Toast.ToastUtil;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;
import com.zxy.tiny.callback.FileCallback;

import java.io.ByteArrayOutputStream;

/**
 * 作   者：Christ
 * 描   述:
 * 邮   箱: 627447123@qq.com
 * 日   期: 2017/12/6 10:34
 * 更新日期: 2017/12/6
 */
public class ShareUtil {
    /**
     * 微信分享
     *
     * @param context
     * @param url
     * @param title
     * @param des
     * @param res
     * @param type    SendMessageToWX.Req.WXSceneTimeline（微信朋友圈）    SendMessageToWX.Req.WXSceneSession (微信聊天)
     */
    public static void shareUrl(final Context context, final String url,final String title, final String des,final String res,final int type) {

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context).asBitmap().load(res).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.size = 32;
                        Tiny.getInstance().source(resource).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile, Throwable t) {
                                Bitmap bs = resource;
                                if (isSuccess) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(outfile);
                                    bs = bitmap;
                                }
                                WXWebpageObject webpageObject = new WXWebpageObject();
                                webpageObject.webpageUrl = url;
                                WXMediaMessage msg = new WXMediaMessage(webpageObject);
                                msg.title = title;
                                msg.description = des;
                                msg.thumbData = BitmapUtil.bmpToByteArray(Bitmap.createScaledBitmap(bs, 150, 150, true), true);
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = System.currentTimeMillis() + "";
                                req.message = msg;
                                req.scene = type;
                                MyApplication.getInstance().registerWx().sendReq(req);
                            }
                        });

                    }
                });
            }
        });
    }

    /**
     * 微信分享
     *
     * @param bitmap
     * @param type   SendMessageToWX.Req.WXSceneTimeline（微信朋友圈）    SendMessageToWX.Req.WXSceneSession (微信聊天)
     */
    public static void shareUrlImage(Context context, final String title, final String des, Bitmap bitmap, final int type) {
        Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
        Tiny.getInstance().source(bitmap).asBitmap().withOptions(options).compress(new BitmapCallback() {
            @Override
            public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                if (isSuccess) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    WXImageObject webpageObject = new WXImageObject();
                    webpageObject.imageData = baos.toByteArray();
                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
                    msg.title = title;
                    msg.description = des;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = System.currentTimeMillis() + "";
                    req.message = msg;
                    req.scene = type;
                    MyApplication.getInstance().registerWx().sendReq(req);
                } else {
                    ToastUtil.toast("图片异常");
                }
            }
        });

    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
