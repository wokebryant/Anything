package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory;

import android.content.Context;
import android.view.View;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder.BaseViewHolder;


/**
 * @author wb-lj589732
 */
public interface TypeFactory {

  int type(RecorderItem recorderBean);

  int type(CommonItem commonItem);

  int type(PhotoWallItem headItem);

  int type(PhotoItem photoItem);

  int type(TagItem tagItem);

  int type(GroupItem groupItem);

  BaseViewHolder createViewHolder(Context context, int type, boolean isSelfPage, View itemView);

}

