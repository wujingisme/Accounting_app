package com.example.kechennsheji;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

public class LogInActivity extends AppCompatActivity {
private Button mBtnLogin;
private EditText mEditText;
private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnLogin=findViewById(R.id.bt_login);
        mTextView=findViewById(R.id.bt_mind2);
        mEditText=findViewById(R.id.et_1);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this,"登录成功!",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
      mTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

      mTextView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(LogInActivity.this,RegisterActivity.class);
              startActivity(intent);
          }
      });

    }
}
