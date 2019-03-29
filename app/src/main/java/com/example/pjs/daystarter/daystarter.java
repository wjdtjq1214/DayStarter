package com.example.pjs.daystarter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class daystarter extends AppCompatActivity {

    ArrayList<weather> weatherArrayList = new ArrayList<weather>();
    TextView tvtemp, tvpop, tvbusnum, tvstation, tvarrmsg1, tvarrmsg2;
    ImageView iv;
    int x, y;
    String localn, busnum, busRouteId, stationNum, seq, stationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daystarter);

        tvtemp = (TextView)findViewById(R.id.tvtemp);
        tvpop = (TextView)findViewById(R.id.tvpop);
        iv = (ImageView)findViewById(R.id.wimage);
        tvbusnum = (TextView)findViewById(R.id.tvbusnum);
        tvstation = (TextView)findViewById(R.id.tvstation);
        tvarrmsg1 = (TextView)findViewById(R.id.tvarrmsg1);
        tvarrmsg2 = (TextView)findViewById(R.id.tvarrmsg2);

        SQLiteDatabase sqlDB;
        myDBHelper mydb = new myDBHelper(this);
        sqlDB = mydb.getReadableDatabase();

        Cursor c = sqlDB.rawQuery("select * from data;", null);
        c.moveToNext();
        localn = c.getString(0);
        x = c.getInt(1);
        y = c.getInt(2);
        busnum = c.getString(3);
        stationName = c.getString(4);
        busRouteId = c.getString(5);
        stationNum = c.getString(6);
        seq = c.getString(7);

        getWeather();
        getBusInfo();
        c.close();
    }

    public void getBusInfo(){
        try {

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();

            Document xmlDoc = null;
            String url = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute?serviceKey=1cNVucb2rIvcLjUq3zpZrisNea5Nx1A1yqkvMnCh1kPXhwzCg%2BzYz7QqGJGL6Qx5gE25BdKVnyg%2F31a3OxgMeQ%3D%3D&stId=" + stationNum + "&busRouteId=" + busRouteId + "&ord=" + seq;
            xmlDoc = parser.parse(url);
            Element root = xmlDoc.getDocumentElement();
            System.out.println(root.getTagName());

            Node xmlNode1 = root.getElementsByTagName("itemList").item(0);

            Node xmlNode21 = ((Element) xmlNode1).getElementsByTagName(
                    "arrmsg1").item(0);
            Node xmlNode22 = ((Element) xmlNode1).getElementsByTagName(
                    "arrmsg2").item(0);

            tvbusnum.setText(busnum);
            tvstation.setText(stationName);
            tvarrmsg1.setText(xmlNode21.getTextContent());
            tvarrmsg2.setText(xmlNode22.getTextContent());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
    }

    public void getWeather(){
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();

            Document xmlDoc = null;
            String url = "http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + x + "&gridy=" + y;
            xmlDoc = parser.parse(url);

            Element root = xmlDoc.getDocumentElement();
            System.out.println(root.getTagName());

            for (int i = 0; i < root.getElementsByTagName("data").getLength(); i++) {
                Node xmlNode1 = root.getElementsByTagName("data").item(i);

                Node xmlNode21 = ((Element) xmlNode1).getElementsByTagName(
                        "hour").item(0);
                Node xmlNode22 = ((Element) xmlNode1).getElementsByTagName(
                        "temp").item(0);
                Node xmlNode23 = ((Element) xmlNode1).getElementsByTagName(
                        "wfEn").item(0);
                Node xmlNode24 = ((Element) xmlNode1).getElementsByTagName(
                        "wfKor").item(0);
                Node xmlNode25 = ((Element) xmlNode1).getElementsByTagName(
                        "pop").item(0);

                weather w = new weather();
                w.setHour(xmlNode21.getTextContent());
                w.setTemp(xmlNode22.getTextContent());
                w.setWfEn(xmlNode23.getTextContent());
                w.setWfKor(xmlNode24.getTextContent());
                w.setPop(xmlNode25.getTextContent());

                weatherArrayList.add(w);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }

        String weather = weatherArrayList.get(0).getWfEn();
        GlideDrawableImageViewTarget gif = new GlideDrawableImageViewTarget(iv);

        if(weather.equals("Clear")){
            Glide.with(this).load(R.drawable.clear).into(gif);
        }else if(weather.equals("Rain")){
            Glide.with(this).load(R.drawable.rain).into(gif);
        }else if(weather.equals("Snow")){
            Glide.with(this).load(R.drawable.snow).into(gif);
        }else if(weather.equals("Snow/Rain")){
            Glide.with(this).load(R.drawable.snow).into(gif);
        }else {
            Glide.with(this).load(R.drawable.cloud).into(gif);
        }

        tvtemp.setText(weatherArrayList.get(0).getTemp() + "℃");
        tvpop.setText("강수확률 " + weatherArrayList.get(0).getPop() + "%");

        //for (int i = 0; i < weatherArrayList.size(); i++) {
            //tv.append("\n" + weatherArrayList.get(i).getHour() + "\n" + weatherArrayList.get(i).getWfEn() + "\n" + weatherArrayList.get(i).getTemp() + "\n" + weatherArrayList.get(i).getPop() + "\n");
        //}
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists data(location varchar(50), x int, y int, busnum varchar(50), stationname varchar(50), busrouteid varchar(50), stationnum varchar(50), seq varchar(50));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void weatherdetail(View v){
        Intent i = new Intent(daystarter.this, weatherDetail.class);
        daystarter.this.startActivity(i);
        daystarter.this.finish();
    }

    public void newset(View v){
        SQLiteDatabase sqlDB;
        myDBHelper mydb = new myDBHelper(this);
        sqlDB = mydb.getReadableDatabase();

        sqlDB.execSQL("drop table data;");

        sqlDB.close();
        mydb.close();

        Intent i = new Intent(daystarter.this, setlocation.class);
        daystarter.this.startActivity(i);
        daystarter.this.finish();

    }
}

