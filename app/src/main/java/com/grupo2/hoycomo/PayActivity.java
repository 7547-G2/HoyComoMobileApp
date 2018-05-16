package com.grupo2.hoycomo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

public class PayActivity extends AppCompatActivity {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";
    String BASE_URI_GOOGLE = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
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

        Profile profile = Profile.getCurrentProfile();
        getAddress(profile);


    }

    public void getAddress(Profile profile){
        String REQUEST_TAG = "getMobileAddress";
        String url = BASE_URI + "/" + profile.getId();
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        parseAddress(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getAddress: " + error.toString());
                        ErrorManager.showToastError("Error al obtener la direccion");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void parseAddress(JSONObject j) {

        try {
            if (j.get("addressDto") != null) {
                JSONObject address = j.getJSONObject("addressDto");
                EditText etDir = findViewById(R.id.etPAdress);
                EditText etFloor = findViewById(R.id.etPFloor);
                EditText etDep = findViewById(R.id.etPDep);
                etDir.setText(address.getString("street"));
                etFloor.setText(address.getString("floor"));
                etDep.setText(address.getString("department"));
            } else {
                ErrorManager.showToastError("Error al obtener la direccion");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error al obtener la direccion");
        }
    }

    public void validateAddress(String ad){
        String REQUEST_TAG = "validateGoogleAddress";
        EditText etDir = findViewById(R.id.etPAdress);
        String url = BASE_URI + etDir.getText().toString() + ",CABA&components=country:AR&key=AIzaSyB6dfZePd70gWspDUgPfWeuT5nTAjvg6oQ";
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        parseGoogleResp(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error validateAddress: " + error.toString());
                        ErrorManager.showToastError("Error al validar la direccion");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void parseGoogleResp(JSONObject j) {

        try {
            if (j.get("addressDto") != null) {
                JSONObject address = j.getJSONObject("addressDto");
                EditText etDir = findViewById(R.id.etPAdress);
                EditText etFloor = findViewById(R.id.etPFloor);
                EditText etDep = findViewById(R.id.etPDep);
                etDir.setText(address.getString("street"));
                etFloor.setText(address.getString("floor"));
                etDep.setText(address.getString("department"));
            } else {
                ErrorManager.showToastError("Error al validar la direccion");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error al validar la direccion");
        }
    }
}
