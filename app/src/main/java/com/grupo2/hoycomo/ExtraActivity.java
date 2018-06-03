package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.grupo2.hoycomo.ErrorManager.showToastError;

public class ExtraActivity extends AppCompatActivity {

    ListView extraListView;
    List<ExtraItem> rowItems;
    ExtraAdapter adapter2 = null;
    private Integer dishId = 0;
    ArrayList<Integer> extraList;
    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
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
        dishId = intent.getIntExtra("dish_id", 0);
        extraListView = findViewById(R.id.lvExtras);
        getList();
    }

    private void getList() {
        String REQUEST_TAG = "getExtras";
        String url = BASE_URI + "/plato/" + dishId;
        // Initialize a new JsonObjectRequest instance
        /*
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        parseExtras(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getExtras: " + error.toString());
                        showToastError("Error al obtener los extras");
                    }
                }
        );
        */
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        parseExtras(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getExtras: " + error.toString());
                        showToastError("Error al obtener los extras");
                    }
                }
        );
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void parseExtras(JSONObject response) {
        JSONArray arr = null;
        try {
            arr = response.getJSONArray("opcionales");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("error parseExtras: " + e.toString());
            showToastError("Error al obtener los extras");
        }
        if (arr.length() > 0) {
            JSONObject a;
            Integer id, price;
            String name;
            rowItems = new ArrayList<>();
            for (int i=0; i < arr.length(); i++){
                try {
                    a = arr.getJSONObject(i);
                    id = a.getInt("id");
                    name = a.getString("nombre");
                    price = a.getInt("precio");
                    ExtraItem ei = new ExtraItem(dishId, id, name, price);
                    rowItems.add(ei);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("cantidad rowItems: " + rowItems.size());
            adapter2 = new ExtraAdapter(this, rowItems);
            extraListView.setAdapter(adapter2);
            extraListView.setClickable(true);
            setListViewHeightBasedOnChildren(extraListView);
        } else {
            showToastError("No hay adicionales para este plato");
        }

    }

    private Integer getTotal(){
        System.out.println("entro total");
        Integer total = 0;
        rowItems = adapter2.getItemList();
        extraList = new ArrayList<>();
        for (int i=0; i < rowItems.size(); i++){
            ExtraItem aux = rowItems.get(i);
            System.out.println("aux: " + aux.getExtraName());
            if (aux.getSelected()){
                System.out.println("seleccionado");
                total = total + aux.getExtraPrice();
                extraList.add(aux.getExtraId());
            }
        }
        System.out.println("lista de extras: " + extraList.size());
        return total;
    }

    public void confExtras(View view) {
        Intent intent = new Intent();
        intent.putExtra("total", getTotal());
        intent.putIntegerArrayListExtra("list", extraList);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void limExtras(View view){
        getList();
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
