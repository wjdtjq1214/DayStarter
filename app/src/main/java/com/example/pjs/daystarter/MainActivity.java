package com.example.pjs.daystarter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends AppCompatActivity {

    ImageView loadingIv;
    SQLiteDatabase sqlDB;
    myDBHelper mydb;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIv = (ImageView)findViewById(R.id.loadingIv);

        GlideDrawableImageViewTarget gif = new GlideDrawableImageViewTarget(loadingIv);
        Glide.with(this).load(R.drawable.loading).into(gif);

        mydb = new myDBHelper(this);
        sqlDB = mydb.getReadableDatabase();

        sqlDB.execSQL("create table if not exists data(location varchar(50), x int, y int, busnum varchar(50), stationname varchar(50), busrouteid varchar(50), stationnum varchar(50), seq varchar(50));");
        c = sqlDB.rawQuery("select * from data;", null);

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(c.moveToNext()){
                    sqlDB.close();
                    mydb.close();
                    c.close();
                    startActivity(new Intent(MainActivity.this, daystarter.class));
                    finish();
                }else{
                    sqlDB.close();
                    mydb.close();
                    c.close();
                    startActivity(new Intent(MainActivity.this, setlocation.class));
                    finish();
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 1500);
    }

    public class myDBHelper extends SQLiteOpenHelper{
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
