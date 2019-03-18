package com.example.kechennsheji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
  private WebView mWvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWvMain=findViewById(R.id.wv);
       mWvMain.loadUrl("file://android_asset/test.html");
    }
}
