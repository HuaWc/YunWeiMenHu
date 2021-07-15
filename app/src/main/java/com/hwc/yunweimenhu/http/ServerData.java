package com.hwc.yunweimenhu.http;

import java.io.Serializable;

/**
 * 作   者：Christ
 * 描   述: 网络请求 基层数据封装
 * 日   期: 2017/11/13 17:50
 * 更新日期: 2017/11/13
 *
 * @author Administrator
 */
public class ServerData implements Serializable {
    private static final long serialVersionUID = 1L;


    private int status;
    private String message;
    private Object result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
