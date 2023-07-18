package com.example.splus.my_fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.splus.AccountActivity;
import com.example.splus.MainActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.SettingActivity;
import com.example.splus.SplashActivity;
import com.example.splus.my_data.Account;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotificationAccountFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });


        ImageButton buttonUpdateInfo = view.findViewById(R.id.buttonUpdateAccountFragment);
        buttonUpdateInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            Bundle bundle = new Bundle();
            Account account = ((MainActivity)requireActivity()).getAccount();
            bundle.putSerializable("account", account);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        // Account manager
        View accountManagerBtn = view.findViewById(R.id.linearAccountFragment);
        accountManagerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            startActivity(intent);
        });

        // Passworrd
        View password = view.findViewById(R.id.textChangePasswordAccountFragment);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Change password", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting
        View setting = view.findViewById(R.id.textSettingAccountFragment);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        // Support
        View support = view.findViewById(R.id.textSupportAccountFragment);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Support", Toast.LENGTH_SHORT).show();
            }
        });

        // Log out
        View logout = view.findViewById(R.id.textLogOutAccountFragment);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm")
                        .setMessage("Are you sure to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Log out successful", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), SplashActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

}
