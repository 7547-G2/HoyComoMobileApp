package com.grupo2.hoycomo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.grupo2.hoycomo.ErrorManager.showToastError;

/**
 * Created by Samsung on 22/03/2018.
 */

public class TrackingFragment extends Fragment {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";
    View v;
    List<OrderItem> rowItems;
    ListView mylistview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tracking, container, false);
        mylistview = v.findViewById(R.id.lvOrders);
        getOrders();
        return v;
    }

    private void getOrders() {
        String REQUEST_TAG = "getOrders";
        Profile profile = Profile.getCurrentProfile();
        String url = BASE_URI + "/" + profile.getId() + "/pedido";
        System.out.println("getOrders: " + url);
        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseOrders(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error : getOrders" + error.toString());
                        showToastError("Error al obtener los pedidos");
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseOrders(JSONArray response) {
        TextView empty = v.findViewById(R.id.tvEmpty);
        if (response.length() == 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.INVISIBLE);
            rowItems = new ArrayList<>();
            JSONObject jOrder;
            for (int i=0; i < response.length(); i++) {
                try {
                    jOrder = response.getJSONObject(i);
                    System.out.println("parseOrders: orden " + i + " : " + jOrder.toString());
                    OrderItem item = new OrderItem(jOrder.getInt("order_id"), jOrder.getInt("store_id"),
                            jOrder.getString("store_name"), jOrder.getString("status"), jOrder.getString("fecha"));
                    rowItems.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mylistview = v.findViewById(R.id.lvOrders);
            Collections.sort(rowItems,new Comparator<OrderItem>(){
                public int compare(OrderItem d1,OrderItem d2){
                    //return d1.getdId() - d2.getdId();
                    Long a,b;
                    a = Long.parseLong(d1.getFecha());
                    //System.out.println("string: " + d1.getDate() + " long: " + a);
                    b = Long.parseLong(d2.getFecha());
                    //System.out.println("string: " + d2.getDate() + " long: " + b);
                    return b.compareTo(a);
                }});
            final OrderAdapter adapter = new OrderAdapter(getContext(), rowItems);
            mylistview.setAdapter(adapter);
            mylistview.setClickable(true);
        }

    }
}
