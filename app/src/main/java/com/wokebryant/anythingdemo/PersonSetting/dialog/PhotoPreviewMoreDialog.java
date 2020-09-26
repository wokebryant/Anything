package com.wokebryant.anythingdemo.PersonSetting.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class PhotoPreviewMoreDialog extends BaseBottomSheetDialog {

    private BottomSheetDialog mDialog;
    private View mRootView;

    private TextView mDeleteView;
    private TextView mCameraView;
    private TextView mPhoneView;
    private TextView mCancelView;

    private OnDialogClickListener mListener;

    public static PhotoPreviewMoreDialog newInstance() {
        PhotoPreviewMoreDialog dialog = new PhotoPreviewMoreDialog();
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.VoiceLiveChiefPanelStyle);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog= (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        mRootView = View.inflate(getContext(), R.layout.lf_photo_preview_nore_dialog, null);
        mDialog.setContentView(mRootView);
        supportPullDownToClose(mRootView, false);
        initView(mRootView);
        return mDialog;
    }

    private void initView(View view) {
        mDeleteView = view.findViewById(R.id.more_dialog_cancel_tv);
        mCameraView = view.findViewById(R.id.more_dialog_camera_tv);
        mPhoneView = view.findViewById(R.id.more_dialog_phone_tv);
        mCancelView = view.findViewById(R.id.more_dialog_cancel_tv);

        mDeleteView.setOnClickListener(onClickListener);
        mCameraView.setOnClickListener(onClickListener);
        mPhoneView.setOnClickListener(onClickListener);
        mCancelView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.more_dialog_delete_tv) {
                if (mListener != null) {
                    mListener.onDelete();
                }
            } else if (v.getId() == R.id.more_dialog_camera_tv) {
                if (mListener != null) {
                    mListener.onCamera();
                }
            } else if (v.getId() == R.id.more_dialog_phone_tv) {
                if (mListener != null) {
                    mListener.onPhone();
                }
            } else if (v.getId() == R.id.more_dialog_cancel_tv) {
                dismissAllowingStateLoss();
            }
        }
    };

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        mListener = listener;
    }

    public interface OnDialogClickListener{

        void onDelete();

        void onCamera();

        void onPhone();

    }

}
