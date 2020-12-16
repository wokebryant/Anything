package com.wokebryant.anythingdemo.laifeng.dynamicsitem.factory;

import android.content.Context;
import android.view.View;

import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsPhotoItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsReplayItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.viewholder.BaseDynamicsViewHolder;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.viewholder.DynamicsPhotoViewHolder;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.viewholder.DynamicsShortVideoViewHolder;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class DynamicsItemTypeFactory implements DynamicsTypeFactory {

  public static final int DYNAMICS_PHOTO_ITEM_LAYOUT = R.layout.lf_dynamics_item_photo;
  public static final int DYNAMICS_SHORT_VIDEO_ITEM_LAYOUT = R.layout.lf_dynamics_item_short_video;


  @Override
  public int type(DynamicsPhotoItem dynamicsPhotoItem) {
    return DYNAMICS_PHOTO_ITEM_LAYOUT;
  }

  @Override
  public int type(DynamicsShortVideoItem dynamicsShortVideoItem) {
    return DYNAMICS_SHORT_VIDEO_ITEM_LAYOUT;
  }

  @Override
  public int type(DynamicsReplayItem dynamicsReplayItem) {
    return 0;
  }

  @Override
  public BaseDynamicsViewHolder createViewHolder(Context context, int type, View itemView) {
    switch (type) {
      case DYNAMICS_PHOTO_ITEM_LAYOUT:
        return new DynamicsPhotoViewHolder(context, itemView);
      case DYNAMICS_SHORT_VIDEO_ITEM_LAYOUT:
        return new DynamicsShortVideoViewHolder(context, itemView);
      default:
        return null;
    }
  }
}
