package com.wokebryant.anythingdemo.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.utils.UIUtil;


public class VoiceLivePlacardDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "VoiceLivePlacardDialog";

    private int MIN_HEIGHT ;
    private int MAX_HEIGHT ;

    private Button mDismissBtn;
    private TextView mChiefNickTv;
    private TextView mPlacardContentTv;
    private TextView mCountTextTv;
    private EditText mPlacardEditEt;
    private Button mPlacardUploadBtn;
    private ImageView mDialogBgIv;
    private View mSpiltLine;

    private String mChiefNick;
    private String mPlacardContent;
    private String mContentTemp;
    private boolean isChief;

    public modifyPlacardListener mListener;

    public VoiceLivePlacardDialog(Context context) {
        super(context,R.style.VoiceLiveDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.lf_dialog_voicelive_placard);
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
        mDismissBtn = findViewById(R.id.lfcontainer_placard_dismiss);
        mChiefNickTv = findViewById(R.id.lfcontainer_chief_nick);
        mPlacardEditEt = findViewById(R.id.lfcontainer_placard_edit);
        mPlacardContentTv = findViewById(R.id.lfcontainer_placard_content);
        mCountTextTv = findViewById(R.id.lfcontainer_count_text_tv);
        mPlacardUploadBtn = findViewById(R.id.lfcontainer_placard_send);
        mDialogBgIv = findViewById(R.id.lfcontainer_chief_dialog_bg);
        mSpiltLine = findViewById(R.id.lfcontainer_spilt_line);

        mDismissBtn.setOnClickListener(this);
        mPlacardUploadBtn.setOnClickListener(this);

        if (isChief) {
            mPlacardContentTv.setVisibility(View.GONE);
            mPlacardEditEt.setVisibility(View.VISIBLE);
            mPlacardUploadBtn.setVisibility(View.VISIBLE);
            mSpiltLine.setVisibility(View.VISIBLE);
            mDismissBtn.setVisibility(View.GONE);
            mDialogBgIv.setVisibility(View.VISIBLE);
            mCountTextTv.setVisibility(View.VISIBLE);
        } else {
            mPlacardContentTv.setVisibility(View.VISIBLE);
            mPlacardEditEt.setVisibility(View.GONE);
            mPlacardUploadBtn.setVisibility(View.GONE);
            mSpiltLine.setVisibility(View.GONE);
            mDismissBtn.setVisibility(View.VISIBLE);
            mDialogBgIv.setVisibility(View.GONE);
            mCountTextTv.setVisibility(View.GONE);
        }

        mPlacardContentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        mPlacardEditEt.setMovementMethod(ScrollingMovementMethod.getInstance());
        mPlacardEditEt.addTextChangedListener(textWatcher);

        MIN_HEIGHT = UIUtil.dip2px(getContext(), 80);
        MAX_HEIGHT = UIUtil.dip2px(getContext() , 168);
    }


    private int mMaxLength = 300;
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text = s.toString();
            int strLen = getStrLength(text);
            if (strLen > mMaxLength) {
                while (getStrLength(text) > mMaxLength) {
                    text = text.substring(0, text.length() - 1);
                }
                mPlacardEditEt.setText(text);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mPlacardEditEt.setSelection(mPlacardEditEt.getText().length());
            int countNum = s.length() > 300 ? 300 : s.length();
            mCountTextTv.setText(String.valueOf(countNum) + "/300");
        }
    };

    private int getStrLength(String validateStr) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < validateStr.length(); i++) {
            String temp = validateStr.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 1;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    private void setData() {
        if (!TextUtils.isEmpty(mChiefNick)) {
            mChiefNickTv.setText(mChiefNick);
        }
        if (!TextUtils.isEmpty(mPlacardContent)) {
            mPlacardContentTv.setText(mPlacardContent);
            mPlacardEditEt.setText(mPlacardContent);
        } else {
            mPlacardContentTv.setText("欢迎来到我的直播间");
        }
        if (!TextUtils.isEmpty(mContentTemp)) {
            mPlacardEditEt.setText(mContentTemp);
        }
        setContentHeight();
    }

    private void setContentHeight() {
        mPlacardContentTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlacardContentTv.getLayoutParams();
                int contentHeight;
                int contentLine = mPlacardContentTv.getLineCount();
                if (0 < contentLine && contentLine <= 4) {
                    contentHeight = MIN_HEIGHT;
                } else {
                    contentHeight = mPlacardContentTv.getMeasuredHeight() <= MAX_HEIGHT ? mPlacardContentTv.getMeasuredHeight() : MAX_HEIGHT;
                }
                params.height = contentHeight;
                mPlacardContentTv.setLayoutParams(params);

                mPlacardContentTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.i(TAG, "contentLine= " + contentLine + " contentHeight= " + contentHeight);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lfcontainer_placard_dismiss) {
            dismiss();
        } else if (v.getId() == R.id.lfcontainer_placard_send) {
            String editContent = mPlacardEditEt.getText().toString();
            mListener.updatePlacard(editContent);
        }
    }

    public void setPlacardContent(String chiefNick, String placardContent, String contentTemp) {
        mChiefNick = chiefNick;
        mPlacardContent = placardContent;
        mContentTemp = contentTemp;
    }

    public void setIsThief(boolean isChief) {
        this.isChief = isChief;
    }

    public interface modifyPlacardListener {

        void updatePlacard(String content);
    }

    public void setModifyPlacardListener(modifyPlacardListener listener) {
        mListener = listener;
    }

}
