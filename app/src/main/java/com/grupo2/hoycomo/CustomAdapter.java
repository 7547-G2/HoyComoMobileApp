package com.grupo2.hoycomo;

import android.content.Context;
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
        ImageView[] iv;


        public ViewHolder() {
            this.iv = new ImageView[5];
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShopListActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.shopName = (TextView) convertView.findViewById(R.id.tvStoreName);
            holder.shopPic = (ImageView) convertView.findViewById(R.id.ivStore);
            holder.shopDesc1 = (TextView) convertView.findViewById(R.id.tvDescLine1);
            holder.shopDesc2 = (TextView) convertView.findViewById(R.id.tvDescLine2);
            holder.favorite = (ImageButton) convertView.findViewById(R.id.ibFavorite);
            holder.iv[0] = (ImageView) convertView.findViewById(R.id.ivS1);
            holder.iv[1] = (ImageView) convertView.findViewById(R.id.ivS2);
            holder.iv[2] = (ImageView) convertView.findViewById(R.id.ivS3);
            holder.iv[3] = (ImageView) convertView.findViewById(R.id.ivS4);
            holder.iv[4] = (ImageView) convertView.findViewById(R.id.ivS5);

            final ShopItem row_pos = shopItems.get(position);

            byte[] decodedString = Base64.decode(row_pos.getShopPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            holder.shopPic.setImageBitmap(decodedByte);
            holder.shopName.setText(row_pos.getShopName());
            holder.shopDesc1.setText(row_pos.getDesc1());
            holder.shopDesc2.setText(row_pos.getDesc2());
            if (row_pos.isFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
            }
            for (int i=0; i < row_pos.getRanking(); i++){
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.shopName = (TextView) convertView.findViewById(R.id.tvStoreName);
            holder.shopPic = (ImageView) convertView.findViewById(R.id.ivStore);
            holder.shopDesc1 = (TextView) convertView.findViewById(R.id.tvDescLine1);
            holder.shopDesc2 = (TextView) convertView.findViewById(R.id.tvDescLine2);
            holder.favorite = (ImageButton) convertView.findViewById(R.id.ibFavorite);

            final ShopItem row_pos = shopItems.get(position);

            byte[] decodedString = Base64.decode(row_pos.getShopPic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            holder.shopPic.setImageBitmap(decodedByte);
            holder.shopName.setText(row_pos.getShopName());
            holder.shopDesc1.setText(row_pos.getDesc1());
            holder.shopDesc2.setText(row_pos.getDesc2());
            if (row_pos.isFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorite_red_36dp);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorite_border_red_36dp);
            }
            for (int i=0; i < row_pos.getRanking(); i++){
                holder.iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
            }
            final ViewHolder finalHolder = holder;
            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: actualizar backend
                    if (row_pos.isFavorite()) {
                        row_pos.setFavorite(false);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_border_black_36dp);
                    } else {
                        row_pos.setFavorite(true);
                        finalHolder.favorite.setImageResource(R.drawable.ic_favorite_black_36dp);
                    }
                }
            });
            convertView.setTag(holder);
        }


        return convertView;
    }

}
