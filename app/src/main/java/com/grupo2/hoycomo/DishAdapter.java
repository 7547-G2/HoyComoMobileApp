package com.grupo2.hoycomo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DishAdapter extends BaseAdapter {

    Context context;
    List<DishItem> dishItems;

    public DishAdapter(Context context, List<DishItem> dishItems) {
        this.context = context;
        this.dishItems = dishItems;
    }

    @Override
    public int getCount() {
        return dishItems.size();
    }

    @Override
    public Object getItem(int position) {
        return dishItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dishItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        TextView diName;
        TextView diDetail;
        TextView diPrice;
        ImageButton diDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final DishItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShoppingActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dish_item, null);
            holder = new ViewHolder();

            holder.diName = convertView.findViewById(R.id.tvDIname);
            holder.diDetail = convertView.findViewById(R.id.tvDIdet);
            holder.diPrice = convertView.findViewById(R.id.tvDIprice);
            holder.diDelete = convertView.findViewById(R.id.ibDIdelete);

            row_pos = dishItems.get(position);
            holder.diName.setText(row_pos.getDish_name());
            holder.diDetail.setText(row_pos.getSum().toString() + " unidades");
            holder.diPrice.setText("$ " + row_pos.getSubTotal().toString());
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.diName = convertView.findViewById(R.id.tvDIname);
            holder.diDetail = convertView.findViewById(R.id.tvDIdet);
            holder.diPrice = convertView.findViewById(R.id.tvDIprice);
            holder.diDelete = convertView.findViewById(R.id.ibDIdelete);

            row_pos = dishItems.get(position);
            holder.diName.setText(row_pos.getDish_name());
            holder.diDetail.setText(row_pos.getSum().toString() + " unidades");
            holder.diPrice.setText("$ " + row_pos.getSubTotal().toString());
        }

        holder.diDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Eliminar")
                        .setMessage("Desea eliminar el plato del carrito ?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OrderSingleton.getInstance(context).deleteDish(row_pos);
                                ((ShoppingActivity)context).updateData(OrderSingleton.getInstance(context).getTotal(row_pos.getStore_id()));
                                notifyDataSetChanged();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

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
