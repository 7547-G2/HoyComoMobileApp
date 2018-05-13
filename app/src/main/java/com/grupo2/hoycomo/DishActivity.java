package com.grupo2.hoycomo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void increaseNum(View view) {
        TextView tvNum = findViewById(R.id.tvCant);
        String aux = tvNum.getText().toString();
        Integer num = Integer.parseInt(aux);
        num++;
        tvNum.setText(num.toString());
    }

    public void decreaseNum(View view) {
        TextView tvNum = findViewById(R.id.tvCant);
        String aux = tvNum.getText().toString();
        Integer num = Integer.parseInt(aux);
        num--;
        if (num < 0) {num = 0;}
        tvNum.setText(num.toString());
    }
}
