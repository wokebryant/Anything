package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.AvatarItem;
import com.wokebryant.anythingdemo.R;

public class AvatarViewHolder extends BaseViewHolder<AvatarItem> {

    private TextView subView;
    private ImageView avatarView;

    public AvatarViewHolder(View itemView) {
        super(itemView);
        subView = itemView.findViewById(R.id.person_setting_avatar_sub);
        avatarView = itemView.findViewById(R.id.person_setting_avatar_avatar);
    }

    @Override
    public void bindViewData(AvatarItem data) {
        if (data != null) {
            subView.setText(data.title);
            avatarView.setImageResource(R.drawable.lf_combsend_heart);
        }

    }
}
