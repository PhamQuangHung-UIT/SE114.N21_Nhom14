package com.example.splus.my_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.splus.AccountManagerActivity;
import com.example.splus.R;
import com.example.splus.my_dialog.LanguageOptionsDialog;

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //Account manager
        View accountManagerBtn = view.findViewById(R.id.accountManagerButton);
        accountManagerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountManagerActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //Language
        View languageBtn = view.findViewById(R.id.language_button);
        languageBtn.setOnClickListener(v -> {
            Dialog chooseLangDlg = new LanguageOptionsDialog(view.getContext());
            chooseLangDlg.setOwnerActivity(getActivity());
            chooseLangDlg.show();
        });
        return view;
    }
}
