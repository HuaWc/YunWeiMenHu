package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zds.base.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 听说匹诺曹总说着慌
 * 侏儒怪拥有宝石满箱
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_remember)
    ImageView ivRemember;
    @BindView(R.id.ll_remember)
    LinearLayout llRemember;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;

    private boolean isRemember = true;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initLogic() {
        initBar();
        initClick();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivRemember.setSelected(true);
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

    private void initClick() {
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLogin();
            }
        });

        llRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRemember = !isRemember;
                ivRemember.setSelected(isRemember);
            }
        });
    }

    private void newLogin() {
        if (StringUtil.isEmpty(etAccount.getText().toString().trim())) {
            ToastUtil.toast("请输入正确的账号和密码！");
            return;
        }
        if (StringUtil.isEmpty(etPassword.getText().toString().trim())) {
            ToastUtil.toast("请输入正确的账号和密码！");
            return;
        }
        String account = etAccount.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", account);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiClient.requestNetPost(this, AppConfig.login, "登陆中", jsonObject, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                UserInfo userInfo = FastJsonUtil.getObject(json, UserInfo.class);
                hideSoftKeyboard();
                if (userInfo != null) {
                    //储存用户信息
                    userInfo.setRemember(isRemember);
                    MyApplication.getInstance().cleanUserInfo();
                    MyApplication.getInstance().saveUserInfo(userInfo);
                    toTheActivity(MainActivity.class);
                    finish();
                } else {
                    ToastUtil.toast(msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

}
