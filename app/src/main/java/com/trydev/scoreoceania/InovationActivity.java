package com.trydev.scoreoceania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InovationActivity extends AppCompatActivity implements View.OnClickListener{
    Button strI, staI, fe,oi;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovation);

        key = getIntent().getStringExtra("key");

        strI = (Button) findViewById(R.id.strI);
        staI = (Button) findViewById(R.id.staI);
        fe = (Button) findViewById(R.id.fe);
        oi = (Button) findViewById(R.id.oi);

        strI.setOnClickListener(this);
        staI.setOnClickListener(this);
        fe.setOnClickListener(this);
        oi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.strI:
                intent = new Intent(InovationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "inovation");
                intent.putExtra("criteria", "strI");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.staI:
                intent = new Intent(InovationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "inovation");
                intent.putExtra("criteria", "staI");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
            case R.id.fe:
                intent = new Intent(InovationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "inovation");
                intent.putExtra("criteria", "fe");
                intent.putExtra("key", key);

                startActivity(intent);
                break;

            case R.id.oi:
                intent = new Intent(InovationActivity.this, InputScoreActivity.class);
                intent.putExtra("judul", "inovation");
                intent.putExtra("criteria", "oi");
                intent.putExtra("key", key);

                startActivity(intent);
                break;
        }
    }
}
