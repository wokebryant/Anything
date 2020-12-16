package com.wokebryant.anythingdemo.personsetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.personsetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.GridSpacingItemDecoration;
import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.R;

import java.util.List;

/**
 * @author wb-lj589732
 * 照片墙
 */
public class PhotoWallViewHolder extends BaseViewHolder<PhotoWallItem> {

    private LinearLayout tipView;
    private TextView progressView;
    private RecyclerView photoWallView;
    private LinearLayout saveView;

    private Context context;
    private MultiAdapter photoWallAdapter;
    private List<PhotoItem> photoItemList;

    public PhotoWallViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tipView = itemView.findViewById(R.id.person_setting_photo_wall_tip);
        progressView = itemView.findViewById(R.id.person_setting_photo_wall_progress);
        photoWallView = itemView.findViewById(R.id.person_setting_photo_wall);
        saveView = itemView.findViewById(R.id.person_setting_photo_wall_save);
    }

    @Override
    public void bindViewData(PhotoWallItem item, int size, int position) {
        if (item != null) {
            progressView.setText(item.progress);
            photoItemList = item.photoList;
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
        saveView.setOnClickListener(onClickListener);
        photoWallAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    private MultiAdapter.OnItemClickListener mOnItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType, int position) {
            if (context instanceof PersonalSettingActivity) {
                PersonalSettingActivity activity = (PersonalSettingActivity) context;
                if (0 != position) {
                    activity.launchPhotoActivity(getCurrentPhotoUrl(position), position);
                }
            }
        }
    };

    private String getCurrentPhotoUrl(int position) {
        String url = photoItemList.get(position).photoUrl;
        return url;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (context instanceof PersonalSettingActivity) {
                PersonalSettingActivity activity = (PersonalSettingActivity) context;
                activity.savePhotoSettingData();
            }
        }
    };

}
