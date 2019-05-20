package com.example.kechennsheji;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.User.UserActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AnalysisIncomeActivity extends AppCompatActivity {
    private DatabaseHelperPayin helperPayin;
    private Button jizhang,chakan,user,fenxibaogao;
    private Button bt_shouru,bt_huafei,btn_search;
    private TextView date1, date2;
    private TextView tv_gongzhi,tv_zhuanzhuang,tv_jiangjin,tv_touzhi,tv_qita,tv_income;
    private PieChart mChart;
    private final String TAG="Piechart";
    private String[] x = new String[] { "兼职", "转账", "奖金","投资","其他" };
    private float m_gongzhi,m_zhuanzhuang,m_jiangjin,m_touzhi,m_qita,total;
    private float m_gongzhi2,m_zhuanzhuang2,m_jiangjin2,m_touzhi2,m_qita2;

    // 凑成100 % 100
    private float[] y ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_income);
        mChart = (PieChart) findViewById(R.id.pie_chart);
        // 图的描述
        mChart.setCenterText("饼状图");
        // 以7个对应数据集做测试
        QTotal_income();
        initview2();
        total=m_gongzhi+m_zhuanzhuang+m_jiangjin+m_touzhi+m_qita;
        m_gongzhi=((float)m_gongzhi/total)*100;
        m_zhuanzhuang=((float)m_zhuanzhuang/total)*100;
        m_jiangjin=((float)m_jiangjin/total)*100;
        m_touzhi=((float)m_touzhi/total)*100;
        m_qita=((float)m_qita/total)*100;
        y = new float[]{m_gongzhi,m_zhuanzhuang,m_jiangjin,m_touzhi,m_qita};
        setData(x.length);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg1();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start_date=date1.getText().toString();
                String end_date=date2.getText().toString();
                Log.d(TAG,start_date);
                helperPayin=new DatabaseHelperPayin(AnalysisIncomeActivity.this,"table_payin",null,1);
                SQLiteDatabase db=helperPayin.getReadableDatabase();
                Cursor cursor_gongzhi=db.rawQuery("select sum(money) from table_payin where sort=?" +
                        " and datetime between ? and ?",new String []{"兼职",start_date,end_date});
                if (cursor_gongzhi.moveToFirst())
                { do { m_gongzhi=cursor_gongzhi.getInt(0); }
                while (cursor_gongzhi.moveToNext()); }
                m_gongzhi2=m_gongzhi;
                cursor_gongzhi.close();
                // Log.d(TAG,String.valueOf(m_gongzhi));
                Cursor cursor_zhuanzhuang=db.rawQuery("select sum(money) from table_payin where sort=?" +
                        " and datetime between ? and ?",new String []{"转账",start_date,end_date});
                if (cursor_zhuanzhuang.moveToFirst())
                { do
                { m_zhuanzhuang=cursor_zhuanzhuang.getInt(0); }
                while (cursor_zhuanzhuang.moveToNext());
                }m_zhuanzhuang2=m_zhuanzhuang;
                cursor_zhuanzhuang.close();
                Log.d(TAG,String.valueOf(m_zhuanzhuang));
                Cursor cursor_jiangjin=db.rawQuery("select sum(money) from table_payin where sort=?" +
                        " and datetime between ? and ?",new String []{"奖金",start_date,end_date});

                if(cursor_jiangjin.moveToFirst())
                {
                    do{
                        m_jiangjin=cursor_jiangjin.getInt(0);
                    }while (cursor_jiangjin.moveToNext());
                }m_jiangjin2=m_jiangjin;
                cursor_jiangjin.close();
                Log.d(TAG,String.valueOf(m_jiangjin));

                Cursor cursor_touzhi=db.rawQuery("select sum(money) from table_payin where sort=?" +
                        " and datetime between ? and ?",new String []{"投资",start_date,end_date});
                if(cursor_touzhi.moveToFirst())
                {
                    do {
                        m_touzhi=cursor_touzhi.getInt(0);

                    }while (cursor_touzhi.moveToNext()) ;
                }m_touzhi2=m_touzhi;
                cursor_touzhi.close();
                Log.d(TAG,String.valueOf(m_touzhi));
                Cursor cursor_qita=db.rawQuery("select sum(money) from table_payin where sort=?" +
                        " and datetime between ? and ?",new String []{"其他",start_date,end_date});
                if(cursor_qita.moveToFirst())
                {
                    do {
                        m_qita=cursor_qita.getInt(0);

                    }while (cursor_qita.moveToNext()) ;
                }m_qita2=m_qita;
                cursor_qita.close();
                initview2();
                total=m_gongzhi+m_zhuanzhuang+m_jiangjin+m_touzhi+m_qita;
                m_gongzhi=((float)m_gongzhi/total)*100;
                m_zhuanzhuang=((float)m_zhuanzhuang/total)*100;
                m_jiangjin=((float)m_jiangjin/total)*100;
                m_touzhi=((float)m_touzhi/total)*100;
                m_qita=((float)m_qita/total)*100;

                y = new float[]{m_gongzhi,m_zhuanzhuang,m_jiangjin,m_touzhi,m_qita};
                setData(x.length);

            }
        });

    }

    //DecimalFormat df=new DecimalFormat("0.00");
    public void QTotal_income()
    {
        helperPayin=new DatabaseHelperPayin(AnalysisIncomeActivity.this,"table_payin",null,1);
        SQLiteDatabase db=helperPayin.getReadableDatabase();

        Cursor cursor_gongzhi=db.rawQuery("select sum(money) from table_payin where sort=?",new String []{"兼职"});
        if (cursor_gongzhi.moveToFirst())
        { do
        { m_gongzhi=cursor_gongzhi.getInt(0); }
        while (cursor_gongzhi.moveToNext());
        }
        m_gongzhi2=m_gongzhi;
        cursor_gongzhi.close();
        Log.d(TAG,String.valueOf(m_gongzhi));
        Cursor cursor_zhuanzhuang=db.rawQuery("select sum(money) from table_payin where sort=?",new String []{"转账"});
        if (cursor_zhuanzhuang.moveToFirst())
        { do
        {
            m_zhuanzhuang=cursor_zhuanzhuang.getInt(0); }
        while (cursor_zhuanzhuang.moveToNext());
        }m_zhuanzhuang2=m_zhuanzhuang;
        cursor_zhuanzhuang.close();
        Log.d(TAG,String.valueOf(m_zhuanzhuang));
        Cursor cursor_jiangjin=db.rawQuery("select sum(money) from table_payin where sort=?",new String []{"奖金"});

        if(cursor_jiangjin.moveToFirst())
        {
            do{
                m_jiangjin=cursor_jiangjin.getInt(0);
            }while (cursor_jiangjin.moveToNext());
        }m_jiangjin2=m_jiangjin;
        cursor_jiangjin.close();
        //  Log.d(TAG,String.valueOf(m_jiangjin));

        Cursor cursor_touzhi=db.rawQuery("select sum(money) from table_payin where sort=?",new String []{"投资"});
        if(cursor_touzhi.moveToFirst())
        {
            do {
                m_touzhi=cursor_touzhi.getInt(0);

            }while (cursor_touzhi.moveToNext()) ;
        }m_touzhi2=m_touzhi;
        cursor_touzhi.close();
        // Log.d(TAG,String.valueOf(m_touzhi));
        Cursor cursor_qita=db.rawQuery("select sum(money) from table_payin where sort=?",new String []{"其他"});
        if(cursor_qita.moveToFirst())
        {
            do {
                m_qita=cursor_qita.getInt(0);

            }while (cursor_qita.moveToNext()) ;
        }m_qita2=m_qita;
        cursor_qita.close();
        // Log.d(TAG,String.valueOf(m_qita)+"  qita");

    }

    public void initview2()
    {
        btn_search=findViewById(R.id.btn_serach);
        tv_income=findViewById(R.id.total_income2);
        tv_gongzhi=findViewById(R.id.total_gongzhi2);
        tv_jiangjin=findViewById(R.id.total_jiangjin2);
        tv_touzhi=findViewById(R.id.total_touzhi2);
        tv_zhuanzhuang=findViewById(R.id.total_zhuanzhuang2);
        tv_qita=findViewById(R.id.total_qita2);
        date1 = findViewById(R.id.Et_date1);
        date2 = findViewById(R.id.Et_date2);

        tv_income.setText(String.valueOf(m_gongzhi+m_zhuanzhuang+m_jiangjin+m_touzhi+m_qita)+" 元");
        tv_gongzhi.setText(String.valueOf(m_gongzhi2)+" 元");
        tv_zhuanzhuang.setText(String.valueOf((m_zhuanzhuang2))+" 元");
        tv_jiangjin.setText(String.valueOf(m_jiangjin2)+" 元");
        tv_touzhi.setText((String.valueOf(m_touzhi2))+" 元");
        tv_qita.setText(String .valueOf(m_qita2)+" 元");
    }

    private void setData(int count) {

        // 准备x"轴"数据：在i的位置，显示x[i]字符串
        ArrayList<String> xVals = new ArrayList<String>();
        // 真实的饼状图百分比分区。
        // Entry包含两个重要数据内容：position和该position的数值。
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int xi = 0; xi < count; xi++) {
            xVals.add(xi, x[xi]);
            // y[i]代表在x轴的i位置真实的百分比占
            yVals.add(new Entry(y[xi], xi));
        }
        PieDataSet yDataSet = new PieDataSet(yVals, "百分比占");
        // 每个百分比占区块绘制的不同颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        yDataSet.setColors(colors);
        // 将x轴和y轴设置给PieData作为数据源
        PieData data = new PieData(xVals, yDataSet);
        // 设置成PercentFormatter将追加%号
        data.setValueFormatter(new PercentFormatter());
        // 文字的颜色
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(10f);
        // 最终将全部完整的数据喂给PieChart
        mChart.setData(data);
        mChart.invalidate();

        jizhang = findViewById(R.id.btn_jizhang);
        jizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisIncomeActivity.this, MainPayoutActivity.class);
                startActivity(intent);
            }
        });
        chakan = findViewById(R.id.btn_chakanzhangdan);
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisIncomeActivity.this, BillListActivity.class);
                startActivity(intent);
            }
        });
        fenxibaogao = findViewById(R.id.btn_fenxibaogao);
        fenxibaogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisIncomeActivity.this, AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
        user = findViewById(R.id.btn_yonghuzhongxin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalysisIncomeActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
        bt_huafei=findViewById(R.id.bt_huafei);
        bt_huafei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AnalysisIncomeActivity.this,AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AnalysisIncomeActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisIncomeActivity.this.date1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    protected void showDatePickDlg1() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(AnalysisIncomeActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisIncomeActivity.this.date2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.show();


    }
}