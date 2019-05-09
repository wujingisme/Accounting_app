package com.example.kechennsheji.User;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.kechennsheji.LogInActivity;
import com.example.kechennsheji.R;

public class SettingActivity extends AppCompatActivity {
    private Button user_info;
    private Button login_out;
    private Button chanegepsw;
    private Button mibao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        user_info=findViewById(R.id.bt_userinfo);
        login_out=findViewById(R.id.bt_loginout);
        chanegepsw=findViewById(R.id.bt_changepsw);
        mibao=findViewById(R.id.bt_mibao);
        setclicklinster();
    }
    public void setclicklinster()
    {
       onClick onclick=new onClick();
        user_info.setOnClickListener(onclick);
        login_out.setOnClickListener(onclick);
        chanegepsw.setOnClickListener(onclick);
        mibao.setOnClickListener(onclick);

    }
    private class onClick implements View.OnClickListener{
        public void onClick(View view)
        {
            Intent intent=null;
            switch (view.getId())
            {
                case R.id.bt_changepsw:
                    intent=new Intent(SettingActivity.this,ModfypswActivity.class);
                    break;
                case R.id.bt_userinfo:
                    intent=new Intent(SettingActivity.this,UserInfoActivity.class);
                    break;
                case R.id.bt_loginout:
                    intent=new Intent(SettingActivity.this,LogInActivity.class);
                    break;
                case R.id.bt_mibao:
                    intent=new Intent(SettingActivity.this,MibaoActivity.class);
                    break;
            }
            startActivity(intent);

        }

    }
}
