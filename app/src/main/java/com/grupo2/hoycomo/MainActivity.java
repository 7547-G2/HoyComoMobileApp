package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    Intent intent;
    String BASE_URI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isOnline()){
            Context context = getApplicationContext();
            CharSequence text = "No se detectó conexión a internet, la aplicación no podrá utilizarse";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        } else {
            boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
            if (!loggedIn) {
                setContentView(R.layout.activity_main);

                callbackManager = CallbackManager.Factory.create();

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                //
                            }

                            @Override
                            public void onCancel() {
                                showToastError("Su solicitud de login fue cancelada por Facebook");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                showToastError("Se produjo un error al intentar conectarse con Facebook, pruebe más tarde");
                            }
                        });
            } else {
                Profile profile = Profile.getCurrentProfile();
                System.out.println("nombre es : " + profile.getFirstName() + ", " + profile.getLastName());
                System.out.println("id es : " + profile.getId());
                System.out.println("usuario es : " + profile.getName());

                validateUser(BASE_URI + "?userId=" + profile.getId());

            }
        }
    }

    public void validateUser(String url){

        String REQUEST_TAG = "validateUser";

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
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }

    private void getActivatedUserResponse(JSONObject j) {
        Integer result = 0;
        try {
            result = j.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            showToastError("Error en la aplicacion");
            finish();
        }
        switch (result) {
            case 200:   intent = new Intent(getApplicationContext(), ShopListActivity.class);
                        startActivity(intent);
                        break;
            case 404:   createUser();
                        break;
            case 405:   setContentView(R.layout.activity_main);
                        showToastError("Su usuario se encuentra desactivado por Hoy Como");
                        break;
            default:    showToastError("Error en la aplicacion, vuelva a intentar");
                        break;
        }
    }

    private void createUser() {
        String REQUEST_TAG = "createUser";
        String url = BASE_URI;

        JSONObject data = new JSONObject();
        Profile prof = Profile.getCurrentProfile();
        try {
            data.put("id", prof.getId());
            data.put("username", prof.getName());
            data.put("firstName", prof.getFirstName());
            data.put("lastName", prof.getLastName());
        } catch (JSONException e) {
            e.printStackTrace();
            // TODO: mostrar error
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
                                // TODO: mostrar error
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // TODO: mostrar error
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error 3: " + error.toString());
                        // TODO: mostrar error
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showToastError(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
