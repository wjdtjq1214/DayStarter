package com.example.pjs.daystarter;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

public class setlocation extends AppCompatActivity {
    EditText et;
    int x = 0;
    int y = 0;

    String localn;

    ArrayList<weather> weatherArrayList = new ArrayList<weather>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlocation);

        et = (EditText)findViewById(R.id.et);

    }
    public void click(View v){

        localn = et.getText().toString();

        Vector<localCode> allLocal = new Vector<localCode>();

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.localdata)));
            String readStr = "";
            String str = null;
            while(((str = br.readLine()) != null)){
                localCode local = new localCode();
                String[] array = str.split("\t");
                local.setLocalCode(array[0]);
                local.setLocalName(array[1]);
                local.setX(Integer.parseInt(array[2]));
                local.setY(Integer.parseInt(array[3]));
                local.setUpLocalCode(array[4]);
                local.setUpLocalName(array[5]);
                allLocal.add(local);
            }
            br.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {
            e.printStackTrace();
        }


        for(localCode l : allLocal){
            if(l.getLocalName().equals(localn)){
                x = l.getX();
                y = l.getY();
                break;
            }
        }

        Intent i = new Intent(this, setbus.class);
        i.putExtra("x", x);
        i.putExtra("y", y);
        i.putExtra("location", localn);
        this.startActivity(i);
        this.finish();
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
