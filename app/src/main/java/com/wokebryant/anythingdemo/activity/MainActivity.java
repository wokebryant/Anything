package com.wokebryant.anythingdemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wokebryant.anythingdemo.Constant;
import com.wokebryant.anythingdemo.DynamicsItem.activity.PersonalDynamicsActivity;
import com.wokebryant.anythingdemo.PersonSetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.dialog.VoiceLiveChiefPanel;
import com.wokebryant.anythingdemo.dialog.VoiceLiveCommonDialog;
import com.wokebryant.anythingdemo.dialog.VoiceLiveFinishDialog;
import com.wokebryant.anythingdemo.dialog.VoiceLivePlacardDialog;
import com.wokebryant.anythingdemo.mapper.TestData;
import com.wokebryant.anythingdemo.util.BomShot.ParticleSystem;
import com.wokebryant.anythingdemo.util.StatusBarUtil;
import com.wokebryant.anythingdemo.util.UIUtil;
import com.wokebryant.anythingdemo.util.VibrateUtil;
import com.wokebryant.anythingdemo.util.floatingview.Floating;
import com.wokebryant.anythingdemo.util.floatingview.FloatingBuilder;
import com.wokebryant.anythingdemo.util.floatingview.FloatingElement;
import com.wokebryant.anythingdemo.util.floatingview.effect.CombSendFloatingTransition;
import com.wokebryant.anythingdemo.view.CombFloatingView;
import com.wokebryant.anythingdemo.view.CombSendView;
import com.wokebryant.anythingdemo.view.CombWaveView;
import com.wokebryant.anythingdemo.view.CombGiftView;
import com.wokebryant.anythingdemo.view.ProgressSendView;
import com.wokebryant.anythingdemo.view.WaveProgressView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int UPDATE_PROGRESS = 1;
    private static final int MAX_PROGRESS = 100;
    private int mCurrentProgress = 0;
    private RelativeLayout mRootView;
    private FrameLayout mFragmentContainer;
    private WaveProgressView mWaveProgressView;
    private View mPonit, mPoint2;
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
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initData();
    }

    private void initView() {
        mRootView = findViewById(R.id.rootView);
        mFragmentContainer = findViewById(R.id.fragment_container);
        mWaveProgressView = findViewById(R.id.waveProgressView);
        mPonit = findViewById(R.id.point);
        mPoint2 = findViewById(R.id.point2);
        mCombSendView = findViewById(R.id.combSendView);
        mCombWaveView = findViewById(R.id.combWaveView);
        mCombFloatingView = new CombFloatingView(MainActivity.this);
        mProgressView = findViewById(R.id.testProgress);
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

        //ViewParent parent = mRootView.getParent();
        //if (parent != null && parent instanceof ViewGroup) {
        //    ((ViewGroup)parent).addView(mCombFloatingView);
        //}

    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
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
                //mWaveProgressView.startWaveProgress(true);
                //mWaveProgressView.setProgress(50);
//                showChiefPanel();
//                showFinishDialog();

                gotoSettingActivity();

                break;
            case R.id.testBtn2:
                gotoDynamicsActivity();
//                showChiefPanel();
                //showCommonDialog();
//                showPlacardDialog();
//                showProgressRing();
                Map<String, String> map = new HashMap<>();
                map.put("top", "279.750000");
                map.put("left", "24");
                map.put("bottom", "1345.250000");
                map.put("right", "24");

                int y = 0;
                if (map.containsKey("bottom")) {
                    y = Double.valueOf(map.get("bottom")).intValue();
                }

                String a = UIUtil.formatNum("10436366", false);

                DecimalFormat df = new DecimalFormat("#0.00");
                double ratio = (double)7 / 8;
                String combRatio = df.format(ratio);
                Log.i("Value= ", "bottom= " + a + " ratio= " + combRatio);
                //if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                //    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                //}
            case R.id.testBtn3:
                //doCombSend();
                break;
            case R.id.testBtn4:
                if (this.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
            default:
                break;
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

    private void gotoSettingActivity() {
        PersonalSettingActivity.launch(MainActivity.this, true);
    }

    private void gotoDynamicsActivity() {
        PersonalDynamicsActivity.launch(MainActivity.this);
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
            if (mCombWaveView != null) {
                mCombWaveView.start();
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startBomShot();
                    //startBomShot();
                    startGiftIconFloating();
                }
            }, 100);
            //startBomShot();
            //startGiftIconFloating();

            VibrateUtil.vSimple(MainActivity.this, 50);
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

            ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            UIUtil.getMemoryInfo(activityManager);
        }

        @Override
        public void onTouchDown() {
            //if (mCombWaveView != null) {
            //    mCombWaveView.start();
            //}
            //startBomShot();
            //startGiftIconFloating();
        }
    };

    private void setWaveViewStyle() {
        if (mCombWaveView != null) {
            mCombWaveView.setColor(Color.parseColor("#66860dab"));
            mCombWaveView.setStyle(Paint.Style.STROKE);
            mCombWaveView.setInitialRadius(UIUtil.dip2px(MainActivity.this, 37));
            mCombWaveView.setMaxRadius(UIUtil.dip2px(MainActivity.this, 54));
            mCombWaveView.setInterpolator(new AccelerateInterpolator(1.0f));
            mCombWaveView.setPaintWidth(UIUtil.dip2px(MainActivity.this, 3.0f));
            mCombWaveView.setDuration(450);
            mCombWaveView.setSpeed(150);
        }
    }

    private void setFloatingViewParams() {
        FrameLayout decorView = (FrameLayout)this.getWindow().getDecorView();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //params.setMargins(0, 0, 0, UIUtil.dip2px(DatePickerActivity.this, 100));
        mCombFloatingView.setLayoutParams(params);
        mCombFloatingView.bringToFront();

        decorView.addView(mCombFloatingView);
    }

    private void startBomShot() {
        ParticleSystem heartPs = createBomShot(R.drawable.lf_combsend_heart, 200, 200, 500);
        ParticleSystem laughPs = createBomShot(R.drawable.lf_combsend_laugh, 200, 200, 500);
        ParticleSystem likePs = createBomShot(R.drawable.lf_combsend_like, 200, 200, 550);

        if (heartPs != null) {
            heartPs.oneShot(mPoint2, 2);
        }

        if (laughPs != null) {
            laughPs.oneShot(mPoint2, 2);
        }

        if (likePs != null) {
            likePs.oneShot(mPoint2, 2);
        }
    }

    private ParticleSystem createBomShot(int resId, int width, int height, int liveTime) {
        ParticleSystem ps = new ParticleSystem(this, 2,
            UIUtil.zoomImage(this, resId, width, height), liveTime);
        ps.setScaleRange(0.4f, 0.7f);
        ps.setSpeedModuleAndAngleRange(0.7f, 1.0f, -100, -150);
        ps.setRotationSpeedRange(90, 180);
        ps.setAcceleration(0.003f, 80);
        ps.setFadeOut(getRandomAlpha(), 400, new AccelerateInterpolator());
        return ps;
    }

    private float getRandomAlpha() {
        Random random = new Random();
        return (0.4f) * random.nextFloat() + 0.4f;
    }

    private void startGiftIconFloating() {
        CombGiftView floatingView = new CombGiftView(MainActivity.this);

        FloatingElement floatingElement = new FloatingBuilder()
            .anchorView(mPonit)
            .targetView(floatingView)
            .floatingTransition(new CombSendFloatingTransition(MainActivity.this))
            .build();

        mFloating.startFloating(floatingElement);

        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.dago_pgc_gift_item_selected_anim);
        //floatingView.startAnimation(animation);

    }

}

