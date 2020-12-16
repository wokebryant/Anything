package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 分组标题
 */
public class GroupViewHolder extends BaseViewHolder<GroupItem> {

  private TextView subView;
  private ImageView arrowView;
  private boolean isSelfPage;

  public GroupViewHolder(View itemView, boolean isSelfPage) {
    super(itemView);
    this.isSelfPage = isSelfPage;
    subView = itemView.findViewById(R.id.person_setting_group_sub);
    arrowView = itemView.findViewById(R.id.person_setting_group_arrow);
  }

  @Override
  public void bindViewData(GroupItem item, int size, int position) {
    subView.setText(item.groupName);
    if (item.clickable && isSelfPage) {
      arrowView.setVisibility(View.VISIBLE);
    } else {
      arrowView.setVisibility(View.GONE);
    }
  }
}
