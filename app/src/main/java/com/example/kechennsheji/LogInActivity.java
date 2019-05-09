package com.example.kechennsheji;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.style.LineHeightSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kechennsheji.SQLite.AppUtils;
import com.example.kechennsheji.User.FindPasswordActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {


    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;

    private Button mBtnLogin;
    private EditText mEt_username;
    private EditText mEt_password;
    private TextView mTextView,mTv_findpwd;
    private String userName,psw,spPsw;
   //private ClassLoader appalication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setOrientation(this,AppUtils.WIDTH);
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
        mTv_findpwd=findViewById(R.id.bt_fdpwd);
        mTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        mTv_findpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogInActivity.this,FindPasswordActivity.class);
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


               // startActivity(new Intent(LogInActivity.this,MainPayoutActivity.class));
              if(isMobileNum(userName))
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
                        }
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



    public  static void setDensity(final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = AppUtils.getStatusBarHeight(application);
        if (appDensity == 0) { //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity; //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks()
            { @Override
            public void onConfigurationChanged(Configuration newConfig) { //字体改变后,将appScaledDensity重新赋值
                if (newConfig != null && newConfig.fontScale > 0)
                { appScaledDensity =application.getResources().getDisplayMetrics().scaledDensity; } }
                @Override
                public void onLowMemory() { } }); } }
                //此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好了)
    public static void setDefault(Activity activity) { setAppOrientation(activity, AppUtils.WIDTH); }
    //此方法用于在某一个Activity里面更改适配的方向
    public static void setOrientation(Activity activity, String orientation)
    { setAppOrientation(activity, orientation); }
    /** * targetDensity * targetScaledDensity * targetDensityDpi
     * 这三个参数是统一修改过后的值 * <p> * orientation:方向值,传入width或height */
    private static void setAppOrientation(@Nullable Activity activity, String orientation)
    { float targetDensity;
        if (orientation.equals("height"))
        { targetDensity = (appDisplayMetrics.heightPixels - barHeight) / 667f; }
        else { targetDensity = appDisplayMetrics.widthPixels / 360f; }
        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        /** * * 最后在这里将修改过后的值赋给系统参数 * * 只修改Activity的density值 */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi; }

}
