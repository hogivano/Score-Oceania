package com.trydev.scoreoceania;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Team;
import util.ConnectionDetector;

public class EditTeamActivity extends AppCompatActivity implements View.OnClickListener{

    Button updateTeam;
    EditText editNama, editUniv;
    Team team;
    DatabaseReference ref;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        cd = new ConnectionDetector(this);

        team = (Team) getIntent().getSerializableExtra("team");

        updateTeam = (Button) findViewById(R.id.updateTeam);
        editNama = (EditText) findViewById(R.id.editNama);
        editUniv = (EditText) findViewById(R.id.editUniv);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("tim").child(team.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editNama.setText(dataSnapshot.child("nama").getValue(String.class));
                editUniv.setText(dataSnapshot.child("universitas").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditTeamActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });

        updateTeam.setOnClickListener(this);
    }

    private void checkInput() {
        if (editUniv.getText().toString().equals("") || editNama.getText().toString().equals("")){
            Toast.makeText(this, "semua harus terisi", Toast.LENGTH_SHORT).show();
        } else {
            ref.child("tim").child(team.getKey()).child("nama").setValue(editNama.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    ref.child("tim").child(team.getKey()).child("universitas").setValue(editUniv.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            updateFinalScore();
                            updateScoring();

                            Toast.makeText(EditTeamActivity.this, "update berhasil", Toast.LENGTH_SHORT).show();
                            EditTeamActivity.super.onBackPressed();
                        }
                    });
                }
            });
        }
    }

    public void updateFinalScore(){
        ref.child("final_score").child(team.getKey()).child("nama").setValue(editNama.getText().toString());
        ref.child("final_score").child(team.getKey()).child("universitas").setValue(editUniv.getText().toString());
    }

    public void updateScoring(){
        ref.child("scoring").child(team.getKey()).child("nama").setValue(editNama.getText().toString());
        ref.child("scoring").child(team.getKey()).child("universitas").setValue(editUniv.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateTeam:
                if (cd.isConnected()) {
                    checkInput();
                } else {
                    Toast.makeText(this, "you must connection internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
