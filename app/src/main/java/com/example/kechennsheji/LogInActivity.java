package com.example.kechennsheji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    private Button mBtnLogin;
    private EditText mEt_username;
    private EditText mEt_password;
    private TextView mTextView;
    private String userName,psw,spPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        interview();
    }
    private void interview()
    {
        mBtnLogin=findViewById(R.id.bt_login);
        mTextView=findViewById(R.id.bt_mind2);
        mEt_username=findViewById(R.id.et_1);
        mEt_password=findViewById(R.id.et_2);
        mTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=mEt_username.getText().toString().trim();
                psw=mEt_password.getText().toString().trim();
                String MD5psw=MD5Utils.MD5(psw);
                spPsw=readPsw(userName);


                startActivity(new Intent(LogInActivity.this,MainPayoutActivity.class));
            /*    if(isMobileNum(userName))
                {
                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(LogInActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(psw)) {
                        Toast.makeText(LogInActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }  else if (MD5psw.equals(spPsw)) {
                        Toast.makeText(LogInActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();

                        saveLoginStatu(true,userName);
                        Intent data =new Intent();
                        data.putExtra("isLogin",true);
                        setResult(RESULT_OK,data);


                        startActivity(new Intent(LogInActivity.this,MainPayoutActivity.class));
                        LogInActivity.this.finish();
                        return;
                    } else if ((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!MD5psw.equals(spPsw)))
                    {
                        Toast.makeText(LogInActivity.this,"输入的密码和用户名不一致！",Toast.LENGTH_SHORT).show();
                        return;
                    }else
                    {
                        Toast.makeText(LogInActivity.this,"此用户名不存在！",Toast.LENGTH_SHORT).show();
                    }
                    }else
                        {
                            Toast.makeText(LogInActivity.this,"您输入的手机号有误！",Toast.LENGTH_SHORT).show();
                        }*/
            }
        });

    }
   private  void saveLoginStatu(boolean status,String username)
            //保存登陆信息到用户界面
    {
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        //logininfo表示文件名
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", userName);//存入登陆状态时的用户名；
        editor.commit();//提交修改
       // editor.clear();
    }



        //从sharedPreference 中根据用户名读取密
    private String readPsw(String userName) {
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        return sp.getString(userName, "");
    }
    public static boolean isMobileNum(String mobiles)
    {
        Pattern pattern=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m= pattern.matcher(mobiles);
        return m.matches();
    }

}
