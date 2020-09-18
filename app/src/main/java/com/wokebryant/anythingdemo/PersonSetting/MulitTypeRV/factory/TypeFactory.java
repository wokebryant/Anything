package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory;

import android.view.View;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.AvatarItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder.BaseViewHolder;


/**
 * Created by beifeng on 2017/2/17.
 * type 工厂类 用来返回不同的itmetpe
 */

public interface TypeFactory {

  int type(RecorderItem recorderBean);

  int type(CommonItem commonItem);

  int type(AvatarItem headItem);

  int type(TagItem tagItem);

  int type(GroupItem groupItem);

  BaseViewHolder createViewHolder(int type, boolean isSelfPage, View itemView);

}

