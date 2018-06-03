package com.grupo2.hoycomo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StatusAdapter extends BaseAdapter {

    Context context;
    List<StatusItem> statusItems;

    public StatusAdapter(Context context, List<StatusItem> statusItems) {
        this.context = context;
        this.statusItems = statusItems;
    }

    @Override
    public int getCount() {
        return statusItems.size();
    }

    @Override
    public Object getItem(int position) {
        return statusItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return statusItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        TextView tvStatus;
        TextView tvDate;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final StatusItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ShoppingActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.od_status_item, null);
            holder = new ViewHolder();

            holder.tvStatus = convertView.findViewById(R.id.tvODstatus);
            holder.tvDate = convertView.findViewById(R.id.tvODfec);

            row_pos = statusItems.get(position);
            holder.tvStatus.setText(row_pos.getStatus());

            holder.tvDate.setText(changeDate(row_pos.getDate()));
        } else {
            holder = (ViewHolder) convertView.getTag();

            holder.tvStatus = convertView.findViewById(R.id.tvODstatus);
            holder.tvDate = convertView.findViewById(R.id.tvODfec);

            row_pos = statusItems.get(position);
            holder.tvStatus.setText(row_pos.getStatus());
            holder.tvDate.setText(changeDate(row_pos.getDate()));
        }

        convertView.setTag(holder);
        return convertView;
    }

    private String changeDate(String date) {
        String horas = date.substring(8,10) + ":" + date.substring(10,12);
        String fecha = date.substring(6,8) + "/" + date.substring(4,6) +
                "/" + date.substring(0,4);
        return horas + " " + fecha;
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
