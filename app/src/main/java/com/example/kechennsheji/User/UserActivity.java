package com.example.kechennsheji.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kechennsheji.AnalysisChartActivity;
import com.example.kechennsheji.BillListActivity;
import com.example.kechennsheji.MainPayoutActivity;
import com.example.kechennsheji.R;

public class UserActivity extends AppCompatActivity {
    private Button jizhang;
    private Button chakan;
    private Button fenxibaogao;
    private Button user;
    private Button mBtn_setting;
    private TextView mTv_username;
    private String spUsername;
    private SharedPreferences sp;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        jizhang=findViewById(R.id.btn_jizhang);
        chakan=findViewById(R.id.btn_chakanzhangdan);
        fenxibaogao=findViewById(R.id.btn_fenxibaogao);
        user=findViewById(R.id.btn_yonghuzhongxin);
        mBtn_setting=findViewById(R.id.setting);

        mTv_username=findViewById(R.id.tv_user_name);
        setclicklinster();
        sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        spUsername=sp.getString("loginUserName","");
        mTv_username.setText(spUsername);

    }
    public void setclicklinster()
    {
        onClick onclick=new onClick();
        jizhang.setOnClickListener(onclick);
        chakan.setOnClickListener(onclick);
        fenxibaogao.setOnClickListener(onclick);
        user.setOnClickListener(onclick);
        mBtn_setting.setOnClickListener(onclick);
        //mTv_username.setOnClickListener(onclick);

    }

     private class onClick implements View.OnClickListener{
        public void onClick(View view)
        {
            Intent intent=null;
            switch (view.getId())
            {
                case R.id.btn_jizhang:
                    intent=new Intent(UserActivity.this,MainPayoutActivity.class);
                    break;
                case R.id.btn_chakanzhangdan:
                    intent=new Intent(UserActivity.this,BillListActivity.class);
                    break;
                case R.id.btn_fenxibaogao:
                    intent=new Intent(UserActivity.this,AnalysisChartActivity.class);
                    break;
                case R.id.btn_yonghuzhongxin:
                    intent=new Intent(UserActivity.this,UserActivity.class);
                    break;
                case R.id.setting:
                    intent=new Intent(UserActivity.this,SettingActivity.class);
                    break;
            }
            startActivity(intent);

        }

     }
}
