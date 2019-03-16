package com.example.kechennsheji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.PrivateKey;

public class RegisterActivity extends AppCompatActivity {
    private Button mBtn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mBtn_register=findViewById(R.id.bt_register);
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this,"恭喜您注册成功！",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(RegisterActivity.this,LogInActivity.class);
                startActivity(intent);
            }
        });
    }
}
