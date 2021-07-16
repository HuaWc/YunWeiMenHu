package com.hwc.yunweimenhu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwc.yunweimenhu.R;
import com.hwc.yunweimenhu.base.BaseActivity;
import com.hwc.yunweimenhu.entity.DictInfo;
import com.hwc.yunweimenhu.entity.KnowledgeModel;
import com.hwc.yunweimenhu.http.ApiClient;
import com.hwc.yunweimenhu.http.AppConfig;
import com.hwc.yunweimenhu.http.GetDictDataHttp;
import com.hwc.yunweimenhu.http.ResultListener;
import com.hwc.yunweimenhu.interfaces.PickerViewSelectOptionsResult;
import com.hwc.yunweimenhu.util.EventUtil;
import com.hwc.yunweimenhu.util.PickerViewUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新增知识库
 */
public class AddKnowledgeBaseActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_theme)
    EditText etTheme;
    @BindView(R.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R.id.tv_select_class)
    TextView tvSelectClass;
    @BindView(R.id.et_appearance)
    EditText etAppearance;
    @BindView(R.id.et_solution)
    EditText etSolution;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.all)
    LinearLayout all;

    private List<String> selectTypeOptions;
    private List<String> selectClassOptions;
    private List<DictInfo> typeList;
    private List<DictInfo> classList;
    private String typeStr = "";
    private String classStr = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_add_knowledge_base);
    }

    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initSelectData();
    }

    private void initSelectData() {
        selectTypeOptions = new ArrayList<>();
        selectClassOptions = new ArrayList<>();
        typeList = new ArrayList<>();
        classList = new ArrayList<>();
        getTypeData();
    }


    private void getTypeData() {
        GetDictDataHttp.getDictData(this, "", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    typeList.addAll(list);
                    for (DictInfo t : typeList) {
                        selectTypeOptions.add(t.getDataName());
                    }
                }
            }
        });

    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        tvSelectClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectClassDialog();
            }
        });
        tvSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectTypeDialog();
            }
        });
    }

    private void showSelectClassDialog() {
        hideSoftKeyboard();
        hideSoftKeyboard3();
        if (StringUtil.isEmpty(tvSelectType.getText().toString().trim())) {
            ToastUtil.toast("请先选择事件分类定义！");
            return;
        }
        if (selectClassOptions == null || selectClassOptions.size() == 0) {
            ToastUtil.toast("数据为空或者数据获取失败，请重试！");
            return;
        }
        PickerViewUtils.selectOptions(this, "类型", selectClassOptions, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                tvSelectClass.setText(selectClassOptions.get(options1));
                classStr = classList.get(options1).getDataValue();
            }
        });
    }

    private void showSelectTypeDialog() {
        hideSoftKeyboard();
        hideSoftKeyboard3();
        if (selectTypeOptions == null || selectTypeOptions.size() == 0) {
            ToastUtil.toast("数据为空或者数据获取失败，请重试！");
            return;
        }
        PickerViewUtils.selectOptions(this, "分类", selectTypeOptions, null, null, new PickerViewSelectOptionsResult() {
            @Override
            public void getOptionsResult(int options1, int options2, int options3) {
                tvSelectClass.setText("");
                classStr = "";
                tvSelectType.setText(selectTypeOptions.get(options1));
                typeStr = typeList.get(options1).getDataValue();
                getClassData();
            }
        });
    }

    private void getClassData() {
        classList.clear();
        GetDictDataHttp.getDictData(this, "", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    classList.addAll(list);
                    selectClassOptions.clear();
                    if (classList != null) {
                        for (DictInfo c : classList) {
                            selectClassOptions.add(c.getDataName());
                        }
                    }
                }
            }
        });




    }


    private void submit() {
        if (StringUtil.isEmpty(etTheme.getText().toString().trim())) {
            ToastUtil.toast("请填写知识主题");
            return;
        }
        if (StringUtil.isEmpty(typeStr)) {
            ToastUtil.toast("请选择事件分类定义");
            return;
        }
        if (StringUtil.isEmpty(classStr)) {
            ToastUtil.toast("请选择故障类型");
            return;
        }
        if (StringUtil.isEmpty(etAppearance.getText().toString().trim())) {
            ToastUtil.toast("请填写故障现象");
            return;
        }
        if (StringUtil.isEmpty(etSolution.getText().toString().trim())) {
            ToastUtil.toast("请填写解决方案");
            return;
        }
        KnowledgeModel model = new KnowledgeModel();
        model.setTheme(etTheme.getText().toString());
        model.setKnowledgeClass(classStr);
        model.setKnowledgeType(typeStr);
        model.setAppearance(etAppearance.getText().toString());
        model.setSolution(etSolution.getText().toString());

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("entity", FastJsonUtil.toJSONString(model));
        ApiClient.requestNetPost(this, AppConfig.OpKnowledgeManagerCreate, "提交中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_KNOWLEDGE_LIST));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

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

}
