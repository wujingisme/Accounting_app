package com.example.kechennsheji;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private String userName,psw,pswagain;
    private Button mBtn_register;
    private EditText mEt_username;
    private EditText mEt_password;
    private EditText mEt_password_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        interview();
    }
         private void interview()
        {
            mEt_username=findViewById(R.id.et_1);
            mEt_password=findViewById(R.id.et_2);
            mEt_password_again=findViewById(R.id.et_3);
            mBtn_register=findViewById(R.id.bt_register);
            mBtn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getEditString();
                    if(isMobileNum(userName)) {
                        if (TextUtils.isEmpty(userName)) {
                            Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(psw)) {
                            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(pswagain)) {
                            Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!psw.equals(pswagain)) {
                            Toast.makeText(RegisterActivity.this, "两次输入的密码不一样！", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (isExistUsername(userName)) {
                            Toast.makeText(RegisterActivity.this, "此账户已存在！", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            saveRegisterInfo(userName, psw);
                            Intent data = new Intent();
                            data.putExtra("userName", userName);
                            setResult(RESULT_OK, data);
                            RegisterActivity.this.finish();

                            Intent intent=new Intent(RegisterActivity.this,LogInActivity.class);
                            startActivity(intent);
                        }
                    }else
                    {
                        Toast.makeText(RegisterActivity.this, "你输入的手机号有误！！", Toast.LENGTH_SHORT).show();


                    }


                }
            });
        }
        public static boolean isMobileNum(String mobiles)
        {
            Pattern pattern=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m= pattern.matcher(mobiles);
            return m.matches();
        }
        private void saveRegisterInfo(String username,String psw)
        {
            String MD5Psw=MD5Utils.MD5(psw);
            SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString(userName,MD5Psw);
            editor.commit();

        }
        private  void getEditString()
        {
           userName =mEt_username.getText().toString().trim();
           psw=mEt_password.getText().toString().trim();
           pswagain=mEt_password_again.getText().toString().trim();
        }
        private Boolean isExistUsername(String username)

        {
            boolean ha_userName=false;
            SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
            String spPsw=sp.getString(userName,"");
            if(!TextUtils.isEmpty(spPsw)){
                ha_userName=true;
            }
            return  ha_userName;
        }

}
