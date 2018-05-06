package com.grupo2.hoycomo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class StoreMenuActivity extends AppCompatActivity {

    ListView menuListView;
    List<MenuItem> rowItems;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_menu);
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

        picture = findViewById(R.id.ivDtPicture);
        byte[] decodedString = Base64.decode(this.getString(R.string.food), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        picture.setImageBitmap(decodedByte);

        menuListView = findViewById(R.id.menuList);
        rowItems = new ArrayList<>();
        MenuItem cat = new MenuItem("Pizzas ", 0, true);
        rowItems.add(cat);
        for (int i = 0; i < 3; i++) {
            MenuItem item = new MenuItem("Pizza " + i, 100 + i*10, false);
            rowItems.add(item);
        }
        MenuItem cat2 = new MenuItem("Empanadas ", 0, true);
        rowItems.add(cat2);
        for (int j = 0; j < 5; j++) {
            MenuItem item2 = new MenuItem("Empanada " + j, 20 + j, false);
            rowItems.add(item2);
        }

        MenuAdapter adapter = new MenuAdapter(this, rowItems);
        menuListView.setAdapter(adapter);
        menuListView.setClickable(true);
    }
}
