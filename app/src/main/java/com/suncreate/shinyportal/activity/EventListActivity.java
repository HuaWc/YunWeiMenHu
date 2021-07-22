package com.suncreate.shinyportal.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.suncreate.shinyportal.R;
import com.suncreate.shinyportal.adapter.ViewPageAdapter;
import com.suncreate.shinyportal.base.BaseActivity;
import com.suncreate.shinyportal.fragment.EventListFragment;
import com.suncreate.shinyportal.util.EventUtil;
import com.zds.base.entity.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christ on 2021/7/21.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class EventListActivity extends BaseActivity {


    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.all)
    LinearLayout all;

    private String[] title;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPageAdapter mAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_event_list);
    }

    @Override
    protected void initLogic() {
        initTab();
        initBar();
        bar.setBackgroundColor(getResources().getColor(R.color.main_bar_color));
        initClick();
    }

    private void initClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initTab() {
        for (int i = 0; i <= 2; i++) {
            EventListFragment fragment = new EventListFragment();
            Bundle args = new Bundle();
            args.putInt("type", i);
            fragment.setArguments(args);
            fragments.add(fragment);
        }

        title = new String[]{"我参与的", "我关注的", "全部"};
        mAdapter = new ViewPageAdapter(getSupportFragmentManager(), this, fragments, title);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        tabLayout.setxTabDisplayNum(3);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_EVENT_LIST0));
                        break;
                    case 1:
                        EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_EVENT_LIST1));
                        break;
                    case 2:
                        EventBus.getDefault().post(new EventCenter(EventUtil.REFRESH_EVENT_LIST2));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
