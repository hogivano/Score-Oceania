package com.trydev.scoreoceania;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import util.ConnectionDetector;

public class DashboardActivity extends AppCompatActivity implements OnClickListener {

    Button btnInputTeam, btnListTeam, btnPosition;
    boolean doubleBackToExitPressedOnce = false;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        cd = new ConnectionDetector(this);

        btnInputTeam = (Button) findViewById(R.id.btnInputTeam);
        btnListTeam = (Button) findViewById(R.id.btnListTeam);
        btnPosition = (Button) findViewById(R.id.btnPosition);
        btnInputTeam.setOnClickListener(this);
        btnListTeam.setOnClickListener(this);
        btnPosition.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInputTeam:
                startActivity(new Intent(DashboardActivity.this, InputTeamActivity.class));
                break;
            case R.id.btnListTeam:
                if (cd.isConnected()) {
                    startActivity(new Intent(DashboardActivity.this, ListTeamActivity.class));
                 } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnPosition:
                if (cd.isConnected()) {
                    startActivity(new Intent(DashboardActivity.this, PositionActivity.class));
                } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
