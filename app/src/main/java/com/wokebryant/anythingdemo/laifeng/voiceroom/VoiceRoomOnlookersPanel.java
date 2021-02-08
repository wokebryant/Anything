package com.wokebryant.anythingdemo.laifeng.voiceroom;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  语音房围观面板
 */
public class VoiceRoomOnlookersPanel extends BottomSheetDialogFragment {

    private static final String TAG = "VoiceRoomOnlookersPanel";

    private BottomSheetDialog mDialog;
    private View mRootView;
    private TextView mOnlookersNumTv;
    private RecyclerView mOnlookersListRv;

    private VoiceRoomOnlookersAdapter mAdapter;

    public static VoiceRoomOnlookersPanel newInstance() {
        VoiceRoomOnlookersPanel panel = new VoiceRoomOnlookersPanel();
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
        mRootView = View.inflate(getContext(), R.layout.lf_voice_room_onlookers_view, null);
        mDialog.setContentView(mRootView);
        initView(mRootView);
        setData();
        return mDialog;
    }

    private void initView(View view) {
        mOnlookersNumTv = view.findViewById(R.id.onlookers_num_tv);
        mOnlookersListRv = view.findViewById(R.id.onlookers_list_rv);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.VERTICAL);
        mOnlookersListRv.setLayoutManager(manager);
    }

    private void setData() {
        VoiceRoomOnlookersModel model = getMockData();
        if (model != null) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(model.totalNum + "人围观");
            stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FFB800")),
                0, String.valueOf(model.totalNum).length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            mOnlookersNumTv.setText(stringBuilder);
            mAdapter = new VoiceRoomOnlookersAdapter(getContext(), model.onlookersList);
            mAdapter.setOnInteractiveButtonClickListener(clickListener);
            mOnlookersListRv.setAdapter(mAdapter);
        }
    }

    private VoiceRoomOnlookersAdapter.OnInteractiveButtonClickListener clickListener = new VoiceRoomOnlookersAdapter.OnInteractiveButtonClickListener() {
        @Override
        public void onAttentionClick(String userId, int position) {
            Toast.makeText(getContext(), "关注", Toast.LENGTH_SHORT).show();
            VoiceRoomOnlookersAdapter.OnlookersViewHolder viewHolder = (VoiceRoomOnlookersAdapter.OnlookersViewHolder)mOnlookersListRv.findViewHolderForAdapterPosition(position);
            if (mAdapter != null) {
                mAdapter.updateAttentionView(viewHolder, position);
            }
        }

        @Override
        public void onChatClick(String userId) {
            Toast.makeText(getContext(), "聊天", Toast.LENGTH_SHORT).show();
        }
    };


    private VoiceRoomOnlookersModel getMockData() {
        VoiceRoomOnlookersModel model = new VoiceRoomOnlookersModel();
        model.totalNum = 10;
        List<VoiceRoomOnlookersModel.OnlookersListItem> listItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            VoiceRoomOnlookersModel.OnlookersListItem item = new VoiceRoomOnlookersModel.OnlookersListItem();
            item.serialNum = i + "";
            item.isAttention = true;
            item.avatarUrl = "https://image.laifeng.com/image/E5C2541332154E80B6594720464CC35F";
            item.medalList = new ArrayList<>();
            item.nick = "昵称" + i;
            item.yid = "111";
            item.ytid = "111";
            listItems.add(item);
        }
        for (int i = 6; i < 12; i++) {
            VoiceRoomOnlookersModel.OnlookersListItem item = new VoiceRoomOnlookersModel.OnlookersListItem();
            item.serialNum = i + "";
            item.isAttention = false;
            item.avatarUrl = "https://image.laifeng.com/image/E5C2541332154E80B6594720464CC35F";
            item.medalList = new ArrayList<>();
            item.nick = "昵称" + i;
            item.yid = "111";
            item.ytid = "111";
            listItems.add(item);
        }
        model.onlookersList = listItems;
        return model;
    }

}
