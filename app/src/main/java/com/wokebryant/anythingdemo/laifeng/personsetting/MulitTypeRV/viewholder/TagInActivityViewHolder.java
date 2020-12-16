package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.TagInActivityItem;
import com.wokebryant.anythingdemo.laifeng.personsetting.SettingConstant;
import com.wokebryant.anythingdemo.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * @author wb-lj589732
 * 标签
 */
public class TagInActivityViewHolder extends BaseViewHolder<TagInActivityItem> {

    private Context context;
    private TagFlowLayout tagFlowLayout;

    public TagInActivityViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        tagFlowLayout = itemView.findViewById(R.id.person_setting_tag);
    }

    @Override
    public void bindViewData(final TagInActivityItem data, int size, int position) {

        //tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
        //    @Override
        //    public void onSelected(Set<Integer> selectPosSet) {
        //
        //    }
        //});
        if (tagFlowLayout != null) {
            tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    tagFlowLayout.onChanged();
                    return false;
                }
            });
            tagFlowLayout.setAdapter(new TagAdapter<String>(data.tagList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    RelativeLayout layout = (RelativeLayout)LayoutInflater.from(context)
                        .inflate(R.layout.lf_person_setting_tag_view, tagFlowLayout, false);
                    TextView textView = layout.findViewById(R.id.tag_content_tv);
                    ImageView imageView = layout.findViewById(R.id.tag_delete_iv);
                    imageView.setVisibility(View.GONE);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textView.getLayoutParams();
                    layoutParams.topMargin = 0;
                    textView.setLayoutParams(layoutParams);
                    if (data.tagList.size() == 1 && SettingConstant.person_setting_tag_none.equals(data.tagList.get(0))) {
                        textView.setBackground(null);
                        textView.setText("还未添加自评标签哦");
                    } else {
                        textView.setText(s);
                    }
                    return layout;
                }
            });
        }
    }

}
