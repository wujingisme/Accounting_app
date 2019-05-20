package com.example.kechennsheji.SQLite;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Connection;

import static android.content.Context.MODE_PRIVATE;

public class Constant {
    public static final int version = 2;
    public static final String DBPayoutname = "DBpayout.db";
    public static final String DBPayinname = "DBpayin.db";
    //SharedPreferences preference = BaseApplication.getInstance().getSharedPreferences("token",Context.MODE_PRIVATE);
   // SharedPreferences preference = getSharedPreferences("token",Context.MODE_PRIVATE);
    /*SharedPreferences sp;
    public Constant(Context context){
        sp=context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
    }
    public SharedPreferences getPreferences(){
        return sp;
    }
    public String getname(){
        String spUsername = sp.getString("loginUserName", "");
        return spUsername;
    }
*/

    }



