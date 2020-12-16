package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.factory;

import android.content.Context;
import android.view.View;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.TagInActivityItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder.BaseViewHolder;


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

  BaseViewHolder createViewHolder(Context context, int type, boolean isSelfPage, View itemView);

}

