package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;


/**
 * @author wb-lj589732
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    private View mItemView;
    private SparseArray<View> viewList ;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        viewList = new SparseArray<>();
    }

    /**
     * 获取当前item的view
     * @param resId
     * @return
     */
    public View getView(int resId) {
        View view = viewList.get(resId);
        if (view == null){
            view = mItemView;
            viewList.put(resId,mItemView);
        }
        return view;
    }

    /**
     *绑定itemview 的数据
     */
    public abstract void bindViewData(T data);
}
