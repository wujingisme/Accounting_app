package com.example.kechennsheji.User;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kechennsheji.R;

import org.w3c.dom.Text;


public class MibaoActivity extends AppCompatActivity {
    private EditText username1;
    private Button confin;
    private String s_id,spUsername,sp_id;
    private final static String TAG="sssssss";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mibao);
        username1=findViewById(R.id.username1);
        confin=findViewById(R.id.confin);
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        spUsername=sp.getString("loginUserName","");
        Log.d(TAG,spUsername);
   //获取Loginfo里的用户名
        confin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_id=username1.getText().toString().trim();
                if(TextUtils.isEmpty(s_id))
                { Toast.makeText(MibaoActivity.this,"请输入要设置的密保信息！",Toast.LENGTH_LONG).show(); }
               else if(s_id.length()!=6)
                { Toast.makeText(MibaoActivity.this,"请输入正确的密保信息！",Toast.LENGTH_LONG).show();
                } else
                { saveMibaoInfo(spUsername,s_id);
                } }}); }
    //获取用户密保信息
    public void saveMibaoInfo(String spUsername,String s_id)
    {
        SharedPreferences sp1=getSharedPreferences("MibaoInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp1.edit();
        editor.putString(spUsername,s_id);
        editor.commit();
        Toast.makeText(MibaoActivity.this,"保存成功！",Toast.LENGTH_LONG).show();
    }
}
