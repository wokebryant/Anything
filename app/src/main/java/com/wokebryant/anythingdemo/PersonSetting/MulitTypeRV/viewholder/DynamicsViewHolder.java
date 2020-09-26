package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.view.View;

import com.wokebryant.anythingdemo.DynamicsItem.PersonalDynamicsItemView;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.DynamicsListIItem;

public class DynamicsViewHolder extends BaseViewHolder<DynamicsListIItem>{

    private PersonalDynamicsItemView view;



    public DynamicsViewHolder(View itemView) {
        super(itemView);
        view = (PersonalDynamicsItemView)itemView;


    }

    @Override
    public void bindViewData(DynamicsListIItem data, int size, int position) {

    }
}
