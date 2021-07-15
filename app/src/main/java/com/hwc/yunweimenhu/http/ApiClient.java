package com.hwc.yunweimenhu.http;

import android.content.Context;

import com.hwc.yunweimenhu.base.Constant;
import com.hwc.yunweimenhu.base.MyApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.log.XLog;
import com.zds.base.util.StringUtil;
import com.zds.base.view.Loading_view;

import java.util.HashMap;
import java.util.Map;


/**
 * 作   者：Christ
 * 描   述: 网络请求工具类
 * 日   期: 2017/11/13 16:05
 * 更新日期: 2017/11/13
 */
public class ApiClient {

    static Loading_view progressDialog = null;

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token_", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>post(url)
                    .tag(context)
                    .headers("token_", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, boolean cache, final Map<String, Object> mapP, final ResultListener listener) {
        if (!cache) {
            if (!MyApplication.getInstance().isNetworkConnected()) {
                //没网络
                listener.onFailure("网络连接异常,请检查您的网络设置");
                return;
            }
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .cacheMode(cache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onCacheSuccess(Response<String> response) {
                                     super.onCacheSuccess(response);
                                     fomartData(response, listener);
                                     dismiss();
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPost(final Context context, String url, String log, final Map<String, Object> mapP, final Map<String, String> head, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            HttpHeaders httpHeaders = new HttpHeaders();
            if (null != head && head.size() > 0) {
                for (String key : head.keySet()) {
                    httpHeaders.put(key, head.get(key));
                }
            }
            OkGo.<String>post(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .headers(httpHeaders)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetPostNoFormat(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>post(url)
                    .tag(context)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     listener.onSuccess(response.body().toString(), "");
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>get(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            OkGo.<String>get(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * post
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, boolean cache, final Map<String, Object> mapP, final ResultListener listener) {
        if (!cache) {
            if (!MyApplication.getInstance().isNetworkConnected()) {
                //没网络
                listener.onFailure("网络连接异常,请检查您的网络设置");
                return;
            }
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .params(getFormatMap(mapP))
                    .cacheMode(cache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onCacheSuccess(Response<String> response) {
                                     super.onCacheSuccess(response);
                                     fomartData(response, listener);
                                     dismiss();
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGet(final Context context, String url, String log, final Map<String, Object> mapP, final Map<String, String> head, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            HttpHeaders httpHeaders = new HttpHeaders();
            if (null != head && head.size() > 0) {
                for (String key : head.keySet()) {
                    httpHeaders.put(key, head.get(key));
                }
            }
            OkGo.<String>get(url)
                    .tag(context)
                    .params("__ewei_shopv2_member_session_1", MyApplication.getInstance().getToken())
                    .headers(httpHeaders)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     fomartData(response, listener);
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }

                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    /**
     * 请求网络数据接口
     * Get
     *
     * @param context
     * @param url
     * @param log
     * @param mapP
     * @param listener
     */
    public static void requestNetGetNoFormat(final Context context, String url, String log, final Map<String, Object> mapP, final ResultListener listener) {
        if (!MyApplication.getInstance().isNetworkConnected()) {
            //没网络
            listener.onFailure("网络连接异常,请检查您的网络设置");
            return;
        }
        try {
            showDialog(log, context);
            OkGo.<String>get(url)
                    .tag(context)
                    .params(getFormatMap(mapP))
                    .execute(new StringCallback() {
                                 /**
                                  * 对返回数据进行操作的回调， UI线程
                                  *
                                  * @param response
                                  */
                                 @Override
                                 public void onSuccess(Response<String> response) {
                                     listener.onSuccess(response.body().toString(), "");
                                 }

                                 @Override
                                 public void onFinish() {
                                     super.onFinish();
                                     listener.onFinsh();
                                     dismiss();
                                 }

                                 @Override
                                 public void onError(Response<String> response) {
                                     try {
                                         if (response.code() == 404 || response.code() >= 500) {
                                             listener.onFailure("服务器异常！错误码：" + response.code());
                                         } else {
                                             listener.onFailure(response.getException().getMessage());
                                         }
                                     } catch (Exception e) {
                                         XLog.error(e);
                                     }

                                 }
                             }
                    );
        } catch (Exception e) {
            listener.onFailure(e.getMessage());
        }

    }

    private static Map<String, String> getFormatMap(Map<String, Object> mapP) {
        Map<String, String> map = new HashMap<>();
        if (null != mapP && mapP.size() > 0) {
            for (String key : mapP.keySet()) {
                map.put(key, String.valueOf(mapP.get(key)));
            }
        }
        return map;
    }

    /**
     * show dialog
     */
    private static void showDialog(String log, Context context) {
        if (!StringUtil.isEmpty(log)) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
            progressDialog = new Loading_view(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(log);
            progressDialog.show();
        }
    }

    /**
     * dis dialog
     */
    private static void dismiss() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 格式化请求数据
     */
    private static void fomartData(Response<String> response, ResultListener listener) {
        try {
            ServerData serverData = FastJsonUtil.getObject(response.body().toString(), ServerData.class);
            if (null != serverData) {
                try {
                    serverData.setMessage(FastJsonUtil.getString(FastJsonUtil.toJSONString(serverData.getResult()), "message"));
                } catch (Exception e) {

                }
                if (serverData.getStatus() == Constant.CODESUCCESS) {
                    listener.onSuccess(FastJsonUtil.toJSONString(serverData.getResult()), serverData.getMessage() == null ? "" : serverData.getMessage());
                } else {
                    listener.onFailure(serverData.getMessage() == null ? "" : serverData.getMessage());
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            listener.onFailure("解析异常");
        }

    }

}
