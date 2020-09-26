package com.wokebryant.anythingdemo.PersonSetting.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.PersonSetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.ICallBack;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.PersonSetting.SettingDataHolder;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.dialog.LFDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wb-lj589732
 * 标签选择页
 */
public class SettingTagFragment extends Fragment {

    private static final String TAG = "SettingTagFragment";

    private static final int MAX_SELECTED = 4;

    private Activity mActivity;
    private MultiAdapter mAdapter;
    private ImageView mBackView;
    private TextView mSaveView;
    private RecyclerView mSelectedTagView;
    private TagFlowLayout mTagListView;
    private TagAdapter mTagAdapter;
    private TagFlowLayout.OnTagClickListener mOnTagClickListener;

    private Set<Integer> mIntegerSet = new HashSet<>();  //刚进页面已经被选择的tag
    private Set<String> mSelectedSet = new HashSet<>();  //点击选中的tag

    private List<String> mSaveTagList = new ArrayList<>(); //最后保存的tag

    private List<TagInFragmentItem> mSelectedTagList;  //刚进页面已经被选择的tag
    private List<String> mAllTagList;

    private ICallBack<List<TagInFragmentItem>> mCallBack;

    private int mOriginSelectedSize;
    private int mLastSelectedSize;
    private boolean mIsSaveEnabled = true;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
        mCallBack = (ICallBack)mActivity;
        getArgumentsData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lf_person_setting_tag, container, false);
        initView(view);
        initSelectedTagData();
        initAllTagData(mSelectedTagList);
        return view;
    }

    public static SettingTagFragment newInstance(List<String> selectedList, List<String> allTagList) {
        SettingTagFragment fragment = new SettingTagFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedList", (ArrayList<String>)selectedList);
        bundle.putStringArrayList("allList", (ArrayList<String>)allTagList);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void getArgumentsData() {
        if (getArguments() != null) {
            mSelectedTagList = SettingDataHolder.transformStringToTagItem(getArguments().getStringArrayList("selectedList"));
            mAllTagList = getArguments().getStringArrayList("allList");
        }
    }

    private void initView(View view) {
        mBackView = view.findViewById(R.id.setting_back_iv);
        mSaveView = view.findViewById(R.id.setting_title_more);
        mSelectedTagView = view.findViewById(R.id.setting_selected_tag_rv);
        mTagListView = view.findViewById(R.id.setting_tag_list_view);

        mBackView.setOnClickListener(onClickListener);
        mSaveView.setOnClickListener(onClickListener);
    }

    private void initSelectedTagData() {
        if (mSelectedTagList != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mAdapter = new MultiAdapter(mSelectedTagList, true);
            mSelectedTagView.setLayoutManager(layoutManager);
            mSelectedTagView.setAdapter(mAdapter);

            mOriginSelectedSize = mSelectedTagList.size();
            mSaveTagList.addAll(SettingDataHolder.transformTagItemToString(mSelectedTagList));
        }
    }



    private void initAllTagData(final List<TagInFragmentItem> list) {
        if (mAllTagList != null) {
            mTagAdapter = new TagAdapter<String>(mAllTagList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mActivity)
                        .inflate(R.layout.lf_person_setting_tag_view, mTagListView, false);
                    TextView textView = layout.findViewById(R.id.tag_content_tv);
                    ImageView imageView = layout.findViewById(R.id.tag_delete_iv);
                    imageView.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textView.getLayoutParams();
                    layoutParams.topMargin = 0;
                    layoutParams.bottomMargin = 25;
                    textView.setLayoutParams(layoutParams);
                    textView.setText(mAllTagList.get(position));
                    textView.setBackgroundResource(R.drawable.lf_bg_tag_unselected);
                    return layout;
                }

                @Override
                public boolean setSelected(int position, String s) {
                    if (isContainSelectedTag(s, list)) {
                        mIntegerSet.add(position);
                        return true;
                    } else {
                        return false;
                    }
                }

                @Override
                public void onSelected(int position, View view) {
                    TextView textView = view.findViewById(R.id.tag_content_tv);
                    textView.setBackgroundResource(R.drawable.lf_bg_tag_selected);
                    mSelectedSet.add(mAllTagList.get(position));
                }

                @Override
                public void unSelected(int position, View view) {
                    TextView textView = view.findViewById(R.id.tag_content_tv);
                    textView.setBackgroundResource(R.drawable.lf_bg_tag_unselected);
                    mSelectedSet.remove(mAllTagList.get(position));
                }
            };

            mOnTagClickListener = new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (needToast(position)) {
                        Toast.makeText(mActivity, "最多选择4个标签", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mSelectedSet.size() > mLastSelectedSize) {
                            mSaveTagList.add(mAllTagList.get(position));
                            updateSelectedUi();
                        } else if (mSelectedSet.size() < mLastSelectedSize){
                            mSaveTagList.remove(mAllTagList.get(position));
                            updateSelectedUi();
                        }
                        mLastSelectedSize = mSelectedSet.size();
                        if (mSelectedSet.size() == 0) {
                            setSaveEnabled(false);
                        } else {
                            setSaveEnabled(true);
                        }
                     }
                    return false;
                }
            };

            mTagListView.setAdapter(mTagAdapter);
            mTagListView.setOnTagClickListener(mOnTagClickListener);
            mTagAdapter.setSelectedList(mIntegerSet);

            mLastSelectedSize = mSelectedSet.size();
        }
    }

    private void setSaveEnabled(boolean enabled) {
        if (mIsSaveEnabled == enabled) {
            return;
        }
        if (enabled) {
            mIsSaveEnabled = true;
            mSaveView.setTextColor(Color.parseColor("#222222"));

        } else {
            mIsSaveEnabled = false;
            mSaveView.setTextColor(Color.parseColor("#999999"));
        }
    }

    private boolean needToast(int position) {
        if (mSelectedSet.size() >= MAX_SELECTED && !mSelectedSet.contains(mAllTagList.get(position))) {
            return true;
        }
        return false;
    }



    /**
     * 是否为已经选中的Tag
     */
    private boolean isContainSelectedTag(String s, List<TagInFragmentItem> mSelectedList) {
        boolean value = false;
        if (s == null || mSelectedList == null) {
            return false;
        }

        for (TagInFragmentItem tag : mSelectedList) {
            if (s.equals(tag.tagName)) {
                value = true;
                break;
            }
        }
        return value;
    }

    /**
     * 点击上面删除键更新下面UI
     */
    public void onSelectedTagDeleteListener(int position) {
        mSelectedSet.remove(mSelectedTagList.get(position).tagName);
        mLastSelectedSize = mSelectedSet.size();

        mSelectedTagList.remove(position);
        mSaveTagList.clear();
        mSaveTagList.addAll(SettingDataHolder.transformTagItemToString(mSelectedTagList));
        mIntegerSet.clear();

        mAdapter.notifyDataSetChanged();
        initAllTagData(mSelectedTagList);
    }

    /**
     * 点击下面更新上面已选择的UI
     */
    private void updateSelectedUi() {
        //if (mSaveTagList.size() > 0) {
            mSelectedTagList.clear();
            mSelectedTagList.addAll(SettingDataHolder.transformStringToTagItem(mSaveTagList));
            mAdapter.notifyDataSetChanged();
        //}
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.setting_back_iv) {
                if (mSaveTagList != null && !mSaveTagList.isEmpty()) {
                    showSureDialog();
                } else {
                    exitPage();
                }

            } else if (v.getId() == R.id.setting_title_more) {
                mCallBack.onSave(mSelectedTagList);
                exitPage();
            }

        }
    };

    private void exitPage() {
        if (mActivity instanceof PersonalSettingActivity && !mActivity.isFinishing()) {
            ((PersonalSettingActivity)mActivity).closeFragment();
        }
    }

    private void showSureDialog() {
        if (mOriginSelectedSize != mSaveTagList.size()) {
            String title = "";
            String content = "确定不保存退出编辑吗";
            final LFDialog dialog = new LFDialog(title, content, "取消", "确定退出", mActivity, R.style.ActorDialogStyle,
                new LFDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        mCallBack.onSave(mSelectedTagList);
                    }
                }, new LFDialog.OnCancelListener() {
                @Override
                public void onCancel() {

                }
            });
            dialog.show();
            dialog.setCancelBtnVisible(View.VISIBLE);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelBtnColor(Color.parseColor(String.valueOf("#333333")));
            dialog.setSureBtnColor(Color.parseColor(String.valueOf("#FF8700")));
        }
    }

}
