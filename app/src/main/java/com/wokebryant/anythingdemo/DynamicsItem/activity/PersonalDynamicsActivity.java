package com.wokebryant.anythingdemo.DynamicsItem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.DynamicsItem.adapter.DynamicsAdapter;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsModel;
import com.wokebryant.anythingdemo.DynamicsItem.model.MockDataUtil;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.ReclycleViewUtil;

import java.util.concurrent.ConcurrentHashMap;

public class PersonalDynamicsActivity extends AppCompatActivity {

    //刚进入列表时统计当前屏幕可见views
    private boolean isFirstVisible = true;

    //用于统计曝光量的map
    private ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();

    private RecyclerView mDynamicsRecycleView;
    private DynamicsAdapter mDynamicsAdapter;
    private DynamicsModel mDynamicsModel;

    public static void launch(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PersonalDynamicsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lf_dynamics_layout);
        initView();
        setData();
    }

    private void initView() {
        mDynamicsRecycleView = findViewById(R.id.lf_dynamics_view);
    }

    private void setData() {
        mDynamicsModel = MockDataUtil.getDynamicsData();
        mDynamicsAdapter = new DynamicsAdapter(PersonalDynamicsActivity.this, mDynamicsModel.data);
        mDynamicsAdapter.setOnItemClickListener(mOnItemClickListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PersonalDynamicsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDynamicsRecycleView.setLayoutManager(layoutManager);
        mDynamicsRecycleView.addOnScrollListener(scrollListener);
        mDynamicsRecycleView.setAdapter(mDynamicsAdapter);
    }

    public void playShortVideo(String url) {
        Toast.makeText(PersonalDynamicsActivity.this, url, Toast.LENGTH_SHORT).show();
    }

    private DynamicsAdapter.OnItemClickListener mOnItemClickListener = new DynamicsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            /*
                我这里通过的是停止滚动后屏幕上可见view。如果滚动过程中的可见view也要统计，你可以根据newState去做区分
                SCROLL_STATE_IDLE:停止滚动
                SCROLL_STATE_DRAGGING: 用户慢慢拖动
                SCROLL_STATE_SETTLING：惯性滚动
                */
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                ReclycleViewUtil.getVisibleViews(recyclerView);
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            //刚进入列表时统计当前屏幕可见views
            if (isFirstVisible) {
                ReclycleViewUtil.getVisibleViews(recyclerView);
                isFirstVisible = false;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
