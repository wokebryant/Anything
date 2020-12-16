package com.wokebryant.anythingdemo.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.utils.UIUtil;


public class VoiceLiveFinishDialog extends PopupWindow {

        private Context mContext;
        private View mPopWindow;

        public VoiceLiveFinishDialog(Context context) {
            this.mContext = context;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            mPopWindow = inflater.inflate(R.layout.lf_dialog_voicelive_finish, null);
            setPopWindow();

        }

        public void setPopWindow() {
            this.setContentView(mPopWindow);
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(UIUtil.dip2px(mContext, 60));
            this.setFocusable(false);
            this.setOutsideTouchable(true);
            this.setBackgroundDrawable(new ColorDrawable(0x00000000));
            this.setAnimationStyle(R.style.lf_voicelive_PopupAnimation);
        }

        public void showPopWindow() {
            View rootView = UIUtil.getRootView((Activity) mContext);
            this.showAtLocation(rootView,Gravity.TOP,0,0);
        }

        public void showAtTime(long time) {
            new CountDownTimer(time,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    dismiss();
                }
            }.start();
        }

}
