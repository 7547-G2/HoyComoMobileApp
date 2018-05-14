package com.grupo2.hoycomo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    ListView dishListView;
    List<DishItem> rowItems;
    Integer sId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
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

        dishListView = findViewById(R.id.lvOrders);
        Intent intent = getIntent();
        sId = intent.getIntExtra("store_id", 99);
        String name = intent.getStringExtra("store_name");
        String leadTime = intent.getStringExtra("store_ld");
        TextView title = findViewById(R.id.tvSname);
        title.setText(name +" (demora " + leadTime + ")");
        System.out.println("store id: " + sId);
        if (OrderSingleton.getInstance(getApplicationContext()).hasOrder(sId)){
            System.out.println("muestra platos");
            Order o = OrderSingleton.getInstance(getApplicationContext()).getOrder(sId);
            rowItems  = o.getDishItemList();
            System.out.println("rowItems " + rowItems.size());
            DishAdapter adapter = new DishAdapter(this, rowItems);
            dishListView.setAdapter(adapter);
        } else {
            System.out.println("no hay platos");
            TextView alert = findViewById(R.id.tvEmpty);
            alert.setVisibility(View.VISIBLE);
            Button conf = findViewById(R.id.btConf);
            conf.setEnabled(false);
            Button empt = findViewById(R.id.btVaciar);
            empt.setEnabled(false);
        }
        setListViewHeightBasedOnChildren(dishListView);
        updateData(OrderSingleton.getInstance(getApplicationContext()).getTotal(sId));
    }

    public void updateData(Integer data){
        TextView tvTotal = findViewById(R.id.tvStotal);
        tvTotal.setText("Total      $ " + data);
    }

    public void siguiente(View view){
        Intent intent= new Intent(getApplicationContext(), PayActivity.class);
        startActivity(intent);
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
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
