package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    Context context;
    List<MenuItem> menuItems;

    public MenuAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        TextView dName;
        TextView dPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(StoreMenuActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder();

            holder.dName = convertView.findViewById(R.id.tvMdesc);
            holder.dPrice = convertView.findViewById(R.id.tvMprice);

            MenuItem row_pos = menuItems.get(position);
            if (row_pos.isCategory()){
                holder.dName.setText(row_pos.getDishName());
            } else {
                holder.dName.setText(row_pos.getDishName());
                holder.dPrice.setText("$ " + row_pos.getDishPrice());
            }


        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.dName = convertView.findViewById(R.id.tvMdesc);
            holder.dPrice = convertView.findViewById(R.id.tvMprice);
        }

        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //put your desired action here
                System.out.println("entro: dish");
                Intent intent= new Intent(context, DishActivity.class);
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
