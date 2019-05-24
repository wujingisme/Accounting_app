package com.example.kechennsheji.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kechennsheji.LogInActivity;
import com.example.kechennsheji.MD5Utils;
import com.example.kechennsheji.R;

import org.w3c.dom.Text;

public class ModfypswActivity extends AppCompatActivity {
    private String TAG="test";
    private EditText et_original_psw,et_new_psw,et_pswagain;
    private Button bt_save;
    String original_psw,new_psw,psw_again;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modfypsw);
        initView();
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(original_psw))
                { Toast.makeText(ModfypswActivity.this,"请输入原始密码",Toast.LENGTH_SHORT).show();
                    return; }
                else if(!MD5Utils.MD5(original_psw).equals(readPsw()))
                { Toast.makeText(ModfypswActivity.this,"输入的密码与原始密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(new_psw))
                { Toast.makeText(ModfypswActivity.this,"请输入要更改的密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(psw_again))
                { Toast.makeText(ModfypswActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return; }
                else if(!new_psw.equals((psw_again)))
                { Toast.makeText(ModfypswActivity.this,"两次输入的密码不一致！",Toast.LENGTH_SHORT).show();
                }
                else{ Toast.makeText(ModfypswActivity.this,"新密码设置成功！",Toast.LENGTH_SHORT).show();;
                    modifyPsw(new_psw);
                    Intent intent=new Intent(ModfypswActivity.this,LogInActivity.class);
                    startActivity(intent); }

            }
        });

    }
    public  void modifyPsw(String newPsw)
    { String md5Psw=MD5Utils.MD5((newPsw));
    SharedPreferences sp = getSharedPreferences("LoginInfo", MODE_PRIVATE);
    //logininfo表示文件名
    String spPsw=sp.getString("loginUserName","");
    SharedPreferences.Editor editor = sp.edit();
    editor.putString(spPsw, md5Psw);//
    editor.commit();//提交修改
    }

    public void initView()
    {
        et_original_psw=findViewById(R.id.original_psw);
        et_new_psw=findViewById(R.id.new_psw);
        et_pswagain=findViewById(R.id.psw_again);
        bt_save=findViewById(R.id.bt_save);
    }
    public void getEditString()
    {
        original_psw =et_original_psw.getText().toString().trim();
        new_psw=et_new_psw.getText().toString().trim();
        psw_again=et_pswagain.getText().toString().trim();
    }
    public String readPsw()
    {
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        String spPsw=sp.getString("loginUserName","");
        String spPsw1=sp.getString(spPsw,"");
        return spPsw1;
    }
}
