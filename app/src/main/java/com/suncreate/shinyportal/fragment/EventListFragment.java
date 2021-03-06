package com.suncreate.shinyportal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.activity.EventDetailActivity;
import com.suncreate.shinyportal.activity.EventToEvaluateActivity;
import com.suncreate.shinyportal.activity.EventToTrackActivity;
import com.suncreate.shinyportal.adapter.EventMenuAdapter;
import com.suncreate.shinyportal.adapter.SelectOptionsChildAdapter;
import com.suncreate.shinyportal.base.BaseFragment;
import com.suncreate.shinyportal.entity.ColletEntity;
import com.suncreate.shinyportal.entity.DAPcs;
import com.suncreate.shinyportal.entity.DictInfo;
import com.suncreate.shinyportal.entity.EventMenuInfo;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.CollectEventHttp;
import com.suncreate.shinyportal.http.GetDictDataHttp;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.interfaces.PickerViewSelectOptionsResult;
import com.suncreate.shinyportal.util.EventUtil;
import com.suncreate.shinyportal.util.PickerViewUtils;
import com.suncreate.shinyportal.util.RecyclerViewHelper;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.suncreate.shinyportal.util.PickerViewUtils.lineSpace;

/**
 * Created by Christ on 2021/7/21.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class EventListFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_ss)
    ImageView ivSs;
    @BindView(R.id.tv_fj)
    TextView tvFj;
    @BindView(R.id.tv_pcs)
    TextView tvPcs;
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
    @BindView(R.id.rv4)
    RecyclerView rv4;
    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.rv5)
    RecyclerView rv5;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.tv_reset_time)
    TextView tvResetTime;


    private List<EventMenuInfo> mList;
    private EventMenuAdapter adapter;

    private int page = 1;
    private int pageSize = 10;


    private List<DictInfo> mList1;
    private SelectOptionsChildAdapter adapter1;
    private String value1 = "";
    private String name1 = "";


    private List<DictInfo> mList2;
    private SelectOptionsChildAdapter adapter2;
    private String value2 = "";
    private String name2 = "";

    private List<DictInfo> mList3;
    private SelectOptionsChildAdapter adapter3;
    private String value3 = "";
    private String name3 = "";

    private List<DictInfo> mList4;
    private SelectOptionsChildAdapter adapter4;
    private String value4 = "";
    private String name4 = "";

    private List<DictInfo> mList5;
    private SelectOptionsChildAdapter adapter5;
    private String value5 = "";
    private String name5 = "";

    private String startStr = "";
    private String endStr = "";

    private Date startDate;
    private Date endDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private List<DictInfo> mList6;
    private List<DAPcs> mList7;

    private List<String> fjList;
    private List<String> pcsList;

    private String selectedorgIds = "";

    private int type;//0 ????????????  1 ????????????  2 ??????

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initLogic() {
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
        GetDictDataHttp.getDictData(getContext(), "PT_ADMINISTRATIVE_EREA", new GetDictDataHttp.GetDictDataResult() {
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
        ApiClient.requestNetGet(getContext(), AppConfig.pcsList, "", hashMap, new ResultListener() {
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
                PickerViewUtils.selectOptions(getContext(), "??????", fjList, null, null, new PickerViewSelectOptionsResult() {
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
                PickerViewUtils.selectOptions(getContext(), "?????????", pcsList, null, null, new PickerViewSelectOptionsResult() {
                    @Override
                    public void getOptionsResult(int options1, int options2, int options3) {
                        tvPcs.setText(pcsList.get(options1));
                        selectedorgIds = mList7.get(options1).getId() + "";
                        getData(false);
                    }
                });
            }
        });
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llSelect.getVisibility() == View.GONE) {
                    llSelect.setVisibility(View.VISIBLE);
                    show(llSelect);
                    getActivity().runOnUiThread(new Runnable() {
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
                    getActivity().runOnUiThread(new Runnable() {
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
                    getActivity().runOnUiThread(new Runnable() {
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
        tvTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                hideSoftKeyboard3();
                //????????????
                Calendar nowDate = Calendar.getInstance();
                //???????????????
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
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
                TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
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

    private void getDictData1() {
        GetDictDataHttp.getDictData(getContext(), "OP_ASSET_TYPE", new GetDictDataHttp.GetDictDataResult() {
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
        GetDictDataHttp.getDictData(getContext(), value1, new GetDictDataHttp.GetDictDataResult() {
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
        GetDictDataHttp.getDictData(getContext(), value2, new GetDictDataHttp.GetDictDataResult() {
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
        mList4.clear();
        GetDictDataHttp.getDictData(getContext(), "OP_ALARM_SOURCE", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList4.addAll(list);
                    adapter4.notifyDataSetChanged();
                }
            }
        });
    }

    private void getDictData5() {
        mList5.clear();
        GetDictDataHttp.getDictData(getContext(), "OP_ALARM_SEND_STATUS", new GetDictDataHttp.GetDictDataResult() {
            @Override
            public void getData(List<DictInfo> list) {
                if (list != null) {
                    mList5.addAll(list);
                    adapter5.notifyDataSetChanged();
                }
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
                    name1 = mList1.get(position).getDataName();
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
        rv1.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
                    name2 = mList2.get(position).getDataName();
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
        rv2.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
                    name3 = mList3.get(position).getDataName();


                } else {
                    //????????????
                    mList3.get(position).setSelected(false);
                    adapter3.notifyItemChanged(position);
                    value3 = "";
                }
            }
        });
        rv3.setAdapter(adapter3);
        rv3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv3);

        mList4 = new ArrayList<>();
        adapter4 = new SelectOptionsChildAdapter(mList4);
        adapter4.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList4.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList4) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter4.notifyItemChanged(mList4.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList4.get(position).setSelected(true);
                    adapter4.notifyItemChanged(position);
                    value4 = mList4.get(position).getDataValue();
                    name4 = mList4.get(position).getDataName();

                } else {
                    //????????????
                    mList4.get(position).setSelected(false);
                    adapter4.notifyItemChanged(position);
                    value4 = "";
                }
            }
        });
        rv4.setAdapter(adapter4);
        rv4.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv4);
        getDictData4();

        mList5 = new ArrayList<>();
        adapter5 = new SelectOptionsChildAdapter(mList5);
        adapter5.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mList5.get(position).isSelected()) {
                    //???????????????
                    for (DictInfo dictInfo : mList5) {
                        if (dictInfo.isSelected()) {
                            dictInfo.setSelected(false);
                            adapter5.notifyItemChanged(mList5.indexOf(dictInfo));
                            break;
                        }
                    }
                    mList5.get(position).setSelected(true);
                    adapter5.notifyItemChanged(position);
                    value5 = mList5.get(position).getDataValue();
                    name5 = mList5.get(position).getDataName();

                } else {
                    //????????????
                    mList5.get(position).setSelected(false);
                    adapter5.notifyItemChanged(position);
                    value5 = "";
                }
            }
        });
        rv5.setAdapter(adapter5);
        rv5.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RecyclerViewHelper.recyclerviewAndScrollView(rv5);
        getDictData5();


        mList = new ArrayList<>();
        adapter = new EventMenuAdapter(mList, type == 0 ? 1 : 0);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("alarmId", mList.get(position).getId());
                toTheActivity(EventDetailActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mList.get(position).getId());
                bundle.putString("str1", mList.get(position).getMap().getAssetName());
                bundle.putString("str2", mList.get(position).getMap().getOrgName());
                bundle.putString("str3", mList.get(position).getMap().getAlarmTime());
                bundle.putString("str4", mList.get(position).getMap().getRecoveryTime());

                switch (view.getId()) {
                    case R.id.tv_look:
                        toTheActivity(EventToTrackActivity.class, bundle);
                        break;
                    case R.id.tv_evaluate:
                        toTheActivity(EventToEvaluateActivity.class, bundle);
                        break;
                    case R.id.iv_sc:
                        if (mList.get(position).getMap().getIsCollect() != null && mList.get(position).getMap().getIsCollect() == 1) {
                            //????????????????????????
                            CollectEventHttp.del(getContext(), mList.get(position).getMap().getCollectId(), new CollectEventHttp.CollectResult() {
                                @Override
                                public void result(String json) {
                                    if (type == 1) {
                                        //????????????
                                        mList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        mList.get(position).getMap().setIsCollect(0);
                                        mList.get(position).getMap().setCollectId("");
                                        adapter.notifyItemChanged(position);
                                    }
                                }
                            });
                        } else {
                            //????????????????????????
                            CollectEventHttp.collect(getContext(), mList.get(position).getId(), new CollectEventHttp.CollectResult() {
                                @Override
                                public void result(String json) {
                                    ColletEntity info = FastJsonUtil.getObject(json, ColletEntity.class);
                                    mList.get(position).getMap().setIsCollect(1);
                                    mList.get(position).getMap().setCollectId(info.getId());
                                    adapter.notifyItemChanged(position);
                                }
                            });
                        }
                        break;
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
        params.put("pageNum", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        switch (type) {
            case 0:
                params.put("listType", "partIn");
                break;
            case 1:
                params.put("listType", "collect");
                break;
            case 2:
                params.put("listType", "all");
                break;
        }
        if (!StringUtil.isEmpty(etSearch.getText().toString().trim())) {
            params.put("keyWords", etSearch.getText().toString().trim());
        }
        if (!StringUtil.isEmpty(selectedorgIds)) {
            params.put("selectedorgIds", selectedorgIds);
        }
        if (!StringUtil.isEmpty(value1)) {
            params.put("assetNature", name1);
        }
        if (!StringUtil.isEmpty(value2)) {
            params.put("assetType", name2);
        }
        if (!StringUtil.isEmpty(value3)) {
            params.put("assetClass", name3);
        }
        if (!StringUtil.isEmpty(value4)) {
            params.put("alarmSource", name4);
        }
        if (!StringUtil.isEmpty(value5)) {
            params.put("alarmStatus", name5);
        }
        if (!StringUtil.isEmpty(startStr)) {
            params.put("startTime", startStr);
        }
        if (!StringUtil.isEmpty(endStr)) {
            params.put("endTime", endStr);
        }
        ApiClient.requestNetGet(getContext(), AppConfig.EventTrackList, "?????????", params, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<EventMenuInfo> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), EventMenuInfo.class);
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


    @Override
    protected void onEventComing(EventCenter center) {
        switch (center.getEventCode()) {
            case EventUtil.REFRESH_EVENT_LIST0:
                if (type == 0) {
                    getData(false);
                }
                break;
            case EventUtil.REFRESH_EVENT_LIST1:
                if (type == 1) {
                    getData(false);
                }
                break;
            case EventUtil.REFRESH_EVENT_LIST2:
                if (type == 2) {
                    getData(false);
                }
                break;
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        type = extras.getInt("type", 0);
    }
}
