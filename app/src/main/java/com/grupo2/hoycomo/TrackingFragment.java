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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        String url = BASE_URI + "/orders/" + profile.getId();
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

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseOrders(JSONArray response) {

        if (response.length() != 0) {
            TextView empty = v.findViewById(R.id.tvEmpty);
            empty.setVisibility(View.INVISIBLE);
        } else {
            rowItems = new ArrayList<>();
            JSONObject jOrder;
            for (int i=0; i < response.length(); i++) {
                try {
                    jOrder = response.getJSONObject(i);
                    OrderItem item = new OrderItem(jOrder.getInt("order_id"), jOrder.getInt("store_id"),
                            jOrder.getString("store_name"), jOrder.getString("status"));
                    rowItems.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mylistview = v.findViewById(R.id.lvOrders);
            final OrderAdapter adapter = new OrderAdapter(getContext(), rowItems);
            mylistview.setAdapter(adapter);
            mylistview.setClickable(true);
        }

    }
}
