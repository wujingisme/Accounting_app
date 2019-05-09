package com.example.kechennsheji.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperPayin extends SQLiteOpenHelper {
    private  final static String TAG="databasepayin";
    public DatabaseHelperPayin(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Constant.DBPayinname, null,Constant.version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"创建数据库");
        String sql="create table table_payin(money integer,sort varchar,datetime varchar,introduce varchar)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
