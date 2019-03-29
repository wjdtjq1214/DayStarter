package com.example.pjs.daystarter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class weatherDetail extends AppCompatActivity {

    ArrayList<weather> weatherArrayList = new ArrayList<weather>();
    TextView tvtemp, tvpop, tvtemp1, tvpop1, tvtemp2, tvpop2, tvtemp3, tvpop3 , tvh1, tvh2, tvh3;
    ImageView iv, iv1, iv2, iv3;
    int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        SQLiteDatabase sqlDB;
        myDBHelper mydb = new myDBHelper(this);
        sqlDB = mydb.getReadableDatabase();

        Cursor c = sqlDB.rawQuery("select * from data;", null);
        c.moveToNext();
        x = c.getInt(1);
        y = c.getInt(2);

        c.close();
        sqlDB.close();
        mydb.close();

        tvtemp = (TextView)findViewById(R.id.tvtemp);
        tvpop = (TextView)findViewById(R.id.tvpop);
        iv = (ImageView)findViewById(R.id.wimage);
        tvtemp1 = (TextView)findViewById(R.id.tvtemp1);
        tvpop1 = (TextView)findViewById(R.id.tvpop1);
        iv1 = (ImageView)findViewById(R.id.wimage1);
        tvtemp2 = (TextView)findViewById(R.id.tvtemp2);
        tvpop2 = (TextView)findViewById(R.id.tvpop2);
        iv2 = (ImageView)findViewById(R.id.wimage2);
        tvtemp3 = (TextView)findViewById(R.id.tvtemp3);
        tvpop3 = (TextView)findViewById(R.id.tvpop3);
        iv3 = (ImageView)findViewById(R.id.wimage3);
        tvh1 = (TextView)findViewById(R.id.tvh1);
        tvh2 = (TextView)findViewById(R.id.tvh2);
        tvh3 = (TextView)findViewById(R.id.tvh3);

        getWeather();

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

        String weather1 = weatherArrayList.get(1).getWfEn();
        GlideDrawableImageViewTarget gif1 = new GlideDrawableImageViewTarget(iv1);

        if(weather.equals("Clear")){
            Glide.with(this).load(R.drawable.clear).into(gif1);
        }else if(weather.equals("Rain")){
            Glide.with(this).load(R.drawable.rain).into(gif1);
        }else if(weather.equals("Snow")){
            Glide.with(this).load(R.drawable.snow).into(gif1);
        }else if(weather.equals("Snow/Rain")){
            Glide.with(this).load(R.drawable.snow).into(gif1);
        }else {
            Glide.with(this).load(R.drawable.cloud).into(gif1);
        }

        tvh1.setText(weatherArrayList.get(1).getHour() + "시");
        tvtemp1.setText(weatherArrayList.get(1).getTemp() + "℃");
        tvpop1.setText("강수확률 " + weatherArrayList.get(1).getPop() + "%");

        String weather2 = weatherArrayList.get(2).getWfEn();
        GlideDrawableImageViewTarget gif2 = new GlideDrawableImageViewTarget(iv2);

        if(weather.equals("Clear")){
            Glide.with(this).load(R.drawable.clear).into(gif2);
        }else if(weather.equals("Rain")){
            Glide.with(this).load(R.drawable.rain).into(gif2);
        }else if(weather.equals("Snow")){
            Glide.with(this).load(R.drawable.snow).into(gif2);
        }else if(weather.equals("Snow/Rain")){
            Glide.with(this).load(R.drawable.snow).into(gif2);
        }else {
            Glide.with(this).load(R.drawable.cloud).into(gif2);
        }

        tvh2.setText(weatherArrayList.get(2).getHour() + "시");
        tvtemp2.setText(weatherArrayList.get(2).getTemp() + "℃");
        tvpop2.setText("강수확률 " + weatherArrayList.get(2).getPop() + "%");

        String weather3 = weatherArrayList.get(3).getWfEn();
        GlideDrawableImageViewTarget gif3 = new GlideDrawableImageViewTarget(iv3);

        if(weather.equals("Clear")){
            Glide.with(this).load(R.drawable.clear).into(gif3);
        }else if(weather.equals("Rain")){
            Glide.with(this).load(R.drawable.rain).into(gif3);
        }else if(weather.equals("Snow")){
            Glide.with(this).load(R.drawable.snow).into(gif3);
        }else if(weather.equals("Snow/Rain")){
            Glide.with(this).load(R.drawable.snow).into(gif3);
        }else {
            Glide.with(this).load(R.drawable.cloud).into(gif3);
        }

        tvh3.setText(weatherArrayList.get(3).getHour() + "시");
        tvtemp3.setText(weatherArrayList.get(3).getTemp() + "℃");
        tvpop3.setText("강수확률 " + weatherArrayList.get(3).getPop() + "%");

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent backIntent = new Intent(this, daystarter.class);
            startActivity(backIntent);
            this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
