package com.wokebryant.anythingdemo.DynamicsItem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.DynamicsItem.adapter.DynamicsAdapter;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsModel;
import com.wokebryant.anythingdemo.DynamicsItem.model.MockDataUtil;
import com.wokebryant.anythingdemo.R;

public class PersonalDynamicsActivity extends AppCompatActivity {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
