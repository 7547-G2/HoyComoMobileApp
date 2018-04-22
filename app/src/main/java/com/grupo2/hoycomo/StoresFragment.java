package com.grupo2.hoycomo;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

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

    String[] favoritesMock;
    Integer[] favorites;

    List<ShopItem> rowItems;
    List<String> categs;
    ListView mylistview;
    RelativeLayout filter;
    Button btFilter;
    EditText etMinPrice, etMaxPrice;
    Spinner spCateg, spDem, spRank, spDist;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.stores, container, false);
        favoritesMock = getResources().getStringArray(R.array.favorite);
        filter =  v.findViewById(R.id.rlFilter);
        btFilter = v.findViewById(R.id.btFilter);
        mylistview = v.findViewById(R.id.storesList);
        spDem = v.findViewById(R.id.spLeadTime);
        spDist = v.findViewById(R.id.spDist);
        spRank = v.findViewById(R.id.spRank);
        loadFilters(v);
        getTypes();
        btFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (filter.getVisibility() == View.VISIBLE) {
                    filter.setVisibility(View.GONE);
                    mylistview.setVisibility(View.VISIBLE);
                    btFilter.setText("Mostrar Filtros");
                } else {
                    filter.setVisibility(View.VISIBLE);
                    mylistview.setVisibility(View.INVISIBLE);
                    btFilter.setText("Ocultar Filtros");
                }

            }
        });
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

    private void loadFilters(View v) {

        etMinPrice = v.findViewById(R.id.etMinPrice);
        etMinPrice.setText("000");
        final SeekBar sbMin = v.findViewById(R.id.sbMinPrice);
        etMaxPrice = v.findViewById(R.id.etMaxPrice);
        etMaxPrice.setText("999");
        final SeekBar sbMax = v.findViewById(R.id.sbMaxPrice);
        etMinPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int after, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int progress = Math.round(Float.parseFloat(s.toString()));
                    sbMin.setProgress(progress);

                } catch (Exception e) {}
            }
        });

        sbMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                etMinPrice.setText(String.valueOf(progress));
                etMinPrice.setSelection(String.valueOf(progress).length());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                etMinPrice.setText(String.valueOf(progressChangedValue));

            }
        });
        etMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int after, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    int progress = Math.round(Float.parseFloat(s.toString()));
                    sbMax.setProgress(progress);

                } catch (Exception e) {}
            }
        });
        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                etMaxPrice.setText(String.valueOf(progress));
                etMaxPrice.setSelection(String.valueOf(progress).length());
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                etMaxPrice.setText(String.valueOf(progressChangedValue));
            }
        });
        Button btDef = v.findViewById(R.id.btDefault);
        btDef.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spCateg.setSelection(0);
                spDem.setSelection(5);
                spDist.setSelection(4);
                spRank.setSelection(0);
                etMinPrice.setText("000");
                etMaxPrice.setText("999");
            }
        });

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

    private  void getTypes() {
        String REQUEST_TAG = "getStoreTypes";
        String url= BASE_URI + "/tipoComida";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println(response.toString());
                        parseCategs(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 2: " + error.toString());
                        ErrorManager.showToastError("Error al obtener categorias");
                    }
                }
        );
        // Adding JsonObject request to request queue
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseCategs(JSONArray response) {
        JSONObject categ;
        categs = new ArrayList<String>();
        categs.add("Todas");
        for (int i = 0; i < response.length(); i++) {
            try {
                categ = response.getJSONObject(i);
                System.out.println(categ.toString());
                categs.add(categ.getString("tipo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, categs);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCateg = v.findViewById(R.id.spCateg);
        spCateg.setAdapter(dataAdapter);
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
        String name, desc1, desc2, rank, image, tipo;
        rowItems = new ArrayList<ShopItem>();
        for (int i = 0; i < response.length(); i++) {
            try {
                comercio = response.getJSONObject(i);
                System.out.println(comercio.toString());
                name = comercio.getString("nombre");
                name = name.substring(0, Math.min(25, name.length()));
                rank = comercio.getString("rating");
                rank = rank.substring(0,1);
                image = comercio.getString("imagenLogo");
                image = image.split(";base64,")[1];
                //tipo = comercio.getString("tipo");
                tipo = "Italiana";
                desc1 =  tipo + " - ";
                desc2 = comercio.getString("leadTime") + " min - Entre $" + comercio.getString("precioMinimo") +
                        " y $" + comercio.getString("precioMaximo");
                ShopItem item = new ShopItem(name, image, desc1, desc2, Boolean.valueOf(favoritesMock[0]), Integer.parseInt(rank));
                rowItems.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mylistview = v.findViewById(R.id.storesList);
        CustomAdapter adapter = new CustomAdapter(getContext(), rowItems);
        mylistview.setAdapter(adapter);

    }
}
