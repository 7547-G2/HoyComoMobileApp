package com.grupo2.hoycomo;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ErrorManager {

    public static void showToastError(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }
}
