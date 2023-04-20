package com.example.splus.my_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.splus.R;
import com.example.splus.my_class.ActivityManager;
import com.example.splus.my_class.LocaleHelper;

public class LanguageOptionsDialog extends Dialog {

    private final String Vietnamese = "vn";

    public LanguageOptionsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_laguage_options);

        String currentLang = LocaleHelper.getLanguage(getContext().getApplicationContext());

        RadioGroup radioGroup = findViewById(R.id.languageRadioGroup);

        if (currentLang.equals(Vietnamese))
            radioGroup.check(R.id.Vietnamese);
        else radioGroup.check(R.id.English);

        Button buttonOK = findViewById(R.id.button_ok);


        buttonOK.setOnClickListener(v -> {

            RadioButton checkedBtn = findViewById(radioGroup.getCheckedRadioButtonId());
            String chosenLang = checkedBtn.getTag().toString();
            LocaleHelper.setLocale(getContext().getApplicationContext(), chosenLang);

            if (!currentLang.equals(chosenLang))
                ActivityManager.recreateAll();
            dismiss();
        });
    }
}