package com.example.pjs.daystarter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PJS on 2017-12-13.
 */

public class stationListViewAdater extends BaseAdapter {
    Context context ;
    LayoutInflater Inflater;

    ArrayList<station> arrayList = new ArrayList<station>() ;

    TextView stStationNm = null;

    private int layout ;

    public stationListViewAdater(Context context, int layout, ArrayList<station> arrayList) {
        this.context = context ;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.arrayList = arrayList ;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size() ;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayList.get(position).getBusRouteId() ;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int finalPosition = position ;

        if(convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        stStationNm = (TextView)convertView.findViewById(R.id.stationNm);

        stStationNm.setText(arrayList.get(position).getStationName());

        return convertView ;
    }

}
