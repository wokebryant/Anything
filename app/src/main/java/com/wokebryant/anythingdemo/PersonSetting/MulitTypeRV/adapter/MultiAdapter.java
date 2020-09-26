package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.Visitable;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.ItemTypeFactory;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder.BaseViewHolder;
import com.wokebryant.anythingdemo.adapter.ChiefPanelAdapter;

import java.util.List;

/**
 * @author wb-lj589732
 */
public class MultiAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  private TypeFactory typeFactory;
  private List<? extends BaseSettingItem> mItems;
  private OnItemClickListener mOnItemClickListener;
  private boolean isSelfPage;


  public MultiAdapter(List<? extends BaseSettingItem> mData, boolean isSelfPage) {
    typeFactory = new ItemTypeFactory();
    mItems = mData;
    this.isSelfPage = isSelfPage;
  }

  @Override
  public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent,false);
    return typeFactory.createViewHolder(parent.getContext(), viewType, isSelfPage, view);
  }

  @Override
  public void onBindViewHolder(BaseViewHolder holder, final int position) {
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnItemClickListener != null) {
          mOnItemClickListener.onItemClick(mItems.get(position).getItemSubType(), position);
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

    void onItemClick(String subType, int position);
  }
}
