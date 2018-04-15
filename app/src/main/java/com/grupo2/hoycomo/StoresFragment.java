package com.grupo2.hoycomo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 22/03/2018.
 */

public class StoresFragment extends Fragment {

    String[] shopNames;
    String[] shopPics;
    String[] shopDesc1s;
    String[] shopDesc2s;
    String[] favoritesMock;
    Integer[] favorites;

    List<ShopItem> rowItems;
    ListView mylistview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stores, container, false);

        rowItems = new ArrayList<ShopItem>();

        getFavorites();
        shopNames = getResources().getStringArray(R.array.shop_names);

        shopPics = getResources().getStringArray(R.array.profile_pics);

        shopDesc1s = getResources().getStringArray(R.array.desc1);

        shopDesc2s = getResources().getStringArray(R.array.desc2);

        favoritesMock = getResources().getStringArray(R.array.favorite);

        for (int i = 0; i < shopNames.length; i++) {
            ShopItem item = new ShopItem(shopNames[i],
                    shopPics[0], shopDesc1s[0],
                    shopDesc2s[0], Boolean.valueOf(favoritesMock[0]), 3);
            rowItems.add(item);
        }

        mylistview = (ListView) v.findViewById(R.id.storesList);
        CustomAdapter adapter = new CustomAdapter(getContext(), rowItems);
        mylistview.setAdapter(adapter);
        return v;
    }

    private void getFavorites() {
        /*
        String REQUEST_TAG = "userFavorites";
        String url = "";
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseFavorites(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 2: " + error.toString());
                        showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    */
    }

    private void parseFavorites(JSONObject response) {

    }

    private void getStores() {
        /*
        String REQUEST_TAG = "stores";
        String url = "";
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseStores(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 2: " + error.toString());
                        showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    */
    }

    private void parseStores(JSONObject response) {

    }
}
