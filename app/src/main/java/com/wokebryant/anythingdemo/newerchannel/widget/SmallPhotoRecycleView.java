package com.wokebryant.anythingdemo.newerchannel.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.newerchannel.NewerChannelContract;
import com.wokebryant.anythingdemo.utils.UIUtil;

import java.util.List;

/**
 * @author wb-lj589732
 *  小图RecycleView(支持切换短视频和图片)
 */
public class SmallPhotoRecycleView extends RecyclerView {

    public static final int ADD_STOKE = 1;
    public static final int REMOVE_STOKE = 2;

    private Context mContext;
    private SmallImageAdapter mAdapter;
    private LinearLayoutManager mManager;
    private NewerChannelContract.OnSmallPicItemClickListener mListener;

    public SmallPhotoRecycleView(@NonNull Context context) {
        this(context, null);
    }

    public SmallPhotoRecycleView(@NonNull Context context,
                                 @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallPhotoRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mManager = new LinearLayoutManager(mContext);
        mManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(mManager);
    }

    public void setAdapter(List<String> imageList, int selectedPosition) {
        mAdapter = new SmallImageAdapter(imageList, selectedPosition);
        mManager.smoothScrollToPosition(this, new RecyclerView.State(), selectedPosition);
        setAdapter(mAdapter);
    }

    public void updateSelectedItem(final int position) {
        mManager.smoothScrollToPosition(this, new RecyclerView.State(), position);
        if (mAdapter != null) {
            mAdapter.updateItem(position);
        }
    }

    public void setOnSmallPicItemClickListener(NewerChannelContract.OnSmallPicItemClickListener listener) {
        mListener = listener;
    }

    /**
     *  适配器
     */
    public class SmallImageAdapter extends RecyclerView.Adapter<SmallImageViewHolder> {

        private List<String> mSmallImageList;
        private int mPreviousPosition = -1;
        private int mSelectPosition;

        public SmallImageAdapter(List<String> smallImageList, int selectedPosition) {
            mSmallImageList = smallImageList;
            mSelectPosition = selectedPosition;
        }

        @NonNull
        @Override
        public SmallImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mContext, R.layout.lf_newer_channel_small_pic_item, null);
            SmallImageViewHolder viewHolder = new SmallImageViewHolder(view);
            viewHolder.itemView.setOnClickListener(onClickListener);
            return viewHolder;
        }

        private View.OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getChildAdapterPosition(v);
                if (mListener != null && mSelectPosition != position) {
                    updateItem(position);
                    mListener.onClick(position);
                }
            }
        };

        @Override
        public void onBindViewHolder(@NonNull SmallImageViewHolder smallImageViewHolder, int i) {
            if (smallImageViewHolder != null) {
                DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().displayer(new RoundedBitmapDisplayer(UIUtil.dip2px(getContext(), 5))).build();
                ImageLoader.getInstance().displayImage(mSmallImageList.get(i), smallImageViewHolder.mPicView, imageOptions);
                if (mSelectPosition == i) {
                    setItemSelectedState(smallImageViewHolder);
                } else {
                    setItemUnselectedState(smallImageViewHolder);
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull SmallImageViewHolder holder, int position,
                                     @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                int type = (int)payloads.get(0);
                switch (type) {
                    case ADD_STOKE:
                        setItemSelectedState(holder);
                        break;
                    case REMOVE_STOKE:
                        setItemUnselectedState(holder);
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public int getItemCount() {
            return mSmallImageList != null ? mSmallImageList.size() : 0;
        }

        public void updateItem(int position) {
            mPreviousPosition = mSelectPosition;
            mSelectPosition = position;
            notifyItemRangeChanged(mSelectPosition, 1, ADD_STOKE);
            notifyItemRangeChanged(mPreviousPosition, 1, REMOVE_STOKE);
        }

        public void setItemSelectedState(SmallImageViewHolder viewHolder) {
            if (viewHolder == null) {
                return;
            }
            viewHolder.mStokeView.setVisibility(VISIBLE);
            viewHolder.itemView.setAlpha(1.0f);
        }

        public void setItemUnselectedState(SmallImageViewHolder viewHolder) {
            if (viewHolder == null) {
                return;
            }
            viewHolder.mStokeView.setVisibility(GONE);
            viewHolder.itemView.setAlpha(0.5f);
        }

    }

    public static class SmallImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mPicView;
        private ImageView mStokeView;

        public SmallImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mPicView = itemView.findViewById(R.id.small_pic_item_content);
            mStokeView = itemView.findViewById(R.id.small_pic_item_stoke);
        }
    }

}
