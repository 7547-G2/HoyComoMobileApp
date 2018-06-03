package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ExtraAdapter extends BaseAdapter {

    Context context;
    List<ExtraItem> extraItems;

    public ExtraAdapter(Context context, List<ExtraItem> extraItems) {
        this.context = context;
        this.extraItems = extraItems;
    }

    @Override
    public int getCount() {
        return extraItems.size();
    }

    @Override
    public Object getItem(int position) {
        return extraItems.get(position);
    }

    public List<ExtraItem> getItemList() {
        return extraItems;
    }

    @Override
    public long getItemId(int position) {
        return extraItems.indexOf(getItem(position));
    }

    private  class ViewHolder {
        RelativeLayout eItemLayout;
        CheckBox eName;
        TextView ePrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ExtraAdapter.ViewHolder holder = null;
        final ExtraItem row_pos;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(ExtraActivity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.extra_item, null);
            holder = new ExtraAdapter.ViewHolder();

            holder.eItemLayout = convertView.findViewById(R.id.rlExtraItem);
            holder.eName = convertView.findViewById(R.id.cbEname);
            holder.ePrice = convertView.findViewById(R.id.tvEprice);

            row_pos = extraItems.get(position);
            holder.eName.setText(row_pos.getExtraName());
            holder.ePrice.setText( "$ " + row_pos.getExtraPrice());

            holder.eName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        ExtraItem a = extraItems.get(position);
                        a.setSelected(true);
                        extraItems.set(position, a);
                    } else {
                        ExtraItem a = extraItems.get(position);
                        a.setSelected(false);
                        extraItems.set(position, a);
                    }

                }
            });


        } else {
            holder = (ExtraAdapter.ViewHolder) convertView.getTag();

            holder.eItemLayout = convertView.findViewById(R.id.rlExtraItem);
            holder.eName = convertView.findViewById(R.id.cbEname);
            holder.ePrice = convertView.findViewById(R.id.tvEprice);

            row_pos = extraItems.get(position);
            holder.eName.setText(row_pos.getExtraName());
            holder.ePrice.setText( "$ " + row_pos.getExtraPrice());
        }

        final ViewHolder finalHolder = holder;
        /*
        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (!finalHolder.eName.isChecked()){
                    System.out.println("selecciono");
                    finalHolder.eName.setChecked(true);
                    ExtraItem a = extraItems.get(position);
                    a.setSelected(true);
                    extraItems.set(position, a);
                } else {
                    System.out.println("remuevo");
                    finalHolder.eName.setChecked(false);
                    ExtraItem a = extraItems.get(position);
                    a.setSelected(false);
                    extraItems.set(position, a);
                }
            }
        };
        */
        //onvertView.setOnClickListener(yourClickListener);
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


