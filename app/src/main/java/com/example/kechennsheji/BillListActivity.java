package com.example.kechennsheji;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.User.UserActivity;

import java.util.ArrayList;
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
    private Boolean bolean=true;
    private TextView expense,income,left;
    private TextView tv_sort,tv_money,tv_date;
    int sum_income,sum_expense,sum_left=0;
    private static  final String TAG="test";
 private List<Map<String,Object>>datalist=new ArrayList<Map<String,Object>>();
 private int[] itemIdArr=new int[]{R.id.tv_shouzhi,id.tv_sort,id.tv_jiner,id.tv_riqi};
 private String[]   dataKeyArr=new String[]{"money","datetime","sort","introduce"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_billlist);
        initview();
        QTotal_expense();
        QTotal_income();
        QTotal_left();
        zhichu_shouru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {       datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, null);
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
                    tv_money_income_Onclick();
                    zhichu_shouru.setText("当前为收入");
                    flag=false;
                    income.setText(":"+String.valueOf(sum_income)+"元");

                }
                else{

                   // Log.d(TAG, String.valueOf(sum));
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                   SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                   cursor = db.query("table_payout", null, null, null, null, null, "money desc");
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
                    tv_money_expense_Onclick();
                    zhichu_shouru.setText("当前为支出");
                    flag=true;

                    expense.setText(":"+String.valueOf(sum_expense)+"元");

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
    public  void   initview() {
        zhichu_shouru=findViewById(id.zhichuorsouru);
        expense=findViewById(R.id.total_expense2);
        income=findViewById(id.total_income2);
        left=findViewById(id.the_left2);
        tv_money=findViewById(R.id._shouzhi);
        tv_sort=findViewById(id._sort);
        tv_date=findViewById(id._jiner);

    }

      public void QTotal_expense()
    {
        helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
        SQLiteDatabase db=helper_out.getReadableDatabase();
        //Cursor cursor=db.rawQuery("select sum(money) from table_payout",null);
        Cursor cursor=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"交通"});
        if (cursor.moveToFirst())
        {
            do
            {
                sum_expense=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();


    }
    public  void QTotal_income()
    {
        helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select sum(money) from table_payin",null);

        if (cursor.moveToFirst())
        {
            do
            {
                sum_income=cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public  void  QTotal_left(){
        sum_left=sum_income-sum_expense;
        left.setText(String.valueOf(sum_left));
    }
    public void tv_money_expense_Onclick()
    {
        tv_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "money desc");
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
                    flag=false;
                    tv_money.setText("金额(desc)");
                }
                else
                {

                    datalist.clear();
                    helper_out=new DatabaseHelper(BillListActivity.this,"table_payout",null,1);
                    SQLiteDatabase db=helper_out.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payout", null, null, null, null,null, "money asc");
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
                    flag=true;
                    tv_money.setText("金额(asc)");
                }
            }
        });

    }

    public void tv_money_income_Onclick()
    {
        tv_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==true)
                {
                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "money desc");
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
                    flag=false;
                    tv_money.setText("金额(desc)");
                }
                else
                {

                    datalist.clear();
                    helper=new DatabaseHelperPayin(BillListActivity.this,"table_payin",null,1);
                    SQLiteDatabase db=helper.getReadableDatabase();
                    Cursor cursor;
                    cursor = db.query("table_payin", null, null, null, null,null, "money asc");
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
                    flag=true;
                    tv_money.setText("金额(asc)");
                }
            }
        });

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
