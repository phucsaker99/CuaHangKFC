package com.example.nhom11detai9.util;

import android.widget.EditText;
import android.widget.TextView;

public class Checkout {
    public static boolean isEmpty(EditText... edts){
        boolean isEmpty = false;
        for (EditText edt: edts
             ) {
            if (edt.getText().toString().isEmpty()){
                isEmpty = true;
                edt.setError("Input empty");
            }
        }
        return isEmpty;
    }

    public static boolean isStringEmpty(String... strings){
        boolean isEmpty = false;
        for (String edt: strings
        ) {
            if (edt.isEmpty()){
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    public static void format(EditText... edts){
        for (EditText edt: edts
        ) {
            edt.setText("");
        }
    }

    public static void format(TextView... textViews){
        for (TextView textView: textViews
        ) {
            textView.setText("");
        }
    }

}
