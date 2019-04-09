package com.example.kechennsheji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MgPayinsortActivity extends AppCompatActivity {

        private TextView mTv_1;
        private TextView mTv_3;
        private TextView mTv_2;
        private TextView mTv_4;
        private TextView mTv_5;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mg_payinsort);
            mTv_1=findViewById(R.id.TV_1);
            mTv_2=findViewById(R.id.TV_2);
            mTv_3=findViewById(R.id.TV_3);
            mTv_4=findViewById(R.id.TV_4);
            mTv_5=findViewById(R.id.TV_5);
            setclickListenrt();
        }

        private void setclickListenrt()
        {
          onClick onclick = new onClick();
            mTv_1.setOnClickListener(onclick);
            mTv_2.setOnClickListener(onclick);
            mTv_3.setOnClickListener(onclick);
            mTv_4.setOnClickListener(onclick);
            mTv_5.setOnClickListener(onclick);


        }
    private class  onClick implements View.OnClickListener
    {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId())
            {
                case R.id.TV_1:
                    intent = new Intent(MgPayinsortActivity.this,MainPayinActivity.class);
                    intent.putExtra("name", mTv_1.getText().toString());
                    break;
                case R.id.TV_2:
                    intent = new Intent(MgPayinsortActivity.this,MainPayinActivity.class);
                    intent.putExtra("name", mTv_2.getText().toString());
                    break;
                case R.id.TV_3:
                    intent = new Intent(MgPayinsortActivity.this,MainPayinActivity.class);
                    intent.putExtra("name", mTv_3.getText().toString());
                    break;
                case R.id.TV_4:
                    intent = new Intent(MgPayinsortActivity.this,MainPayinActivity.class);
                    intent.putExtra("name", mTv_4.getText().toString());
                    break;
                case R.id.TV_5:
                    intent = new Intent(MgPayinsortActivity.this,MainPayinActivity.class);
                    intent.putExtra("name", mTv_5.getText().toString());
                    break;
            }
            startActivity(intent);

        }
    }

}
