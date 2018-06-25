package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

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
    String sId = "";
    Integer off = 0;
    ListView menuListView;
    List<MenuCateg> categItems;
    List<MenuItem> rowItems;
    TextView tvDTname, tvDTleadTime;
    String coments = "";
    String name = "";

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

        ScrollView scrollView = findViewById(R.id.svMenu);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        Intent intent = getIntent();
        String leadTime, minPrice, maxPrice;
        Integer rank;
        name = intent.getStringExtra("name");
        leadTime = intent.getStringExtra("leadTime");
        minPrice = intent.getStringExtra("minPrice");
        maxPrice = intent.getStringExtra("maxPrice");
        sId = intent.getStringExtra("store_id");
        rank = intent.getIntExtra("rank", 3);
        tvDTname = findViewById(R.id.tvDtName);
        tvDTleadTime = findViewById(R.id.tvDtLeadTime);
        TextView tvDTprice = findViewById(R.id.tvDtPrice);
        tvDTname.setText(name);
        tvDTleadTime.setText(leadTime + " min");
        tvDTprice.setText("$" + minPrice + " - $" + maxPrice);
        ImageView[] iv = new ImageView[5];;
        iv[0] = findViewById(R.id.ivSS1);
        iv[1] = findViewById(R.id.ivSS2);
        iv[2] = findViewById(R.id.ivSS3);
        iv[3] = findViewById(R.id.ivSS4);
        iv[4] = findViewById(R.id.ivSS5);
        for (int i=0; i < rank ; i++){
            iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
        }

        menuListView = findViewById(R.id.menuList);
        getMenu();
    }

    private void getMenu() {
        String REQUEST_TAG = "getStoreMenu";
        String url = BASE_URI + sId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
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
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void parseMenu(JSONObject response) {
        JSONObject jCateg, jDish;
        JSONArray jList, responseArray = null, comentArray = null;
        categItems = new ArrayList<>();
        rowItems = new ArrayList<>();
        try {
            String image = response.getString("imagen_comercio");
            off = response.getInt("descuentoGlobal");
            if (off > 0) {
                TextView tvAlert = findViewById(R.id.tvOFF);
                tvAlert.setVisibility(View.VISIBLE);
                tvAlert.setText("¡Solo por hoy " + off + " % de descuento!");
            }
            image = image.split(";base64,")[1];
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView ivImage = findViewById(R.id.ivDtPicture);
            ivImage.setImageBitmap(decodedByte);
            responseArray = response.getJSONArray("menu");
            comentArray = response.getJSONArray("comentarios");
            TextView tvComents = findViewById(R.id.tvDtOpinion);
            tvComents.setText("Comentarios(" + comentArray.length() + ")");
            if (comentArray.length() > 0 ){
                coments = comentArray.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < responseArray.length(); i++) {
            try {
                jCateg = responseArray.getJSONObject(i);
                //System.out.println(jCateg.toString());
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
            MenuItem mItem = new MenuItem(sId, aux.getcName(), 0, true);
            rowItems.add(mItem);
            List<Dish> dList = aux.getcList();
            for (int  x = 0; x < dList.size(); x++){
                Dish aux2 = dList.get(x);
                mItem = new MenuItem(sId, aux2.getdName(), aux2.getdPrice(), false);
                mItem.setDishId(aux2.getdId());
                rowItems.add(mItem);
            }
        }
        RelativeLayout loading = findViewById(R.id.rlLoading);
        loading.setVisibility(View.GONE);
        tvDTname.setBackgroundColor(Color.parseColor("#9e403f3f"));
        tvDTname.setTextColor(Color.WHITE);
        MenuAdapter adapter = new MenuAdapter(this, rowItems, off);
        menuListView.setAdapter(adapter);
        menuListView.setClickable(true);
        setListViewHeightBasedOnChildren(menuListView);

    }

    public void checkout(View view){
        Intent intent= new Intent(getApplicationContext(), ShoppingActivity.class);
        intent.putExtra("store_id", Integer.parseInt(sId));
        intent.putExtra("store_name", tvDTname.getText().toString());
        intent.putExtra("store_ld", tvDTleadTime.getText().toString());
        startActivity(intent);
    }

    public void showComents(View view){
        System.out.println("entro showComents");
        //coments = "[{\"date\": \"201806012055\", \"rating\": 5, \"coment\": \"un comenario corto\", \"date-rep\": \"201806012200\", \"replica\": \"replica corta\"},{\"date\": \"201806012055\", \"rating\": 1, \"coment\": \"Siempre se encuentra un agente externo a quien culpar. Esto es muy común entre los distintos departamentos de una empresa.\", \"date-rep\": \"201806012200\", \"replica\": \"replica corta\"},{\"date\": \"201806012055\", \"rating\": 0, \"coment\": \"Siempre se encuentra un agente externo a quien culpar.\", \"date-rep\": \"201806012200\", \"replica\": \"Los managers a veces actúan de forma “proactiva” al encarar los problemas, en lugar de tener una actitud “reactiva”. Esto en ciertos casos se transforma en reactividad disfrazada.\"},{\"date\": \"201806012055\", \"rating\": 0, \"coment\": \"Siempre se encuentra un agente externo a quien culpar.\", \"date-rep\": \"201806012200\", \"replica\": \"Los managers a veces actúan de forma “proactiva” al encarar los problemas, en lugar de tener una actitud “reactiva”. Esto en ciertos casos se transforma en reactividad disfrazada.\"}]";
        if (!coments.isEmpty()){
            Intent intent= new Intent(getApplicationContext(), ComentActivity.class);
            intent.putExtra("store_name", name);
            intent.putExtra("store_coments", coments);
            startActivity(intent);
        }

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
