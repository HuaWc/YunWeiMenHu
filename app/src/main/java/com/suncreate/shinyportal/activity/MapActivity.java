package com.suncreate.shinyportal.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.AdapterMapSearchItem;
import com.suncreate.shinyportal.adapter.NewTreeAdapter;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.entity.MapSearchItem;
import com.suncreate.shinyportal.entity.NewTreeItem;
import com.suncreate.shinyportal.entity.TreeCamera;
import com.suncreate.shinyportal.entity.TreeItem;
import com.suncreate.shinyportal.http.ApiClient;
import com.suncreate.shinyportal.http.AppConfig;
import com.suncreate.shinyportal.http.ResultListener;
import com.suncreate.shinyportal.util.GDLocationUtil;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.EngineType;
import com.supermap.data.Point2D;
import com.supermap.data.Workspace;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.MapControl;
import com.supermap.mapping.MapView;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/27.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class MapActivity extends BaseActivity {
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_locate)
    RelativeLayout rlLocate;
    @BindView(R.id.cv_jg1)
    CardView cvJg1;
    @BindView(R.id.cv_search1)
    CardView cvSearch1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.ll_jg2)
    LinearLayout llJg2;
    @BindView(R.id.rv_jg)
    RecyclerView rvJg;
    @BindView(R.id.cv_jg2)
    CardView cvJg2;
    @BindView(R.id.cv_search2)
    CardView cvSearch2;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.iv_back3)
    ImageView ivBack3;
    @BindView(R.id.et_search3)
    EditText etSearch3;
    @BindView(R.id.iv_search3)
    ImageView ivSearch3;
    @BindView(R.id.cv_search3)
    CardView cvSearch3;
    @BindView(R.id.rv_search_relative)
    RecyclerView rvSearchRelative;
    @BindView(R.id.cv_search_relative)
    CardView cvSearchRelative;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.all)
    LinearLayout all;
    @BindView(R.id.Map_view)
    MapView m_mapView;
    @BindView(R.id.zoomControls)
    ZoomControls zoomControls;


    private List<MapSearchItem> searchItems;
    private AdapterMapSearchItem adapterMapSearchItem;

    private List<NewTreeItem> treeItems;
    private NewTreeAdapter treeAdapter;

    private int positionNow;

    private MapControl m_mapControl = null;
    private Workspace m_wokspace;

    private Map<String, CallOut> pointList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_map);
    }


    @Override
    protected void initLogic() {
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initNowPosition();
        initClick();
        initAdapter();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cvJg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ll1.setVisibility(View.GONE);
                        ll2.setVisibility(View.VISIBLE);
                        ll3.setVisibility(View.GONE);
                    }
                });
            }
        });
        cvSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.VISIBLE);
                etSearch3.requestFocus();
            }
        });
        llJg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
            }
        });
        cvSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.VISIBLE);
                etSearch3.requestFocus();
            }
        });
        ivBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                hideSoftKeyboard();
                hideSoftKeyboard3();
            }
        });
        ivSearch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //搜索功能实现
            }
        });

        rlLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locate();
            }
        });
        etSearch3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!StringUtil.isEmpty(editable.toString().trim())) {
                    search();
                } else {
                    searchItems.clear();
                    adapterMapSearchItem.notifyDataSetChanged();
                }
            }
        });
    }

    private void initAdapter() {

        searchItems = new ArrayList<>();
        adapterMapSearchItem = new AdapterMapSearchItem(searchItems);
        rvSearchRelative.setLayoutManager(new LinearLayoutManager(mContext));
        rvSearchRelative.setAdapter(adapterMapSearchItem);
        adapterMapSearchItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //搜索结果 直接进入相机详情
                Bundle bundle = new Bundle();
                bundle.putString("cameraId", searchItems.get(position).getId());
                // toTheActivity(DossierDetailActivity.class, bundle);
            }
        });

        treeItems = new ArrayList<>();
        treeAdapter = new NewTreeAdapter(treeItems, new NewTreeAdapter.ClickThirdResult() {
            @Override
            public void onClick(int position, TreeCamera camera, int positionChild) {
                //点击第三级的相机 直接移动视角 显示点位
                showSingleCamera(position, camera);
            }
        });
        rvJg.setLayoutManager(new LinearLayoutManager(mContext));
        rvJg.setAdapter(treeAdapter);
        treeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (treeItems.get(position).getLevel()) {
                    case 1:
                        treeItems.get(position).setExpand(!treeItems.get(position).isExpand());
                        if (treeItems.get(position).isExpand()) {
                            //收缩到打开
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(true);
                                }
                            }
                        } else {
                            //打开到收缩
                            for (NewTreeItem item : treeItems) {
                                if (item.getParentId() == treeItems.get(position).getId()) {
                                    item.setVisible(false);
                                }
                            }
                        }
                        treeAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        //先判断那没拿数据
                        if (treeItems.get(position).isHaveGet()) {
                            treeItems.get(position).setExpand(!treeItems.get(position).isExpand());
                            treeAdapter.notifyItemChanged(position);
                            if (treeItems.get(position).isExpand()) {
                                if (treeItems.get(position).getCameraList() == null || treeItems.get(position).getCameraList().size() == 0) {
                                    ToastUtil.toast("机构下暂无点位数据！");
                                } else {
                                    addCameraPointToMap(position);
                                }
                            }
                        } else {
                            getThirdTreeData(position);
                        }
                        break;
                }
            }
        });
        getTreeData();
    }

    private void getTreeData() {
        Map<String, Object> hashMap = new HashMap<>();
        // hashMap.put("selectAll", false);
        ApiClient.requestNetPost(this, AppConfig.tree, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<TreeItem> list = FastJsonUtil.getList(json, TreeItem.class);
                if (list != null) {
                    //加工数据
                    handleData(list);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void handleData(List<TreeItem> list) {
        int id = 1;
        for (int i = 0; i < list.size(); i++) {
            NewTreeItem newTreeItem = new NewTreeItem();
            newTreeItem.setId(id);
            newTreeItem.setLevel(1);
            newTreeItem.setVisible(true);
            newTreeItem.setName(list.get(i).getTitleName() + "(" + list.get(i).getTitleNumber() + ")");
            treeItems.add(newTreeItem);
            if (list.get(i).getOrgUtils() != null && list.get(i).getOrgUtils().size() != 0) {
                for (int j = 0; j < list.get(i).getOrgUtils().size(); j++) {
                    NewTreeItem newTreeItem2 = new NewTreeItem();
                    newTreeItem2.setParentId(id);
                    newTreeItem2.setLevel(2);
                    newTreeItem2.setName(list.get(i).getOrgUtils().get(j).getOrgName() + "(" + list.get(i).getOrgUtils().get(j).getOrgNumber() + ")");
                    newTreeItem2.setOrgCode(list.get(i).getOrgUtils().get(j).getOrgCode());
                    treeItems.add(newTreeItem2);
                }
            }
            id++;
        }
        treeAdapter.notifyDataSetChanged();

    }

    private void getThirdTreeData(int position) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("orgCode", treeItems.get(position).getOrgCode());
        ApiClient.requestNetPost(this, AppConfig.treeThree, "加载中", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                treeItems.get(position).setExpand(true);
                treeItems.get(position).setHaveGet(true);
                List<TreeCamera> list = FastJsonUtil.getList(json, TreeCamera.class);
                if (list != null && list.size() != 0) {
                    //装配第三级数据
                    if (treeItems.get(position).getCameraList() == null) {
                        treeItems.get(position).setCameraList(new ArrayList<>());
                    }
                    treeItems.get(position).getCameraList().addAll(list);
                    treeAdapter.notifyItemChanged(position);
                    addCameraPointToMap(position);
                } else {
                    ToastUtil.toast("机构下暂无点位数据！");
                }
            }


            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private void search() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("mapKeywords", etSearch3.getText().toString().trim());
        hashMap.put("pageNum", 1);
        hashMap.put("pageSize", 20);
        ApiClient.requestNetPost(this, AppConfig.mapKeywords, "", hashMap, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                List<MapSearchItem> list = FastJsonUtil.getList(FastJsonUtil.getString(json, "list"), MapSearchItem.class);
                searchItems.clear();
                if (list != null && list.size() != 0) {
                    searchItems.addAll(list);
                }
                adapterMapSearchItem.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void initNowPosition() {
        //初始化地图，定位，移动视角，定义地图点点击事件
        pointList = new HashMap<>();
        //打开工作空间
        m_wokspace = new Workspace();

        //将地图显示控件和工作空间关联
        m_mapControl = m_mapView.getMapControl();
        m_mapControl.getMap().setWorkspace(m_wokspace);


        DatasourceConnectionInfo dsInfo = new DatasourceConnectionInfo();
        dsInfo.setServer("http://112.29.172.100:8090/iserver/services/map-tpk-gljd/rest/maps/gljd");
        dsInfo.setEngineType(EngineType.Rest);
        dsInfo.setAlias("ChinaRest");
        Datasource ds = m_wokspace.getDatasources().open(dsInfo);
        if (ds != null) {
            m_mapControl.getMap().getLayers().add(ds.getDatasets().get(0), true);
            m_mapControl.getMap().refresh();
        } else {
            Toast.makeText(MapActivity.this, "打开数据源失败了", Toast.LENGTH_SHORT).show();
        }

        m_mapView.post(new Runnable() {
            @Override
            public void run() {
                m_mapControl.getMap().viewEntire();

            }
        });

        //放大按钮
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_mapControl.getMap().zoom(2);
                m_mapControl.getMap().refresh();
            }
        });

        //缩小按钮
        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_mapControl.getMap().zoom(0.5);
                m_mapControl.getMap().refresh();
            }
        });

        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {
                GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                    @Override
                    public void result(AMapLocation location) {
                        double longitude = location.getLongitude();
                        double latitude = location.getLatitude();
                        CallOut callOut = new CallOut(MapActivity.this);
                        callOut.setStyle(CalloutAlignment.BOTTOM);
                        callOut.setCustomize(true);
                        callOut.setLocation(longitude, latitude);

                        ImageView imageView = new ImageView(MapActivity.this);
                        imageView.setBackgroundResource(R.mipmap.dw);
                        imageView.setMaxWidth(40);
                        imageView.setMaxHeight(40);
                        callOut.setContentView(imageView);
                        pointList.put("location_my_point",callOut);
                        m_mapView.addCallout(callOut, "location_my_point");
                        m_mapControl.panTo(new Point2D(longitude, latitude), 300);
                        m_mapControl.getMap().zoom(10);
                        m_mapControl.getMap().refresh();
                    }
                });
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                ToastUtil.toast("未开启定位权限");
            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);


    }

    public void locate() {
        if (pointList.containsKey("location_my_point")) {
            double longitude = pointList.get("location_my_point").getLocationX();
            double latitude = pointList.get("location_my_point").getLocationY();
            m_mapControl.panTo(new Point2D(longitude, latitude), 300);
            m_mapControl.getMap().zoom(10);
            m_mapControl.getMap().refresh();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    GDLocationUtil.getLocation(new GDLocationUtil.MyLocationListener() {
                        @Override
                        public void result(AMapLocation location) {
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            CallOut callOut = new CallOut(MapActivity.this);
                            callOut.setStyle(CalloutAlignment.BOTTOM);
                            callOut.setCustomize(true);
                            callOut.setLocation(longitude, latitude);

                            ImageView imageView = new ImageView(MapActivity.this);
                            imageView.setBackgroundResource(R.mipmap.dw);
                            imageView.setMaxWidth(20);
                            imageView.setMaxHeight(20);
                            callOut.setContentView(imageView);
                            pointList.put("location_my_point",callOut);
                            m_mapView.addCallout(callOut, "location_my_point");
                            m_mapControl.panTo(new Point2D(longitude, latitude), 300);
                            m_mapControl.getMap().zoom(10);
                            m_mapControl.getMap().refresh();
                        }
                    });
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.toast("未开启定位权限");
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }


    }

    private void showSingleCamera(int position, TreeCamera camera) {
        positionNow = position;
        if (pointList.size() > 1) {
            for (String s : pointList.keySet()) {
                if (!s.equals("location_my_point")) {
                    m_mapView.removeCallOut(s);
                }
            }
        }
        if (StringUtil.isEmpty(camera.getLatitude()) || StringUtil.isEmpty(camera.getLongitude())) {
            ToastUtil.toast("该相机的坐标信息有误，无法显示！");
            return;
        }

        double longitude = Double.parseDouble(camera.getLongitude());
        double latitude = Double.parseDouble(camera.getLatitude());
        CallOut callOut = new CallOut(MapActivity.this);
        callOut.setStyle(CalloutAlignment.BOTTOM);
        callOut.setCustomize(true);
        callOut.setLocation(longitude, latitude);

        ImageView imageView = new ImageView(MapActivity.this);
        imageView.setBackgroundResource(R.mipmap.camera_point_icon);
        imageView.setMaxWidth(40);
        imageView.setMaxHeight(40);
        callOut.setContentView(imageView);
        pointList.put(camera.getCameraName(),callOut);
        m_mapView.addCallout(callOut, camera.getCameraName());
        m_mapControl.panTo(new Point2D(longitude, latitude), 300);
        m_mapControl.getMap().zoom(10);
        m_mapControl.getMap().refresh();

    }

    private void addCameraPointToMap(int position) {
        positionNow = position;
        if (pointList.size() > 1) {
            for (String s : pointList.keySet()) {
                if (!s.equals("location_my_point")) {
                    m_mapView.removeCallOut(s);
                }
            }
        }
        if (treeItems.get(position).getCameraList() != null && treeItems.get(position).getCameraList().size() > 0) {
            boolean haveMove = false;
            for (TreeCamera t : treeItems.get(position).getCameraList()) {
                if (StringUtil.isEmpty(t.getLatitude()) || StringUtil.isEmpty(t.getLongitude())) {
                    continue;
                }
                double longitude = Double.parseDouble(t.getLongitude());
                double latitude = Double.parseDouble(t.getLatitude());
                if (!haveMove) {
                    m_mapControl.panTo(new Point2D(longitude, latitude), 300);
                    m_mapControl.getMap().zoom(10);
                    haveMove = true;
                }


                CallOut callOut = new CallOut(MapActivity.this);
                callOut.setStyle(CalloutAlignment.BOTTOM);
                callOut.setCustomize(true);
                callOut.setLocation(longitude, latitude);

                ImageView imageView = new ImageView(MapActivity.this);
                imageView.setBackgroundResource(R.mipmap.camera_point_icon);
                imageView.setMaxWidth(40);
                imageView.setMaxHeight(40);
                callOut.setContentView(imageView);
                pointList.put(t.getCameraName(),callOut);
                m_mapView.addCallout(callOut, t.getCameraName());

            }
            m_mapControl.getMap().refresh();
        }

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
