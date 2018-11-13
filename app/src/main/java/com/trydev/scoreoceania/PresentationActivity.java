package com.trydev.scoreoceania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PresentationActivity extends AppCompatActivity implements View.OnClickListener{
    Button pu, fpd, tc, aa;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        pu = (Button) findViewById(R.id.pu);
        fpd = (Button) findViewById(R.id.fpd);
        tc = (Button) findViewById(R.id.tc);
        aa = (Button) findViewById(R.id.aa);

        key = getIntent().getStringExtra("key");
        pu.setOnClickListener(this);
        fpd.setOnClickListener(this);
        tc.setOnClickListener(this);
        aa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.pu:
                intent = new Intent(PresentationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "presentation");
                intent.putExtra("criteria", "pu");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.fpd:
                intent = new Intent(PresentationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "presentation");
                intent.putExtra("criteria", "fpd");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.tc:
                intent = new Intent(PresentationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "presentation");
                intent.putExtra("criteria", "tc");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.aa:
                intent = new Intent(PresentationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "presentation");
                intent.putExtra("criteria", "aa");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
        }
    }
}
