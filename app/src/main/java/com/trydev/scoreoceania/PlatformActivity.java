package com.trydev.scoreoceania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlatformActivity extends AppCompatActivity implements View.OnClickListener {
    Button td, mPre, mPro;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform);

        key = getIntent().getStringExtra("key");
        td = (Button) findViewById(R.id.td);
        mPre = (Button) findViewById(R.id.mPre);
        mPro = (Button) findViewById(R.id.mPro);

        td.setOnClickListener(this);
        mPre.setOnClickListener(this);
        mPro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.td:
                intent = new Intent(PlatformActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "platform");
                intent.putExtra("criteria", "td");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.mPre:
                intent = new Intent(PlatformActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "platform");
                intent.putExtra("criteria", "mPre");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.mPro:
                intent = new Intent(PlatformActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "platform");
                intent.putExtra("criteria", "mPro");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
        }
    }
}
