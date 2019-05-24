package com.example.kechennsheji.User;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kechennsheji.R;

public class UserInfoActivity extends AppCompatActivity {
    private EditText et_username,et_nickname,et_gender,et_age;
    private String username,nickname,gender,age,spUsername;
    private Button bt_save;
    private final static String TAG="insert";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initview();
        SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        spUsername=sp.getString("loginUserName","");
        SharedPreferences sharedPreferences=getSharedPreferences("Userinfo",MODE_PRIVATE);
        String a= spUsername+"nickname";
        String b= spUsername+"age";
        String c= spUsername+"gender";
        Log.d(TAG,"用户信息"+c);
        nickname=sharedPreferences.getString(a,"");
        age=sharedPreferences.getString(b,"");
        gender=sharedPreferences.getString(c,"");
        et_nickname.setText(nickname);
        et_age.setText(age);
        et_gender.setText(gender);
       /* et_nickname.setFocusableInTouchMode(false);
        et_age.setFocusable(false);
        et_age.setFocusableInTouchMode(false)*/;
        et_username.setText(spUsername);
        et_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfoActivity.this,android.R.style.Theme_Holo_Light_Dialog);
                //builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle("选择性别");
                //    指定下拉列表的显示数据
                final String[] cities = {"男", "女"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        et_gender.setText(cities[which]);

                    }
                });
                builder.show();

            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getString();
                if (TextUtils.isEmpty(nickname))
                { Toast.makeText(UserInfoActivity.this,
                        "请输入昵称", Toast.LENGTH_SHORT).show(); }
                else if(TextUtils.isEmpty(gender))
                { Toast.makeText(UserInfoActivity.this,
                        "请输入性别", Toast.LENGTH_SHORT).show(); }
                else if(TextUtils.isEmpty(age))
                { Toast.makeText(UserInfoActivity.this,
                        "请输入年龄", Toast.LENGTH_SHORT).show(); }
                else{
                    et_nickname.setText(nickname);
                    et_age.setText(age);
                    et_nickname.setFocusableInTouchMode(false);
                    et_age.setFocusable(false);
                    et_age.setFocusableInTouchMode(false);
                    SharedPreferences sharedPreferences=getSharedPreferences("Userinfo",MODE_PRIVATE);
                   SharedPreferences.Editor editor=sharedPreferences.edit();
                   editor.putString(username+"nickname",nickname);
                   editor.putString(username+"age",age);
                   editor.putString(username+"gender",gender);
                   editor.commit();
                    Toast.makeText(UserInfoActivity.this, "成功保存个人信息！", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void getString()
    {
        nickname=et_nickname.getText().toString();
        username=et_username.getText().toString();
        age=et_age.getText().toString();
        gender=et_gender.getText().toString();
    }
    public void initview()
    {
        et_username=findViewById(R.id.et_username);
        et_nickname=findViewById(R.id.et_nickname);
        et_gender=findViewById(R.id.et_gender);
        et_age=findViewById(R.id.et_age);
        bt_save=findViewById(R.id.save);
    }
}
