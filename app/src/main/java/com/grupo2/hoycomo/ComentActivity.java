package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.grupo2.hoycomo.ErrorManager.showToastError;

public class ComentActivity extends AppCompatActivity {

    ListView comentListView;
    List<ComentItem> rowItems;
    String storeName = "", coments = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);
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

        comentListView = findViewById(R.id.lvComents);
        Intent intent = getIntent();
        storeName = intent.getStringExtra("store_name");
        coments = intent.getStringExtra("store_coments");
        System.out.println(coments);
        TextView title = findViewById(R.id.tvCname);
        title.setText("Comentarios sobre " + storeName);
        rowItems = new ArrayList<>();
        try {
            JSONArray jComents = new JSONArray(coments);
            JSONObject jComent;
            Integer rating;
            String date, coment, dateRep, replic;
            for (int j=0; j < jComents.length(); j++) {
                jComent = jComents.getJSONObject(j);
                System.out.println(jComent.toString());
                rating = jComent.getInt("rating");
                date = jComent.getString("date");
                coment = jComent.getString("coment");
                if(jComent.isNull("replica"))
                {
                    dateRep = "";
                    replic = "";
                    System.out.println("es null");
                }
                else
                {
                    dateRep = jComent.getString("date-rep");
                    replic = jComent.getString("replica");
                    System.out.println("no es null");
                }
                ComentItem siAux = new ComentItem(rating, date, coment, dateRep, replic, storeName);
                System.out.println("add");
                rowItems.add(siAux);
            }
            Collections.sort(rowItems,new Comparator<ComentItem>(){
                public int compare(ComentItem d1,ComentItem d2){
                    //return d1.getdId() - d2.getdId();
                    Long a,b;
                    a = Long.parseLong(d1.getDate());
                    //System.out.println("string: " + d1.getDate() + " long: " + a);
                    b = Long.parseLong(d2.getDate());
                    //System.out.println("string: " + d2.getDate() + " long: " + b);
                    return b.compareTo(a);
                }});

            ComentAdapter adapter = new ComentAdapter(this, rowItems);
            System.out.println("1");
            comentListView.setAdapter(adapter);
            System.out.println("2");
            //setListViewHeightBasedOnChildren(comentListView);
        } catch (JSONException e) {
            e.printStackTrace();
            showToastError("Error al obtener los comentarios");
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
