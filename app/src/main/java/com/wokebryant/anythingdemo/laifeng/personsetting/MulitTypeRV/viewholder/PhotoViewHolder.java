package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 照片墙item
 */
public class PhotoViewHolder extends BaseViewHolder<PhotoItem>{

    private TextView avatarView;
    private ImageView photoView;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        avatarView = itemView.findViewById(R.id.person_setting_avatar);
        photoView = itemView.findViewById(R.id.person_setting_photo);
    }

    @Override
    public void bindViewData(PhotoItem item, int size, int position) {
        if (item != null && item.photoUrl != null) {
            if (position == 0) {
                avatarView.setVisibility(View.VISIBLE);
            } else {
                avatarView.setVisibility(View.GONE);
            }
            if (PhotoItem.defaultUrl.equals(item.photoUrl)) {
                photoView.setImageResource(R.drawable.lf_bg_setting_photo);
            } else {
                photoView.setImageResource(R.color.colorAccent);
            }

        }
    }
}
