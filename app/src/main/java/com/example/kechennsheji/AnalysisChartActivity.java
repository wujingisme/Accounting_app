
/*
Cursor cursor_jiaotong=db.rawQuery("select sum(money) from table_payout where sort=?" +
        " and datetime between ? and ?",new String []{"交通",start_date,end_date});
*/

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
//import package com.example.kechennsheji.User;

import com.example.kechennsheji.SQLite.DatabaseHelper;
import com.example.kechennsheji.SQLite.DatabaseHelperPayin;
import com.example.kechennsheji.User.UserActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnalysisChartActivity extends AppCompatActivity {
    private DatabaseHelper helper_out;
    private DatabaseHelperPayin helperPayin;
    private Button jizhang,chakan,user,fenxibaogao;
    private Button bt_shouru,bt_huafei,btn_search;

    private TextView date1, date2;
    private TextView tv_expense,tv_jiaotong,tv_zhufang,tv_yilioa,tv_yule,tv_shenghuoyongp,tv_jiaoyu,tv_qita;
    private PieChart mChart;
    private final String TAG="Piechart";
    private String[] x = new String[] { "交通", "吃饭", "医疗","娱乐","教育","生活用品","其他" };
    private float m_jiaotong,m_zhufang,m_yiliao,m_yule,m_jiaoyu,m_shenghuoyongping,m_qita,total;
    private float m_jiaotong2,m_zhufang2,m_yiliao2,m_yule2,m_jiaoyu2,m_shenghuoyongping2,m_qita2;

    // 凑成100 % 100
    private float[] y ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysizechart);
        mChart = (PieChart) findViewById(R.id.pie_chart);
        // 图的描述
        mChart.setCenterText("饼状图");
        // 以7个对应数据集做测试
        QTotal_expense();
        initview();
        setclicklinster();
        total=m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita;
        m_jiaotong=((float)m_jiaotong/total)*100;
        m_zhufang=((float)m_zhufang/total)*100;
        m_yiliao=((float)m_yiliao/total)*100;
        m_yule=((float)m_yule/total)*100;
        m_jiaoyu=((float)m_jiaoyu/total)*100;
        m_shenghuoyongping=((float)m_shenghuoyongping/total)*100;
        m_qita=((float)m_qita/total)*100;

        y = new float[]{m_jiaotong,m_zhufang,m_yiliao,m_yule,m_jiaoyu,m_shenghuoyongping,m_qita};
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
                helper_out=new DatabaseHelper(AnalysisChartActivity.this,"table_payout",null,1);
                SQLiteDatabase db=helper_out.getReadableDatabase();
                Cursor cursor_jiaotong=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"交通",start_date,end_date});
                Cursor cursor_zhufang=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"吃饭",start_date,end_date});
                Cursor cursor_yiliao=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"医疗",start_date,end_date});
                Cursor cursor_yule=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"娱乐",start_date,end_date});
                Cursor cursor_jiaoyu=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"教育",start_date,end_date});
                Cursor cursor_shenghuoyongp=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"生活用品",start_date,end_date});
                Cursor cursor_qita=db.rawQuery("select sum(money) from table_payout where sort=?" +
                        " and datetime between ? and ?",new String []{"其他",start_date,end_date});
                if (cursor_jiaotong.moveToFirst())
                { do { m_jiaotong=cursor_jiaotong.getInt(0);
                } while (cursor_jiaotong.moveToNext());
                } m_jiaotong2=m_jiaotong;
                cursor_jiaotong.close();
                if (cursor_zhufang.moveToFirst())
                { do { m_zhufang=cursor_zhufang.getInt(0); }
                    while (cursor_zhufang.moveToNext());
                }m_zhufang2=m_zhufang;
                cursor_zhufang.close();
                if(cursor_yiliao.moveToFirst())
                { do{ m_yiliao=cursor_yiliao.getInt(0);
                    }while (cursor_yiliao.moveToNext());
                }m_yiliao2=m_yiliao;
                cursor_yiliao.close();
                if(cursor_yule.moveToFirst())
                { do { m_yule=cursor_yule.getInt(0);
                    }while (cursor_yule.moveToNext()) ;
                }m_yule2=m_yule;
                cursor_yule.close();
                if(cursor_jiaoyu.moveToFirst())
                { do { m_jiaoyu=cursor_jiaoyu.getInt(0);
                    }while (cursor_jiaoyu.moveToNext()) ;
                }m_jiaoyu2=m_jiaoyu;
                cursor_jiaoyu.close();
                if(cursor_shenghuoyongp.moveToFirst())
                { do{ m_shenghuoyongping=cursor_shenghuoyongp.getInt(0);
                    }while (cursor_shenghuoyongp.moveToNext());
                }m_shenghuoyongping2=m_shenghuoyongping;
                cursor_shenghuoyongp.close();
                if(cursor_qita.moveToFirst())
                { do { m_qita=cursor_qita.getInt(0);
                    }while (cursor_qita.moveToNext()) ;
                }m_qita2=m_qita;
                cursor_qita.close();

                initview();
                setclicklinster();
                total=m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita;
                m_jiaotong=((float)m_jiaotong/total)*100;
                m_zhufang=((float)m_zhufang/total)*100;
                m_yiliao=((float)m_yiliao/total)*100;
                m_yule=((float)m_yule/total)*100;
                m_jiaoyu=((float)m_jiaoyu/total)*100;
                m_shenghuoyongping=((float)m_shenghuoyongping/total)*100;
                m_qita=((float)m_qita/total)*100;
                y = new float[]{m_jiaotong,m_zhufang,m_yiliao,m_yule,m_jiaoyu,m_shenghuoyongping,m_qita};
                setData(x.length);
            }
        });

    }
    //    DecimalFormat df=new DecimalFormat("0.00");
    public void QTotal_expense()
    {
        helper_out=new DatabaseHelper(AnalysisChartActivity.this,"table_payout",null,1);
        SQLiteDatabase db=helper_out.getReadableDatabase();
        // Cursor cursor_jiaotong=db.rawQuery("select sum(money) from table_payout where sort=?",new String []{"交通"});
        Cursor cursor_jiaotong= db.rawQuery("select sum(money) from " +
                "table_payout where sort=?",new String []{"交通"});
        Cursor cursor_zhufang=db.rawQuery("select sum(money) from " +
                "table_payout where sort=?",new String []{"吃饭"});
        Cursor cursor_yiliao=db.rawQuery("select sum(money) from " +
                "table_payout where sort=?",new String []{"医疗"});
        Cursor cursor_yule=db.rawQuery("select sum(money) from" +
                " table_payout where sort=?",new String []{"娱乐"});
        Cursor cursor_jiaoyu=db.rawQuery("select sum(money) from" +
                " table_payout where sort=?",new String []{"教育"});
        Cursor cursor_shenghuoyongp=db.rawQuery("select sum(money) from " +
                "table_payout where sort=?",new String []{"生活用品"});
        Cursor cursor_qita=db.rawQuery("select sum(money) from" +
                " table_payout where sort=?",new String []{"其他"});

        if (cursor_jiaotong.moveToFirst())
        { do { m_jiaotong=cursor_jiaotong.getInt(0); }
        while (cursor_jiaotong.moveToNext());
        } m_jiaotong2=m_jiaotong;
        cursor_jiaotong.close();
        if (cursor_zhufang.moveToFirst())
        { do { m_zhufang=cursor_zhufang.getInt(0); }
        while (cursor_zhufang.moveToNext());
        }m_zhufang2=m_zhufang;
        cursor_zhufang.close();
        if(cursor_yiliao.moveToFirst())
        { do{ m_yiliao=cursor_yiliao.getInt(0);
        }while (cursor_yiliao.moveToNext());
        }m_yiliao2=m_yiliao;
        cursor_yiliao.close();
        if(cursor_yule.moveToFirst())
        { do { m_yule=cursor_yule.getInt(0);
            }while (cursor_yule.moveToNext()) ;
        }m_yule2=m_yule;
        cursor_yule.close();
        if(cursor_jiaoyu.moveToFirst())
        {
            do {
                m_jiaoyu=cursor_jiaoyu.getInt(0);

            }while (cursor_jiaoyu.moveToNext()) ;
        }m_jiaoyu2=m_jiaoyu;
        cursor_jiaoyu.close();
        if(cursor_shenghuoyongp.moveToFirst())
        {
            do{
                m_shenghuoyongping=cursor_shenghuoyongp.getInt(0);
            }while (cursor_shenghuoyongp.moveToNext());
        }m_shenghuoyongping2=m_shenghuoyongping;
        cursor_shenghuoyongp.close();
        if(cursor_qita.moveToFirst())
        {
            do {
                m_qita=cursor_qita.getInt(0);

            }while (cursor_qita.moveToNext()) ;
        }m_qita2=m_qita;
        cursor_qita.close();


    }
    public void initview()
    {
        btn_search=findViewById(R.id.btn_serach);
        bt_shouru=findViewById(R.id.bt_shouru);
        user = findViewById(R.id.btn_yonghuzhongxin);
        fenxibaogao = findViewById(R.id.btn_fenxibaogao);
        jizhang = findViewById(R.id.btn_jizhang);
        chakan=findViewById(R.id.btn_chakanzhangdan);

        date1 = findViewById(R.id.Et_date1);
        date2 = findViewById(R.id.Et_date2);
        tv_expense=findViewById(R.id.total_expense2);
        tv_jiaotong=findViewById(R.id.total_jiaotong2);
        tv_jiaoyu=findViewById(R.id.total_jiaoyu2);
        tv_yilioa=findViewById(R.id.total_yiliao2);
        tv_zhufang=findViewById(R.id.total_zhufang2);
        tv_yule=findViewById(R.id.total_yule2);
        tv_qita=findViewById(R.id.total_qita2);
        tv_shenghuoyongp=findViewById(R.id.total_shenghuo2);
        tv_expense.setText(String.valueOf(m_jiaotong+m_zhufang+m_yiliao+m_yule+m_jiaoyu+m_shenghuoyongping+m_qita)+" 元");
        tv_jiaotong.setText(String.valueOf(m_jiaotong2)+" 元");
        tv_jiaoyu.setText(String.valueOf((m_jiaoyu2))+" 元");
        tv_yilioa.setText(String.valueOf(m_yiliao2)+" 元");
        tv_zhufang.setText((String.valueOf(m_zhufang2))+" 元");
        tv_yule.setText(String.valueOf(m_yule2)+" 元");
        tv_shenghuoyongp.setText((String.valueOf(m_shenghuoyongping2))+" 元");
        tv_qita.setText(String .valueOf(m_qita2)+" 元");
    }
    private void setData(int count) {// 准备x"轴"数据：在i的位置，显示x[i]字符串
        ArrayList<String> xVals = new ArrayList<String>(); // 真实的饼状图百分比分区。// Entry包含两个重要数据内容：position和该position的数值。
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int xi = 0; xi < count; xi++) {
            xVals.add(xi, x[xi]);// y[i]代表在x轴的i位置真实的百分比占
            yVals.add(new Entry(y[xi], xi)); }
        PieDataSet yDataSet = new PieDataSet(yVals, "百分比占");
        // 每个百分比占区块绘制的不同颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.DKGRAY);
        colors.add(Color.WHITE);
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


    }
    public void setclicklinster() {
        onClick onclick = new onClick();
        chakan.setOnClickListener(onclick);
        fenxibaogao.setOnClickListener(onclick);
        user.setOnClickListener(onclick);
        jizhang.setOnClickListener(onclick);
        bt_shouru.setOnClickListener(onclick);

    }
    private class onClick implements View.OnClickListener {

        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId())
            {
                case R.id.btn_yonghuzhongxin:
                    intent=new Intent(AnalysisChartActivity.this,UserActivity.class);
                    break;
                case R.id.btn_chakanzhangdan:
                    intent=new Intent(AnalysisChartActivity.this,BillListActivity.class);
                    break;
                case R.id.btn_fenxibaogao:
                    intent=new Intent(AnalysisChartActivity.this,AnalysisChartActivity.class);
                    break;
                case R.id.btn_jizhang:
                    intent=new Intent(AnalysisChartActivity.this,MainPayoutActivity.class);
                    break;
                case R.id.bt_shouru:
                    intent=new Intent(AnalysisChartActivity.this,AnalysisIncomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AnalysisChartActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisChartActivity.this.date1.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    protected void showDatePickDlg1() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog1 = new DatePickerDialog(AnalysisChartActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AnalysisChartActivity.this.date2.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog1.show();


    }

}