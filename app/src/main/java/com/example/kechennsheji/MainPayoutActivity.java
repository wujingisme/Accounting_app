package com.example.kechennsheji;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.Pay;
import com.example.kechennsheji.User.UserActivity;

import java.util.*;

public class MainPayoutActivity extends AppCompatActivity{
    private DatabaseHelper helper;
    List<Pay> PayList;

private Button chakan,fenxibaogao,user,mBtnManagerSort;
private Button mBtn_confin;
private String m_sort;
private EditText mEt_Money,mEt_Sort,mEt_Introduce;
private TextView mEt_calander;
private RadioButton Rd_payin,Rd_payout;
private final static String TAG="insert";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new DatabaseHelper(MainPayoutActivity.this,"table_payout",null,1);
        final SQLiteDatabase db=helper.getReadableDatabase();
        initView();
        setclicklinster();

        mBtn_confin=findViewById(R.id.confin);
        mBtn_confin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //int S_money= Integer.parseInt(mEt_Money.getText().toString());
                String S_money=(mEt_Money.getText().toString());
                String S_calander=mEt_calander.getText().toString();
                String S_sort=mEt_Sort.getText().toString();
                String S_introduce=mEt_Introduce.getText().toString();

                if(TextUtils.isEmpty(S_money))
                {
                    Toast.makeText(MainPayoutActivity.this,"请你添加收入金额",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(S_calander))
                {
                    Toast.makeText(MainPayoutActivity.this,"请你添加收入日期",Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(S_sort))
                {
                    Toast.makeText(MainPayoutActivity.this,"请你添加收入类别",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("money",S_money);
                    contentValues.put("sort",S_sort);
                    contentValues.put("datetime",S_calander);
                    contentValues.put("introduce",S_introduce);
                    db.insert("table_payout",null,contentValues);
                    db.close();
                    Log.d(TAG,"插入收入数据"+S_sort);
                    Toast.makeText(MainPayoutActivity.this,"成功添加收入一条数据",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainPayoutActivity.this,MainPayoutActivity.class);
                    startActivity(intent);
                }

            }
        });
        mEt_calander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });
       //调出分类
        m_sort=getIntent().getStringExtra("name");
        mEt_Sort.setText(m_sort);

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
   @SuppressLint("WrongViewCast")
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
                    intent=new Intent(MainPayoutActivity.this,BillListActivity.class);
                    break;
                case R.id.btn_fenxibaogao:
                    intent=new Intent(MainPayoutActivity.this,AnalysisChartActivity.class);
                    break;
                case R.id.btn_yonghuzhongxin:
                    intent=new Intent(MainPayoutActivity.this,UserActivity.class);
                    break;
                case R.id.manager_sort:
                    intent=new Intent(MainPayoutActivity.this,MgPayoutsortActivity.class);
                    break;
                case R.id.RB_payin:
                    intent=new Intent(MainPayoutActivity.this,MainPayinActivity.class);
                    break;
            }
            startActivity(intent);

        }

    }

  protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainPayoutActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainPayoutActivity.this.mEt_calander.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

}
