package com.example.kechennsheji.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private  final static String TAG="database";

    //Constant constant=new Constant(context);
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Constant.DBPayoutname, null,Constant.version);

        //super(context,constant.getname(), null,Constant.version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"创建数据库");
        String sql="create table table_payout(money integer,sort varchar,datetime varchar,introduce varchar)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


   /*     String sql;
        switch (oldVersion)
        {
            case 1:
                sql="alter table table_pay add address varchar";
                db.execSQL(sql);
                break;
            case 2:
                sql="alter table table_pay add age varchar";
                db.execSQL(sql);
                break;
            case 3:
                sql="alter table table_pay add gender varchar";
                db.execSQL(sql);
                break;
        }
        Log.d(TAG,"升级数据库" );*/

    }
}
