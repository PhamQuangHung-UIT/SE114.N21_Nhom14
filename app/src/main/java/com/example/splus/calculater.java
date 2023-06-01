package com.example.keyboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private TextView textView;
  private final int[] buttonIds = {
      R.id.btn_0, R.id.btn_0_1, R.id.btn_1, R.id.btn_1_1, R.id.btn_2, R.id.btn_2_1,
      R.id.btn_3, R.id.btn_3_1, R.id.btn_4, R.id.btn_4_1, R.id.btn_5, R.id.btn_5_1,
      R.id.btn_6, R.id.btn_6_1, R.id.btn_7, R.id.btn_7_1, R.id.btn_8, R.id.btn_8_1,
      R.id.btn_9, R.id.btn_9_1, R.id.btn_add, R.id.btn_add_1, R.id.btn_except, R.id.btn_except_1,
      R.id.btn_multiply, R.id.btn_multiply_1, R.id.btn_divide, R.id.btn_divide_1,
      R.id.btn_all_clear, R.id.btn_all_clear_1, R.id.btn_del, R.id.btn_del_1,
      R.id.btn_equal, R.id.btn_equal_1, R.id.btn_fx, R.id.btn_fx_1,
      R.id.btn_x, R.id.btn_x_1, R.id.btn_tri_tuyet_doi, R.id.btn_can_hai,
      R.id.btn_tich_phan, R.id.btn_giai_thua, R.id.btn_dao_ham, R.id.btn_e,
      R.id.btn_dot, R.id.btn_mu, R.id.btn_log, R.id.btn_ln,
      R.id.percent, R.id.percent_1, R.id.btn_pi, R.id.btn_sin,
      R.id.btn_cos, R.id.btn_tan, R.id.btn_cot, R.id.btn_dong_ngoac, R.id.btn_mo_ngoac
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calculate);

    // ket noi voi textView
    textView = findViewById(R.id.tv);

    // ket noi voi 2 tableLayout
    final TableLayout table1 = findViewById(R.id.table1);
    final TableLayout table2 = findViewById(R.id.table2);

    // ket noi voi nut nhan chuyen doi giua 2 tableLayout
    Button btnChange = findViewById(R.id.btn_change);
    Button btnChange1 = findViewById(R.id.btn_change_1);

    // su kien khi nhan R.id.btnChane va R.id.btnChange1
    btnChange.setOnClickListener(v -> {
      // kiem tra trang thai hien tai cua table2 an/hien
      int visibility = table2.getVisibility();

      // hien table2 va an table1
      if (visibility == View.GONE) {
        table2.setVisibility(View.VISIBLE);
        table1.setVisibility(View.GONE);
      }
    });
    btnChange1.setOnClickListener(v -> {
      // kiem tra trang thai hien tai cua table2 an/hien
      int visibility = table2.getVisibility();

      // hien table1 va an table2
      if (visibility == View.VISIBLE) {
        table2.setVisibility(View.GONE);
        table1.setVisibility(View.VISIBLE);
      }
    });

    for (int buttonId : buttonIds) {
      Button button = findViewById(buttonId);
      button.setOnClickListener(this);
    }
  }

  @Override
  public void onClick(View v) {
    String currentText = textView.getText().toString();
    String newText = currentText;

    if ((v.getId() == R.id.btn_0) || (v.getId() == R.id.btn_0_1)) {
      newText = currentText + "0";
    } else if ((v.getId() == R.id.btn_1) || (v.getId() == R.id.btn_1_1)) {
      newText = currentText + "1";
    } else if ((v.getId() == R.id.btn_2) || (v.getId() == R.id.btn_2_1)) {
      newText = currentText + "2";
    } else if ((v.getId() == R.id.btn_3) || (v.getId() == R.id.btn_3_1)) {
      newText = currentText + "3";
    } else if ((v.getId() == R.id.btn_4) || (v.getId() == R.id.btn_4_1)) {
      newText = currentText + "4";
    } else if ((v.getId() == R.id.btn_5) || (v.getId() == R.id.btn_5_1)) {
      newText = currentText + "5";
    } else if ((v.getId() == R.id.btn_6) || (v.getId() == R.id.btn_6_1)) {
      newText = currentText + "6";
    } else if ((v.getId() == R.id.btn_7) || (v.getId() == R.id.btn_7_1)) {
      newText = currentText + "7";
    } else if ((v.getId() == R.id.btn_8) || (v.getId() == R.id.btn_8_1)) {
      newText = currentText + "8";
    } else if ((v.getId() == R.id.btn_9) || (v.getId() == R.id.btn_9_1)) {
      newText = currentText + "9";
    } else if ((v.getId() == R.id.btn_add) || (v.getId() == R.id.btn_add_1)) {
      newText = currentText + " + ";
    } else if ((v.getId() == R.id.btn_except) || (v.getId() == R.id.btn_except_1)) {
      newText = currentText + " - ";
    } else if ((v.getId() == R.id.btn_multiply) || (v.getId() == R.id.btn_multiply_1)) {
      newText = currentText + " * ";
    } else if ((v.getId() == R.id.btn_divide) || (v.getId() == R.id.btn_divide_1)) {
      newText = currentText + " / ";
    } else if ((v.getId() == R.id.btn_dot) || (v.getId() == R.id.btn_dot_1)) {
      newText = currentText + ".";
    } else if ((v.getId() == R.id.btn_equal) || (v.getId() == R.id.btn_equal_1)) {
      newText = currentText + " = ";
    } else if ((v.getId() == R.id.btn_9) || (v.getId() == R.id.btn_9_1)) {
      newText = currentText + "9";
    } else if ((v.getId() == R.id.btn_del) || (v.getId() == R.id.btn_del_1)) {
      // Xoa ky tu cuoi
      if (!currentText.isEmpty()) {
        newText = currentText.substring(0, currentText.length() - 1);
      }
    } else if (v.getId() == R.id.btn_all_clear || v.getId() == R.id.btn_all_clear_1) {
      // xoa toan bo noi da nhap
      newText = ("");
    } else if (v.getId() == R.id.btn_fx || v.getId() == R.id.btn_fx_1) {
      newText = currentText + "f(x)";
    } else if ((v.getId() == R.id.btn_x) || (v.getId() == R.id.btn_x_1)) {
      newText = currentText + "x";
    } else if (v.getId() == R.id.btn_tri_tuyet_doi) {
      newText = currentText + "abs( ";
    } else if (v.getId() == R.id.btn_can_hai) {
      newText = currentText + "√( ";
    } else if (v.getId() == R.id.btn_tich_phan) {
      newText = currentText + "dx*f( ";
    } else if (v.getId() == R.id.btn_giai_thua) {
      newText = currentText + "!";
    } else if (v.getId() == R.id.btn_dao_ham) {
      newText = currentText + "'";
    } else if (v.getId() == R.id.btn_e) {
      newText = currentText + "e";
    } else if (v.getId() == R.id.btn_mu) {
      newText = currentText + "^";
    } else if (v.getId() == R.id.btn_log) {
      newText = currentText + "log( ";
    } else if (v.getId() == R.id.btn_ln) {
      newText = currentText + "ln( ";
    } else if (v.getId() == R.id.percent || v.getId() == R.id.percent_1) {
      newText = currentText + "%";
    } else if (v.getId() == R.id.btn_pi) {
      newText = currentText + "𝜋";
    } else if (v.getId() == R.id.btn_sin) {
      newText = currentText + "sin";
    } else if (v.getId() == R.id.btn_cos) {
      newText = currentText + "cos";
    } else if (v.getId() == R.id.btn_tan) {
      newText = currentText + "tran";
    } else if (v.getId() == R.id.btn_cot) {
      newText = currentText + "cot";
    } else if (v.getId() == R.id.btn_mo_ngoac) {
      newText = currentText + "(";
    } else if (v.getId() == R.id.btn_dong_ngoac) {
      newText = currentText + ")";
    }
    textView.setText(newText);
  }
}
