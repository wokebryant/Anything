package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.personsetting.dialog.BaseBottomSheetDialog;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 *  真人认证弹窗
 */
public class RealPersonBottomDialog extends BaseBottomSheetDialog {

    private BottomSheetDialog mDialog;
    private View mRootView;
    private LinearLayout mSureLl;
    private ImageView mAvatarIv;
    private TextView mNickTv;

    private String mAvatar;
    private String mNick;

    private OnDialogClickListener mListener;

    public static RealPersonBottomDialog newInstance(String avatar, String nick) {
        RealPersonBottomDialog dialog = new RealPersonBottomDialog();
        Bundle bundle = new Bundle();
        bundle.putString("avatar", avatar);
        bundle.putString("nick", nick);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.VoiceLiveChiefPanelStyle);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAvatar = bundle.getString("avatar");
            mNick = bundle.getString("nick");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog= (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        mRootView = View.inflate(getContext(), R.layout.lf_real_person_certification_dialog, null);
        mDialog.setContentView(mRootView);
        supportPullDownToClose(mRootView, false);
        initView(mRootView);
        setData();
        return mDialog;
    }


    private void initView(View view) {
        mSureLl = view.findViewById(R.id.real_man_sure_ll);
        mAvatarIv = view.findViewById(R.id.real_person_avatar_iv);
        mNickTv = view.findViewById(R.id.lf_real_person_nick_tv);
        mSureLl.setOnClickListener(onClickListener);
    }

    private void setData() {
        //LFImageLoader.displayImage(mAvatar, mAvatarIv
        //    , LFImageLoaderTools.getInstance().getRoundOptionForAnchorIcon());
        mNickTv.setText(mNick);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == mSureLl.getId()) {
                mListener.onGotoCertification();
            }
        }
    };


    public void setOnDialogClickListener(OnDialogClickListener listener) {
        mListener = listener;
    }


    public interface OnDialogClickListener{

        void onGotoCertification();

    }

}
