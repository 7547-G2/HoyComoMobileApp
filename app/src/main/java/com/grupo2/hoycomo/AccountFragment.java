package com.grupo2.hoycomo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Samsung on 22/03/2018.
 */

public class AccountFragment extends Fragment {
    String BASE_URI = "";
    CallbackManager callbackManager;
    String accountStatus = "Activa";
    String accountAddress = "Av. Paseo Colón 850";
    String accountCP = "C1063ACV";
    String accountFloor = "4";
    String accountDep = "C";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //
                    }

                    @Override
                    public void onCancel() {
                        //
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        //
                    }
                });

        Profile profile = Profile.getCurrentProfile();
        ProfilePictureView profilePictureView;

        View v = inflater.inflate(R.layout.account, container, false);
        profilePictureView = (ProfilePictureView) v.findViewById(R.id.friendProfilePicture);

        profilePictureView.setProfileId(profile.getId());
        LoginButton loginButton = (LoginButton) v.findViewById(R.id.login_button);
        loginButton.setFragment(this);

        validateUser(profile.getId());
        getAddress(profile.getId());
        TextView tvStatus = (TextView) v.findViewById(R.id.tvAccountStatus);
        tvStatus.setText(accountStatus);

        final EditText etDir = (EditText) v.findViewById(R.id.etAdress);
        etDir.setText(accountAddress);

        final EditText etCP = (EditText) v.findViewById(R.id.etCP);
        etCP.setText(accountCP);

        final EditText etFloor = (EditText) v.findViewById(R.id.etFloor);
        etFloor.setText(accountFloor);

        final EditText etDep = (EditText) v.findViewById(R.id.etDep);
        etDep.setText(accountDep);

        final Button button = v.findViewById(R.id.btSave);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: ENVIAR DATOS AL BACKEND
                JSONObject data = new JSONObject();
                try {
                    data.put("address", etDir.getText());
                    data.put("cp", etCP.getText());
                    data.put("floor", etFloor.getText());
                    data.put("dep", etDep.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                    // TODO: mostrar error
                }
                sendDataBackend(data);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void validateUser(String id){
        /*
        String REQUEST_TAG = "validateUser";
        url = "BASE_URI + "?userId=" + id";
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
                        showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
        */
    }

    private void getActivatedUserResponse(JSONObject j) {
        /*
        Integer result = 0;
        try {
            result = j.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            showToastError("Error en la aplicación");
            finish();
        }
        switch (result) {
            case 200:   accountStatus = "Activa";
                break;
            case 405:   accountStatus = "Deshabilitado";
                break;
            default:    showToastError("Error en la aplicación, vuelva a intentar");
                break;
        }
        */
    }

    public void getAddress(String id){
        /*
        String REQUEST_TAG = "getMobileAddress";
        url = "BASE_URI + "?userId=" + id";
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
                        showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
        */
    }

    private void parseAddress(JSONObject j) {
        /*
        Integer result = 0;
        try {
            result = j.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
            showToastError("Error en la aplicación");
            finish();
        }
        switch (result) {
            case 200:   accountAddress = l.getString("address");
                        accountCP = l.getString("cp");
                        accountFloor = l.getString("floor");
                        accountDep = l.getString("dep");
                        break;
            case 404:   showToastError("Por favor cargue una direccion");
                        break;
            default:    showToastError("Error en la aplicación, vuelva a intentar");
                        break;
        }
        */
    }

    public void sendDataBackend(JSONObject data){
        /*
        String REQUEST_TAG = "updateAddress";
        url = "";

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        Context context = getActivity().getApplicationContext();
                        CharSequence text = "Los datos se guardaron";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error sendDataBackend: " + error.toString());
                        showToastError("Error en la aplicación");
                        finish();
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest,REQUEST_TAG);
    */
    }
}
