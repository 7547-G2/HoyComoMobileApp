package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderDetailActivity extends AppCompatActivity {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";
    Integer oId = 0;
    ListView odSListView;
    List<StatusItem> rowSatusItems;
    ListView odDListView;
    List<DishItem> rowDishesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
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

        Intent intent = getIntent();
        oId = intent.getIntExtra("order_id", 99);
        System.out.println("------> order: " + oId);
        Button btCancel = findViewById(R.id.btCancel);
        btCancel.setEnabled(false);
        odSListView = findViewById(R.id.lvODstatus);
        odDListView = findViewById(R.id.lvODdishes);
        getOrderDetail();
    }

    private void getOrderDetail() {
        String REQUEST_TAG = "getOrderDetail";
        String url = BASE_URI + "/detallePedido/" + oId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        parseDetail(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getOrderDetail: " + error.toString());
                        ErrorManager.showToastError("Error al obtener el pedido");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void parseDetail(JSONObject response) {
        try {
            Integer total = response.getInt("total");
            String storeName = response.getString("storeName");
            TextView tvTotal = findViewById(R.id.tvODtotal);
            tvTotal.setText("$ " + String.valueOf(total));
            TextView tvStore = findViewById(R.id.tvODname);
            tvStore.setText(storeName);
            JSONArray jStatus, jDishes;
            JSONObject oStatus, oDish;
            jDishes = response.getJSONArray("orderContent");
            rowDishesItems = new ArrayList<>();
            for (int i=0; i < jDishes.length(); i++) {
                oDish = jDishes.getJSONObject(i);
                DishItem diAux = new DishItem(0,0,oDish.getString("name"),
                        oDish.getInt("cant"), oDish.getInt("subtotal"), "");
                rowDishesItems.add(diAux);
            }
            DishDetailAdapter adapter = new DishDetailAdapter(this, rowDishesItems);
            odDListView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(odDListView);

            jStatus = response.getJSONArray("statusHistory");
            rowSatusItems = new ArrayList<>();
            if (jStatus.length() == 1) {
                Button btCancel = findViewById(R.id.btCancel);
                btCancel.setEnabled(true);
            }
            for (int j=0; j < jStatus.length(); j++) {
                oStatus = jStatus.getJSONObject(j);
                StatusItem siAux = new StatusItem(oStatus.getString("status"), oStatus.getString("date"));
                rowSatusItems.add(siAux);
            }
            StatusAdapter adapter2 = new StatusAdapter(this, rowSatusItems);
            odSListView.setAdapter(adapter2);
            setListViewHeightBasedOnChildren(odSListView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cancelOrder(View view){
        String REQUEST_TAG = "cancelOrder";
        String url = BASE_URI + "/pedido/" + oId + "/cancel";
        //System.out.println(requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        //
                        ErrorManager.showToastError("Pedido Cancelado");
                        getOrderDetail();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(" 111: " + error.toString());
                String responseBody = null;
                try {
                    responseBody = new String(error.networkResponse.data,
                            HttpHeaderParser.parseCharset(error.networkResponse.headers));
                    System.out.println(" 222: " + responseBody);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
            }
        });
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);
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
