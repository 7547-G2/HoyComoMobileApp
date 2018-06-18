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

import static com.grupo2.hoycomo.ErrorManager.showToastError;

public class CustomAdapter extends BaseAdapter {

    Context context;

    public CustomAdapter(Context context, List<ShopItem> shopItems) {
        this.context = context;
        this.shopItems = shopItems;
    }

    List<ShopItem> shopItems;

    @Override
    public int getCount() {
        return shopItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shopItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shopItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        ImageView shopPic;
        TextView shopName;
        TextView shopDesc1;
        TextView shopDesc2;
        ImageButton favorite;
        TextView tvDesc;
        ImageView[] iv;


        public ViewHolder() {
            this.iv = new ImageView[5];
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Integer id = 0;
        final String name, tipo, leadTime, minPrice, maxPrice;
        final Integer rank, desc;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShopListActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.shopName = convertView.findViewById(R.id.tvStoreName);
            holder.shopPic = convertView.findViewById(R.id.ivStore);
            holder.shopDesc1 = convertView.findViewById(R.id.tvDescLine1);
            holder.shopDesc2 = convertView.findViewById(R.id.tvDescLine2);
            holder.favorite = convertView.findViewById(R.id.ibFavorite);
            holder.tvDesc = convertView.findViewById(R.id.tvListOff);
            holder.tvDesc.setVisibility(View.INVISIBLE);

            holder.iv[0] = convertView.findViewById(R.id.ivS1);
            holder.iv[1] = convertView.findViewById(R.id.ivS2);
            holder.iv[2] = convertView.findViewById(R.id.ivS3);
            holder.iv[3] = convertView.findViewById(R.id.ivS4);
            holder.iv[4] = convertView.findViewById(R.id.ivS5);

            final ShopItem row_pos = shopItems.get(position);
            id = row_pos.getId();
            byte[] decodedString = Base64.decode(row_pos.getShopPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            tipo = row_pos.getTipoName();
            leadTime = row_pos.getLeadTime();
            minPrice = row_pos.getMinPrice();
            maxPrice = row_pos.getMaxPrice();
            name = row_pos.getShopName();
            rank = row_pos.getRanking();
            desc = row_pos.getDesc();

            if (desc > 0) {
                holder.tvDesc.setVisibility(View.VISIBLE);
                holder.tvDesc.setText("¡" + desc + " % desc!");
            }

            holder.shopPic.setImageBitmap(decodedByte);
            holder.shopName.setText(name);
            holder.shopDesc1.setText(tipo + " - ");
            holder.shopDesc2.setText(leadTime + " min - Entre $" + minPrice + " y $" + maxPrice);
            if (row_pos.isFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
            }
            for (int i = 0; i < 5; i++){
                //System.out.println("----------Ranks: "+ i);
                holder.iv[i].setImageResource(R.drawable.ic_star_border_yellow_20dp);
            }
            for (int i = 0; i < rank ; i++){
                //System.out.println("----------Ranks: "+ i);
                holder.iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
            }

            final ViewHolder finalHolder = holder;
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: actualizar backend
                    if (row_pos.isFavorite()) {
                        row_pos.setFavorite(false);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
                    } else {
                        row_pos.setFavorite(true);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
                    }
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.shopName = convertView.findViewById(R.id.tvStoreName);
            holder.shopPic = convertView.findViewById(R.id.ivStore);
            holder.shopDesc1 = convertView.findViewById(R.id.tvDescLine1);
            holder.shopDesc2 = convertView.findViewById(R.id.tvDescLine2);
            holder.favorite = convertView.findViewById(R.id.ibFavorite);
            holder.tvDesc = convertView.findViewById(R.id.tvListOff);
            holder.tvDesc.setVisibility(View.INVISIBLE);

            holder.iv[0] = convertView.findViewById(R.id.ivS1);
            holder.iv[1] = convertView.findViewById(R.id.ivS2);
            holder.iv[2] = convertView.findViewById(R.id.ivS3);
            holder.iv[3] = convertView.findViewById(R.id.ivS4);
            holder.iv[4] = convertView.findViewById(R.id.ivS5);

            final ShopItem row_pos = shopItems.get(position);
            id = row_pos.getId();
            byte[] decodedString = Base64.decode(row_pos.getShopPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            tipo = row_pos.getTipoName();
            leadTime = row_pos.getLeadTime();
            minPrice = row_pos.getMinPrice();
            maxPrice = row_pos.getMaxPrice();
            name = row_pos.getShopName();
            rank = row_pos.getRanking();
            desc = row_pos.getDesc();

            if (desc > 0) {
                holder.tvDesc.setVisibility(View.VISIBLE);
                holder.tvDesc.setText("¡" + desc + " % desc!");
            }

            holder.shopPic.setImageBitmap(decodedByte);
            holder.shopName.setText(name);
            holder.shopDesc1.setText(tipo + " - ");
            holder.shopDesc2.setText(leadTime + " min - Entre $" + minPrice + " y $" + maxPrice);
            if (row_pos.isFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
            }
            for (int i = 0; i < 5; i++){
                //System.out.println("----------Ranks: "+ i);
                holder.iv[i].setImageResource(R.drawable.ic_star_border_yellow_20dp);
            }
            for (int i = 0; i < rank; i++){
                //System.out.println("----------Ranks: "+ i);
                holder.iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
            }
            final ViewHolder finalHolder = holder;
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: actualizar backend
                    if (row_pos.isFavorite()) {
                        row_pos.setFavorite(false);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
                    } else {
                        row_pos.setFavorite(true);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
                    }
                }
            });
        }
        final Integer finalId = id;
        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //put your desired action here
                System.out.println("entro: " + finalId.toString());
                Intent intent= new Intent(context, StoreMenuActivity.class);
                intent.putExtra("store_id",finalId.toString());
                intent.putExtra("name", name);
                intent.putExtra("leadTime", leadTime);
                intent.putExtra("minPrice", minPrice);
                intent.putExtra("maxPrice", maxPrice);
                intent.putExtra("rank", rank);
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
