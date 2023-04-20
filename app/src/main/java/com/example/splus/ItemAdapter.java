package com.example.splus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.my_interface.IClickItemListener;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.UserViewHolder> {

    private final List<ItemData> mListItem;
    private final IClickItemListener iClickItemListener;

    public ItemAdapter(List<ItemData> mListItem, IClickItemListener iClickItemListener) {
        this.mListItem = mListItem;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ItemData item = mListItem.get(position);
        if (item == null) {
            return;
        }
        holder.itemImage.setImageResource(item.getResourceID());
        holder.itemName.setText(item.getItemName());
        holder.itemDes.setText(item.getItemDes());

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemListener.onClickItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListItem != null) {
            return mListItem.size();
        }
        return 0;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout layoutItem;
        private final ImageView itemImage;
        private final TextView itemName;
        private final TextView itemDes;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.item_layout);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemDes = itemView.findViewById(R.id.itemDes);
        }
    }
}
