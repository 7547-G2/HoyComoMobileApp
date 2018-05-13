package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.grupo2.hoycomo.ErrorManager.showToastError;

public class StoreMenuActivity extends AppCompatActivity {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser/menu/";

    ListView menuListView;
    List<MenuCateg> categItems;
    List<MenuItem> rowItems;

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

        ScrollView scrollView = findViewById(R.id.svMenu);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        Intent intent = getIntent();
        String name, leadTime, minPrice, maxPrice, sId;
        Integer rank;
        name = intent.getStringExtra("name");
        leadTime = intent.getStringExtra("leadTime");
        minPrice = intent.getStringExtra("minPrice");
        maxPrice = intent.getStringExtra("maxPrice");
        sId = intent.getStringExtra("store_id");
        rank = intent.getIntExtra("rank", 3);
        TextView tvDTname = findViewById(R.id.tvDtName);
        TextView tvDTleadTime = findViewById(R.id.tvDtLeadTime);
        TextView tvDTprice = findViewById(R.id.tvDtPrice);
        TextView tvDTrank = findViewById(R.id.tvDtRank);
        tvDTname.setText(name);
        tvDTleadTime.setText(leadTime + " min");
        tvDTprice.setText("$" + minPrice + " - $" + maxPrice);
        tvDTrank.setText(rank.toString() + "/5");


        menuListView = findViewById(R.id.menuList);
        /*
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
        */
        getMenu(sId);
    }

    private void getMenu(String id) {
        String REQUEST_TAG = "getstores";
        String url = BASE_URI + id;
        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseMenu(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getMenu: " + error.toString());
                        showToastError("Error al obtener el menu");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseMenu(JSONArray response) {
        JSONObject jCateg, jDish;
        JSONArray jList;
        categItems = new ArrayList<>();
        rowItems = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                jCateg = response.getJSONObject(i);
                System.out.println(jCateg.toString());
                MenuCateg cItem = new MenuCateg(jCateg.getInt("id_categ"), jCateg.getString("nombre_categ"), jCateg.getInt("orden_categ"));
                jList = jCateg.getJSONArray("platos");
                for (int j = 0; j < jList.length(); j++){
                    jDish = jList.getJSONObject(j);
                    Dish dish = new Dish(jDish.getInt("id_plato"), jDish.getString("nombre_plato"), jDish.getString("imagen"),
                            jDish.getInt("precio"), jDish.getInt("orden_plato"));
                    cItem.addItem(dish);
                }
                cItem.sortDishes();
                categItems.add(cItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(categItems,new Comparator<MenuCateg>(){
            public int compare(MenuCateg s1,MenuCateg s2){
                if (s1.getcId() > s2.getcId()){
                    return 1;
                } else if (s1.getcId() < s2.getcId()){
                    return -1;
                } else {
                    return 0;
                }
            }});
        for (int z = 0; z < categItems.size(); z++){
            MenuCateg aux = categItems.get(z);
            MenuItem mItem = new MenuItem(aux.getcName(), 0, true);
            rowItems.add(mItem);
            List<Dish> dList = aux.getcList();
            for (int  x = 0; x < dList.size(); x++){
                Dish aux2 = dList.get(x);
                mItem = new MenuItem(aux2.getdName(), aux2.getdPrice(), false);
                mItem.setDishId(aux2.getdId());
                rowItems.add(mItem);
            }
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
