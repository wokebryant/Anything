package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory;

import android.content.Context;
import android.view.View;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.DynamicsItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.DynamicsListIItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInActivityItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder.BaseViewHolder;


/**
 * @author wb-lj589732
 */
public interface TypeFactory {

  int type(RecorderItem recorderBean);

  int type(CommonItem commonItem);

  int type(PhotoWallItem headItem);

  int type(PhotoItem photoItem);

  int type(TagInActivityItem tagItem);

  int type(TagInFragmentItem tagItem);

  int type(GroupItem groupItem);

  int type(DynamicsListIItem dynamicsListIItem);

  int type(DynamicsItem dynamicsItem);

  BaseViewHolder createViewHolder(Context context, int type, boolean isSelfPage, View itemView);

}

