package com.example.pjs.daystarter;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class setbus extends AppCompatActivity {
    ArrayList<bus> busInfo = new ArrayList<bus>();

    Intent intent;
    EditText busNumEt;
    ListView lv = null;
    busListViewAdater lva;
    int x, y;
    String localn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setbus);

        intent = getIntent();
        x = intent.getIntExtra("x", 0);
        y = intent.getIntExtra("y" , 0);
        localn = intent.getStringExtra("location");


        busNumEt = (EditText)findViewById(R.id.busNumEt);
        lv = (ListView)findViewById(R.id.lv);
    }

    public void click(View v){

        busInfo.clear();

        try {

            final String busNum = busNumEt.getText().toString();

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();

            Document xmlDoc = null;
            String url = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList?serviceKey=1cNVucb2rIvcLjUq3zpZrisNea5Nx1A1yqkvMnCh1kPXhwzCg%2BzYz7QqGJGL6Qx5gE25BdKVnyg%2F31a3OxgMeQ%3D%3D&strSrch=" + busNum;
            xmlDoc = parser.parse(url);

            Element root = xmlDoc.getDocumentElement();
            System.out.println(root.getTagName());

            for (int i = 0; i < root.getElementsByTagName("itemList").getLength(); i++) {
                Node xmlNode1 = root.getElementsByTagName("itemList").item(i);

                Node xmlNode21 = ((Element) xmlNode1).getElementsByTagName(
                        "busRouteId").item(0);
                Node xmlNode22 = ((Element) xmlNode1).getElementsByTagName(
                        "busRouteNm").item(0);
                Node xmlNode23 = ((Element) xmlNode1).getElementsByTagName(
                        "edStationNm").item(0);
                Node xmlNode24 = ((Element) xmlNode1).getElementsByTagName(
                        "stStationNm").item(0);

                bus b = new bus();

                b.setBusRouteId(xmlNode21.getTextContent());
                b.setBusRouteNm(xmlNode22.getTextContent());
                b.setEdStataionNm(xmlNode23.getTextContent());
                b.setStStationNm(xmlNode24.getTextContent());

                busInfo.add(b);
            }

            lva = new busListViewAdater(this, R.layout.buslist, busInfo);

            lv.setAdapter(lva);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(setbus.this, setstation.class);
                    i.putExtra("x", x);
                    i.putExtra("y", y);
                    i.putExtra("location", localn);
                    i.putExtra("busRouteId", busInfo.get(position).getBusRouteId());
                    i.putExtra("busnum", busNum);
                    setbus.this.startActivity(i);
                    setbus.this.finish();
                    setbus.this.finish();
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
}
