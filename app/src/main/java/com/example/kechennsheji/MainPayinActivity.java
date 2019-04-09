package com.example.kechennsheji;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.SQLite.Pay;
import com.example.kechennsheji.User.UserActivity;

import java.util.*;

public class MainPayinActivity extends AppCompatActivity{
    private DatabaseHelperPayin helper;
    List<Pay> PayList;

    private Button chakan,fenxibaogao,user,mBtnManagerSort;
    private Button mBtn_confin;
    private String m_sort;
    private EditText mEt_Money,mEt_calander,mEt_Sort,mEt_Introduce;
    private RadioButton Rd_payin,Rd_payout;
    private final static String TAG="insert";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payin);
        helper=new DatabaseHelperPayin(MainPayinActivity.this,"table_payin",null,1);
        final SQLiteDatabase db=helper.getReadableDatabase();
        initView();
        setclicklinster();

        mBtn_confin=findViewById(R.id.confin);
        mBtn_confin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int S_money= Integer.parseInt(mEt_Money.getText().toString());
                String S_calander=mEt_calander.getText().toString();
                String S_sort=mEt_Sort.getText().toString();
                String S_introduce=mEt_Introduce.getText().toString();
                ContentValues contentValues=new ContentValues();
                contentValues.put("money",S_money);
                contentValues.put("sort",S_sort);
                contentValues.put("datetime",S_calander);
                contentValues.put("introduce",S_introduce);
                db.insert("table_payin",null,contentValues);
                db.close();
                Log.d(TAG,"插入收入数据"+S_sort);

            }
        });
        //调出分类
        m_sort=getIntent().getStringExtra("name");
        mEt_Sort.setText(m_sort);


      /*  //调出日历
        mEt_calander.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mEt_calander.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });*/
    }





    public void setclicklinster() {
        onClick onclick = new onClick();
        chakan.setOnClickListener(onclick);
        fenxibaogao.setOnClickListener(onclick);
        user.setOnClickListener(onclick);
        Rd_payin.setOnClickListener(onclick);
        Rd_payout.setOnClickListener(onclick);
        mBtnManagerSort.setOnClickListener(onclick);

    }
    public void initView()
    {
        chakan=findViewById(R.id.btn_chakanzhangdan);
        user=findViewById(R.id.btn_yonghuzhongxin);
        fenxibaogao=findViewById(R.id.btn_fenxibaogao);
        mBtnManagerSort=findViewById(R.id.manager_sort);

        Rd_payin=findViewById(R.id.RB_payin);
        Rd_payout=findViewById(R.id.RB_payout);

        mEt_Money=findViewById(R.id.et_11);
        mEt_calander=findViewById(R.id.et_22);
        mEt_Sort=findViewById(R.id.et_3);
        mEt_Introduce=findViewById(R.id.et_4);

    }


    private class onClick implements View.OnClickListener{
        public void onClick(View view)
        {

            Intent intent=null;
            switch (view.getId())
            {
                case R.id.btn_chakanzhangdan:
                    intent=new Intent(MainPayinActivity.this,BillListActivity.class);
                    break;
                case R.id.btn_fenxibaogao:
                    intent=new Intent(MainPayinActivity.this,AnalysisChartActivity.class);
                    break;
                case R.id.btn_yonghuzhongxin:
                    intent=new Intent(MainPayinActivity.this,UserActivity.class);
                    break;
                case R.id.manager_sort:
                    intent=new Intent(MainPayinActivity.this,MgPayinsortActivity.class);
                    break;
                case R.id.RB_payout:
                    intent=new Intent(MainPayinActivity.this,MainPayoutActivity.class);
                    break;
            }
            startActivity(intent);

        }

    }

    /*protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainPayinActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainPayinActivity.this.mEt_calander.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }*/



}
