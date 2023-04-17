package com.example.splus.my_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.splus.R;
import com.example.splus.my_class.LocaleHelper;

public class LanguageOptionsDialog extends Dialog {

    private final String Vietnamese = "vn";
    private final String English = "en";

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

        final String[] chosenLang = new String[1];

        buttonOK.setOnClickListener(v -> {

            TextView languageTextView = LanguageOptionsDialog.this.getOwnerActivity().findViewById(R.id.currentLanguageTextView);

            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.Vietnamese:
                    LocaleHelper.setLocale(LanguageOptionsDialog.this.getContext().getApplicationContext(), Vietnamese);
                    chosenLang[0] = Vietnamese;
                    languageTextView.setText(R.string.language_type_vn);
                    break;
                case R.id.English:
                    LocaleHelper.setLocale(LanguageOptionsDialog.this.getContext().getApplicationContext(), English);
                    chosenLang[0] = English;
                    languageTextView.setText(R.string.language_type_en);
                    break;
            }
            if (currentLang.equals(chosenLang[0]))
                LanguageOptionsDialog.this.getOwnerActivity().recreate();
            LanguageOptionsDialog.this.dismiss();
        });
    }
}