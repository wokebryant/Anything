package com.wokebryant.anythingdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wokebryant.anythingdemo.Constant;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.adapter.MulitAdpter;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.BannerBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.ContentBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.decorate.Visitable;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.dialog.VoiceLiveChiefPanel;
import com.wokebryant.anythingdemo.dialog.VoiceLiveCommonDialog;
import com.wokebryant.anythingdemo.dialog.VoiceLiveFinishDialog;
import com.wokebryant.anythingdemo.dialog.VoiceLivePlacardDialog;
import com.wokebryant.anythingdemo.mapper.TestData;
import com.wokebryant.anythingdemo.util.BomShot.ParticleSystem;
import com.wokebryant.anythingdemo.util.UIUtil;
import com.wokebryant.anythingdemo.util.floatingview.Floating;
import com.wokebryant.anythingdemo.view.CombFloatingView;
import com.wokebryant.anythingdemo.view.CombSendView;
import com.wokebryant.anythingdemo.view.CombWaveView;
import com.wokebryant.anythingdemo.view.ProgressSendView;
import com.wokebryant.anythingdemo.view.WaveProgressView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int UPDATE_PROGRESS = 1;
    private static final int MAX_PROGRESS = 100;
    private int mCurrentProgress = 0;
    private RelativeLayout mRootView;
    private WaveProgressView mWaveProgressView;
    private View mPonit;
    private CombSendView mCombSendView;
    private CombWaveView mCombWaveView;
    private CombFloatingView mCombFloatingView;
    private Floating mFloating;
    private Button mButton,mButton2,mButton3,mButton4;

    private VoiceLiveChiefPanel mChiefPanel;
    private VoiceLiveCommonDialog mCommonDialog;
    private VoiceLivePlacardDialog mPlacardDialog;
    private VoiceLiveFinishDialog mLiveFinishDialog;

    private ProgressSendView mProgressView;
    private RecyclerView mRecycleView;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    if (mCurrentProgress != MAX_PROGRESS) {
                        mCurrentProgress += 1;
                        mWaveProgressView.setProgress(mCurrentProgress);
                        sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                    } else {
                        mCurrentProgress = 0;
                        mWaveProgressView.setProgress(0);
                        mWaveProgressView.resetProgress();
                        sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                        //mWaveProgressView.release();
                    }
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initData();
    }

    private void initView() {
        mRootView = findViewById(R.id.rootView);
        mWaveProgressView = findViewById(R.id.waveProgressView);
        mPonit = findViewById(R.id.point);
        mCombSendView = findViewById(R.id.combSendView);
        mCombWaveView = findViewById(R.id.combWaveView);
        mCombFloatingView = new CombFloatingView(MainActivity.this);
        mProgressView = findViewById(R.id.testProgress);
        mRecycleView = findViewById(R.id.recycleView);
        mButton = findViewById(R.id.testBtn);
        mButton2 = findViewById(R.id.testBtn2);
        mButton3 = findViewById(R.id.testBtn3);
        mButton4 = findViewById(R.id.testBtn4);
        mFloating = new Floating(MainActivity.this);
        mFloating.setFloatingDecorView(mRootView);
        mButton.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);

        mCombSendView.setOnCombSendListener(onCombSendListener);
        mButton3.setOnTouchListener(onTouchListener);

        setLayoutParams();
        setWaveViewStyle();
        setFloatingViewParams();

        mRootView.addView(mCombFloatingView);
    }

    private void initData() {
        mockRecycleViewData();
    }

    private void setLayoutParams() {
        if (mProgressView != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mProgressView.getLayoutParams();
            params.width = params.height = UIUtil.vp2px(this, 64);
            mProgressView.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testBtn:
                //mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                mWaveProgressView.startWaveProgress(true);
                mWaveProgressView.setProgress(50);
//                showChiefPanel();
//                showFinishDialog();

                break;
            case R.id.testBtn2:
                showCommonDialog();
//                showPlacardDialog();
//                showProgressRing();
            case R.id.testBtn3:
                //doCombSend();
                break;
            default:
        }
    }

    private void showChiefPanel() {
        if (this != null && !this.isFinishing()) {
            try{
                if (mChiefPanel == null) {
                    mChiefPanel = VoiceLiveChiefPanel.newInstance();
                }
                mChiefPanel.setChiefInfo("1747105","","勒布朗","1802",true);
                mChiefPanel.setIsChief(true);
                mChiefPanel.setListData(TestData.getManagerData(),TestData.getHostData());
                if (!mChiefPanel.isAdded()) {
                    FragmentManager manager = getSupportFragmentManager();
                    mChiefPanel.show(manager,"VoiceLiveChiefPanel");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showCommonDialog() {
        VoiceLiveCommonDialog commonDialog = new VoiceLiveCommonDialog(this, "湖人总冠军", "勒布朗", "詹姆斯", new VoiceLiveCommonDialog.OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getBaseContext(),"确定按钮点击",Toast.LENGTH_SHORT).show();
            }
        });
//        commonDialog.show();

        VoiceLiveCommonDialog commonDialog2 = new VoiceLiveCommonDialog(this, Constant.lakersChampion, "勒布朗", "詹姆斯", new VoiceLiveCommonDialog.OnClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getBaseContext(), "确定按钮点击", Toast.LENGTH_SHORT).show();
            }
        }, new VoiceLiveCommonDialog.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "取消按钮点击", Toast.LENGTH_SHORT).show();
            }
        });
        commonDialog2.show();
        commonDialog2.setCancelBtnVisible(GONE);
    }

    private void showFinishDialog() {
        VoiceLiveFinishDialog finishDialog = new VoiceLiveFinishDialog(this);
        finishDialog.showPopWindow();
        finishDialog.showAtTime(3000);
    }

    private void showPlacardDialog() {
        VoiceLivePlacardDialog placardDialog = new VoiceLivePlacardDialog(this);
        placardDialog.setIsThief(false);
        placardDialog.setPlacardContent("勒布朗", Constant.lakersChampion, Constant.lakersChampion);
        placardDialog.show();
    }

    private void showProgressRing() {
        if (mProgressView != null) {
            mProgressView.resetAndStartProgress("0");
        }
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doCombSend();
                    break;
                case MotionEvent.ACTION_UP:
                    mCombSendView.touchUp();
                    break;
                default:
            }
            return true;
        }
    };

    private void doCombSend() {
        mCombSendView.reset();
        //mCombFloatingView.cancelAnim();
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mButton3, scaleX, scaleY);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCombSendView.setVisibility(VISIBLE);
                PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0, 1f);
                ObjectAnimator.ofPropertyValuesHolder(mCombSendView, alpha).setDuration(100).start();
            }
        });
        objectAnimator.setDuration(100).start();
        mButton3.setVisibility(View.INVISIBLE);
    }

    private CombSendView.OnCombSendListener onCombSendListener = new CombSendView.OnCombSendListener() {
        @Override
        public void onCombSend(int combNum) {
            if (mCombFloatingView != null) {
                mCombFloatingView.startFloatingAnim(combNum);
            }
        }

        @Override
        public void onCombSendEnd() {
            if (mCombFloatingView != null) {
                mCombFloatingView.removeSelf();
            }
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mCombSendView, alpha);
            objectAnimator.setInterpolator(new AccelerateInterpolator());
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCombSendView.setVisibility(GONE);
                    PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0, 1f);
                    PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0, 1f);
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(mButton3, scaleX, scaleY);
                    objectAnimator1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }
                    });
                    objectAnimator1.setDuration(100).start();
                }
            });
            objectAnimator.setDuration(100).start();
            mButton3.setVisibility(VISIBLE);
        }

        @Override
        public void onTouchUp() {
            if (mCombWaveView != null) {
                mCombWaveView.stop();
            }
        }

        @Override
        public void onTouchDown() {
            if (mCombWaveView != null) {
                mCombWaveView.start();
            }
            startBomShot();

        }
    };

    private void setWaveViewStyle() {
        if (mCombWaveView != null) {
            mCombWaveView.setColor(Color.parseColor("#860dab"));
            mCombWaveView.setStyle(Paint.Style.STROKE);
            mCombWaveView.setInitialRadius(UIUtil.dip2px(MainActivity.this, 36));
            mCombWaveView.setMaxRadius(UIUtil.dip2px(MainActivity.this, 55));
            mCombWaveView.setInterpolator(new AccelerateInterpolator(1.0f));
            mCombWaveView.setPaintWidth(UIUtil.dip2px(MainActivity.this, 3.0f));
            mCombWaveView.setDuration(600);
            mCombWaveView.setSpeed(200);
        }
    }

    private void setFloatingViewParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(0, 0, 0, UIUtil.dip2px(MainActivity.this, 100));
        mCombFloatingView.setLayoutParams(params);
        mCombFloatingView.bringToFront();
    }

    private void startBomShot() {
        ParticleSystem ps = new ParticleSystem(this, 1, R.drawable.lf_combsend_heart, 1000);
        ps.setScaleRange(0.5f, 0.7f);
        ps.setSpeedModuleAndAngleRange(0.2f, 0.5f, 0, 360);
        ps.setRotationSpeedRange(90, 180);
        //ps.setAcceleration(0.00013f, 90);
        ps.setFadeOut(400, new AccelerateInterpolator());
        ps.oneShot(mPonit, 1);

        ParticleSystem ps1 = new ParticleSystem(this, 1, R.drawable.lf_combsend_laugh, 1000);
        ps1.setScaleRange(0.5f, 0.7f);
        ps1.setSpeedModuleAndAngleRange(0.2f, 0.5f, 0, 360);
        ps1.setRotationSpeedRange(90, 180);
        //ps1.setAcceleration(0.00013f, 90);
        ps1.setFadeOut(400, new AccelerateInterpolator());
        ps1.oneShot(mPonit, 1);

        ParticleSystem ps2 = new ParticleSystem(this, 1, R.drawable.lf_combsend_like, 1000);
        ps2.setScaleRange(0.5f, 0.7f);
        ps2.setSpeedModuleAndAngleRange(0.2f, 0.5f, 0, 360);
        ps2.setRotationSpeedRange(90, 180);
        //ps2.setAcceleration(0.00013f, 90);
        ps2.setFadeOut(400, new AccelerateInterpolator());
        ps2.oneShot(mPonit, 1);
        //ps.emit(arg0, 100, 100);
    }

    private void mockRecycleViewData() {
        //  模拟本地数据
        List<Visitable> beans = new ArrayList<>();
        beans.add(new BannerBean(
                "www.baidu.com"));
        beans.add(new ContentBean());
        beans.add(new BannerBean(
                "www.jd.com"));
        beans.add(new BannerBean(
                "www.baidu.com"));
        beans.add(new ContentBean());
        beans.add(new BannerBean(
                "www.qq.com"));
        beans.add(new ContentBean());
        beans.add(new BannerBean(
                "www.sina.com"));
        beans.add(new ContentBean());
        beans.add(new BannerBean(
                "www.taobao.com"));
        beans.add(new ContentBean());
        MulitAdpter multiRecyclerAdapter = new MulitAdpter(beans);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(multiRecyclerAdapter);
    }

}

