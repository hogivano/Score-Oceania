package com.trydev.scoreoceania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import util.ConnectionDetector;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    EditText username, password;
    ImageView positionTeam;
    Button login;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        positionTeam = (ImageView) findViewById(R.id.positionTeam);

        cd = new ConnectionDetector(this);
        login.setOnClickListener(this);
        positionTeam.setOnClickListener(this);
    }

    public void cekConnection(){
        if (cd.isConnected()) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        } else {
            Toast.makeText(this, "please check your connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void cekInput(){
        if (username.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this, "Username/password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            auth();
        }
    }

    public void auth (){
        if (username.getText().toString().equals("oceania") && password.getText().toString().equals("kerenbingits")){
            cekConnection();
        } else {
            Toast.makeText(this, "username atau password salah", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                cekInput();
                break;
            case R.id.positionTeam:
                if (cd.isConnected()) {
                    startActivity(new Intent(MainActivity.this, PositionActivity.class));
                } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
