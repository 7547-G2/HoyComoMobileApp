package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    Context context;
    List<OrderItem> orderItems;

    public OrderAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        RelativeLayout oLayout;
        TextView oStore;
        TextView oStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final OrderItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShopListActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_item, null);
            holder = new ViewHolder();

            holder.oLayout = convertView.findViewById(R.id.rlOrderItem);
            holder.oStore = convertView.findViewById(R.id.tvOstore);
            holder.oStatus = convertView.findViewById(R.id.tvOStatus);

            row_pos = orderItems.get(position);
            holder.oStore.setText(row_pos.getStoreName());
            holder.oStatus.setText(row_pos.getStatus());
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.oLayout = convertView.findViewById(R.id.rlOrderItem);
            holder.oStore = convertView.findViewById(R.id.tvOstore);
            holder.oStatus = convertView.findViewById(R.id.tvOStatus);

            row_pos = orderItems.get(position);
            holder.oStore.setText(row_pos.getStoreName());
            holder.oStatus.setText(row_pos.getStatus());
        }

        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("entro: order");
                Intent intent= new Intent(context, OrderDetailActivity.class);
                intent.putExtra("order_id", row_pos.getOrderId());
                context.startActivity(intent);

            }
        };

        convertView.setOnClickListener(yourClickListener);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }
}
