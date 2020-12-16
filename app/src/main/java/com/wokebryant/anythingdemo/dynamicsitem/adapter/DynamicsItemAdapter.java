package com.wokebryant.anythingdemo.dynamicsitem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wokebryant.anythingdemo.dynamicsitem.factory.DynamicsItemTypeFactory;
import com.wokebryant.anythingdemo.dynamicsitem.factory.DynamicsTypeFactory;
import com.wokebryant.anythingdemo.dynamicsitem.item.BaseDynamicsItem;
import com.wokebryant.anythingdemo.dynamicsitem.viewholder.BaseDynamicsViewHolder;

import java.util.List;

/**
 * @author wb-lj589732
 */
public class DynamicsItemAdapter extends RecyclerView.Adapter<BaseDynamicsViewHolder> {

  private DynamicsTypeFactory typeFactory;
  private List<? extends BaseDynamicsItem> mItems;
  private OnItemClickListener mOnItemClickListener;

  public DynamicsItemAdapter(List<? extends BaseDynamicsItem> mData) {
    typeFactory = new DynamicsItemTypeFactory();
    mItems = mData;
  }

  @Override
  public BaseDynamicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,false);
    return typeFactory.createViewHolder(parent.getContext(), viewType, view);
  }

  @Override
  public void onBindViewHolder(BaseDynamicsViewHolder holder, final int position) {
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnItemClickListener != null) {
          mOnItemClickListener.onItemClick(mItems.get(position).getItemMediaType(), position);
        }
      }
    });
    holder.bindViewData(mItems.get(position), mItems.size(), position);
  }

  @Override
  public int getItemViewType(int position) {
    return mItems.get(position).type(typeFactory);
  }

  @Override
  public int getItemCount() {
    return (mItems != null ? mItems.size() : 0);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    mOnItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener{

    void onItemClick(String mediaType, int position);
  }
}
