package com.suncreate.shinyportal.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anhui.police.auth.sdk.AuthConfig;
import com.anhui.police.auth.sdk.AuthSDK;
import com.anhui.police.auth.sdk.AuthType;
import com.anhui.police.auth.sdk.AuthUser;
import com.anhui.police.auth.sdk.IAuthListener;
import com.anhui.police.auth.sdk.ITokenListener;
import com.anhui.police.market.bean.MarketAppInfoBean;
import com.anhui.police.market.callback.AbstractMarketResult;
import com.anhui.police.market.sdk.MarketConfigure;
import com.google.gson.Gson;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.base.MyApplication;
import com.zds.base.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christ on 2021/8/17.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class TestActivity extends BaseActivity implements IAuthListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox checkBox, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private Button mButton, mButton1, mButton2, mButton3;
    private TextView mTextView, showTextView;

    private AuthConfig.AuthMethodType authMethodType;
    private AuthType authType;
    private StringBuffer stringBuffer = new StringBuffer();
    private List<AuthType> authTypes = new ArrayList<>();
    private String methodType = null, getToken = null;
    /**
     * 边框宽度/圆角半径/边框颜色/内部填充颜色
     */
    private int strokeWidth = 5, roundRadius = 45, strokeColor = Color.parseColor("#33bb77"), fillColor = Color.parseColor("#33bb77");
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void initLogic() {
        checkBox = findViewById(R.id.checkBox);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);

        mButton = findViewById(R.id.auth_btn);
        mButton1 = findViewById(R.id.auth_btn1);
        mButton2 = findViewById(R.id.auth_btn2);
        mButton3 = findViewById(R.id.auth_btn3);

        mTextView = findViewById(R.id.auth_tv);
        showTextView = findViewById(R.id.auth_select_type_tv);

        radioGroup = findViewById(R.id.radio_group);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);

        checkBox.setOnCheckedChangeListener(this);
        checkBox1.setOnCheckedChangeListener(this);
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4.setOnCheckedChangeListener(this);
        checkBox5.setOnCheckedChangeListener(this);

        mButton.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.radioButton1);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        mButton.setBackgroundDrawable(gd);
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(fillColor);
        gd1.setCornerRadius(roundRadius);
        gd1.setStroke(strokeWidth, strokeColor);
        mButton1.setBackgroundDrawable(gd1);
        mButton2.setBackgroundDrawable(gd1);
        mButton3.setBackgroundDrawable(gd1);

        AuthSDK.getDefault().setAuthListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("auth", requestCode + "-->" + resultCode);
        AuthSDK.getDefault().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 验证失败
     *
     * @param errorCode
     * @param errorMsg
     */
    @Override
    public void onAuthError(int errorCode, String errorMsg) {
        Log.e("onAuthError", errorCode + "--" + errorMsg);
        mTextView.setText("errCode = " + errorCode + ", errMsg = " + errorMsg);
    }

    /**
     * 验证成功
     *
     * @param token
     * @param user
     * @param level
     */
    @Override
    public void onSuccess(String token, AuthUser user, String level) {
        if (user != null) {
            mTextView.setText(token + user.toString());
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        mTextView.setText("验证中...");
        switch (v.getId()) {
            case R.id.auth_btn:
                if (authTypes.size() == 0) {
                    mTextView.setText("验证方式不能为空");
                    Toast.makeText(this, "验证方式不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                AuthConfig config = AuthConfig.builder().setAuthMethodType(authMethodType).setAuthTypes(authTypes);
                AuthSDK.getDefault().startAuth(TestActivity.this, config, this);
                break;

            case R.id.auth_btn1:
                AuthSDK.getDefault().getToken(this, new ITokenListener() {

                    @Override
                    public void onSuccess(int code, String token) {
                        getToken = token;
                        mTextView.setText(String.format("success: code = %s, token = %s", code, token));
                    }

                    /**
                     * 验证成功
                     *
                     * @param msg
                     */
                    @Override
                    public void onError(int code, String msg) {
                        mTextView.setText(String.format("err: code = %s, msg = %s", code, msg));
                    }
                });
                break;

            case R.id.auth_btn2:
                if (getToken != null) {
                    AuthSDK.getDefault().getUserInfo(this, getToken, this);
                } else {
                    mTextView.setText("err : token is null.");
                }
                break;

            case R.id.auth_btn3:
                AuthSDK.getDefault().startAuth(this, this);
                break;
            default:
                break;
        }
    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkBox:
                authType = AuthType.DEFAULT;
                break;
            case R.id.checkBox1:
                authType = AuthType.DIGITAL_CERTIFICATE;
                break;
            case R.id.checkBox2:
                authType = AuthType.GESTURE;
                break;
            case R.id.checkBox3:
                authType = AuthType.FACE;
                break;
            case R.id.checkBox4:
                authType = AuthType.VOICE;
                break;
            case R.id.checkBox5:
                authType = AuthType.PWD;
                break;

            default:
                break;
        }
        if (isChecked) {
            authTypes.add(authType);
        } else {
            authTypes.remove(authType);
        }
        stringBuffer.setLength(0);
        int current = 0;
        for (AuthType type : authTypes) {
            if (current > 0) {
                stringBuffer.append("->");
            }
            stringBuffer.append(type.getTypeCode()+"、"+type.getType());
            current++;
        }
        if (current == 0){
            stringBuffer.append("未选择");
        }
        if (authType.getTypeCode() == 0) {
            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
            checkBox4.setChecked(false);
            checkBox5.setChecked(false);
        } else {
            checkBox.setChecked(false);
        }
        buttonView.setChecked(isChecked);
        if (authTypes.size() > 1 && !authTypes.contains(AuthType.DEFAULT)) {
            radioButton1.setEnabled(false);
            if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton1) {
                radioGroup.check(R.id.radioButton2);
            }
        } else {
            radioButton1.setEnabled(true);
        }
        showTextView.setText(String.format("✉当前选择的验证类型为：%s \n️✔️验证方式为：%s", methodType, stringBuffer.toString()));
    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                authMethodType = AuthConfig.AuthMethodType.AUTH_METHOD_DEFAULT;
                break;

            case R.id.radioButton2:
                authMethodType = AuthConfig.AuthMethodType.AUTH_METHOD_SWITCH;
                break;

            case R.id.radioButton3:
                authMethodType = AuthConfig.AuthMethodType.AUTH_METHOD_ORDER;
                break;

            default:
                break;
        }
        if (authMethodType.getType() == 1){
            methodType = "默认方式验证";
        } else if (authMethodType.getType() == 2){
            methodType = "允许切换验证方式";
        } else if (authMethodType.getType() == 3){
            methodType = "强制验证顺序验证";
        } else {
            methodType = null;
        }
        if (authTypes.size() == 0){
            stringBuffer.setLength(0);
            stringBuffer.append("未选择");
        }
        showTextView.setText(String.format("✉️当前选择的验证类型为：%s \n✔验证方式为：%s", methodType, stringBuffer.toString()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
