package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    Intent intent;
    String BASE_URI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateUser();
    }

    public void validateUser(){

        String REQUEST_TAG = "validateUser";
        String url="";
        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        getActivatedUserResponse(response);
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
    }

    private void getActivatedUserResponse(JSONObject j) {
        Integer result = 0;
        try {
            result = j.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error en la aplicación");
            finish();
        }
        switch (result) {
            case 200:   intent = new Intent(getApplicationContext(), ShopListActivity.class);
                        startActivity(intent);
                break;
            case 404:   newUser();
                        break;
            case 405:   userDisabled();
                        ErrorManager.showToastError("Su usuario se encuentra desactivado por Hoy Como");
                        break;
            default:    ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
                        break;
        }
    }

    private void userDisabled() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main2);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        Profile prof = Profile.getCurrentProfile();
        tvWelcome.setText("¡Bienvenido " + prof.getFirstName() + " " + prof.getLastName() + " !");
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);
        profilePictureView.setProfileId(prof.getId());
        RelativeLayout rlAddress = (RelativeLayout) findViewById(R.id.rlAddress);
        rlAddress.setVisibility(View.INVISIBLE);
        Button btSave = (Button) findViewById(R.id.btSave);
        btSave.setClickable(false);
        TextView tvDisabled = (TextView)findViewById(R.id.tvError);
        tvDisabled.setVisibility(View.VISIBLE);
        tvDisabled.setText("Lo siento, su usuario se encuentra deshabilitado por Hoy Como");
    }

    private void newUser (){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main2);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        Profile prof = Profile.getCurrentProfile();
        tvWelcome.setText("¡Bienvenido " + prof.getFirstName() + " " + prof.getLastName() + " !");
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);
        profilePictureView.setProfileId(prof.getId());
    }

    public void saveAddress(View v) {
        if (inputOk()) {
            ErrorManager.showToastError("Se guardaron los datos");
            //createUser();
        }
    }

    private boolean inputOk() {
        boolean ok = true;
        EditText etAddress = (EditText) findViewById(R.id.etAdress);
        if (etAddress.getText().length() < 5) {
            ErrorManager.showToastError("La dirección ingresada no es valida");
            ok = false;
        } else {
            EditText etCp = (EditText) findViewById(R.id.etCP);
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
        //TODO ver si el profile aca esta vivo
        Profile prof = Profile.getCurrentProfile();
        try {
            EditText etAddress = (EditText) findViewById(R.id.etAdress);
            EditText etCp = (EditText) findViewById(R.id.etCP);
            EditText etFloor = (EditText) findViewById(R.id.etFloor);
            EditText etDep = (EditText) findViewById(R.id.etDep);
            data.put("id", prof.getId());
            data.put("username", prof.getName());
            data.put("firstName", prof.getFirstName());
            data.put("lastName", prof.getLastName());
            data.put("address", etAddress.getText());
            data.put("cp", etCp.getText());
            data.put("floor", etFloor.getText());
            data.put("dep", etDep.getText());
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
        }
        // Initialize a new JsonObjectRequest instance
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
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
