package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RankActivity extends AppCompatActivity {

    Integer oId = 0;
    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
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

        final RatingBar rbRatingBar = findViewById(R.id.rbRank);
        rbRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Float ratingVal = rating;
                Float ratingvalue = rbRatingBar.getRating();
                Button btSend = findViewById(R.id.btRsend);
                if (ratingvalue > 0) {
                    btSend.setEnabled(true);
                } else {
                    btSend.setEnabled(false);
                }
            }
        });
    }

    public void sendRank(View view) {
        String REQUEST_TAG = "createComment";
        Profile prof = Profile.getCurrentProfile();
        String url = BASE_URI + "/" + prof.getId() + "/pedido/" + oId.toString() + "/comentario";
        System.out.println("uri post comment: " + url);

        JSONObject data = new JSONObject();
        try {
            RatingBar rbRatingBar = findViewById(R.id.rbRank);
            EditText etComent = findViewById(R.id.etRcomment);
            data.put("estrellas", rbRatingBar.getRating());
            data.put("comentario", etComent.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
        }
        // Initialize a new JsonObjectRequest instance
        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        try {
                            if (response.getInt("code") == 200) {
                                intent = new Intent(getApplicationContext(), ShopListActivity.class);
                                startActivity(intent);
                            } else {
                                ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 3: " + error.toString());
                        ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
                    }
                }
        );

        // Adding JsonObject request to request queue
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
        */
        final String requestBody = data.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        ErrorManager.showToastError("Gracias por su comentario !");
                        onBackPressed();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    System.out.println(uee.toString());
                    return null;
                }
            }
        };
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);
    }

}
