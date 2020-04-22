package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

public class VoiceLivePlacardDialog extends Dialog implements View.OnClickListener {

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
    private long mChiefRoomId;
    private boolean isChief;

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
        mDismissBtn = findViewById(R.id.placard_dismiss);
        mChiefNickTv = findViewById(R.id.chief_nick);
        mPlacardEditEt = findViewById(R.id.placard_edit);
        mPlacardContentTv = findViewById(R.id.placard_content);
        mCountTextTv = findViewById(R.id.count_text_tv);
        mPlacardUploadBtn = findViewById(R.id.placard_send);
        mDialogBgIv = findViewById(R.id.chief_dialog_bg);
        mSpiltLine = findViewById(R.id.spilt_line);

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
    }


    private int mMaxLength = 120;
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
            int countNum = s.length() >120 ? 120 : s.length();
            mCountTextTv.setText(String.valueOf(countNum) + "/120");
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
        if (mChiefNick != null && mChiefNick.length() != 0) {
            mChiefNickTv.setText(mChiefNick);
        }
        if (mPlacardContent != null && mPlacardContent.length() != 0) {
            mPlacardContentTv.setText(mPlacardContent);
        } else {
            mPlacardContentTv.setText("欢迎来到我的直播间");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.placard_dismiss) {
            dismiss();
        } else if (v.getId() == R.id.placard_send) {
            uploadPlacardContent();
        }
    }

    public void setPlacardContent(String chiefNick, String placardContent) {
        mChiefNick = chiefNick;
        mPlacardContent = placardContent;
    }

    public void setChiefRoomId(long ChiefRoomId) {
        mChiefRoomId = ChiefRoomId;
    }

    public void setIsThief(boolean isChief) {
        this.isChief = isChief;
    }

    public void uploadPlacardContent() {
        if (mChiefRoomId != 0) {
            String editContent = mPlacardEditEt.getText().toString();
            if (editContent != null && editContent.length() != 0) {

            } else {

            }
            //todo
        }
    }

}
