package com.grupo2.hoycomo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DishDetailAdapter extends BaseAdapter {

    Context context;
    List<DishItem> dishItems;

    public DishDetailAdapter(Context context, List<DishItem> dishItems) {
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
        TextView diPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final DishItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShoppingActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.od_dish_item, null);
            holder = new ViewHolder();

            holder.diName = convertView.findViewById(R.id.tvODdish);
            holder.diPrice = convertView.findViewById(R.id.tvODprice);

            row_pos = dishItems.get(position);
            holder.diName.setText(row_pos.getDish_name() + " - " + row_pos.getSum().toString() + " unid.");
            holder.diPrice.setText("$ " + row_pos.getSubTotal().toString());
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.diName = convertView.findViewById(R.id.tvODdish);
            holder.diPrice = convertView.findViewById(R.id.tvODprice);

            row_pos = dishItems.get(position);
            holder.diName.setText(row_pos.getDish_name() + " - " + row_pos.getSum().toString() + " unid.");
            holder.diPrice.setText("$ " + row_pos.getSubTotal().toString());
        }

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
