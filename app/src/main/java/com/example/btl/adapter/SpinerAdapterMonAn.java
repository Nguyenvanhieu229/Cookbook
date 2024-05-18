package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.btl.R;


public class SpinerAdapterMonAn extends BaseAdapter {
    private int[] imgs = {R.drawable.thit_bo, R.drawable.thit_lon, R.drawable.ca, R.drawable.nam, R.drawable.banh, R.drawable.hoaqua};
    private Context context;

    public SpinerAdapterMonAn(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = LayoutInflater.from(context).inflate(R.layout.mon_an_image, parent, false);
        ImageView img = item.findViewById(R.id.anhMonAn);
        img.setImageResource(imgs[position]);
        return img;
    }

}
