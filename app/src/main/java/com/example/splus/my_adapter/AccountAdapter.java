package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Account;
import com.example.splus.my_interface.IClickAccountListener;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.UserViewHolder> {

    private final List<Account> accountList;
    private final IClickAccountListener iClickAccountListener;

    public AccountAdapter(List<Account> accountList, IClickAccountListener iClickAccountListener) {
        this.accountList = accountList;
        this.iClickAccountListener = iClickAccountListener;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public IClickAccountListener getiClickAccounteListener() {
        return iClickAccountListener;
    }
    @NonNull
    @Override
    public AccountAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.UserViewHolder holder, int position) {
        Account account = accountList.get(position);
        if (account == null) {
            return;
        }
        //holder.accountAvatar.setImageResource(account.getAvatarID());
        holder.accountAvatar.setImageResource(R.drawable.account_box);
        holder.accountName.setText(account.getFullname());
        holder.accountID.setText(account.getAccountID());

        holder.accountItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickAccountListener.onClickAccount(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (accountList != null) {
            return accountList.size();
        }
        return 0;
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ImageView accountAvatar;
        private final LinearLayout accountItem;
        private final TextView accountName;
        private final TextView accountID;

        public UserViewHolder(@NonNull View view) {
            super(view);
            accountAvatar = view.findViewById(R.id.imageAvatarAccountActivity);
            accountItem = view.findViewById(R.id.linearItemAccount);
            accountName = view.findViewById(R.id.textNameItemAccount);
            accountID = view.findViewById(R.id.textIdItemAccount);
        }
    }
}
