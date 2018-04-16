package com.grupo2.hoycomo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Samsung on 22/03/2018.
 */

public class StoresFragment extends Fragment {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";

    String[] shopNames;
    String[] shopPics;
    String[] shopDesc1s;
    String[] shopDesc2s;
    String[] favoritesMock;
    Integer[] favorites;

    List<ShopItem> rowItems;
    ListView mylistview;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.stores, container, false);
        shopPics = getResources().getStringArray(R.array.profile_pics);
        favoritesMock = getResources().getStringArray(R.array.favorite);
        /*
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
        */
        //getFavorites();
        getStores();
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
        String REQUEST_TAG = "getstores";
        String url = BASE_URI + "/comercios";
        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseStores(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 2: " + error.toString());
                        ErrorManager.showToastError("Error al obtener los comercios");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseStores(JSONArray response) {
        JSONObject comercio;
        String name, desc1, desc2, rank;
        rowItems = new ArrayList<ShopItem>();
        for (int i = 0; i < response.length(); i++) {
            try {
                comercio = response.getJSONObject(i);
                System.out.println(comercio.toString());
                name = comercio.getString("nombre");
                name = name.substring(0, Math.min(25, name.length()));
                rank = comercio.getString("rating");
                desc1 = comercio.getString("tipo") + " - ";
                desc2 = comercio.getString("leadTime") + " min - Entre $" + comercio.getString("minPrice") +
                        " y $" + comercio.getString("maxPrice");
                ShopItem item = new ShopItem(name, shopPics[0], desc1, desc2, Boolean.valueOf(favoritesMock[0]), Integer.parseInt(rank));
                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mylistview = (ListView) v.findViewById(R.id.storesList);
        CustomAdapter adapter = new CustomAdapter(getContext(), rowItems);
        mylistview.setAdapter(adapter);

    }
}
