package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.wokebryant.anythingdemo.R;

public class CombSendView extends FrameLayout implements View.OnTouchListener {

    private Context mContext;

    //private FrameLayout mCombFloatingView;
    private CombFloatingView mCombFloatingView;
    private Button mCombSendBtn;

    public CombSendView(Context context) {
        super(context);
        initView();
    }

    public CombSendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CombSendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mContext = getContext();

        View.inflate(mContext, R.layout.lf_view_combsend, this);
        //mCombFloatingView = findViewById(R.id.comb_floating_view);
        mCombSendBtn = findViewById(R.id.comb_send_btn);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }
        return true;
    }

    private void touchDown() {

    }

    private void touchUp() {

    }




}
