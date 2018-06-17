package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ComentAdapter extends BaseAdapter {

    Context context;
    List<ComentItem> comentItems;

    public ComentAdapter(Context context, List<ComentItem> comentItems) {
        this.context = context;
        this.comentItems = comentItems;
    }

    @Override
    public int getCount() {
        return comentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return comentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comentItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        TextView cDate;
        TextView cText;
        ImageView[] iv;

        public ViewHolder() {
            this.iv = new ImageView[5];
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ComentAdapter.ViewHolder holder = null;
        final ComentItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ComentActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.coment_item, null);
            holder = new ComentAdapter.ViewHolder();

            holder.cDate = convertView.findViewById(R.id.tvCdate);
            holder.cText = convertView.findViewById(R.id.tvCfill);
            holder.iv[0] = convertView.findViewById(R.id.ivCS1);
            holder.iv[1] = convertView.findViewById(R.id.ivCS2);
            holder.iv[2] = convertView.findViewById(R.id.ivCS3);
            holder.iv[3] = convertView.findViewById(R.id.ivCS4);
            holder.iv[4] = convertView.findViewById(R.id.ivCS5);

            row_pos = comentItems.get(position);

            for (int i=0; i < row_pos.getRating() ; i++){
                holder.iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
            }

            String sDate = changeDate(row_pos.getDate());
            holder.cDate.setText(" - " + sDate);

            String comentario = "<b>" + row_pos.getUser() + ": <b> \"" + row_pos.getComent() + "\"";
            String replica = row_pos.getReplica();
            if (!replica.isEmpty()) {
                comentario = comentario + System.lineSeparator() + System.lineSeparator();
                String sDateRep = changeDate(row_pos.getDateRep());
                comentario = comentario + "<b> Respondío <b>-" + sDateRep  + System.lineSeparator();
                comentario = comentario + "\"" + replica + "\"";
            }
            holder.cText.setText(Html.fromHtml(comentario));

        } else {
            holder = (ComentAdapter.ViewHolder) convertView.getTag();

            holder.cDate = convertView.findViewById(R.id.tvCdate);
            holder.cText = convertView.findViewById(R.id.tvCfill);
            holder.iv[0] = convertView.findViewById(R.id.ivCS1);
            holder.iv[1] = convertView.findViewById(R.id.ivCS2);
            holder.iv[2] = convertView.findViewById(R.id.ivCS3);
            holder.iv[3] = convertView.findViewById(R.id.ivCS4);
            holder.iv[4] = convertView.findViewById(R.id.ivCS5);

            row_pos = comentItems.get(position);

            for (int i=0; i < row_pos.getRating() ; i++){
                holder.iv[i].setImageResource(R.drawable.ic_star_yellow_20dp);
            }

            String sDate = changeDate(row_pos.getDate());
            holder.cDate.setText(" - " + sDate);

            String comentario = "<b>" + row_pos.getUser() + ": <b> \"" + row_pos.getComent() + "\"";
            String replica = row_pos.getReplica();
            if (!replica.isEmpty()) {
                comentario = comentario + System.lineSeparator() + System.lineSeparator();
                String sDateRep = changeDate(row_pos.getDateRep());
                comentario = comentario + "<b> Respondío <b>-" + sDateRep  + System.lineSeparator();
                comentario = comentario + "\"" + replica + "\"";
            }
            holder.cText.setText(Html.fromHtml(comentario));
        }

        convertView.setTag(holder);
        return convertView;
    }

    private String changeDate(String date) {
        //String horas = date.substring(8,10) + ":" + date.substring(10,12);
        String fecha = date.substring(6,8) + "/" + date.substring(4,6) +
                "/" + date.substring(0,4);
        //return horas + " " + fecha;
        return fecha;
    }

}
