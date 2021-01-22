package com.wokebryant.anythingdemo.newerchannel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class TagListView extends TagFlowLayout {

    private TagAdapter mTagAdapter;

    public TagListView(Context context) {
        this(context, null);
    }

    public TagListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setTagAdapter(List<String> tagList) {
        mTagAdapter = new TagAdapter<String>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                FrameLayout frameLayout = (FrameLayout)LayoutInflater.from(getContext())
                    .inflate(R.layout.lf_newer_channel_tag_item, parent, false);
                TextView textView = frameLayout.findViewById(R.id.newer_channel_tag_item_tv);
                textView.setText(s);
                return frameLayout;
            }

        };
        setAdapter(mTagAdapter);
    }
}
