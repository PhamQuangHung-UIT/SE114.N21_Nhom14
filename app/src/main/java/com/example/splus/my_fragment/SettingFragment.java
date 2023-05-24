package com.example.splus.my_fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.splus.AccountActivity;
import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.my_dialog.LanguageOptionsDialog;

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ImageButton buttonUpdateInfo = view.findViewById(R.id.buttonUpdateSettingFragment);
        buttonUpdateInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //Account manager
        View accountManagerBtn = view.findViewById(R.id.accountManagerButton);
        accountManagerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //Language
        View languageBtn = view.findViewById(R.id.buttonLanguageSettingFragment);
        languageBtn.setOnClickListener(v -> {
            Dialog chooseLangDlg = new LanguageOptionsDialog(view.getContext());
            chooseLangDlg.setOwnerActivity(getActivity());
            chooseLangDlg.show();
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotifySettingFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });
        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }
}
