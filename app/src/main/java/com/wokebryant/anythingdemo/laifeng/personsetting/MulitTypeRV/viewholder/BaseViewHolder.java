package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder;

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

    public View getView(int resId) {
        View view = viewList.get(resId);
        if (view == null){
            view = mItemView;
            viewList.put(resId,mItemView);
        }
        return view;
    }

    public abstract void bindViewData(T data, int size, int position);
}
