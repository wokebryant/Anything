package com.wokebryant.anythingdemo.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wokebryant.anythingdemo.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * laifeng_app_ad
 * com.youku.crazytogether.widget
 * Created by lee on 15/12/17.
 * Email:lee131483@gmail.com
 *
 * usage:
 * <p>
 * <com.youku.crazytogether.app.modules.ugc.widgets.MultiStateView xmlns:android="http://schemas.android.com/apk/res/android"
 * xmlns:tools="http://schemas.android.com/tools"
 * xmlns:app="http://schemas.android.com/apk/res-auto"
 * android:id="@+id/multiStateView"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * app:msv_errorView="@layout/error_view"
 * app:msv_emptyView="@layout/empty_view"
 * app:msv_loadingView="@layout/loading_view"
 * app:msv_contentView="@layout/content_view"
 * app:msv_viewState="loading">
 * </com.youku.crazytogether.app.modules.ugc.widgets.MultiStateView>
 */
public class MultiStateView extends FrameLayout {

//    public static final int VIEW_STATE_CONTENT = 0;
//
//    public static final int VIEW_STATE_ERROR = 1;
//
//    public static final int VIEW_STATE_EMPTY = 2;
//
//    public static final int VIEW_STATE_LOADING = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ViewState.CONTENT, ViewState.ERROR, ViewState.EMPTY, ViewState.LOADING})
    public @interface ViewState {
        int CONTENT = 0;
        int ERROR = 1;
        int EMPTY = 2;
        int LOADING = 3;
    }


    private LayoutInflater mInflater;

    private View mContentView;

    private View mLoadingView;

    private View mErrorView;

    private View mEmptyView;

    @ViewState
    private int mViewState = ViewState.CONTENT;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.lf_multi_stateview);

        mContentView = getChildAt(0);

        int contentViewResId = a.getResourceId(R.styleable.lf_multi_stateview_msv_contentView, -1);
        if (contentViewResId > -1) {
            mContentView = mInflater.inflate(contentViewResId, this, false);
            if(getChildCount()>0)
                removeAllViews();
            addView(mContentView, mContentView.getLayoutParams());
        }

        int loadingViewResId = a.getResourceId(R.styleable.lf_multi_stateview_msv_loadingView, -1);
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }

        int emptyViewResId = a.getResourceId(R.styleable.lf_multi_stateview_msv_emptyView, -1);
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(R.styleable.lf_multi_stateview_msv_errorView, -1);
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }

        int viewState = a.getInt(R.styleable.lf_multi_stateview_msv_viewState, ViewState.CONTENT);

        switch (viewState) {
            case ViewState.CONTENT:
                mViewState = ViewState.CONTENT;
                break;

            case ViewState.ERROR:
                mViewState = ViewState.ERROR;
                break;

            case ViewState.EMPTY:
                mViewState = ViewState.EMPTY;
                break;

            case ViewState.LOADING:
                mViewState = ViewState.LOADING;
                break;
        }

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) throw new IllegalArgumentException("Content view is not defined");
        setView();
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
    @Override
    public void addView(View child) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) mContentView = child;
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    /**
     * Returns the {@link View} associated with the {@link ViewState}
     *
     * @param state The {@link ViewState} with to return the view for
     * @return The {@link View} associated with the {@link ViewState}, null if no view is present
     */
    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case ViewState.LOADING:
                return mLoadingView;

            case ViewState.CONTENT:
                return mContentView;

            case ViewState.EMPTY:
                return mEmptyView;

            case ViewState.ERROR:
                return mErrorView;

            default:
                return null;
        }
    }

    /**
     * Returns the current {@link ViewState}
     *
     * @return
     */
    @ViewState
    public int getViewState() {
        return mViewState;
    }

    /**
     * Sets the current {@link ViewState}
     *
     * @param state The {@link ViewState} to set {@link MultiStateView} to
     */
    public void setViewState(@ViewState int state) {
        if (state != mViewState) {
            mViewState = state;
            setView();
        }
    }

    /**
     * Shows the {@link View} based on the {@link ViewState}
     */
    private void setView() {
        switch (mViewState) {
            case ViewState.LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }

                mLoadingView.setVisibility(View.VISIBLE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;

            case ViewState.EMPTY:
                if (mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }

                mEmptyView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                break;

            case ViewState.ERROR:
                if (mErrorView == null) {
                    throw new NullPointerException("Error View");
                }

                mErrorView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;

            case ViewState.CONTENT:
            default:
                if (mContentView == null) {
                    // Should never happen, the view should throw an exception if no content view is present upon creation
                    throw new NullPointerException("Content View");
                }

                mContentView.setVisibility(View.VISIBLE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                if (mErrorView != null) mErrorView.setVisibility(View.GONE);
                if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView && view != mEmptyView;
    }

    /**
     * Sets the view for the given view state
     *
     * @param view          The {@link View} to use
     * @param state         The {@link ViewState}to set
     * @param switchToState If the {@link ViewState} should be switched to
     */
    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        switch (state) {
            case ViewState.LOADING:
                if (mLoadingView != null) removeView(mLoadingView);
                mLoadingView = view;
                addView(mLoadingView);
                break;

            case ViewState.EMPTY:
                if (mEmptyView != null) removeView(mEmptyView);
                mEmptyView = view;
                addView(mEmptyView);
                break;

            case ViewState.ERROR:
                if (mErrorView != null) removeView(mErrorView);
                mErrorView = view;
                addView(mErrorView);
                break;

            case ViewState.CONTENT:
                if (mContentView != null) removeView(mContentView);
                mContentView = view;
                addView(mContentView);
                break;
        }

        if (switchToState) setViewState(state);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param view  The {@link View} to use
     * @param state The {@link ViewState} to set
     */
    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes     Layout resource id
     * @param state         The {@link ViewState} to set
     * @param switchToState If the {@link ViewState} should be switched to
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    /**
     * Sets the {@link View} for the given {@link ViewState}
     *
     * @param layoutRes Layout resource id
     * @param state     The {@link View} state to set
     */
    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }
}
