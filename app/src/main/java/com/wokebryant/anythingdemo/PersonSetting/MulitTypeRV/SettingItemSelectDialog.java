package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;

/**
 * @author wb-lj589732
 * 通用设置页面item选择弹窗
 */

public class SettingItemSelectDialog extends BottomSheetDialogFragment {

    public static BottomSheetDialogFragment newInstance(Object o) {
        SettingItemSelectDialog dialog = new SettingItemSelectDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }


    public OnItemSelectListener onItemSelectListener;

    public interface OnItemSelectListener {
        void itemSelect(String detail);
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        onItemSelectListener = listener;
    }


}
