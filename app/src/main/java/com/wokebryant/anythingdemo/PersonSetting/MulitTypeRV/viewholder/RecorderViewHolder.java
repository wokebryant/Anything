package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.PersonSetting.Audio.SoundPlayerView;
import com.wokebryant.anythingdemo.PersonSetting.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.R;


/**
 * @author wb-lj589732
 * 录音
 */
public class RecorderViewHolder extends BaseViewHolder<RecorderItem> {

  private Context context;
  private SoundPlayerView soundPlayerView;

  public RecorderViewHolder(Context context, View itemView) {
    super(itemView);
    this.context = context;
    soundPlayerView = itemView.findViewById(R.id.person_setting_recorder);
  }

  @Override
  public void bindViewData(RecorderItem item,  int size, int position) {
    if (item != null && item.recorderUrl != null) {
      soundPlayerView.setPositionFrom(SettingConstant.FROM_PERSON_SETTING_EDIT);
      soundPlayerView.setPlayUrl(item.recorderUrl);
      soundPlayerView.setOnDeleteRecorderListener(new SoundPlayerView.OnDeleteRecorderListener() {
        @Override
        public void onDelete() {
          if (context instanceof PersonalSettingActivity) {
            PersonalSettingActivity activity = (PersonalSettingActivity) context;
            activity.deleteRecorder();
          }
        }
      });
    }
  }

}
