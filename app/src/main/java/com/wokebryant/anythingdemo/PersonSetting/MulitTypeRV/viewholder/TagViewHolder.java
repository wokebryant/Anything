package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 标签
 */
public class TagViewHolder extends BaseViewHolder<TagItem> {

    private ImageView tagView;
    private TextView descView;

    public TagViewHolder(View itemView) {
        super(itemView);
        tagView = itemView.findViewById(R.id.person_setting_tag_tag);
        descView = itemView.findViewById(R.id.person_setting_tag_desc);
    }

    @Override
    public void bindViewData(TagItem data, int position) {
        tagView.setImageResource(R.drawable.lf_combsend_heart);
        descView.setText(data.desc);
    }
}
