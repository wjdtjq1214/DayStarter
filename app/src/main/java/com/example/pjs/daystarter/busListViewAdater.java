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

public class busListViewAdater extends BaseAdapter {
    Context context ;
    LayoutInflater Inflater;

    ArrayList<bus> arrayList = new ArrayList<bus>() ;

    TextView busRouteNm, stStationNm, edStataionNm = null;

    private int layout ;

    public busListViewAdater(Context context, int layout, ArrayList<bus> arrayList) {
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

        busRouteNm = (TextView)convertView.findViewById(R.id.busRouteNm);
        stStationNm = (TextView)convertView.findViewById(R.id.stStationNm);
        edStataionNm = (TextView)convertView.findViewById(R.id.edStataionNm);

        busRouteNm.setText(arrayList.get(position).getBusRouteNm());
        stStationNm.setText(arrayList.get(position).getStStationNm());
        edStataionNm.setText(arrayList.get(position).getEdStataionNm());

        return convertView ;
    }

}
