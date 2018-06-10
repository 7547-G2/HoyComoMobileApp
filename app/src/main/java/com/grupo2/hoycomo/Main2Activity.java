package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Main2Activity extends AppCompatActivity {

    Intent intent;
    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";
    String BASE_URI_GOOGLE = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Profile prof = Profile.getCurrentProfile();
        validateUser(prof);
    }

    public void validateUser(final Profile prof){

        String REQUEST_TAG = "validateUser";
        String url= BASE_URI + "/" + prof.getId() + "/authorized";
        // Initialize a new JsonObjectRequest instance


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        getActivatedUserResponse(response, prof);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 2: " + error.toString());
                        ErrorManager.showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );
        // Adding JsonObject request to request queue
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        if (response.equals("No existe el usuario")) {
                            newUser(prof);
                        } else if (response.equals("El usuario está deshabilitado")) {
                            userDisabled(prof);
                            ErrorManager.showToastError("Su usuario se encuentra desactivado por Hoy Como");
                        } else {
                            intent = new Intent(getApplicationContext(), ShopListActivity.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                ErrorManager.showToastError("Error al obtener el estado de la cuenta");
            }
        });

        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);
        */

    }

    private void getActivatedUserResponse(JSONObject j, Profile prof) {
        Integer result = 0;
        try {
            result = j.getInt("state");
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error en la aplicación");
            finish();
        }
        switch (result) {
            case 0:     intent = new Intent(getApplicationContext(), ShopListActivity.class);
                        startActivity(intent);
                        break;
            case 9:     newUser(prof);
                        break;
            case 1:
                        try {
                            userDisabled(prof, "Su usuario ha sido deshabilitado por Hoy Como: " + j.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
            default:    ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
                        break;
        }
    }


    private void userDisabled(Profile prof, String description) {
        setContentView(R.layout.activity_main2);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.ic_action_name);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);

        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        tvWelcome.setText("¡Bienvenido " + prof.getFirstName() + " " + prof.getLastName() + "!");
        ProfilePictureView profilePictureView;
        profilePictureView = findViewById(R.id.friendProfilePicture);
        profilePictureView.setProfileId(prof.getId());
        RelativeLayout rlAddress = findViewById(R.id.rlAddress);
        rlAddress.setVisibility(View.INVISIBLE);
        Button btSave = findViewById(R.id.btSaveFilter);
        btSave.setVisibility(View.GONE);
        TextView tvDisabled = findViewById(R.id.tvError);
        tvDisabled.setVisibility(View.VISIBLE);
        tvDisabled.setText(description);
    }

    private void newUser(Profile prof){
        setContentView(R.layout.activity_main2);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.ic_action_name);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        tvWelcome.setText("¡Bienvenido " + prof.getFirstName() + " " + prof.getLastName() + " !");
        ProfilePictureView profilePictureView;
        profilePictureView = findViewById(R.id.friendProfilePicture);
        profilePictureView.setProfileId(prof.getId());
    }

    public void saveAddress(View v) {
        if (inputOk()) {
            ErrorManager.showToastError("Se guardaron los datos");
            validateAddress();
        }
    }

    private boolean inputOk() {
        boolean ok = true;
        EditText etAddress = findViewById(R.id.etPAdress);
        if (etAddress.getText().length() < 5) {
            ErrorManager.showToastError("La dirección ingresada no es valida");
            ok = false;
        } else {
            EditText etCp = findViewById(R.id.etCP);
            if (etCp.getText().length() < 5) {
                ErrorManager.showToastError("El codigo postal ingresado no es valido");
                ok = false;
            }
        }
        return  ok;
    }

    private void createUser() {
        String REQUEST_TAG = "createUser";
        String url = BASE_URI;

        JSONObject data = new JSONObject();
        JSONObject address = new JSONObject();
        Profile prof = Profile.getCurrentProfile();
        try {
            EditText etAddress = findViewById(R.id.etPAdress);
            EditText etCp = findViewById(R.id.etCP);
            EditText etFloor = findViewById(R.id.etPFloor);
            EditText etDep = findViewById(R.id.etDep);
            EditText etPhone = findViewById(R.id.etPhone);
            data.put("facebookId", prof.getId());
            data.put("username", prof.getName());
            data.put("firstName", prof.getFirstName());
            data.put("lastName", prof.getLastName());
            data.put("link", prof.getLinkUri().toString());
            data.put("phone", etPhone.getText().toString());
            //System.out.println("link: " + prof.getLinkUri().toString());
            data.put("mobileUserState", "AUTHORIZED");
            address.put("street", etAddress.getText());
            address.put("postalCode", etCp.getText());
            address.put("floor", etFloor.getText());
            address.put("department", etDep.getText());
            data.put("addressDto", address);
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
                        System.out.println(response);
                        intent = new Intent(getApplicationContext(), ShopListActivity.class);
                        startActivity(intent);
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

    public void goBack(View view) {
        super.onBackPressed();
    }

    public void validateAddress(){
        String REQUEST_TAG = "validateGoogleAddress";
        EditText etDir = findViewById(R.id.etPAdress);
        String url = BASE_URI_GOOGLE + etDir.getText().toString() + ",CABA&components=country:AR&key=AIzaSyB6dfZePd70gWspDUgPfWeuT5nTAjvg6oQ";
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
        boolean valida = false;
        try {
            String result = j.getString("status");
            if (result.equals("OK")) {
                //System.out.println("OK");
                JSONArray array = j.getJSONArray("results");
                for (int i=0; i<array.length(); i++){
                    //System.out.println("Results: " + i);
                    JSONObject o1 = array.getJSONObject(i);
                    //System.out.println("o1: " + o1.toString());
                    JSONObject o2 = o1.getJSONObject("geometry");
                    //System.out.println("o2: " + o2.toString());
                    String type = o2.getString("location_type");
                    //System.out.println("type: " + type);
                    JSONObject ubi = o2.getJSONObject("location");
                    if (type.contentEquals("ROOFTOP")){
                        valida = true;
                        //System.out.println("valida true");
                        //lat = ubi.getDouble("lat");
                        //lng = ubi.getDouble("lng");
                        createUser();
                    }
                }
            }
            if (!valida) {
                ErrorManager.showToastError("La direccion no es valida");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error al validar la direccion");
        }
    }
}
