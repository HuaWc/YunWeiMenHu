package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.SelectOptionsChildAdapter;
import com.suncreate.shinyportal.adapter.WorkOrderUserAdapter;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.base.MyApplication;
import com.suncreate.shinyportal.entity.DAPcs;
import com.suncreate.shinyportal.entity.DictInfo;
import com.suncreate.shinyportal.entity.WorkOrderUser;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.GetDictDataHttp;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.interfaces.PickerViewSelectOptionsResult;
import com.suncreate.shinyportal.util.EventUtil;
import com.suncreate.shinyportal.util.PickerViewUtils;
import com.suncreate.shinyportal.util.RecyclerViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.suncreate.shinyportal.util.PickerViewUtils.lineSpace;

/**
 * ???????????? ????????????
 */
public class WorkOrderManagementUserActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_dbsj)
    TextView tvDbsj;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_fj)
    TextView tvFj;
    @BindView(R.id.tv_pcs)
    TextView tvPcs;
    @BindView(R.id.tv_clzt)
    TextView tvClzt;
    @BindView(R.id.tv_shzt)
    TextView tvShzt;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.tv_more_blue)
    TextView tvMoreBlue;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.tv_title_main)
    TextView tvTitleMain;
    @BindView(R.id.tv_reset_time)
    TextView tvResetTime;
    @BindView(R.id.rv4)
    RecyclerView rv4;

    private int page = 1;
    private int pageSize = 10;


    List<WorkOrderUser> mList;
    WorkOrderUserAdapter adapter;

    private List<DictInfo> mList1;
    private SelectOptionsChildAdapter adapter1;
    private String value1 = "";

    private List<DictInfo> mList2;
    private SelectOptionsChildAdapter adapter2;
    private String value2 = "";

    private List<DictInfo> mList3;
    private SelectOptionsChildAdapter adapter3;
    private String value3 = "";

    private String startStr = "";
    private String endStr = "";

    private Date startDate;
    private Date endDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private List<DictInfo> mList4;
    private List<String> optionsList4;
    private String value4 = "";

    private List<DictInfo> mList5;
    private List<String> optionsList5;
    private String value5 = "";

    private List<String> userTypes;

    private List<DictInfo> mList6;
    private List<DAPcs> mList7;

    private List<String> fjList;
    private List<String> pcsList;

    private String selectedorgIds = "";

    private boolean isUpcoming = false;

    private List<DictInfo> mList8;
    private SelectOptionsChildAdapter adapter8;
    private String value8 = "";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_work_order_management_user);

    }

    @Override
    protected void initLogic() {
        if (isUpcoming) {
            tvTitleMain.setText("????????????");
            tvDbsj.setVisibility(View.GONE);
        }
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
        initAdapter();
        initFjData();
    }

    private void initFjData() {
        mList6 = new ArrayList<>();
        mList7 = new ArrayList<>();
        fjList = new ArrayList<>();
        pcsList = new ArrayList<>();

        getFjData();
    }

    private void getFjData() {
        mList6.clear();
        fjList.clear();
        GetDictDataHttp.getDictData(this, "PT_ADMINISTRATIVE_EREA", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList6.addAll(list);
                    fjList.add("??????");
                    for (DictInfo d : list) {
                        fjList.add(d.getDataName());
                    }
                }
            }
        });
    }

    private void getPcsData(String id) {
        mList7.clear();
        pcsList.clear();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("areaCode", id);
        ApiClient.requestNetGet(this, AppConfig.pcsList, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<DAPcs> list = FastJsonUtil.getList(json, DAPcs.class);
                if (list != null && list.size() != 0) {
                    mList7.addAll(list);
                    //?????????????????????
                    for (DAPcs d : list) {
                        pcsList.add(d.getOrgName());
                    }
                }

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void initAdapter() {
        mList1 = new ArrayList<>();
        adapter1 = new SelectOptionsChildAdapter(mList1);
        adapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList1.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList1) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter1.notifyItemChanged(mList1.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList1.get(position).setSelected(true);
                    adapter1.notifyItemChanged(position);
                    value1 = mList1.get(position).getDataValue();
                    value2 = "";
                    value3 = "";
                    getDictData2();
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                } else {
                    //????????????
                    mList1.get(position).setSelected(false);
                    adapter1.notifyItemChanged(position);
                    value1 = "";
                    value2 = "";
                    value3 = "";
                    mList2.clear();
                    adapter2.notifyDataSetChanged();
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                }
            }
        });
        rv1.setAdapter(adapter1);
        rv1.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv1);
        getDictData1();


        mList2 = new ArrayList<>();
        adapter2 = new SelectOptionsChildAdapter(mList2);
        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList2.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList2) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter2.notifyItemChanged(mList2.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList2.get(position).setSelected(true);
                    adapter2.notifyItemChanged(position);
                    value2 = mList2.get(position).getDataValue();
                    value3 = "";
                    getDictData3();
                } else {
                    //????????????
                    mList2.get(position).setSelected(false);
                    adapter2.notifyItemChanged(position);
                    value2 = "";
                    value3 = "";
                    mList3.clear();
                    adapter3.notifyDataSetChanged();
                }
            }
        });
        rv2.setAdapter(adapter2);
        rv2.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv2);

        mList3 = new ArrayList<>();
        adapter3 = new SelectOptionsChildAdapter(mList3);
        adapter3.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList3.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList3) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter3.notifyItemChanged(mList3.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList3.get(position).setSelected(true);
                    adapter3.notifyItemChanged(position);
                    value3 = mList3.get(position).getDataValue();

                } else {
                    //????????????
                    mList3.get(position).setSelected(false);
                    adapter3.notifyItemChanged(position);
                    value3 = "";
                }
            }
        });
        rv3.setAdapter(adapter3);
        rv3.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv3);

        mList4 = new ArrayList<>();
        optionsList4 = new ArrayList<>();
        getDictData4();

        mList5 = new ArrayList<>();
        optionsList5 = new ArrayList<>();
        getDictData5();

        mList8 = new ArrayList<>();
        adapter8 = new SelectOptionsChildAdapter(mList8);
        adapter8.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList8.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList8) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter8.notifyItemChanged(mList8.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList8.get(position).setSelected(true);
                    adapter8.notifyItemChanged(position);
                    value8 = mList8.get(position).getDataValue();

                } else {
                    //????????????
                    mList8.get(position).setSelected(false);
                    adapter8.notifyItemChanged(position);
                    value8 = "";
                }
            }
        });
        rv4.setAdapter(adapter8);
        rv4.setLayoutManager(new GridLayoutManager(this, 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv4);
        getDictData8();

        getUserTypes();
    }


    private void getUserTypes() {
        userTypes = new ArrayList<>();
        Map<String, Object> hashMap = new HashMap<>();
        ApiClient.requestNetGet(this, AppConfig.getLoginRoleType, "?????????", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //??????????????????????????????????????????????????????????????????
                List<String> list = Arrays.asList(json.split(","));
                userTypes.addAll(list);
                initMainAdapter();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initMainAdapter() {
        mList = new ArrayList<>();
        adapter = new WorkOrderUserAdapter(mList, userTypes);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                WorkOrderUser item = mList.get(position);
                toTheActivity(WorkOrderDetailActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                WorkOrderUser item = mList.get(position);
                switch (view.getId()) {
                    case R.id.tv_look:
                        //??????
                        toTheActivity(WorkOrderDetailActivity.class, bundle);
                        //toTheActivity(EventDetailAfterReviewActivity.class, bundle);
                        break;
                    case R.id.tv_do:
                        //??????
                        if ((StringUtil.isEmpty(item.getExp1()) || !item.getExp1().equals("QUESTION_REPORT"))
                                && (StringUtil.isEmpty(item.getMap().getHandleStatusZibiao()) || !item.getMap().getHandleStatusZibiao().equals("CHANGE_DEAL"))
                                && (item.getHandleStatus() != null && (item.getHandleStatus().equals("UN_DEAL") || item.getHandleStatus().equals("DEALING") || item.getHandleStatus().equals("HELP_DEAL") || item.getHandleStatus().equals("CHANGE_DEAL")))) {
                            toTheActivity(WorkOrderHandleActivity.class, bundle);
                        }
                        break;
                    case R.id.tv_track:
                        //??????
                        toTheActivity(WorkOrderTrackActivity.class, bundle);
                        break;
                    case R.id.tv_check:
                        //??????
                        if ((item.getHandleStatus() != null && item.getHandleStatus().equals("DEALED")) && (item.getVerifyStatus() != null && (item.getVerifyStatus().equals("UN_AUDITED") || item.getVerifyStatus().equals("'AUDITING")))) {
                            toTheActivity(WorkOrderProcessAuditActivity.class, bundle);
                        }
                        break;
                    case R.id.tv_submit:
                        //????????????
                        if ((item.getExp1() != null && item.getExp1().equals("QUESTION_REPORT")) && (item.getHandleStatus() != null && item.getHandleStatus().equals("DEALING"))) {
                            toTheActivity(WorkOrderProblemUpActivity.class, bundle);
                        }
                }
            }
        });
        getData(false);
    }

    private void getData(boolean more) {
        if (more) {
            page++;
        } else {
            page = 1;
            mList.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", MyApplication.getInstance().getUserInfo().getId());
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keyWords", etSearch.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(selectedorgIds)) {
            params.put("selectedorgIds", selectedorgIds);
        }
        if (!StringUtil.isEmpty(value1)) {
            params.put("assetNature", value1);
        }
        if (!StringUtil.isEmpty(value2)) {
            params.put("assetType", value2);
        }
        if (!StringUtil.isEmpty(value3)) {
            params.put("assetClass", value3);
        }
        if (!StringUtil.isEmpty(value4)) {
            params.put("handleStatus", value4);
        }
        if (!StringUtil.isEmpty(value5)) {
            params.put("verifyStatus", value5);
        }
        if (!StringUtil.isEmpty(value8)) {
            params.put("alarmSource", value8);
        }
        if (!StringUtil.isEmpty(startStr)) {
            params.put("alarmTimeStart", startStr);
        }
        if (!StringUtil.isEmpty(endStr)) {
            params.put("alarmTimeEnd", endStr);
        }
        if (isUpcoming) {
            params.put("isTodoThings", 1);
        }
        ApiClient.requestNetGet(this, AppConfig.OpFaultInfoList, "?????????", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<WorkOrderUser> list = FastJsonUtil.getList(FastJsonUtil.getString(FastJsonUtil.getString(json, "pageInfo"), "list"), WorkOrderUser.class);
                if (list != null && list.size() != 0) {
                    mList.addAll(list);
                } else {
                    if (page > 1) {
                        page--;
                    }
                }
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(String msg) {
                if (page > 1) {
                    page--;
                }
                ToastUtil.toast(msg);
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();

            }
        });
    }

    private void initClick() {
        tvResetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTimeStart.setText("");
                tvTimeEnd.setText("");
            }
        });
        tvFj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (fjList == null || fjList.size() == 0) {
                    ToastUtil.toast("???????????????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "??????", fjList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvFj.setText(fjList.get(options1));
                        tvPcs.setText("");
                        if (options1 != 0) {
                            getPcsData(mList6.get(options1 - 1).getDataValue());
                            selectedorgIds = mList6.get(options1 - 1).getDataValue();
                        } else {
                            mList7.clear();
                            pcsList.clear();
                            selectedorgIds = "";
                        }
                        getData(false);
                    }
                });
            }
        });
        tvPcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (pcsList == null || pcsList.size() == 0) {
                    ToastUtil.toast("????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "?????????", pcsList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvPcs.setText(pcsList.get(options1));
                        selectedorgIds = mList7.get(options1).getId() + "";
                        getData(false);
                    }
                });
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(false);
            }
        });
        tvDbsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isUpcoming", true);
                toTheActivity(WorkOrderManagementUserActivity.class, bundle);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.GONE) {
                    llSelect.setVisibility(View.VISIBLE);
                    show(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMore.setVisibility(View.GONE);
                            tvMoreBlue.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        tvMoreBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.VISIBLE) {
                    llSelect.setVisibility(View.GONE);
                    hide(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMoreBlue.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    });
                }
                getData(false);
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.VISIBLE) {
                    llSelect.setVisibility(View.GONE);
                    hide(llSelect);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMoreBlue.setVisibility(View.GONE);
                            tvMore.setVisibility(View.VISIBLE);
                        }
                    });
                }
                getData(false);
            }
        });
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                Calendar nowDate = Calendar.getInstance();
                //???????????????
                TimePickerView pvTime = new TimePickerBuilder(WorkOrderManagementUserActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (endDate != null && date.getTime() > endDate.getTime()) {
                            ToastUtil.toast("???????????????????????????????????????");
                            return;
                        }
                        startStr = formatter.format(date);//?????? String
                        startDate = date;
                        tvTimeStart.setText(startStr);
                        //getData(false);


                    }
                }).setDate(nowDate)//?????????????????????????????????
                        .setType(new boolean[]{true, true, true, true, true, true})//???????????????????????????????????? true:?????? false:??????
                        //.setLabel("???", "???", "???", "???", "???", "???")
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        .setDividerColor(0x1F191F25)//?????????????????????
                        .isCyclic(false)//???????????????????????? ???????????????31???????????????1??? ????????????????????????????????????????????????
                        .setSubmitColor(0xFFF79D1F)//????????????????????????
                        .setCancelColor(0xFFA3A5A8)//????????????????????????
                        .setTitleText("????????????")//????????????
                        .setTitleColor(0xFF191F25)//??????????????????
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });
        tvTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                Calendar nowDate = Calendar.getInstance();
                //???????????????
                TimePickerView pvTime = new TimePickerBuilder(WorkOrderManagementUserActivity.this, new OnTimeSelectListener() {
                    public void onTimeSelect(final Date date, View v) {
                        if (startDate != null && date.getTime() < startDate.getTime()) {
                            ToastUtil.toast("???????????????????????????????????????");
                            return;
                        }
                        endStr = formatter.format(date);//?????? String
                        endDate = date;
                        tvTimeEnd.setText(endStr);
                        //getData(false);


                    }
                }).setDate(nowDate)//?????????????????????????????????
                        .setType(new boolean[]{true, true, true, true, true, true})//???????????????????????????????????? true:?????? false:??????
                        //.setLabel("???", "???", "???", "???", "???", "???")
                        .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                        .setDividerColor(0x1F191F25)//?????????????????????
                        .isCyclic(false)//???????????????????????? ???????????????31???????????????1??? ????????????????????????????????????????????????
                        .setSubmitColor(0xFFF79D1F)//????????????????????????
                        .setCancelColor(0xFFA3A5A8)//????????????????????????
                        .setTitleText("????????????")//????????????
                        .setTitleColor(0xFF191F25)//??????????????????
                        .setLineSpacingMultiplier(lineSpace)
                        .build();
                pvTime.show();
            }
        });
        ivSs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData(false);
            }
        });
        tvClzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (optionsList4 == null || optionsList4.size() == 0) {
                    ToastUtil.toast("??????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "??????", optionsList4, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvClzt.setText(optionsList4.get(options1));
                        if (options1 == 0) {
                            value4 = "";
                        } else {
                            value4 = mList4.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
        tvShzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                if (optionsList5 == null || optionsList5.size() == 0) {
                    ToastUtil.toast("??????????????????????????????");
                    return;
                }
                PickerViewUtils.selectOptions(WorkOrderManagementUserActivity.this, "??????", optionsList5, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvShzt.setText(optionsList5.get(options1));
                        if (options1 == 0) {
                            value5 = "";
                        } else {
                            value5 = mList5.get(options1 - 1).getDataValue();
                        }
                        getData(false);
                    }
                });
            }
        });
    }

    private void getDictData1() {
        GetDictDataHttp.getDictData(this, "OP_ASSET_TYPE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList1.addAll(list);
                    adapter1.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData2() {
        mList2.clear();
        GetDictDataHttp.getDictData(this, value1, new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList2.addAll(list);
                    adapter2.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData3() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, value2, new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList3.addAll(list);
                    adapter3.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData4() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, "OP_FAULT_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList4.addAll(list);
                    optionsList4.add("??????");
                    for (DictInfo op : list) {
                        optionsList4.add(op.getDataName());
                    }
                }
            }
        });
    }


    private void getDictData5() {
        mList3.clear();
        GetDictDataHttp.getDictData(this, "OP_VERIFY_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList5.addAll(list);
                    optionsList5.add("??????");
                    for (DictInfo op : list) {
                        optionsList5.add(op.getDataName());
                    }
                }
            }
        });
    }

    private void getDictData8() {
        GetDictDataHttp.getDictData(this, "OP_ALARM_SOURCE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList8.addAll(list);
                    adapter8.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_FAULT_LIST:
                getData(false);
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        isUpcoming = extras.getBoolean("isUpcoming", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void show(View view) {
        // ????????????
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, -0.0f);
        mShowAction.setRepeatMode(Animation.REVERSE);
        mShowAction.setDuration(500);
        view.startAnimation(mShowAction);
    }

    public void hide(View view) {
        // ????????????
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(500);
        view.startAnimation(mHiddenAction);
    }

    //??????onBackPressed()??????,????????????????????????
    @Override
    public void onBackPressed() {

        if (llSelect.getVisibility() == View.VISIBLE) {
            llSelect.setVisibility(View.GONE);
            hide(llSelect);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMoreBlue.setVisibility(View.GONE);
                    tvMore.setVisibility(View.VISIBLE);
                }
            });
        } else {
            finish();
        }
    }
}
