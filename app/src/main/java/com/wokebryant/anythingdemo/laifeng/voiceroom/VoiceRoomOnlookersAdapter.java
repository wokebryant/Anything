package com.wokebryant.anythingdemo.laifeng.voiceroom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wokebryant.anythingdemo.R;

import java.util.List;

/**
 *  语音房围观面板列表Adapter
 */
public class VoiceRoomOnlookersAdapter extends RecyclerView.Adapter<VoiceRoomOnlookersAdapter.OnlookersViewHolder> {
    private static final String TAG = "VoiceRoomOnlookersAdapt";

    private Context mContext;
    private List<VoiceRoomOnlookersModel.OnlookersListItem> mDataList;
    private OnInteractiveButtonClickListener mClickListener;

    public VoiceRoomOnlookersAdapter(Context context, List<VoiceRoomOnlookersModel.OnlookersListItem> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public OnlookersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lf_voice_room_onlookers_item_view, viewGroup, false);
        OnlookersViewHolder holder = new OnlookersViewHolder(view);
        Log.i(TAG, "position= " + holder.getAdapterPosition());
        return new OnlookersViewHolder(view);
    }

    private boolean allowClick(int position) {
        boolean allow;
        if (mDataList != null && !mDataList.isEmpty() && mDataList.size() > position && mDataList.get(position) != null) {
            allow = true;
        } else {
            allow = false;
        }
        return allow;
    }

    public void setOnInteractiveButtonClickListener(OnInteractiveButtonClickListener listener) {
        mClickListener = listener;
    }

    public interface OnInteractiveButtonClickListener {
        void onAttentionClick(String userId, int position);

        void onChatClick(String userId);
    }


    @Override
    public void onBindViewHolder(@NonNull OnlookersViewHolder onlookersViewHolder, final int i) {
        final VoiceRoomOnlookersModel.OnlookersListItem item = mDataList.get(i);
        if (item == null) {
            return;
        }
        onlookersViewHolder.mInteractiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener == null || !allowClick(i)) {
                    return;
                }
                if (v.getId() == R.id.onlookers_item_interactive_tv) {
                    if (item.isAttention) {
                        mClickListener.onChatClick(item.yid);
                    } else {
                        mClickListener.onAttentionClick(item.yid, i);
                    }
                }
            }
        });

        onlookersViewHolder.mNumTv.setText(item.serialNum);
        // TODO: 2021/2/7  加载头像
        ImageLoader.getInstance().displayImage(item.avatarUrl, onlookersViewHolder.mAvatarIv);
        onlookersViewHolder.mNickTv.setText(item.nick);
        // TODO: 2021/2/7 加载勋章列表
        updateInteractiveView(onlookersViewHolder, item);
    }

    public void updateAttentionView(OnlookersViewHolder viewHolder, int position) {
        if (mDataList != null) {
            VoiceRoomOnlookersModel.OnlookersListItem item = mDataList.get(position);
            item.isAttention = true;
            mDataList.set(position, item);
            updateInteractiveView(viewHolder, item);
        }
    }

    public void updateInteractiveView(OnlookersViewHolder viewHolder, VoiceRoomOnlookersModel.OnlookersListItem item) {
        if (viewHolder != null && item != null) {
            if (item.isAttention) {
                viewHolder.mInteractiveTv.setText("私聊");
                viewHolder.mInteractiveTv.setTextColor(Color.parseColor("#FFAC00"));
                viewHolder.mInteractiveTv.setBackgroundResource(R.drawable.lf_bg_voice_room_attention);
            } else {
                viewHolder.mInteractiveTv.setText("关注");
                viewHolder.mInteractiveTv.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.mInteractiveTv.setBackgroundResource(R.drawable.lf_bg_voice_room_unattention);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public static class OnlookersViewHolder extends RecyclerView.ViewHolder {

        private TextView mNumTv;
        private ImageView mAvatarIv;
        private TextView mNickTv;
        private ImageView mMedalIv;
        private TextView mInteractiveTv;

        public OnlookersViewHolder(@NonNull View itemView) {
            super(itemView);
            mNumTv = itemView.findViewById(R.id.onlookers_item_num_tv);
            mAvatarIv = itemView.findViewById(R.id.onlookers_item_avatar_iv);
            mNickTv = itemView.findViewById(R.id.onlookers_item_nick_tv);
            mMedalIv = itemView.findViewById(R.id.onlookers_item_medal_iv);
            mInteractiveTv = itemView.findViewById(R.id.onlookers_item_interactive_tv);
        }
    }


}
