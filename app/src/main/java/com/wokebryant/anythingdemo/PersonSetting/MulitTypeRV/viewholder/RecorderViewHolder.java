package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagItem;
import com.wokebryant.anythingdemo.R;


/**
 * @author wb-lj589732
 */
public class RecorderViewHolder extends BaseViewHolder<RecorderItem> {

  private ImageView addRecorderView;
  private TextView stateView;
  private TextView deleteView;

  public RecorderViewHolder(View itemView) {
    super(itemView);
  }

  @Override
  public void bindViewData(RecorderItem data) {

  }

}
