package com.example.kechennsheji.SQLite;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;


public class AppUtils {
    public static final String WIDTH ="120dp" ;

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
            return sbar;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
