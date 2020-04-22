package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.adapter.ChiefPanelAdapter;
import com.wokebryant.anythingdemo.model.ChiefPanelHostModel;
import com.wokebryant.anythingdemo.model.ChiefPanelManagerModel;
import com.wokebryant.anythingdemo.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class VoiceLiveChiefPanel extends BottomSheetDialogFragment implements View.OnClickListener{

    private static final String TAG = "VoiceLiveChiefPanel";
    private static final int maxListSize = 10;

    private ImageView mChiefAvatarIv;
    private TextView mChiefIdTv,mChiefReportTv,mChiefNickTv,mChiefFansTv;
    private FrameLayout mNoManagerFl,mNoHostFl;
    private RecyclerView mManagerRecycleView,mHostRecycleView;
    private Button mAttentionBtn;
    private Space mSpaceView;

    private String mChiefId,mChiefAvatarUrl,mChiefNick,mChiefFans;
    private List<ChiefPanelManagerModel> mManagerList = new ArrayList<>();
    private List<ChiefPanelHostModel> mHostList = new ArrayList<>();
    private boolean isViewer;
    private boolean mIsAttention;

    private BottomSheetDialog mDialog;
    private View mRootView;
    private BottomSheetBehavior<View> mBehavior;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public static VoiceLiveChiefPanel newInstance() {
        VoiceLiveChiefPanel panel = new VoiceLiveChiefPanel();
        return panel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.VoiceLiveChiefPanelStyle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        mRootView = View.inflate(getContext(),R.layout.lf_dialog_voicelive_chief_panel,null);
        mDialog.setContentView(mRootView);
        initView(mRootView);
        setData();
        return mDialog;
    }

    private void initView(View rootView) {
        mChiefAvatarIv = rootView.findViewById(R.id.chief_avatar_iv);
        mChiefIdTv = rootView.findViewById(R.id.chief_id_tv);
        mChiefReportTv = rootView.findViewById(R.id.chief_report_tv);
        mChiefNickTv = rootView.findViewById(R.id.chief_nick_tv);
        mChiefFansTv = rootView.findViewById(R.id.chief_fans_tv);
        mNoManagerFl = rootView.findViewById(R.id.chief_no_manager_fl);
        mNoHostFl = rootView.findViewById(R.id.chief_no_host_fl);
        mManagerRecycleView = rootView.findViewById(R.id.chief_manager_list_rv);
        mHostRecycleView = rootView.findViewById(R.id.chief_host_list_rv);
        mAttentionBtn = rootView.findViewById(R.id.chief_attention_btn);
        mSpaceView = rootView.findViewById(R.id.space_view);

        mChiefReportTv.setOnClickListener(this);
        mAttentionBtn.setOnClickListener(this);

        mBehavior = BottomSheetBehavior.from((View)mRootView.getParent());
        mBehavior.setSkipCollapsed(true);
        mBehavior.setHideable(true);
        mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
    }

    private void setData() {
        if (mChiefId != null && mChiefId.length() != 0) {
            String id = "ID: " + mChiefId;
            mChiefIdTv.setText(id);
        }
        if (mChiefAvatarUrl != null && mChiefAvatarUrl.length() != 0) {
            //todo
        }
        if (mChiefNick != null && mChiefNick.length() != 0) {
            mChiefNickTv.setText(mChiefNick);
        }
        if (mChiefFans != null && mChiefFans.length() != 0) {
            String fans = "粉丝 " + mChiefFans;
            mChiefFansTv.setText(fans);
        }
        if (isViewer) {
            mAttentionBtn.setVisibility(View.VISIBLE);
            mSpaceView.setVisibility(View.GONE);
            mChiefReportTv.setVisibility(View.VISIBLE);
            if (mIsAttention) {
                mAttentionBtn.setEnabled(false);
                mAttentionBtn.setText("已关注");
            } else {
                mAttentionBtn.setEnabled(true);
                mAttentionBtn.setText("关注");
            }
        } else {
            mAttentionBtn.setVisibility(View.GONE);
            mSpaceView.setVisibility(View.VISIBLE);
            mChiefReportTv.setVisibility(View.GONE);
        }

        if (mManagerList != null) {
            if (!mManagerList.isEmpty() && mManagerList.size() <= maxListSize) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mManagerRecycleView.setLayoutManager(manager);

                ChiefPanelAdapter managerAdapter = new ChiefPanelAdapter(getContext(),mManagerList);
                managerAdapter.setOnItemClickListener(new ChiefPanelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(boolean isHost, View itemView) {
                        if (!isHost) {
                            int position = mManagerRecycleView.getChildAdapterPosition(itemView);
                            long userId = mManagerList.get(position).userId;
                            Toast.makeText(getContext(),"点击" + position,Toast.LENGTH_SHORT).show();
                            dismissAllowingStateLoss();
                            //todo
                        }
                    }
                });
                mManagerRecycleView.setAdapter(managerAdapter);

                mManagerRecycleView.setVisibility(View.VISIBLE);
                mNoManagerFl.setVisibility(View.GONE);
            } else {
                mManagerRecycleView.setVisibility(View.GONE);
                mNoManagerFl.setVisibility(View.VISIBLE);
            }

        }

        if (mHostList != null) {
            if (!mHostList.isEmpty() && mHostList.size() <= maxListSize) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mHostRecycleView.setLayoutManager(manager);

                ChiefPanelAdapter hostAdapter = new ChiefPanelAdapter(getContext(),mHostList,true);
                hostAdapter.setOnItemClickListener(new ChiefPanelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(boolean isHost, View itemView) {
                        if (isHost) {
                            int position = mHostRecycleView.getChildAdapterPosition(itemView);
                            long userId = mHostList.get(position).userId;
                            Toast.makeText(getContext(),"点击" + position,Toast.LENGTH_SHORT).show();
                            dismissAllowingStateLoss();
                            //todo
                        }
                    }
                });
                mHostRecycleView.setAdapter(hostAdapter);

                mHostRecycleView.setVisibility(View.VISIBLE);
                mNoHostFl.setVisibility(View.GONE);
            } else {
                mHostRecycleView.setVisibility(View.GONE);
                mNoHostFl.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setChiefInfo(String chiefId, String chiefAvatarUrl, String chiefNick, String chiefFans, boolean isAttention) {
        mChiefId = chiefId;
        mChiefAvatarUrl = chiefAvatarUrl;
        mChiefNick = chiefNick;
        mChiefFans = chiefFans;
        mIsAttention = isAttention;
    }

    public void setListData(List<ChiefPanelManagerModel> managerList, List<ChiefPanelHostModel> hostList) {
        mManagerList = managerList;
        mHostList = hostList;
    }

    public void setIsChief(boolean isViewer) {
        this.isViewer = isViewer;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.chief_report_tv) {
            //todo
        } else if (v.getId() == R.id.chief_attention_btn) {
            mAttentionBtn.setEnabled(false);
            mAttentionBtn.setText("已关注");
            //todo
        }
    }

    public void release() {
        if (mHostList != null && mHostList.isEmpty()) {
            mHostList.clear();
        }
        if (mManagerList != null && mHostList.isEmpty()) {
            mManagerList.clear();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        release();
        super.onDismiss(dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRootView.getParent() != null) {
            ((ViewGroup)(mRootView.getParent())).removeView(mRootView);
        }
    }


}
