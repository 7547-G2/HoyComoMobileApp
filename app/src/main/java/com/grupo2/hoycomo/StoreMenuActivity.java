package com.grupo2.hoycomo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

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
        /*
        picture = findViewById(R.id.ivDtPicture);
        byte[] decodedString = Base64.decode(this.getString(R.string.food), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        picture.setImageBitmap(decodedByte);
        */

        ScrollView scrollView = (ScrollView) findViewById(R.id.svMenu);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        menuListView = findViewById(R.id.menuList);
        rowItems = new ArrayList<>();
        MenuItem cat = new MenuItem("Pizzas ", 0, true);
        rowItems.add(cat);
        for (int i = 0; i < 5; i++) {
            MenuItem item = new MenuItem("Pizza " + i, 100 + i*10, false);
            rowItems.add(item);
        }
        MenuItem cat2 = new MenuItem("Empanadas ", 0, true);
        rowItems.add(cat2);
        for (int j = 0; j < 10; j++) {
            MenuItem item2 = new MenuItem("Empanada " + j, 20 + j, false);
            rowItems.add(item2);
        }

        MenuAdapter adapter = new MenuAdapter(this, rowItems);
        menuListView.setAdapter(adapter);
        menuListView.setClickable(true);
        setListViewHeightBasedOnChildren(menuListView);
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
