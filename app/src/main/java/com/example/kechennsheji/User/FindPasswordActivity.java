package com.example.kechennsheji.User;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kechennsheji.MD5Utils;
import com.example.kechennsheji.R;

public class FindPasswordActivity extends AppCompatActivity {
    private EditText et_username,et_id;
    private String s_username,s_id,sp_id,sp_name,MD5psw;
    private Button confin;
    private static  final String TAG="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        et_id=findViewById(R.id.name1);
        et_username=findViewById(R.id.username1);
        confin=findViewById(R.id.confin);
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        sp_name=sp.getString("loginUserName","");
        //在LoginInfo.xml里获取用户名
        SharedPreferences sp1=getSharedPreferences("MibaoInfo",MODE_PRIVATE);
        sp_id=sp1.getString(sp_name,"");
        //通过获取的用户名来获取在MibaoInfo.xml里该用户的密保信息

        confin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_id=et_id.getText().toString();
                s_username=et_username.getText().toString();
                Log.d(TAG,s_username);

                if(TextUtils.isEmpty(s_username))//输入用户名为空
                {
                    Toast.makeText(FindPasswordActivity.this,"请输入用户名！",Toast.LENGTH_SHORT).show();
                }
               else if(TextUtils.isEmpty(s_id))//输入密保信息为空
                {
                    Toast.makeText(FindPasswordActivity.this,"请输身份证后六位数！",Toast.LENGTH_SHORT).show();
                }
                else if(s_username.equals(sp_name)&&s_id.equals(sp_id))//输入信息都正确的时候
                {
                    MD5psw=MD5Utils.MD5("123456");
                    SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString(sp_name,MD5psw);
                    editor.commit();
                    Toast.makeText(FindPasswordActivity.this,"密码重置为123456",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(FindPasswordActivity.this,"用户名或身份证后六位数不正确！",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
