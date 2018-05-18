package com.grupo2.hoycomo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.Profile;

import org.json.JSONArray;
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

        RadioButton rbEfect = findViewById(R.id.rbEfect);
        rbEfect.setSelected(true);
        RadioGroup rg = findViewById(R.id.rgPay);
        final RelativeLayout rl = findViewById(R.id.rlTC);
        rl.setVisibility(View.INVISIBLE);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbEfect) {
                    rl.setVisibility(View.INVISIBLE);
                } else {
                    rl.setVisibility(View.VISIBLE);
                }
            }
        });

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
                System.out.println("OK");
                JSONArray array = j.getJSONArray("results");
                for (int i=0; i<array.length(); i++){
                    System.out.println("Results: " + i);
                    JSONObject o1 = array.getJSONObject(i);
                    System.out.println("o1: " + o1.toString());
                    JSONObject o2 = o1.getJSONObject("geometry");
                    System.out.println("o2: " + o2.toString());
                    String type = o2.getString("location_type");
                    System.out.println("type: " + type);
                    if (type.contentEquals("ROOFTOP")){
                        valida = true;
                        System.out.println("valida true");
                        validatePayMethod();
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

    private void validatePayMethod() {
        RadioButton rbEfect = findViewById(R.id.rbEfect);
        boolean ok = false;
        if (rbEfect.isChecked()){
            ok = true;
        } else {
            EditText etName = findViewById(R.id.etPname);
            String name = etName.getText().toString();
            System.out.println("name: " + name);
            if (name.length() < 5) {
                ErrorManager.showToastError("El nombre de la tarjeta es invalido");
            } else {
                EditText etNumber = findViewById(R.id.etPnumber);
                String numberTc = etNumber.getText().toString();
                System.out.println("numberTc: " + numberTc);
                if (numberTc.length() < 15 || !(esVisa(numberTc) || esMaster(numberTc) || esAmex(numberTc)) || !luhnTest(numberTc )) {
                    ErrorManager.showToastError("El numero de la tarjeta es invalido");
                } else {
                    EditText etMM = findViewById(R.id.etPmm);
                    String mm = etMM.getText().toString();
                    EditText etAA = findViewById(R.id.etPaa);
                    String aa = etAA.getText().toString();

                    System.out.println("mm: " + mm);
                    System.out.println("aa: " + aa);
                    if (mm.isEmpty() || aa.isEmpty()){
                        ErrorManager.showToastError("Fecha de vencimiento invalida");
                    } else {
                        Integer mmInt = Integer.parseInt(mm);
                        Integer aaInt = Integer.parseInt(aa);
                        if (mmInt > 12 || aaInt < 18 || (mmInt < 6 && aaInt < 19)) {
                            ErrorManager.showToastError("Fecha de vencimiento invalida");
                        } else {
                            EditText etCod = findViewById(R.id.etPcod);
                            String cod = etCod.getText().toString();
                            System.out.println("cod: " + cod);
                            if (cod.length() != 3){
                                ErrorManager.showToastError("Codigo de Seguridad Invalido");
                            } else {
                                ok = true;
                            }
                        }
                    }
                }
            }
        }

        if (ok) {
            ErrorManager.showToastError("TODO OK - grabar");
        }

    }

    private boolean esVisa(String numberTc) {
        String pre = numberTc.substring(0,4);
        Integer preInt = Integer.parseInt(pre);
        if (numberTc.length() == 16 && preInt >= 4000 && preInt <= 4999){
            System.out.println("es visa");
            return true;
        } else {
            return false;
        }
    }

    private boolean esMaster(String numberTc) {
        String pre = numberTc.substring(0,3);
        Integer preInt = Integer.parseInt(pre);
        if (numberTc.length() == 16 && preInt >= 510 && preInt <= 559){
            System.out.println("es master");
            return true;
        } else {
            return false;
        }
    }

    private boolean esAmex(String numberTc) {
        String pre = numberTc.substring(0,4);
        Integer preInt = Integer.parseInt(pre);
        if (numberTc.length() == 16 && preInt >= 3000 && preInt <= 3059){
            System.out.println("es amex");
            return true;
        } else {
            return false;
        }
    }

    public static boolean luhnTest(String numero){

        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numero).reverse().toString();
        for(int i = 0 ;i < reversa.length();i++){
            int digito = Character.digit(reversa.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if(digito >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

    private boolean inputOk() {
        boolean ok = true;
        EditText etAddress = findViewById(R.id.etPAdress);
        if (etAddress.getText().length() < 5) {
            ErrorManager.showToastError("La dirección ingresada no es valida");
            ok = false;
        }
        return  ok;
    }

    public void confirm(View view){
        if (inputOk()) {
            validateAddress();
        }
    }
}
