package com.trydev.scoreoceania;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Team;
import util.ConnectionDetector;

import static android.view.View.*;

public class InputTeamActivity extends AppCompatActivity implements OnClickListener{

    private DatabaseReference mDatabase;
    EditText namaTeam, namaUniv;
    Button simpanTeam;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_team);
        cd = new ConnectionDetector(this);

        namaTeam = (EditText) findViewById(R.id.namaTeam);
        namaUniv = (EditText) findViewById(R.id.namaUniv);
        simpanTeam = (Button) findViewById(R.id.simpanTeam);

        simpanTeam.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void checkInput(){
        if (namaTeam.getText().toString().equals("") || namaUniv.getText().toString().equals("")){
            Toast.makeText(this, "team/univ tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            storeDatabase(namaTeam.getText().toString(), namaUniv.getText().toString());
        }
    }

    private void storeDatabase(String tim, String univ) {
        Team team = new Team();
        team.setNama(tim);
        team.setUniversitas(univ);
        mDatabase.child("tim").push().setValue(team);
        Toast.makeText(this, "save done", Toast.LENGTH_SHORT).show();
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.simpanTeam:
                if (cd.isConnected()) {
                    checkInput();
                } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
