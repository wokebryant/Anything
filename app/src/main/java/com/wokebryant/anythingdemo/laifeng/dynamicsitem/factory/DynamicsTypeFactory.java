package com.wokebryant.anythingdemo.laifeng.dynamicsitem.factory;

import android.content.Context;
import android.view.View;

import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsPhotoItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsReplayItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.laifeng.dynamicsitem.viewholder.BaseDynamicsViewHolder;


/**
 * @author wb-lj589732
 */
public interface DynamicsTypeFactory {

  int type(DynamicsPhotoItem dynamicsPhotoItem);

  int type(DynamicsShortVideoItem dynamicsShortVideoItem);

  int type(DynamicsReplayItem dynamicsReplayItem);


  BaseDynamicsViewHolder createViewHolder(Context context, int type, View itemView);

}

