package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.GridSpacingItemDecoration;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.SettingActivity;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 照片墙
 */
public class PhotoWallViewHolder extends BaseViewHolder<PhotoWallItem> {

    private LinearLayout tipView;
    private TextView progressView;
    private RecyclerView photoWallView;

    private Context context;
    private MultiAdapter photoWallAdapter;

    public PhotoWallViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tipView = itemView.findViewById(R.id.person_setting_photo_wall_tip);
        progressView = itemView.findViewById(R.id.person_setting_photo_wall_progress);
        photoWallView = itemView.findViewById(R.id.person_setting_photo_wall);
    }

    @Override
    public void bindViewData(PhotoWallItem item,  int position) {
        if (item != null) {
            progressView.setText(item.progress);
            bindPhotoWallData(item);
        }
    }

    private void bindPhotoWallData(PhotoWallItem item) {
        if (photoWallAdapter == null) {
            photoWallAdapter = new MultiAdapter(item.photoList, true);
            GridLayoutManager manager = new GridLayoutManager(context, 4);
            photoWallView.setLayoutManager(manager);
            photoWallView.addItemDecoration(new GridSpacingItemDecoration(4, 15, false));
            photoWallView.setAdapter(photoWallAdapter);
        } else {
            photoWallAdapter.notifyDataSetChanged();
        }
        photoWallAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    private MultiAdapter.OnItemClickListener mOnItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType, int position) {
            if (context instanceof SettingActivity) {
                SettingActivity activity = (SettingActivity) context;
                if (0 == position) {
                    activity.launchFragment(true, position);
                } else {
                    activity.launchFragment(false, position);
                }
            }
        }
    };

}
