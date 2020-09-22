package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.Audio.SoundPlayerView;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.SettingActivity;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagItem;
import com.wokebryant.anythingdemo.R;


/**
 * @author wb-lj589732
 * 录音
 */
public class RecorderViewHolder extends BaseViewHolder<RecorderItem> {

  private Context context;
  private SoundPlayerView soundPlayerView;
  private ImageView addView;
  private TextView stateView;
  private ImageView deleteView;

  public RecorderViewHolder(Context context, View itemView) {
    super(itemView);
    this.context = context;
    soundPlayerView = itemView.findViewById(R.id.person_setting_recorder);
  }

  @Override
  public void bindViewData(RecorderItem item, int position) {
    if (item != null && item.recorderUrl != null) {
      soundPlayerView.setPositionFrom(SettingConstant.FROM_PERSON_SETTING_EDIT);
      soundPlayerView.setPlayUrl(item.recorderUrl);
    }
  }

}
