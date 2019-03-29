package com.example.pjs.daystarter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class setstation extends AppCompatActivity {

    ArrayList<station> stationArrayList = new ArrayList<station>();
    stationListViewAdater lva;
    ListView busstationlv;
    Intent i;
    int x, y;
    String localn, busnum, busRouteId, stationNum, seq, stationName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setstation);

        busstationlv = (ListView)findViewById(R.id.busstationlv);

        i = getIntent();

        stationArrayList.clear();

        try {

            String routeId = i.getStringExtra("busRouteId");
            x = i.getIntExtra("x", 0);
            y = i.getIntExtra("y" , 0);
            localn = i.getStringExtra("location");
            busnum = i.getStringExtra("busnum");

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();

            Document xmlDoc = null;
            String url = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute?serviceKey=1cNVucb2rIvcLjUq3zpZrisNea5Nx1A1yqkvMnCh1kPXhwzCg%2BzYz7QqGJGL6Qx5gE25BdKVnyg%2F31a3OxgMeQ%3D%3D&busRouteId=" + routeId;
            xmlDoc = parser.parse(url);
            Element root = xmlDoc.getDocumentElement();
            System.out.println(root.getTagName());

            for (int i = 0; i < root.getElementsByTagName("itemList").getLength(); i++) {
                Node xmlNode1 = root.getElementsByTagName("itemList").item(i);

                Node xmlNode21 = ((Element) xmlNode1).getElementsByTagName(
                        "busRouteId").item(0);
                Node xmlNode22 = ((Element) xmlNode1).getElementsByTagName(
                        "stationNm").item(0);
                Node xmlNode23 = ((Element) xmlNode1).getElementsByTagName(
                        "station").item(0);
                Node xmlNode24 = ((Element) xmlNode1).getElementsByTagName(
                        "seq").item(0);

                station s = new station();

                s.setBusRouteId(xmlNode21.getTextContent());
                s.setStationName(xmlNode22.getTextContent());
                s.setStationNum(xmlNode23.getTextContent());
                s.setSeq(xmlNode24.getTextContent());

                stationArrayList.add(s);
            }

            lva = new stationListViewAdater(this, R.layout.stationlist, stationArrayList);
            busstationlv.setAdapter(lva);
            busstationlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    busRouteId = stationArrayList.get(position).getBusRouteId();
                    stationNum = stationArrayList.get(position).getStationNum();
                    seq = stationArrayList.get(position).getSeq();
                    stationName = stationArrayList.get(position).getStationName();

                    SQLiteDatabase sqlDB;
                    myDBHelper mydb = new myDBHelper(setstation.this);
                    sqlDB = mydb.getWritableDatabase();

                    sqlDB.execSQL("create table if not exists data(location varchar(50), x int, y int, busnum varchar(50), stationname varchar(50), busrouteid varchar(50), stationnum varchar(50), seq varchar(50));");
                    sqlDB.execSQL("insert into data values ('"+ localn + "', " + x + ", " + y + ", '" + busnum + "', '" + stationName + "', '" + busRouteId + "', '" + stationNum + "', '" + seq + "');");

                    sqlDB.close();
                    mydb.close();

                    Intent intent = new Intent(setstation.this, daystarter.class);
                    setstation.this.startActivity(intent);
                    setstation.this.finish();
                    setstation.this.finish();
                }
            });


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.toString());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent backIntent = new Intent(this, MainActivity.class);
            startActivity(backIntent);
            this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
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
}
