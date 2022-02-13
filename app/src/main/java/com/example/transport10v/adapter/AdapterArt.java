package com.example.transport10v.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.transport10v.R;
import com.example.transport10v.model.ModelArt;

import java.util.List;

public class AdapterArt extends BaseAdapter {

    private Context context;
    private final List<ModelArt> modelArtList;

    public AdapterArt(Context context, List<ModelArt> modelArtList) {
        this.context = context;
        this.modelArtList = modelArtList;
    }

    @Override
    public int getCount() {
        return modelArtList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArtList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return modelArtList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.artlist,null);
        TextView Nr_art = (TextView) view.findViewById(R.id.Nr_Art);
        TextView Nazwa_art = (TextView) view.findViewById(R.id.Nazwa_Art);
        TextView Waga_art = (TextView) view.findViewById(R.id.Waga_Art);

        Nr_art.setText(String.valueOf(modelArtList.get(position).getNr_art()));
        Nazwa_art.setText(modelArtList.get(position).getNazwa_art());
        Waga_art.setText(String.valueOf(modelArtList.get(position).getWaga_Art()));

        return view;
    }
}
