package com.wokebryant.anythingdemo.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;

import com.wokebryant.anythingdemo.laifeng.personsetting.dialog.BaseBottomSheetDialog;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 真人认证弹窗
 */
public class RealManCertificationDialog extends BaseBottomSheetDialog {

    private BottomSheetDialog mDialog;
    private View mRootView;

    private LinearLayout mSureLl;

    private OnDialogClickListener mListener;

    public static RealManCertificationDialog newInstance() {
        RealManCertificationDialog dialog = new RealManCertificationDialog();
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
        mRootView = View.inflate(getContext(), R.layout.lf_real_man_certification_dialog, null);
        mDialog.setContentView(mRootView);
        supportPullDownToClose(mRootView, false);
        initView(mRootView);
        return mDialog;
    }

    private void initView(View view) {
        mSureLl = view.findViewById(R.id.real_man_sure_ll);

        mSureLl.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == mSureLl.getId()) {
                mListener.onGotoAlbum();
            }
        }
    };

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        mListener = listener;
    }

    public interface OnDialogClickListener{

        void onGotoAlbum();

    }

}
