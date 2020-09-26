package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.PersonSetting.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.Fragment.SettingTagFragment;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.R;

public class TagInFragmentViewHolder extends BaseViewHolder<TagInFragmentItem>{

    private Context context;
    private TextView tagView;
    private ImageView deleteView;

    public TagInFragmentViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tagView = itemView.findViewById(R.id.tag_content_tv);
        deleteView = itemView.findViewById(R.id.tag_delete_iv);
    }

    @Override
    public void bindViewData(TagInFragmentItem data, int size, final int position) {
        tagView.setText(data.tagName);
        tagView.setBackgroundResource(R.drawable.lf_bg_tag_selected);
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof PersonalSettingActivity) {
                    PersonalSettingActivity activity = (PersonalSettingActivity) context;
                    Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(SettingConstant.TAG_FRAGMENT_TAG);
                    if (fragment instanceof SettingTagFragment) {
                        SettingTagFragment tagFragment = (SettingTagFragment)fragment;
                        tagFragment.onSelectedTagDeleteListener(position);
                    }
                }
            }
        });
    }

}
