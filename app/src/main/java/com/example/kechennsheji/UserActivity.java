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
    private Button user_info;
    private Button login_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        jizhang=findViewById(R.id.btn_jizhang);
        chakan=findViewById(R.id.btn_chakanzhangdan);
        fenxibaogao=findViewById(R.id.btn_fenxibaogao);
        user=findViewById(R.id.btn_yonghuzhongxin);
        user_info=findViewById(R.id.bt_userinfo);
        login_out=findViewById(R.id.bt_loginout);

       setclicklinster();
    }
    public void setclicklinster()
    {
        onClick onclick=new onClick();
        jizhang.setOnClickListener(onclick);
        chakan.setOnClickListener(onclick);
        fenxibaogao.setOnClickListener(onclick);
        user.setOnClickListener(onclick);
        user_info.setOnClickListener(onclick);
        login_out.setOnClickListener(onclick);

    }
     private class onClick implements View.OnClickListener{
        public void onClick(View view)
        {
            Intent intent=null;
            switch (view.getId())
            {
                case R.id.btn_jizhang:
                    intent=new Intent(UserActivity.this,MainActivity.class);
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
                case R.id.bt_userinfo:
                    intent=new Intent(UserActivity.this,UserInfoActivity.class);
                    break;
                case R.id.bt_loginout:
                    intent=new Intent(UserActivity.this,LogInActivity.class);
                    break;
            }
            startActivity(intent);

        }

     }
}
