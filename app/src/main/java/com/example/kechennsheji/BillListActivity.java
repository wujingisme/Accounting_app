package com.example.kechennsheji;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.User.UserActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kechennsheji.R.*;

public class BillListActivity extends AppCompatActivity {
    private DatabaseHelperPayin helper;
    private DatabaseHelper helper_out;
    private Button jizhang,chakan,fenxibaogao,user;
    private Button zhichu_shouru;
    private EditText editText1, editText2;
    private ListView ls;
    private Boolean flag=true;
 private List<Map<String,Object>>datalist=new ArrayList<Map<String,Object>>();
 private int[] itemIdArr=new int[]{R.id.tv_shouzhi,id.tv_sort,id.tv_jiner,id.tv_riqi};
 private String[]   dataKeyArr=new String[]{"money","datetime","sort","introduce"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_billlist);
        zhichu_shouru=findViewById(id.zhichuorsouru);
        zhichu_shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                   SQLiteDatabase db=helper.getReadableDatabase();

                    Cursor cursor = db.query("table_payin", null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    zhichu_shouru.setText("支出");
                    flag=false;

                }
                else{
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                   SQLiteDatabase db=helper_out.getReadableDatabase();

                    Cursor cursor = db.query("table_payout", null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        int money = cursor.getInt(0);
                        String datetime = cursor.getString(1);
                        String sort = cursor.getString(2);
                        String introduce= cursor.getString(3);

                        ls=findViewById(R.id.List_1);
                        SimpleAdapter simpleAdapter=new SimpleAdapter(BillListActivity.this,datalist,R.layout.item_list,dataKeyArr,itemIdArr);
                        ls.setAdapter(simpleAdapter);
                        Map<String,Object> map;
                        map=new HashMap<String,Object>();
                        map.put("money",money);
                        map.put("datetime",datetime);
                        map.put("sort",sort);
                        map.put("introduce",introduce);
                        datalist.add(map);
                    }
                    zhichu_shouru.setText("收入");
                    flag=true;
                }

            }
        });





        jizhang=findViewById(id.btn_jizhang);

        jizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BillListActivity.this,MainPayoutActivity.class);
                startActivity(intent);
            }
        });
        chakan=findViewById(id.btn_chakanzhangdan);
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BillListActivity.this,AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
        fenxibaogao=findViewById(id.btn_fenxibaogao);
        fenxibaogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BillListActivity.this,AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
        user=findViewById(id.btn_yonghuzhongxin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BillListActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

        editText1 = findViewById(id.Et_billdate1);
        editText2 = findViewById(id.Et_billdate2);
     /*   editText1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        editText2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg1();
                    return true;
                }
                return false;
            }
        });*/


      /*  editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg1();
                }
            }
        });*/
    }

  //  @SuppressLint("ClickableViewAccessibility")
 /*   protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(BillListActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BillListActivity.this.editText1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    protected void showDatePickDlg1() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(BillListActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BillListActivity.this.editText2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.show();

    }*/
}
