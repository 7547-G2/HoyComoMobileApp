package com.grupo2.hoycomo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

import java.io.UnsupportedEncodingException;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Samsung on 22/03/2018.
 */

public class AccountFragment extends Fragment {
    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser";
    CallbackManager callbackManager;
    String accountStatus = "";
    String accountAddress = "";
    String accountCP = "";
    String accountFloor = "";
    String accountDep = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();

        Profile profile = Profile.getCurrentProfile();
        ProfilePictureView profilePictureView;

        View v = inflater.inflate(R.layout.account, container, false);
        profilePictureView = (ProfilePictureView) v.findViewById(R.id.friendProfilePicture);

        profilePictureView.setProfileId(profile.getId());

        Button btLogin = (Button) v.findViewById(R.id.login_button);
        btLogin.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                logOut();
            }
        });

        TextView tvStatus = (TextView) v.findViewById(R.id.tvAccountStatus);
        validateUser(profile, tvStatus);
        getAddress(profile);

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
                JSONObject data = new JSONObject();
                try {
                    data.put("street", etDir.getText());
                    data.put("postalCode", etCP.getText());
                    data.put("floor", etFloor.getText());
                    data.put("department", etDep.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                    ErrorManager.showToastError("Error al guardar la direccion");
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

    public void validateUser(final Profile prof, final TextView tvStatus){

        String REQUEST_TAG = "validateUser";
        String url= BASE_URI + "/" + prof.getId() + "/authorized";
        // Initialize a new JsonObjectRequest instance
        /*
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
        */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        if (response.equals("El usuario está deshabilitado")) {
                            tvStatus.setText("Deshabilitada");
                        } else {
                            tvStatus.setText("Activa");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
                ErrorManager.showToastError("Error en la aplicación, vuelva a intentar");
            }
        });
        com.grupo2.hoycomo.AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);

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
                accountAddress = address.getString("street");
                accountCP = address.getString("postalCode");
                accountFloor = address.getString("floor");
                accountDep = address.getString("department");
                final EditText etDir = (EditText) getView().findViewById(R.id.etAdress);
                etDir.setText(accountAddress);

                final EditText etCP = (EditText) getView().findViewById(R.id.etCP);
                etCP.setText(accountCP);

                final EditText etFloor = (EditText) getView().findViewById(R.id.etFloor);
                etFloor.setText(accountFloor);

                final EditText etDep = (EditText) getView().findViewById(R.id.etDep);
                etDep.setText(accountDep);
            } else {
                ErrorManager.showToastError("Error al obtener la direccion");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            ErrorManager.showToastError("Error al obtener la direccion");
        }
    }

    public void sendDataBackend(JSONObject data){
        String REQUEST_TAG = "updateAddress";
        Profile profile = Profile.getCurrentProfile();
        String url = BASE_URI + "/" + profile.getId() + "/address";

        // Initialize a new JsonObjectRequest instance
        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        ErrorManager.showToastError("Se guardaron los cambios");
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error sendDataBackend: " + error.toString());
                        ErrorManager.showToastError("Error al guardar los cambios");
                    }
                }
        );
        */
        final String requestBody = data.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response.toString());
                        ErrorManager.showToastError("Se guardaron los cambios");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error sendDataBackend: " + error.toString());
                ErrorManager.showToastError("Error al guardar los cambios");
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
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);
    }

    private void logOut() {
        new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Cerrar Sesión")
                .setMessage("Desea cerrar su sesión ?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        getActivity().finishAffinity();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
