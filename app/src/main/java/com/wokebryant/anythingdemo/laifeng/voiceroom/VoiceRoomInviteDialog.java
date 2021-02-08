package com.wokebryant.anythingdemo.laifeng.voiceroom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wokebryant.anythingdemo.R;

/**
 *  语音房邀请弹窗
 */
public class VoiceRoomInviteDialog extends Dialog {

    private static final String TAG = "VoiceRoomInviteDialog";
    public static final String DEFAULT_BG_IMAGE = "https://img.alicdn.com/imgextra/i4/O1CN01kARGFx1YPP3SFcK6H_!!6000000003051-2-tps-510-232.png";

    private Context mContext;
    private ImageView mCloseTv;
    private ImageView mBgView;
    private TextView mPrimaryTv;
    private TextView mOnlineNumTv;
    private ImageView mTagIv;
    private ImageView mAvatarIv;
    private TextView mNickTv;
    private TextView mAcceptTv;

    public VoiceRoomInviteDialog(@NonNull Context context) {
        super(context,R.style.AttentionGuideStyle);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.lf_voice_room_onlookers_invite_view);
        setDialogWindow(this);
        initView();
        setData();
    }

    private void setDialogWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private void initView() {
        mCloseTv = findViewById(R.id.invite_close_tv);
        mBgView = findViewById(R.id.invite_bg_view);
        mPrimaryTv = findViewById(R.id.invite_primary_tv);
        mOnlineNumTv = findViewById(R.id.invite_online_num_tv);
        mTagIv = findViewById(R.id.invite_tag_iv);
        mAvatarIv = findViewById(R.id.invite_avatar_iv);
        mNickTv = findViewById(R.id.invite_nick_tv);
        mAcceptTv = findViewById(R.id.invite_accept_tv);

        mCloseTv.setOnClickListener(onClickListener);
        mAcceptTv.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.invite_accept_tv) {
                // TODO: 2021/2/7 接受邀请

            } else if (v.getId() == R.id.invite_close_tv) {
                dismiss();
            }
        }
    };

    private void setData() {
        ImageLoader.getInstance().displayImage(DEFAULT_BG_IMAGE, mBgView);
        mPrimaryTv.setText("安心er的私密空间");
        mOnlineNumTv.setText("5人在线");
        mNickTv.setText("安心er");

    }

}
