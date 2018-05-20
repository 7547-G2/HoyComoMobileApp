package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import static com.grupo2.hoycomo.ErrorManager.showToastError;

public class DishActivity extends AppCompatActivity {

    String BASE_URI = "https://hoy-como-backend.herokuapp.com/api/mobileUser/menu/";
    Integer id = 0;
    Integer sId = 0;
    Integer sum = 0;
    Integer price = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
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
        id = intent.getIntExtra("dish_id", 0);
        sId = intent.getIntExtra("store_id", 0);
        EditText etObs = findViewById(R.id.etObs);
        etObs.setText("");
        //getDish(id);

        final TextView number = findViewById(R.id.tvCant);
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int after, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    TextView subT = findViewById(R.id.tvDsubTotal);
                    sum = price * Integer.parseInt(number.getText().toString());
                    subT.setText("$ " + sum);
                } catch (Exception e) {}
            }
        });

    }

    private void getDish(Integer id) {
        String REQUEST_TAG = "getDish";
        String url = BASE_URI + id;
        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseDish(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.println("error getMenu: " + error.toString());
                        showToastError("Error al obtener el menu");
                    }
                }
        );

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayRequest,REQUEST_TAG);
    }

    private void parseDish(JSONArray response) {
    }

    public void increaseNum(View view) {
        TextView tvNum = findViewById(R.id.tvCant);
        Button add = findViewById(R.id.btDsave);
        add.setEnabled(true);
        String aux = tvNum.getText().toString();
        Integer num = Integer.parseInt(aux);
        num++;
        if (num > 99) {num = 99;}
        tvNum.setText(num.toString());
    }

    public void decreaseNum(View view) {
        TextView tvNum = findViewById(R.id.tvCant);
        String aux = tvNum.getText().toString();
        Integer num = Integer.parseInt(aux);
        num--;
        if (num <= 0) {
            num = 0;
            Button add = findViewById(R.id.btDsave);
            add.setEnabled(false);
        }
        tvNum.setText(num.toString());
    }

    public void addDish(View view) {
        TextView tvNum = findViewById(R.id.tvCant);
        TextView tvName = findViewById(R.id.tvDname);
        EditText etObs = findViewById(R.id.etObs);
        Integer cant = Integer.parseInt(tvNum.getText().toString());
        DishItem aux = new DishItem(sId, id, tvName.getText().toString(), cant, sum, etObs.getText().toString());
        OrderSingleton.getInstance(this).addDish(aux);
        onBackPressed();
    }

}
