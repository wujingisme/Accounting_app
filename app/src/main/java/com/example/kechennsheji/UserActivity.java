package com.example.kechennsheji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {
    private Button jizhang;
    private Button chakan;
    private Button fenxibaogao;
    private Button user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        jizhang=findViewById(R.id.btn_jizhang);
        jizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        chakan=findViewById(R.id.btn_chakanzhangdan);
        chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,BillListActivity.class);
                startActivity(intent);
            }
        });
        fenxibaogao=findViewById(R.id.btn_fenxibaogao);
        fenxibaogao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,AnalysisChartActivity.class);
                startActivity(intent);
            }
        });
        user=findViewById(R.id.btn_yonghuzhongxin);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

    }
}
