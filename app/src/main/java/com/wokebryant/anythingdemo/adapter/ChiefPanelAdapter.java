package com.wokebryant.anythingdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.model.ChiefPanelHostModel;
import com.wokebryant.anythingdemo.model.ChiefPanelManagerModel;

import java.util.List;

public class ChiefPanelAdapter extends RecyclerView.Adapter<ChiefPanelAdapter.ViewHolder> {

    private Context mContext;
    private List<ChiefPanelManagerModel> mManagerList;
    private List<ChiefPanelHostModel> mHostList;
    private OnItemClickListener mOnItemClickListener;
    private boolean isHost;

    public ChiefPanelAdapter(Context context, List<ChiefPanelManagerModel> managerList) {
        mContext = context;
        mManagerList = managerList;
    }

    public ChiefPanelAdapter(Context context, List<ChiefPanelHostModel> hostList, boolean isHost) {
        mContext = context;
        mHostList = hostList;
        this.isHost = isHost;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext,R.layout.lf_item_voice_chief_panel,null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(isHost,v);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (isHost) {
            //todo
            viewHolder.mChiefHead.setImageResource(mHostList.get(i).resId);
            viewHolder.mChiefTag.setImageResource(R.drawable.lf_voicelive_host);
            viewHolder.itemView.setTag(i);
        } else {
            viewHolder.mChiefHead.setImageResource(mManagerList.get(i).resId);
            viewHolder.mChiefTag.setImageResource(R.drawable.lf_voicelive_manager);
            viewHolder.itemView.setTag(i);
        }

    }

    @Override
    public int getItemCount() {
        if (isHost) {
            return mHostList.size();
        } else {
            return mManagerList.size();
        }
    }

    public interface OnItemClickListener{
        void onItemClick(boolean isHost, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mChiefHead;
        private ImageView mChiefTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mChiefHead = itemView.findViewById(R.id.chief_item_avatar);
            mChiefTag = itemView.findViewById(R.id.chief_item_tag);
        }
    }
}
