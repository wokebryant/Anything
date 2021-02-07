package com.wokebryant.anythingdemo.laifeng.newerchannel.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.laifeng.personsetting.dialog.BaseBottomSheetDialog;
import com.wokebryant.anythingdemo.laifeng.newerchannel.NewerChannelContract;
import com.wokebryant.anythingdemo.laifeng.newerchannel.SerMap;

import java.util.HashMap;
import java.util.List;

/**
 * @author wb-lj589732
 *  更多内容弹窗
 */
public class NewerChannelContentDialog extends BaseBottomSheetDialog {

    private TextView mNickTv;
    private LinearLayout mAttentionLl;
    private TextView mAttentionTv;
    private ImageView mAttentionIv;
    private TextView mFullContentTv;
    private TextView mPackUpTv;
    private SmallPhotoRecycleView mPhotoRv;
    private BottomSheetDialog mDialog;
    private View mRootView;
    private NewerChannelContract.OnContentMoreDialogClickLisenter mListener;

    private HashMap<String, Object> mUserData;
    private String mNick;
    private List<String> mFullContentList;
    private List<String> mPhotoList;
    private int mSelectedPosition;
    private boolean mIsAttention;

    public static NewerChannelContentDialog newInstance() {
        NewerChannelContentDialog dialog = new NewerChannelContentDialog();
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.VoiceLiveChiefPanelStyle);
        Bundle bundle = getArguments();
        if (bundle != null) {
            SerMap serMap = (SerMap)bundle.get("userData");
            mUserData = serMap.getMap();
            mNick = (String)mUserData.get("nick");
            mFullContentList = (List<String>)mUserData.get("contentList");
            mPhotoList = (List<String>)mUserData.get("photoList");
            mSelectedPosition = (int)mUserData.get("position");
            mIsAttention = (boolean)mUserData.get("attention");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        mRootView = View.inflate(getContext(), R.layout.lf_newer_channel_full_content_view, null);
        mDialog.setContentView(mRootView);
        supportPullDownToClose(mRootView, false);
        initView(mRootView);
        setData();
        return mDialog;
    }

    private void initView(View view) {
        mNickTv = view.findViewById(R.id.newer_channel_nick_tv);
        mAttentionLl = view.findViewById(R.id.newer_channel_attention_ll);
        mAttentionTv = view.findViewById(R.id.newer_channel_attention_tv);
        mAttentionIv = view.findViewById(R.id.newer_channel_attention_iv);
        mPhotoRv = view.findViewById(R.id.newer_channel_small_pic_rv);
        mFullContentTv = view.findViewById(R.id.newer_channel_full_content_tv);
        mPackUpTv = view.findViewById(R.id.newer_channel_pack_up_tv);
        mAttentionLl.setOnClickListener(onClickListener);
        mPackUpTv.setOnClickListener(onClickListener);
    }

    private void setData() {
        mNickTv.setText(mNick);
        SpannableString s = new SpannableString("哈哈");
        String str = String.valueOf(s);
        mFullContentTv.setText(s);
        updateAttentionUi(mIsAttention);

        if (mPhotoList != null) {
            mPhotoRv.setAdapter(mPhotoList, mSelectedPosition);
            mPhotoRv.setOnSmallPicItemClickListener(new NewerChannelContract.OnSmallPicItemClickListener() {
                @Override
                public void onClick(int position) {
                    if (mListener != null) {
                        mSelectedPosition = position;
                        mFullContentTv.setText(mFullContentList.get(position));
                        mListener.onSmallItemClick(position);
                    }
                }
            });

        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener == null) {
                return;
            }
            if (v.getId() == R.id.newer_channel_attention_ll) {
                mListener.onAttentionClick();
                updateAttentionUi(true);
            } else if (v.getId() == R.id.newer_channel_pack_up_tv) {
                mListener.onPackUpClick(mSelectedPosition);
            }
        }
    };

    private void updateAttentionUi(boolean isAttention) {
        if (isAttention) {
            mAttentionIv.setImageResource(R.drawable.lf_newer_channel_done);
            mAttentionTv.setVisibility(View.GONE);
        } else {
            mAttentionIv.setImageResource(R.drawable.lf_newer_channel_add);
            mAttentionTv.setVisibility(View.VISIBLE);
        }
    }

    public void updateSelectedItem(int position) {
        mSelectedPosition = position;
        mPhotoRv.updateSelectedItem(position);
        mFullContentTv.setText(mFullContentList.get(position));
    }

    public void setOnContentMoreDialogClickListener(NewerChannelContract.OnContentMoreDialogClickLisenter listener) {
        mListener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) {
            mListener.onPackUpClick(mSelectedPosition);
        }
    }
}
